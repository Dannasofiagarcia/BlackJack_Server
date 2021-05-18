package model;

public class Win {
    public String tpye = "Win";
    private String id;
    private String body;

    public Win() {
    }

    public Win(String id, String body) {
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
