package app;
import app.login.LoginController;
import app.user.UserController;
import app.user.UserRepository;
import app.util.Path;
import app.util.viewUtil;
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

        app.routes(()-> {
            before(LoginController.ensureLogin);
            path("/", ()->{
                get(viewUtil.serveIndexPage);
            });
            path(Path.Web.INDEX, ()->{
                get(viewUtil.serveIndexPage);
            });
            path(Path.Web.LOGIN, ()->{
                get(LoginController.serveLoginPage);
                path("create",()->{
                    get(LoginController.serveCreateAccountPage);
                    post(UserController::createUser);
                });
            });

            //This block of code is currently not used
            path(Path.Web.USER,()-> {
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