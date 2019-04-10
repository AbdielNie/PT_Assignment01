/*
 * Class: MiRidesApplication
 * Description: The class is used to for manage all the cars and bookings.
 * Author: [LiangyuNie] - [s3716113]
 */
public class MiRidesApplication
{
    /**
     * the current cars in this system
     */
    private Car[] cars;
    /**
     * the bookings associated with those cars in this system
     */
    private Booking[] bookings;

    /**
     * the number of cars in this system
     */
    private int numCars;
    /**
     * the number of bookings in this system
     */
    private int numBookings;
    /**
     * the menu of this system
     */
    private Menu menu;

    /**
     * Constructs the application.
     */
    public MiRidesApplication()
    {
        cars = new Car[5];
        bookings = new Booking[10];

        numCars = 0;
        numBookings = 0;

        menu = new Menu();
    }

    /**
     * Starts the application.
     */
    public void start()
    {
        while (true)
        {
            menu.display();

            System.out.print("Please enter your option: ");
            String option = menu.getOption();

            if ("EX".equals(option))
            {
                System.out.println("Good bye.");
                break;
            }

            switch (option)
            {
                case "CC":
                    createCar();
                    break;

                case "BC":
                    bookCar();
                    break;

                case "CB":
                    completeBooking();
                    break;

                case "DA":
                    displayAllCars();
                    break;

                case "DB":
                    displayAllBookings();
                    break;

                case "SS":
                    searchSpecialCar();
                    break;

                case "SA":
                    searchAvailableCars();
                    break;

                case "SD":
                    seedData();
                    break;

                default:
                    System.out.println("Error - Invalid option.");
                    break;
            }
        }

        // close the menu
        menu.close();
    }

    /**
     * Creates a car.
     */
    private void createCar()
    {
        Car newCar = enterCar();

        if (newCar != null)
        {
            addCar(newCar);
            System.out.printf(
                "New Car added successfully for registration number: %s.\n",
                newCar.getRegNo());
        }
    }

    /**
     * Adds a new car to this system.
     *
     * @param newCar a given car
     */
    private void addCar(Car newCar)
    {
        ensureCarArrayCapacity(numCars + 1);

        cars[numCars] = newCar;
        numCars++;
    }

    /**
     * Enters a car by the user.
     *
     * @return a car entered by the user
     */
    private Car enterCar()
    {
        String regNo = menu.enter("Enter Registration No:       ");

        // The registration number should be exactly six characters
        // that consist of an initial 3 alphabetical characters,
        // followed by 3 numeric characters.
        if (regNo.length() != 6
                ||!Character.isAlphabetic(regNo.charAt(0))
                || !Character.isAlphabetic(regNo.charAt(1))
                || !Character.isAlphabetic(regNo.charAt(2))
                || !Character.isDigit(regNo.charAt(3))
                || !Character.isDigit(regNo.charAt(4))
                || !Character.isDigit(regNo.charAt(5)))
        {
            System.out.println("Error - Registration number is invalid.");
            return null;
        }

        if (containsCar(regNo))
        {
            System.out.println("Error - Already exists in the system.");
            return null;
        }

        String make  = menu.enter("Enter Make:                  ");
        String model = menu.enter("Enter Model:                 ");
        String driverName = menu.enter("Enter Driver's Name:         ");

        // The make, model and driver name must handle
        // being able to have multiple words
        if (make.equals(model)
                || make.equals(driverName) || model.equals(driverName))
        {
            System.out.println(
                    "Error - The make, model and driver name must " +
                    "handle being able to have multiple words.");
            return null;
        }

        // Passenger capacity must be greater than 0 and less than 10
        int passengerCapacity = menu.enterInteger(
                "Enter Passenger Capacity:    ", 1, 9);

        return new Car(regNo, make, model, driverName, passengerCapacity);
    }

