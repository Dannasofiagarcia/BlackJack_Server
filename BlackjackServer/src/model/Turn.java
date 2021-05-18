package model;

public class Turn {
    public String tpye = "Turn";
    private String id;
    private String body;

    public Turn() {
    }

    public Turn(String id) {
        super();
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
