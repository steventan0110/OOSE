package app.user;

import app.login.LoginController;
import app.util.Path;
import app.util.viewUtil;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class UserController {
    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public void getAll(Context ctx) throws SQLException {
        ctx.json(userRepository.getAll());
    }
    public void createUser(Context ctx) throws SQLException {
        String ctx_role = ctx.formParam("role");
        int role = 0;
        if (ctx_role.equals("Instructor")) {
            role = 1;
        } else if (ctx_role.equals("Student")) {
            role = 2;
        } else {
            role = 3;
        }
        System.out.println(ctx.formParam("phone"));
        User temp = new User(
                ctx.formParam("name"),
                ctx.formParam("password"),
                ctx.formParam("email"),
                role,
                ctx.formParam("phone")
        );
        userRepository.createUser(temp);
        Map<String, Object> model = viewUtil.baseModel(ctx);
        ctx.redirect(Path.Web.LOGIN);

    }

    public void update(Context ctx) throws SQLException, UserNotFoundException {
        User temp = userRepository.getOneUser(Integer.parseInt(ctx.pathParam("id")));
        temp.setName(ctx.formParam("name"));
        userRepository.update(temp);
        ctx.status(204);
    }

    public void delete(Context ctx) throws SQLException, UserNotFoundException {
        userRepository.delete(userRepository.getOneUser(Integer.parseInt(ctx.pathParam("id"))));
        ctx.status(204);
    }


}
