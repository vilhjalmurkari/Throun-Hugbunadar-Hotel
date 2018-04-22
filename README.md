# Throun-Hugbunadar-Hotel
Verkefni í Þróun Hugbúnaðar.


-UML rit fyrir Hótelleitina okkar er að finna í UML.   
-API fyrir kóðann má finna undir HotelAPI.md en þar er API fyrir Hótelleit (*HotelAPI*), Hótel (*Hotel*), Herbergi (*Room*), Notendur (*User*), og Bókanir (*Booking*).    


### Að keyra hótelleit
Sæktu ```/src/``` og keyrðu ```build.sh run``` eða ```build.bat run``` eftir því hvort þú sért á Unix eða Windows.   
Það ætti að birtast notendaviðmót sem leyfir þér að velja hótel og síðan herbergi.

----

## HotelAPI
Skilagildi | Fallakall | Um notkun
:---|:---|:---
void | HotelAPI() | Initialiser
Hotel | getHotel(String name, String city) | Sækir eina hótelið sem uppfyllir tvenndina (nafn,borg)
ArrayList\<Hotel\> | hotelSearch(String city_or_name, int min_rating, int max_rating, long start_date, long end_date) | Leitar að hótelum með leitarstreng city_or_name en síar skv. fjöldi stjarna sem hótelin hafa [min_rating;max_rating] og eru laus
boolean | isRoomFree(Room r, long start_date, long end_date) | Athugar hvort viðeigandi herbergi sé laust
ArrayList\<Room\> | availableRooms(Hotel hotel, long start_date, long end_date) | Athugar hvaða herbergi í viðeigandi hóteli séu laus
User | makeUser(String name, String email) | Býr til notanda í gagnagrunni m.v. nafn og email, og skilar svo þeim notanda sem hlut
User | getUser(String name, String email) | Sækir notandann úr gagnagrunninum
void | bookRoomForUser(User user, Room room, long start_date, long end_date) | Viðeigandi herbergi hefur verið bókað fyrir veiðeigandi notanda í gagnagrunni og bókun hefur verið bætt við notandann
void | deleteUser(String email) | Eyðir notanda úr gagnagrunni með netfangið email

----

## AdminAPI
Skilagildi | Fallakall | Um notkun
:---|:---|:---
void | AdminAPI(User user) | Initialiser; user verður að hafa admin réttindi til að geta notað föll
void | escalateUserPriveleges(User user) | Gefur user admin réttindi
void | descalateUserPriveleges(User user) | Lætur user missa admin réttindi
void | setRoomPrice(int new_price, Room room) | Verði á viðeigandi herbergi hefur verið breytt
void | setRoomPrice(int new_price, ArrayList\<Room\> rooms) | Verði á viðeigandi herbergjum hefur verið breytt
void | changeRoomPriceByAmount(int price_change, Room room) | Verð á viðeigandi herbergi hækkar eða lækkar um price_change
void | changeRoomPriceByPercent(double percent, Room room) | Breytir verði á viðeigandi herbergi eftir prósentu
void | addHotel(Hotel hotel) | Bætir hóteli í gagnagrunn
void | addHotels(ArrayList\<Hotel\> hotels) | Bætirhótelum í gagnagrunn
void | addRoomToHotel(Room room, Hotel hotel) | Bætir herbergi við viðeigandi hótel
void | deleteHotel(Hotel hotel) | Eyðir viðeigandi hóteli úr gagnagrunninum
void | deleteRoom(Room room) | Eyðir viðeigandi herbergi úr gagnagrunni
void | changeHotelDescription(String description, Hotel hotel) | Breyti lýsingu á hóteli

----

## Hotel
#### Breytur
Tag | Breytunafn | Um breytu
:---|:---:|---
String | name | Nafn hótels
int | rating | Fjöldi stjarna sem hótelið hefur
String | description | Lýsing á hótelinu
String | city | Nafn borgar sem hótelið er staðsett í
ArrayList\<String\> | tags | Listi af tögum sem hótelið hefur
ArrayList\<Room\> | rooms | Listi af herbergjum í hóteli

#### Föll
Skilagildi | Fallakall | Um notkun
:---|:---|:---
void | Hotel(String name, int rating, String description, String city, ArrayList\<String\> tags, ArrayList\<Room\> rooms) | Initialiser
void | printHotel() | Prentar upplýsingar um hótelið á skynsamlegan máta

----

## Room
#### Breytur
Tag | Breytunafn | Um breytu
:---|:---:|---
String | name | Nafn hótels
int | id | Auðkenni þessa herbergis
int | size | Fermetrafjöldi þessa herbergis
int | price | Verð þessa herbergis
int | bed_count | Fjöldi rúma í þessu herbergi
ArrayList\<String\> | tags | Tög sem þetta herbergi hefur

#### Föll
Skilagildi | Fallakall | Um notkun
:---|:---|:---
void | Room(int size, int bed_count, int price, ArrayList\<String\> tags) | Initialiser


