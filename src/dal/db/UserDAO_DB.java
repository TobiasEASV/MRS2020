package dal.db;

import be.User;
import dal.IUserDataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAO_DB implements IUserDataAccess {
    private MyDatabaseConnector databaseConnector;
    private List<User> allUsers = new ArrayList<>();

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

        try (Connection con = databaseConnector.getConnection()) {
            System.out.println("Connection to database");

            String sql  = "SELECT * FROM [User] WHERE Id = (?);";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,id);


            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int Id_DB = rs.getInt("Id");
                String name_DB = rs.getString("Name");
                return new User(Id_DB ,name_DB);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteUser(User user) throws Exception {

        try (Connection con = databaseConnector.getConnection()) {
            System.out.println("Connection to database");

            String sql  = "DELETE FROM [User] WHERE Id = (?);";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,user.getId());
            pstmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public User createUser(String name) throws Exception {

        try (Connection con = databaseConnector.getConnection()) {
            System.out.println("Connection to database");

            String sql = "INSERT INTO [User] (Name) VALUES (?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, name);


            ResultSet rs = pstmt.executeQuery();

            int id_DB = rs.getInt("Id");
            String name_DB = rs.getString("Name");
            User user = new User(id_DB, name_DB);
            allUsers.add(user);
            return user;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateUser(User user) throws Exception {

        try (Connection con = databaseConnector.getConnection()) {
            System.out.println("Connection to database");

            String sql  = "UPDATE [User] SET Name = (?) WHERE Id = (?);";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, user.getName());
            pstmt.setInt(2,user.getId());
            pstmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
