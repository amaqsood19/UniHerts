package asmirza.uniherts;

/**
 * Created by ASMIRZA on 13/02/2015.
 */
public class Room extends Place {


    private String inBuilding;
    private String type;

    public Room(Double lat, Double lang, String name, float zoom,String type) {
        super(lat, lang, name, zoom);
        this.type = type;
    }

    public Room(Double lat, Double lang, String name, float zoom,String inBuilding,String type) {
        super(lat, lang, name, zoom);
        this.inBuilding = inBuilding;
        this.type = type;
    }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInBuilding() {
        return inBuilding;
    }

    public void setInBuilding(String inBuilding) {
        this.inBuilding = inBuilding;
    }


    @Override
    public String toString() {
        return "Room{" +
                "inBuilding='" + inBuilding + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
