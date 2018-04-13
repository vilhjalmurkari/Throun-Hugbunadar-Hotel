package hotelAPI;

import java.util.HashMap;
import java.sql.*;

public class User {
	public int id;
	public String name;
	public String email;
	// FIXIT
	// Bookings are never initialised :o
	public HashMap<Integer, Booking> bookings;

	public User(int id) {
		this.id = id;
		//this.name = name;
		//this.email = email;
		//Ættu bookings nokkuð að vera í constructornum?
		//
		//Nei, það ættu að vera til notendur án bókana
		//this.bookings = bookings;
	}

	public void cancelUnconfirmed() {
		//if(bookings == null) bookings = DBmanager.getBookingsByUser();
		for( int i=0; i<bookings.size(); i++ ) {
			if(bookings.get(i).confirmed ) {
				bookings.remove(i);
				//DBmanager.bookRoom(---);
			}
		}
		System.out.println("All unconfirmed bookings have been canceled.");
	}


	// Needs an update to utilise HASHMAP
	// FIXIT
	public void cancelBooking(int key) {
		for( int i=0; i<bookings.size(); i++ ) {
			if( bookings.get(i).confirmed ) {
				System.out.println("Sorry, this booking has already been confirmed.");
				break;
			}
			if( bookings.get(i).id==key ) {
				bookings.remove(i);
				break;
			}
		}
	}
	
	public void confirmBooking(int id) {
		Booking b = bookings.get(id);
		if(b != null) {
			b.confirmBooking();
			b.confirmed = true;
		}
	}

	private void updateEmail(String newEmail) {
		this.email = newEmail;
		// Perhaps add here some form of confirmation email?
	}

	public void makeBooking(Room room, long start_date, long end_date) throws SQLException {
		assert( DBmanager.isRoomFree(room, start_date, end_date) );

		Booking booking = new Booking(room, start_date, end_date);
		bookings.put(booking.id, booking);
		DBmanager.bookRoom( room, this, start_date, end_date);
	}

}
