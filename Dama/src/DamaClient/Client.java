/**
 * DamaClient rappresenta il programma lato client che si avvierà
 */
package DamaClient;

import Utilities.*;
import java.io.IOException;
import java.net.*;

/**
 * Classe `Client` per il gioco della Dama.
 * Si connette a un server e gestisce la comunicazione tra client e server per la partita.
 */
public class Client {
    
    //socket a cui il client si collegherà
    private static Socket server=null;
    
    //oggetto per tutte le operazioni I/O lato client 
    private static OperazioniIOSocket op= new OperazioniIOSocket();
    
    //nome Giocatore
    private static String nomeGiocatore=null;
    
    //rappresenta la partita
    private static boolean partitaOn=false;
    
    /**
     * Punto di ingresso dell'applicazione client.
     *
     * @param args gli argomenti da linea di comando
     * @throws IOException se si verifica un errore I/O
     */
    public static void main(String[] args) throws IOException{
        
        //----------MESSAGGIO INIZIALE
        op.scriviLinea("Benvenuto nel programma del client.");
        
        //porta e indirizzo di collegamento
        int porta= 5260;
        String serverAddress= "localhost";
        
        
        try{
            nomeGiocatore= inserisciGiocatore();
        }catch(IOException e ){
            op.stampaErrore(e);
        }
        
        connessioneServer(serverAddress, porta);
        
        op.riceviRisposta();
        
        invioInfo(nomeGiocatore);
        
        //gestione partita
        avviaPartita();      
    }
    
    /**
     * Effettua la connessione al server.
     *
     * @param indirizzo l'indirizzo del server (default è localhost)
     * @param porta la porta del server
     */
    public static void connessioneServer(String indirizzo, int porta){
        
        try{
            //indirizzo e porta del server
            server = new Socket(InetAddress.getByName(indirizzo), porta);
            
            //avvio degli oggetti per la comunicazione
            op.Connetti(server);
            
        }catch (UnknownHostException ex){
            op.stampaErrore(ex);
        }
        catch(IOException e){
            op.stampaErrore(e);
        }

    }
    
    /**
     * Richiede all'utente di inserire il proprio nome giocatore.
     *
     * @return il nome del giocatore inserito
     * @throws IOException se si verifica un errore I/O
     */
    public static String inserisciGiocatore() throws IOException
    {
        String pedina, nickname;
        op.scriviLinea("Benvenuto in questa partita di DAMA");
        
        op.scrivi("Inserisci ora il tuo nickname per questa partita: ");
        nickname = op.leggiString();
        
        return nickname;
    }

    /**
     * Richiede all'utente di inserire il tipo di pedina da utilizzare.
     *
     * @return il tipo di pedina scelto
     * @throws IOException se si verifica un errore I/O
     */
    private static String chiediPedina() throws IOException {
        String pedina;
        op.scrivi("Inserisci la pedina che vorrai usare (X oppure O): ");
         
        do{
            pedina = op.leggiString();
            pedina = pedina.toUpperCase();
            
            if(!(pedina.equals("X")) && !(pedina.equals("O")))
            {
                op.scrivi("Errore! tipo di pedina inserito non corretto, reinseriscilo: ");
            }
            
        }while(!(pedina.equals("X")) && !(pedina.equals("O")));
        
        return pedina;
    }
    
    
    /**
     * Avvia la partita e gestisce lo svolgimento.
     *
     * @throws IOException se si verifica un errore I/O
     */
    private static void avviaPartita() throws IOException {
            partitaOn=true;
            
            op.scriviLinea();
            /**aspettare la damiera**/
            String schacchiera = op.riceviStringa();

           op.scriviLinea(schacchiera);
            while(partitaOn){
                
                String risposta= op.riceviStringa();
                
                if(!"ATTESA".equals(risposta)){
                    
                    op.clearConsole();
                    op.scriviLinea(risposta);

                    Mossa m= chiediMossa();

                    op.inviaMossa(m);
                    
                    boolean ripeti;
                    do{
                        ripeti=false;
                        switch(op.riceviRisposta()){
                            case -1 -> {
                                op.scriviLinea("Attenzione mossa sbagliata! Reinserire");
                                m= chiediMossa();
                                op.inviaMossa(m);
                                ripeti=true;
                                }
                            case 0 ->{
                                op.clearConsole();
                                op.scriviLinea("Mossa eseguita!");
    
                                break;
                            }
                            
                            case 1 ->{
                                op.clearConsole();
                                op.scriviLinea("Pedina avversaria mangiata!");
                                
                                
                                break;
                            }
                            
                            case 2->{
                                op.clearConsole();
                                op.scriviLinea("VITTORIA! Partita vinta");
                                partitaOn=false;
                                break;
                            }

                        }
                    }while(ripeti);
                    
                    schacchiera = op.riceviStringa();

                    op.scriviLinea(schacchiera);
                    
                    if(partitaOn){
                        op.scriviLinea();
                        op.scriviLinea();
                        op.scriviLinea("Attesa mossa avversario!");
                    }
                }
        
            }
            
            op.scrivi("Partita TERMINATA");
        
    }
    
     /**
     * Invia le informazioni del giocatore al server.
     *
     * @param nome il nome del giocatore
     * @throws IOException se si verifica un errore I/O
     */
    private static void invioInfo(String nome) throws IOException {
        op.inviaStringa(nome);
        
        String risposta= op.riceviStringa();
        
        if("PEDINA".equals(risposta)){
            
            try{
                op.inviaCarattere(chiediPedina().charAt(0));
            }
            catch (IOException e){
                op.stampaErrore(e);
            }
        }
        else
            op.scriviLinea("La tua pedina e': " + risposta);
    }
    
    
    /**
    * Richiede all'utente di inserire una mossa.
    *
    * @return l'oggetto Mossa rappresentante la mossa inserita
    * @throws IOException se si verifica un errore I/O
    * 
    */
    private static Mossa chiediMossa() throws IOException {

        Mossa m = null;
        
        do{
            try{
                op.scrivi("\nInserisci la coordinata Y (la riga) della pedina da spostare: ");
                int tempX = op.leggiInt();
                
                op.scrivi("\nInserisci la coordinata X (la colonna) della pedina da spostare: ");
                int tempY = op.leggiInt();
                
                Posizione p1 = new Posizione(tempX, tempY);
                
                op.scrivi("\nInserisci la coordinata Y (la riga) dove la pedina andra' spostata: ");
                tempX = op.leggiInt();
                
                op.scrivi("\nInserisci la coordinata X (la colonna) dove la pedina andra' spostata: ");
                tempY = op.leggiInt();
                Posizione p2 = new Posizione(tempX, tempY);
                
                m = new Mossa(p1, p2);
            }
            catch(Exception exc)
            {
                op.stampaErrore(exc);
            }
        }while(m==null || !m.getValida() );
        
        return m;
    }
    
}
