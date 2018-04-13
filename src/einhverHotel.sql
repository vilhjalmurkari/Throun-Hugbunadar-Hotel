insert into Hotels (name, rating, description, city) values ('Holt', 4, 'Frábært hótel á besta stað', "Reykjavík");
insert into Hotels (name, rating, description, city) values ('Hilton', 5, 'Skemmtilegt fjölskylduhótel með góðan morgunverð', "Reykjavík");
insert into Hotels (name, rating, description, city) values ('Leifur Eiríksson', 3, 'Með betri hótelum, en staðsetning ekki nægilega góð', "Akureyri");
insert into Rooms (id, hotel_name, hotel_city, size, price, bed_count) values (1, 'Hilton', "Reykjavík", 30, 5000, 3);
insert into Rooms (id, hotel_name, hotel_city, size, price, bed_count) values (2, 'Hilton', "Reykjavík", 30, 7500, 2);
insert into Rooms (id, hotel_name, hotel_city, size, price, bed_count) values (3, 'Holt', "Reykjavík", 20, 20000, 2);
insert into Rooms (id, hotel_name, hotel_city, size, price, bed_count) values (4, 'Leifur Eiríksson', "Akureyri", 35, 25000, 6);
insert into Rooms (id, hotel_name, hotel_city, size, price, bed_count) values (5, 'Hilton', "Reykjavík", 20, 15000, 4);
insert into Rooms (id, hotel_name, hotel_city, size, price, bed_count) values (6, 'Leifur Eiríksson', 'Akureyri', 20, 17500, 4);
insert into Rooms (id, hotel_name, hotel_city, size, price, bed_count) values (7, 'Leifur Eiríksson', 'Akureyri', 40, 19000, 5);

insert into Hotel_tags (hotel_name, hotel_city, tag_name) values ('my hotel', 105, 'Bar');
insert into Hotel_tags (hotel_name, hotel_city, tag_name) values ('my hotel', 105, 'Sundlaug');
insert into Hotel_tags (hotel_name, hotel_city, tag_name) values ('my hotel', 105, 'Nálægt strönd');
insert into Hotel_tags (hotel_name, hotel_city, tag_name) values ('my hotel', 105, 'Diskótek');
insert into Hotel_tags (hotel_name, hotel_city, tag_name) values ('my hotel', 105, 'Reiðhjólaleiga');

insert into Room_tags (room_id, tag_name) values (1, "Ísskápur");
insert into Room_tags (room_id, tag_name) values (1, "Sturta");
insert into Room_tags (room_id, tag_name) values (1, "Svalir");
insert into Room_tags (room_id, tag_name) values (1, "Wifi");
