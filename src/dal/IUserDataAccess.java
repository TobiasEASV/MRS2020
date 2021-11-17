package dal;

import be.Movie;
import be.User;

import java.util.List;

public interface IUserDataAccess {

    public List<User> getAllUsers() throws Exception;

    public User getUser(int id) throws Exception;

    public void deleteUser(User user) throws Exception;

    public User createUser(String name) throws Exception;

    public void updateUser(User user) throws Exception;



}
