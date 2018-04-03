import java.util.ArrayList;
import java.sql.*;

class HotelAPI {

	public HotelAPI() throws SQLException {
		DBmanager.init();
	}

	public ArrayList<Hotel> getAllHotels() throws SQLException {
		return DBmanager.getAllHotels();
	}

	public ArrayList<Hotel> getHotelsByName(String hotel_name) {
		return DBmanager.getHotelsByName(hotel.name);
	}

	public ArrayList<String> getHotelTags(Hotel hotel) {
		return hotel.tags;
	}

	public ArrayList<String> getRoomTags(Room room) {
		return room.tags;
	}

	public ArrayList<Room> getRoomsFromHotel(Hotel hotel) {
		return hotel.rooms;
	}

	public void setRoomPrice(double new_price, Room room) {
		room.price = new_price;
		DBmanager.setRoomPrice(new_price, room.id);
	}

	public void setRoomPrice(double new_price, ArrayList<Room> rooms) {
		for(Room r : rooms) {
			setRoomPrice(new_price, r);
		}
	}

	public void changeRoomPriceByAmount(double price_change, ArrayList<Room> rooms) throws SQLException {
		DBmanager.changeRoomPriceByAmount(price_change, rooms);
	}

	public void changeRoomPriceByPercent(double percent, ArrayList<Room> rooms) throws SQLException {
		DBmanager.changeRoomPriceByPercent(percent, rooms);
	}

	public void addHotel(Hotel hotel) throws SQLException {
		DBmanager.addHotel(hotel);
	}

	public void addHotels(ArrayList<Hotel> hotels) throws SQLException {
		DBmanager.addHotels(hotels);
	}

	public void addRoomToHotel(Room room, Hotel hotel) throws SQLException {
		DBmanager.addRoomToHotel(room, hotel.name, hotel.zipcode);
	}

	public void addRoomToHotel(Room room, Hotel hotel) throws SQLException {
		DBmanager.addRoomToHotel(room, hotel);
	}

	public void addRoomsToHotel(ArrayList<Room> rooms, Hotel hotel) throws SQLException {
		DBmanager.addRoomsToHotel(rooms, hotel.name, hotel.zipcode);
	}

	public void bookRoom(Room room, User user, long start_date, long end_date) throws SQLException {
		DBmanager.bookRoom(room.id, user.id, start_date, end_date);
	}

	public ArrayList<Hotel> search(SearchQuery query) throws SQLException {
		return DBmanager.search(query);
	}

	public ArrayList<Hotel> getHotelsWithQuery(String hotelQuery, String roomQuery) throws SQLException {
		return DBmanager.getHotelsWithQuery(hotelQuery, roomQuery);
	}

	public ArrayList<Room> getRoomsWithQuery(Hotel hotel, String roomQuery) throws SQLException {
		return DBmanager.getRoomsWithQuery(hotel.name, hotel.zipcode, roomQuery);
	}
}
