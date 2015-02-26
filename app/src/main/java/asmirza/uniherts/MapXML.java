package asmirza.uniherts;

import org.xmlpull.v1.XmlPullParser;

import java.util.HashMap;

/**
 * Created by ASMIRZA on 13/02/2015.
 */
public class MapXML {

    static XmlPullParser xpp;
    static HashMap<String,Place> buildings = new HashMap<String,Place>();

    public MapXML(XmlPullParser xpp) {
        this.xpp = xpp;
    }

    public void readXML()  {

        Building building;
        Room room;


        String name = "";
        String address = "";
        Double lat = 0.0;
        Double lang = 0.0;
        float zoom = 0;


        String roomType = "";

        building = new Building(lat, lang, name, address, zoom);

        int event;
        String text=null;
        try {
            event = xpp.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String tageName =xpp.getName();
                switch (event){
                    case XmlPullParser.START_TAG:
                        if(tageName.equals("rooms"))
                        {

                            boolean stopLoop = false;


                            int event2 = xpp.getEventType();
                            String tageName2 = "";
                            while (!stopLoop) {

                                tageName2 =xpp.getName();
                                switch (event2){
                                    case XmlPullParser.START_TAG:
                                        break;
                                    case XmlPullParser.TEXT:
                                        text = xpp.getText();
                                        break;
                                    case XmlPullParser.END_TAG:
                                        if(tageName2.equals("rooms")){
                                            stopLoop = true;
                                        }
                                        if(tageName2.equals("name")){
                                            name = text;
                                        }
                                        else if(tageName2.equals("lat")){
                                            lat = Double.parseDouble(text);
                                        }
                                        else if(tageName2.equals("lang")){
                                            lang = Double.parseDouble(text);
                                        }
                                        else if(tageName2.equals("zoom")){
                                            zoom = Float.valueOf(text);
                                        }
                                        else if(tageName2.equals("type")){
                                            roomType = text;
                                        }
                                        else if(tageName2.equals("room")){
                                            System.out.println("" + tageName);
                                            System.out.println("ROOM END ");
                                            room = new Room(lat, lang, name, zoom, building.getName(), roomType);
                                            System.out.println("ROOM READY " + room.getName());
                                            building.addRoom(room);
                                            System.out.println("ROOM added ");
                                        }
                                        else{
                                        }
                                        break;
                                }
                                if (!stopLoop) {
                                    event2 = xpp.next();
                                }
                            }
                        }
                    case XmlPullParser.TEXT:
                        text = xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if(tageName.equals("building"))
                        {
                            System.out.println("" + tageName);
                            System.out.println("BUILDING ADDED");
                            buildings.put(building.getName(),building);
                        }
                        if(tageName.equals("name")){
                            name = text;
                        }
                        else if(tageName.equals("address")){
                            address = text;
                        }
                        else if(tageName.equals("lat")){
                            lat = Double.parseDouble(text);
                        }
                        else if(tageName.equals("lang")){
                            lang = Double.parseDouble(text);
                        }
                        else if(tageName.equals("zoom")){
                            zoom = Float.valueOf(text);
                            building = new Building(lat, lang, name, address, zoom);
                            System.out.println("BUILDING READY " + building.getName());
                        }
                        else{
                        }
                        break;
                }
                event = xpp.next();

            }



        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public HashMap<String, Place> getBuildings() {
        return buildings;
    }

    public void setBuildings(HashMap<String, Place> buildings) {
        this.buildings = buildings;
    }

    public void getAllRooms(HashMap<String, Place> buildings) {

    }
}
