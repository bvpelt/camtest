package nl.bsoft.couchdb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by bvpelt on 11/21/15.
 *
 * Example of array of db names
 *
 * ["_replicator","_users","albums","albums-replica","archive","bulktest-database","hello-world","mydatabase","somedatabase","test_suite_db","test_suite_db2"]
 */

@JsonIgnoreProperties({ "logger"})
public class AllDbs {
    // Logger initialization
    private static Logger logger = LoggerFactory.getLogger(AllDbs.class);

    private String [] names;

    public String[] getNames() {
        return names;
    }

    public void setNames(final String[] names) {
        this.names = names;
    }

    public void construct(final String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            AllDbs rootNode = mapper.readValue(jsonString, AllDbs.class);
            setNames(rootNode.getNames());
        } catch (IOException e) {
            logger.error("Error during reading jsonString {} {}", jsonString, e);
        }
    }


}
