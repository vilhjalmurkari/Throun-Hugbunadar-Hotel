//keyrsla: java -cp .:sqlite-jdbc....jar hotelSearch
import java.sql.*;
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
	public ArrayList<Integer> zipcode;
	public int price_min;
	public int price_max;
	public int rating_min;
	public int rating_max;
	public int size_min;
	public int size_max;

	public SearchQuery(ArrayList<Integer> zipcode, int price_min, int price_max, int rating_min, int rating_max, int size_min, int size_max) {
		this.zipcode = zipcode;
		this.price_min = price_min;
		this.price_max = price_max;
		this.rating_min = rating_min;
		this.rating_max = rating_max;
		this.size_min = size_min;
		this.size_max = size_max;
	}
}
//NOTE: það vantar einhver svona dbController klasa(held að minnstakosti að það sé ætlast til þess)

class hotelSearch {
	public static void updateAllRoomPrices(double price_change, ArrayList<Room> rooms) {
		//...
	}

	public static void setRoomPrice(double price, ArrayList<Room> rooms) {
		//...
	}

	public static ArrayList<Hotel> search(SearchQuery query) {
		return new ArrayList<Hotel>();
	}

	//þetta gerir ekkert sérstakt eins og er.
	public static void main(String[] args) throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		Connection connection = null;

		try {
			connection = DriverManager.getConnection("jdbc:sqlite:testdb.db");
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select name from Hotels");

			while(rs.next()) {
				System.out.println(rs.getString(1));
			}
		}
		catch(SQLException e) {
			System.err.println(e.getMessage());
		}finally {
			try {
				if(connection != null) {
					connection.close();
				}
			}catch(SQLException e) {
				System.err.println(e);
			}
		}
	}
}