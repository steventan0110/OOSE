package app;
import app.user.UserController;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.*;
import static io.javalin.apibuilder.ApiBuilder.*;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> { config.addStaticFiles("/public"); });
        app.start(8080);
        //app.get("/", ctx -> ctx.result("Hello World"));
        app.error(404, ctx -> ctx.result("Your requested Page is not found!"));
        app.get("/users", UserController.fetchAllUsernames);
        app.get("/users/:id", UserController.fetchById);
    }
}