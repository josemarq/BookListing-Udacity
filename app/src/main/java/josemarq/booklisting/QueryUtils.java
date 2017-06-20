package josemarq.booklisting;

import android.text.TextUtils;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving books form the api.
 */
public final class QueryUtils {

    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Query the API and return a list of {@link Books} objects.
     */
    public static List<Books> fetchBooksData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Books}
        List<Books> books = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Books}
        return books;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            Log.i("QueryUtils", "url: " + url);
            return jsonResponse;

        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            Log.i("QueryUtils", "url: " + url);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the books JSON results.", e);

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link Books} objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<Books> extractFeatureFromJson(String booksJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(booksJSON)) {
            return null;
        }

        String autor = "";
        String publisher;
        // Create an empty ArrayList that we can start adding books to
        List<Books> books = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the response
            JSONObject baseJsonResponse = new JSONObject(booksJSON);
            Log.i("QueryUtils", "Response= " + booksJSON);

            // Extract the JSONArray associated with the key items.
            JSONArray booksArray = baseJsonResponse.getJSONArray("items");

            // For each book in the array, create an {@link Books} object
            for (int i = 0; i < booksArray.length(); i++) {

                // Get a single book at position i within the list
                JSONObject currentBook = booksArray.getJSONObject(i);

                /* For a given book, extract the JSONObject associated with the
                key called "volumenInfo", for extact the Authors.*/

                JSONObject volumeInfo = currentBook.getJSONObject("volumeInfo");

                //Check before go if the array exist (not all books have authors) for avoid excepmtion
                if (!currentBook.getJSONObject("volumeInfo").has("authors")) {
                    Log.i("QueryUtils", i + " Item No have Authors");
                    autor = "-";
                } else {
                    JSONArray authorsArray = volumeInfo.getJSONArray("authors");
                    autor = "";
                    //Some books have more than one authors.
                    for (int h = 0; h < authorsArray.length(); h++) {
                        if (h == 0) {
                            autor = authorsArray.getString(h);
                        } else {
                            autor = autor + ", " + authorsArray.getString(h);
                        }
                    }
                }

                //Check before go if the array exist (not all books have publisher) for avoid excepmtion
                if (!currentBook.getJSONObject("volumeInfo").has("publisher")) {
                    Log.i("QueryUtils", i + "Item No have Publisher");
                    publisher = "-";
                } else {
                    publisher = volumeInfo.getString("publisher");
                }

                // Extract the value for the key called "title"
                String titulo = volumeInfo.getString("title");

                // Extract the value for the link
                String url = volumeInfo.getString("previewLink");

                // Create a new {@link Books} object with the magnitude, location, time,
                // and url from the JSON response.
                Books book = new Books(titulo, autor, publisher, url);

                // Add the new {@link Books} to the list of books.
                books.add(book);
            }

        } catch (JSONException e) {
            /* If an error is thrown when executing any of the above statements in the "try" block,
             catch the exception here, so the app doesn't crash. Print a log message
             with the message from the exception.*/
            Log.e("QueryUtils", "Problem parsing the books JSON results", e);
        }
        return books;
    }

}
