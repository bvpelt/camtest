package nl.bsoft.couchdb;

import org.junit.Test;

/**
 * Created by bvpelt on 11/21/15.
 */
public class TestCouchDB {

    @Test
    public void testCreateCouchDb() {
        CouchDB cb = new CouchDB();

        cb.setName("mytest");

        boolean result = cb.existsDB();

    }
}
