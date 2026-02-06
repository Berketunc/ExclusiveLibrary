public class Book 
{ 
    private String title;
    private String Author;
    private int dateFinished;
    private String comments;
    private int rating;

    public Book(String title, String Author, int dateFinished, String comments, int rating) 
    {
        this.title = title;
        this.Author = Author;
        this.dateFinished = dateFinished;
        this.comments = comments;
        this.rating = rating;
    }

    // For UI
    public String getTitle() { return title; }
    public String getAuthor() { return Author; }
    public int getDateFinished() { return dateFinished; }
    public String getComments() { return comments; }
    public int getRating() { return rating; }

}

    