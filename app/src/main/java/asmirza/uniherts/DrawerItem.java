package asmirza.uniherts;

/**
 * Created by ASMIRZA on 18/04/2015.
 */
public class DrawerItem {

    String ItemName;
    int imgResID;
    boolean selected = false;

    public DrawerItem(String itemName, int imgResID, boolean selected) {
        ItemName = itemName;
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

}