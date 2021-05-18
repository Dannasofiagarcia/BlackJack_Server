package model;

public class Stand {
    public String tpye = "Stand";
    private String id;
    private String body;

    public Stand() {
    }

    public Stand(String id, String body) {
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
}
