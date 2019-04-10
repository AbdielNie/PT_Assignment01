import java.util.Scanner;

/*
 * Class: Menu
 * Description: The class present a menu to the user for interacting with the program.
 * Author: LiangyuNie - s3716113
 */
public class Menu
{

    /**
     * the object to get input from console
     */
    private Scanner console;

    /**
     * Constructs a menu instance.
     */
    public Menu()
    {
        console = new Scanner(System.in);
    }

    /**
     * Displays the options of the menu.
     */
    public void display()
    {
        System.out.println();
        System.out.println("*** MiRides System Menu ***");
        System.out.println("Create Car                   CC");
        System.out.println("Book Car                     BC");
        System.out.println("Complete Booking             CB");
        System.out.println("Display ALL Cars             DA");
        System.out.println("Search Specific Car          SS");
        System.out.println("Search available cars        SA");
        System.out.println("Seed Data                    SD");
        System.out.println("Exit Program                 EX");
    }

    /**
     * Gets an option entered by the user.
     *
     * @return the option entered by the user
     */
    public String getOption()
    {
        String option = console.nextLine();

        return option.toUpperCase();
    }

    /**
     * Prompts the user to enter a string value.
     *
     * @param prompt the prompt message
     * @return a string value entered by the user.
     */
    public String enter(String prompt)
    {
        System.out.print(prompt);
        return console.nextLine();
    }

    /**
     * Prompts the user to enter a valid integer value.
     *
     * @param prompt the prompt message
     * @param min the minimum value of the restriction
     * @param max the maximum value of the restriction
     * @return an integer value which is in [min, max]
     */
    public int enterInteger(String prompt, int min, int max)
    {
        while (true)
        {
            String value = enter(prompt);

            try
            {
                int i = Integer.parseInt(value);
                if (i < min || i > max)
                {
                    System.out.printf(
                        "Error - the entered integer should be in [%d, %d].\n",
                        min, max);
                    continue;
                }

                return i;
            }
            catch (NumberFormatException ex)  // invalid value
            {
                System.out.println(
                        "Error - please enter a valid integer value.");
            }
        }
    }

    /**
     * Prompts the user to enter a valid double value.
     *
     * @param prompt the prompt message
     * @return an integer value entered by the user
     */
    public double enterDouble(String prompt)
    {
        while (true)
        {
            String value = enter(prompt);

            try
            {
                return Double.parseDouble(value);
            }
            catch (NumberFormatException ex)   // invalid value
            {
                System.out.println(
                        "Error - please enter a valid double value.");
            }
        }
    }

    /**
     * Releases the resource.
     */
    public void close()
    {
        console.close();
    }
}
