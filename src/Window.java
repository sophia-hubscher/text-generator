import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Creates window that contains user controls
 *
 * @author Sophia Hübscher
 * @version 2.3
 */
public class Window extends JFrame implements ActionListener
{
    //components & objects
    JPanel            panel;
    JPanel            showTextPanel;

    JButton           setButton, helpButton;
    JComboBox<String> bookSelector;
    JSlider           wordCountSlider;
    JLabel            wordCountLabel;
    JTextArea         showText;
    JScrollPane outputScroll;
    Timer             timer;
    Display           display;
    Book              book;

    //variables
    final int WIDTH; //size of window
    final int HEIGHT;

    String[] bookNames      = {"Heidi", "Dracula", "Über Psychoanalyse",
            "A Christmas Carol", "The Hound of the Baskervilles",
            "Peter Pan", "Inspirational Quotes", "Bee Movie"};
    int      bookChosen     = 3;      //third book is chosen when the program
    int      lastBookChosen = 3;      //
    int      wordCount      = 300;
    int      maxWords       = 160016; //num words in dracula
    int      delay          = 220;    //delay between timer fires
    boolean  bookChanged    = false;

    /**
     * Constructor for objects of class Window
     *
     * @param width display width
     * @param height display height
     */
    public Window(int width, int height)
    {
        super("TextGenerator");

        //initialise instance variables
        WIDTH = width;
        HEIGHT = height;

        //make display object
        display = new Display(width, height);

        //this allows me to place things where I want them
        this.setLayout(new BorderLayout());

        //makes book pull-down
        bookSelector = new JComboBox<String>(bookNames);    //create the pull-down menu object
        bookSelector.setSelectedIndex(3);                   //sets which one is selected by default
        bookSelector.addActionListener(this);               //this means we can react when a different one is selected
        bookSelector.setActionCommand("bookSelector");      //the command we react to
        bookSelector.setToolTipText("Titles available");

        //select book button
        setButton = new JButton("Set");
        setButton.addActionListener(this);
        setButton.setActionCommand("setButton");
        setButton.setToolTipText("Set book & length");
        setButton.setVisible(false); //invisible until book changed

        //help book button
        helpButton = new JButton("?");
        helpButton.addActionListener(this);
        helpButton.setActionCommand("helpButton");
        helpButton.setToolTipText("Help");

        //makes slider
        wordCountSlider = new JSlider(JSlider.HORIZONTAL, 50, 350, 300);
        wordCountSlider.setForeground(Color.WHITE);
        wordCountSlider.addChangeListener(new ChangeListener()
          {
              public void stateChanged(ChangeEvent e)
              {
                  //as long as words less than words in book, updates num of words displayed
                  if (wordCountSlider.getValue() < maxWords)
                  {
                      wordCount = wordCountSlider.getValue();
                  }
              }
          }
        );

        //turn on labels at major tick marks for slider
        wordCountSlider.setMajorTickSpacing(50);
        wordCountSlider.setPaintTicks(true);
        wordCountSlider.setPaintLabels(true);
        wordCountSlider.setToolTipText("Select # of words displayed");

        //makes slider label
        wordCountLabel = new JLabel("Words:");
        wordCountLabel.setForeground(Color.WHITE);
        wordCountLabel.setToolTipText("Select the # of words displayed");

        //makes panel for buttons and textfields
        panel = new JPanel();
        panel.setBackground(Color.BLACK);

        //adds components to panel
        panel.add(wordCountLabel);
        panel.add(wordCountSlider);
        panel.add(bookSelector);
        panel.add(setButton);
        panel.add(helpButton);

        //area where text is displayed
        showText = new JTextArea("", 35 , 50); //30, 60 is size of text area in chars
        showText.setForeground(Color.BLACK);
        Font fontBody = new Font("SansSerif", Font.PLAIN, 16);
        showText.setFont(fontBody);
        showText.setEditable(false);
        showText.setLineWrap(true);
        showText.setWrapStyleWord(true);
        showText.setToolTipText("This is generated from the selected text!");

        //adds the report to a scroll pane, so if it gets big, it will scroll
        outputScroll = new JScrollPane(showText);

        //adds the scroll pane to the overall panel
        showTextPanel = new JPanel();
        showTextPanel.add(outputScroll);

        //creates panel and display
        this.add(panel, BorderLayout.NORTH);
        this.add(showTextPanel, BorderLayout.WEST);
        this.add(display, BorderLayout.CENTER);

        //final setup (by mr. beckwith)
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    /**
     * Creates a Timer object and starts timer
     */
    public void startTimer()
    {
        timer = new Timer(delay, this);
        timer.setActionCommand("timerFired");
        timer.start();
    }

    /**
     * Performs actions
     *
     * @param e contains info about action & is automatically sent
     */
    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand().equals("timerFired"))
        {
            display.repaint();

            //display new text
            showText.setText(display.displayText);
        }  else if (e.getActionCommand().equals("bookSelector"))  //bookSelector menu altered
        {
            //updates info
            bookChosen = bookSelector.getSelectedIndex();

            //only runs if a new book was chosen
            if (lastBookChosen != bookChosen)
            {
                bookChanged = true;
                setButton.setVisible(true);
            } else
            {
                setButton.setVisible(false);
            }

            //sets number of words in book
            if (bookChosen == 0)      maxWords = 84512;
            else if (bookChosen == 1) maxWords = 160016;
            else if (bookChosen == 2) maxWords = 15364;
            else if (bookChosen == 3) maxWords = 28402;
            else if (bookChosen == 4) maxWords = 59019;
            else if (bookChosen == 5) maxWords = 47230;
            else if (bookChosen == 6) maxWords = 1893;
            else if (bookChosen == 7) maxWords = 9154;
            else if (bookChosen == 8) maxWords = 4651;
        } else if (e.getActionCommand().equals("setButton"))  //select book button clicked
        {
            //send info to display
            display.setBookChosen(bookChosen);
            display.setBookChanged(bookChanged);
            display.setMaxWords(maxWords);
            display.setWordCount(wordCount);

            //make set button invisible again
            lastBookChosen = bookChosen;
            bookChanged = false; //reset var
            setButton.setVisible(false);
        } else if (e.getActionCommand().equals("helpButton"))  //help button clicked
        {
            JOptionPane.showMessageDialog(null,
                    "To generate text, choose a title from the pull-down menu and press\n" +
                            "the 'Set' button. This program takes text from a book and\n" +
                            "creates text that sounds like it could come from the book. To set\n" +
                            "the length of the text, move the slider and choose a new book\n\n" +
                            "VERSION: 2.3\n",
                    "Help",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
