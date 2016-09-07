package models.news_model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Item implements Serializable {

    @SerializedName("Id")
    private String id;
    @SerializedName("CreateAt")
    private double createAt;
    @SerializedName("title")
    private String title;
    @SerializedName("headline")
    private String headline;
    @SerializedName("Details")
    private String details;
    @SerializedName("Image")
    private String image;

    @SerializedName("image_url")
    private String imageUrl;

    @SerializedName("no_of_likes")
    private int no_of_likes;

    @SerializedName("is_liked_by_you")
    private String is_liked_by_you;

    @SerializedName("is_bookmarked_by_you")
    private String is_bookmarked_by_you;

    @SerializedName("Attachments")
    private List<Attachment> attachments = new ArrayList<Attachment>();

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public int getNo_of_likes() {
        return no_of_likes;
    }

    public void setNo_of_likes(int no_of_likes) {
        this.no_of_likes = no_of_likes;
    }

    public String getIs_liked_by_you() {
        return is_liked_by_you;
    }

    public void setIs_liked_by_you(String is_liked_by_you) {
        this.is_liked_by_you = is_liked_by_you;
    }

    public String getIs_bookmarked_by_you() {
        return is_bookmarked_by_you;
    }

    public void setIs_bookmarked_by_you(String is_bookmarked_by_you) {
        this.is_bookmarked_by_you = is_bookmarked_by_you;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getCreateAt() {
        return createAt;
    }

    public void setCreateAt(double createAt) {
        this.createAt = createAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


}
