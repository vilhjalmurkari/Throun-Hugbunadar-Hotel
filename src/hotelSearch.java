//keyrsla: java -cp .:sqlite-jdbc....jar hotelSearch
import java.sql.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.Scanner;

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

class DBmanager {

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
//NOTE: það vantar einhver svona dbController klasa(held að minnstakosti að það sé ætlast til þess)

class hotelSearch {
	enum programState {
		MENU,
		HOTEL_REG,
		ROOM_REG,
		CHANGE_ROOM_PRICE
	}

	enum inputType {
		INTEGER,
		FLOAT,
		STRING
		//einhverjar fleiri týpur sem við viljum tryggja að séu á réttu format-i
	}

	private programState state;
	private Scanner input;
	private String inputString;
	private boolean possibleMenuCommand;

	public hotelSearch(programState startState, Scanner inputStream) {
		state = startState;
		input = inputStream;
		inputString = "";
		possibleMenuCommand = false;
	}

	private void clearScreen() {
		//ansi escape codes
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	/*private String checkAndAcceptInput(Scanner scanner, inputType type, String message) {
		String input = "";
		boolean accepted = true;
		boolean showMessage = false;

		do {
			input = scanner.next();
			accepted = true;

			//hér reynum við að afsanna að inntakið sé af þeirri gerð sem við viljum að það sé af
			switch(type) {
				case STRING:
				break;

				case INTEGER:
					for(char c : input.toCharArray()) {
						if(c < '0' || c > '9') {
							accepted = false;
							break;
						}
					}

					if(!accepted) {
						System.out.println();
						System.out.println(message);
						System.out.println();
					}
				break;
			}
		}while(!accepted);

		return input;
	}*/

	// Side-effect free ;)
	private void displayMenuMessage() {
		switch(state) {
			case MENU:
				System.out.println("Velkomin í hótelleitarforrit 7H");
				System.out.println("vinsamlegast sláðu inn tölu sem samsvarast eftirfarandi valmöguleikum:");
				System.out.println();
				System.out.println("1): Skrá hótel");
				System.out.println("2): Skrá herbergi fyrir hótel");
				System.out.println("3): Breyta verði hótelherbergja");
			break;

			case HOTEL_REG:
				System.out.println("Sláðu inn viðeigandi upplýsingar um hótelið:");
			break;

			case ROOM_REG:
				System.out.println("hér getur þú skráð herbergi");
			break;

			case CHANGE_ROOM_PRICE:
				System.out.println("hér getur þú breytt verði á hótelherbergjum");
			break;
		}

		System.out.println();
		System.out.print("(h): Hætta ");
		if(state != programState.MENU) System.out.print("- (b) Til baka");
		System.out.println();
		System.out.println();
	}

	private boolean affirm(String stringToParse) {
		switch(stringToParse) {
			case "n":
			case "no":
			case "nei":
				return false;

			case "j":
			case "já":
			case "ja":
			case "y":
			case "yes":
				return true;

			default:
				System.out.println("túlkað sem nei");
				return false;
		}
	}

	// After:	A list of hotels has been added to the database.
	private void hotelRegInput() {
		ArrayList<Hotel> hotelBuffer = new ArrayList<Hotel>();
		clearScreen();

		for( boolean quitLoop = false ; !quitLoop ; ) {
			System.out.println("Skráðu nafn á nýju hóteli:");
			String newHotelName = input.next();

			System.out.println("Skráðu póstfang nýja hótelsins:");
			String newHotelZip = input.next();
			System.out.println(newHotelZip);

			System.out.println("Skráðu lýsingu á nýja hótelinu:");
			String newHotelDesription = input.next();

			System.out.println("Skráðu stjörnufjölda nýja hótelsins:");
			String newHotelRating = input.next();

			hotelBuffer.add(new Hotel(newHotelName,
										Integer.parseInt(newHotelRating),
									  newHotelDesription,
									  Integer.parseInt(newHotelZip),
									  null,
									  null));

			System.out.println();
			System.out.println("Vilt þú bæta við fleiri hótelum? (Y/N)");
			System.out.println();


			//athuga hvort við viljum skrá fleiri hótel
			this.inputString = input.next().toLowerCase();
			quitLoop = affirm(this.inputString);
		}

		DBmanager.addHotels(hotelBuffer);
		this.state = programState.MENU;
	}

	//public Room(int id, int size, int bed_count, boolean shower, int price)
	private void roomRegInput() {
		ArrayList<Room> roomBuffer = new ArrayList<Room>();
		String hotelName = "";
		String hotelZip = "";
		clearScreen();

		for( boolean quitLoop = false ; !quitLoop ; ) {
			System.out.println("Skráðu nafn hótelsins sem á að skrá herbergið fyrir:");
			hotelName = input.next();

			System.out.println("Skráðu póstfang hótelsins sem á að skrá herbergið fyrir:");
			hotelZip = input.next();
			System.out.println();

			System.out.println("Skráðu stærð á nýju hótelherbergi:");
			String newRoomSize = input.next();

			System.out.println("Skráðu fjölda rúma í nýja hótelherberginu:");
			String newRoomBedCount = input.next();

			System.out.println("Er sturta í hótelherberginu? (Y/N)");
			String newRoomHasShower = input.next();

			System.out.println("Sláðu inn verð á nýja hótelherberginu:");
			String newRoomPrice = input.next();

			roomBuffer.add(new Room(-1,
				 					Integer.parseInt(newRoomSize),
				 					Integer.parseInt(newRoomBedCount),
				 					Boolean.parseBoolean(newRoomHasShower),
				 					Integer.parseInt(newRoomPrice)));

			System.out.println();
			System.out.println("Vilt þú bæta við fleiri hótelherbergjum? (Y/N)");
			System.out.println();


			//athuga hvort við viljum skrá fleiri herbergi
			this.inputString = input.next().toLowerCase();
			quitLoop = affirm(this.inputString);
		}

		DBmanager.addRoomsToHotel(roomBuffer, DBmanager.getHotel(hotelName, hotelZip));
		this.state = programState.MENU;
	}

	private void changeHotelRoomPriceInput() {
		System.out.println("Skráðu nafn hótelsins:");
		String hotelName = input.next();

		System.out.println("Skráðu póstfang hótelsins:");
		String hotelZip = input.next();
		System.out.println();

		//TODO: prósenta o.fl.
		System.out.println("Skráðu inn verðbreytingu:");
		String priceChange = input.next();

		Hotel hotel = DBmanager.getHotel(hotelName, hotelZip);
		DBmanager.changeRoomPriceByAmount(Double.parseDouble(priceChange), DBmanager.getHotelRooms(hotel));

		this.state = programState.MENU;
	}

	// Before:	The program is in some state.
	// After:	The program is in a state specified by <inputString>
	private void menuInput() {
		this.inputString = input.next();

		switch(this.inputString.charAt(0)) {
			case '1':
				this.state = programState.HOTEL_REG;
			break;

			case '2':
				this.state = programState.ROOM_REG;
			break;

			case '3':
				this.state = programState.CHANGE_ROOM_PRICE;
			break;

			default:
 				this.possibleMenuCommand = true;
			break;
		}
	}

	// After:	The display has been changed to specified menu screen.
	private void navigationInput() {
		switch(this.inputString.charAt(0)) {
			case 'h':
				System.exit(0);
			break;

			case 'b':
				if(state != programState.MENU) {
					state = programState.MENU;
				}else {
					System.out.println();
					System.out.println("Skipun óþekkt, reynið aftur.");
					System.out.println();
				}
			break;

			default:
				System.out.println();
				System.out.println("Skipun óþekkt, reynið aftur.");
				System.out.println();
			break;
		}
	}

	//þetta gerir ekkert sérstakt eins og er.
	// Ekki satt.
	public static void main(String[] args) throws ClassNotFoundException {
		hotelSearch program = new hotelSearch(programState.MENU, new Scanner(System.in).useDelimiter("\n"));

		while(true) {
			program.clearScreen();
			program.displayMenuMessage();

			switch(program.state) {
				case MENU:
					program.menuInput();
				break;

				case HOTEL_REG:
					program.hotelRegInput();
				break;

				case ROOM_REG:
					program.roomRegInput();
				break;

				case CHANGE_ROOM_PRICE:
					program.changeHotelRoomPriceInput();
				break;
			}

			if(program.possibleMenuCommand) {
				program.navigationInput();
			}
		}

		/*
		stdInput.close();

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
		sqli
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
		*/
	}
}
