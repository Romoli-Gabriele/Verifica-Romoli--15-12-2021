package it.meucci;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class MultiSrv {
    Vector<ServerThread> threadList = new Vector<ServerThread>(); //Creazione lista dei gestori dei client
    int nBiglietti = 5;
    public void start() {
        try {
            ServerSocket server = new ServerSocket(6789); //Apertura porta
            String ind = InetAddress.getLocalHost().getHostAddress();//IP server
            System.out.println("Server partito in esecuzione..." + ind);//Stampo anche IP
            int index = 0;
            for (;;index++) {
                Socket socket = server.accept();//accetta client e libera la porta
                System.out.println("Server socket " + server);//Avverte l'entrata di client
                ServerThread serverthread = new ServerThread(socket,server,this, index);//creazione thread per gestire il client
                threadList.add(serverthread);//aggiungi il gestore appena creato alla lista
                serverthread.start();//Lo avvio
            }
        } catch (Exception e) {
            System.out.println("Errore connessione client o creazione thread");
            System.exit(1);
        }
    }
    public void close(int index){//chiusura di tutti i socket
        for(int i = 0;i < threadList.size(); i++){
            if(index == i){
                threadList.get(i).close(false);//l'ultimo che ha richiesto l'acquisto ----> Biglietti esauriti
            }else{
                threadList.get(i).close(true);//tutti gli altri ----> Disponibili 0 Biglietti
            }
            
        }
        System.exit(1);
    }
    
    public String vendi(int index){// metodo della vendita
        String StringMD;
        if(nBiglietti > 0){
            StringMD = ("Biglietto acquistato\n");
            nBiglietti--;
        }else{
            StringMD = "Biglietti Esauriti\n";
            this.close(index);//chiama la chiusura di tutto se non ci sono biglietti disponibili
        }
        return StringMD;
    }

}
