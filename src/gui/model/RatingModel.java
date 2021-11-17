package gui.model;

import be.Movie;
import bll.MovieManager;

public class RatingModel {

    MovieManager movieManager;

    public RatingModel(){

        movieManager = new MovieManager();

    }

    public int getRating(Movie movie) throws Exception {
        return movieManager.getRatingOnMovie(movie);
    }

}
