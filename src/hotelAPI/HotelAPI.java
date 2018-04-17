package hotelAPI;

import java.util.ArrayList;
import java.sql.*;

public class HotelAPI {
	
	// Notkun: HotelAPI()
	// Fyrir:  Ekkert
	// Eftir:  Controller hefur verið upphafsstilltur.
	public HotelAPI() throws SQLException {
		DBmanager.init();
	}

	// Notkun: x = getHotel(s,c)
	// Fyrir:  s og c eru leitarstrengir, hótel nafn og borg.
	// Eftir:  x er fyrsti Hotel hlutur sem finnst í gagnagrunni.
	public Hotel getHotel(String hotel_name, String hotel_city) throws SQLException {
		return DBmanager.getHotel(hotel_name, hotel_city);
	}

	// Notkun: hotelSearch(c,min,max)
	// Fyrir:  c er leitarstrengur fyrir einhverja borg (nafn hótels eða borg), 
	//           en null ef hann skiptir ekki máli.
	//         min og max eru lægstu og hæstu stjörnur sem hótel má hafa,
	//           en er -1 ef ekki skiptir máli.
	// Skilar:  lista af hótelum sem uppfylla leitarskilyrði.
	public ArrayList<Hotel> hotelSearch(String hotel_city_or_name, int min_rating, int max_rating) throws SQLException {
		if(hotel_city == null) hotel_city = "";
		if(min_rating == -1) min_rating = 0;
		if(max_rating == -1) max_rating = 5;

		return DBmanager.hotelSearch(hotel_city_or_name, min_rating, max_rating);
	}

	// Notkun: makeUser(n,e)
	// Fyrir:  n er nafn notanda,
	//         e er tölvupóstfang notenda
	// Skilar: User hlut með e sem auðkenni.
	//         Notenda hefur verið bætt við í gagnagrunn.
	public User makeUser(String name, String email) throws SQLException {
		User user = new User(name, email);
		DBmanager.addUser(user);
		return user;
	}

	public void bookRoomForUser(User user, Room room) throws SQLException {
		return;
	}
}
