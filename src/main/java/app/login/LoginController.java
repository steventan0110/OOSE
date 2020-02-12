package app.login;

import io.javalin.http.Handler;

public class LoginController {
    public static Handler ensureLogin = ctx-> {
        if (!ctx.path().startsWith("/user")) {
            return;
        }
        if (ctx.sessionAttribute("currentUser") == null) {
            ctx.sessionAttribute("loginRedirect", ctx.path());
            ctx.redirect("/auth");
        }
    };
}
