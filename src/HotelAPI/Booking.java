package HotelAPI;

import java.util.Date;
import java.util.ArrayList;

public class Booking {
	public int id;
	public Room room;
	public Date start_date;
	public Date end_date;
	public boolean confirmed;

	// Usage:  Booking(r,s,e)
	// Before: r er herbergi,
	//         s og e eru byrjunar og enda dagsetningar
	//         á þessari bókun sem heiltölur.
	// After:  Bókun hefur verið gerð.
	public Booking(Room room, Date start_date, Date end_date) {
		this.room = room;
		this.start_date = start_date;
		this.end_date = end_date;
		this.confirmed = false;
	}

	// Notkun: confirmBooking()
	// Fyrir:  Ekkert.
	// Eftir:  Þessi bókun hefur verið staðfest
	//         en ekki færð í gagnagrunn.
	public void confirmBooking(){
		this.confirmed = true;
//		DBmanager.confirmBooking(this);
	}
}
