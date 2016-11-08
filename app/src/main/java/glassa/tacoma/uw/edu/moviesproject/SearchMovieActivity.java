package glassa.tacoma.uw.edu.moviesproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.widget.ListView;

import glassa.tacoma.uw.edu.moviesproject.search.Transport;

import static glassa.tacoma.uw.edu.moviesproject.R.id.list_view;
import static glassa.tacoma.uw.edu.moviesproject.R.id.search_view;

/**
 * Search movie bar class for project
 */
public class SearchMovieActivity extends AppCompatActivity {
    private SearchView mySearchView;
    private ListView myListView;
    private final String MY_CLASS = "Name";
    private final String URL="http://cssgate.insttech.washington.edu/~_450team2/movieSearcher.php";
    
    /**
     * onCreat create the search bar frame and list of result
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);


        myListView = (ListView) findViewById(list_view);
        mySearchView = (SearchView) findViewById(search_view);

        mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Transport sr = new Transport(SearchMovieActivity.this,URL, query, myListView, MY_CLASS);
                sr.execute();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                Transport sr = new Transport(SearchMovieActivity.this,URL, query, myListView, MY_CLASS);
                sr.execute();
                return false;
            }
        });
    }
}
