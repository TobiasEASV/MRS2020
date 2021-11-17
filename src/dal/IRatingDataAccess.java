package dal;

import be.Movie;
import be.Rating;
import be.User;

import java.util.List;

public interface IRatingDataAccess {

    public List<Rating> getAllRatings() throws Exception;

    public Rating createRating(Movie movie, User user, int rating) throws Exception;

    public void deleteRating(Rating rating) throws Exception;

    public void updateRating(Rating rating) throws Exception;


}
