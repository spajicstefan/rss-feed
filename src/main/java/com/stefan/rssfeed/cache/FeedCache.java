package com.stefan.rssfeed.cache;

import javax.annotation.PostConstruct;

import com.stefan.rssfeed.model.Feed;
import com.stefan.rssfeed.parser.RSSFeedParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FeedCache extends DBCache<Feed> {

    private final RSSFeedParser rssFeedParser = new RSSFeedParser("https://rss.nytimes.com/services/xml/rss/nyt/Technology.xml");

    @PostConstruct
    @Scheduled(fixedDelay = 15 * 60 * 1000)
    public void refresh() {
        log.debug("Refreshing the {} cache.", this.getClass().getSimpleName());
        data = rssFeedParser.readFeed();
    }
}
