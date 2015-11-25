package nl.bsoft.couchdb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by bvpelt on 11/21/15.
 *
 * See http://docs.couchdb.org/en/1.6.1/api/basics.html
 */
public class CouchDB {
    // Logger initialization
    private static Logger logger = LoggerFactory.getLogger(CouchDB.class);

    private String name = null;

    public CouchDB() {
    }

    public CouchDB(final String name) {
        this.name = name;
    }

    public boolean createDB() {
        boolean result = false;

        return result;
    }

    public boolean deleteDB() {
        boolean result = false;

        return result;
    }

    public boolean existsDB() {
        boolean result = false;

        if (null != name) {
            // get list of db'
            CouchDBConnector cdbc = new CouchDBConnector();

            String response = null;

            response = cdbc.send("_all_dbs");

            // does name occur in list?
            logger.info("Result of existDB: {}", response);
        }

        return existsDB();
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
