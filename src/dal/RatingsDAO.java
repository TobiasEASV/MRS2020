package dal;

import be.Movie;
import be.Rating;
import be.User;
import dal.db.RatingsOnThreads;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RatingsDAO implements IRatingDataAccess {

    private static final String RATING_FILE1 = "data/Rating1.txt";
    private static final String RATING_FILE2 = "data/Rating2.txt";
    private static final String RATING_FILE3 = "data/Rating3.txt";
    private static final String RATING_FILE4 = "data/Rating4.txt";
    private static final String RATING_FILE5= "data/Rating5.txt";
    private static final String RATING_FILE= "data/ratings.txt";
    private static final String FILE_SEPERATOR = ",";
    private MovieDAO movieDAO = new MovieDAO();
    private UserDAO userDAO = new UserDAO();
    public static List<Rating> allRatings = new ArrayList<>();



    @Override
    public List<Rating> getAllRatings() throws Exception {


        RatingsOnThreads ratingsOnThreads1 = new RatingsOnThreads(RATING_FILE1);
        RatingsOnThreads ratingsOnThreads2 = new RatingsOnThreads(RATING_FILE2);
        RatingsOnThreads ratingsOnThreads3 = new RatingsOnThreads(RATING_FILE3);
        RatingsOnThreads ratingsOnThreads4 = new RatingsOnThreads(RATING_FILE4);
        RatingsOnThreads ratingsOnThreads5 = new RatingsOnThreads(RATING_FILE5);

        ratingsOnThreads1.start();
        ratingsOnThreads2.start();
        ratingsOnThreads3.start();
        ratingsOnThreads4.start();
        ratingsOnThreads5.start();
        //allRatings.add(new Rating(new Movie(1,2000,"testtest"), new User(1,"tobias"), 6));

        return allRatings;








        /**
        List<Rating> allRatings = new ArrayList<>();

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
                allRatings.add(rating);
            }
        } catch (Exception e) {
            System.out.println("Error in RatingDAO");
            throw e;
        }
        return allRatings;
         **/
    }

    @Override
    public Rating createRating(Movie movie, User user, int userRating) throws Exception {
        List<Rating> ratings = getAllRatings();
        Rating rating = new Rating(movie, user, userRating);
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
