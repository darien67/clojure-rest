# clojure-rest

A document looks like this (JSON encoded):

{
      "id" : "some id"
      , "title" : "some title"
      , "text" : "some text"
    }

A GET call to /documents should return a list of these documents.
http://localhost:3000/documents

A POST call to /documents with a documents as body shall create a new document,
 assigning a new id (ignoring the posted one).

curl  -H "Content-Type: application/json" -X POST -d '{ "id" : "some id" , "title" : "title 1" , "text" : "some text 1" }' http://localhost:3000/documents/

A GET to /documents/[ID] should return the document with the given id,
 or 404 if the document does not exist.

http://localhost:3000/documents/7b24c90d-a2a5-4a84-abd9-009f9fde5e08

A PUT to /documents/[ID] should update the document with the given id 
and replace title and text with those from the document in the uploaded body.

 curl  -H "Content-Type: application/json" -X PUT -d '{  "title" : "some title2" , "text" : "some updated  text2" }' http://localhost:3000/documents/7b24c90d-a2a5-4a84-abd9-009f9fde5e08

A DELETE to /documents/[ID] should delete the document with the given id and
 return 204 (NO CONTENT) in any case.
curl  -H "Content-Type: application/json" -X DELETE -d  http://localhost:3000/documents/7b24c90d-a2a5-4a84-abd9-009f9fde5e08

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein ring server

## License

Copyright © 2017 FIXME
