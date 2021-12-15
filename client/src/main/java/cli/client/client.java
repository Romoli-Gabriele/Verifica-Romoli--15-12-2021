package cli.client;

import java.io.*;
import java.net.*;

public class client {
    String nomeServer = "localhost";
    int portaServer = 6789;
    Socket socket;
    BufferedReader tastiera;
    String StringUser = " ";
    DataInputStream in;
    DataOutputStream out;

    protected Socket connetti() {
        tastiera = new BufferedReader(new InputStreamReader(System.in));
        try {
            socket = new Socket(nomeServer, portaServer); // creazione nuovo Socket
            out = new DataOutputStream(socket.getOutputStream()); // gestione input e output
            in = new DataInputStream(socket.getInputStream());
            
        } catch (Exception e) {
            System.err.println("Errore creazione Socket");
            System.exit(1);
        }
        return socket;
    }

    public void comunica() throws IOException {
        threadClose controllo = new threadClose(in, this); // Creazione thread controllo chiusura da remoto
        controllo.start();
        try {
            for (;;) {
                System.out.println("Scrivi A per acquistare un biglietto o D per vedere quanti sono i biglietti disponibili" + '\n');
                StringUser = tastiera.readLine();
                out.writeBytes(StringUser + '\n');
            }
        } catch (Exception e) {
            socket.close();
            System.exit(1);
        }
    }
}
