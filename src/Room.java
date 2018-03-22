import java.util.Date;
import java.util.ArrayList;

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