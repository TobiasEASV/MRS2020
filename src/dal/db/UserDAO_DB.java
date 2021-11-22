package dal.db;

import be.Movie;
import be.User;
import dal.IMovieDataAccess;
import dal.IUserDataAccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAO_DB implements IUserDataAccess {
    private MyDatabaseConnector databaseConnector;
    public static List<User> allUsers = new ArrayList<>();

    public UserDAO_DB(){
        databaseConnector = new MyDatabaseConnector();
    }


    @Override
    public List<User> getAllUsers() throws Exception {
        if(allUsers.isEmpty()){
            try (Connection con = databaseConnector.getConnection()) {
                System.out.println("Connection to database");

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM [User]");

                while (rs.next()) {
                    String name = rs.getString("Name");
                    User user = new User (name);
                    allUsers.add(user);
                }

            } catch (Exception e) {
                throw new Exception("Connection fjle");
            }
        }

        return allUsers;
    }

    @Override
    public User getUser(int id) throws Exception {
        return null;
    }

    @Override
    public void deleteUser(User user) throws Exception {

    }

    @Override
    public User createUser(String name) throws Exception {
        return null;
    }

    @Override
    public void updateUser(User user) throws Exception {

    }
}
