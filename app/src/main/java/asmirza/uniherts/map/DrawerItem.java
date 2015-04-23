package asmirza.uniherts.map;

/**
 * Created by ASMIRZA on 18/04/2015.
 */
public class DrawerItem {

    String ItemName;
    String type;
    int imgResID;
    boolean selected = false;

    public DrawerItem(String itemName, String type, int imgResID, boolean selected) {
        ItemName = itemName;
        this.type = type;
        this.imgResID = imgResID;
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public int getImgResID() {
        return imgResID;
    }

    public void setImgResID(int imgResID) {
        this.imgResID = imgResID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}