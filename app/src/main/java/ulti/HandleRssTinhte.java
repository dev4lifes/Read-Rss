package ulti;

import java.io.Serializable;

/**
 * Created by DEV4LIFE on 1/17/16.
 */
public class HandleRssTinhte implements IHandleRss,Serializable{

    public HandleRssTinhte(){

    }

    public static IHandleRss newInstance(){
        IHandleRss handle = new HandleRssTinhte();
        return handle;
    }
    @Override
    public String getDescriptionFromOriginDes(String str) {
        String temp = "";
        int pos = str.indexOf("<br />");
        if(pos!=-1){
            temp = str.substring(0,pos);
        }else {
            temp = str;
        }
        return temp;
    }

    @Override
    public String getLinkImageFromOriginDes(String str) {
        String temp = "";

        int posStart = str.indexOf("src");
        int posEnd = str.indexOf("jpg");
        if(posStart!=-1 && posEnd!=-1){
            temp = str.substring(posStart+5, posEnd + 3);
        }else {
            temp = "";
        }

//        String link = temp.replace("\"", "");
        return temp;
    }
}
