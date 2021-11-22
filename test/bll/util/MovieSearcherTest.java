package bll.util;

import be.Movie;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;



class MovieSearcherTest {


    @Test
    void search() {

        System.out.println("search");
        List<Movie> searchBase = new ArrayList<>();
        Movie expectedResult = new Movie(6,1994, "Shawshank Redemption");
        searchBase.add(expectedResult);
        searchBase.add(new Movie(4,2004,"I Robot"));
        searchBase.add(new Movie(5,1999, "Pirates of Silicon Valley"));

        String query = "red";
        MovieSearcher instance = new MovieSearcher();
        List<Movie> result = instance.search(searchBase, query);

        Assert.assertNotNull( "Assert that the search method returns an object",result);
        Assert.assertTrue( "Search for \"red\" should return one result",result.size() == 1);
        Assert.assertTrue("Search for \"red\" should return result with ID: 1",result.get(0).getId() == 1);



    }
}