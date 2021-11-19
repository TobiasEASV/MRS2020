package dal;

import be.Movie;
import be.Rating;
import be.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RatingsDAO implements IRatingDataAccess {


    private static final String RATING_FILE= "data/ratings.txt";
    private static final String FILE_SEPERATOR = ",";
    //private MovieDAO movieDAO = new MovieDAO();
    //private UserDAO userDAO = new UserDAO();
    public List<Rating> allRatings = new ArrayList<>();



    @Override
    public List<Rating> getAllRatings() throws Exception {

        try (BufferedReader br = new BufferedReader(new FileReader(RATING_FILE))) {

            if(allRatings.isEmpty()) {
                while (true) {
                    String aLineOfText = br.readLine();
                    if (aLineOfText == null) {
                        break;
                    }
                    String[] ratingData = aLineOfText.split(FILE_SEPERATOR);
                    int movieId = Integer.parseInt(ratingData[0]);
                    int userID = Integer.parseInt(ratingData[1]);
                    int userRating = Integer.parseInt(ratingData[2]);

                    Rating rating = new Rating(movieId, userID, userRating);
                    allRatings.add(rating);
                }
            }
        } catch (Exception e) {
            System.out.println("Error in RatingDAO");
            throw e;
        }
        return allRatings;

    }

    @Override
    public Rating createRating(int movieID, int userID, int userRating) throws Exception {
        List<Rating> ratings = getAllRatings();
        Rating rating = new Rating(movieID, userID, userRating);
        ratings.add(rating);
        writeAllRatingsToFile(ratings);
        return rating;
    }

    @Override
    public void deleteRating(Rating deleteRating) throws Exception {
        List<Rating> ratings = getAllRatings();
        ratings.removeIf(rat -> rat.getUserId() == deleteRating.getUserId());
        writeAllRatingsToFile(ratings);
    }

    @Override
    public void updateRating(Rating updateRating) throws Exception {
        List<Rating> ratings = getAllRatings();
        ratings.removeIf(rat -> rat.getUserId() == updateRating.getUserId());
        ratings.add(updateRating);
        writeAllRatingsToFile(ratings);

    }

    private void writeAllRatingsToFile(List<Rating> ratings) throws IOException {
        FileOutputStream fos = new FileOutputStream(RATING_FILE);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        for (Rating rating : ratings) {
            bw.write(Integer.toString(rating.getMovieId()));
            bw.write(FILE_SEPERATOR);
            bw.write((Integer.toString(rating.getUserId())));
            bw.write(FILE_SEPERATOR);
            bw.write((Integer.toString(rating.getUserRating())));
            bw.write(FILE_SEPERATOR);
            bw.newLine();
        }
        bw.close();
    }

    public static void main(String[] args) throws Exception {
        RatingsDAO ratingsDAO = new RatingsDAO();
        MovieDAO m = new MovieDAO();
        UserDAO u = new UserDAO();
        u.getAllUsers();
        m.getAllMovies();
        for (Rating rating: ratingsDAO.getAllRatings()) {
            System.out.println(rating);

        }

    }
}
