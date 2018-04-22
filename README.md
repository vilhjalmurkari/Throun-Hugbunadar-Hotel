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
boolean | isRoomFree(Room r, long start_date, long end_date) | Athugar hvort viðeigandi herbergi sé laust
ArrayList<Room> | availableRooms(Hotel hotel, long start_date, long end_date) | Athugar hvaða herbergi í viðeigandi hóteli séu laus
User | makeUser(String name, String email) | Býr til notanda í gagnagrunni m.v. nafn og email, og skilar svo þeim notanda sem hlut
User | getUser(String name, String email) | Sækir notandann úr gagnagrunninum
void | bookRoomForUser(User user, Room room, long start_date, long end_date) | Viðeigandi herbergi hefur verið bókað fyrir veiðeigandi notanda í gagnagrunni og bókun hefur verið bætt við notandann
void | deleteUser(String email) | Eyðir notanda úr gagnagrunni með netfangið email


## AdminAPI
Skilagildi | Fallakall | Um notkun
:---|:---|:---
void | AdminAPI() | Initialiser
void | setRoomPrice(int new_price, Room room) | Verði á viðeigandi herbergi hefur verið breytt
void | setRoomPrice(int new_price, ArrayList<Room> rooms) | Verði á viðeigandi herbergjum hefur verið breytt
void | changeRoomPriceByAmount(int price_change, Room room) | Verð á viðeigandi herbergi hækkar eða lækkar um price_change
void | changeRoomPriceByPercent(double percent, Room room) | Breytir verði á viðeigandi herbergi eftir prósentu
void | addHotel(Hotel hotel) | Bætir hóteli í gagnagrunn
void | addHotels(ArrayList<Hotel> hotels) | Bætirhótelum í gagnagrunn
void | addRoomToHotel(Room room, Hotel hotel) | Bætir herbergi við viðeigandi hótel
void | deleteHotel(Hotel hotel) | Eyðir viðeigandi hóteli úr gagnagrunninum
void | deleteRoom(Room room) | Eyðir viðeigandi herbergi úr gagnagrunni



Kannski líka hvernig User, Hotel, Room, og Booking eru notuð.
