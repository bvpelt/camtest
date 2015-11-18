package nl.bsoft.integration;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by bvpelt on 11/11/15.
 */
public class Acte extends Document {

    private String mimeType;
    private byte[] baseDocument;

    public Acte() {
    }

    public Acte(final String mimeType, final byte[] baseDocument) {
        this.mimeType = mimeType;
        this.baseDocument = baseDocument; // Arrays.copyOf(baseDocument, baseDocument.length);
    }

    public void loadFromJson(final String jsonObject) {
        super.loadFromJson(jsonObject);

        JsonConverter js = new JsonConverter();
        js.loadString(jsonObject);
        int len = 0;
        try {
            this.mimeType = js.getStringNode("mimeType");
            len = js.getNode().get("baseDocument").getBinaryValue().length;
            baseDocument = Arrays.copyOf(js.getNode().get("baseDocument").getBinaryValue(), len);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] getBaseDocument() {
        return baseDocument;
    }

    public void setBaseDocument(byte[] baseDocument) {
        this.baseDocument = baseDocument;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}
