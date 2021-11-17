package gui.model;

import be.Movie;
import bll.MovieManager;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Comparator;
import java.util.List;

public class MovieModel {

    private MovieManager movieManager;

    private ObservableList<Movie> moviesToBeViewed;
    private ObjectProperty<MovieModel> selectedMovie = new SimpleObjectProperty<>();
    private StringProperty newTitle = new SimpleStringProperty();
    private StringProperty newYear = new SimpleStringProperty();
    private StringProperty newRating = new SimpleStringProperty();


    public StringProperty getNewYear() {
        return newYear;
    }

    public StringProperty getNewRating() {
        return newRating;
    }

    public StringProperty getNewTitle(){
        return newTitle;
    }

    public String getTitle(){
        return newTitle.get();
    }



    public MovieModel() throws Exception {
        movieManager = new MovieManager();
        moviesToBeViewed = FXCollections.observableArrayList();
        moviesToBeViewed.addAll(movieManager.getAllMovies());
    }

    public ObservableList<Movie> getMovies() {
        return moviesToBeViewed;
    }
    public void addMovie() throws Exception {
        movieManager.addMovie(newTitle.get(), Integer.parseInt(newYear.get()));
        moviesToBeViewed.add(movieManager.getAllMovies().get(movieManager.getAllMovies().size() - 1));
    }

    public void deleteMovie(Movie movie) throws Exception {
        movieManager.removeMovie(movie);
        moviesToBeViewed.remove(movie);
    }

    public void updateMovie(Movie movie) throws Exception {
        movie.setTitle(newTitle.get());
        movie.setYear(Integer.parseInt(newYear.get()));

        movieManager.updateMovie(movie);
        if (moviesToBeViewed.removeIf(m -> m.getId() == movie.getId())){
            moviesToBeViewed.add(movie);
            moviesToBeViewed.sort(Comparator.comparing(Movie::getId));
        }
    }

    public void searchMovie(String query) throws Exception {
        List<Movie> searchResults = movieManager.searchMovies(query);
        moviesToBeViewed.clear();
        moviesToBeViewed.addAll(searchResults);
    }
}
