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
    public void createUser(Context ctx) throws SQLException {
        User temp = new User(ctx.formParam("name"));
        userRepository.createUser(temp);
        ctx.status(201);
    }

    public void update(Context ctx) throws SQLException, UserNotFoundException {
        User temp = userRepository.getOneUser(Integer.parseInt(ctx.pathParam("id")));
        temp.setName(ctx.formParam("name"));
        userRepository.update(temp);
        ctx.status(204);
    }

    public void delete(Context ctx) throws SQLException, UserNotFoundException {
        userRepository.delete(userRepository.getOneUser(Integer.parseInt(ctx.pathParam("id"))));
        ctx.status(204);
    }


}
