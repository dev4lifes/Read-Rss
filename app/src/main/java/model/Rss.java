package model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by DEV4LIFE on 1/12/16.
 */
//@Namespace(reference = "http://purl.org/rss/1.0/modules/slash/",prefix = "slash")
@Root(name = "rss",strict = false)
public class Rss {
    @Element(name = "channel")
    private Channel channel;

    public Channel getChannel() {
        return channel;
    }
}
