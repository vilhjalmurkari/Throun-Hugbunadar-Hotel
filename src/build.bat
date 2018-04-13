@echo off

pushd HotelAPI
javac Booking.java Hotel.java Room.java User.java SearchQuery.java DBmanager.java HotelAPI.java
popd

if "%1" == "zip" zip -r HotelAPI.zip HotelAPI
if "%2" == "zip" zip -r HotelAPI.zip HotelAPI

javac View.java

if %errorlevel% == 0 (
	if "%1" == "run" java -cp .;sqlite-jdbc-3.18.0.jar HotelView
	if "%1" == "run" java -cp .;sqlite-jdbc-3.18.0.jar HotelView
)