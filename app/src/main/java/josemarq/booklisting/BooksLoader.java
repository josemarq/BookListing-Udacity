
/**
 * Created by josemarquez on 20/6/17.
 */
package josemarq.booklisting;

import android.content.Context;
import android.content.AsyncTaskLoader;
import java.util.List;

/**
 * Loads a list of books by using an AsyncTask to perform the
 * network request to the given URL.
 */
public class BooksLoader extends AsyncTaskLoader<List<Books>> {

    /**
     * Query URL
     */
    private String mUrl;

    /**
     * Constructs a new {@link BooksLoader}.
     *
     * @param context of the activity
     * @param url     to load data from
     */
    public BooksLoader(Context context, String url) {

        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<Books> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of books.
        List<Books> books = QueryUtils.fetchBooksData(mUrl);
        return books;
    }
}