package model;

/**
 * Created by DEV4LIFE on 1/13/16.
 */
public class NewsItem {
    private String avatar;
    private String title;
    private String link;
    private String pubDate;
    private String description;

    public NewsItem() {
    }

    public NewsItem(String avatar, String title, String link, String pubDate, String description) {
        this.avatar = avatar;
        this.title = title;
        this.link = link;
        this.pubDate = pubDate;
        this.description = description;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
