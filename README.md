# Camtest
Some test software with camel

Requirements:
+ A working installed activemq instance at localhost listening at port 61616
+ A working installed couchdb instance at localhost (or docker) listening at port 5984 with database archive created

[camel example](https://svn.apache.org/repos/asf/camel/trunk/examples/camel-example-jms-file/src/main/java/org/apache/camel/example/jmstofile/CamelJmsToFileExample.java)

## Connection configuration
See
+ [transport config](http://activemq.apache.org/configuring-transports.html)
    + [tcp config](http://activemq.apache.org/tcp-transport-reference.html)
+ [broker config](http://activemq.apache.org/broker-uri.html)
+ [connection couchdb](http://camel.apache.org/couchdb.html)