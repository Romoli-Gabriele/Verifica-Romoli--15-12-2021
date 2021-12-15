package cli.client;

import java.io.IOException;

public class App 
{
    public static void main( String[] args ) throws IOException
    {
        client client1 = new client();
        client1.connetti();//Connessione Server
        client1.comunica();//Inizio comunicazione
    }
}
