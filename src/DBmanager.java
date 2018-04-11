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

		} catch(ClassNotFoundException e) {
			System.out.println("Couldn't find jdbc jar file.");
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
				null,
				null
			);

			listOfHotels.add(h);
		}

		for(Hotel h : listOfHotels) {
			h.tags = getHotelTags(h.name, h.zipcode);
			h.rooms = getRoomsFromHotel(h.name, h.zipcode);
		}

		return listOfHotels;
	}

	public static Hotel getHotel(String hotel_name, int hotel_zipcode) throws SQLException {
		Hotel hotel = null;
		ResultSet rset = sqlStatement.executeQuery("SELECT * FROM Hotels WHERE name= \"" + hotel_name + "\" AND zipcode = " + hotel_zipcode);

		while(rset.next()) {

			String name = rset.getString("name");
			int zipcode = rset.getInt("zipcode");

		 	hotel = new Hotel(
				name,
				rset.getInt("rating"),
				rset.getString("description"),
				zipcode,
				getHotelTags(name, zipcode),
				getRoomsFromHotel(name, zipcode)
			);
		}
		if (hotel == null) {
			System.out.println("Ekkert fannst");
		}

		return hotel;
	}

	public static ArrayList<Hotel> getHotelsByName(String hotel_name) throws SQLException {
		ArrayList<Hotel> listOfHotels = new ArrayList<Hotel>();
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM Hotels WHERE name = ?");
		ps.setString(1,hotel_name);
		ResultSet rset = ps.executeQuery();

		while(rset.next()) {
			Hotel h = new Hotel(
				rset.getString("name"),
				rset.getInt("rating"),
				rset.getString("description"),
				rset.getInt("zipcode"),
				null,
				null
			);

			listOfHotels.add(h);
		}

		for(Hotel h : listOfHotels) {
			h.tags = getHotelTags(h.name, h.zipcode);
			h.rooms = getRoomsFromHotel(h.name, h.zipcode);
		}

		return listOfHotels;
	}

	private static ArrayList<String> getHotelTags(String hotel_name, int hotel_zipcode) throws SQLException {
		ArrayList<String> result = new ArrayList<String>();
		PreparedStatement ps = connection.prepareStatement("SELECT tag_name FROM Hotel_tags WHERE hotel_name = ? AND hotel_zipcode = ?");
		ps.setString(1,hotel_name);
		ps.setInt(2,hotel_zipcode);
		ps.executeQuery();
		ResultSet rset = ps.executeQuery();


		while(rset.next()) result.add(rset.getString("tag_name"));

		return result;
	}

	private static ArrayList<String> getRoomTags(int room_id) throws SQLException {
		ArrayList<String> result = new ArrayList<String>();
		PreparedStatement ps = connection.prepareStatement("SELECT tag_name FROM Room_tags WHERE room_id = ?");
		ps.setInt(1,room_id);
		ResultSet rset = ps.executeQuery();

		while(rset.next()) result.add(rset.getString("tag_name"));

		return result;
	}

	// Notkun: getRoomsFromHotel( hotel_name, hotel_zipcode)
	// Skilar: ArrayList af herbergjum sem eru í viðeigandi hóteli.
	//         Ath. hotel_name, hotel_zipcode er lykill.
	public static ArrayList<Room> getRoomsFromHotel(String hotel_name, int hotel_zipcode) throws SQLException {
		ArrayList<Room> listOfRooms = new ArrayList<Room>();
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM Rooms WHERE hotel_name= ? AND hotel_zipcode = ?");
		ps.setString(1,hotel_name);
		ps.setInt(2,hotel_zipcode);
		ResultSet rset = ps.executeQuery();

		while(rset.next()) {
			int room_id = rset.getInt("id");
			Room room = new Room(
				rset.getInt("size"),
				rset.getInt("bed_count"),
				rset.getInt("price"),
				null
			);

			room.id = room_id;

			listOfRooms.add(room);
		}

		for(Room r : listOfRooms) r.tags = getRoomTags(r.id);

		return listOfRooms;
	}

	// Notkun: getRoomsFromHotel(hotel)
	// Skilar: ArrayList af herbergjum sem eru í viðeigandi hóteli.
	//         Ath. þetta mun nota hótel hlut til að kalla á fallið með name og zipcode
	public static ArrayList<Room> getRoomsFromHotel(Hotel hotel) throws SQLException {
		return getRoomsFromHotel(hotel.name, hotel.zipcode);
	}

	// Notkun: getRoomFromHotel( room_id, hotel_name, hotel_zipcode)
	// Skilar:  Skilar hótelherbergi með ákveðið id.
	//         Ath. hotel_name, hotel_zipcode er lykill.
	public static Room getRoomFromHotel( int room_id, String hotel_name, int hotel_zipcode) throws SQLException {
		Room room = null;
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM Rooms WHERE hotel_name= ? AND hotel_zipcode = ?");
		ps.setInt(1, room_id);
		ps.setString(2,hotel_name);
		ps.setInt(3,hotel_zipcode);
		ResultSet rset = ps.executeQuery();

		while(rset.next()) {
			int id = rset.getInt("id");
		 	room = new Room(
				rset.getInt("size"),
				rset.getInt("bed_count"),
				rset.getInt("price"),
				getRoomTags(id)
			);
		}

		if (room == null) {
			System.out.println("Ekkert fannst");
		}

		return room;
	}

	// Notkun: getRoomFromHotel(room_id, hotel)
	// Skilar: Skilar herbergi með ákveðið room_id.
	//         Ath. þetta mun nota hótel hlut til að kalla á fallið með name og zipcode
	public static Room getRoomFromHotel( int room_id, Hotel hotel) throws SQLException {
		return getRoomFromHotel( room_id, hotel.name, hotel.zipcode);
	}

	public static void setRoomPrice(int new_price, Room room) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("UPDATE Rooms SET price = ? WHERE id = ?");

		ps.setInt(1, (int)new_price);
		ps.setInt(2, room.id);

		ps.executeUpdate();
	}

	public static void changeRoomPriceByAmount(double price_change, Room room) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("UPDATE Rooms SET price = ? WHERE id = ?");

		room.price += (int)price_change;

		ps.setInt(1, room.price);
		ps.setInt(2, room.id);

		ps.executeUpdate();
	}

	public static void changeRoomPriceByPercent(double percent, Room room) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("UPDATE Rooms SET price = ? WHERE id = ?");

		room.price = (int)(room.price * (1.0 + percent / 100.0));

		ps.setInt(1, room.price);
		ps.setInt(2, room.id);

		ps.executeUpdate();
	}

	public static void addHotel(Hotel hotel) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("INSERT INTO Hotels(name, rating, description, zipcode) VALUES(?, ?, ?, ?)");

		ps.setString(1, hotel.name);
		ps.setInt(2, hotel.rating);
		ps.setString(3, hotel.description);
		ps.setInt(4, hotel.zipcode);

		ps.executeUpdate();

		//tags
		if (hotel.tags != null) {
			ps = connection.prepareStatement("INSERT INTO Hotel_tags(hotel_name, hotel_zipcode, tag_name) VALUES(?, ?, ?)");

			for(String tag : hotel.tags) {
				ps.setString(1, hotel.name);
				ps.setInt(2, hotel.zipcode);
				ps.setString(3, tag);

				ps.executeUpdate();
			}
		}

		if (hotel.rooms != null) {
			addRoomsToHotel(hotel.rooms, hotel.name, hotel.zipcode);
		}
	}

	public static void addHotels(ArrayList<Hotel> hotels) throws SQLException {
		for(Hotel h : hotels) {
			addHotel(h);
		}
	}

	public static void addRoomToHotel(Room room, String hotel_name, int hotel_zipcode) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("INSERT INTO Rooms(id, hotel_name, hotel_zipcode, size, price, bed_count) VALUES(?, ?, ?, ?, ?, ?)");

		ps.setInt(1, room.id);
		ps.setString(2, hotel_name);
		ps.setInt(3, hotel_zipcode);
		ps.setInt(4, room.size);
		ps.setInt(5, room.price);
		ps.setInt(4, room.bed_count);

		ps.executeUpdate();
	}

	public static void addRoomsToHotel(ArrayList<Room> rooms, String hotel_name, int hotel_zipcode) throws SQLException {
		for(Room r : rooms) {
			addRoomToHotel(r, hotel_name, hotel_zipcode);
		}
	}

	public static void addTagToHotel(String hotel_name, int hotel_zipcode, String tag) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("INSERT INTO Hotel_tags(hotel_name, hotel_zipcode, tag_name) VALUES(?, ?, ?)");

		ps.setString(1, hotel_name);
		ps.setInt(2, hotel_zipcode);
		ps.setString(3, tag);

		ps.executeUpdate();
	}

	public static void bookRoom(int room_id, int user_id, long start_date, long end_date) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("INSERT INTO Bookings(user_id, room_id, start_date, end_date, confirmed) VALUES(?, ?, ?, ?, ?)");

		ps.setInt(1, user_id);
		ps.setInt(2, room_id);
		ps.setLong(3, start_date);
		ps.setLong(4, end_date);
		ps.setBoolean(5, false); // Bókanir eru alltaf fyrst óstaðfestar.

		ps.executeUpdate();
	}


	public static ArrayList<Hotel> search(SearchQuery query) throws SQLException {

		int tala = 0;
		String hotelString = "SELECT * FROM Hotels WHERE ";
		for( Integer i: query.zipcodes) {
			if (tala > 0) hotelString += "OR ";

			hotelString += "zipcode = " + i + " ";
			tala++;
		}

		if (query.rating_min != Integer.MIN_VALUE) {
			if (tala > 0) hotelString += "AND ";

			hotelString += "rating >= " + query.rating_min + " ";
			tala++;
		}

		if (query.rating_max != Integer.MAX_VALUE) {
			if (tala > 0) hotelString += "AND ";

			hotelString += "rating <= " + query.rating_max + " ";
			tala++;
		}

		// Gera Query fyrir herbergin
		//
		// YIKES! Þetta er ekki fallegt
		// Sérstaklega þessi skeyting strengja :O
		//
		// Betra kannski að nota PreparedStatement?
		String roomString = "";

		if (query.price_min != Integer.MIN_VALUE) {
			roomString += "AND price >= " + query.price_min + " ";
		}

		if (query.price_max != Integer.MAX_VALUE) {
			roomString += "AND price <= " + query.price_max + " ";
		}

		if (query.size_min != Integer.MIN_VALUE) {
			roomString += "AND size >= " + query.size_min + " ";
		}

		if (query.size_max != Integer.MAX_VALUE) {
			roomString += "AND size <= " + query.size_max + " ";
		}

		ArrayList<Hotel> hotels = getHotelsWithQuery(hotelString, roomString);
		return hotels;
	}

	public static ArrayList<Hotel> getHotelsWithQuery(String hotelQuery, String roomQuery) throws SQLException {
		ArrayList<Hotel> listOfHotels = new ArrayList<Hotel>();
	  ResultSet rset = sqlStatement.executeQuery(hotelQuery);

		while(rset.next()) {

			String hotel_name = rset.getString("name");
			int hotel_zipcode = rset.getInt("zipcode");

		 	Hotel hotel = new Hotel(
				hotel_name,
				rset.getInt("rating"),
				rset.getString("description"),
				hotel_zipcode,
				getHotelTags(hotel_name, hotel_zipcode),
				getRoomsWithQuery(hotel_name, hotel_zipcode, roomQuery)
			);

			listOfHotels.add(hotel);
		}
		return listOfHotels;

	}

	public static ArrayList<Room> getRoomsWithQuery(String hotel_name, int hotel_zipcode, String roomQuery) throws SQLException {
		ArrayList<Room> listOfRooms = new ArrayList<Room>();

		String query = "SELECT * FROM Rooms WHERE hotel_name= \"" + hotel_name + "\" AND hotel_zipcode = " + hotel_zipcode + " ";
		roomQuery = query + roomQuery;

		ResultSet rset = sqlStatement.executeQuery(roomQuery);
		int room_id = rset.getInt("id");

		while(rset.next()) {
			Room room = new Room(
				rset.getInt("size"),
				rset.getInt("bed_count"),
				rset.getInt("price"),
				getRoomTags(room_id)
			);

			listOfRooms.add(room);
		}

		return listOfRooms;
	}


	// Notkun: isRoomFree(r,s,e)
	// Fyrir:  r er herbergi, s,e eru upphafs- og lokadags.
	// Skilar: satt e.o.a.e. herbergi er laust þetta tímabil.
	public static boolean isRoomFree(Room r, int start_date, int end_date) throws SQLException {

		PreparedStatement ps = connection.prepareStatement("SELECT COOUNT(*) FROM Bookings WHERE start_date < ? AND ? > end_date AND id = ?");
		ps.setInt(1, start_date); // Opna bilið ]s;e[
		ps.setInt(2, start_date);
		ps.setInt(3, r.id);
		ResultSet rs = ps.executeQuery();
		return rs.getInt(0) == 0;
	}

	// Notkun: confirmBooking(b)
	// Fyrir:  b er bókun.
	// Eftir:  b hefur verið staðfest í gagnagrunni.
	public static void confirmBooking(Booking b) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("UPDATE Bookings SET confirmed = T WHERE id = ?");
		ps.setInt(1, b.id);
		ps.executeUpdate();
	}

	public static void deleteHotel( Hotel h ) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("DELETE FROM Hotels WHERE name = ? AND zipcode = ?");
		ps.setString(1, h.name);
		ps.setInt(2, h.zipcode);

		ps.executeUpdate();
	}

	public static void deleteRoom( Room r ) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("DELETE FROM Rooms WHERE id = ?");
		ps.setInt(1, r.id);

		ps.executeUpdate();
	}

	public static int getRoomCount() throws SQLException {
			int result = 0;

			ResultSet rset = sqlStatement.executeQuery("select count(*) from Rooms;");
			result = rset.getInt(1);

			return result;
		}

}
