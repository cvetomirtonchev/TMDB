package tonchev.tmdb.modul;

/**
 * Created by Цветомир on 4.4.2017 г..
 */

public class Movie {
    private String title;
    private String discription;
    private String year;
    private String genre;
    private String writer;
    private String imageUrl;
    private String time;

    public Movie(String title, String discription, String year, String genre, String writer, String imageUrl,String time) {
        this.title = title;
        this.discription = discription;
        this.year = year;
        this.genre = genre;
        this.writer = writer;
        this.imageUrl = imageUrl;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public String getDiscription() {
        return discription;
    }

    public String getYear() {
        return year;
    }

    public String getGenre() {
        return genre;
    }

    public String getWriter() {
        return writer;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTime() {
        return time;
    }
}
