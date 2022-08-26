package com.stefan.rssfeed.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Feed {

    private String title;

    private String link;

    private String description;

    private String language;

    private String copyright;

    private String pubDate;

    private List<FeedItem> items = new ArrayList<>();
}
