package hotelAPI;

import java.util.Set;
import java.util.HashMap;
import java.sql.*;

public class User {

	public String name;
	public String email;
	public HashMap<Integer, Booking> bookings;

	public User(String email) throws SQLException {
		this.email = email;
		this.bookings = DBmanager.getBookings(this);
	}

	public void cancelUnconfirmed() throws SQLException {
		Set<Integer> keys = bookings.keySet();
		for(Integer key : keys) {
			if(!bookings.get(key).confirmed) {
				bookings.get(key).cancelBooking();
				bookings.remove(bookings.get(key).id);
			}
		}
		System.out.println("All unconfirmed bookings have been canceled.");
	}


	public void cancelBooking(int key) {
		if( bookings.containsKey(key) ) {
			bookings.get(key).cancelBooking();
			bookings.remove(key);

			return;
		}

		System.out.println("Sorry, this booking has already been confirmed or doesn't exist.");
	}
	
	public void confirmBooking(int id) throws SQLException {
		Booking b = bookings.get(id);
		if(b != null) {
			b.confirmBooking();
			b.confirmed = true;
		}
	}

	public void makeBooking(Room room, long start_date, long end_date) throws SQLException {
		
		// returns true iff room was free and booked.
		if( !DBmanager.bookRoom( room, this, start_date, end_date) )
			return;

		Booking booking = new Booking(room, start_date, end_date);
		bookings.put(booking.id, booking);
	}

}
