package cs.dawson.dawsonelectriccurrents.cancelled;

import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import cs.dawson.dawsonelectriccurrents.beans.CancelledClass;

public class RssFeeder
{
    private String url;

    public RssFeeder(String url)
    {
        this.url = url;
    }

    public List<CancelledClass> getItems() throws Exception {

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();

        RssParseHandler handler = new RssParseHandler();

        saxParser.parse(url, handler);

        return handler.getItems();

    }
}
