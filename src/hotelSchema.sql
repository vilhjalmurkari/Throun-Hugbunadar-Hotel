CREATE TABLE Hotels(
	name VARCHAR(32),
	rating INTEGER,
	description VARCHAR(140),
	zipcode INTEGER,
	PRIMARY KEY(name, zipcode)
	/*
	name + zipcode make-ar sense því held það séu ekki hótel á sama stað með sama nafn
	ef við værum með id þá værum við í raun og veru að gera þetta nema við myndum leyfa það að
	hótel megi hafa sama nafn og sama zip kóða samtímis, og þá þyrftum við einhvernveginn að vinna
	okkur úr því ef það kemur upp, en hér segjum við einfaldlega að það sé ekki leyfilegt.
	*/
);

CREATE TABLE Hotel_tags(
	hotel_name VARCHAR(32),
	hotel_zipcode INTEGER,
	tag_name VARCHAR(32),--þettaeru16stafir, vikrar smá lítið...
	FOREIGN KEY (hotel_name, hotel_zipcode) REFERENCES Hotels(name, zipcode)
);

CREATE TABLE Rooms(
	--þetta id mun fá gildi sjálfkrafa við innsetningu í tölfuna(sjá https://sqlite.org/autoinc.html)
	id INTEGER PRIMARY KEY,
	hotel_name VARCHAR(32),
	hotel_zipcode INTEGER,
	size INTEGER,
	price INTEGER,
	bed_count INTEGER,
	FOREIGN KEY (hotel_name, hotel_zipcode) REFERENCES Hotels(name, zipcode)
);

CREATE TABLE Room_tags(
	room_id INTEGER,
	tag_name VARCHAR(32),
	FOREIGN KEY (room_id) REFERENCES Rooms(id)
);

CREATE TABLE Bookings(
	user_id INTEGER,
	room_id INTEGER,
	FOREIGN KEY(user_id) REFERENCES Users(id),
	FOREIGN KEY(room_id) REFERENCES Rooms(id)
);

CREATE TABLE Users(
	id INTEGER PRIMARY KEY,
	name VARCHAR(64)
);
