package hotelAPI;

import java.util.HashMap;
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

	public static boolean bookRoom(Room room, User user, long start_date, long end_date) throws SQLException {
		if( !isRoomFree(room, start_date, end_date) ) return false;

		PreparedStatement ps = connection.prepareStatement("INSERT INTO Bookings(user_email, room_id, start_date, end_date, confirmed) VALUES(?, ?, ?, ?, ?)");

		ps.setString(1, user.email);
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

	// Notkun: bookings = getBookings(u)
	// Fyrir:  u er notandi.
	// Eftir:  bookings er HashMap<Integer,Booking> af bókunum með bókunarnúmer.
	public static HashMap<Integer,Booking> getBookings(User user) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM Bookings WHERE user_email = ?");
		ps.setString(1,user.email);

		ResultSet rs = ps.executeQuery();

		HashMap<Integer,Booking> bookings = new HashMap<Integer,Booking>();
		while(rs.next()) {
			Room r = getRoomById(rs.getInt("room_id"));
			long s = rs.getLong("start_date");
			long e = rs.getLong("end_date");
			Booking b = new Booking(r,s,e);

			int id = rs.getInt("id");
			
			bookings.put(id,b);
		}

		return bookings;
	}
	
	private static Room getRoomById(int id) throws SQLException {
		//PreparedStatement ps = connection.prepareStatement("SELECT * FROM Rooms WHERE id = ?");
		return null;
	}

	// Notkun: confirmBooking(b)
	// Fyrir:  b er bókun.
	// Eftir:  b hefur verið staðfest í gagnagrunni.
	public static void confirmBooking(Booking b) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("UPDATE Bookings SET confirmed = TRUE WHERE id = ?");
		ps.setInt(1, b.id);
		ps.executeUpdate();
	}

	// Notkun: cancelBooking(b)
	// Fyrir:  b er bókun.
	// Eftir:  b hefur verið hætt við í gagnagrunni.
	public static void cancelBooking(Booking booking) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("DELETE FROM Bookings WHERE id = ?");
		ps.setInt(1, booking.id);
		ps.executeUpdate();
	}
	
	// Notkun: deleteHotel(h)
	// Fyrir:  h er hótel.
	// Eftir:  h hefur verið eytt úr gagnagrunnni.
	public static void deleteHotel( Hotel h ) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("DELETE FROM Hotels WHERE name = ? AND city = ?");
		ps.setString(1, h.name);
		ps.setString(2, h.city);

		ps.executeUpdate();
	}

	// Notkun: deleteRoom(r)
	// Fyrir:  r er herbergi.
	// Eftir:  r hefur verið eytt úr gagnagrunnni.
	public static void deleteRoom( Room r ) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("DELETE FROM Rooms WHERE id = ?");
		ps.setInt(1, r.id);

		ps.executeUpdate();
	}


}
