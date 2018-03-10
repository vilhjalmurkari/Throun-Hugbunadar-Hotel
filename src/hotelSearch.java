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

	public static void addHotel(Hotel hotel) {

	}

	public static void addRoomToHotel(Room room, Hotel hotel) {

	}

	public static ArrayList<Hotel> search(SearchQuery query) {
		//sql query...
		return new ArrayList<Hotel>();
	}

	public static void clearScreen() {
		//ansi escape codes
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public static String checkAndAcceptInput(Scanner scanner, inputType type, String message) {
		String input = "";
		char inputFrontChar = 0;
		boolean accepted = true;

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
				break;
			}
		}while(!accepted);

		return input;
	}

	//þetta gerir ekkert sérstakt eins og er.
	public static void main(String[] args) throws ClassNotFoundException {
		Scanner stdInput = new Scanner(System.in).useDelimiter("\n");
		programState state = programState.MENU;
		ArrayList<Hotel> hotelBuffer = new ArrayList<Hotel>();

		while(true) {
			clearScreen();

			switch(state) {
				case MENU:
					System.out.println("Velkomin í hótelleitarforrit 7H");
					System.out.println("vinsamlegast sláðu inn tölu sem samsvarast eftirfarandi valmöguleikum:");
					System.out.println();
					System.out.println("1): Skrá hótel");
					System.out.println("2): Skrá herbergi fyrir hótel");
					System.out.println("3): Breyta verði á hótelherbergi");
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

			String input = "";
			char inputFrontChar = 0;
			boolean inputDone = false;

			while(!inputDone) {
				boolean possibleMenuCommand = false;

				switch(state) {
					case MENU:
						input = stdInput.next();
						inputFrontChar = input.charAt(0);

						switch(inputFrontChar) {
							case '1':
								state = programState.HOTEL_REG;
								inputDone = true;
							break;

							case '2':
								state = programState.ROOM_REG;
								inputDone = true;
							break;

							case '3':
								state = programState.CHANGE_ROOM_PRICE;
								inputDone = true;
							break;

							default:
								possibleMenuCommand = true;
							break;
						}
					break;

					case HOTEL_REG:
						while(true) {
							System.out.println("Skráðu nafn á nýju hóteli:");
							String newHotelName = stdInput.next();

							System.out.println("Skráðu póstfang nýja hótelsins:");
							String newHotelZip = stdInput.next();

							System.out.println("Skráðu lýsingu á nýja hótelinu:");
							String newHotelDesription = stdInput.next();

							System.out.println("Skráðu stjörnufjölda nýja hótelsins:");
							String newHotelRating = stdInput.next();

							hotelBuffer.add(new Hotel(newHotelName, 
													  Integer.parseInt(newHotelRating),
													  newHotelDesription, 
													  Integer.parseInt(newHotelZip), 
													  null, 
													  null));

							System.out.println();
							System.out.println("Vilt þú bæta við fleiri hótelum?");
							System.out.println();
							input = stdInput.next();

							if(input.charAt(0) == 'n' || input.charAt(0) == 'N') {
								state = programState.MENU;
								inputDone = true;
								break;
							}
						}
					break;

					case ROOM_REG:
						switch(inputFrontChar) {
							default:
								possibleMenuCommand = true;
							break;
						}
					break;

					case CHANGE_ROOM_PRICE:
						switch(inputFrontChar) {
							default:
								possibleMenuCommand = true;
							break;
						}
					break;
				}

				if(possibleMenuCommand) {
					switch(inputFrontChar) {
						case 'h':
							System.exit(0);
						break;

						case 'b':
							if(state != programState.MENU) {
								state = programState.MENU;
								inputDone = true;
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
