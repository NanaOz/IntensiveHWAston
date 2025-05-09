package app.javacode.entity;

public class Book {
    private int id;
    private String title;
    private int authorId;
    private int year;
    private boolean available;

    public Book() {
    }

    public Book(int id, String title, int authorId, int year, boolean available) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.year = year;
        this.available = available;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authorId=" + authorId +
                ", year=" + year +
                ", available=" + available +
                '}';
    }
}
