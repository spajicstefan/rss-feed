package com.stefan.rssfeed.web.rest;

import com.stefan.rssfeed.model.Feed;
import com.stefan.rssfeed.service.RSSFeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/rssfeed", produces = MediaType.APPLICATION_JSON_VALUE)
public class RSSFeedController {

    private final RSSFeedService rssFeedService;

    @GetMapping()
    public ResponseEntity<Feed> getRSSFeed() {
        log.debug("GET /rssfeed ");

        return ResponseEntity.ok().body(rssFeedService.getRSSFeed());
    }
}
