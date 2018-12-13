import java.awt.*;
import javax.swing.*;

/**
 * Displays text on screen
 *
 * @author Sophia Hübscher
 * @version 2.3
 */
public class Display extends JPanel
{
    Font    fontBody, fontHeader, fontLBody, fontTitle;
    Book    book = new Book("aChristmasCarol", 300, 20000);
    String  displayText;
    int     wordCount      = 300;
    int     bookChosen     = 3; //third book is chosen when the program
    int     lastBookChosen = 3; //is run
    int     maxWords       = 28402;
    int     width;
    int     height;
    boolean bookChanged;

    /**
     * Constructor for objects of class Display
     *
     * @param width display width
     * @param height display height
     */
    public Display(int width, int height)
    {
        this.height = height;
        this.width = width;

        fontLBody  = new Font("SansSerif", Font.ITALIC,  20);
        fontHeader = new Font("SansSerif", Font.BOLD,    28);
        fontTitle  = new Font("SansSerif", Font.BOLD,    50);

        //setting display size
        setPreferredSize(new Dimension(width, height));

        //reads txt file and creates String
        book.txtToString();
        book.makeHashMap();
        book.writeNewText();
    }

    /**
     * Displays text and other basic graphics
     * @param g the graphics object, automatically sent when repaint() is called
     */
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g); //clears the old drawings

        //improves graphics
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        //draws background
        if (book.bookName.equals("heidi"))                          g.setColor(new Color(175, 195, 224));
        else if (book.bookName.equals("dracula"))                   g.setColor(new Color(152, 68,  55));
        else if (book.bookName.equals("überPsychoanalyse"))         g.setColor(new Color(201, 164, 115));
        else if (book.bookName.equals("aChristmasCarol"))           g.setColor(new Color(119, 134, 59));
        else if (book.bookName.equals("theHoundOfTheBaskervilles")) g.setColor(new Color(133, 28,  25));
        else if (book.bookName.equals("peterPan"))                  g.setColor(new Color(42,  54,  34));
        else if (book.bookName.equals("inspirationalQuotes"))       g.setColor(new Color(245, 168, 77));
        else if (book.bookName.equals("beeMovie"))                  g.setColor(new Color(232, 161, 63));

        g.fillRect(0, 0, width, height);

        //draws lines
        g.setColor(Color.WHITE);     //right horizontal bar
        g.fillRect(0, 110, width, 4);
        g.setColor(Color.BLACK);     //left vertical bar
        g.fillRect(0, 0, 4, height);

        //draws text
        g.setColor(Color.WHITE);
        g.setFont(fontTitle);
        g.drawString("TextGenerator", 35, 70);
        g.setFont(fontHeader);
        g.drawString(book.bookName, 10, 145);

        g.setFont(fontLBody);
        g.drawString("words in text   | " + maxWords, 10, 170);

        //text to display
        displayText = book.getNewText();
        displayText.replace("\\s+", " "); //remove extra whitespace

        //selects book
        if (bookChanged)
        {
            if (bookChosen == 0)      book = new Book("heidi",                     wordCount, 10000);
            else if (bookChosen == 1) book = new Book("dracula",                   wordCount, 10000);
            else if (bookChosen == 2) book = new Book("überPsychoanalyse",         wordCount, 10000);
            else if (bookChosen == 3) book = new Book("aChristmasCarol",           wordCount, 10000);
            else if (bookChosen == 4) book = new Book("theHoundOfTheBaskervilles", wordCount, 10000);
            else if (bookChosen == 5) book = new Book("peterPan",                  wordCount, 10000);
            else if (bookChosen == 6) book = new Book("inspirationalQuotes",       wordCount, 1000);
            else if (bookChosen == 7) book = new Book("beeMovie",                  wordCount, 9000);

            //reads txt file and creates String
            if (lastBookChosen != bookChosen)
            {
                book.txtToString();
                book.makeHashMap();
                book.writeNewText();
            } else
            {
                book.writeNewText();
            }

            //resetting boolean
            bookChanged = false;
        }
    }

    /**
     * Setter for bookChanged
     *
     * @param bookChanged boolean that says whether or not the book has
     * been changed
     */
    public void setBookChanged(boolean bookChanged)
    {
        this.bookChanged = bookChanged;
    }

    /**
     * Setter for bookChosen
     *
     * @param newBook number representing chosen book
     */
    public void setBookChosen(int newBook)
    {
        lastBookChosen = bookChosen;
        bookChosen = newBook;
    }

    /**
     * Setter for wordCount
     *
     * @param wordCount int with value of words displayed
     */
    public void setWordCount(int wordCount)
    {
        this.wordCount = wordCount;
    }

    /**
     * Setter for maxWords
     *
     * @param maxWords number of words in book
     */
    public void setMaxWords(int maxWords)
    {
        this.maxWords = maxWords;
    }
}
