import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager 
{
    private static final String URL = "jdbc:sqlite:library.db";

    public DatabaseManager() {
        try (Connection conn = DriverManager.getConnection(URL)) 
        {
            String sql = "CREATE TABLE IF NOT EXISTS books (" +
                         "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                         "title TEXT NOT NULL," +
                         "author TEXT," +
                         "dateFinished TEXT," +
                         "comments TEXT," +
                         "rating INTEGER)";
            conn.createStatement().execute(sql);
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void saveBook(Book book) 
    {
        String sql = "INSERT INTO books(title, author, dateFinished, comments, rating) VALUES(?,?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getDateFinished());
            pstmt.setString(4, book.getComments());
            pstmt.setInt(5, book.getRating());
            pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public List<Book> getAllBooks() 
    {
        List<Book> books = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL);
             ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM books")) 
             {
            while (rs.next()) 
                {
                books.add(new Book(rs.getInt("id"), rs.getString("title"), 
                        rs.getString("author"), rs.getString("dateFinished"), 
                        rs.getString("comments"), rs.getInt("rating")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return books;
    }
}