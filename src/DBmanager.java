import java.util.ArrayList;

public class DBmanager {
	
	Connection connection;
	Statement sqlStatement;


///Krisi er ad leika ser
	public static void init() throws SQLException {
		Class.forName("org.sqlite.JDBC");
		connection = DriverManager.getConnection("jdbc:sqlite:testdb.db");
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
		//Hotel(String name, int rating, String description, int zipcode, ArrayList<String> tags, ArrayList<Room> rooms)
		return new Hotel(null, 0, null, 0, null, null);
	}

	public static void addHotel(Hotel hotel) {
		System.out.println("A hotel has been added to the database. <Unimplemented>");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println( "WHAT????");
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
