class HotelAPI {

	public HotelAPI() {
		DBmanager.init();
	}

	public ArrayList<Hotel> getAllHotels() {
		return DBmanager.getAllHotels();
	}

	public Hotel getHotel(Hotel hotel) {
		return DBmanager.getHotel(hotel.name, hotel.zipcode);
	}

	public ArrayList<String> getHotelTags(String hotel_name, int hotel_zipcode) {
		return DBmanager.getHotelTags(hotel_name, hotel_zipcode);
	}

	public ArrayList<String> getRoomTags(Room room) {
		return DBmanager.getRoomTags(room.id);
	}

	public ArrayList<Room> getRoomsFromHotel(String hotel_name, int hotel_zipcode) {
		return DBmanager.getRoomsFromHotel(hotel_name, hotel_zipcode);
	}

	public ArrayList<Room> getRoomsFromHotel(Hotel hotel) {
		return DBmanager.getRoomsFromHotel(hotel);
	}

	public void setRoomPrice(double new_price, Room room) {
		return DBmanager.setRoomPrice(new_price, room.id);
	}

	public void changeRoomPriceByAmount(double price_change, ArrayList<Room> rooms) {
		return DBmanager.changeRoomPriceByAmount(price_change, rooms);
	}

	public void changeRoomPriceByPercent(double percent, ArrayList<Room> rooms) {
		return DBmanager.changeRoomPriceByPercent(percent, rooms);
	}

	public void addHotel(Hotel hotel) {
		return DBmanager.addHotel(hotel);
	}

	public void addHotels(ArrayList<Hotel> hotels) {
		return DBmanager.addHotels(hotels);
	}

	public void addRoomToHotel(Room room, String hotel_name, int hotel_zipcode) {
		return DBmanager.addRoomToHotel(room, hotel_name, hotel_zipcode);
	}

	public void addRoomToHotel(Room room, Hotel hotel) {
		return DBmanager.addRoomToHotel(room, hotel);
	}

	public void addRoomsToHotel(ArrayList<Room> rooms, String hotel_name, int hotel_zipcode) {
		return DBmanager.addRoomsToHotel(rooms, hotel_name, hotel_zipcode);
	}

	public void addRoomsToHotel(ArrayList<Room> rooms, Hotel hotel) {
		return DBmanager.addRoomsToHotel(rooms, hotel);
	}

	public void bookRoom(int room_id, int user_id, long start_date, long end_date) {
		return DBmanager.bookRoom(room_id, user_id, start_date, end_date);
	}

	public ArrayList<Hotel> search(SearchQuery query) {
		return DBmanager.search(query);
	}

	public ArrayList<Hotel> getHotelsWithQuery(String hotelQuery, String roomQuery) {
		return DBmanager.getHotelsWithQuery(hotelQuery, roomQuery);
	}

	public ArrayList<Room> getRoomsWithQuery(String hotel_name, int hotel_zipcode, String roomQuery) {
		return DBmanager.getRoomsWithQuery(hotel_name, hotel_zipcode, roomQuery);
	}
}