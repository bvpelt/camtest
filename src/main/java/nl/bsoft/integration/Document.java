package nl.bsoft.integration;

import java.util.Date;

/**
 * Created by bvpelt on 11/7/15.
 */
public class Document {

    private String documentType;
    private String title;
    private String description;
    private String[] keywords;
    private Date publicationDate;

    public Document() {
        setDocumentType(this.getClass().getName());
    }

    public String toJson() {
        String result = null;

        JsonConverter js = new JsonConverter();
        try {
            result = js.getObjectJson(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public void loadFromJson(final String jsonObject) {
        JsonConverter js = new JsonConverter();
        js.loadString(jsonObject);

        this.documentType = js.getStringNode("documentType");
        this.title = js.getStringNode("title");
        this.description = js.getStringNode("description");
        this.keywords = js.getStringArray("keywords");
        this.publicationDate = js.getDateNode("publicationDate");
    }


    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(final String documentType) {
        this.documentType = documentType;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String[] getKeywords() {
        return keywords;
    }

    public void setKeywords(final String[] keywords) {
        this.keywords = keywords;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(final Date publicationDate) {
        this.publicationDate = publicationDate;
    }


}
