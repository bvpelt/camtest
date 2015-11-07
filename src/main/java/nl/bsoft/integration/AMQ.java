package nl.bsoft.integration;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

/**
 * Created by bvpelt on 11/7/15.
 */
public class AMQ {
    public static final int ERROR = -1;
    public static final int SUCCESS = 0;

    private static Logger logger = LoggerFactory.getLogger(AMQ.class);

    private ActiveMQConnectionFactory connectionFactory = null;
    private Connection connection = null;
    private Session session = null;

    public AMQ() {

    }

    public int startAMQ() {
        int status = ERROR; // possible values 0 == ok, -1 == error

        // Create a ConnectionFactory
        connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616?trace=true&soTimeout=60000");

        // Create a Connection
        try {
            connection = connectionFactory.createConnection();

            connection.start();

            status = SUCCESS;
        } catch (JMSException e) {
            logger.error("Problem creating AMQ connection: ", e);
            if (null != connection) {
                closeAMQ();
            }
        }

        return status;
    }

    public int closeAMQ() {
        int status = ERROR; // possible values 0 == ok, -1 == error

        if (connection != null) {
            try {
                connection.stop();
                connection.close();
                status = SUCCESS;
            } catch (JMSException e1) {
                logger.error("Problem closing AMQ connection: ", e1);
            } finally {
                connection = null;
                connectionFactory = null;
            }
        }

        return status;
    }

    public int startSession() {
        int status = ERROR; // possible values 0 == ok, -1 == error

        if (null == connection) {
            logger.error("No valid connection setup, can't start session");
        } else {
            try {
                // Create a Session
                session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                status = SUCCESS;
            } catch (JMSException e) {
                logger.error("Problem creating AMQ session: ", e);
                if (null != session) {
                    closeSession();
                }
            }
        }
        return status;
    }


    public int closeSession() {
        int status = ERROR; // possible values 0 == ok, -1 == error

        try {
            session.close();
            status = SUCCESS;
        } catch (JMSException e) {
            logger.error("Problem closing AMQ session: ", e);
        } finally {
            session = null;
        }

        return status;
    }

    public TextMessage createTextMessage(final String message) {
        TextMessage msg = null;

        try {
            msg = session.createTextMessage(message);
        } catch (JMSException e) {
            logger.error("Problem creating text message: ", e);
            msg = null;
        }
        return msg;
    }

    public int sendQueueMessage(final String queueName, final Message msg) {
        int status = ERROR;

        try {
            // Create the destination (Topic or Queue)
            Destination destination = session.createQueue(queueName);

            // Create a MessageProducer from the Session to the Topic or Queue
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);

            producer.send(msg);

            status = SUCCESS;
        } catch (JMSException e) {
            logger.error("Problem creating text message: ", e);
        }

        return status;
    }

    public void doTest() {
        try {
            // Create a ConnectionFactory
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616?trace=true&soTimeout=60000");

            // Create a Connection
            Connection connection = connectionFactory.createConnection();
            connection.start();

            // Create a Session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            Destination destination = session.createQueue("TEST.FOO");

            // Create a MessageProducer from the Session to the Topic or Queue
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            // Create a messages
            String text = "Hello world! From: " + Thread.currentThread().getName() + " : " + this.hashCode();
            TextMessage message = session.createTextMessage(text);

            // Tell the producer to send the message
            System.out.println("Sent message: " + message.hashCode() + " : " + Thread.currentThread().getName());
            producer.send(message);

            // Clean up
            session.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }
}
