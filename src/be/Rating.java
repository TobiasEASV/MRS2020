package be;

public class Rating {

    private Movie movie;
    private User user;
    private int userRating;


    public Rating(Movie movie, User user, int userRating) {
        this.movie = movie;
        this.user= user;
        this.userRating = userRating;
    }

    public int getMovieId() {
        return movie.getId();
    }

    public int getUserId() {
        return user.getId();
    }

    public int getUserRating() {
        return userRating;
    }

    public void setMovieId(int movieId) {
        this.movie = movie;
    }

    public void setUserId(int userId) {
        this.user = user;
    }

    public void setUserRating(int userRating) {
        this.userRating = userRating;
    }

    @Override
    public String toString() {
        return movie.getTitle() + ", " + user.getName() + ", " +  userRating;
    }
}
