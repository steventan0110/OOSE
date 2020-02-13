package app.login;

import app.user.UserController;
import app.util.Path;
import app.util.viewUtil;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.Map;

public class LoginController {
    public static Handler ensureLogin = ctx-> {
        if (!ctx.path().startsWith("/user")) {
            return;
        }
        if (ctx.sessionAttribute("currentUser") == null) {
            ctx.sessionAttribute("loginRedirect", ctx.path());
            ctx.redirect(Path.Web.LOGIN);
        }
    };

    public static Handler serveLoginPage = ctx -> {
        Map<String, Object> model = viewUtil.baseModel(ctx);
        ctx.render(Path.Template.LOGIN, model);
    };

    public static Handler serveCreateAccountPage = ctx -> {
        Map<String, Object> model = viewUtil.baseModel(ctx);
        ctx.render(Path.Template.CREATE, model);
    };
//    public void createUser(Context ctx) {
//        UserController uc
//    }
}
