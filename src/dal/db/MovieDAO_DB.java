package dal.db;

import be.Movie;
import dal.IMovieDataAccess;

import java.util.List;

public class MovieDAO_DB implements IMovieDataAccess {

    private MyDatabaseConnector databaseConnector;

    public MovieDAO_DB() {
        databaseConnector = new MyDatabaseConnector();
    }

    public List<Movie> getAllMovies() throws Exception {

        //TODO Do this
        throw new UnsupportedOperationException();
    }

    @Override
    public Movie getMovie(int id) throws Exception {
        return null;
    }


    public Movie createMovie(String title, int year) throws Exception {
        //TODO Do this
        throw new UnsupportedOperationException();
    }

    public void updateMovie(Movie movie) throws Exception {
        //TODO Do this
        throw new UnsupportedOperationException();
    }

    public void deleteMovie(Movie movie) throws Exception {
        //TODO Do this
        throw new UnsupportedOperationException();
    }

    public List<Movie> searchMovies(String query) throws Exception {

        //TODO Do this
        throw new UnsupportedOperationException();
    }

}
