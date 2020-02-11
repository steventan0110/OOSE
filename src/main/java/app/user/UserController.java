package app.user;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.sql.SQLException;
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
//    public void create(Context ctx) throws SQLException {
//        userRepository.create();
//        ctx.status(201);
//    }

//    public static Handler fetchAllUsernames = ctx-> {
//        UserDao dao = UserDao.instance();
//        Iterable<String> allUsers = dao.getAllUsernames();
//        ctx.json(allUsers);
//    };
//
//    public static Handler fetchById = ctx -> {
//        int id = Integer.parseInt(Objects.requireNonNull(ctx.queryParam("id")));
//        UserDao dao = UserDao.instance();
//        Optional<User> user = dao.getUserById(id);
//        if (!user.isPresent()) {
//            ctx.html("Not Found");
//        } else {
//            User temp = user.get();
//            System.out.println(temp);
//            ctx.json(temp);
//        }
//    };
}
