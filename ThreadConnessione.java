import java.io.BufferedReader; 
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ThreadConnessione implements Runnable { // Il thread gestisce la connessione con un client
    private Socket client; // Socket del client connesso
    private BufferedReader in; // Stream di input per ricevere messaggi
    private ListaClient listaClient; // Riferimento alla lista dei client connessi
    protected String nomeClient; // Nome del client che si connette
    
    public ThreadConnessione(Socket socket, ListaClient listaClient) throws IOException {
        this.client = socket; // Assegna il socket del client
        this.listaClient = listaClient; // Assegna la lista dei client connessi
        in = new BufferedReader(new InputStreamReader(client.getInputStream())); // Inizializza lo stream di input per leggere i messaggi
        nomeClient = "errore"; // Nome predefinito in caso di errore
    }
    
    public void run() {
        String messaggio = "";
        boolean primo = true; // Flag per gestire il primo messaggio (nome utente)
        
        try {
            while (!Thread.interrupted()) { // Continua finché il thread non viene interrotto
                messaggio = in.readLine(); // Legge un messaggio dal client
                
                if (primo) { // Se è il primo messaggio ricevuto, lo considera il nome del client
                    nomeClient = messaggio;
                    System.out.println(nomeClient + " Connesso"); // Stampa il nome del client connesso
                    primo = false;
                } else {
                    // Invia il messaggio a tutti gli altri client connessi
                    listaClient.sendAll(nomeClient + ": " + messaggio, client);
                }
            }
        } catch (IOException e) {
            System.out.println("Connessione interrotta con " + nomeClient);
        }
    }
}
