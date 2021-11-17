package gui;

import be.Movie;
import gui.model.MovieModel;
import gui.model.RatingModel;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class movieViewController implements Initializable {

    @FXML
    private TextField txtTitle;

    @FXML
    private TextField txtYear;

    @FXML
    private TextField txtRatings;

    @FXML
    private Label labTotalUser;

    @FXML
    private TextField txtMovieSearch;

    @FXML
    private ListView<Movie> lstMovies;

    private MovieModel movieModel = new MovieModel();
    private RatingModel ratingModel = new RatingModel();


    public movieViewController() throws Exception {

        try {
            movieModel = new MovieModel();
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        lstMovies.setItems(movieModel.getMovies());

        movieModel.getNewTitle().bindBidirectional(txtTitle.textProperty());
        movieModel.getNewYear().bindBidirectional(txtYear.textProperty());
        movieModel.getNewRating().bindBidirectional(txtRatings.textProperty());

        lstMovies.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                showMovieDetails(newValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        txtMovieSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                movieModel.searchMovie(newValue);
            } catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            }
        });

    }

    private void showMovieDetails(Movie movie) throws Exception {
        if (movie != null) {
            // Fill the labels with info from the movie object.
            txtTitle.setText(movie.getTitle());
            txtYear.setText(String.valueOf(movie.getYear()));
            txtRatings.setText(String.valueOf(ratingModel.getRating(movie)));
        } else {
            // Movie is null, remove all the text.
            txtTitle.setText("");
            txtYear.setText("");
            txtRatings.setText("null");
        }

    }

    public void btnHandleUpdateMovie(ActionEvent actionEvent) throws Exception {
        movieModel.updateMovie(lstMovies.getSelectionModel().getSelectedItem());
    }

    public void btnHandleAddMovie(ActionEvent actionEvent) throws Exception {
        movieModel.addMovie();
    }

    public void btnHandleRemove(ActionEvent actionEvent) throws Exception {
        movieModel.deleteMovie(lstMovies.getSelectionModel().selectedItemProperty().get());
    }

    private void displayError(Throwable t)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }
}
