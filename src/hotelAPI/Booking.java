package hotelAPI;

import java.util.ArrayList;
import java.sql.*;

public class Booking {
	protected int id;
	protected Room room;
	protected long start_date;
	protected long end_date;
	protected boolean confirmed;

	// Usage:  Booking(r,s,e)
	// Before: r er herbergi,
	//         s og e eru byrjunar og enda dagsetningar
	//             á þessari bókun sem heiltölur.
	// After:  Bókun hefur verið gerð.
	protected Booking(Room room, long start_date, long end_date) {
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
	protected void confirmBooking() throws SQLException {
		this.confirmed = true;
		DBmanager.confirmBooking(this);
	}
	
	// Notkun: cancelBooking()
	// Fyrir:  Þessi bókun er óstaðfest.
	// Eftir:  Þessi bókun hefur verið fjarlægð úr gagnagrunninum.
	protected void cancelBooking() throws SQLException {
		DBmanager.cancelBooking(this);
	}
}
