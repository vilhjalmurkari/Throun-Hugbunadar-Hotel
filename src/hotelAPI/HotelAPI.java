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


	//Ef leita á einungis eftir tilteknum gildum eru hin gildin stillt sem null eða -1, eftir því hvort stilla á streng eða tölu.
	//Ef öll gildin eru null eða -1 skilar fallið öllum hótelum í gagnagrunninum
	// Notkun: x = hotelSearch(c,min,max)
	// Fyrir:  c er leitarstrengur fyrir einhverja borg, null ef hann skiptir ekki máli.
	//         min og max eru lægstu og 
	// Eftir:  
	public ArrayList<Hotel> hotelSearch(String hotel_city, int min_rating, int max_rating) throws SQLException {
		if(hotel_city == null) hotel_city = "";
		if(min_rating == -1) min_rating = 0;
		if(max_rating == -1) max_rating = 5;

		return DBmanager.hotelSearch(hotel_city, min_rating, max_rating);
	}

	// Notkun: 
	// Fyrir:  
	// Eftir:  
	public void setRoomPrice(int new_price, Room room) throws SQLException {
		ArrayList<Room> al = new ArrayList<Room>();
		al.add(room);
		setRoomPrice(new_price, al);
	}

	// Notkun: 
	// Fyrir:  
	// Eftir:  
	public void setRoomPrice(int new_price, ArrayList<Room> rooms) throws SQLException {
		for(Room r : rooms) {
			r.price = new_price;
		}
		DBmanager.setRoomPrice(new_price, rooms);
	}

	// Notkun: 
	// Fyrir:  
	// Eftir:  
	public void changeRoomPriceByAmount(int price_change, Room room) throws SQLException {
		DBmanager.changeRoomPriceByAmount(price_change, room);
		room.price += price_change;
	}

	// Notkun: 
	// Fyrir:  
	// Eftir:  
	public void changeRoomPriceByPercent(double percent, Room room) throws SQLException {
		DBmanager.changeRoomPriceByPercent(percent, room);
		room.price = (int)((1+percent) * room.price);
	}

	// Notkun: 
	// Fyrir:  
	// Eftir:  
	public void addHotel(Hotel hotel) throws SQLException {
		DBmanager.addHotel(hotel);
	}

	// Notkun: 
	// Fyrir:  
	// Eftir:  
	public void addHotels(ArrayList<Hotel> hotels) throws SQLException {
		DBmanager.addHotels(hotels);
	}

	// Notkun: 
	// Fyrir:  
	// Eftir:  
	public void addRoomToHotel(Room room, Hotel hotel) throws SQLException {
		hotel.rooms.add(room);
		DBmanager.addRoomToHotel(room, hotel.name, hotel.city);
	}

	// Notkun: 
	// Fyrir:  
	// Eftir:  
	public void deleteHotel(Hotel hotel) throws SQLException {
		DBmanager.deleteHotel(hotel);
	}

	// Notkun: 
	// Fyrir:  
	// Eftir:  
	public void deleteRoom(Room room) throws SQLException {
		DBmanager.deleteRoom(room);
	}
}
