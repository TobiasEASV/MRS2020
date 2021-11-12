package dal;

import be.Movie;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MovieDAO implements IMovieDataAccess {

    private static final String MOVIES_FILE = "data/movie_titles.txt";
    private List<Movie> allMoviesInList = new ArrayList<>();



    public List<Movie> getAllMovies() throws IOException {

        File allMovieInFil = new File(MOVIES_FILE);
        Scanner readLine = new Scanner(allMovieInFil);

        while (readLine.hasNextLine()){
            String movieData = readLine.nextLine();
            String[] movieDataSplit = movieData.split(",");
            int id = Integer.parseInt(movieDataSplit[0]);
            int year = Integer.parseInt(movieDataSplit[1]);
            String title = movieDataSplit[2];
            Movie movie = new Movie(id, year, title );
            allMoviesInList.add(movie);
        }

        return allMoviesInList;
    }

    @Override
    public Movie createMovie(String title, int year) throws Exception {
        List<Integer> MoviesId = new ArrayList<>();
        for (Movie movie: getAllMovies()) {
            MoviesId.add(movie.getId());
        }

        int id = 0;
        while (MoviesId.contains(id)){
            id++;
        }
        return new Movie( id, year, title);
    }

    @Override
    public void updateMovie(Movie movie) throws Exception {
        if(allMoviesInList.contains(movie)){
            int index = allMoviesInList.indexOf(movie);
            allMoviesInList.;
        }
    }

    @Override
    public void deleteMovie(Movie movie) throws Exception {
        allMoviesInList.remove(movie);
    }






    /*
    public List<Movie> getAllMovies() {
        List<Movie> allMovieList = new ArrayList<>();

        File moviesFile = new File(MOVIES_FILE);


        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(moviesFile))) {
            boolean hasLines = true;
            while (hasLines) {
                String line = bufferedReader.readLine();
                hasLines = (line != null);
                if (hasLines && !line.isBlank())
                {
                    String[] separatedLine = line.split(",");

                    int id = Integer.parseInt(separatedLine[0]);
                    int year = Integer.parseInt(separatedLine[1]);
                    String title = separatedLine[2];
                    if(separatedLine.length > 3)
                    {
                        for(int i = 3; i < separatedLine.length; i++)
                        {
                            title += "," + separatedLine[i];
                        }
                    }
                    Movie movie = new Movie(id, title, year);
                    allMovieList.add(movie);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allMovieList;
    }
    */


}