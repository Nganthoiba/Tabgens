package bean;

/**
 * Created by Developer on 07-09-2016.
 */
public class Bookmark_Article_Bean {
    String Id,CreateAt,Name,article_detail,Image,images_url,detail_url,no_of_likes,is_bookmarked_by_you,is_liked_by_you;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCreateAt() {
        return CreateAt;
    }

    public void setCreateAt(String createAt) {
        CreateAt = createAt;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getArticle_detail() {
        return article_detail;
    }

    public void setArticle_detail(String article_detail) {
        this.article_detail = article_detail;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getImages_url() {
        return images_url;
    }

    public void setImages_url(String images_url) {
        this.images_url = images_url;
    }

    public String getDetail_url() {
        return detail_url;
    }

    public void setDetail_url(String detail_url) {
        this.detail_url = detail_url;
    }

    public String getNo_of_likes() {
        return no_of_likes;
    }

    public void setNo_of_likes(String no_of_likes) {
        this.no_of_likes = no_of_likes;
    }

    public String getIs_bookmarked_by_you() {
        return is_bookmarked_by_you;
    }

    public void setIs_bookmarked_by_you(String is_bookmarked_by_you) {
        this.is_bookmarked_by_you = is_bookmarked_by_you;
    }

    public String getIs_liked_by_you() {
        return is_liked_by_you;
    }

    public void setIs_liked_by_you(String is_liked_by_you) {
        this.is_liked_by_you = is_liked_by_you;
    }

    public Bookmark_Article_Bean(String id, String name, String article_detail, String images_url, String detail_url, String no_of_likes) {
        this.Id = id;
        this.Name = name;
        this.article_detail = article_detail;
        this.images_url = images_url;
        this.detail_url = detail_url;
        this.no_of_likes = no_of_likes;
    }

    public Bookmark_Article_Bean() {
    }
}
