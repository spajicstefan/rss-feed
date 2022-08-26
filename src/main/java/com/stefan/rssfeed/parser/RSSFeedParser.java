package com.stefan.rssfeed.parser;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

import com.stefan.rssfeed.model.Feed;
import com.stefan.rssfeed.model.FeedItem;

public class RSSFeedParser {

    static final String TITLE = "title";
    static final String DESCRIPTION = "description";
    static final String LANGUAGE = "language";
    static final String COPYRIGHT = "copyright";
    static final String LINK = "link";
    static final String AUTHOR = "creator";
    static final String ITEM = "item";
    static final String PUB_DATE = "pubDate";
    static final String GUID = "guid";
    static final String CONTENT = "content";


    final URL url;

    public RSSFeedParser(final String feedUrl) {
        try {
            this.url = new URL(feedUrl);
        } catch (final MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public Feed readFeed() {
        Feed feed = null;
        try {
            boolean isFeedHeader = true;

            String description = "";
            String title = "";
            String link = "";
            String language = "";
            String copyright = "";
            String author = "";
            String pubdate = "";
            String guid = "";
            String image = "";

            final XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            final InputStream in = read();
            final XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                if (event.isStartElement()) {
                    final String localPart = event.asStartElement().getName()
                            .getLocalPart();
                    switch (localPart) {
                        case ITEM:
                            if (isFeedHeader) {
                                isFeedHeader = false;
                                feed = new Feed()
                                        .setTitle(title)
                                        .setLink(link)
                                        .setDescription(description)
                                        .setLanguage(language)
                                        .setCopyright(copyright)
                                        .setPubDate(pubdate);
                            }
                            event = eventReader.nextEvent();
                            break;
                        case TITLE:
                            title = getCharacterData(event, eventReader);
                            break;
                        case DESCRIPTION:
                            description = getCharacterData(event, eventReader);
                            break;
                        case LINK:
                            link = getAttributeData(event, "href");
                            break;
                        case GUID:
                            guid = getCharacterData(event, eventReader);
                            break;
                        case LANGUAGE:
                            language = getCharacterData(event, eventReader);
                            break;
                        case AUTHOR:
                            author = getCharacterData(event, eventReader);
                            break;
                        case PUB_DATE:
                            pubdate = getCharacterData(event, eventReader);
                            break;
                        case COPYRIGHT:
                            copyright = getCharacterData(event, eventReader);
                            break;
                        case CONTENT:
                            image = getAttributeData(event, "url");
                            break;
                    }
                } else if (event.isEndElement()) {
                    if (event.asEndElement().getName().getLocalPart() == (ITEM)) {
                        final FeedItem message = new FeedItem();
                        message.setAuthor(author);
                        message.setDescription(description);
                        message.setGuid(guid);
                        message.setLink(link);
                        message.setTitle(title);
                        message.setPubDate(pubdate);
                        message.setImage(image);
                        feed.getItems().add(message);
                        event = eventReader.nextEvent();
                        continue;
                    }
                }
            }

        } catch (final XMLStreamException e) {
            throw new RuntimeException(e);
        }

        return feed;
    }

    private String getCharacterData(XMLEvent event, final XMLEventReader eventReader)
            throws XMLStreamException {
        event = eventReader.nextEvent();
        if (event instanceof Characters) {
            return event.asCharacters().getData();
        }
        return "";
    }

    private String getAttributeData(final XMLEvent event, final String attributeName){
        final Iterator<Attribute> attribute = event.asStartElement().getAttributes();
        while(attribute.hasNext()){
            final Attribute myAttribute = attribute.next();
            if(myAttribute.getName().toString().equals(attributeName)){
                return myAttribute.getValue();
            }
        }
        return "";
    }

    private InputStream read() {
        try {
            return url.openStream();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

}
