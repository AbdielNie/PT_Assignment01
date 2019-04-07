import java.util.*;

/*
 * Class: Booking
 * Description: The class represents a single booking record for
 *              any object that can be booked.
 * Author: LiangyuNie - s3716113
 */
public class Booking
{

    private String id;
    private double bookingFee;
    private DateTime pickUpDateTime;

    private String firstName;
    private String lastName;

    private int numPassengers;
    private double kilometersTravelled;
    private double tripFee;
    private Car car;

    public Booking(String firstName, String lastName,
                   DateTime required, int numPassengers, Car car)
    {
        this.pickUpDateTime = required;
        this.firstName = firstName;
        this.lastName = lastName;
        this.numPassengers = numPassengers;
        this.car = car;
    }

    public String getDetails()
    {
        String newLine = "\n";
        StringBuilder details = new StringBuilder();

        details.append("id:           ").append(id).append(newLine);

        details.append("Booking Fee:  ")
                .append(String.format("%.2f", bookingFee)).append(newLine);

        details.append("Pick up Date: ").
                append(pickUpDateTime.getFormattedDate()).append(newLine);

        details.append("Name:         ")
                .append(firstName).append(" ").append(lastName).append(newLine);

        details.append("Passengers:   ").append(numPassengers).append(newLine);

        details.append("Travelled:    ")
                .append(kilometersTravelled == 0 ? "N/A" :
                        String.format("%.2fkm", kilometersTravelled))
                .append(newLine);

        details.append("Trip Fee:     ")
                .append(tripFee == 0 ? "N/A" : String.format("%.2f", tripFee))
                .append(newLine);

        details.append("Car Id:       ").append(car.getRegNo());

        return details.toString();
    }

    public String toString()
    {
        StringJoiner joiner = new StringJoiner(":");

        joiner.add(id)
                .add(String.format("%.2f", bookingFee))
                .add(pickUpDateTime.getEightDigitDate())
                .add(firstName + " " + lastName)
                .add(String.valueOf(numPassengers))
                .add(String.format("%.2fkm", kilometersTravelled))
                .add(car.getRegNo());

        return joiner.toString();
    }

    public DateTime getPickUpDateTime()
    {
        return pickUpDateTime;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public Car getCar()
    {
        return car;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public double getBookingFee()
    {
        return bookingFee;
    }

    public double getTripFee()
    {
        return tripFee;
    }

    public void setBookingFee(double bookingFee)
    {
        this.bookingFee = bookingFee;
    }

    public void setKilometersTravelled(double kilometersTravelled)
    {
        this.kilometersTravelled = kilometersTravelled;
    }

    public void setTripFee(double tripFee)
    {
        this.tripFee = tripFee;
    }

}
