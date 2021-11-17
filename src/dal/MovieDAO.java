package dal;

import be.Movie;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MovieDAO implements IMovieDataAccess {

    private static final String MOVIES_FILE = "data/movie_titles.txt";
    private static final String FILE_SEPERATOR = ",";



    @Override
    public List<Movie> getAllMovies() throws IOException {
        List<Movie> allMovies = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new FileReader(MOVIES_FILE))){

                while (true) {
                    String aLineOfText = br.readLine();
                    if (aLineOfText == null) {
                        break;
                    }
                    String[] movieData = aLineOfText.split(FILE_SEPERATOR);
                    int id = Integer.parseInt(movieData[0]);
                    int year = Integer.parseInt(movieData[1]);
                    String title = movieData[2];
                    Movie movie = new Movie(id, year, title);
                    allMovies.add(movie);
                }
                allMovies.sort(Comparator.comparing(Movie::getId));
            }catch (Exception e){
                System.out.println("Error in MovieDAO");
                throw e;
            }

        return allMovies;
    }

    @Override
    public Movie getMovie(int id) throws Exception {
        List<Movie> movies = getAllMovies();
        try{
            for (Movie movie: movies) {
                if(movie.getId() == id){
                    return movie;
                }
            }
        }catch (Exception e){
            throw new Exception("Can find the movie");
        }

        return null;
    }

    @Override
    public Movie createMovie(String title, int year) throws Exception {
        List<Movie> movies = getAllMovies();
        int newId = 1;
        if(!movies.isEmpty()){
            newId = movies.stream().max(Comparator.comparing(Movie::getId)).get().getId() + 1;
        }

        Movie newMovie = new Movie( newId, year, title);
        movies.add(newMovie);
        writeAllMoviesToFile(movies);
        return newMovie;
    }

    @Override
    public void updateMovie(Movie updateMovie) throws Exception {
        List<Movie> movies = getAllMovies();
        movies.removeIf(movie -> movie.getId() == updateMovie.getId());
        movies.add(updateMovie);
        writeAllMoviesToFile(movies);
    }

    @Override
    public void deleteMovie(Movie movie) throws Exception {
        List<Movie> movies = getAllMovies();
        movies.removeIf(m -> m.getId() == movie.getId());
        writeAllMoviesToFile(movies);
    }


    private void  writeAllMoviesToFile(List<Movie> movies) throws IOException {
        FileOutputStream fos = new FileOutputStream(MOVIES_FILE);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        for (Movie movie : movies){
            bw.write(Integer.toString(movie.getId()));
            bw.write(FILE_SEPERATOR);
            bw.write(Integer.toString(movie.getYear()));
            bw.write(FILE_SEPERATOR);
            bw.write(movie.getTitle());
            bw.write(FILE_SEPERATOR);
            bw.newLine();
        }
        bw.close();
    }

    public static void main(String[] args) throws Exception {
        MovieDAO movieDAO = new MovieDAO();

        for (Movie m : movieDAO.getAllMovies()) {
            System.out.println(m);

        }
    }

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


