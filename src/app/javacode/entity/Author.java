package app.javacode.entity;

public class Author {
    private int id;
    private String name;
    private String country;

    public Author() {
    }

    public Author(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Author [id=" + id + ", name=" + name + ", country=" + country + "]";
    }
}
