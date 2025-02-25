import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MainServer {

    public static void main(String[] args) {
        final int PORT = 5500; // Porta su cui il server ascolta le connessioni
        
        try {
            ServerSocket ServerSocket = new ServerSocket(PORT); // Crea il server sulla porta specificata
            ArrayList<Thread> listaThreadconnessioni = new ArrayList<Thread>(); // Lista di thread per gestire le connessioni
            ListaClient listaClient = new ListaClient(); // Oggetto che mantiene la lista dei client connessi
            
            System.out.println("Server aperto");
            System.out.println("In attesa di connessioni...");

            while (true) { // Ciclo infinito: il server rimane sempre in ascolto per nuovi client
                Socket nuovoClient = ServerSocket.accept(); // Attende un client e accetta la connessione
                
                listaClient.addClient(nuovoClient); // Aggiunge il nuovo client alla lista dei client connessi
                
                // Crea un nuovo thread per gestire la comunicazione con il client
                Thread connessioneThread = new Thread(new ThreadConnessione(nuovoClient, listaClient));
                listaThreadconnessioni.add(connessioneThread); // Aggiunge il thread alla lista
                
                // Avvia il thread appena creato per gestire il client
                listaThreadconnessioni.get(listaThreadconnessioni.size() - 1).start();
                // `size() - 1` serve per ottenere l'ultimo thread aggiunto alla lista
            }

        } catch (IOException e) {
            System.out.println("Errore di connessione");
        }
    }
}

