package dal.db;

import be.Movie;
import be.Rating;
import be.User;
import dal.MovieDAO;
import dal.RatingsDAO;
import dal.UserDAO;

import java.io.BufferedReader;
import java.io.FileReader;

public class RatingsOnThreads extends Thread {
    private String RATING_FILE; //= "data/ratings.txt";
    private static final String FILE_SEPERATOR = ",";
    private MovieDAO movieDAO = new MovieDAO();
    private UserDAO userDAO = new UserDAO();

    public RatingsOnThreads(String file){
        this.RATING_FILE = file;
    }
    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new FileReader(RATING_FILE))) {

            while (true) {
                String aLineOfText = br.readLine();
                if (aLineOfText == null) {
                    break;
                }
                String[] ratingData = aLineOfText.split(FILE_SEPERATOR);
                int movieId = Integer.parseInt(ratingData[0]);
                int userID = Integer.parseInt(ratingData[1]);
                int userRating = Integer.parseInt(ratingData[2]);

                Movie movie = movieDAO.getMovieBinary(movieId);
                User user = userDAO.getUserBinary(userID);

                Rating rating = new Rating(movie, user, userRating);
                RatingsDAO.allRatings.add(rating);
            }
        } catch (Exception e) {
            System.out.println("Error in RatingDAO");
            try {
                throw e;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }
}
