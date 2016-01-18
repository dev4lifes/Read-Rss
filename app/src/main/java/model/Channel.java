package model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by DEV4LIFE on 1/12/16.
 */

@Root(name = "channel",strict = false)
public class Channel {

    @ElementList(name = "item",inline = true)
    private List<Item> items;
    public List<Item> getItems() {
        return items;
    }

}
