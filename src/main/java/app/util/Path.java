package app.util;

public class Path {
    public static class Web {
        public static final String INDEX = "/index";
        public static final String LOGIN = "/auth/login";
        public static final String LOGOUT = "/auth/logout";
        public static final String FirstPage = "/user/index";


    }

    public static class Template {
        public static final String INDEX = "/velocity/index/index.vm";
        public static final String LOGIN = "/velocity/login/login.vm";
        public static final String NOT_FOUND = "/velocity/notFound.vm";
        public static final String FirstPage = "/velocity/user/index.vm";
        public static final String CREATEACCOUNT = "/velocity/login/createAccount.vm";
    }
}
