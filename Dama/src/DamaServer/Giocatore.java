package DamaServer;

import Utilities.*;
import java.io.IOException;
import java.net.*;

/**
 * La classe `Giocatore` rappresenta un giocatore in una determinata partita. Estende la classe `Thread`
 * per eseguire come un thread separato. Ogni giocatore è associato a un socket, un username
 * e un riferimento alla partita a cui appartiene.
 */
public class Giocatore extends Thread{
    
    //socket del giocatore
    private Socket giocatore=null;
    
    private int id;
    
    //username del giocatore
    private String username;
    
    //riferimento alla partita
    private Partita p=null;
    
    //oggetto per le operazioni I/O
    private OperazioniIOSocket op= new OperazioniIOSocket();
    
     /**
     * Crea un nuovo oggetto `Giocatore`.
     *
     * @param p la partita a cui il giocatore appartiene
     * @param c il socket del giocatore
     * @param id l'ID del giocatore
     */
    public Giocatore(Partita p, Socket c, int id){
        setPartita(p);
        setGiocatore(c);
        setID(id);
    }
    
    @Override
    public void run() {
        
        /**Connetto ai servizi di I/O**/
        try {
            op.Connetti(giocatore);
            
            op.inviaRisposta(1);
        } catch (IOException ex) {
            op.stampaErrore(ex);
        }
        
        /**informazione giocatori**/
        infoGiocatori();
        
        
        //stampa damiara entrambi giocatori
        try {
            inviaDamiera();
        } catch (IOException ex) {
            op.stampaErrore(ex);
        }
        
        /**gestione della partita**/
        while(!p.vinta()){
            
            try {
                //giocatore 1 la mossa
                if(p.getTurno()!=id)
                    op.inviaStringa("ATTESA");
                
                p.iniziaTurno(id);
                
                
                inviaDamiera();
                
                int risposta=-1;
                do{//chiedere la mossa
                    Mossa mossa= op.riceviMossa();

                    
                    //applicare la  mossa se giusta altrimenti richiedere
                    risposta= p.applicaMossa(mossa);

                    op.inviaRisposta(risposta);
                
                }while(risposta==-1);

                //quando la mossa è stata fatta ed è giusta
                p.finisciTurno(id);
                
                inviaDamiera();
                
            } catch (IOException | InterruptedException | ClassNotFoundException ex) {
                op.stampaErrore(ex);
            } catch (Exception ex) {
                op.stampaErrore(ex);
            }
            
        }
        
        try {
            giocatore.close();
        } catch (IOException ex) {
            op.stampaErrore(ex);
        }       
    }

    /**
     * Imposta il socket del giocatore.
     *
     * @param giocatore Il socket del giocatore
     */
    public void setGiocatore(Socket giocatore) {
        this.giocatore = giocatore;
    }

    /**
     * Imposta l'ID del giocatore.
     *
     * @param id L'ID del giocatore
     */
    public void setID(int id) {
        this.id= id;
    }

    /**
     * Imposta la partita a cui il giocatore appartiene.
     *
     * @param p La partita
     */
    public void setPartita(Partita p) {
        this.p = p;
    }
    /**
     * Richiede il nickname del giocatore e imposta il valore corrispondente nella partita.
     */
    private void infoGiocatori() {
        
        /**chiedo il nickname**/
        try {
            username= op.riceviStringa();
        } catch (IOException ex) {
                op.stampaErrore(ex);
        }
        
        /**chiedo pedina**/
        if(id==1){
            p.setGiocatore1(username);
            
            try {
                op.inviaStringa("PEDINA");
                p.setPedina1(op.riceviCarattere());
                
            } catch (IOException e) {
                op.stampaErrore(e);
            }
        }
        else{ 
            
            try {
                
                p.setGiocatore2(username);
                
                char c=p.getPedina1();
                if(c=='X'){
                    p.setPedina2('O');
                }
                else
                    p.setPedina2('X');
                
                op.inviaStringa(String.valueOf(p.getPedina2()));
            }
            catch (Exception e){
                op.stampaErrore(e);
            }
        }

    }
    /**
    * Invia la damiera al giocatore tramite il socket.
    *
    * @throws IOException Se si verifica un errore di I/O durante l'invio della damiera
    */
    private void inviaDamiera() throws IOException {
        String damiera=p.ritornaDamiera(id);

        op.inviaStringa(damiera);
    }

}
