package tonchev.tmdb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import tonchev.tmdb.modul.Movie;

public class MainActivity extends AppCompatActivity {
    private TextView title;
    private TextView discription;
    private TextView year;
    private TextView genre;
    private TextView writer;
    private ImageView image;
    private EditText searched;
    private TextView time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = (TextView) findViewById(R.id.movie_title);
        discription = (TextView) findViewById(R.id.movie_discription);
        year = (TextView) findViewById(R.id.movie_year);
        genre = (TextView) findViewById(R.id.movie_genre);
        writer = (TextView) findViewById(R.id.movie_writer);
        image = (ImageView) findViewById(R.id.movie_image);
        time = (TextView) findViewById(R.id.movie_time);

        findViewById(R.id.search_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searched = (EditText) findViewById(R.id.movie_search);
                String movieSearch = searched.getText().toString();

                new SearchMovie().execute(movieSearch);

            }
        });
    }
    class SearchMovie extends AsyncTask<String,Void,Movie> {


        @Override
        protected Movie doInBackground(String... params) {
            Movie movie = null;
            String search = params[0];
            try {
                URL url = new URL("http://www.omdbapi.com/?t=" + search.replace(" ", "+").trim());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                Scanner sc = new Scanner(connection.getInputStream());
                StringBuilder builder = new StringBuilder();
                while (sc.hasNext()) {
                    builder.append(sc.nextLine());
                }
                JSONObject jsonObject = new JSONObject(builder.toString());
                String title = jsonObject.getString("Title");
                String discription = jsonObject.getString("Plot");
                String genre = jsonObject.getString("Genre");
                String writer = jsonObject.getString("Writer");
                String year = jsonObject.getString("Year");
                String image = jsonObject.getString("Poster");
                String time = jsonObject.getString("Runtime");
                movie = new Movie(title, discription, year, genre, writer, image, time);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return movie;
        }

        @Override
        protected void onPostExecute(Movie movie) {
            if (movie != null) {
                title.setText("Name: " + movie.getTitle());
                discription.setText("Discription: " + movie.getDiscription());
                genre.setText("Genre: " + movie.getGenre());
                year.setText("Year: " + movie.getYear());
                writer.setText("Writer: " + movie.getWriter());
                time.setText("Duration: " + movie.getTime());
                discription.setVisibility(View.VISIBLE);
                new DownloadImageTask().execute(movie.getImageUrl());
            } else {
                title.setText(null);
                discription.setText(null);
                genre.setText(null);
                year.setText(null);
                writer.setText(null);
                time.setText(null);
                image.setImageBitmap(null);
                discription.setVisibility(View.INVISIBLE);

            }
        }

        class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {


            protected Bitmap doInBackground(String... urls) {
                String urldisplay = urls[0];
                Bitmap mIcon11 = null;
                try {
                    InputStream in = new java.net.URL(urldisplay).openStream();
                    mIcon11 = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
                return mIcon11;
            }

            protected void onPostExecute(Bitmap result) {
                image.setImageBitmap(result);
            }


        }

    }}
