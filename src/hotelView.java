//keyrsla: java -cp .:sqlite-jdbc....jar hotelView
//keyrsla: java -cp .;sqlite-jdbc-3.18.0.jar hotelView
import java.sql.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.Scanner;

class hotelView {
	enum programState {
		MENU,
		HOTEL_REG,
		ROOM_REG,
		CHANGE_ROOM_PRICE
	}

	private programState state;
	private Scanner input;
	private String inputString;
	private boolean possibleMenuCommand;

	public hotelView(programState startState, Scanner inputStream) {
		state = startState;
		input = inputStream;
		inputString = "";
		possibleMenuCommand = false;
	}

	public static long parseNumber(String s) {
		long result = 0;
		boolean minus = false;

		if(s.charAt(0) == '-') {
			minus = true;
			s = s.substring(1, s.length());
		}

		for(char c : s.toCharArray()) {
			result = result * 10 + (int)(c - '0');
		}

		//return minus ? -result : result;
		if(minus) {
			return -result;
		}else {
			return result;
		}
	}

	private void clearScreen() {
		//ansi escape codes
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

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

	private boolean affirm(String input) {
		switch(input) {
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
	private void hotelRegInput() throws SQLException {
		ArrayList<Hotel> hotelBuffer = new ArrayList<Hotel>();
		clearScreen();

		for( boolean quitLoop = false ; !quitLoop ; ) {
			System.out.println("Skráðu nafn á nýju hóteli:");
			String newHotelName = input.next();
			System.out.println();

			System.out.println("Skráðu póstfang nýja hótelsins:");
			String newHotelZip = input.next();
			System.out.println();

			System.out.println("Skráðu lýsingu á nýja hótelinu:");
			String newHotelDesription = input.next();
			System.out.println();

			System.out.println("Skráðu stjörnufjölda nýja hótelsins:");
			String newHotelRating = input.next();
			System.out.println();

			hotelBuffer.add(new Hotel(newHotelName,
									  (int)parseNumber(newHotelRating),
									  newHotelDesription,
									  (int)parseNumber(newHotelZip),
									  null,
									  null));

			System.out.println();
			System.out.println("Vilt þú bæta við fleiri hótelum? (Y/N)");
			System.out.println();


			//athuga hvort við viljum skrá fleiri hótel
			this.inputString = input.next().toLowerCase();
			quitLoop = !affirm(this.inputString);
		}

		DBmanager.addHotels(hotelBuffer);
		this.state = programState.MENU;
	}

	//public Room(int id, int size, int bed_count, boolean shower, int price)
	private void roomRegInput() throws SQLException {
		ArrayList<Room> roomBuffer = new ArrayList<Room>();
		String hotelName = "";
		int hotelZip = 0;
		clearScreen();

		for( boolean quitLoop = false ; !quitLoop ; ) {
			System.out.println("Skráðu nafn hótelsins sem á að skrá herbergið fyrir:");
			hotelName = input.next();

			System.out.println("Skráðu póstfang hótelsins sem á að skrá herbergið fyrir:");
			hotelZip = (int)parseNumber(input.next());

			System.out.println();

			System.out.println("Skráðu stærð á nýju hótelherbergi:");
			String newRoomSize = input.next();

			System.out.println("Skráðu fjölda rúma í nýja hótelherberginu:");
			String newRoomBedCount = input.next();

			//System.out.println("Er sturta í hótelherberginu? (Y/N)");
			//String newRoomHasShower = input.next();

			System.out.println("Sláðu inn verð á nýja hótelherberginu:");
			String newRoomPrice = input.next();

			roomBuffer.add(new Room((int)parseNumber(newRoomSize),
				 					(int)parseNumber(newRoomBedCount),
				 					(int)parseNumber(newRoomPrice),
									null
									));

			System.out.println();
			System.out.println("Vilt þú bæta við fleiri hótelherbergjum? (Y/N)");
			System.out.println();


			//athuga hvort við viljum skrá fleiri herbergi
			this.inputString = input.next().toLowerCase();
			quitLoop = affirm(this.inputString);
		}

		Hotel hotel = DBmanager.getHotel(hotelName, hotelZip);
		DBmanager.addRoomsToHotel(roomBuffer, hotelName, hotelZip);
		this.state = programState.MENU;
	}

	private void changeHotelRoomPriceInput() throws SQLException {
		System.out.println("Skráðu nafn hótelsins:");
		String hotelName = input.next();

		System.out.println("Skráðu póstfang hótelsins:");
		String hotelZipString = input.next();
		int hotelZip = (int)parseNumber(hotelZipString);
		System.out.println();

		System.out.println("Skráðu inn ID herbergisins sem á að breyta:");
		String room_idString = input.next();
		int room_id = Integer.parseInt(room_idString);
		System.out.println();

		//TODO: prósenta o.fl.
		System.out.println("Skráðu inn verðbreytingu:");
		String priceChange = input.next();

		Hotel hotel = DBmanager.getHotel(hotelName, hotelZip);
<<<<<<< HEAD
		DBmanager.changeRoomPriceByAmount(Double.parseDouble(priceChange), DBmanager.getRoomFromHotel( room_id, hotel));
=======
		//DBmanager.changeRoomPriceByAmount(Double.parseDouble(priceChange), DBmanager.getRoomFromHotel(hotel));
>>>>>>> 72e6c8c16fdfa3ca0dfcb94fda6ed1da58e5e376

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
		hotelView program = new hotelView(programState.MENU, new Scanner(System.in).useDelimiter("\n"));

		try {
			DBmanager.init();

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
		}catch (SQLException e) {
        	e.printStackTrace();
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
