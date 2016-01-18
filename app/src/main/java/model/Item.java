package model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by DEV4LIFE on 1/12/16.
 */
@Root(name = "item",strict = false)
public class Item {

    @Element(name = "title",required = true)
    private String title;

    @Element(name = "description",required = true)
    private String description;

    @Element(name = "pubDate",required = false)
    private String pubDate;

    @Element(name = "link",required = true)
    private String link;

    @Element(name = "guid",required = false)
    private String guid;

//    @Element(name = "slash",required = false)
//    private String slash;

    public Item() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}
