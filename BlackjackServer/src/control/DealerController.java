package control;

import comm.Receptor.OnMessageListener;

import java.util.*;

import com.google.gson.Gson;

import comm.TCPConnection;
import comm.TCPConnection.OnConnectionListener;
import javafx.application.Platform;
import model.*;
import view.DealerWindow;

public class DealerController implements OnMessageListener, OnConnectionListener{

	private DealerWindow view;
	private TCPConnection connection;
	Stack<String> cartas;
	private int additionalCards;
	private String user1;
	private String user2;
	private boolean playingUser1;
	private boolean playingUser2;
	private boolean firstPlayer;
	private boolean ready1;
	private boolean ready2;
	private boolean startGame;
	private boolean restart;
	private int scoreUser1;
	private int scoreUser2;

	public DealerController(DealerWindow view) {
		this.view = view;
		init();
		user1 = "";
		user2 = "";
		playingUser1 = true;
		playingUser2 = true;
		firstPlayer = true;
		ready1 = false;
		ready2 = false;
		startGame = false;
		restart = true;
		scoreUser1 = 0;
		scoreUser2 = 0;
	}

	public void init() {
		connection = TCPConnection.getInstance();
		connection.setPuerto(5000);
		connection.start();
		connection.setConnectionListener(this);
		connection.setMessageListener(this);
		initCards();
	}


	@Override
	public void onConnection(String id) {
			Platform.runLater(
				()->{
					System.out.println("<<<Nuevo cliente conectado " +  id +">>>" + "\n");
					Gson gson = new Gson();
					BaseCards cards = new BaseCards(id, giveCardInit(), firstPlayer);
					String json = gson.toJson(cards);
					connection.sendDirectMessage(id, json);
					if(user1.equals("")){
						user1 = id;
						try {
							Thread.sleep(4000);
						} catch(InterruptedException e){
							e.printStackTrace();
						}
					} else{
						user2 = id;
						startGame = true;
					}
					firstPlayer = false;

				}
			);

	}

	public String giveCardInit(){
		String[] cards = new String[2];
		cards[0] = cartas.pop();
		cards[1] = cartas.pop();
		System.out.println(cards[0] + "," + cards[1]);
		return cards[0] + "," + cards[1];
	}

	public String additionalCard(){
		return cartas.pop();
	}

	public void initCards(){
		cartas = new Stack<String>();
		String[] prefix = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
		String[] type = {"C", "T", "D", "P"};
		String[] cards = new String[52];
		int contador = 0;
		for(int i = 0; i < prefix.length; i++){
			for(int j = 0; j < type.length; j++){
				cards[contador] = (prefix[i] + type[j]);
				contador++;
			}
		}
		for ( int i = 51; i > 0; i-- ) {
			int rand = (int)(Math.random()*(i+1));
			String temp = cards[i];
			cards[i] = cards[rand];
			cards[rand] = temp;

		}

		for(int i = 0; i < cards.length; i++){
			cartas.push(cards[i]);
		}

	}

	public void whoIsNext(String infoId){
		Gson gson = new Gson();
		if(infoId.equals(user1)) {
			if(playingUser2 == true) {
				Turn turn = new Turn(user2);
				String json = gson.toJson(turn);
				connection.sendDirectMessage(user2, json);
			} else{
				Turn turn = new Turn(user1);
				String json = gson.toJson(turn);
				connection.sendDirectMessage(user1, json);
			}
		} else if (infoId.equals(user2)){
			if(playingUser1 == true) {
				Turn turn = new Turn(user1);
				String json = gson.toJson(turn);
				connection.sendDirectMessage(user1, json);
			} else{
				Turn turn = new Turn(user2);
				String json = gson.toJson(turn);
				connection.sendDirectMessage(user2, json);
			}
		}
	}

	public void notifyWinner(String id){
		Win winner = new Win(id, "Winner");
		Gson gson = new Gson();
		String json = gson.toJson(winner);
		connection.sendDirectMessage(id, json);
	}

	public void notifyLoser(String id){
		Lost loser = new Lost(id, "Loser");
		Gson gson = new Gson();
		String json = gson.toJson(loser);
		connection.sendDirectMessage(id, json);
	}

	public void playersReady(String id){
		if(id.equals(user1)){
			ready1 = true;
		} else{
			ready2 = true;
		}
		if(ready1 && ready2 == true){
			startGame();
		}
		try {
			Thread.sleep(1000);
		} catch(InterruptedException e){
			e.printStackTrace();
		}

		System.out.println(ready1);
		System.out.println(ready2);
	}

