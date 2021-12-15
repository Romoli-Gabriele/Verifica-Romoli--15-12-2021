package it.meucci;


public class App 
{
    public static void main( String[] args )
    {
        MultiSrv topServer = new MultiSrv();//Creo istanza multi server 
        topServer.start(); // lo metto in attesa dei client
    }
}
