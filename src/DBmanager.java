import java.util.ArrayList;
import java.sql.*;

public class DBmanager {

	static Connection connection;
	static Statement sqlStatement;


	// Notkun: init()
	// Eftir:  Connection og Statement hafa verið upphafsstillt
	//         svo unnt er að framkvæma SQL aðgerðir.
	public static void init() throws SQLException {
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:testdb.db");
			sqlStatement = connection.createStatement();
		} catch( ClassNotFoundException e ) {
			System.out.println("Couldn't find jdbc file.");
		}
	}

	// Notkun: getAllHotels()
	// Skilar: ArrayList af öllum hótelum í gagnagrunni.
	public static ArrayList<Hotel> getAllHotels() throws SQLException {
		ArrayList<Hotel> listOfHotels = new ArrayList<Hotel>();
		ResultSet rset = sqlStatement.executeQuery("SELECT * FROM Hotels");

		while(rset.next()) {

			String hotel_name = rset.getString("name");
			int hotel_zipcode = rset.getInt("zipcode");

			Hotel h = new Hotel(
				hotel_name,
				rset.getInt("rating"),
				rset.getString("description"),
				hotel_zipcode,
				getHotelTags(hotel_name, hotel_zipcode),
				getRoomsFromHotel(hotel_name, hotel_zipcode)
			);

			listOfHotels.add(h);
		}
		return listOfHotels;
	}

	private static ArrayList<String> getHotelTags(String hotel_name, int hotel_zipcode) throws SQLException {
		ArrayList<String> result = new ArrayList<String>();
		ResultSet rset = sqlStatement.executeQuery("SELECT tag_name FROM Hotel_tags WHERE hotel_name = \"" + hotel_name + "\" and hotel_zipcode = " + hotel_zipcode);

		while(rset.next()) result.add(rset.getString("tag_name"));

		return result;
	}

	private static ArrayList<String> getRoomTags(int room_id) throws SQLException {
		ArrayList<String> result = new ArrayList<String>();
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
				getRoomTags(room_id)
			);

			listOfRooms.add(room);
		}

		return listOfRooms;
	}

	// Notkun: getRoomsFromHotel(hotel)
	// Skilar: ArrayList af herbergjum sem eru í viðeigandi hóteli.
	//         Ath. þetta mun nota hótel hlut til að kalla á fallið með name og zipcode
	public static ArrayList<Room> getRoomsFromHotel(Hotel hotel) throws SQLException {
		return getRoomsFromHotel(hotel.name, hotel.zipcode);
	}

	public static void setRoomPrice(double new_price, ArrayList<Room> rooms) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("UPDATE TABLE Rooms SET price = ? WHERE id = ?");

		for(Room r : rooms) {
			ps.setInt(1, (int)new_price);
			ps.setInt(2, r.id);
			ps.executeUpdate();
		}
	}

	public static void changeRoomPriceByAmount(double price_change, ArrayList<Room> rooms) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("UPDATE TABLE Rooms SET price = ? WHERE id = ?");

		for(Room r : rooms) {
			r.price += (int)price_change;

			ps.setInt(1, r.price);
			ps.setInt(2, r.id);
			ps.executeUpdate();
		}
	}

	public static void changeRoomPriceByPercent(double percent, ArrayList<Room> rooms) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("UPDATE TABLE Rooms SET price = ? WHERE id = ?");

		for(Room r : rooms) {
			r.price = (int)(r.price * (1.0 + percent / 100.0));

			ps.setInt(1, r.price);
			ps.setInt(2, r.id);
			ps.executeUpdate();
		}
	}

	public static void addHotel(Hotel hotel) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("INSERT INTO Hotels(name, rating, description, zipcode) VALUES(?, ?, ?, ?)");

		ps.setString(1, hotel.name);
		ps.setInt(2, hotel.rating);
		ps.setString(3, hotel.description);
		ps.setInt(4, hotel.zipcode);

		ps.executeUpdate();

		//tags
		ps = connection.prepareStatement("INSERT INTO Hotel_tags(hotel_name, hotel_zipcode, tag_name) VALUES(?, ?, ?)");

		for(String tag : hotel.tags) {
			ps.setString(1, hotel.name);
			ps.setInt(2, hotel.zipcode);
			ps.setString(3, tag);

			ps.executeUpdate();
		}

		addRoomsToHotel(hotel.rooms, hotel);
	}

	public static void addHotels(ArrayList<Hotel> hotels) throws SQLException {
		for(Hotel h : hotels) {
			addHotel(h);
		}
	}

	public static void addRoomToHotel(Room room, Hotel hotel) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("INSERT INTO Rooms(id, hotel_name, hotel_zip, size, price, bed_count) VALUES(?, ?, ?, ?, ?, ?)");

		ps.setNull(1, java.sql.Types.INTEGER);
		ps.setString(2, hotel.name);
		ps.setInt(3, hotel.zipcode);
		ps.setInt(4, room.size);
		ps.setInt(5, room.price);
		ps.setInt(4, room.bed_count);

		ps.executeUpdate();
	}

	public static void addRoomsToHotel(ArrayList<Room> rooms, Hotel hotel) throws SQLException {
		for(Room r : rooms) {
			addRoomToHotel(r, hotel);
		}
	}

	public static void bookRoom(int room_id, int user_id, long start_date, long end_date) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("INSERT INTO Bookings(user_id, room_id, start_date, end_date) VALUES(?, ?, ?, ?)");

		ps.setInt(1, user_id);
		ps.setInt(2, room_id);
		ps.setLong(3, start_date);
		ps.setLong(4, end_date);

		ps.executeUpdate();
	}

	public static ArrayList<Hotel> search(SearchQuery query) {
		//sql query...
		return new ArrayList<Hotel>();
	}
}
