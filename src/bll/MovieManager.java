package bll;

import be.Movie;
import be.Rating;
import be.User;
import bll.util.MovieSearcher;
import dal.*;

import java.util.ArrayList;
import java.util.List;

public class MovieManager {

    private MovieSearcher movieSearcher = new MovieSearcher();

    private IMovieDataAccess movieDAO;
    private IRatingDataAccess ratingDAO;
    private IUserDataAccess userDAO;

    public MovieManager() {
        movieDAO = new MovieDAO();
        ratingDAO = new RatingsDAO();
        userDAO = new UserDAO();
    }

    // Movie

    public List<Movie> getAllMovies() throws Exception {
        return movieDAO.getAllMovies();
    }

    public void addMovie(String title, int year) throws Exception {
        movieDAO.createMovie(title, year);
    }

    public void removeMovie(Movie movie) throws Exception {
        movieDAO.deleteMovie(movie);
    }

    public void updateMovie(Movie movie) throws Exception {
        movieDAO.updateMovie(movie);
    }

    public List<Movie> searchMovies(String query) throws Exception {
        List<Movie> allMovies = getAllMovies();
        List<Movie> searchResult = movieSearcher.search(allMovies, query);
        return searchResult;
    }

    // User

    public void getAllUser() throws Exception {
        userDAO.getAllUsers();
    }

    public void createNewUser(String name) throws Exception {
        userDAO.createUser(name);
    }

    public User getUserById(int id) throws Exception {
        try {
            for (User user: userDAO.getAllUsers()) {
                if(user.getId() == id){
                    return user;
                }
            }
        }catch (Exception e){
            throw new Exception("Can not find the user");
        }
        return null;
    }

    // Rating

    public void rateMovie(Movie movie, User user, int rating) throws Exception {
        if(movieDAO.getMovie(movie.getId()) != null || userDAO.getUser(user.getId()) != null){
            ratingDAO.createRating(movie, user, rating);
        }else System.out.println("can not find the user or movie");

    }

    public int getRatingOnMovie(Movie movie)throws Exception{
        List<Rating> movielist = new ArrayList<>();
        int rat = 1;
        try {
            for (Rating movieRating: ratingDAO.getAllRatings()) {
                if(movieRating.getMovieId() == movie.getId()){
                    rat += movieRating.getUserRating();
                    //movielist.add(movieRating);
                }
            }
        }catch (Exception e){
            throw new Exception("Can not find the movie");
        }
        return rat;
    }

}
