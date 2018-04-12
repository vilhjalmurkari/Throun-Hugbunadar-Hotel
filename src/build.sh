#!/bin/bash

javac Booking.java Hotel.java Room.java User.java SearchQuery.java DBmanager.java HotelAPI.java HotelView.java

if [[ "$?" == "0" ]]; then
	if [[ "$1" == "run" ]]; then
		java HotelView
	fi
fi