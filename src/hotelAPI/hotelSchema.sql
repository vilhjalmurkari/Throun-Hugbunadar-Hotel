CREATE TABLE Hotels(
	name VARCHAR(32),
	rating INTEGER,
	description VARCHAR(140),
	city VARCHAR(32),
	PRIMARY KEY(name, city)
	/*
	name + city make-ar sense því held það séu ekki hótel á sama stað með sama nafn
	ef við værum með id þá værum við í raun og veru að gera þetta nema við myndum leyfa það að
	hótel megi hafa sama nafn og vera í sömu borg samtímis, og þá þyrftum við einhvernveginn að vinna
	okkur úr því ef það kemur upp, en hér segjum við einfaldlega að það sé ekki leyfilegt.
	*/
);

CREATE TABLE Hotel_tags(
	hotel_name VARCHAR(32),
	hotel_city VARCHAR(32),
	tag_name VARCHAR(32),--þettaeru16stafir, vikrar smá lítið...
	FOREIGN KEY (hotel_name, hotel_city) REFERENCES Hotels(name, city)
);

CREATE TABLE Rooms(
	--þetta id mun fá gildi sjálfkrafa við innsetningu í tölfuna(sjá https://sqlite.org/autoinc.html)
	id INT PRIMARY KEY,
	hotel_name VARCHAR(32),
	hotel_city INTEGER,
	size INTEGER,
	price INTEGER,
	bed_count INTEGER,
	FOREIGN KEY (hotel_name, hotel_city) REFERENCES Hotels(name, city)
);

CREATE TABLE Room_tags(
	room_id INTEGER,
	tag_name VARCHAR(32),
	FOREIGN KEY (room_id) REFERENCES Rooms(id)
);

CREATE TABLE Bookings(
	id INTEGER PRIMARY KEY,
	user_email VARCHAR(255),
	room_id INTEGER,
	start_date INTEGER,
	end_date INTEGER,
	confirmed BOOLEAN,
	FOREIGN KEY(user_email) REFERENCES Users(email),
	FOREIGN KEY(room_id) REFERENCES Rooms(id)
);

CREATE TABLE Users(
	name VARCHAR(64),
	email VARCHAR(255) PRIMARY KEY
);
