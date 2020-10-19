package com.feeds.api.controller;

import com.feeds.api.model.Feed;
import com.feeds.api.service.FeedService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * REST-ful services to get, add, delete and manage feeds
 */
@RestController
@RequestMapping(value = "/api/feed")
public class FeedController {
    FeedService feedService;

    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    /**
     * Gets feed
     *
     * @param id
     * @return optional feed
     */
    @GetMapping(value = "/get/{id}")
    public Optional<Feed> getFeed (@PathVariable(name="id") Integer id) {
        return feedService.getFeedById(id);
    }

    /**
     * Crawls and adds/updates feeds in DB.
     *
     * @param url
     * @return feeds
     */
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Feed> addFeed (@Valid @RequestBody String url) {
        return feedService.addOrUpdateFeeds(url);
    }

    /**
     * Removes feeds by {@param feedUrl}
     * @param feedUrl
     * @return result of delete
     */
    @DeleteMapping(value = "/delete/{feedUrl}")
    public boolean deleteFeeds (@PathVariable(name="feedUrl") String feedUrl) {
        return feedService.deleteFeedsByUrl(feedUrl);
    }
}
