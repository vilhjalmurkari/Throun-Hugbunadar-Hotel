import java.util.ArrayList;
import java.sql.*;
import hotelAPI.*;

class testing {

	static void datePrint(long posix_time) {
		int year = 1970 + (posix_time / 60 / 60 / 24 / 365);
		int month = ((posix_time / 60 / 60 / 24) % 12) + 1;
		boolean leap_year = (year % 4 == 0) ? ((year % 100 == 0) ? (year % 400 == 0 ? true : false) : true) : false;
		System.out.println(month + " " + year);
	}

	public static void main(String[] args) throws SQLException {
		datePrint(1);
		/*
		HotelAPI api = new HotelAPI();
		ArrayList<Hotel> hotels = api.hotelSearch("R", -1, -1);

		for(Hotel h : hotels) {
			System.out.println(h.name);
		}

		System.out.println();
		*/
	}

}
