import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StarRatingPanel extends JPanel {
    private int rating = 0;
    private final JLabel[] stars = new JLabel[5];

    public StarRatingPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        for (int i = 0; i < 5; i++) {
            int index = i + 1;
            stars[i] = new JLabel("☆");
            stars[i].setFont(new Font("Serif", Font.PLAIN, 24));
            stars[i].setForeground(new Color(255, 200, 0));
            stars[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
            stars[i].addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    rating = index;
                    updateStars();
                }
            });
            add(stars[i]);
        }
    }

    private void updateStars() {
        for (int i = 0; i < 5; i++) stars[i].setText(i < rating ? "★" : "☆");
    }

    public int getRating() { return rating; }
    public void setRating(int r) { this.rating = r; updateStars(); }
}