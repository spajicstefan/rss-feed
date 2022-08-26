package com.stefan.rssfeed.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class FeedItem {

    private String title;

    private String link;

    private String description;

    private String author;

    private String guid;

    private String pubDate;

    private String image;

}
