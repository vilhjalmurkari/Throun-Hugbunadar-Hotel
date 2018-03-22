import java.util.Date;
import java.util.ArrayList;

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