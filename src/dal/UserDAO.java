package dal;

import be.Movie;
import be.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class UserDAO implements IUserDataAccess{

    private static final String USER_FILE = "data/users.txt";
    private static final String FILE_SEPERATOR = ",";
    public static List<User> allUsers = new ArrayList<>();

    @Override
    public List<User> getAllUsers() throws Exception {
        //List<User> allUsers = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(USER_FILE))){

            while (true) {
                if(allUsers.isEmpty()) {
                    String aLineOfText = br.readLine();
                    if (aLineOfText == null) {
                        break;
                    }
                    String[] userData = aLineOfText.split(FILE_SEPERATOR);
                    int id = Integer.parseInt(userData[0]);
                    String name = userData[1];
                    User user = new User(id, name);
                    allUsers.add(user);
                }else break;
            }
            allUsers.sort(Comparator.comparing(User::getId));
        }catch (Exception e){
            System.out.println("Error in UserDAO");
            throw e;
        }
        return allUsers;
    }

    @Override
    public User getUser(int id) throws Exception {

        List<User> users = getAllUsers();
        try{
            for (User user: users) {
                if(user.getId() == id){

                    return user;
                }
            }
        }catch (Exception e){
            throw new Exception("Can find the user");
        }
        return null;
    }

    public User getUserBinary(int id) throws Exception {
        allUsers.sort(Comparator.comparingInt(User::getId));

        int first = 0;
        int last = allUsers.size() - 1;

        while (first <= last) {
            int middle = last + ((first - last) / 2);

            User middleUser = allUsers.get(middle);

            if (middleUser.getId() == id) {

                return middleUser;
            } else if (middleUser.getId() < id) {
                first = middle + 1;
            } else {
                last = middle - 1;
            }

        }

        return null;
    }

    @Override
    public void deleteUser(User deleteUser) throws Exception {
        List<User> users = getAllUsers();
        users.removeIf(user -> user.getId() == deleteUser.getId());
        writeAllUsersToFile(users);

    }

    @Override
    public User createUser(String name) throws Exception {
        List<User> users = getAllUsers();
        int newId = 1;
        if(!users.isEmpty()){
            newId = users.stream().max(Comparator.comparing(User::getId)).get().getId() + 1;
        }

       User user = new User(newId, name);
        users.add(user);
        writeAllUsersToFile(users);
        return user;
    }

    @Override
    public void updateUser(User updateUser) throws Exception {
        List<User> users = getAllUsers();
        users.removeIf(user -> user.getId() == updateUser.getId());
        users.add(updateUser);
        writeAllUsersToFile(users);

    }

    private void writeAllUsersToFile(List<User> users) throws IOException {
        FileOutputStream fos = new FileOutputStream(USER_FILE);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        for (User user : users){
            bw.write(Integer.toString(user.getId()));
            bw.write(FILE_SEPERATOR);
            bw.write(user.getName());
            bw.write(FILE_SEPERATOR);
            bw.newLine();
        }
        bw.close();
    }

    public static void main(String[] args) throws Exception {
        UserDAO userDAO = new UserDAO();
        for (User user: userDAO.getAllUsers()) {
            System.out.println(user);

        }
    }
}
