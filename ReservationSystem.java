import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ReservationSystem {

    // Booking Class
    static class Booking {
        List<String> passengerNames;
        int busNo;
        Date date;
        int numberOfTickets;

        Scanner ab = new Scanner(System.in);

        public Booking() {
            passengerNames = new ArrayList<>();

            System.out.println("Enter number of tickets to book: ");
            this.numberOfTickets = ab.nextInt();
            ab.nextLine();

            for (int i = 1; i <= numberOfTickets; i++) {
                System.out.println("Enter name of passenger " + i + ": ");
                passengerNames.add(ab.nextLine());
            }

            System.out.println("Enter Bus number: ");
            this.busNo = ab.nextInt();

            boolean validDate = false;
            while (!validDate) {
                System.out.println("Enter date (dd-MM-yyyy): ");
                String dateInput = ab.next();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                dateFormat.setLenient(false);
                try {
                    date = dateFormat.parse(dateInput);
                    validDate = true;
                } catch (ParseException e) {
                    System.out.println("Invalid date format or invalid date. Please enter a valid date.");
                }
            }
        }

        public boolean isAvailable(ArrayList<Booking> bookings, ArrayList<Bus> buses) {
            int capacity = 0;
            for (Bus bus : buses) {
                if (bus.getBusNo() == busNo) {
                    capacity = bus.getCapacity();
                }
            }
            int booked = 0;
            for (Booking b : bookings) {
                if (b.busNo == busNo && b.date.equals(date)) {
                    booked += b.numberOfTickets;
                }
            }
            return (booked + numberOfTickets) <= capacity;
        }

        public void displayBookingDetails() {
            System.out.println("Bus Number: " + busNo);
            System.out.println("Date: " + date);
            System.out.println("Number of Tickets: " + numberOfTickets);
            System.out.println("Passenger Names: ");
            for (String name : passengerNames) {
                System.out.println("- " + name);
            }
        }
    }

    // Bus Class
    static class Bus {
        private int busNo;
        private boolean ac;
        private int capacity;

        Bus(int no, boolean ac, int cap) {
            this.busNo = no;
            this.ac = ac;
            this.capacity = cap;
        }

        public int getCapacity() {
            return capacity;
        }

        public void setCapacity(int cap) {
            capacity = cap;
        }

        public int getBusNo() {
            return busNo;
        }

        public boolean isAc() {
            return ac;
        }

        public void setAc(boolean ac) {
            this.ac = ac;
        }

        public void displayBusInfo() {
            System.out.println("-------------------------------------------");
            System.out.println("|      " + busNo + "      |   " + ac + "   |       " + capacity + "      |");
            System.out.println("-------------------------------------------");
        }
    }

    // Main Class
    public static void main(String[] args) {
        ArrayList<Bus> buses = new ArrayList<>();
        ArrayList<Booking> bookings = new ArrayList<>();

        // Adding buses
        buses.add(new Bus(1, true, 1));
        buses.add(new Bus(2, false, 20));
        buses.add(new Bus(3, true, 30));

        Scanner ab = new Scanner(System.in);
        int userOption = 1;
        while (userOption == 1) {
            System.out.println("-------------------------------------------");
            System.out.println("|    BusNo    |    AC    |    Capacity    |");
            for (Bus bus : buses) {
                bus.displayBusInfo();
            }
            System.out.println("Enter 1 to book and 2 to exit:");
            userOption = ab.nextInt();
            if (userOption == 1) {
                Booking booking = new Booking();
                if (booking.isAvailable(bookings, buses)) {
                    System.out.println("Your details:");
                    booking.displayBookingDetails();
                    System.out.println("Enter yes to confirm booking, No to cancel: ");
                    String result = ab.next();
                    if (result.equalsIgnoreCase("yes")) {
                        bookings.add(booking);
                        System.out.println("Booking is confirmed.");
                    } else {
                        System.out.println("Your booking is cancelled.");
                    }
                } else {
                    System.out.println("Sorry. Not enough tickets available. Try another bus or date...");
                }
            }
        }
        ab.close();
    }
}
