package hotelAPI;

import java.util.Date;
import java.util.ArrayList;
import java.sql.*;

public class User {
	public int id;
	public String name;
	public String email;
	public ArrayList<Booking> bookings;

	public User(int id, String name, String email, ArrayList<Booking> bookings) {
		this.id = id;
		this.name = name;
		this.email = email;
		//Ættu bookings nokkuð að vera í constructornum?
		//   Nei, það ættu að vera til notendur án bókana
		//this.bookings = bookings;
	}

	public void cancelUnconfirmed() {
		for( int i=0; i<bookings.size(); i++ ) {
			if( bookings.get(i).confirmed )
				bookings.remove(i);
		}
		System.out.println("All unconfirmed bookings have been canceled.");
	}

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

	public void updateEmail(String newEmail) {
		this.email = newEmail;
	}

	public void makeBooking(Room room, Date start_date, Date end_date) throws SQLException {
		assert( DBmanager.isRoomFree(room, (int)start_date.getTime(), (int)end_date.getTime() ) );

		Booking booking = new Booking(room, start_date, end_date);
		bookings.add(booking);
		DBmanager.bookRoom(this.id, room.id, start_date.getTime(), end_date.getTime());
	}

}
