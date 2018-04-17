#!/bin/bash
ERRORCODE=0

echo "compiling hotelAPI"
cd hotelAPI
javac Booking.java Hotel.java Room.java User.java DBmanager.java HotelAPI.java
ERRORCODE="$?"
cd ..

if [[ ERRORCODE -eq "0" ]]; then
	if [ "$1" == "jar" ] || [ "$2" == "jar" ]; then
		echo "building jar"
		jar cf hotelAPI.jar hotelAPI/*.class hotels.db
	fi

	echo "compiling view"
	javac View.java
	ERRORCODE="$?"
	
	if [[ ERRORCODE -eq "0" ]]; then
		if [ "$1" == "run" ] || [ "$2" == "run" ]; then
			java -cp .:sqlite-jdbc-3.18.0.jar View
		fi
	fi
fi
