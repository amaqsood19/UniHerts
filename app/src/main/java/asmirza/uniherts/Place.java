package asmirza.uniherts;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by ASMIRZA on 13/02/2015.
 */
public class Place {

    private Double lat;
    private Double lang;
    private String name;
    private float zoom;
    private MarkerOptions marker;

    public Place() {

    }


    public Place(Double lat, Double lang, String name, float zoom) {
        this.lat = lat;
        this.lang = lang;
        this.name = name;
        this.zoom = zoom;
        this.marker = new MarkerOptions().position(new LatLng(lat, lang)).title(name);
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLang() {
        return lang;
    }

    public void setLang(Double lang) {
        this.lang = lang;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getZoom() {
        return zoom;
    }

    public void setZoom(float zoom) {
        this.zoom = zoom;
    }

    public MarkerOptions getMarker() {
        return marker;
    }

    public void setMarker(MarkerOptions marker) {
        this.marker = marker;
    }

    @Override
    public String toString() {
        return "Place{" +
                "lat=" + lat +
                ", lang=" + lang +
                ", name='" + name + '\'' +
                ", zoom=" + zoom +
                ", marker=" + marker +
                '}';
    }
}
