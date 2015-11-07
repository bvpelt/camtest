package nl.bsoft.integration;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.ConnectionFactory;

/**
 * Hello world!
 */
public class App {
    private static Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        App app = new App();

        // app.doFileTest();
        app.doCouchDbTest();
    }


    public void doCouchDbTest() {
        try {
            // START SNIPPET: e1
            CamelContext context = new DefaultCamelContext();
            // END SNIPPET: e1
            // Set up the ActiveMQ JMS Components
            // START SNIPPET: e2
            logger.debug("Create permanent broker");
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616?trace=true&soTimeout=60000");
            // Note we can explicit name the component
            context.addComponent("test-jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
            // END SNIPPET: e2
            // Add some configuration by hand ...
            // START SNIPPET: e3


            logger.info("Create route from test-jms queue test.queue to file //test");
            context.addRoutes(new RouteBuilder() {
                                  public void configure() {
                                      from("test-jms:queue:test.queue").to("couchdb:http://localhost:5984/archive");
                                  }
                              }

            );

            // END SNIPPET: e3
            // Camel template - a handy class for kicking off exchanges
            // START SNIPPET: e4
            ProducerTemplate template = context.createProducerTemplate();
            // END SNIPPET: e4
            // Now everything is set up - lets start the context
            context.start();
            // Now send some test text to a component - for this case a JMS Queue
            // The text get converted to JMS messages - and sent to the Queue
            // test.queue
            // The file component is listening for messages from the Queue
            // test.queue, consumes
            // them and stores them to disk. The content of each file will be the
            // test we sent here.
            // The listener on the file component gets notified when new files are
            // found ... that's it!
            // START SNIPPET: e5
            /*
            for (
                    int i = 0;
                    i < 10; i++)

            {
                logger.info("Sending message {} to test-jms test.queue", i);
                template.sendBody("test-jms:queue:test.queue", "Test Message: " + i);
            }
            */
            // END SNIPPET: e5

            // wait a bit and then stop
            Thread.sleep(1000);
            context.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doFileTest() {
        try {
            // START SNIPPET: e1
            CamelContext context = new DefaultCamelContext();
            // END SNIPPET: e1
            // Set up the ActiveMQ JMS Components
            // START SNIPPET: e2
            logger.debug("Create permanent broker");
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616?trace=true&soTimeout=60000");
            // Note we can explicit name the component
            context.addComponent("test-jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
            // END SNIPPET: e2
            // Add some configuration by hand ...
            // START SNIPPET: e3


            logger.info("Create route from test-jms queue test.queue to file //test");
            context.addRoutes(new RouteBuilder() {
                                  public void configure() {
                                      from("test-jms:queue:test.queue").to("file://test");
                                  }
                              }

            );

            // END SNIPPET: e3
            // Camel template - a handy class for kicking off exchanges
            // START SNIPPET: e4
            ProducerTemplate template = context.createProducerTemplate();
            // END SNIPPET: e4
            // Now everything is set up - lets start the context
            context.start();
            // Now send some test text to a component - for this case a JMS Queue
            // The text get converted to JMS messages - and sent to the Queue
            // test.queue
            // The file component is listening for messages from the Queue
            // test.queue, consumes
            // them and stores them to disk. The content of each file will be the
            // test we sent here.
            // The listener on the file component gets notified when new files are
            // found ... that's it!
            // START SNIPPET: e5
            for (
                    int i = 0;
                    i < 10; i++)

            {
                logger.info("Sending message {} to test-jms test.queue", i);
                template.sendBody("test-jms:queue:test.queue", "Test Message: " + i);
            }
            // END SNIPPET: e5

            // wait a bit and then stop
            Thread.sleep(1000);
            context.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
