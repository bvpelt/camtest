package nl.bsoft.couchdb;


import nl.bsoft.integration.JsonConverter;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by bvpelt on 11/21/15.
 */
public class TestAllDbs {
    // Logger initialization
    private static Logger logger = LoggerFactory.getLogger(TestAllDbs.class);

    private String n1 = "_replicator";
    private String n2 = "_users";
    private String n3 = "albums";

    private String [] dbsarray = {n1, n2, n3};

    private String dbs = "[\"" + n1 + "\",\"" + n2 + "\",\"" + n3 + "\"]";

    /*
    @Test
    public void testAllDbs() {
       AllDbs ab = new AllDbs();

        String json = "[\"_replicator\",\"_users\",\"albums\",\"albums-replica\",\"archive\",\"bulktest-database\",\"hello-world\",\"mydatabase\",\"somedatabase\",\"test_suite_db\",\"test_suite_db2\"]";

        ab.construct(json);

        String [] names = ab.getNames();

        Assert.assertEquals(11, names.length);

    }
    */

    @Test
    public void createAllDbs() {
        AllDbs ab = new AllDbs();

        ab.setNames(dbsarray);

        String a = ab.getNames().toString();
        logger.info("Arrays: {}", a);

        String result = null;
        try {
            String [] names = ab.getNames();
            StringBuffer sb = new StringBuffer();
            sb.append("[");
            int len = names.length;
            int i = 0;
            for (i = 0; i < len -1; i++) {
                sb.append("\"");
                sb.append(names[i]);
                sb.append("\",");
            }
            sb.append("\"");
            sb.append(names[i]);
            sb.append("\"");
            sb.append("]");
            result = sb.toString();
            Assert.assertEquals(dbs, result);
        } catch (Exception e) {
            logger.error("Cannot convert to json");
        }
    }

    public String getJsonString(final Object object) throws Exception {
        JsonConverter jc = new JsonConverter();

        String result= jc.getObjectJson(object);

        return result;
    }
}
