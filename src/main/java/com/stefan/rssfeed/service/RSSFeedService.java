package com.stefan.rssfeed.service;

import com.stefan.rssfeed.cache.FeedCache;
import com.stefan.rssfeed.model.Feed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RSSFeedService {

    private final FeedCache feedCache;

    /**
     * Get rss feed from cache.
     *
     * @return feed.
     */
    public Feed getRSSFeed() {
        log.debug("Returning rss feed from cache");

        return feedCache.getData();
    }
}
