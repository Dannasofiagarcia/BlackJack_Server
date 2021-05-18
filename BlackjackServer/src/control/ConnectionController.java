package control;

import comm.*;
import javafx.application.*;
import view.*;

public class ConnectionController implements TCPConnection.OnConnectionListener{

	private ConnectionWindow view;
	private TCPConnection connection;

	public ConnectionController(ConnectionWindow view) {
		this.view = view;
		init();
	}

	public void init() {
		connection = TCPConnection.getInstance();
		connection.setPuerto(5000);
		connection.setConnectionListener(this);
		connection.start();
	}

	@Override
	public void onConnection(String id) {
		//Estamos conectados
		//No se puede usar metodos con resultado grafico en un hilo que no sea el principal
		Platform.runLater(

				()->{
					//if(connection.getSessions().size() == 2) {
						DealerWindow window = new DealerWindow();
						window.show();
						view.close();
					//}
				}

				);
	}

	@Override
	public void startGame() {

	}


}
