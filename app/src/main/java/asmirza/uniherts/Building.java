package asmirza.uniherts;

import java.util.HashMap;

/**
 * Created by ASMIRZA on 13/02/2015.
 */
public class Building extends Place {

    private HashMap<String,Place> rooms;
    private String address;


    public Building(Double lat, Double lang, String name, float zoom, String address, HashMap<String, Place> rooms) {
        super(lat, lang, name, zoom);
        this.address = address;
        this.rooms = rooms;
        rooms = new HashMap<String,Place>();
    }

    public Building(Double lat, Double lang, String name, String address, float zoom) {
        super(lat, lang, name, zoom);
        this.address = address;
        rooms = new HashMap<String,Place>();
    }

    public HashMap<String, Place> getRooms() {
        return rooms;
    }

    public void setRooms(HashMap<String, Place> rooms) {
        this.rooms = rooms;
    }

    public Room getRoom(String roomNo)
    {
        return (Room) rooms.get(roomNo);
    }

    public void addRoom(Room room)
    {
        rooms.put(room.getName(),room);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Building{" + super.toString() +
                "rooms=" + rooms +
                ", address='" + address + '\'' +
                '}';
    }
}
