package model;

import java.io.Serializable;

import ulti.IHandleRss;

/**
 * Created by DEV4LIFE on 1/16/16.
 */
public class ObjectNewspaper implements Serializable{

    private String titleSection;
    private int iconSection;
    private CharSequence[] titlePaper;
    private CharSequence[] titleRssPaper;
    private String urlPaper;
    private IHandleRss handRss;

    public ObjectNewspaper() {
    }

    public ObjectNewspaper(String titleSection, int iconSection, CharSequence[] titlePaper, CharSequence[] titleRssPaper, String urlPaper, IHandleRss handRss) {
        this.titleSection = titleSection;
        this.iconSection = iconSection;
        this.titlePaper = titlePaper;
        this.titleRssPaper = titleRssPaper;
        this.urlPaper = urlPaper;
        this.handRss = handRss;
    }

    public CharSequence[] getTitlePaper() {
        return titlePaper;
    }

    public CharSequence[] getTitleRssPaper() {
        return titleRssPaper;
    }

    public String getUrlPaper() {
        return urlPaper;
    }

    public IHandleRss getHandRss() {
        return handRss;
    }

    public String getTitleSection() {
        return titleSection;
    }

    public int getIconSection() {
        return iconSection;
    }
}
