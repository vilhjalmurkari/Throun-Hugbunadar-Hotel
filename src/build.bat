@echo off

pushd HotelAPI
javac Booking.java Hotel.java Room.java User.java SearchQuery.java DBmanager.java HotelAPI.java
popd

zip -r HotelAPI.zip HotelAPI

javac View.java

if ERRORLEVEL == 0 (
	if %1 == "run" (
		::java -cp .;sqlite-jdbc-3.18.0.jar HotelView
		java View
	)
)