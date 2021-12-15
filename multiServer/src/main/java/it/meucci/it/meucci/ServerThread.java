package it.meucci;

import java.net.*;
import java.io.*;

public class ServerThread extends Thread {
    ServerSocket server = null;
    Socket client = null;
    String StringRV = null;
    String StringMD = null;
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;
    MultiSrv allThread;
    int index;

    public ServerThread(Socket socket, ServerSocket server, MultiSrv gestore, int index) {
        this.client = socket;
        this.server = server;
        this.allThread = gestore;
        this.index = index;
    }

    public void run() {
        try {
            comunica();
        } catch (Exception e) {
        }

    }
    public void close(boolean D){
        try {
            String rispostaChiusura;
            if(D){
                rispostaChiusura =("Disponibili 0 biglietti\n");//Chi ha richiesto l'acquisto (se c'è)
            }else{
                rispostaChiusura =("Biglietti Esauriti\n");//Tutti gli altri
            }
            outVersoClient.writeBytes(rispostaChiusura);// invia segnale al client di chiudersi
            outVersoClient.close();
            inDalClient.close();
            client.close();
        } catch (IOException e) {
            System.out.println("Errore invio messaggio di chiusura");
        }
    }

    public void comunica() throws Exception {// gestione comunicazione con il singolo client
        inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
        outVersoClient = new DataOutputStream(client.getOutputStream());
        for (;;) {
            StringRV = inDalClient.readLine();//Lettura dal client
            if(StringRV.equals("D")){
                StringMD = ("Disponibili "+allThread.nBiglietti+" biglietti\n");
                if(allThread.nBiglietti==0){
                    allThread.close(-1);//Se viene richiesta la disponibilità non si risponde con "biglietti esauriti" nemmeno a chi la ha richiesto
                }
            }else if(StringRV.equals("A")){
               StringMD = allThread.vendi(this.index);//Invia identificatore del thread al server per rispondere "biglietti esauriti"  a chi richiede l'acquisto
            }
            outVersoClient.writeBytes(StringMD+'\n');//Invio risposta
        }
        
    }
}