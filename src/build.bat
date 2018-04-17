@echo off

pushd hotelAPI
javac Booking.java Hotel.java Room.java User.java DBmanager.java HotelAPI.java
popd

if "%1" == "jar" jar cf hotelAPI.jar hotelAPI/*.class hotels.db
if "%2" == "jar" jar cf hotelAPI.jar hotelAPI/*.class hotels.db

if %errorlevel% == 0 (
	javac View.java
	if "%1" == "run" java -cp .;sqlite-jdbc-3.18.0.jar View
	if "%2" == "run" java -cp .;sqlite-jdbc-3.18.0.jar View
)