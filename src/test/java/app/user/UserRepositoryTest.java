package app.user;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ListIterator;

import static org.junit.Assert.*;

public class UserRepositoryTest {
    private UserRepository unit;
    private Connection connection;
    private final static String URI = "jdbc:sqlite:./Test.db";

    @Before
    public void setUp() throws Exception {
        //initialize connection
        connection = DriverManager.getConnection(URI);
        unit = new UserRepository(connection);

        Statement statement = connection.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS users " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)";
        statement.execute(sql);
    }

    @After
    public void tearDown() throws Exception {
        String sql = "DROP TABLE IF EXISTS users;";
        Statement st = connection.createStatement();
        st.execute(sql);
        connection.close();
    }
    @Test
    public void createUser() throws SQLException {
        User user = new User("Steven");
        assertEquals(0, user.getId());
        unit.createUser(user);
        assertEquals(1, user.getId());
        User user2 = new User("Shuhao");
        unit.createUser(user2);
        assertEquals(2, user2.getId());
    }

    @Test
    public void getOneUser() throws SQLException, UserNotFoundException {
        User user = new User("Steven");
        unit.createUser(user);
        assertEquals(1, unit.getOneUser(1).getId());
        assertEquals("Steven", unit.getOneUser(1).getName());
    }

    @Test(expected = UserNotFoundException.class)
    public void getOneUserNotFound() throws SQLException, UserNotFoundException {
        unit.getOneUser(1);
    }

    @Test
    public void getAll() throws SQLException {
        User user = new User("Steven");
        User user1 = new User("Steven1");
        User user2 = new User("Steven2");
        unit.createUser(user);
        unit.createUser(user1);
        unit.createUser(user2);
        List<User> result = unit.getAll();
        assertEquals(1, result.get(0).getId());
        assertEquals("Steven", result.get(0).getName());
        assertEquals(2, result.get(1).getId());
        assertEquals("Steven1", result.get(1).getName());
        assertEquals(3, result.get(2).getId());
        assertEquals("Steven2", result.get(2).getName());
    }

    @Test
    public void update() throws SQLException, UserNotFoundException {
        User user = new User("Steven");
        unit.createUser(user);
        User new_user = new User("StevenTan");
        new_user.setId(user.getId());
        unit.update(new_user);
        assertEquals("StevenTan", unit.getOneUser(user.getId()).getName());
    }

    @Test(expected = UserNotFoundException.class)
    public void updateUserNotFound() throws SQLException, UserNotFoundException {
        User user = new User("Steven");
        unit.createUser(user);
        User new_user = new User("StevenTan");
        new_user.setId(user.getId()+1);
        unit.update(new_user);
    }

    @Test
    public void delete() throws SQLException, UserNotFoundException {
        User user = new User("Steven");
        unit.createUser(user);
        assertEquals(1, unit.getOneUser(1).getId());
        unit.delete(user);
        List<User> allUser = unit.getAll();
        assertEquals(0, allUser.size());
    }
}