# Throun-Hugbunadar-Hotel
Verkefni � �r�un Hugb�na�ar.


-UML rit fyrir H�telleitina okkar er a� finna � UML.   
-API fyrir k��ann m� finna undir HotelAPI.md en �ar er API fyrir H�telleit (*HotelAPI*), H�tel (*Hotel*), Herbergi (*Room*), Notendur (*User*), og B�kanir (*Booking*).    


### A� keyra h�telleit
S�ktu /src/ og keyr�u ```build.sh run``` e�a ```build.bat run``` eftir �v� hvort �� s�rt � Unix e�a Windows.   
�a� �tti a� birtast notendavi�m�t sem leyfir ��r a� velja h�tel og s��an herbergi.



## HotelAPI
Skilagildi | Fallakall | Um notkun
:---|:---|:---
void | HotelAPI() | Initialiser
Hotel | getHotel(String name, String city) | S�kir eina h�teli� sem uppfyllir tvenndina (nafn,borg)
ArrayList<Hotel> | hotelSearch(String city_or_name, int min_rating, int max_rating | Leitar a� h�telum me� leitarstreng city_or_name en s�ar skv. fj�ldi stjarna sem h�telin hafa [min_rating;max_rating]
User | makeUster(String name, String email) | B�r til notanda � gagnagrunni m.v. nafn og email, og skilar svo �eim notanda sem hlut
void | deleteUser(String email) | Ey�ir notanda �r gagnagrunni me� netfangi� email
void | bookRoomForUser(User u, Room r) | B�kar herbergi r fyrir notanda u � gagnagrunni; b�kun b�tt vi� notanda hlut
void | 





L�ka fyrir AdminAPI


Kannski l�ka hvernig User, Hotel, Room, og Booking eru notu�.
