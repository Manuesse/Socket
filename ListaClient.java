import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ListaClient {

    private ArrayList<Socket> listaSockets; // Lista di socket dei client connessi

    // Costruttore: inizializza la lista dei client
    public ListaClient() {
        listaSockets = new ArrayList<Socket>();
    }

    // Metodo per aggiungere un client alla lista
    public synchronized void addClient(Socket c) throws IOException {
        listaSockets.add(c); // Aggiunge il socket del client alla lista
    }

    // Metodo per rimuovere un client dalla lista e chiudere il socket
    public synchronized void removeClient(int i) throws IOException { 
        listaSockets.get(i).close(); // Chiude il socket del client
        listaSockets.remove(i); // Rimuove il client dalla lista
    }

    // Metodo per inviare un messaggio a tutti i client tranne a chi lo ha inviato
    public synchronized void sendAll(String message, Socket client) throws IOException {
        for (Socket socket : listaSockets) { // Scorre tutti i client connessi
            if (socket != client) { // Evita di inviare il messaggio al mittente
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                out.println(message); // Invia il messaggio
                out.flush(); // Assicura che il messaggio venga spedito subito
            }
        }
    }
}
