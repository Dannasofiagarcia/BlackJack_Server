package control;

import com.google.gson.*;
import comm.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import model.*;
import view.PlayerWindow;
import comm.Receptor.OnMessageListener;

public class PlayerController implements OnMessageListener {

	private PlayerWindow view;
	private TCPConnection connection;
	private String id;


	public PlayerController(PlayerWindow view) {
		this.view = view;
		init();
	}

	public void init() {
		connection = TCPConnection.getInstance();
		connection.setListenerOfMessages(this);

		view.getTakeCard().setOnAction(event -> {
			Gson gson = new Gson();
			String json = gson.toJson(new AdditionalCards(id, ""));
			TCPConnection.getInstance().getEmisor().sendMessage(json);
			view.notMyTurn();

		});
		view.getStand().setOnAction(event -> {
			Gson gson = new Gson();
			String json = gson.toJson(new Stand(id, view.getScore().getText()));
			TCPConnection.getInstance().getEmisor().sendMessage(json);
			view.stand();
		});
		view.getRestart().setOnAction(event -> {
			Gson gson = new Gson();
			String json = gson.toJson(new Restart());
			TCPConnection.getInstance().getEmisor().sendMessage(json);
		});
	}

	public void winner(){
		view.updateStatus("¡Ganaste!");

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("¡Ganaste!");
		alert.setHeaderText(null);
		alert.setContentText("Ganaste la partida, si desea jugar de nuevo reinice");
		alert.show();
		view.stand();
	}

	public void lost(){
		view.updateStatus("¡Perdiste!");

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("¡Perdiste!");
		alert.setHeaderText(null);
		alert.setContentText("El adversario ganó la partida, si desea jugar de nuevo reinice");
		alert.show();
		view.stand();
	}

	public void countScore(){
		Gson gson = new Gson();
		if(Integer.parseInt(view.getScore().getText()) == 21){
			Win uWin = new Win(id, "Winner");
			String json = gson.toJson(uWin);
			TCPConnection.getInstance().getEmisor().sendMessage(json);
			view.stand();
		} else if (Integer.parseInt(view.getScore().getText()) > 21){
			MoreTO moreTO = new MoreTO(id, view.getScore().getText());
			String json = gson.toJson(moreTO);
			TCPConnection.getInstance().getEmisor().sendMessage(json);
			view.stand();

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("¡Puntaje mayor a 21!");
			alert.setHeaderText(null);
			alert.setContentText("Perdiste. A la espera de que el adversario termine de jugar");
			alert.show();
			view.stand();
		}
	}

	public void notifyImReady(){
		Gson gson = new Gson();
		StartGame startGame = new StartGame(id);
		String json = gson.toJson(startGame);
		TCPConnection.getInstance().getEmisor().sendMessage(json);
	}

	public void deadHeat(){
		view.updateStatus("¡Empate!");

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("¡Empate!");
		alert.setHeaderText(null);
		alert.setContentText("Usted y el adversario empataron la partida");
		alert.show();
		view.stand();
	}


	@Override
	public void OnMessage(String msg) {
		Platform.runLater(() -> {
					Gson gson = new Gson();
					Generic msjObj = gson.fromJson(msg, Generic.class);
					System.out.println(msjObj.getType());
					switch (msjObj.getType()){
						case "BaseCards":
							BaseCards bc = gson.fromJson(msg, BaseCards.class);
							this.id = bc.getId();
							String[] tempBC = bc.getBody().split(",");
							view.showBaseCards(tempBC[0], tempBC[1]);
							if(bc.isFirstPlayer() == true){
								view.myTurn();
							}
							notifyImReady();
							countScore();
							break;
						case "AdditionalCards":
							AdditionalCards ac = gson.fromJson(msg, AdditionalCards.class);
							view.showAdditionalCard(ac.getBody());

							countScore();
							break;

						case "Turn":
							view.myTurn();
							break;

						case "Win":
							winner();
							view.activeRestartBtn();
							break;

						case "DeadHeat":
							deadHeat();
							view.activeRestartBtn();
							break;

						case "Restart":
							view.reboot();
							break;

						case "Lost":
							lost();
							view.activeRestartBtn();
							break;

						case "StartGame":
							view.startGame();
							break;


					}
				}
		);
	}
}
