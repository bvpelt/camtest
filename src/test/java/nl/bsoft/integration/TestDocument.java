package nl.bsoft.integration;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;

/**
 * Created by bvpelt on 11/7/15.
 */
public class TestDocument {
    private static Logger logger = LoggerFactory.getLogger(TestDocument.class);

    private static String jsonTest = "{\"title\":\"Dit is een test\",\"description\":\"Omschrijving van dit document\",\"keywords\":[\"test\",\"document\",\"omschrijving\"],\"publicationDate\":1425158114000}";
    private static String title = "Dit is een test";
    private static String description = "Omschrijving van dit document";
    private static String [] keywords = { "test", "document", "omschrijving" };
    private static long timeinMills = 1425158114000L;

    @Test
    public void testCreateDocument() {
        String testName = "testCreateDocument";
        logger.info("Start test {}", testName);

        Document doc = new Document();
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

        Assert.assertEquals(jsonTest, json);
        logger.info("Result json: {}", json);

        logger.info("End   test {}", testName);
    }

    @Test
    public void testLoadDocument() {
        String testName = "testLoadDocument";
        logger.info("Start test {}", testName);

        Document doc = new Document();
        doc.loadFromJson(jsonTest);

        Assert.assertEquals(title, doc.getTitle());
        Assert.assertEquals(description, doc.getDescription());
        Assert.assertEquals(keywords.length, doc.getKeywords().length);
        for (int i = 0; i < keywords.length; i++) {
            Assert.assertEquals(keywords[i], doc.getKeywords()[i]);
        }
        Assert.assertEquals(timeinMills, doc.getPublicationDate().getTime());

        logger.info("End   test {}", testName);
    }
}
