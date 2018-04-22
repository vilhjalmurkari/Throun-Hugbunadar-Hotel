# Throun-Hugbunadar-Hotel
Verkefni � �r�un Hugb�na�ar.


-UML rit fyrir H�telleitina okkar er a� finna � UML.   
-API fyrir k��ann m� finna undir HotelAPI.md en �ar er API fyrir H�telleit (*HotelAPI*), H�tel (*Hotel*), Herbergi (*Room*), Notendur (*User*), og B�kanir (*Booking*).    


### A� keyra h�telleit
S�ktu ```/src/``` og keyr�u ```build.sh run``` e�a ```build.bat run``` eftir �v� hvort �� s�rt � Unix e�a Windows.   
�a� �tti a� birtast notendavi�m�t sem leyfir ��r a� velja h�tel og s��an herbergi.

----

## HotelAPI
Skilagildi | Fallakall | Um notkun
:---|:---|:---
void | HotelAPI() | Initialiser
Hotel | getHotel(String name, String city) | S�kir eina h�teli� sem uppfyllir tvenndina (nafn,borg)
ArrayList\<Hotel\> | hotelSearch(String city_or_name, int min_rating, int max_rating, long start_date, long end_date) | Leitar a� h�telum me� leitarstreng city_or_name en s�ar skv. fj�ldi stjarna sem h�telin hafa [min_rating;max_rating] og eru laus
boolean | isRoomFree(Room r, long start_date, long end_date) | Athugar hvort vi�eigandi herbergi s� laust
ArrayList\<Room\> | availableRooms(Hotel hotel, long start_date, long end_date) | Athugar hva�a herbergi � vi�eigandi h�teli s�u laus
User | makeUser(String name, String email) | B�r til notanda � gagnagrunni m.v. nafn og email, og skilar svo �eim notanda sem hlut
User | getUser(String name, String email) | S�kir notandann �r gagnagrunninum
void | bookRoomForUser(User user, Room room, long start_date, long end_date) | Vi�eigandi herbergi hefur veri� b�ka� fyrir vei�eigandi notanda � gagnagrunni og b�kun hefur veri� b�tt vi� notandann
void | deleteUser(String email) | Ey�ir notanda �r gagnagrunni me� netfangi� email

----

## AdminAPI
Skilagildi | Fallakall | Um notkun
:---|:---|:---
void | AdminAPI(User user) | Initialiser; user ver�ur a� hafa admin r�ttindi til a� geta nota� f�ll
void | escalateUserPriveleges(User user) | Gefur user admin r�ttindi
void | descalateUserPriveleges(User user) | L�tur user missa admin r�ttindi
void | setRoomPrice(int new_price, Room room) | Ver�i � vi�eigandi herbergi hefur veri� breytt
void | setRoomPrice(int new_price, ArrayList\<Room\> rooms) | Ver�i � vi�eigandi herbergjum hefur veri� breytt
void | changeRoomPriceByAmount(int price_change, Room room) | Ver� � vi�eigandi herbergi h�kkar e�a l�kkar um price_change
void | changeRoomPriceByPercent(double percent, Room room) | Breytir ver�i � vi�eigandi herbergi eftir pr�sentu
void | addHotel(Hotel hotel) | B�tir h�teli � gagnagrunn
void | addHotels(ArrayList\<Hotel\> hotels) | B�tirh�telum � gagnagrunn
void | addRoomToHotel(Room room, Hotel hotel) | B�tir herbergi vi� vi�eigandi h�tel
void | deleteHotel(Hotel hotel) | Ey�ir vi�eigandi h�teli �r gagnagrunninum
void | deleteRoom(Room room) | Ey�ir vi�eigandi herbergi �r gagnagrunni
void | changeHotelDescription(String description, Hotel hotel) | Breyti l�singu � h�teli

----

## Hotel
#### Breytur
Tag | Breytunafn | Um breytu
:---|:---:|---
String | name | Nafn h�tels
int | rating | Fj�ldi stjarna sem h�teli� hefur
String | description | L�sing � h�telinu
String | city | Nafn borgar sem h�teli� er sta�sett �
ArrayList\<String\> | tags | Listi af t�gum sem h�teli� hefur
ArrayList\<Room\> | rooms | Listi af herbergjum � h�teli

#### F�ll
Skilagildi | Fallakall | Um notkun
:---|:---|:---
void | Hotel(String name, int rating, String description, String city, ArrayList\<String\> tags, ArrayList\<Room\> rooms) | Initialiser
void | printHotel() | Prentar uppl�singar um h�teli� � skynsamlegan m�ta

----

## Room
#### Breytur
Tag | Breytunafn | Um breytu
:---|:---:|---
String | name | Nafn h�tels
int | id | Au�kenni �essa herbergis
int | size | Fermetrafj�ldi �essa herbergis
int | price | Ver� �essa herbergis
int | bed_count | Fj�ldi r�ma � �essu herbergi
ArrayList\<String\> | tags | T�g sem �etta herbergi hefur

#### F�ll
Skilagildi | Fallakall | Um notkun
:---|:---|:---
void | Room(int size, int bed_count, int price, ArrayList\<String\> tags) | Initialiser


