package asmirza.uniherts;

/**
 * Created by ASMIRZA on 13/02/2015.
 */
public class Parking extends Place {

    private String address;
    private String code;
    private int type;
    private Boolean isDisable;
    private String spaceDetails;
    private String features;
    private String parkingRestrictions;
    private String restrictionsTime;

    public Parking(Double lat, Double lang, String name, float zoom, String address, String code, int type, Boolean isDisable, String spaceDetails, String features, String parkingRestrictions, String restrictionsTime) {
        super(lat, lang, name, zoom);
        this.address = address;
        this.code = code;
        this.type = type;
        this.isDisable = isDisable;
        this.spaceDetails = spaceDetails;
        this.features = features;
        this.parkingRestrictions = parkingRestrictions;
        this.restrictionsTime = restrictionsTime;
    }

    public Parking() {

    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeString() {
        String typeString = "";

        if (type == 1) {
            typeString = "Visitor Parking";
        } else if (type == 2) {
            typeString = "Student Parking (Pay and Display)";
        } else if (type == 3) {
            typeString = "Student Residence Parking";
        } else if (type == 4) {
            typeString = "Staff Parking (Fee Paying)";
        }

        return typeString;
    }

    public Boolean getIsDisable() {
        return isDisable;
    }

    public void setIsDisable(Boolean isDisable) {
        this.isDisable = isDisable;
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
                "} ";
    }
}
