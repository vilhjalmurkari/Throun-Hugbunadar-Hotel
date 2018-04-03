import java.util.ArrayList;
import java.sql.*;

class HotelAPI {

	public HotelAPI() throws SQLException {
		DBmanager.init();
	}

	public ArrayList<Hotel> getAllHotels() throws SQLException {
		return DBmanager.getAllHotels();
	}

	public Hotel getHotel(String hotel_name, int hotel_zipcode) throws SQLException {
		return DBmanager.getHotel(hotel_name, hotel_zipcode);
	}

	public ArrayList<Room> getRoomsFromHotel(String hotel_name, int hotel_zipcode) throws SQLException {
		return DBmanager.getRoomsFromHotel(hotel_name, hotel_zipcode);
	}

	public ArrayList<Room> getRoomsFromHotel(Hotel hotel) throws SQLException {
		return DBmanager.getRoomsFromHotel(hotel);
	}

	public void setRoomPrice(double new_price, int room_id) throws SQLException {
		DBmanager.setRoomPrice(new_price, room_id);
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

	public void addRoomToHotel(Room room, String hotel_name, int hotel_zipcode) throws SQLException {
		DBmanager.addRoomToHotel(room, hotel_name, hotel_zipcode);
	}

	public void addRoomToHotel(Room room, Hotel hotel) throws SQLException {
		DBmanager.addRoomToHotel(room, hotel);
	}

	public void addRoomsToHotel(ArrayList<Room> rooms, String hotel_name, int hotel_zipcode) throws SQLException {
		DBmanager.addRoomsToHotel(rooms, hotel_name, hotel_zipcode);
	}

	public void addRoomsToHotel(ArrayList<Room> rooms, Hotel hotel) throws SQLException {
		DBmanager.addRoomsToHotel(rooms, hotel);
	}

	public void bookRoom(int room_id, int user_id, long start_date, long end_date) throws SQLException {
		DBmanager.bookRoom(room_id, user_id, start_date, end_date);
	}

	public ArrayList<Hotel> search(SearchQuery query) throws SQLException {
		return DBmanager.search(query);
	}

	public ArrayList<Hotel> getHotelsWithQuery(String hotelQuery, String roomQuery) throws SQLException {
		return DBmanager.getHotelsWithQuery(hotelQuery, roomQuery);
	}

	public ArrayList<Room> getRoomsWithQuery(String hotel_name, int hotel_zipcode, String roomQuery) throws SQLException {
		return DBmanager.getRoomsWithQuery(hotel_name, hotel_zipcode, roomQuery);
	}
}
