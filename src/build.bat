@echo off

pushd hotelAPI
javac Booking.java Hotel.java Room.java User.java SearchQuery.java DBmanager.java HotelAPI.java
popd

if "%1" == "zip" zip -r hotelAPI.zip hotelAPI
if "%2" == "zip" zip -r hotelAPI.zip hotelAPI

javac View.java

if %errorlevel% == 0 (
	if "%1" == "run" java -cp .;sqlite-jdbc-3.18.0.jar View
	if "%2" == "run" java -cp .;sqlite-jdbc-3.18.0.jar View
)