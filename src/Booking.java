import java.util.Date;
import java.util.ArrayList;

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