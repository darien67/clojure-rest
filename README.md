# clojure-rest

A document looks like this (JSON encoded):

{
      "id" : "some id"
      , "title" : "some title"
      , "text" : "some text"
    }

A GET call to /documents should return a list of these documents.
A POST call to /documents with a documents as body shall create a new document,
 assigning a new id (ignoreing the posted one).
A GET to /documents/[ID] should return the document with the given id,
 or 404 if the document does not exist.
A PUT to /documents/[ID] should update the document with the given id 
and replace title and text with those from the document in the uploaded body.
A DELETE to /documents/[ID] should delete the document with the given id and
 return 204 (NO CONTENT) in any case.


## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein ring server

## License

Copyright © 2017 FIXME
