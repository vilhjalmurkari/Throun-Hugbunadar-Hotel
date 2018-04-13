#!/bin/bash
cd HotelAPI
javac Booking.java Hotel.java Room.java User.java SearchQuery.java DBmanager.java HotelAPI.java
cd ..

if [ "$1" == "zip" ] || [ "$2" == "zip" ]; then
	zip -r HotelAPI.zip HotelAPI
fi

javac View.java

if [[ "$?" == "0" ]]; then
	if [ "$1" == "run" ] || [ "$2" == "run" ]; then
		java -cp .:sqlite-jdbc-3.18.0.jar View
		#java View
	fi
fi