package app.util;

public class Path {
    public static class Web {
        public static final String INDEX = "/index";
        public static final String LOGIN = "/auth";
        public static final String LOGOUT = "/logout";
        public static final String USER = "/user";

    }

    public static class Template {
        public static final String INDEX = "/velocity/index.vm";
        public static final String LOGIN = "/velocity/login/login.vm";
        public static final String CREATE = "/velocity/login/createAccount.vm";
        public static final String NOT_FOUND = "/velocity/notFound.vm";
    }
}
