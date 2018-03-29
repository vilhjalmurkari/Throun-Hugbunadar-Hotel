import java.util.Date;
import java.util.ArrayList;

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

}
