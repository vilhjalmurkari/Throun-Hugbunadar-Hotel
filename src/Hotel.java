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

	public void printHotel() {
		System.out.println("Name: " + name);
		System.out.println("Zipcode: " + zipcode);
		System.out.println("Rating: " + rating);
		System.out.print("description: ");

		char[] description_array = description.toCharArray();
		int max_len = 32;

		for(int i = 0; 
			i < ((description_array.length > max_len) ? max_len : description_array.length);
			i++) {
			System.out.print(description_array[i]);
			if(i == max_len-1) System.out.println("...");
		}

		System.out.println();

		System.out.println("tags:");

		for(String tag : tags) {
			System.out.print(tag + ", ");
		}

		System.out.println();

		System.out.println("Rooms count: " + rooms.size());
	}

	//einhver föll hér
	public int getHotelRating() {
		return this.rating;
	}
}