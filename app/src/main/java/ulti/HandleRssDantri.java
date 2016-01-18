package ulti;

import java.io.Serializable;

/**
 * Created by DEV4LIFE on 1/14/16.
 */
public class HandleRssDantri implements IHandleRss,Serializable {

    public HandleRssDantri(){

    }
    public static IHandleRss newInstance(){
        IHandleRss handle = new HandleRssDantri();
        return handle;
    }
    @Override
    public String getDescriptionFromOriginDes(String des) {
        return "";
    }

    @Override
    public String getLinkImageFromOriginDes(String str) {
        String temp="";
        String link="";
        int posStart = str.indexOf("src");
        int endStart = str.indexOf("></a>");
        if (posStart!=-1 && endStart!=-1) {
            temp = str.substring(posStart + 4, endStart);
        }else {
            temp="";
        }
        if (!temp.equals("")){
            link = temp.replace("\"", "");
        }else {
            link = "";
        }
        return link;
    }
}
