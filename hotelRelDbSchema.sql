CREATE TABLE Hotel( 
	name VARCHAR(32),
	rating INT,
	description VARCHAR(140),
	zipcode INT,
	PRIMARY KEY(name, zipcode)
	/*
	name + zipcode make-ar sense því held það séu ekki hótel á sama stað með sama nafn
	ef við værum með id þá værum við í raun og veru að gera þetta nema við myndum leyfa það að
	hótel megi hafa sama nafn og sama zip kóða samtímis, og þá þyrftum við einhvernveginn að vinna
	okkur úr því ef það kemur upp, en hér segjum við einfaldlega að það sé ekki leyfilegt.
	*/
);

CREATE TABLE Hotel_tag(
	hotel_name VARCHAR(32),
	hotel_zipcode INT,
	tag_name VARCHAR(32),--þettaeru16stafir, vikrar smá lítið...
	FOREIGN KEY (hotel_name, hotel_zipcode) REFERENCES Hotel(name, zipcode)
);

CREATE TABLE Room(
	--þetta id mun fá gildi sjálfkrafa við innsetningu í tölfuna(sjá https://sqlite.org/autoinc.html)
	id INT PRIMARY KEY,
	hotel_name VARCHAR(32),
	hotel_zipcode INT,
	size INT,
	FOREIGN KEY (hotel_name, hotel_zipcode) REFERENCES Hotel(name, zipcode)
);

CREATE TABLE Room_tag( 
	room_id INT,
	FOREIGN KEY (room_id) REFERENCES Room(id)	
);