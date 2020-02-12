package app.user;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private Connection connection;
    public UserRepository(Connection connection) throws SQLException {
        this.connection = connection;
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS users " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)");
        statement.close();
    }

    public void createUser(User user) throws SQLException {
        String createURL = "INSERT INTO users (name) VALUES(?)";
        PreparedStatement pst = connection.prepareStatement(createURL, Statement.RETURN_GENERATED_KEYS);
        pst.setString(1, user.getName());
        if (pst.executeUpdate() != 1) {
            System.err.println("Unable to create user");
        }
        ResultSet rs = pst.getGeneratedKeys();
        rs.next();
        user.setId(rs.getInt(1));
//        while (rs.next()) {
//            System.out.println(rs.getInt(1));
//        }
        pst.close();
    }

    public User getOneUser(int id) throws SQLException, UserNotFoundException {
        String oneUserURL = "SELECT id, name FROM users WHERE id = ?";
        PreparedStatement pst = connection.prepareStatement(oneUserURL);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        try {
            if (rs.next()) {
                User temp = new User(rs.getString("name"));
                temp.setId(rs.getInt("id"));
                return temp;
            } else {
                throw new UserNotFoundException();
            }
        }
        finally {
            pst.close();
            rs.close();
        }
    }

    public List<User> getAll() throws SQLException {
        List<User> User = new ArrayList<User>();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT id, name FROM users");
        while (result.next()) {
            User temp = new User(result.getString("name"));
            temp.setId(result.getInt("id"));
            User.add(temp);
        }
        result.close();
        statement.close();
        return User;
    }

    public void update(User user) throws SQLException, UserNotFoundException {
        String updateSql = "UPDATE users SET name = ? WHERE id = ?";
        PreparedStatement pst = connection.prepareStatement(updateSql);
        pst.setString(1, user.getName());
        pst.setInt(2, user.getId());
        try {
            //check if any update is made
            if (pst.executeUpdate() == 0) throw new UserNotFoundException();
        }
        finally {
            pst.close();
        }
    }

    public void delete(User user) throws SQLException, UserNotFoundException {
        String deleteURL = "DELETE FROM users WHERE id = ?";
        PreparedStatement pst = connection.prepareStatement(deleteURL);
        pst.setInt(1,user.getId());
        try {
            if (pst.executeUpdate() == 0) throw new UserNotFoundException();
        }
        finally {
            pst.close();
        }
    }
}
