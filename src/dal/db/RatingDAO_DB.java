package dal.db;

import be.Rating;
import dal.IRatingDataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RatingDAO_DB implements IRatingDataAccess {

    private MyDatabaseConnector databaseConnector;
    private List<Rating> allRatings = new ArrayList<>();

    public RatingDAO_DB(){
        databaseConnector = new MyDatabaseConnector();
    }

    @Override
    public List<Rating> getAllRatings() throws Exception {
        if(allRatings.isEmpty()){
            try (Connection con = databaseConnector.getConnection()) {
                System.out.println("Connection to database");

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM Rating");

                while (rs.next()) {
                    int movieId_DB = rs.getInt("MovieId");
                    int userId_DB = rs.getInt("UserId");
                    int Score_DB = rs.getInt("Score");
                    Rating rating = new Rating (movieId_DB, userId_DB, Score_DB);
                    allRatings.add(rating);
                }

            } catch (Exception e) {
                throw new Exception("Connection fjle");
            }
        }

        return allRatings;
    }

    @Override
    public Rating createRating(int movieID, int userID, int score) throws Exception {

        try (Connection con = databaseConnector.getConnection()) {
            System.out.println("Connection to database");

            String sql = "INSERT INTO Rating (MovieId, UserId, Score) VALUES (?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, movieID);
            pstmt.setInt(2, userID);
            pstmt.setInt(3, score);

            ResultSet rs = pstmt.executeQuery();

            int movieId_DB = rs.getInt("MovieId");
            int userId_DB = rs.getInt("UserId");
            int score_DB = rs.getInt("Score");

            Rating rating = new Rating(movieId_DB, userId_DB, score_DB);

            allRatings.add(rating);
            return rating;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteRating(Rating rating) throws Exception {

        try (Connection con = databaseConnector.getConnection()) {
            System.out.println("Connection to database");

            String sql  = "DELETE FROM Rating WHERE UserId = (?) AND MovieId = (?);";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, rating.getUserId());
            pstmt.setInt(2, rating.getMovieId());
            pstmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateRating(Rating rating) throws Exception {

        try (Connection con = databaseConnector.getConnection()) {
            System.out.println("Connection to database");

            String sql  = "UPDATE Rating SET Score = (?) WHERE UserId = (?) AND MovieId = (?);";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, rating.getUserRating());
            pstmt.setInt(2, rating.getUserId());
            pstmt.setInt(3, rating.getMovieId());
            pstmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
