package app.index;

import app.user.User;
import app.user.UserRepository;
import app.util.Path;
import app.util.viewUtil;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.Map;

public class IndexController {
    public static Handler serveIndexPage = ctx -> {

        Map<String, Object> model = viewUtil.baseModel(ctx);
        String temp = ctx.sessionAttribute("currentUser");
        model.put("currentUser", temp);
        ctx.render(Path.Template.FirstPage, model);
    };
}
