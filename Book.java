public class Book 
{ 
    private int id;
    private String title;
    private String Author;
    private String dateFinished;
    private String comments;
    private int rating;

    public Book(int id, String title, String author, String dateFinished, String comments, int rating) 
    {
        this.id = id;
        this.title = title;
        this.Author = author;
        this.dateFinished = dateFinished;
        this.comments = comments;
        this.rating = rating;
    }

    // For UI
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return Author; }
    public String getDateFinished() { return dateFinished; }
    public String getComments() { return comments; }
    public int getRating() { return rating; }

}

    