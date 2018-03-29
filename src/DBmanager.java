import java.util.ArrayList;
import java.sql.*;

public class DBmanager {

	static Connection connection;
	static Statement sqlStatement;


	// Notkun: init()
	// Eftir:  Connection og Statement hafa verið upphafsstillt
	//         svo unnt er að framkvæma SQL aðgerðir.
	public static void init() {
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:testdb.db");
			sqlStatement = connection.createStatement();
		} catch( ClassNotFoundException e ) {
			System.out.println("Couldn't find jdbc file.");
		} catch( SQLException e ) {
			System.out.println("Unable to make SQL connection.");
			throw new SQLException(e.getmessage());
		}
	}

	// Notkun: getAllHotels()
	// Skilar: ArrayList af öllum hótelum í gagnagrunni.
	public static ArrayList<Hotel> getAllHotels() throws SQLException {
		ArrayList<Hotel> listOfHotels = new ArrayList<Hotel>();
		ResultSet rset = sqlStatement.executeQuery("SELECT * FROM Hotels");

		while(rset.next()) {

			String hotel_name = rset.getString("name");
			String hotel_zipcode = rset.getString("zipcode"); 

			Hotel h = new Hotel(
				hotel_name,
				rset.getInt("rating"),
				rset.getString("description"),
				hotel_zipcode,
				getHotelTags(hotel_name, hotel_zipcode),
				getRoomFromHotel(hotel_name, hotel_zipcode);
			);

			listOfHotels.add(h);
		}
		return listOfHotels;
	}

	private static ArrayList<String> getHotelTags(String hotel_name, String hotel_zipcode) throws SQLException {
		ArrayList<String> result;
		ResultSet rset = sqlStatement.executeQuery("SELECT tag_name FROM Hotel_tags WHERE hotel_name = \"" + hotel_name + "\" and hotel_zip = " + hotel_zip);

		while(rset.next()) result.add(rset.getString("tag_name"));

		return result;
	}

	private static ArrayList<String> getRoomTags(int room_id) throws SQLException {
		ArrayList<String> result;
		ResultSet rset = sqlStatement.executeQuery("SELECT tag_name FROM Room_tags WHERE room_id = " + room_id);

		while(rset.next()) result.add(rset.getString("tag_name"));

		return result;
	}

	// Notkun: getRoomsFromHotel( hotelname, zip)
	// Skilar: ArrayList af herbergjum sem eru í viðeigandi hóteli.
	//         Ath. hotelname, zip er lykill.
	public static ArrayList<Room> getRoomsFromHotel(String hotel_name, int hotel_zipcode) throws SQLException {
		ArrayList<Room> listOfRooms = new ArrayList<Room>();
		String query = "SELECT * FROM Rooms WHERE hotel_name= \"" + hotel_name + "\" AND hotel_zipcode = " + hotel_zipcode + ";";

		ResultSet rset = sqlStatement.executeQuery(query);
		int room_id = rset.getInt("id");

		while(rset.next()) {
			Room room = new Room(
				room_id,
				rset.getInt("size"),
				rset.getInt("bed_count"),
				rset.getInt("price"),
				getRoomTags(room_id);
			);

			listOfRooms.add(room);
		}

		return listOfRooms;
	}

	// Notkun: getRoomsFromHotel(hotel)
	// Skilar: ArrayList af herbergjum sem eru í viðeigandi hóteli.
	//         Ath. þetta mun nota hótel hlut til að kalla á fallið með name og zipcode
	public static ArrayList<Room> getRoomsFromHotel(Hotel hotel) {
		return getRoomsFromHotel(hotel.name, hotel.zipcode);
	}

	public static void setRoomPrice(double new_price, ArrayList<Room> rooms) throws SQLException {
		PreparedStatement ps = conn.preparedStatement("UPDATE TABLE Rooms SET price = ? WHERE id = ?");

		for(Room r : rooms) {
			ps.setInteger(1, new_price);
			ps.setInteger(2, r.id);
			ps.executeUpdate();
		}
	}

	public static void changeRoomPriceByAmount(double price_change, ArrayList<Room> rooms) {
		PreparedStatement ps = conn.preparedStatement("UPDATE TABLE Rooms SET price = ? WHERE id = ?");

		for(Room r : rooms) {
			r.price += price_change;

			ps.setInteger(1, r.price);
			ps.setInteger(2, r.id);
			ps.executeUpdate();
		}
	}

	public static void changeRoomPriceByPercent(double percent, ArrayList<Room> rooms) {
		PreparedStatement ps = conn.preparedStatement("UPDATE TABLE Rooms SET price = ? WHERE id = ?");

		for(Room r : rooms) {
			r.price *= 1.0 + precent / 100.0;

			ps.setInteger(1, r.price);
			ps.setInteger(2, r.id);
			ps.executeUpdate();
		}
	}

	public static void addHotel(Hotel hotel) throws SQLException {
		PreparedStatement ps = conn.preparedStatement("INSERT INTO Hotels(name, rating, description, zipcode) VALUES(?, ?, ?, ?)");

		ps.setString(1, hotel.name);
		ps.setInteger(2, hotel.rating);
		ps.setString(3, hotel.description);
		ps.setInteger(4, hotel.zipcode);

		ps.executeUpdate();

		//tags
		ps = conn.preparedStatement("INSERT INTO Hotel_tags(hotel_name, hotel_zipcode, tag_name) VALUES(?, ?, ?)");

		for(String tag : hotel.tags) {
			ps.setString(1, hotel.name);
			ps.setInteger(2, hotel.zipcode);
			ps.setString(3, tag);

			ps.executeUpdate();
		}

		addRoomsToHotel(hotel.rooms, hotel);
	}

	public static void addHotels(ArrayList<Hotel> hotels) {
		for(Hotel h : hotels) {
			addHotel(h);
		}
	}

	public static void addRoomToHotel(Room room, Hotel hotel) {
		PreparedStatement ps = conn.preparedStatement("INSERT INTO Rooms(id, hotel_name, hotel_zip, size, price, bed_count) VALUES(?, ?, ?, ?, ?, ?)");

		ps.setNull(1, java.sql.Types.INTEGER);
		ps.setString(2, hotel.name);
		ps.setInteger(3, hotel.zipcode);
		ps.setInteger(4, hotel.size);
		ps.setInteger(5, hotel.price);
		ps.setInteger(4, hotel.bed_count);

		ps.executeUpdate();
	}

	public static void addRoomsToHotel(ArrayList<Room> rooms, Hotel hotel) {
		for(Room r : rooms) {
			addRoomToHotel(r, hotel);
		}
	}

	public static void bookRoom(int room_id, int user_id) {
		PreparedStatement ps = conn.preparedStatement("INSERT INTO Bookings(user_id, room_id) VALUES(?, ?)");

		ps.setInteger(1, user_id);
		ps.setInteger(2, room_id);

		ps.executeUpdate();
	}

	public static ArrayList<Hotel> search(SearchQuery query) {
		//sql query...
		return new ArrayList<Hotel>();
	}
}
