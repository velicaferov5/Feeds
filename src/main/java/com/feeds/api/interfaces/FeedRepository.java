package com.feeds.api.interfaces;

import com.feeds.api.model.Feed;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository to make CRUD operations on {@link Feed}
 */
@Repository
public interface FeedRepository extends CrudRepository<Feed, Integer> {

    @Query("from Feed feed where feed.sourceUrl=:sourceUrl")
    List<Feed> findFeedByUrl(@Param("sourceUrl") String sourceUrl);

    @Query("delete from Feed feed where feed.sourceUrl=:sourceUrl")
    void deleteFeedsByUrl(@Param("sourceUrl") String sourceUrl);
}
