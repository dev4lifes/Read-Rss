package ulti;

import java.io.Serializable;

/**
 * Created by DEV4LIFE on 1/13/16.
 */
public class HandleRssVnexpress implements IHandleRss, Serializable {

    public HandleRssVnexpress() {

    }

    public static IHandleRss newInstance() {
        IHandleRss handle = new HandleRssVnexpress();
        return handle;
    }


    @Override
    public String getDescriptionFromOriginDes(String str) {
        int pos = str.indexOf("</br>");
        String temp = "";
        if (pos != -1) {
            temp = str.substring(pos + 5, str.length());
        } else {
            temp = "";
        }
        return temp;
    }

    @Override
    public String getLinkImageFromOriginDes(String str) {
        int posStart = str.indexOf("src");
        int endStart = str.indexOf("></a>");
        String temp = str.substring(posStart + 4, endStart);
        String link = temp.replace("\"", "");
        return link;
    }
}
