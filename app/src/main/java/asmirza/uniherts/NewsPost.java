package asmirza.uniherts;

import android.graphics.Bitmap;

public class NewsPost {

    String message;
    Bitmap picture;
    String name;
    String description;
    String pictureLink;
    String dateTimeCreated;

    public NewsPost(String message, Bitmap picture, String name, String description, String pictureLink, String dateTimeCreated) {
        this.message = message;
        this.picture = picture;
        this.name = name;
        this.description = description;
        this.pictureLink = pictureLink;
        this.dateTimeCreated = dateTimeCreated;
    }

    public NewsPost() {

    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPictureName() {
        return name;
    }

    public void setPictureName(String name) {
        this.name = name;
    }

    public String getPictureDescription() {
        return description;
    }

    public void setPictureDescription(String description) {
        this.description = description;
    }

    public String getPictureLink() {
        return pictureLink;
    }

    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink;
    }

    public String getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(String dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    @Override
    public String toString() {
        return "NewsPost{" +
                "message='" + message + '\'' +
                ", picture=" + picture +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", pictureLink='" + pictureLink + '\'' +
                ", dateTimeCreated='" + dateTimeCreated + '\'' +
                '}';
    }
}
