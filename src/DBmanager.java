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
		}
	}

	// Notkun: getAllHotels()
	// Skilar: ArrayList af öllum hótelum í gagnagrunni.
	public static ArrayList<Hotel> getAllHotels() throws SQLException {
		ArrayList<Hotel> listOfHotels = new ArrayList<Hotel>();
		ResultSet rset = sqlStatement.executeQuery("SELECT * FROM Hotels");
		while(rset.next()) {
			ArrayList<Room> roomsInAHotel = getRoomFromHotel(rset.getString("name"), rset.getInt("zipcode"));

			Hotel h = new Hotel(
				rset.getString("name"),
				rset.getInt("rating"),
				rset.getString("description"),
				rset.getInt("zipcode"),
				null,
				roomsInAHotel
			);

			listOfHotels.add(h);
		}
		return listOfHotels;
	}

	// Notkun: getRoomFromHotel( hotelname, zip)
	// Skilar: ArrayList af herbergjum sem eru í viðeigandi hóteli.
	//         Ath. hotelname, zip er lykill.
	public static ArrayList<Room> getRoomFromHotel( String hotel_name, int hotel_zipcode) throws SQLException {
		ArrayList<Room> listOfRooms = new ArrayList<Room>();
		String query = "SELECT * FROM Rooms WHERE hotel_name= \"" + hotel_name +
			"\" AND hotel_zipcode=" + hotel_zipcode + ";";

		ResultSet rset = sqlStatement.executeQuery(query);

		while(rset.next()) {
			Room room = new Room(
				rset.getInt("id"),
				rset.getInt("size"),
				0,
				false,
				10000
			);
			listOfRooms.add(room);
		}
		return listOfRooms;
	}




	public static void setRoomPrice(double price, ArrayList<Room> rooms) {
		//...
	}

	public static void changeRoomPriceByAmount(double price_change, ArrayList<Room> rooms) {
		//...
	}

	public static void changeRoomPriceByPercent(double percent, ArrayList<Room> rooms) {

	}

	public static void reserveRoom(int roomID) {

	}

	public static Hotel getHotel(String hotelName, String hotelZip) {
		return null;
	}

	public static void addHotel(Hotel hotel) {
		System.out.println("A hotel has been added to the database. <Unimplemented>");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println( "Something didn't work properly.");
		}
	}

	public static void addHotels(ArrayList<Hotel> hotels) {
		for(Hotel h : hotels) {
			addHotel(h);
		}
	}

	public static void addRoomToHotel(Room room, Hotel hotel) {
		//:)
	}

	public static void addRoomsToHotel(ArrayList<Room> rooms, Hotel hotel) {
		for(Room r : rooms) {
			addRoomToHotel(r, hotel);
		}
	}

	public static ArrayList<Room> getHotelRooms(Hotel hotel) {
		return new ArrayList<Room>();
	}

	public static ArrayList<Hotel> search(SearchQuery query) {
		//sql query...
		return new ArrayList<Hotel>();
	}
}
