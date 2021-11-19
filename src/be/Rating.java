package be;

public class Rating {

    private int movieID;
    private int userID;
    private int userRating;


    public Rating(int movieID, int userID, int userRating) {
        this.movieID = movieID;
        this.userID = userID;
        this.userRating = userRating;
    }

    public int getMovieId() {
        return movieID;
    }

    public int getUserId() {
        return userID;
    }

    public int getUserRating() {
        return userRating;
    }

    public void setMovieId(int movieId) {
        this.movieID = movieID;
    }

    public void setUserId(int userId) {
        this.userID = userID;
    }

    public void setUserRating(int userRating) {
        this.userRating = userRating;
    }

    @Override
    public String toString() {
        return movieID + ", " + userID + ", " +  userRating;
    }
}
