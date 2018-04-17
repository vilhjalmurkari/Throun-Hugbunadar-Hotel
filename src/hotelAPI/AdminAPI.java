package hotelAPI

import java.util.ArrayList;

class AdminAPI {

	// Notkun: AdminAPI()
	// Fyrir:  Ekkert.
	// Eftir:  Controller hefur verið upphafsstilltur.
	public AdminAPI() {
		DBmanager.init();
	}

	// Notkun: setRoomPrice(p,r)
	// Fyrir:  p er nýtt verð,
	//         r er herbergi sem breyta á verði á.
	// Eftir:  Verð herbergi r hefur verið breytt í p.
	public void setRoomPrice(int new_price, Room room) throws SQLException {
		ArrayList<Room> al = new ArrayList<Room>();
		al.add(room);
		setRoomPrice(new_price, al);
	}

	// Notkun: setRoomPrice(p,r)
	// Fyrir:  p er nýtt verð,
	//         r er listi herbergja sem breyta á verði á.
	// Eftir:  Verð herbergja í r hefur verið breytt í p.
	public void setRoomPrice(int new_price, ArrayList<Room> rooms) throws SQLException {
		for(Room r : rooms) {
			r.price = new_price;
		}
		DBmanager.setRoomPrice(new_price, rooms);
	}

	// Notkun: changeRoomPriceByAmount
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
