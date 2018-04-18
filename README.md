# Throun-Hugbunadar-Hotel
Verkefni í Þróun Hugbúnaðar.


-UML rit fyrir Hótelleitina okkar er að finna í UML.   
-API fyrir kóðann má finna undir HotelAPI.md en þar er API fyrir Hótelleit (*HotelAPI*), Hótel (*Hotel*), Herbergi (*Room*), Notendur (*User*), og Bókanir (*Booking*).    


### Að keyra hótelleit
Sæktu /src/ og keyrðu ```build.sh run``` eða ```build.bat run``` eftir því hvort þú sért á Unix eða Windows.   
Það ætti að birtast notendaviðmót sem leyfir þér að velja hótel og síðan herbergi.



## HotelAPI
Skilagildi | Fallakall | Um notkun
:---|:---|:---
void | HotelAPI() | Initialiser
Hotel | getHotel(String name, String city) | Sækir eina hótelið sem uppfyllir tvenndina (nafn,borg)
ArrayList<Hotel> | hotelSearch(String city_or_name, int min_rating, int max_rating | Leitar að hótelum með leitarstreng city_or_name en síar skv. fjöldi stjarna sem hótelin hafa [min_rating;max_rating]
User | makeUster(String name, String email) | Býr til notanda í gagnagrunni m.v. nafn og email, og skilar svo þeim notanda sem hlut
void | deleteUser(String email) | Eyðir notanda úr gagnagrunni með netfangið email
void | bookRoomForUser(User u, Room r) | Bókar herbergi r fyrir notanda u í gagnagrunni; bókun bætt við notanda hlut
void | 





Líka fyrir AdminAPI


Kannski líka hvernig User, Hotel, Room, og Booking eru notuð.
