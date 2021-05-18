package comm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

import comm.Sessions;

import comm.Receptor.OnMessageListener;

public class TCPConnection extends Thread {

    //SINGLETON
    private static TCPConnection instance = null;

    private TCPConnection() {
        sessions = new ArrayList<>();
        //waitingSessions = new ArrayList<>();
    }


    public static synchronized TCPConnection getInstance() {
        if (instance == null) {
            instance = new TCPConnection();
        }
        return instance;
    }


    //GLOBAL
    private int puerto;
    private OnConnectionListener connectionListener;
    private OnMessageListener messageListener;
    private ServerSocket server;
    private ArrayList<Sessions> sessions;
    //private ArrayList<Sessions> waitingSessions;


    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    @Override
    public void run() {
        try {
            server = new ServerSocket(puerto);

            while (true) {
                System.out.println("Esperando en el puerto " + puerto);
                Socket socket = server.accept();
                System.out.println("Nuevo cliente conectado");
                String id = UUID.randomUUID().toString();
                Sessions session = new Sessions(id, socket);

                if (sessions.size() < 2) {
                    sessions.add(session);
                    connectionListener.onConnection(id);
                    setAllMessageListener(messageListener);
                } else {
                    System.out.println("No se pudo aceptar el cliente nuevo, cantidad máxima de clientes alcanzado");
                    //waitingSessions.add(session);
                    //System.out.println("Cliente agregado a lista de espera, hay " + waitingSessions.size() + " clientes en espera");
                }
            }


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setAllMessageListener(OnMessageListener listener) {
        for (int i = 0; i < sessions.size(); i++) {
            Sessions s = sessions.get(i);
            s.getReceptor().setListener(listener);
        }
    }

    public void setConnectionListener(OnConnectionListener connectionListener) {
        this.connectionListener = connectionListener;
    }

    public interface OnConnectionListener {
        public void onConnection(String id);
        public void startGame();
    }

    public void setMessageListener(OnMessageListener messageListener) {
        this.messageListener = messageListener;
    }


    public void sendBroadcast(String msg) {

        for (int i = 0; i < sessions.size(); i++) {
            Sessions s = sessions.get(i);
            s.getEmisor().sendMessage(msg);
        }

    }

    public void sendDirectMessage(String id, String msg) {
        for (int i = 0; i < sessions.size(); i++) {
            if (sessions.get(i).getId().equals(id)) {
                sessions.get(i).getEmisor().sendMessage(msg);
                break;
            }
        }
    }

    public ArrayList<Sessions> getSessions() {
        return sessions;
    }
}
