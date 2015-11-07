package nl.bsoft.integration;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.TextMessage;
import java.util.Calendar;

/**
 * Created by bvpelt on 11/7/15.
 */
public class TestAMQ {
    private static Logger logger = LoggerFactory.getLogger(TestAMQ.class);

    @Test
    public void testCreateAMQ() {
        String testName = "testCreateAMQ";
        logger.info("Start test {}", testName);

        AMQ amq = new AMQ();
        int status;

        status = amq.startAMQ();
        Assert.assertEquals(status, AMQ.SUCCESS);
        logger.info("Created AMQ with status {}", status);

        status = amq.closeAMQ();
        Assert.assertEquals(status, AMQ.SUCCESS);
        logger.info("Closed AMQ with status {}", status);

        logger.info("Created and closed AMQ");
        logger.info("End   test {}", testName);
    }

    @Test
    public void testCreateSession() {
        String testName = "testCreateSession";
        logger.info("Start test {}", testName);

        AMQ amq = new AMQ();
        int status;

        status = amq.startAMQ();
        Assert.assertEquals(status, AMQ.SUCCESS);
        logger.info("Created AMQ with status {}", status);

        status = amq.startSession();
        Assert.assertEquals(status, AMQ.SUCCESS);
        logger.info("Created Session with status {}", status);

        status = amq.closeSession();
        Assert.assertEquals(status, AMQ.SUCCESS);
        logger.info("Closed Session with status {}", status);

        status = amq.closeAMQ();
        Assert.assertEquals(status, AMQ.SUCCESS);
        logger.info("Closed AMQ with status {}", status);

        logger.info("Created and closed AMQ and session");
        logger.info("End   test {}", testName);
    }

    /*
    @Test
    public void testSendMessage() {
        String testName = "testSendMessage";
        logger.info("Start test {}", testName);

        String queueName = "test.queue";
        AMQ amq = new AMQ();
        int status;

        status = amq.startAMQ();
        Assert.assertEquals(status, AMQ.SUCCESS);
        logger.info("Created AMQ with status {}", status);

        status = amq.startSession();
        Assert.assertEquals(status, AMQ.SUCCESS);
        logger.info("Created Session with status {}", status);

        TextMessage tm = amq.createTextMessage("Mijn test message");
        Assert.assertNotNull(tm);
        logger.info("Created message");

        status = amq.sendQueueMessage(queueName, tm);
        Assert.assertEquals(status, AMQ.SUCCESS);
        logger.info("Sended message with status {}", status);

        status = amq.closeSession();
        Assert.assertEquals(status, AMQ.SUCCESS);
        logger.info("Closed Session with status {}", status);

        status = amq.closeAMQ();
        Assert.assertEquals(status, AMQ.SUCCESS);
        logger.info("Closed AMQ with status {}", status);
        logger.info("End   test {}", testName);
    }
    */

    @Test
    public void testSendMessage01() {
        String testName = "testSendMessage01";
        logger.info("Start test {}", testName);

        String queueName = "test.queue";
        AMQ amq = new AMQ();
        int status;

        amq.doTest();

        status = amq.closeAMQ();
        logger.info("Closed AMQ with status {}", status);
        logger.info("End   test {}", testName);
    }

    @Test
    public void testSendJsonMessage() {
        String testName = "testSendJsonMessage";
        logger.info("Start test {}", testName);

        Document doc = new Document();
        doc.setTitle("Mijn test document");
        doc.setKeywords(new String[] {"test", "json", "document"});
        doc.setDescription("Leeg document, gebruikt voor testen");
        int year = 2015;
        int month = Calendar.FEBRUARY;
        int day = 28;
        int hour = 22;
        int minute = 15;
        int second = 14;
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(year, month, day, hour, minute, second);

        doc.setPublicationDate(cal.getTime());

        String queueName = "test.queue";
        AMQ amq = new AMQ();
        int status;

        status = amq.startAMQ();
        Assert.assertEquals(status, AMQ.SUCCESS);
        logger.info("Created AMQ with status {}", status);

        status = amq.startSession();
        Assert.assertEquals(status, AMQ.SUCCESS);
        logger.info("Created Session with status {}", status);

        TextMessage tm = amq.createTextMessage(doc.toJson());
        Assert.assertNotNull(tm);
        logger.info("Created json message");

        status = amq.sendQueueMessage(queueName, tm);
        Assert.assertEquals(status, AMQ.SUCCESS);
        logger.info("Sended json message with status {}", status);

        status = amq.closeSession();
        Assert.assertEquals(status, AMQ.SUCCESS);
        logger.info("Closed Session with status {}", status);

        status = amq.closeAMQ();
        Assert.assertEquals(status, AMQ.SUCCESS);
        logger.info("Closed AMQ with status {}", status);
        logger.info("End   test {}", testName);
    }


    @Test
    public void testSendJsonMessages() {
        String testName = "testSendJsonMessages";
        logger.info("Start test {}", testName);
        for (int i = 0; i < 100; i++) {
            testSendJsonMessage();
        }
        logger.info("End   test {}", testName);
    }

}
