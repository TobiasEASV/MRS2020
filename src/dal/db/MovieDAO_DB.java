package dal.db;

import be.Movie;
import dal.IMovieDataAccess;
import dal.MovieDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO_DB implements IMovieDataAccess {

    private MyDatabaseConnector databaseConnector;
    public static  List<Movie> allMovies = new ArrayList<>();

    public MovieDAO_DB() {
        databaseConnector = new MyDatabaseConnector();
    }

    public List<Movie> getAllMovies() throws Exception {
        if(allMovies.isEmpty()){
            try (Connection con = databaseConnector.getConnection()) {
                System.out.println("Connection to database");

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM Movie");

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String title = rs.getString("Title");
                    int year = rs.getInt("Year");
                    Movie movie = new Movie(id, year,title);
                    allMovies.add(movie);
                }

            } catch (Exception e) {
                throw new Exception("Connection fjle");
            }
        }

       return allMovies;
    }

    @Override
    public Movie getMovie(int id) throws Exception {

        try (Connection con = databaseConnector.getConnection()) {
            System.out.println("Connection to database");

            String sql  = "SELECT * FROM Movie WHERE Id = (?);";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,id);


            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int Id = rs.getInt("Id");
                String title = rs.getString("Title");
                int year = rs.getInt("Year");
                return new Movie(Id, year,title);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public Movie createMovie(String title, int year) throws Exception {
        try (Connection con = databaseConnector.getConnection()) {
            System.out.println("Connection to database");

            String sql = "INSERT INTO Movie (Title, YEAR ) VALUES (?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, title);
            pstmt.setInt(2, year);


            ResultSet rs = pstmt.executeQuery();

            int id_DB = rs.getInt("Id");
            String title_DB = rs.getString("Title");
            int year_DB = rs.getInt("Year");
            Movie movie = new Movie(id_DB, year_DB, title_DB);
            allMovies.add(movie);
            return movie;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public void updateMovie(Movie movie) throws Exception {
        int id = movie.getId();

        try (Connection con = databaseConnector.getConnection()) {
            System.out.println("Connection to database");

            String sql  = "UPDATE Movie SET Title = (?), Year = (?) WHERE Id = (?);";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, movie.getTitle());
            pstmt.setInt(2, movie.getYear());
            pstmt.setInt(3,id);
            pstmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteMovie(Movie movie) throws Exception {
        int id = movie.getId();

        try (Connection con = databaseConnector.getConnection()) {
            System.out.println("Connection to database");

            String sql  = "DELETE FROM Movie WHERE Id = (?);";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,id);
            pstmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<Movie> searchMovies(String query) throws Exception {

        //TODO Do this
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) throws Exception {
        MovieDAO_DB movieDAO_db = new MovieDAO_DB();
        Movie te = movieDAO_db.getMovie(17584);
        movieDAO_db.updateMovie(new Movie(17584, 2001, "testestetse"));
        System.out.println(movieDAO_db.getMovie(1));
    }

}
