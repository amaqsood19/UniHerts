package asmirza.uniherts;

import java.util.ArrayList;

/**
 * Created by ASMIRZA on 13/02/2015.
 */
public class Building extends Place {

    private ArrayList<Room> rooms;
    private String address;


    public Building(Double lat, Double lang, String name, float zoom, String address, ArrayList<Room> rooms) {
        super(lat, lang, name, zoom);
        this.address = address;
        this.rooms = rooms;
    }

    public Building(Double lat, Double lang, String name, String address, float zoom) {
        super(lat, lang, name, zoom);
        this.address = address;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    public void addRoom(Room room)
    {
        rooms.add(room);
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
