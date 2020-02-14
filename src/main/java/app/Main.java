package app;
import app.index.IndexController;
import app.login.LoginController;
import app.user.UserController;
import app.user.UserRepository;
import app.util.viewUtil;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.*;
import sun.rmi.runtime.Log;

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
        LoginController LoginController = new LoginController(UserRepository);
        Javalin app = Javalin.create(config -> {
            config.addStaticFiles("/public");
            config.addStaticFiles("/public/meetme");
        });


        app.get("/about", ctx -> ctx.render("public/userFirst.html"));
        //definition of routes:
        //TODO:the frontend interaction is not defined for the controller yet!
        app.routes(()-> {
            before(LoginController.ensureLogin);
            path("auth", ()->{
                path("login", ()->{
                   post(LoginController::verifyLogin);
                   get(LoginController.serveLoginPage);
                });
                path("create",()->{
                    post(UserController::createUser);
                    get(LoginController.serveCreateAccountPage);
                });
            });
            path("user",()-> {
                path("index", ()->{
                   get(IndexController.serveIndexPage);
                });
                get(UserController::getAll);
                path(":id", ()-> {
                    delete(UserController::delete);
                    put(UserController::update);
               });
           });
        });

        app.error(404, viewUtil.notFound);
        app.events(event -> {
            event.serverStopped(() -> {connection.close();});
        });
        app.start(10001);

    }
}