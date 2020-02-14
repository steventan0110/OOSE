package app.login;

import app.user.User;
import app.user.UserRepository;
import app.util.Path;
import app.util.viewUtil;
import io.javalin.http.Context;
import io.javalin.http.Handler;


import java.sql.SQLException;
import java.util.Map;

public class LoginController {


    private UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static Handler ensureLogin = ctx-> {
        if (!ctx.path().startsWith("/user")) {
            return;
        }
        if (ctx.sessionAttribute("currentUser") == null) {
            ctx.sessionAttribute("loginRedirect", ctx.path());
            ctx.redirect("/auth");
        }
    };

    public static Handler serveLoginPage = ctx -> {
        Map<String, Object> model = viewUtil.baseModel(ctx);
        model.put("authenticationSucceeded", "null");
        ctx.render(Path.Template.LOGIN, model);
    };

    public static Handler serveCreateAccountPage = ctx -> {
        Map<String, Object> model = viewUtil.baseModel(ctx);
        ctx.render(Path.Template.CREATEACCOUNT, model);
    };

    public void verifyLogin(Context ctx) throws SQLException {
        Map<String, Object> model = viewUtil.baseModel(ctx);
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");
        if (userRepository.authenticate(email, password)) {
            //successfully authenticate the user
            System.out.println("Login Successful");
            User cur_user = userRepository.getOneUser(email, password);
            model.put("authenticationSucceeded", true);
            model.put("currentUser", cur_user.getName());
            ctx.sessionAttribute("currentUser", cur_user.getName());

            ctx.redirect(Path.Web.FirstPage);
            //Rendering here is not working, need to add to session attribute
            //ctx.render(Path.Template.FirstPage, model);
        } else {
            //login failed
            System.out.println("login failed");
            model.put("authenticationSucceeded", false);
            ctx.render(Path.Template.LOGIN, model);
        }
    }
}
