package hotelAPI

import java.util.ArrayList;

class AdminAPI {
	private User user;
	private boolean isAdmin;

	// Notkun: AdminAPI(u)
	// Fyrir:  u er notandi.
	// Eftir:  Controller hefur verið upphafsstilltur,
	//         unnt er að framkvæma breytingar e.o.a.e notandi er admin.
	public AdminAPI(User user) {
		DBmanager.init();
		this.user = user;
		isAdmin = DBmanager.isUserAdmin(user);
	}
	
	// Notkun: escalateUserPriveleges(u)
	// Fyrir:  u er notandi.
	// Eftir:  u hefur fengið admin réttindi.
	public void escalateUserPriveleges(User user) {
		if(isAdmin) {
			DBmanager.setUserPriveleges(user,true);
		}
	}

	// Notkun: escalateUserPriveleges(u)
	// Fyrir:  u er notandi.
	// Eftir:  u hefur misst (ef hafði) admin réttindi.
	public void descalateUserPriveleges(User user) {
		if(isAdmin) {
			DBmanager.setUserPriveleges(user,false);
		}
	}

	// Notkun: setRoomPrice(p,r)
	// Fyrir:  p er nýtt verð,
	//         r er herbergi sem breyta á verði á.
	// Eftir:  Verð herbergi r hefur verið breytt í p.
	public void setRoomPrice(int new_price, Room room) throws SQLException {
		if(isAdmin) {
			ArrayList<Room> al = new ArrayList<Room>();
			al.add(room);
			setRoomPrice(new_price, al);
		}
	}

	// Notkun: setRoomPrice(p,r)
	// Fyrir:  p er nýtt verð,
	//         r er listi herbergja sem breyta á verði á.
	// Eftir:  Verð herbergja í r hefur verið breytt í p.
	public void setRoomPrice(int new_price, ArrayList<Room> rooms) throws SQLException {
		if(isAdmin) {
			for(Room r : rooms) {
				r.price = new_price;
			}
			DBmanager.setRoomPrice(new_price, rooms);
		}
	}

	// Notkun: changeRoomPriceByAmount(p,r)
	// Fyrir:  p er heiltöluverðbreyting, r er herbergi.
	// Eftir:  Verði r hefur hækkað um p í gagnagrunni og hlut.
	public void changeRoomPriceByAmount(int price_change, Room room) throws SQLException {
		if(isAdmin) {
			DBmanager.changeRoomPriceByAmount(price_change, room);
			room.price += price_change;
		}
	}

	// Notkun: changeRoomPriceByPercent(p,r)
	// Fyrir:  p er hækkun í broti (t.d. 0,13 fyrir 13% hækkun), r er herbergi.
	// Eftir:  Verði r hefur hækkað um p í gagnagrunni og hlut.
	public void changeRoomPriceByPercent(double percent, Room room) throws SQLException {
		if(isAdmin) {
			DBmanager.changeRoomPriceByPercent(percent, room);
			room.price = (int)((1+percent) * room.price);
		}
	}

	// Notkun: addHotel(h)
	// Fyrir:  h er hótel.
	// Eftir:  h hefur verið bætt við gagnagrunn.
	public void addHotel(Hotel hotel) throws SQLException {
		if(isAdmin) {
			DBmanager.addHotel(hotel);
		}
	}

	// Notkun: addHotels(h)
	// Fyrir:  h er listi hótela.
	// Eftir:  Öllum hótelum í h hefur verið bætt við í gagnagrunn.
	public void addHotels(ArrayList<Hotel> hotels) throws SQLException {
		if(isAdmin) {
			DBmanager.addHotels(hotels);
		}
	}

	// Notkun: addRoomToHotel(r,h)
	// Fyrir:  r er herbergi, h er hótel.
	// Eftir:  r hefur verið bætt við í h í gagnagrunni og hlut.
	public void addRoomToHotel(Room room, Hotel hotel) throws SQLException {
		if(isAdmin) {
			hotel.rooms.add(room);
			DBmanager.addRoomToHotel(room, hotel.name, hotel.city);
		}
	}

	// Notkun: deleteHotel(h)
	// Fyrir:  h er hótel.
	// Eftir:  h hefur verið eytt úr gagnagrunni.
	public void deleteHotel(Hotel hotel) throws SQLException {
		if(isAdmin) {
			DBmanager.deleteHotel(hotel);
		}
	}

	// Notkun: deleteRoom(r)
	// Fyrir:  r er herbergi.
	// Eftir:  r hefur verið eytt úr gagnagrunni.
	public void deleteRoom(Room room) throws SQLException {
		if(isAdmin) {
			DBmanager.deleteRoom(room);
		}
	}
}
