import java.util.Date;
import java.util.ArrayList;

class Room {
	public int id;
	public int size;
	//public boolean shower;
	public int price;
	public int bed_count;
	public ArrayList<String> tags;

	public Room(int id, int size, int bed_count, int price, ArrayList<String> tags) {
		this.id = id;
		this.size = size;
		this.bed_count = bed_count;
		//this.shower = shower;
		this.price = price;
		this.tags = tags;
	}

	//einhver föll hér
	public int getRoomSize() {
		return this.size;
	}
}