@echo off

javac Booking.java Hotel.java Room.java User.java SearchQuery.java DBmanager.java HotelAPI.java HotelView.java

if ERRORLEVEL == 0 (
	if %1 == "run" (
		java -cp .;sqlite-jdbc-3.18.0.jar HotelView
	)
)