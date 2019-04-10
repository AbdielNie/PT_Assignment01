import java.util.StringJoiner;

/*
 * Class: Booking
 * Description: The class represents a single booking record for
 *              any object that can be booked.
 * Author: LiangyuNie - s3716113
 */
public class Booking
{
    /**
     * the id of this booking
     */
    private String id;
    /**
     * the standard booking fee for a car, which is $1.50
     */
    private double bookingFee;
    /**
     * the date to pick up the customer
     */
    private DateTime pickUpDateTime;

    /**
     * the first name of the customer
     */
    private String firstName;
    /**
     * the last name of the customer
     */
    private String lastName;

    /**
     * required number of passengers
     */
    private int numPassengers;
    /**
     * distance the car has travelled
     */
    private double kilometersTravelled;
    /**
     * the fee of trip, which is calculated as kilometres
     * travelled multiplied by 30% of the booking fee
     */
    private double tripFee;
    /**
     * the car associated with this booking
     */
    private Car car;

    /**
     * Constructs a booking instance with given parameters.
     */
    public Booking(String firstName, String lastName,
                   DateTime required, int numPassengers, Car car)
    {
        this.pickUpDateTime = required;
        this.firstName = firstName;
        this.lastName = lastName;
        this.numPassengers = numPassengers;
        this.car = car;
    }

    /**
     * Gets the details of this booking.
     *
     * @return the details of this booking.
     */
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

    /**
     * Gets the string value of this booking.
     *
     * @return the string value of this booking
     */
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

    /**
     * Gets the date to pick up the customer
     *
     * @return the date to pick up the customer
     */
    public DateTime getPickUpDateTime()
    {
        return pickUpDateTime;
    }

    /**
     * Gets the id of this booking.
     *
     * @return the id of this booking
     */
    public String getId()
    {
        return id;
    }

    /**
     * Sets the id of this booking.
     *
     * @param id a given id
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * Gets the car associated with this bookings.
     *
     * @return the car associated with this bookings
     */
    public Car getCar()
    {
        return car;
    }

    /**
     * Gets the first name of the customer.
     *
     * @return the first name of the customer
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * Gets the last name of the customer.
     *
     * @return the last name of the customer
     */
    public String getLastName()
    {
        return lastName;
    }

    /**
     * Gets the standard booking fee for a car.
     *
     * @return the standard booking fee for a car
     */
    public double getBookingFee()
    {
        return bookingFee;
    }

    /**
     * Gets the fee of trip.
     *
     * @return the fee of trip
     */
    public double getTripFee()
    {
        return tripFee;
    }

    /**
     * Sets the standard booking fee for a car.
     *
     * @param bookingFee a given booking fee
     */
    public void setBookingFee(double bookingFee)
    {
        this.bookingFee = bookingFee;
    }

    /**
     * Sets distance the car has travelled.
     *
     * @param kilometersTravelled a given distance in kilometers
     */
    public void setKilometersTravelled(double kilometersTravelled)
    {
        this.kilometersTravelled = kilometersTravelled;
    }

    /**
     * Sets the fee of trip.
     *
     * @param tripFee a given fee of trip
     */
    public void setTripFee(double tripFee)
    {
        this.tripFee = tripFee;
    }

}
