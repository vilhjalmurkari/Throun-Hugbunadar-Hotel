package hotelAPI;

import java.util.ArrayList;
import java.sql.*;

public class Booking {
	public int id;
	public Room room;
	public long start_date;
	public long end_date;
	public boolean confirmed;

	// Usage:  Booking(r,s,e)
	// Before: r er herbergi,
	//         s og e eru byrjunar og enda dagsetningar
	//             á þessari bókun sem heiltölur.
	// After:  Bókun hefur verið gerð.
	public Booking(Room room, long start_date, long end_date) {
		this.id = (int)(Math.random()*Integer.MAX_VALUE);
		this.room = room;
		this.start_date = start_date;
		this.end_date = end_date;
		this.confirmed = false;
	}

	// Notkun: confirmBooking()
	// Fyrir:  Ekkert.
	// Eftir:  Þessi bókun hefur verið staðfest
	//             en ekki færð í gagnagrunn.
	public void confirmBooking() throws SQLException {
		this.confirmed = true;
		DBmanager.confirmBooking(this);
	}
}
