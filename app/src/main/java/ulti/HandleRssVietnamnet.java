package ulti;

import java.io.Serializable;

/**
 * Created by DEV4LIFE on 1/14/16.
 */
public class HandleRssVietnamnet implements IHandleRss,Serializable {

    public HandleRssVietnamnet(){

    }
    public static IHandleRss newInstance() {
        IHandleRss handle = new HandleRssVietnamnet();
        return handle;
    }
    @Override
    public String getDescriptionFromOriginDes(String str) {
        int pos = str.indexOf("<br />");
        return str.substring(0,pos);
    }

    @Override
    public String getLinkImageFromOriginDes(String str) {
        int posStart = str.indexOf("src");
        int endStart = str.indexOf("?w=220");
        String temp = str.substring(posStart+4,endStart);
        String link = temp.replace("\"", "");

        return link.replace(" /", "");
    }
}