	public void chooseWinner(){
		if(scoreUser1 > 21 && scoreUser2 > 21){
			DeadHeat deadHeat = new DeadHeat();
			Gson gson = new Gson();
			String json = gson.toJson(deadHeat);
			connection.sendBroadcast(json);
		} else if(scoreUser1 > 21 && scoreUser2 < 21){
			notifyWinner(user2);
			notifyLoser(user1);
		} else if(scoreUser1 < 21 && scoreUser2 > 21){
			notifyWinner(user1);
			notifyLoser(user2);
		}else if(scoreUser1 > scoreUser2){
			notifyWinner(user1);
			notifyLoser(user2);
		} else if (scoreUser1 < scoreUser2){
			notifyWinner(user2);
			notifyLoser(user1);
		} else if(scoreUser1 == scoreUser2) {
			DeadHeat deadHeat = new DeadHeat();
			Gson gson = new Gson();
			String json = gson.toJson(deadHeat);
			connection.sendBroadcast(json);
		}
	}

	public void restart(){
		Restart reboot = new Restart();
		Gson gson = new Gson();
		String json = gson.toJson(reboot);
		connection.sendBroadcast(json);

		playingUser1 = true;
		playingUser2 = true;
		firstPlayer = true;
		ready1 = false;
		ready2 = false;
		startGame = false;
		restart = true;
		scoreUser1 = 0;
		scoreUser2 = 0;
		initCards();

		BaseCards cards = new BaseCards(user1, giveCardInit(), true);
		json = gson.toJson(cards);
		connection.sendDirectMessage(user1, json);

		BaseCards cards2 = new BaseCards(user2, giveCardInit(), false);
		json = gson.toJson(cards2);
		connection.sendDirectMessage(user2, json);
	}

	public void stand(Stand standInfo){
		if(standInfo.getId().equals(user1)){
			playingUser1 = false;
			scoreUser1 = Integer.parseInt(standInfo.getBody());
		} else{
			playingUser2 = false;
			scoreUser2 = Integer.parseInt(standInfo.getBody());
		}
		if(playingUser1 == false && playingUser2 == false){
			chooseWinner();
		} else {
			try {
				Thread.sleep(2000);
			} catch(InterruptedException e){
				e.printStackTrace();
			}
			whoIsNext(standInfo.getId());
		}
	}

	public void moreTo(MoreTO moreTOInfo){
		if(moreTOInfo.getId().equals(user1)){
			playingUser1 = false;
			scoreUser1 = Integer.parseInt(moreTOInfo.getBody());
		} else{
			playingUser2 = false;
			scoreUser2 = Integer.parseInt(moreTOInfo.getBody());
		}
		if(playingUser1 == false && playingUser2 == false){
			chooseWinner();
		} else{
			if(playingUser1 == true) {
				whoIsNext(user2);
			} else if (playingUser2 == true){
				whoIsNext(user1);
			}
		}
	}

	public void win(Win winInfo){
		if(winInfo.getId().equals(user1)){
			notifyWinner(user1);
			notifyLoser(user2);

		} else{
			notifyWinner(user1);
			notifyLoser(user2);
		}
	}

	@Override
	public void OnMessage(String msg) {
		Platform.runLater(() -> {
					Gson gson = new Gson();
					Generic msjObj = gson.fromJson(msg, Generic.class);
					System.out.println(msjObj.getType());
					switch (msjObj.getType()){
						case "AdditionalCards":
							AdditionalCards info = gson.fromJson(msg, AdditionalCards.class);
							AdditionalCards ac = new AdditionalCards(info.getId(), additionalCard());
							String json = gson.toJson(ac);
							connection.sendDirectMessage(info.getId(), json);

							whoIsNext(info.getId());
							break;

						case "Stand":
							Stand standInfo = gson.fromJson(msg,Stand.class);
							stand(standInfo);
							break;

						case "MoreTO":
							MoreTO moreTOInfo = gson.fromJson(msg, MoreTO.class);
							moreTo(moreTOInfo);
							break;

						case "Win":
							Win winInfo = gson.fromJson(msg, Win.class);
							win(winInfo);
							break;

						case "Restart":
							restart();
							break;

						case "StartGame":
							StartGame startInfo = gson.fromJson(msg, StartGame.class);
							playersReady(startInfo.getId());
							break;


					}
				}
		);


		//A♠
		//3♦
		//9♥
		//K♠
		//J♣
	}

	@Override
	public void startGame() {
		try {
			Thread.sleep(1000);
		} catch(InterruptedException e){
			e.printStackTrace();
		}
		Gson gson = new Gson();
		StartGame start = new StartGame();
		String json = gson.toJson(start);
		connection.sendBroadcast(json);
		//giveCardInit();
	}


}
