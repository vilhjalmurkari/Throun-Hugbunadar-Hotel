CREATE TABLE Hotel( 
	--þetta er þægilegt því nú mun hver ný röð í Hotel fá sjálfgefið id sem er hæsta id-ið í töflunni + 1
	id INT PRIMARY KEY,
	name VARCHAR(32),
	rating INT,
	description VARCHAR(140),
	zipcode INT,
);

CREATE TABLE Hotel_tag(
	hotel_id INT,
	tag_name VARCHAR(32),--þettaeru16stafir, vikrar smá lítið...
	FOREIGN KEY (hotel_id) REFERENCES Hotel(id)
);

CREATE TABLE Room(  
	id INT PRIMARY KEY,
	hotel_id INT,
	FOREIGN KEY (hotel_id) REFERENCES Hotel(id)
);

CREATE TABLE Room_tag( 
	room_id INT,
	FOREIGN KEY (room_id) REFERENCES Room(id)	
);