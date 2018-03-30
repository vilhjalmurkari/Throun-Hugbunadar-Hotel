import java.util.Date;
import java.util.ArrayList;
import java.sql.*;

class User {
	public int id;
	public String name;
	public String email;
	public ArrayList<Booking> bookings;

	public User(int id, String name, String email, ArrayList<Booking> bookings) {
		this.id = id;
		this.name = name;
		this.email = email;
		//Ættu bookings nokkuð að vera í constructornum?
		this.bookings = bookings;
	}

	public void cancelUnconfirmed() {
		//...
	}

	public void cancelBooking(int key) {
		//...
	}

	public void updateEmail(String newEmail) {
		this.email = newEmail;
	}

	public void makeBooking(Room room, Date start_date, Date end_date) throws SQLException {
		Booking booking = new Booking(room, start_date, end_date);
		bookings.add(booking);
		DBmanager.bookRoom(this.id, room.id, start_date.getTime(), end_date.getTime());
	}

}
