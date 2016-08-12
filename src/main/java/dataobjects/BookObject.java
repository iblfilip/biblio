package dataobjects;

public class BookObject {
    int id;
    String title;
    String author_name;
    Integer year_of_publish;
    String place_of_publish;
    Integer isbn;
    String genre;
    String rating;
    String publisher;
    String notes;
    Integer quantity;

    public void setId(int id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setAuthor(String author_name) {
        this.author_name = author_name;
    }
    public void setYearOfPublish(int year_of_publish) {
        this.year_of_publish = year_of_publish;
    }
    public void setPlaceOfPublish(String place_of_publish) {
        this.place_of_publish = place_of_publish;
    }
    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;

    }

    public String getTitle() {
        return title;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public Integer getYear_of_publish() {
        return year_of_publish;
    }

    public String getPlace_of_publish() {
        return place_of_publish;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getNotes() {
        return notes;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getRating() {
        return rating;
    }

    public String getGenre() {
        return genre;
    }

    public Integer getIsbn() {
        return isbn;
    }

    public void setItem(String columnName, Object item){
        if(columnName.equals(BookColumnEnum.id.name()))
            setId((Integer)item);
        else if(columnName.equals(BookColumnEnum.title.name()))
            setTitle(item.toString());
        else if(columnName.equals(BookColumnEnum.author_name.name()))
            setAuthor(item.toString());
        else if(columnName.equals(BookColumnEnum.year_of_publish.name()))
            setYearOfPublish((Integer)item);
        else if(columnName.equals(BookColumnEnum.place_of_publish.name()))
            setPlaceOfPublish(item.toString());
        else if(columnName.equals(BookColumnEnum.isbn.name()))
            setIsbn((Integer)item);
        else if(columnName.equals(BookColumnEnum.genre.name()))
            setGenre(item.toString());
        else if(columnName.equals(BookColumnEnum.rating.name()))
            setRating(item.toString());
        else if(columnName.equals(BookColumnEnum.publisher.name()))
            setPublisher(item.toString());
        else if(columnName.equals(BookColumnEnum.notes.name()))
            setNotes(item.toString());
        else if(columnName.equals(BookColumnEnum.quantity.name()))
            setQuantity((Integer)item);
    }
}
