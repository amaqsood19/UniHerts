package asmirza.uniherts;

/**
 * Created by ASMIRZA on 13/02/2015.
 */
public class Parking extends Place {

        private String spaceDetails;
        private String code;
        private String type;
        private String features;
        private String address;
        private String restrictionsTime;
        private String parkingRestrictions;

    public Parking(Double lat, Double lang, float zoom, String name, String code, String spaceDetails, String type, String features, String address, String restrictionsTime, String parkingRestrictions) {
        super(lat, lang,  name, zoom);
        this.code = code;
        this.spaceDetails = spaceDetails;
        this.type = type;
        this.features = features;
        this.address = address;
        this.restrictionsTime = restrictionsTime;
        this.parkingRestrictions = parkingRestrictions;
    }

    public Parking() {

    }

    public String getSpaceDetails() {
        return spaceDetails;
    }

    public void setSpaceDetails(String spaceDetails) {
        this.spaceDetails = spaceDetails;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRestrictionsTime() {
        return restrictionsTime;
    }

    public void setRestrictionsTime(String restrictionsTime) {
        this.restrictionsTime = restrictionsTime;
    }

    public String getParkingRestrictions() {
        return parkingRestrictions;
    }

    public void setParkingRestrictions(String parkingRestrictions) {
        this.parkingRestrictions = parkingRestrictions;
    }

    @Override
    public String toString() {
        return "Parking{" + super.toString() +
                "spaceDetails='" + spaceDetails + '\'' +
                ", code='" + code + '\'' +
                ", type='" + type + '\'' +
                ", features='" + features + '\'' +
                ", address='" + address + '\'' +
                ", parkingRestrictions='" + parkingRestrictions + '\'' +
                ", restrictionsTime='" + restrictionsTime + '\'' +
                "} " ;
    }
}
