import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;

public class LibraryApp extends JFrame {
    private DatabaseManager db = new DatabaseManager();
    private JTable table;
    private DefaultTableModel model;

    public LibraryApp() {
        setTitle("Java Book Library");
        setSize(600, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        model = new DefaultTableModel(new String[]{"ID", "Title", "Author", "Rating"}, 0);
        table = new JTable(model);
        table.removeColumn(table.getColumnModel().getColumn(0)); // Hide ID column from view
        
        // Double-click to view details
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    // Get the ID from the first column to find the right book
                    int bookId = (int) table.getValueAt(row, 0);
                    Book selected = db.getAllBooks().stream()
                                      .filter(b -> b.getId() == bookId)
                                      .findFirst().orElse(null);
                    showDetailPopup(selected);
                }
            }
        });

        // Bottom Panel for Buttons
        JPanel buttonPanel = new JPanel();
        JButton addBtn = new JButton("Add New Book");
        JButton deleteBtn = new JButton("Delete Selected");
        deleteBtn.setBackground(new Color(255, 102, 102)); // Subtle Red

        addBtn.addActionListener(e -> showDetailPopup(null));
        
        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int bookId = (int) table.getValueAt(row, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "Delete this book?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    db.deleteBook(bookId);
                    refreshTable();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a book to delete.");
            }
        });

        buttonPanel.add(addBtn);
        buttonPanel.add(deleteBtn);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        refreshTable();
    }

    private void refreshTable() {
        model.setRowCount(0);
        for (Book b : db.getAllBooks()) {
            // We include ID in the table (can be hidden later) so we know which one to delete/edit
            model.addRow(new Object[]{b.getId(), b.getTitle(), b.getAuthor(), "★".repeat(b.getRating())});
        }
    }

    private void showDetailPopup(Book book) {
        JDialog dialog = new JDialog(this, "Book Details", true);
        dialog.setLayout(new GridLayout(0, 1, 5, 5));
        
        JTextField t = new JTextField(book != null ? book.getTitle() : "");
        JTextField a = new JTextField(book != null ? book.getAuthor() : "");
        JTextArea c = new JTextArea(book != null ? book.getComments() : "", 3, 20);
        StarRatingPanel s = new StarRatingPanel();
        if (book != null) s.setRating(book.getRating());

        dialog.add(new JLabel(" Title:")); dialog.add(t);
        dialog.add(new JLabel(" Author:")); dialog.add(a);
        dialog.add(new JLabel(" Rating:")); dialog.add(s);
        dialog.add(new JLabel(" Comments:")); dialog.add(new JScrollPane(c));

        JButton save = new JButton("Save");
        save.addActionListener(e -> {
            // FIXED CONSTRUCTOR CALL: matches Book(String, String, String, String, int)
            Book newBook = new Book(t.getText(), a.getText(), LocalDate.now().toString(), c.getText(), s.getRating());
            db.saveBook(newBook);
            refreshTable();
            dialog.dispose();
        });

        dialog.add(save);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibraryApp().setVisible(true));
    }
}