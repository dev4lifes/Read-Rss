package ulti;

import java.io.Serializable;

/**
 * Created by DEV4LIFE on 1/18/16.
 */
public class HandleRssGeneral implements IHandleRss,Serializable {

    public HandleRssGeneral(){

    }

    public static IHandleRss newInstance(){
        IHandleRss handle = new HandleRssGeneral();
        return handle;
    }
    @Override
    public String getDescriptionFromOriginDes(String des) {
        return null;
    }

    @Override
    public String getLinkImageFromOriginDes(String des) {
        return null;
    }
}
