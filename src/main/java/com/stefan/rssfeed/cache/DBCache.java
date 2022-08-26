package com.stefan.rssfeed.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * A simple cache class to act as a proxy to a rss feed for
 * high availability.
 *
 * @param <T> Datatype.
 */
@Slf4j
@RequiredArgsConstructor
public abstract class DBCache<T> {

    T data;

    /**
     * Returns data from cache.
     * @return cached data.
     */
    public T getData() {
        return data;
    }
}
