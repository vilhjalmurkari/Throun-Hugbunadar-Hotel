insert into Hotels (name, rating, description, zipcode) values ('Holt', 4, 'Frábært hótel á besta stað', 107);
insert into Hotels (name, rating, description, zipcode) values ('Hilton', 5, 'Skemmtilegt fjölskylduhótel með góðan morgunverð', 107);
insert into Hotels (name, rating, description, zipcode) values ('Leifur Eiríksson', 3, 'Með betri hótelum, en staðsetning ekki nægilega góð', 200);
insert into Rooms (hotel_name, hotel_zipcode, size, price, bed_count) values ('Hilton', 107, 30, 5000, 3);
insert into Rooms (hotel_name, hotel_zipcode, size, price, bed_count) values ('Hilton', 107, 30, 7500, 2);
insert into Rooms (hotel_name, hotel_zipcode, size, price, bed_count) values ('Holt', 107, 20, 20000, 2);
insert into Rooms (hotel_name, hotel_zipcode, size, price, bed_count) values ('Leifur Eiríksson', 200, 35, 25000, 6);
insert into Rooms (hotel_name, hotel_zipcode, size, price, bed_count) values ('Hilton', 107, 20, 15000, 4);
insert into Rooms (hotel_name, hotel_zipcode, size, price, bed_count) values ('Leifur Eiríksson', 200, 20, 17500, 4);
insert into Rooms (hotel_name, hotel_zipcode, size, price, bed_count) values ('Leifur Eiríksson', 200, 40, 19000, 5);

insert into Rooms (id, hotel_name, hotel_zipcode, size, price, bed_count) values (1,'my hotel', 105, 3, 800, 5);

insert into Hotel_tags (hotel_name, hotel_zipcode, tag_name) values ('my hotel', 105, 'Bar');
insert into Hotel_tags (hotel_name, hotel_zipcode, tag_name) values ('my hotel', 105, 'Sundlaug');
insert into Hotel_tags (hotel_name, hotel_zipcode, tag_name) values ('my hotel', 105, 'Nálægt strönd');
insert into Hotel_tags (hotel_name, hotel_zipcode, tag_name) values ('my hotel', 105, 'Diskótek');
insert into Hotel_tags (hotel_name, hotel_zipcode, tag_name) values ('my hotel', 105, 'Reiðhjólaleiga');

insert into Room_tags (room_id, tag_name) values (1, "Ísskápur");
insert into Room_tags (room_id, tag_name) values (1, "Sturta");
insert into Room_tags (room_id, tag_name) values (1, "Svalir");
insert into Room_tags (room_id, tag_name) values (1, "Wifi");