    /**
     * Returns true if current system contains the given car, otherwise false.
     *
     * @param regNo the registration number of the given car
     * @return true if current system contains the given car, otherwise false
     */
    private boolean containsCar(String regNo)
    {
        for (int i = 0; i < numCars; i++)
        {
            Car car = cars[i];

            if (car.getRegNo().equals(regNo))
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Books a car
     */
    private void bookCar()
    {
        Booking newBooking = enterBooking();

        if (newBooking != null)
        {
            addBooking(newBooking);
            System.out.printf(
                "\nThank you for your booking. %s will pick you up on %s.\n",
                newBooking.getCar().getDriverName(),
                newBooking.getPickUpDateTime().getFormattedDate());

            System.out.printf(
                    "Your booking reference is: %s.\n", newBooking.getId());
        }
    }

    /**
     * Enters a booking by the user.
     *
     * @return a booking entered by the user.
     */
    private Booking enterBooking()
    {
        String dateStr = menu.enter("Enter Date Required:         ");

        DateTime required;
        try
        {
            required = parseDate(dateStr);
        }
        catch (Exception ex)
        {
            System.out.println("Error - Please enter a valid date.");
            return null;
        }

        DateTime now = new DateTime();

        int days = DateTime.diffDays(required, now);
        // The date of the booking must not be in the past
        // or more than one week in the future.
        if (days < 0 || days > 7)
        {
            System.out.println("Error - No cars are available on this date.");
            return null;
        }

        Car[] availableCars = searchAvailableCars(required);
        if (availableCars.length == 0)
        {
            System.out.println("Error - No cars are available on this date.");
            return null;
        }

        System.out.println("The following cars are available.");
        for (int i = 0; i < availableCars.length; i++)
        {
            System.out.printf("%d.  %s\n", i + 1, availableCars[i].getRegNo());
        }

        int bookingIndex = menu.enterInteger(
                "Please select the number next to the car you wish to book: ",
                1, availableCars.length);
        Car bookingCar = availableCars[bookingIndex - 1];

        String firstName = menu.enter("Enter First Name:            ");
        if (firstName.length() < 3) {
            System.out.println("Error - invalid first name.");
            return null;
        }

        String lastName  = menu.enter("Enter Last Name:             ");
        if (lastName.length() < 3) {
            System.out.println("Error - invalid last name.");
            return null;
        }

        int numPassengers = menu.enterInteger(
                "Enter Number of Passengers:   ", 1, 9);
        if (numPassengers > bookingCar.getPassengerCapacity()) {
            System.out.println(
                    "Error - The passenger capacity of this car is not enough.");
            return null;
        }

        // try to book the car
        boolean bookingResult =
                bookingCar.book(firstName, lastName, required, numPassengers);
        if (bookingResult)  // book successfully
        {
            return bookingCar.getLastBooking();
        }

        return null;
    }

    /**
     * Adds a booking to this system.
     *
     * @param newBooking a given booking
     */
    private void addBooking(Booking newBooking)
    {
        Car bookingCar = newBooking.getCar();
        DateTime pickUpDateTime = newBooking.getPickUpDateTime();
        String firstName = newBooking.getFirstName();
        String lastName = newBooking.getLastName();

        // generates the id of the booking object
        String bookingId = bookingCar.getRegNo() + "_"
                + firstName.substring(0, 3).toUpperCase()
                + lastName.substring(0, 3).toUpperCase() + "_"
                + pickUpDateTime.getEightDigitDate();

        newBooking.setId(bookingId);
        newBooking.setBookingFee(1.5);

        ensureBookingArrayCapacity(numBookings + 1);
        bookings[numBookings] = newBooking;
        numBookings++;
    }

    /**
     * Searchers in the cars in this system to find out all the cars met
     * the required date.
     *
     * @param required the required date
     * @return the cars met the required date in this system
     */
    private Car[] searchAvailableCars(DateTime required)
    {
        int numAvailableCars = 0;
        for (int i = 0; i < numCars; i++)
        {
            if (cars[i].isAvailable(required))
            {
                numAvailableCars++;
            }
        }

        Car[] availableCars = new Car[numAvailableCars];
        int index = 0;
        for (int i = 0; i < numCars; i++)
        {
            if (cars[i].isAvailable(required))
            {
                availableCars[index++] = cars[i];
            }
        }

        return availableCars;
    }

    /**
     * Parses a String value to a DateTime value.
     *
     * @param dateStr a given String value
     * @return a DateTime value
     */
    private DateTime parseDate(String dateStr)
    {
        String[] dateInfo = dateStr.split("/");
        int day = Integer.parseInt(dateInfo[0]);
        int month = Integer.parseInt(dateInfo[1]);
        int year = Integer.parseInt(dateInfo[2]);

        return new DateTime(day, month, year);
    }

    /**
     * Grow the capacity of the cars array if necessary to ensure it's
     * capacity is larger than the given capacity.
     *
     * @param newCapacity the given capacity
     */
    private void ensureCarArrayCapacity(int newCapacity)
    {
        if (newCapacity >= cars.length)
        {
            Car[] newArray = new Car[cars.length * 2];
            System.arraycopy(cars, 0, newArray, 0, cars.length);

            cars = newArray;
        }
    }

    /**
     * Grow the capacity of the bookings array if necessary to ensure it's
     * capacity is larger than the given capacity.
     *
     * @param newCapacity the given capacity
     */
    private void ensureBookingArrayCapacity(int newCapacity)
    {
        if (newCapacity >= bookings.length)
        {
            Booking[] newArray = new Booking[cars.length * 2];
            System.arraycopy(bookings, 0, newArray, 0, cars.length);

            bookings = newArray;
        }
    }

    /**
     * Completes an entered booking.
     */
    private void completeBooking()
    {
        String bookingInfo   = menu.enter(
                "Enter Registration or Booking Date: ");
        String firstName     = menu.enter("Enter first name:  ");
        String lastName      = menu.enter("Enter last name:   ");

        Booking booking = null;
        for (int i = 0; i < numBookings; i++)
        {
            Booking b = bookings[i];

            // if the registration number or date of the booking matches
            if (b.getCar().getRegNo().equals(bookingInfo)
                || b.getPickUpDateTime().getFormattedDate().equals(bookingInfo))
            {
                if (b.getFirstName().equals(firstName)
                        && b.getLastName().equals(lastName))
                {
                    booking = b;
                    break;
                }
            }
        }

        if (booking == null)
        {
            System.out.println("Error - The booking could not be located.");
            return;
        }

        double kilometersTravelled =
                menu.enterDouble("Enter kilometers:  ");
        completeBooking(booking, kilometersTravelled);

        System.out.println(
                "Thank you for riding with MiRide. " +
                "We hope you enjoyed your trip.");

        System.out.printf("$%.2f has been deducted from your account.\n",
                booking.getBookingFee() + booking.getTripFee());
    }

    /**
     * Completes a specified booking with it's travelled distance in kilometers.
     *
     * @param booking a specified booking
     * @param kilometersTravelled travelled distance in kilometers
     */
    private void completeBooking(Booking booking, double kilometersTravelled)
    {
        booking.setKilometersTravelled(kilometersTravelled);
        booking.setTripFee(kilometersTravelled * booking.getBookingFee() * 0.3);

        Car bookingCar = booking.getCar();
        bookingCar.completeBooking(booking.getId());
    }

    /**
     * Searches a specified car by a given registration number.
     */
    private void searchSpecialCar()
    {
        String regNo = menu.enter("Enter Registration No:    ");
        boolean found = false;

        for (int i = 0; i < numCars; i++)
        {
            Car car = cars[i];

            if (car.getRegNo().equals(regNo))
            {
                found = true;

                System.out.println();
                System.out.println(car.getDetails());
                System.out.println();
                break;
            }
        }

        // not found the specified car
        if (!found)
        {
            System.out.println("Error - The car could not be located.");
        }
    }

    /**
     * Displays all cars' details.
     */
    private void displayAllCars()
    {
        if (numCars == 0)
        {
            System.out.println(
                    "There are no cars currently in the system yet.");
            return;
        }

        System.out.println("All cars' information:\n");
        for (int i = 0; i < numCars; i++)
        {
            System.out.println(cars[i].getDetails());
            System.out.println();
        }
    }

    /**
     * Searches all cars in this system to find out the cars which are
     * available on the required date.
     */
    private void searchAvailableCars()
    {
        String dateStr = menu.enter("Enter Date Required:         ");

        DateTime required;
        try
        {
            required = parseDate(dateStr);
        }
        catch (Exception ex)
        {
            System.out.println("Error - Please enter a valid date.");
            return;
        }

        DateTime now = new DateTime();
        int days = DateTime.diffDays(required, now);

        // The date of the booking must not be in the past
        // or more than one week in the future.
        if (days < 0 || days > 7)
        {
            System.out.println("Error - No cars are available on this date.");
            return;
        }

        Car[] availableCars = searchAvailableCars(required);

        System.out.println("The following cars are available.");
        for (Car car : availableCars)
        {
            System.out.println(car.getDetails());
            System.out.println();
        }
    }

    /**
     * Pre-populates the system with a range of cars and booking objects.
     */
    private void seedData()
    {
        // the collection of cars already have data
        if (numCars != 0)
        {
            System.out.println(
                    "Error - The collection of cars already have data.");
            return;
        }

        // initializes 6 hard coded cars
        Car[] seedCars =
            {
                new Car("ABC123", "Make1",
                        "Model1", "Abe Smith", 3),
                new Car("DEF123", "Make2",
                        "Model2", "Bob Brown", 4),
                new Car("GHI123", "Make3",
                        "Model3", "Canny Taylor", 4),
                new Car("JKL123", "Make4",
                        "Model4", "David Martin", 6),
                new Car("MNO123", "Make5",
                        "Model5", "Edward White", 8),
                new Car("PQR123", "Make6",
                        "Model6", "Frank Thomas", 9)
            };

        for (Car seedCar : seedCars)
        {
            addCar(seedCar);
        }

        // Two cars that HAVE BEEN been booked,
        // but the bookings have not been completed
        cars[0].book("Alice", "Moore", new DateTime(), 2);
        Booking car0Booking = cars[0].getLastBooking();
        addBooking(car0Booking);

        cars[1].book("Emma", "Davis", new DateTime(), 4);
        Booking car1Booking = cars[1].getLastBooking();
        addBooking(car1Booking);

        // Two cars that HAVE BEEN booked,
        // and the bookings have been completed
        cars[2].book("Kate", "Young", new DateTime(), 2);
        Booking car2Booking = cars[2].getLastBooking();
        addBooking(car2Booking);
        completeBooking(car2Booking, 43);


        cars[3].book("Denny", "Larry", new DateTime(), 4);
        Booking car3Booking = cars[3].getLastBooking();
        addBooking(car3Booking);
        completeBooking(car3Booking, 100);

        System.out.println("Seed Data successfully.");
    }

    /**
     * Displays all bookings' details.
     */
    private void displayAllBookings()
    {
        if (numBookings == 0)
        {
            System.out.println(
                    "There are no bookings currently in the system yet.");
            return;
        }

        System.out.println("All bookings' information:\n");
        for (int i = 0; i < numBookings; i++)
        {
            System.out.println(bookings[i].getDetails());
            System.out.println();
        }
    }

    /**
     * The main method.
     *
     * @param args the arguments of the main method.
     */
    public static void main(String[] args)
    {
        MiRidesApplication app = new MiRidesApplication();
        app.start();
    }
}
