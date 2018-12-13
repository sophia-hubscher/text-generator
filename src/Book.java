import java.io.*;
import java.util.*;

/**
 * Book object and methods are used to read
 * a txt file, store data, generate new text,
 * and hold information about the current text.
 *
 * @author Sophia Hübscher
 * @version 2.3
 */
public class Book
{
    HashMap<String, List<String>> words = new HashMap<String, List<String>>();
    List<String>    startWords          = new ArrayList<String>();
    String[]        splitBookText;
    int             wordAmount;
    int             wordsRead;
    String          bookText            = "";
    String          newText             = "";
    String          bookName;
    String          lineToPrint;

    /**
     * Constructor for objects of class Book
     *
     * @param bookName name of first book displayed
     * @param wordCount number of words displayed
     * @param wordsRead number of words that the program will read
     */
    public Book(String bookName, int wordCount, int wordsRead)
    {
        this.bookName = bookName;
        this.wordsRead = wordsRead;
        wordAmount = wordCount;
    }

    /**
     * Reads txt file and turns it into a String
     */
    public void txtToString()
    {
        //creates readable file
        String fileName = "res/textFiles/" + bookName + "Text.TXT";
        File file = new File(fileName);

        //reads and prints entire book
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            //adds text to newText
            while ((lineToPrint = reader.readLine()) != null)
            {
                bookText += lineToPrint; //adds line to newText
                bookText += " ";         //adds " " so that words are split correctly
            }
        } catch (IOException e)
        {
            //if doesn't work, prints error msg
            System.out.println("ERROR: Could not find or read file");
        }
    }

    /**
     * Goes through the txt file and stores all of its data in a HashMap
     */
    public void makeHashMap()
    {
        //splits text into individual words
        splitBookText = bookText.split("\\s+");

        //gets rid of many non letter characters
        for (int i = 0; i < splitBookText.length; i++)
        {
            splitBookText[i] = splitBookText[i].replaceAll("[^a-zA-Z-!.?äöüÄÖÜáéóíèß'@#]", "");
        }

        //creates list of starting words
        for (int i = 0; i < splitBookText.length - 1; i++)
        {
            //if word ends in '.', '!', or '?', adds word after to list
            if (splitBookText[i].endsWith(".") || splitBookText[i].endsWith("!") || splitBookText[i].endsWith("?"))
            {
                startWords.add(splitBookText[i + 1] + " " + splitBookText[i + 2]);
            }
        }

        //creates HashMap
        for (int i = 0; i < wordsRead; i++)
        {
            //adds 1 word keys to the HashMap
            if (words.containsKey(splitBookText[i]))
            {
                if (i + 1 < splitBookText.length)
                {
                    words.get(splitBookText[i]).add(splitBookText[i + 1]);
                }
            } else
            {
                if (i + 1 < splitBookText.length)
                {
                    words.put(splitBookText[i], new java.util.ArrayList(Arrays.asList(splitBookText[i + 1])));
                }
            }

            //adds 2 word keys to the HashMap
            if (words.containsKey(splitBookText[i] + " " + splitBookText[i + 1]))
            {
                if (i + 1 < splitBookText.length)
                {
                    words.get(splitBookText[i] + " " + splitBookText[i + 1]).add(splitBookText[i + 2]);
                }
            } else
            {
                if (i + 1 < splitBookText.length)
                {
                    words.put(splitBookText[i] + " " + splitBookText[i + 1],
                            new java.util.ArrayList(Arrays.asList(splitBookText[i + 2])));
                }
            }
        }
    }

    /**
     * Looks through the HashMap and creates text based on the data in
     * the HashMap
     */
    public void writeNewText()
    {
        int     maxNum      = 0;
        int     ranNum;
        String  lastWord    = "";
        String  nextWord    = "";
        String  next2Words  = "";

        //chooses first 2 wordWords
        ranNum = (int)(Math.random() * startWords.size());
        newText = startWords.get(ranNum);

        nextWord = newText.substring(newText.indexOf(" ") + 1);

        //addresses bug that would give a nullPointerException for some keys
        for (int i = 0; i < wordsRead; i++)
        {
            //if data is missing --> adds it
            if (!words.containsKey(nextWord))
            {
                if (i + 1 < splitBookText.length)
                {
                    words.put(nextWord, new java.util.ArrayList(Arrays.asList(splitBookText[i + 2])));
                }
            }
        }

        //creates hashMap
        for (int i = 0; i < wordAmount / 2; i++)
        {
            if ((i == 0 && words.containsKey(nextWord)) || words.containsKey(lastWord))
            {
                boolean sentenceDone = (nextWord.contains(".") ||
                        nextWord.contains("!") ||
                        nextWord.contains("?"));

                if (!(wordsRead < wordAmount && sentenceDone))
                {
                    lastWord = nextWord;

                    //nextWord
                    maxNum = words.get(lastWord).size() - 1;
                    ranNum = (int)(Math.random() * maxNum);
                    nextWord = words.get(lastWord).get(ranNum);

                    //addresses bug that would give a nullPointerException for some keys
                    if (!words.containsKey(lastWord + " " + nextWord))
                    {
                        if (i < splitBookText.length)
                        {
                            words.put(lastWord + " " + nextWord,
                                    new java.util.ArrayList(Arrays.asList(splitBookText[i])));
                        }
                    }

                    //next2Words
                    maxNum = words.get(lastWord + " " + nextWord).size() - 1;
                    ranNum = (int)(Math.random() * maxNum);
                    next2Words = words.get(lastWord + " " + nextWord).get(ranNum); //get next 2 word key text

                    //addresses repeating words error
                    //TODO fix this section
                    if (newText.contains(nextWord + " " + next2Words))
                    {
                        int tempNum = splitBookText.length - 1;

                        //addresses nullPointerException error
                        if (!words.containsKey(splitBookText[tempNum]))
                        {
                            if (i < splitBookText.length)
                            {
                                words.put(splitBookText[tempNum],
                                        new java.util.ArrayList(Arrays.asList(splitBookText[i])));
                            }
                        }

                        nextWord = words.get(splitBookText[tempNum]).get((int)(Math.random() *
                                words.get(splitBookText[tempNum]).size() - 1));
                    }

                    //adds nextWord and next2Words to newText
                    newText += " " + nextWord + " " + next2Words;

                    //sets up nextWord variable for the next check
                    nextWord = next2Words;
                }
            }
        }
    }

    /**
     * Getter for newText
     *
     * @return String text that is displayed in Display class
     */
    public String getNewText()
    {
        return newText;
    }

    /**
     * Setter for wordCount
     *
     * @param wordCount int with value of words displayed
     */
    public void setWordCount(int wordCount)
    {
        wordAmount = wordCount;
    }
}
