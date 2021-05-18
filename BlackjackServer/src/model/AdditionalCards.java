package model;

public class AdditionalCards {
    public String tpye = "AdditionalCards";
    private String id;
    private String body;

    public AdditionalCards() {
    }

    public AdditionalCards(String id, String body) {
        super();
        this.id = id;
        this.body = body;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
