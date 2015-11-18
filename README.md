# Camtest
Some test software with camel

Requirements:
+ A working installed activemq instance at localhost listening at port 61616 (see [docker activemq image](https://github.com/bvpelt/activemq))
+ A working installed couchdb instance at localhost (or docker) listening at port 5984 with database archive created (see [docker couchdb image](https://github.com/bvpelt/couchdb))
    + After startup of couchdb the archive database should be created by
     ```bash
    curl -X PUT http://127.0.0.1:5984/archive
    ```

[camel example](https://svn.apache.org/repos/asf/camel/trunk/examples/camel-example-jms-file/src/main/java/org/apache/camel/example/jmstofile/CamelJmsToFileExample.java)

# Description test
In order to be able to execute the test the following actions must be done:
+ start activemq
+ start couchdb
+ start test by running
    + TestAMQ in test / nl.bsoft.integration, this places messages in the activemq message queue
    + App in src / nl.bsoft.integration, this reads messages from message queue and transfers messages to couchdb

## Connection configuration
See
+ [transport config](http://activemq.apache.org/configuring-transports.html)
    + [tcp config](http://activemq.apache.org/tcp-transport-reference.html)
+ [broker config](http://activemq.apache.org/broker-uri.html)
+ [connection couchdb](http://camel.apache.org/couchdb.html)

## Mime types
See
+ [iana lijst](http://www.iana.org/assignments/media-types/media-types.xhtml)

## Tools
See
+ [takipi](https://www.takipi.com/)
+ [design patterns](https://dzone.com/articles/memento-pattern-1)