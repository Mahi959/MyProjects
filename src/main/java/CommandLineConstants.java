import java.text.SimpleDateFormat;
import java.util.Date;
/*
 * this class is used for the numbering the Command line arguments
 */
public class CommandLineConstants {
    public static final String FORMATTED_DATE = new SimpleDateFormat("MM//dd/yyyy h:mm a").format(new Date())
            .replaceAll("[ :/]", "_");

    public static final int NUMBER_ZERO = 0;
    public static final int NUMBER_ONE = 1;
    public static final int NUMBER_TWO = 2;
    public static final int NUMBER_THREE = 3;
    public static final int NUMBER_FOUR = 4;
    public static final int NUMBER_FIVE = 5;
    public static final int NUMBER_SIX = 6;
    public static final int NUMBER_SEVEN = 7;
    private CommandLineConstants(){

    }
}
