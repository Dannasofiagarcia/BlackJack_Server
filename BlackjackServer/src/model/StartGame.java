package model;

public class StartGame {

    public String tpye = "StartGame";
    private String id;

    public StartGame() {
    }

    public StartGame(String id) {
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
