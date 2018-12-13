/**
 * TextGenerator
 *
 * @author Sophia Hübscher
 * @version 2.3
 */
public class TextGenerator
{
    public static void main(String[] args)
    {
        //the JFrame size:
        int windowWidth = 1210;
        int windowHeight = 780;

        //make Frame, handing over Rabbit object to it
        Window window = new Window(windowWidth, windowHeight);

        window.startTimer();
    }
}