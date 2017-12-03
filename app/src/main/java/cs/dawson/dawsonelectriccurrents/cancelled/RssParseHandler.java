package cs.dawson.dawsonelectriccurrents.cancelled;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;
import cs.dawson.dawsonelectriccurrents.beans.CancelledClass;

public class RssParseHandler extends DefaultHandler
{
    private List<CancelledClass> items;
    private CancelledClass currentItem;
    private boolean parsingTitle;
    private boolean parsingCourse;
    private boolean parsingTeacher;
    private boolean parsingDateTimeCancelled;

    public RssParseHandler()
    {
        items = new ArrayList<CancelledClass>();
    }

    public List<CancelledClass> getItems()
    {
        return items;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if ("item".equals(qName)) {
            currentItem = new CancelledClass();
        } else if ("title".equals(qName)) {
            parsingTitle = true;
        } else if ("course".equals(qName)) {
            parsingCourse = true;
        }else if ("teacher".equals(qName)) {
            parsingTeacher = true;
        }else if ("pubDate".equals(qName)) {
            parsingDateTimeCancelled = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("item".equals(qName)) {
            items.add(currentItem);
            currentItem = null;
        } else if ("title".equals(qName)) {
            parsingTitle = false;
        } else if ("course".equals(qName)) {
            parsingCourse = false;
        }else if ("teacher".equals(qName)) {
            parsingTeacher = false;
        }else if ("pubDate".equals(qName)) {
            parsingDateTimeCancelled = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (parsingTitle)
        {
            if (currentItem != null)
            {
                currentItem.setTitle(new String(ch, start, length));
                parsingCourse = false;
            }
        }
        else if (parsingCourse)
        {
            if (currentItem != null)
            {
                currentItem.setCourse(new String(ch, start, length));
                parsingCourse = false;
            }
        }
        else if (parsingTeacher)
        {
            if (currentItem != null)
            {
                currentItem.setTeacher(new String(ch, start, length));
                parsingTeacher = false;
            }
        }
        else if (parsingDateTimeCancelled)
        {
            if (currentItem != null)
            {
                currentItem.setDateTimeCancelled(new String(ch, start, length));
                parsingDateTimeCancelled = false;
            }
        }
    }
}