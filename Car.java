import java.util.*;

/*
 * Class: Car
 * Description: The class represents a single car.
 * Author: LiangyuNie - s3716113
 */
public class Car
{
	
    private static final int MAX_BOOKINGS_NUM = 5;
    // Set Variable
    private String regNo;
    private String make;
    private String model;
    private String driverName; 
    private int passengerCapacity;
    private boolean available;
    private Booking[] currentBookings;
    private Booking[] pastBookings;

    private int numCurrentBookings;
    private int numPastBookings;
    //Constructor
    public Car(String regNo, String make, String model,
               String driverName, int passengerCapacity)
    {
    	//setter
        this.regNo = regNo;
        this.make = make;
        this.model = model;
        this.driverName = driverName;
        this.passengerCapacity = passengerCapacity;

        currentBookings = new Booking[MAX_BOOKINGS_NUM];
        pastBookings = new Booking[10];

        numCurrentBookings = 0;
        numPastBookings = 0;

        available = true;
    }

    public boolean book(String firstName, String lastName,
                        DateTime required, int numPassengers)
    {
        if (!available)
        {
            return false;
        }

        Booking newBooking = new Booking(firstName, lastName,
                required, numPassengers, this);
        currentBookings[numCurrentBookings] = newBooking;
        numCurrentBookings++;

        if (numCurrentBookings == MAX_BOOKINGS_NUM)
        {
            available = false;
        }

        return true;
    }

    public Booking getLatestBooking()
    {
        if (numCurrentBookings == 0)
        {
            return null;
        }

        return currentBookings[numCurrentBookings  - 1];
    }

    public void completeBooking(String id) 
    {
        int index = 0;

        for (int i = 0; i < numCurrentBookings; i++)
        {
            if (currentBookings[i].getId().equals(id))
            {
                index = i;
                break;
            }
        }

        Booking completedBooking = currentBookings[index];
        // remove the completed booking from currentBookings
        for (int i = index; i < numCurrentBookings - 1; i++)
        {
            currentBookings[index] = currentBookings[index + 1];
        }
        currentBookings[numCurrentBookings - 1] = null;
        numCurrentBookings--;
        available = true;

        ensurePastBookingCapacity(numPastBookings + 1);
        pastBookings[numPastBookings] = completedBooking;
        numPastBookings++;
    }

    private void ensurePastBookingCapacity(int newCapacity)
    {
        if (newCapacity >= pastBookings.length)
        {
            Booking[] newArray = new Booking[pastBookings.length * 2];
            System.arraycopy(
                    pastBookings, 0, newArray, 0, pastBookings.length);

            pastBookings = newArray;
        }
    }

    public String getDetails()
    {
        String newLine = "\n";
        StringBuilder details = new StringBuilder();

        details.append("RegNo:        ").append(regNo).append(newLine);

        details.append("Make & Model: ")
                .append(make).append(" ").append(model).append(newLine);

        details.append("Driver Name:  ").append(driverName).append(newLine);

        details.append("Capacity:     ")
                .append(passengerCapacity).append(newLine);

        details.append("Available:    ").append(available ? "YES" : "NO");

        return details.toString();
    }

    public String toString()
    {
        StringJoiner joiner = new StringJoiner(":");

        joiner.add(regNo)
            .add(make)
            .add(model)
            .add(driverName)
            .add(String.valueOf(passengerCapacity))
            .add(available ? "YES" : "NO");

        return joiner.toString();
    }

    public String getRegNo()
    {
        return regNo;
    }

    public boolean isAvailable(DateTime required)
    {
        if (!available)
        {
            return false;
        }

        for (int i = 0; i < numCurrentBookings; i++)
        {
            Booking booking = currentBookings[i];

            int daysDiff = DateTime.diffDays(
                    required, booking.getPickUpDateTime());
            // the same day
            if (daysDiff == 0)
            {
                return false;
            }
        }

        return true;
    }

    public String getDriverName()
    {
        return driverName;
    }

    public int getPassengerCapacity()
    {
        return passengerCapacity;
    }

}
