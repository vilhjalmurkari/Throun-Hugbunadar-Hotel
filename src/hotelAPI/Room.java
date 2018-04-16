package hotelAPI;

import java.util.ArrayList;

public class Room {
	public int id;
	public int size;
	public int price;
	public int bed_count;
	public ArrayList<String> tags;

	public Room(int size, int bed_count, int price, ArrayList<String> tags) {
		this.id = (int) (Math.random()*Integer.MAX_VALUE);
		this.size = size;
		this.bed_count = bed_count;
		this.price = price;
		this.tags = tags;
	}
}
