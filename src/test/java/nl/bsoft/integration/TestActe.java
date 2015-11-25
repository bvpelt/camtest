package nl.bsoft.integration;

import org.apache.commons.codec.binary.Base64;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

/**
 * Created by bvpelt on 11/11/15.
 */
public class TestActe {
    private static Logger logger = LoggerFactory.getLogger(TestActe.class);

    private static String docType = "nl.bsoft.integration.Acte";
    private static String title = "Dit is een test";
    private static String description = "Omschrijving van dit document";
    private static String kw1 = "test";
    private static String kw2 = "document";
    private static String kw3 = "omschrijving";
    private static String[] keywords = {kw1, kw2, kw3};
    private static long timeinMills = 1425158114000L;
    private static String docData = "# Camtest\n" +
            "Some test software with camel\n" +
            "\n" +
            "Requirements:\n" +
            "+ A working installed activemq instance at localhost listening at port 61616\n" +
            "+ A working installed couchdb instance at localhost (or docker) listening at port 5984 with database archive created\n" +
            "\n" +
            "[camel example](https://svn.apache.org/repos/asf/camel/trunk/examples/camel-example-jms-file/src/main/java/org/apache/camel/example/jmstofile/CamelJmsToFileExample.java)\n" +
            "\n" +
            "## Connection configuration\n" +
            "See\n" +
            "+ [transport config](http://activemq.apache.org/configuring-transports.html)\n" +
            "    + [tcp config](http://activemq.apache.org/tcp-transport-reference.html)\n" +
            "+ [broker config](http://activemq.apache.org/broker-uri.html)\n" +
            "+ [connection couchdb](http://camel.apache.org/couchdb.html)";


    private static String jsonTestStart = "{\"documentType\":\"" + docType + "\",\"title\":\"" + title +
            "\",\"description\":\"" + description +
            "\",\"keywords\":[\"" + kw1 + "\",\"" + kw2 + "\",\"" + kw3 + "\"],\"publicationDate\":" +
            (new Long(timeinMills)).toString() +
            ",\"mimeType\":\"text/plain\"" +
            ",\"baseDocument\":\"";

    private static String jsonTestEnd = "\"}";
    private static String mimeType = "text/plain";


    @Test
    public void testCreateActe() {
        String testName = "testCreateActe";
        try {
            logger.info("Start test {}", testName);

            String base64DocData = new Base64().encodeAsString(docData.getBytes("UTF-8"));
            String jsonTest = jsonTestStart + base64DocData + jsonTestEnd;

            Acte doc = new Acte();
            doc.setTitle(title);
            doc.setDescription(description);
            doc.setKeywords(keywords);
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

            doc.setBaseDocument(docData.getBytes("UTF-8"));
            doc.setMimeType(mimeType);

            String json = doc.toJson();
            logger.info("json string: {}", json);

            Assert.assertEquals(jsonTest, json);
            logger.info("Result json: {}", json);

            logger.info("End   test {}", testName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testCreateActe01() {
        String testName = "testCreateActe01";
        try {
            logger.info("Start test {}", testName);

            String base64DocData = new Base64().encodeAsString(docData.getBytes("UTF-8"));
            String jsonTest = jsonTestStart + base64DocData + jsonTestEnd;

            Acte doc = new Acte(mimeType, docData.getBytes());
            doc.setTitle(title);
            doc.setDescription(description);
            doc.setKeywords(keywords);
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

            String json = doc.toJson();
            logger.info("json string: {}", json);

            Assert.assertEquals(jsonTest, json);
            logger.info("Result json: {}", json);

            logger.info("End   test {}", testName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testLoadDocument() {
        String testName = "testLoadDocument";
        try {
            logger.info("Start test {}", testName);


            String base64DocData = new Base64().encodeAsString(docData.getBytes("UTF-8"));
            String jsonTest = jsonTestStart + base64DocData + jsonTestEnd;

            Acte doc = new Acte();
            doc.loadFromJson(jsonTest);

            Assert.assertEquals(title, doc.getTitle());
            Assert.assertEquals(description, doc.getDescription());
            Assert.assertEquals(keywords.length, doc.getKeywords().length);
            for (int i = 0; i < keywords.length; i++) {
                Assert.assertEquals(keywords[i], doc.getKeywords()[i]);
            }
            Assert.assertEquals(timeinMills, doc.getPublicationDate().getTime());

            Assert.assertEquals(mimeType, doc.getMimeType());
            Assert.assertArrayEquals(docData.getBytes(), doc.getBaseDocument());

            logger.info("End   test {}", testName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
