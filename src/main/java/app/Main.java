package app;
import app.user.UserController;
import app.user.UserRepository;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        //initialization of database
        Connection connection = DriverManager.getConnection("jdbc:sqlite:./OOSE.db");
        UserRepository UserRepository = new UserRepository(connection);
        UserController UserController = new UserController(UserRepository);
        Javalin app = Javalin.create(config -> { config.addStaticFiles("/public"); });

        //before -> direct to login page
        app.get("/auth", ctx -> {
            ctx.render("/public/login/login.html");
        });
        app.get("/createAccount", ctx-> {
           ctx.render("/public/login/createAccount.html");
        });
        //definition of routes:
        //TODO:the frontend interaction is not defined for the controller yet!
        app.routes(()-> {
           path("user",()-> {
              get(UserController::getAll);
              post(UserController::createUser);
               path(":id", ()-> {
                  delete(UserController::delete);
                  put(UserController::update);
               });
           });
        });

        app.error(404, ctx -> ctx.result("Your requested Page is not found!"));
        app.events(event -> {
            event.serverStopped(() -> {connection.close();});
        });
        app.start(8080);

    }
}