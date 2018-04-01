//keyrsla: java -cp .:sqlite-jdbc....jar hotelView
import java.util.ArrayList;

class dbmanTest {
	//public Hotel(String name, int rating, String description, int zipcode, ArrayList<String> tags, ArrayList<Room> rooms) {
	public static void main(String[] args) {
		try {
			DBmanager.init();

			if(false) {
				DBmanager.addHotel(new Hotel("my hotel",
											 5, 
											 "mjög flott og gott hótel", 
											 105, 
											 null, 
											 null));

			}

			if(false) {
				Room new_room = new Room(40, 3, 500, null);
				DBmanager.addRoomToHotel(new_room, "my hotel", 105);
			}

			if(false) DBmanager.setRoomPrice(1234, 2);

			Hotel h = DBmanager.getHotel("my hotel", 105);
			System.out.println("hotel name: " + h.name + ", hotel zipcode: " + h.zipcode);

			if(false) DBmanager.changeRoomPriceByPercent(-33.333, h.rooms);
			if(true) DBmanager.changeRoomPriceByAmount(100, h.rooms);

			h = DBmanager.getHotel("my hotel", 105);

			for(Room r : h.rooms) {
				System.out.println("id: " + r.id + ", size: " + r.size + ", price: " + r.price + ", bed_count: " + r.bed_count);
			}

		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}