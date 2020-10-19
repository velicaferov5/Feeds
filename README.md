Feeds API allows to crawl news websites and insert, get, update, remove and manage feeds.

Feeds have:
ID
sourceUrl
feedUrl
title
description
publicationDate
imageUrl

Following REST-ful services can be used to get, insert, update, delete feeds:

Get feeds by ID:
url: /api/feed/get/{id}
Call type: GET
input: id (integer)
Output: Optional Feed

Crawl feeds by URL:
url: /api/feed/add
Call type: POST
input: url (String)
Output: Feed list

Delete feeds by URL:
url: /api/feed/delete/{url}
Call type: DELETE
input: url (String)
Output: Boolean (success of deletion)

Requirement (Java version): 12
