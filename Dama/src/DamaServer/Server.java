/**
 * DamaServer rappresenta il programma lato server che gestirà le partite
 */
package DamaServer;

import Utilities.OperazioniIO;
import java.io.*;
import java.net.*;

/**
 * La classe `Server` rappresenta il lato server del programma.
 */
public class Server {
    
    private static ServerSocket server=null;
    
    static private OperazioniIO op= new OperazioniIO();
    
    //tener conto delle partite aperte
    static int nPartita=1;
    
    /**
     * Avvia il server e gestisce l'avvio delle partite.
     *
     * @param args gli argomenti della riga di comando
     * @throws IOException se si verifica un errore di I/O durante l'esecuzione del server
     */
     public static void main(String[] args) throws IOException{
        
        //----------MESSAGGIO INIZIALE
        op.scriviLinea("Benvenuto nel programma lato server");
        
        /**Inizializzo Socket Server**/
        try{
            server=new ServerSocket(5260);
        }
        catch (IOException e){
               op.stampaErrore(e);
        }
        
        try{
            while(true){
               try{
                    avviaPartita(nPartita);
                    nPartita++;
               }
               catch (Exception e){
                   op.scriviLinea("Errore avvio " + nPartita + "° partita : " + e);
               }
            }
        }catch (Exception e){
            server.close();
        }
    }

     /**
     * Avvia una partita specificata, aspettando 2 collegamenti client e creando una partita
     *
     * @param i il numero della partita
     * @throws IOException se si verifica un errore di I/O durante l'avvio della partita
     */
    private static void avviaPartita(int i) throws IOException {
        
       //Avvio delle partite
       op.scriviLinea("Partita numero "+ i + ", attendo giocatori");
       
       Partita match= new Partita(i);
       
       //giocatori
       Socket giocatore1=null;
       Socket giocatore2= null;
       
       //attesa 2 giocatori
       giocatore1 = server.accept();  //accettiamo i client 
       op.scriviLinea("Giocatore 1 entrato in lobby...");
       
       giocatore2 = server.accept();  //accettiamo i client 
       op.scriviLinea("Giocatore 2 entrato, avvio partita...");
       
       Giocatore t1 = new Giocatore(match, giocatore1, 1);
       Giocatore t2= new Giocatore(match, giocatore2, 2);
       
       t1.start();
       t2.start();
    }
}
