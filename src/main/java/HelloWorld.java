import io.javalin.Javalin;
import io.javalin.http.staticfiles.*;
import static io.javalin.apibuilder.ApiBuilder.*;

public class HelloWorld {
    public static void main(String[] args) {
//        Javalin app = Javalin.create().start(7000);
        Javalin app = Javalin.create(config -> { config.addStaticFiles("/public"); });
        app.start(8080);
        //app.get("/", ctx -> ctx.result("Hello World"));
    }
}