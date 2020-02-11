package app.user;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
    }

    @Test
    public void getAll() {
    }

    @Test
    public void update() {
    }
}