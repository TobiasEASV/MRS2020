package gui.model;

import be.Movie;
import bll.MovieManager;

public class RatingModel {

    MovieManager movieManager;

    public RatingModel(){

        movieManager = new MovieManager();

    }

    public double getRating(Movie movie) throws Exception {
        return Math.round(movieManager.getRatingOnMovie(movie));
    }

    public int getTotalUserCountRating(Movie movie) throws Exception {
        return Math.round(movieManager.getTotalUserHasRating(movie));
    }

}
