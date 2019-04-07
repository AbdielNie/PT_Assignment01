import java.util.Scanner;

/*
 * Class: Menu
 * Description: The class present a menu to the user for interacting with the program.
 * Author: LiangyuNie - s3716113
 */
public class Menu
{

    private Scanner in;

    public Menu()
    {
        in = new Scanner(System.in);
    }

    public void display() {
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

    public String getOption()
    {
        String option = in.nextLine();

        return option.toUpperCase();
    }

    public String enter(String prompt)
    {
        System.out.print(prompt);
        return in.nextLine();
    }

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
            catch (NumberFormatException ex)
            {
                System.out.println(
                        "Error - please enter a valid integer value.");
            }
        }
    }

    public double enterDouble(String prompt)
    {
        while (true)
        {
            String value = enter(prompt);

            try
            {
                return Double.parseDouble(value);
            }
            catch (NumberFormatException ex)
            {
                System.out.println(
                        "Error - please enter a valid double value.");
            }
        }
    }

    public void close()
    {
        in.close();
    }
}
