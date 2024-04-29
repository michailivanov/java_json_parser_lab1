package michail.TestClasses;

import java.util.Arrays;

public class Book {
    private String title;
    private String author;
    private String[] genres;
    private int publishedYear;
    private double rating;
    private boolean available;

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genres=" + Arrays.toString(genres) +
                ", publishedYear=" + publishedYear +
                ", rating=" + rating +
                ", available=" + available +
                '}';
    }
}
