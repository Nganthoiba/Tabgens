package models.cme_tab_model;

import java.util.HashMap;
import java.util.Map;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("Id")
    private String id;
    @SerializedName("CreateAt")
    private double createAt;
    @SerializedName("DeleteAt")
    private double deleteAt;
    @SerializedName("UpdateAt")
    private double updateAt;
    @SerializedName("Name")
    private String name;
    @SerializedName("Textual_content")
    private String textualContent;
    @SerializedName("Images")
    private String images;
    @SerializedName("external_link_url")
    private String externalLinkUrl;
    @SerializedName("Active")
    private String active;
    @SerializedName("short_description")
    private String shortDescription;
    @SerializedName("images_url")
    private String imagesUrl;
    @SerializedName("detail_url")
    private String detailUrl;

    @SerializedName("no_of_likes")
    private int no_of_likes;

    @SerializedName("is_liked_by_you")
    private String is_liked_by_you;

    @SerializedName("is_bookmarked_by_you")
    private String is_bookmarked_by_you;

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

    public double getDeleteAt() {
        return deleteAt;
    }

    public void setDeleteAt(double deleteAt) {
        this.deleteAt = deleteAt;
    }

    public double getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(double updateAt) {
        this.updateAt = updateAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTextualContent() {
        return textualContent;
    }

    public void setTextualContent(String textualContent) {
        this.textualContent = textualContent;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getExternalLinkUrl() {
        return externalLinkUrl;
    }

    public void setExternalLinkUrl(String externalLinkUrl) {
        this.externalLinkUrl = externalLinkUrl;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getImagesUrl() {
        return imagesUrl;
    }

    public void setImagesUrl(String imagesUrl) {
        this.imagesUrl = imagesUrl;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }



}
