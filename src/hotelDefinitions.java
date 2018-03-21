import java.util.Date;
import java.util.ArrayList;

class Hotel {
	public String name;
	public int rating;
	public String description;
	public int zipcode;
	public ArrayList<String> tags;
	public ArrayList<Room> rooms;

	public Hotel(String name, int rating, String description, int zipcode, ArrayList<String> tags, ArrayList<Room> rooms) {
		this.name = name;
		this.rating = rating;
		this.description = description;
		this.zipcode = zipcode;
		this.tags = tags;
		this.rooms = rooms;
	}

	//einhver föll hér
	public int getHotelRating() {
		return this.rating;
	}
}

class Room {
	public int id;
	public int size;
	//mögulega eitthvað til að gera þessi eigindi aðeins meira modular... bara mögulega
	public int bed_count;
	public boolean shower;
	public int price;

	public Room(int id, int size, int bed_count, boolean shower, int price) {
		this.id = id;
		this.size = size;
		this.bed_count = bed_count;
		this.shower = shower;
		this.price = price;
	}

	//einhver föll hér
	public int getRoomSize() {
		return this.size;
	}

}

class User {
	public int id;
	public String name;
	public String email;
	public ArrayList<Booking> bookings;

	public User(int id, String name, String email, ArrayList<Booking> bookings) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.bookings = bookings;
	}

	public void cancelUnconfirmed() {
		//...
	}

	public void cancelBooking(int key) {
		//...
	}
}

class Booking {
	public int id;
	public Room room;
	public Date start_date;
	public Date end_date;
	public boolean confirmed;

	// Usage:
	// Before:
	// After:
	public Booking(int id, Room room, Date start_date, Date end_date) {
		this.id = id;
		this.room = room;
		this.start_date = start_date;
		this.end_date = end_date;
		this.confirmed = false;
	}

	//einhver föll hér
}

class SearchQuery {
	public ArrayList<Integer> zipcodes;
	public int price_min;
	public int price_max;
	public int rating_min;
	public int rating_max;
	public int size_min;
	public int size_max;

	public SearchQuery(ArrayList<Integer> zipcodes, int price_min, int price_max, int rating_min, int rating_max, int size_min, int size_max) {
		this.zipcodes = zipcodes;
		this.price_min = price_min;
		this.price_max = price_max;
		this.rating_min = rating_min;
		this.rating_max = rating_max;
		this.size_min = size_min;
		this.size_max = size_max;
	}
}