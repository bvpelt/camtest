package nl.bsoft.couchdb;

import nl.bsoft.http.TestClient;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by bvpelt on 11/21/15.
 */
public class CouchDBConnector {
    // Logger initialization
    private static Logger logger = LoggerFactory.getLogger(CouchDBConnector.class);

    private String scheme;
    private String hostname;
    private String port;

    // local variables
    private String baseUrl = null;
    private TestClient tc = null;

    public CouchDBConnector() {
        scheme = "http";
        hostname = "127.0.0.1";
        port = "5984";
    }

    public CouchDBConnector(final String scheme, final String hostname, final String port) {
        this.scheme = scheme;
        this.hostname = hostname;
        this.port = port;
    }

    public String send(final String path) {
        String url = null;
        String result = null;

        if (null == tc) {
            createClient();
        }

        setBaseUrl(scheme, hostname, port);
        url = baseUrl + path;

        try {
            CloseableHttpResponse response = null;

            response = tc.sendRequest(url, "GET" );

            int status;
            HttpEntity entity = null;

            status = response.getStatusLine().getStatusCode();
            if ((status >= 200) && (status < 203)) { // success
                entity = response.getEntity();
                if (entity != null) {
                    result = EntityUtils.toString(entity);
                }
            }
        } catch (Exception e) {
            logger.error("Error during sending request {}", e);
        } finally {
            tc.closeSession();
        }

        return result;
    }

    /*
    Create a client with the suitable parameters
     */
    private void createClient() {
        tc = new TestClient();
    }

    private void setBaseUrl(final String scheme, final String hostname, final String port) {
        baseUrl = scheme + "://" + hostname;

        if (port != null) {
            baseUrl = baseUrl + ":" + port;
        }

        baseUrl = baseUrl + "/";
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(final String scheme) {
        this.scheme = scheme;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(final String hostname) {
        this.hostname = hostname;
    }

    public String getPort() {
        return port;
    }

    public void setPort(final String port) {
        this.port = port;
    }
}
