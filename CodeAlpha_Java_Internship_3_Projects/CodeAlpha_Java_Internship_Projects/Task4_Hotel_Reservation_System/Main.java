import java.io.*;
import java.util.*;

public class Main {
    private static final String FILE = "bookings.csv";
    private static final Scanner sc = new Scanner(System.in);
    private static final List<Room> rooms = new ArrayList<Room>();
    private static final List<Booking> bookings = new ArrayList<Booking>();
    private static int nextBookingId = 1001;

    static class Room {
        int number;
        String category;
        double price;
        Room(int number, String category, double price) {
            this.number = number; this.category = category; this.price = price;
        }
    }

    static class Booking {
        int id, roomNumber, nights;
        String guestName;
        double total;
        boolean active;
        Booking(int id, int roomNumber, int nights, String guestName, double total, boolean active) {
            this.id = id; this.roomNumber = roomNumber; this.nights = nights;
            this.guestName = guestName; this.total = total; this.active = active;
        }
    }

    public static void main(String[] args) {
        initializeRooms();
        loadBookings();
        while (true) {
            System.out.println("\n===== HOTEL RESERVATION SYSTEM =====");
            System.out.println("1. View rooms");
            System.out.println("2. Search available rooms");
            System.out.println("3. Book a room");
            System.out.println("4. View booking details");
            System.out.println("5. Cancel booking");
            System.out.println("6. Exit");
            int choice = readInt("Choose an option: ");
            switch (choice) {
                case 1: showRooms(false); break;
                case 2: showRooms(true); break;
                case 3: bookRoom(); break;
                case 4: viewBooking(); break;
                case 5: cancelBooking(); break;
                case 6: saveBookings(); System.out.println("Bookings saved. Goodbye!"); return;
                default: System.out.println("Please choose 1 to 6.");
            }
        }
    }

    private static void initializeRooms() {
        rooms.add(new Room(101, "Standard", 1500));
        rooms.add(new Room(102, "Standard", 1500));
        rooms.add(new Room(201, "Deluxe", 2500));
        rooms.add(new Room(202, "Deluxe", 2500));
        rooms.add(new Room(301, "Suite", 4500));
    }

    private static boolean isAvailable(int roomNumber) {
        for (Booking b : bookings)
            if (b.roomNumber == roomNumber && b.active) return false;
        return true;
    }

    private static void showRooms(boolean availableOnly) {
        System.out.println("\n--- ROOM LIST ---");
        System.out.printf("%-10s %-12s %-12s %-12s%n", "Room", "Category", "Price/night", "Status");
        for (Room r : rooms) {
            boolean available = isAvailable(r.number);
            if (!availableOnly || available) {
                System.out.printf("%-10d %-12s Rs.%-9.2f %-12s%n",
                    r.number, r.category, r.price, available ? "Available" : "Booked");
            }
        }
        if (availableOnly) System.out.println("(Only currently available rooms are shown.)");
    }

    private static void bookRoom() {
        showRooms(true);
        int roomNumber = readInt("Enter room number to book: ");
        Room selected = null;
        for (Room r : rooms) if (r.number == roomNumber) selected = r;
        if (selected == null || !isAvailable(roomNumber)) {
            System.out.println("Invalid room number or room is not available.");
            return;
        }
        System.out.print("Enter guest name: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty() || name.contains(",")) {
            System.out.println("Guest name cannot be empty or contain a comma.");
            return;
        }
        int nights = readInt("Number of nights: ");
        if (nights <= 0) { System.out.println("Number of nights must be at least 1."); return; }

        double total = selected.price * nights;
        System.out.printf("Total amount: Rs. %.2f%n", total);
        System.out.print("Simulate payment? (yes/no): ");
        String payment = sc.nextLine().trim().toLowerCase(Locale.ROOT);
        if (!payment.equals("yes") && !payment.equals("y")) {
            System.out.println("Payment not completed. Booking cancelled.");
            return;
        }
        Booking booking = new Booking(nextBookingId++, roomNumber, nights, name, total, true);
        bookings.add(booking);
        saveBookings();
        System.out.println("Payment successful (simulation). Booking confirmed!");
        printBooking(booking);
    }

    private static void viewBooking() {
        int id = readInt("Enter booking ID: ");
        for (Booking b : bookings) {
            if (b.id == id) { printBooking(b); return; }
        }
        System.out.println("Booking ID not found.");
    }

    private static void cancelBooking() {
        int id = readInt("Enter booking ID to cancel: ");
        for (Booking b : bookings) {
            if (b.id == id && b.active) {
                b.active = false;
                saveBookings();
                System.out.println("Booking cancelled successfully. No real payment/refund was processed.");
                return;
            }
        }
        System.out.println("Active booking not found.");
    }

    private static void printBooking(Booking b) {
        System.out.println("\n--- BOOKING DETAILS ---");
        System.out.println("Booking ID : " + b.id);
        System.out.println("Guest name : " + b.guestName);
        System.out.println("Room number: " + b.roomNumber);
        System.out.println("Nights     : " + b.nights);
        System.out.printf("Total      : Rs. %.2f%n", b.total);
        System.out.println("Status     : " + (b.active ? "Confirmed" : "Cancelled"));
    }

    private static void loadBookings() {
        File file = new File(FILE);
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",", -1);
                if (p.length == 7) {
                    try {
                        Booking b = new Booking(Integer.parseInt(p[0]), Integer.parseInt(p[1]),
                            Integer.parseInt(p[2]), p[3], Double.parseDouble(p[4]), Boolean.parseBoolean(p[5]));
                        bookings.add(b);
                        nextBookingId = Math.max(nextBookingId, b.id + 1);
                    } catch (NumberFormatException ignored) { }
                }
            }
        } catch (IOException e) {
            System.out.println("Could not load bookings: " + e.getMessage());
        }
    }

    private static void saveBookings() {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE))) {
            for (Booking b : bookings)
                out.println(b.id + "," + b.roomNumber + "," + b.nights + "," + b.guestName + "," +
                    b.total + "," + b.active + ",saved");
        } catch (IOException e) {
            System.out.println("Could not save bookings: " + e.getMessage());
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try { return Integer.parseInt(sc.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("Enter a valid whole number."); }
        }
    }
}
