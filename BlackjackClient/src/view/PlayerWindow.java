package view;

import java.io.IOException;

import control.PlayerController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class PlayerWindow extends Stage {

	// UI Elements
	private Scene scene;
	private PlayerController control;
	private Button takeCard;
	private Button stand;
	private Label card1;
	private Label card2;
	private Label card3;
	private Label card4;
	private Label card5;
	private Label status;
	private Label score;
	private int count;
	private HBox hBox;
	private Button restart;

	public PlayerWindow() {
		int count = 0;

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("PlayerWindow.fxml"));
			Parent parent = loader.load();

			scene = new Scene(parent, 477, 422);
			this.setScene(scene);

			takeCard = (Button) loader.getNamespace().get("takeCard");
			stand = (Button) loader.getNamespace().get("stand");
			card1 = (Label) loader.getNamespace().get("card1");
			card2 = (Label) loader.getNamespace().get("card2");
			card3 = (Label) loader.getNamespace().get("card3");
			card4 = (Label) loader.getNamespace().get("card4");
			card5 = (Label) loader.getNamespace().get("card5");
			status = (Label) loader.getNamespace().get("status");
			score = (Label) loader.getNamespace().get("score");
			hBox = (HBox) loader.getNamespace().get("hBox");
			restart = (Button) loader.getNamespace().get("restart");

			control = new PlayerController(this);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void showBaseCards(String c1, String c2){
		//C T D P

		String pica = "♠";
		String diamante = "♦";
		String corazon = "♥";
		String trebol = "♣";

		String type = c1.charAt(1) + "";
		if(type.equals("0")){
			type = c1.charAt(2) + "";
		}
		if(type.equals("C")){
			c1 = c1.replace("C", "");
			if(c1.equals("J") || c1.equals("Q") || c1.equals("K") || c1.equals("A")) {
				count += 11;
				score.setText(count + "");
			}else {
				count += Integer.parseInt(c1);
				score.setText(count + "");
			}
			c1 = c1 + corazon;
		} else if(type.equals("T")){
			c1 = c1.replace("T", "");
			if(c1.equals("J") || c1.equals("Q") || c1.equals("K")|| c1.equals("A")) {
				count += 11;
				score.setText(count + "");
			}else {
				count += Integer.parseInt(c1);
				score.setText(count + "");
			}
			c1 = c1 + trebol;
		} else if(type.equals("D")){
			c1 = c1.replace("D", "");
			if(c1.equals("J") || c1.equals("Q") || c1.equals("K")|| c1.equals("A")) {
				count += 11;
				score.setText(count + "");
			}else {
				count += Integer.parseInt(c1);
				score.setText(count + "");
			}
			c1 = c1 + diamante;
		} else if(type.equals("P")){
			c1 = c1.replace("P", "");
			if(c1.equals("J") || c1.equals("Q") || c1.equals("K")|| c1.equals("A")) {
				count += 11;
				score.setText(count + "");
			}else {
				count += Integer.parseInt(c1);
				score.setText(count + "");
			}
			c1 = c1 + pica;
		}

		type = c2.charAt(1) + "";
		if(type.equals("0")){
			type = c2.charAt(2) + "";
		}
		if(type.equals("C")){
			c2 = c2.replace("C", "");
			if(c2.equals("J") || c2.equals("Q") || c2.equals("K") || c2.equals("A")) {
				count += 11;
				score.setText(count + "");
			}else {
				count += Integer.parseInt(c2);
				score.setText(count + "");
			}
			c2 = c2 + corazon;
		} else if(type.equals("T")){
			c2 = c2.replace("T", "");
			if(c2.equals("J") || c2.equals("Q") || c2.equals("K")|| c2.equals("A")) {
				count += 11;
				score.setText(count + "");
			}else {
				count += Integer.parseInt(c2);
				score.setText(count + "");
			}
			c2 = c2 + trebol;
		} else if(type.equals("D")){
			c2 = c2.replace("D", "");
			if(c2.equals("J") || c2.equals("Q") || c2.equals("K") || c2.equals("A")) {
				count += 11;
				score.setText(count + "");
			}else {
				count += Integer.parseInt(c2);
				score.setText(count + "");
			}
			c2 = c2 + diamante;
		} else if(type.equals("P")){
			c2 = c2.replace("P", "");
			if(c2.equals("J") || c2.equals("Q") || c2.equals("K")|| c2.equals("A")) {
				count += 11;
				score.setText(count + "");
			}else {
				count += Integer.parseInt(c2);
				score.setText(count + "");
			}
			c2 = c2 + pica;
		}
		card1.setText(c1);
		card2.setText(c2);
	}

	public void activeRestartBtn(){
		restart.setDisable(false);
	}

	public void reboot(){
		score.setText("");
		stand.setDisable(true);
		takeCard.setDisable(true);
		card1.setText("");
		card2.setText("");
		card3.setText("");
		card4.setText("");
		card5.setText("");
		status.setText("Jugando");
		restart.setDisable(true);
		count = 0;
	}

	public void showAdditionalCard(String c1){
		//C T D P

		String pica = "♠";
		String diamante = "♦";
		String corazon = "♥";
		String trebol = "♣";

		String type = c1.charAt(1) + "";
		if(type.equals("0")){
			type = c1.charAt(2) + "";
		}
		if(type.equals("C")){
			c1 = c1.replace("C", "");
			if(c1.equals("J") || c1.equals("Q") || c1.equals("K") || c1.equals("A")) {
				count += 11;
				score.setText(count + "");
			}else {
				count += Integer.parseInt(c1);
				score.setText(count + "");
			}
			c1 = c1 + corazon;
		} else if(type.equals("T")){
			c1 = c1.replace("T", "");
			if(c1.equals("J") || c1.equals("Q") || c1.equals("K")|| c1.equals("A")) {
				count += 11;
				score.setText(count + "");
			}else {
				count += Integer.parseInt(c1);
				score.setText(count + "");
			}
			c1 = c1 + trebol;
		} else if(type.equals("D")){
			c1 = c1.replace("D", "");
			if(c1.equals("J") || c1.equals("Q") || c1.equals("K")|| c1.equals("A")) {
				count += 11;
				score.setText(count + "");
			}else {
				count += Integer.parseInt(c1);
				score.setText(count + "");
			}
			c1 = c1 + diamante;
		} else if(type.equals("P")){
			c1 = c1.replace("P", "");
			if(c1.equals("J") || c1.equals("Q") || c1.equals("K")|| c1.equals("A")) {
				count += 11;
				score.setText(count + "");
			}else {
				count += Integer.parseInt(c1);
				score.setText(count + "");
			}
			c1 = c1 + pica;
		}


		if(card3.getText().equals("")) {
			card3.setText(c1);
		} else if(card4.getText().equals("")){
			card4.setText(c1);
		} else if(card5.getText().equals("")){
			card5.setText(c1);
		}


	}

	public void startGame(){
		takeCard.setVisible(true);
		status.setVisible(true);
		stand.setVisible(true);
		hBox.setVisible(true);
		score.setVisible(true);
		status.setText("Jugando");

	}

	public void myTurn(){
		takeCard.setDisable(false);
		stand.setDisable(false);
	}

	public void notMyTurn(){
		takeCard.setDisable(true);
		stand.setDisable(true);
	}

	public void stand(){
		takeCard.setDisable(true);
		stand.setDisable(true);
	}

	public void updateStatus(String msg){
		status.setText(msg);
	}

	public Button getTakeCard() {
		return takeCard;
	}

	public Button getStand() {
		return stand;
	}

	public Label getCard1() {
		return card1;
	}

	public Label getCard2() {
		return card2;
	}

	public Label getCard3() {
		return card3;
	}

	public Label getCard4() {
		return card4;
	}

	public Label getCard5() {
		return card5;
	}

	public Label getStatus() {
		return status;
	}

	public Label getScore() {
		return score;
	}

	public Button getRestart() {
		return restart;
	}
}
