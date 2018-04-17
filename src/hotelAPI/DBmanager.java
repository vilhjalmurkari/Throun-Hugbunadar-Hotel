package hotelAPI;

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
			connection = DriverManager.getConnection("jdbc:sqlite:hotels.db");
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
			String hotel_city = rset.getString("city");

			Hotel h = new Hotel(
				hotel_name,
				rset.getInt("rating"),
				rset.getString("description"),
				hotel_city,
				null,
				null
			);

			listOfHotels.add(h);
		}

		for(Hotel h : listOfHotels) {
			h.tags = getHotelTags(h.name, h.city);
			h.rooms = getRoomsFromHotel(h.name, h.city);
		}

		return listOfHotels;
	}

	// Notkun: hotels = search(leit);
	// Fyrir:  leit er einhver leitarstrengur.
	// Eftir:  hotels er ArrayList af Hótelum sem ??????????????????????
	//
	// ???????????
	//
	//hotel_name og hotel_city mega vera null, annaðhvort eða bæði. Ef strengirnir er null breytast þeir eiginlega í wildcard(*)
	public static ArrayList<Hotel> hotelSearch(String hotel_city, int min_rating, int max_rating) throws SQLException {
		ArrayList<Hotel> result = new ArrayList<Hotel>();

		sqlStatement.execute("PRAGMA case_sensitive_like = true");

		PreparedStatement ps = connection.prepareStatement("SELECT * FROM Hotels WHERE city LIKE ? AND rating >= ? AND rating <= ?");
		ps.setString(1, "%" + hotel_city + "%");
		ps.setInt(2, min_rating);
		ps.setInt(3, max_rating);

		ResultSet rset = ps.executeQuery();

		while(rset.next()) {
			Hotel h = new Hotel(
				rset.getString("name"),
				rset.getInt("rating"),
				rset.getString("description"),
				rset.getString("city"),
				null,
				null
			);

			result.add(h);
		}
		
		for(Hotel h : result) {
			h.tags = getHotelTags(h.name, h.city);
			h.rooms = getRoomsFromHotel(h.name, h.city);
		}

		return result;
	}

	// Notkun: hotel = getHotel(name,city);
	// Fyrir:  name og city eru nákvæmir strengir til að leita eftir.
	// Eftir:  hotel er fyrsta hótelið sem finnst.
	public static Hotel getHotel(String hotel_name, String hotel_city) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM Hotels WHERE name= ? AND city = ?");
		ps.setString(1,hotel_name);
		ps.setString(2,hotel_city);
		ResultSet rset = ps.executeQuery();


		while(rset.next()) {

			String name = rset.getString("name");
			String city = rset.getString("city");

		 	Hotel hotel = new Hotel(
				name,
				rset.getInt("rating"),
				rset.getString("description"),
				city,
				getHotelTags(name, city),
				getRoomsFromHotel(name, city)
			);

			return hotel;
		}

		System.out.println("Ekkert fannst");

		return null;
	}

	// Notkun: hotels = getHotelsByName(leit);
	// Fyrir:  leit er einhver leitarstrengur.
	// Eftir:  hotels er ArrayList af Hótelum sem innihalda "leit".
	
	public static ArrayList<Hotel> getHotelsByName(String hotel_name) throws SQLException {
		ArrayList<Hotel> listOfHotels = new ArrayList<Hotel>();
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM Hotels WHERE name LIKE ?");
		ps.setString(1, "%" + hotel_name + "%");
		ResultSet rset = ps.executeQuery();

		while(rset.next()) {
			Hotel h = new Hotel(
				rset.getString("name"),
				rset.getInt("rating"),
				rset.getString("description"),
				rset.getString("city"),
				null,
				null
			);

			listOfHotels.add(h);
		}
		
		// Could be terribly slow.
		for(Hotel h : listOfHotels) {
			h.tags = getHotelTags(h.name, h.city);
			h.rooms = getRoomsFromHotel(h.name, h.city);
		}

		return listOfHotels;
	}

	private static ArrayList<String> getHotelTags(String hotel_name, String hotel_city) throws SQLException {
		ArrayList<String> result = new ArrayList<String>();
		PreparedStatement ps = connection.prepareStatement("SELECT tag_name FROM Hotel_tags WHERE hotel_name = ? AND hotel_city = ?");
		ps.setString(1,hotel_name);
		ps.setString(2,hotel_city);
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

	// Notkun: getRoomsFromHotel( hotel_name, hotel_city)
	// Skilar: ArrayList af herbergjum sem eru í viðeigandi hóteli.
	//         Ath. hotel_name, hotel_city er lykill.
	public static ArrayList<Room> getRoomsFromHotel(String hotel_name, String hotel_city) throws SQLException {
		ArrayList<Room> listOfRooms = new ArrayList<Room>();
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM Rooms WHERE hotel_name= ? AND hotel_city = ?");
		ps.setString(1,hotel_name);
		ps.setString(2,hotel_city);
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

	// ER þetta fall GAGNLEGT?
	//
	// Notkun: getRoomFromHotel(room_id, hotel)
	// Fyrir:  room_id er auðkenni herbergis. hotel er hótel hlutur.
	// Skilar: Skilar herbergi með ákveðið room_id.
	public static Room getRoomFromHotel( int room_id, Hotel hotel) throws SQLException {
		return getRoomFromHotel( room_id, hotel.name, hotel.city);
	}

	// Notkun: getRoomFromHotel( room_id, hotel_name, hotel_city)
	// Fyrir:  room_id er auðkenni herbergis, hotel_name og hotel_city er nákvæmt nafn og borg hótels
	// Skilar: Skilar hótelherbergi með ákveðið id.
	public static Room getRoomFromHotel( int room_id, String hotel_name, String hotel_city) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM Rooms WHERE id = ? AND hotel_name = ? AND hotel_city = ?");
		ps.setInt(1, room_id);
		ps.setString(2,hotel_name);
		ps.setString(3,hotel_city);
		ResultSet rset = ps.executeQuery();

		while(rset.next()) {
			int id = rset.getInt("id");
		 	Room room = new Room(
				rset.getInt("size"),
				rset.getInt("bed_count"),
				rset.getInt("price"),
				getRoomTags(id)
			);
			return room;
		}

		System.out.println("Ekkert fannst");

		return null;
	}

	// Notkun: setRoomPrice(price,rooms)
	// Fyrir:  price er eitthvert nýtt verð, rooms er listi herbergja sem breyta á verði í.
	// Eftir:  Verðum rooms hefur verið breytt í hlut og gagnagrunni.
	public static void setRoomPrice(int new_price, ArrayList<Room> rooms) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("UPDATE Rooms SET price = ? WHERE id = ?");

		ps.setInt(1, new_price);
		for( Room r : rooms ) {
			ps.setInt(2, r.id);
			ps.executeUpdate();
		}

	}

	// Notkun: changeRoomPriceByAmount(delta, room);
	// Fyrir:  delta er hækkun/lækkun í verði, room er herbergishlutur.
	// Eftir:  room hefur hækkað um delta í verði í hlut og gagnagrunni.
	public static void changeRoomPriceByAmount(int price_change, Room room) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("UPDATE Rooms SET price = ? WHERE id = ?");

		room.price += price_change;

		ps.setInt(1, room.price);
		ps.setInt(2, room.id);

		ps.executeUpdate();
	}


	// Notkun: changeRoomPriceByPercent(percent, room);
	// Fyrir:  percent er prósentuleg hækkun í verði, room er herbergishlutur.
	// Eftir:  room hefur hækkað um percent prósent í verði í hlut og gagnagrunni.
	public static void changeRoomPriceByPercent(double percent, Room room) throws SQLException {
		int delta = (int)(room.price * percent);
		changeRoomPriceByAmount(delta, room);
	}

	// Notkun: addHotel(hotel)
	// Fyrir:  hotel er Hotel hlutur.
	// Eftir:  gögnum hotel hefur verið bætt við í gagnagrunni.
	public static void addHotel(Hotel hotel) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("INSERT INTO Hotels(name, rating, description, city) VALUES(?, ?, ?, ?)");

		ps.setString(1, hotel.name);
		ps.setInt(2, hotel.rating);
		ps.setString(3, hotel.description);
		ps.setString(4, hotel.city);

		ps.executeUpdate();

		//tags
		if (hotel.tags != null) {
			ps = connection.prepareStatement("INSERT INTO Hotel_tags(hotel_name, hotel_city, tag_name) VALUES(?, ?, ?)");

			for(String tag : hotel.tags) {
				ps.setString(1, hotel.name);
				ps.setString(2, hotel.city);
				ps.setString(3, tag);

				ps.executeUpdate();
			}
		}

		if (hotel.rooms != null) {
			
			addRoomsToHotel(hotel.rooms, hotel.name, hotel.city);
		}
	}


	// Notkun: addHotels(hotels)
	// Fyrir:  hotel er listi af Hotel hlutum.
	// Eftir:  Öll gögn hótela í hotels hefur verið bætt við gagnagrunni.
	public static void addHotels(ArrayList<Hotel> hotels) throws SQLException {
		for(Hotel h : hotels) {
			addHotel(h);
		}
	}

	// Notkun: addRoomToHotel(room,hotel,city)
	// Fyrir:  room er herbergishlutur, hotel og city er nákvæmt nafn og borg hótels.
	// Eftir:  Gögnum room hefur verið bætt við í gagnagrunn á viðeigandi stað.
	public static void addRoomToHotel(Room room, String hotel_name, String hotel_city) throws SQLException {
		ArrayList<Room> al = new ArrayList<Room>();
		al.add(room);
		addRoomsToHotel(al, hotel_name, hotel_city);
	}

	// Notkun: addRoomsToHotel(rooms,hotel,city)
	// Fyrir:  rooms er ArrayList herbergja, hotel og city er nákvæmt nafn og borg hótels.
	// Eftir:  Gögnum herbergja í rooms hefur verið bætt við í gagnagrunn á viðeigandi stað.
	public static void addRoomsToHotel(ArrayList<Room> rooms, String hotel_name, String hotel_city) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("INSERT INTO Rooms(id, hotel_name, hotel_city, size, price, bed_count) VALUES(?, ?, ?, ?, ?, ?)");

		ps.setString(2, hotel_name);
		ps.setString(3, hotel_city);

		for(Room room : rooms) {
			ps.setInt(1, room.id);
			ps.setInt(4, room.size);
			ps.setInt(5, room.price);
			ps.setInt(4, room.bed_count);
			ps.executeUpdate();
		}
	}

	protected static void addTagToHotel(String hotel_name, String hotel_city, String tag) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("INSERT INTO Hotel_tags(hotel_name, hotel_city, tag_name) VALUES(?, ?, ?)");

		ps.setString(1, hotel_name);
		ps.setString(2, hotel_city);
		ps.setString(3, tag);

		ps.executeUpdate();
	}
	// Þetta ætti ekki að vera þörf. Ef Bookings er notað á ekki að nota þetta
	public static boolean bookRoom(Room room, User user, long start_date, long end_date) throws SQLException {
		assert( isRoomFree(room, start_date, end_date) );

		PreparedStatement ps = connection.prepareStatement("INSERT INTO Bookings(user_id, room_id, start_date, end_date, confirmed) VALUES(?, ?, ?, ?, ?)");

		ps.setInt(1, user.id);
		ps.setInt(2, room.id);
		ps.setLong(3, start_date);
		ps.setLong(4, end_date);
		ps.setBoolean(5, false); // Bókanir eru alltaf fyrst óstaðfestar.

		ps.executeUpdate();
		return true;
	}

	// Notkun: isRoomFree(r,s,e)
	// Fyrir:  r er herbergi, s,e eru upphafs- og lokadags.
	// Skilar: satt e.o.a.e. herbergi er laust þetta tímabil.
	public static boolean isRoomFree(Room r, long start_date, long end_date) throws SQLException {

		PreparedStatement ps = connection.prepareStatement("SELECT COOUNT(*) FROM Bookings WHERE start_date < ? AND ? > end_date AND id = ?");
		ps.setLong(1, start_date); // Opna bilið ]s;e[
		ps.setLong(2, start_date);
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
		PreparedStatement ps = connection.prepareStatement("DELETE FROM Hotels WHERE name = ? AND city = ?");
		ps.setString(1, h.name);
		ps.setString(2, h.city);

		ps.executeUpdate();
	}

	public static void deleteRoom( Room r ) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("DELETE FROM Rooms WHERE id = ?");
		ps.setInt(1, r.id);

		ps.executeUpdate();
	}

	/*
	public static ArrayList<Hotel> getHotelsByCityAndRating(String hotel_city, int hotel_rating) throws SQLException {
		ArrayList<Hotel> listOfHotels = new ArrayList<Hotel>();
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM Hotels WHERE city LIKE ? AND rating >= ?");
		ps.setString(1, "%" + hotel_city + "%");
		ps.setInt(2, hotel_rating);
		ResultSet rset = ps.executeQuery();

		while(rset.next()) {
			Hotel h = new Hotel(
				rset.getString("name"),
				rset.getInt("rating"),
				rset.getString("description"),
				rset.getString("city"),
				null,
				null
			);

			listOfHotels.add(h);
		}

		for(Hotel h : listOfHotels) {
			h.tags = getHotelTags(h.name, h.city);
			h.rooms = getRoomsFromHotel(h.name, h.city);
		}

		return listOfHotels;
	}
	*/

	/*
	Delete?

	private static ArrayList<Hotel> getHotelsWithQuery(String hotelQuery, String roomQuery) throws SQLException {
		ArrayList<Hotel> listOfHotels = new ArrayList<Hotel>();
		ResultSet rset = sqlStatement.executeQuery(hotelQuery);

		while(rset.next()) {

			String hotel_name = rset.getString("name");
			String hotel_city = rset.getString("city");

		 	Hotel hotel = new Hotel(
				hotel_name,
				rset.getInt("rating"),
				rset.getString("description"),
				hotel_city,
				getHotelTags(hotel_name, hotel_city),
				getRoomsWithQuery(hotel_name, hotel_city, roomQuery)
			);

			listOfHotels.add(hotel);
		}
		return listOfHotels;

	}
	*/
	
	/*
	Hvað er þetta fyrir? Er þetta nytsamlegt?
	Delete?

	private static ArrayList<Room> getRoomsWithQuery(String hotel_name, String hotel_city, String roomQuery) throws SQLException {
		ArrayList<Room> listOfRooms = new ArrayList<Room>();

		String query = "SELECT * FROM Rooms WHERE hotel_name= \"" + hotel_name + "\" AND hotel_city = " + hotel_city + " ";
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
	*/

	/*
	Óþarfi þetta fall

	private static int getRoomCount() throws SQLException {
		int result = 0;

		ResultSet rset = sqlStatement.executeQuery("select count(*) from Rooms;");
		result = rset.getInt(1);

		return result;
	}
	*/

	/* 
	Er þetta fall nauðsynlegt? Mætti ekki alveg sleppa þessu í API?

	public static void addTagToHotel(Hotel hotel, String tag) throws SQLException {
		addTagToHotel(hotel.name, hotel.city, tag);
	}
	*/

	/*
	Delete?

	public static ArrayList<Hotel> search_old(SearchQuery query) throws SQLException {

		int tala = 0;
		String hotelString = "SELECT * FROM Hotels WHERE ";
		for( String c: query.cities) {
			if (tala > 0) hotelString += "OR ";

			hotelString += "city LIKE \"%" + c + "%\" ";
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
	*/

}