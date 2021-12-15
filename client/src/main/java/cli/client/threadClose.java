package cli.client;

import java.net.*;
import java.io.*;

public class threadClose extends Thread {

    DataInputStream in;
    client cli;
    String reply;

    public threadClose(DataInputStream in, client cli) {
        this.in = in;
        this.cli = cli;
    }

    public void run() {
        for (;;) {
            try {
                reply = in.readLine();//Lettura e controllo se bisogna chiudere il  client
                System.out.println(reply+"\n");
                if (reply.equals("Biglietti Esauriti")||reply.equals("Disponibili 0 biglietti")) {
                    System.out.println("Server disconnesso, chiusura client..");
                    this.stop();
                }
            } catch (IOException e) {
                System.out.println("Errore lettura dal Server");
            }
        }
    }

}