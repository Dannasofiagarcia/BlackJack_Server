package model;

public class BaseCards {
    public String tpye = "BaseCards";
    private String id;
    private String body;
    private boolean firstPlayer;

    public BaseCards() {
    }

    public BaseCards(String id, String body, boolean firstPlayer) {
        super();
        this.id = id;
        this.body = body;
        this.firstPlayer = firstPlayer;
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

    public boolean isFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(boolean firstPlayer) {
        this.firstPlayer = firstPlayer;
    }
}
