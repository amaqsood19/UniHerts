package asmirza.uniherts;

/**
 * Created by ASMIRZA on 23/04/2015.
 */
public class MenuDrawerItem {
    private String title;
    private int icon;

    public MenuDrawerItem() {
    }

    public MenuDrawerItem(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return this.icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

}