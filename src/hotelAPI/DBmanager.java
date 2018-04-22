package hotelAPI;

import java.util.HashMap;
import java.util.ArrayList;
import java.sql.*;

public class DBmanager {

	static Connection connection;
	static Statement sqlStatement;


	// Notkun: init()
	//         Verður að vera kallað áður en önnur föll eru notuð.
	// Eftir:  Connection og Statement hafa verið upphafsstillt
	//         svo unnt er að framkvæma SQL aðgerðir.
	public static void init() throws SQLException {
		if(connection == null) {
			try {
				Class.forName("org.sqlite.JDBC");
				connection = DriverManager.getConnection("jdbc:sqlite:hotels.db");
				sqlStatement = connection.createStatement();

			} catch(ClassNotFoundException e) {
				System.out.println("Couldn't find jdbc jar file.");
			}
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


	// Notkun: hotelSearch(c,min,max,start,end)
	// Fyrir:  c er leitarstrengur fyrir einhverja borg (nafn hótels eða borg),
	//         min og max eru lægstu og hæstu stjörnur sem hótel má hafa,
	//         start og end eru byrjunar og enda dagsetningar sem leita á efir.
	// Skilar: lista af hótelum sem uppfylla leitarskilyrði.
	public static ArrayList<Hotel> hotelSearch(String hotel_city_or_name, int min_rating, int max_rating, long start_date, long end_date) throws SQLException {
		ArrayList<Hotel> result = new ArrayList<Hotel>();

		if(!hotel_city_or_name.equals("") && hotel_city_or_name.charAt(0) == Character.toUpperCase(hotel_city_or_name.charAt(0))) {
			sqlStatement.execute("PRAGMA case_sensitive_like = true");
		}else {
			sqlStatement.execute("PRAGMA case_sensitive_like = false");
		}

		PreparedStatement ps = connection.prepareStatement("SELECT * FROM Hotels AS h, Rooms AS r WHERE (h.name=r.hotel_name AND h.city=r.hotel_city) AND (city LIKE ? OR name LIKE ?) AND rating >= ? AND rating <= ? AND r.id <> (SELECT room_id FROM Rooms AS r1, Bookings AS b1 WHERE r1.id = b1.room_id AND b1.start_date >= ? AND b1.end_date <= ?)");

		ps.setString(1, "%" + hotel_city_or_name + "%");
		ps.setString(2, "%" + hotel_city_or_name + "%");
		ps.setInt(3, min_rating);
		ps.setInt(4, max_rating);
		ps.setLong(5, start_date);
		ps.setLong(6, end_date);

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

	// Notkun: getHotel(name,city);
	// Fyrir:  name og city eru nákvæmir strengir til að leita eftir.
	// Skilar: fyrsta hótelið sem finnst.
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

	// Notkun: getHotelsByName(leit);
	// Fyrir:  leit er einhver leitarstrengur.
	// Skilar: listi af Hótelum sem innihalda "leit".
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

	// Notkun: getRoomFromHotel( room_id, hotel_name, hotel_city)
	// Fyrir:  room_id er auðkenni herbergis, hotel_name og hotel_city er nákvæmt nafn og borg hótels
	// Skilar: Skilar hótelherbergi með ákveðið id.
	private static Room getRoomFromHotel( int room_id, String hotel_name, String hotel_city) throws SQLException {
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

	// Notkun: bookRoom(r,u,s,h)
	// Fyrir:  r er herbergi sem á að bóka,
	//         u er notandi,
	//         s og h eru byrjunar og lokadagsetningar.
	// Eftir:  r hefur verið bókað frá s til h í nafni u.
	//         Skilar satt ef herbergið var bókað,
	//         ósatt ef herbergið var ekki laust.
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

		PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) AS total FROM Bookings WHERE start_date < ? AND ? > end_date AND room_id = ?");
		ps.setLong(1, start_date); // Opna bilið ]s;e[
		ps.setLong(2, end_date);
		ps.setInt(3, r.id);
		ResultSet rs = ps.executeQuery();
		return rs.getInt("total") == 0;
	}

	// Notkun: getBookings(u)
	// Fyrir:  u er notandi.
	// Skilar: HashMap<Integer,Booking> af <bókunarnúmerum,bókunum>.
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

	// Notkun: getRoomById(i)
	// Fyrir:  i er bókunarnúmer einhverrar bókunnar.
	// Skilar: Room hlut sem fylgir bókunarnúmerinu i.
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

	// Notkun: addUser(u)
	// Fyrir:  u er notandi.
	// Eftir:  u hefur verið bætt í gagnagrunn.
	protected static void addUser(User user) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("INSERT INTO Users(name,email,is_admin) VALUES(?,?,?)");
		ps.setString(1,user.name);
		ps.setString(2,user.email);
		ps.setBoolean(3,false);
		ps.executeUpdate();
	}

	// Notkun: isUserAdmin(u)
	// Fyrir:  u er notandi.
	// Skilar: Satt e.o.a.e. u er admin í gagnagrunni.
	protected static boolean isUserAdmin(User user) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) AS total FROM Users WHERE name = ? AND email = ? AND is_admin = ?");
	
		ps.setString(1, user.name);
		ps.setString(2, user.email);
		ps.setBoolean(3, true);

		ResultSet rs = ps.executeQuery();

		return rs.getInt("total") == 0;
	}


	// Notkun: setUserPriveleges(u,a)
	// Fyrir:  u er notandi, a er boolean gildi.
	// Eftir:  u er admin ef a, en annars ekki.
	protected static void setUserPriveleges(User user, boolean isAdmin) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("UPDATE User SET is_admin = ? WHERE name = ? AND email = ?");
	
		ps.setBoolean(1, isAdmin);
		ps.setString(2, user.name);
		ps.setString(3, user.email);

		ps.executeUpdate();
	}


	// Notkun: getUser(n,e)
	// Fyrir:  n er nafn, e er netfang.
	// Skilar: User hlut sem svarar til n og e, null ef ekki til.
	protected static User getUser(String name, String email) throws SQLException {
		User user = null;

		PreparedStatement ps = connection.prepareStatement("SELECT * FROM Users WHERE name = ? AND email = ?");

		ps.setString(1, name);
		ps.setString(2, email);

		ResultSet rs = ps.executeQuery();

		if(rs.next()) {
			user = new User(rs.getString("name"), rs.getString("email"));
		}

		return user;
	}

	// Notkun: deleteUser(u)
	// Fyrir:  u er netfang notandans.
	// Eftir:  Notandi u hefur verið eytt úr gagnagrunn.
	protected static void deleteUser(String email) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("DELETE FROM Users WHERE email = ?");

		ps.setString(1, email);
		ps.executeUpdate();
	}
}
