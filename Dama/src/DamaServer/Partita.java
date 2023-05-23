package DamaServer;

import Utilities.Mossa;

/**
 * La classe `Partita` rappresenta una partita del gioco.
 */
public class Partita {
    
    //stato partita
    private boolean vittoria=false;
    
    //numero della partita
    private int numeroPartita;
    
    //campo da gioco
    private Damiera campoGioco;
    
    /**username dei giocatori**/
    private String giocatore1;
    private String giocatore2;
    
    /**pedine dei giocatori**/
    private char pedina1=' ';
    private char pedina2=' ';
    
    //turno sarà 1 o 2 in base al turno del giocatore
    private int turno=1;
    
    /**
     * Crea un nuovo oggetto Partita con il numero specificato.
     *
     * @param n Il numero della partita
     */
    public Partita(int n){
        setNumeroPartita(n);
        campoGioco= new Damiera();
    }
    
    /**
     * Imposta il numero della partita.
     *
     * @param x Il numero della partita
     * @throws IllegalArgumentException Se il parametro è negativo o zero
     */
    public void setNumeroPartita(int x){
        if(x>0){
            numeroPartita=x;
        }else
            throw new IllegalArgumentException("paramentro in arrivo sbagliato");
    }
    
    /**
     * Restituisce la rappresentazione della damiera per il giocatore specificato.
     *
     * @param id L'ID del giocatore
     * @return La rappresentazione della damiera
     */
    synchronized public String ritornaDamiera(int id){
        
        char c=' ';
        
        if(id==1)
            c=pedina1;
        else
            c=pedina2;
        
        return campoGioco.toString(c);
    }
    
    /**
     * Avvia il turno del giocatore specificato.
     *
     * @param id L'ID del giocatore
     * @throws InterruptedException Se l'attesa viene interrotta
     */
    synchronized public void iniziaTurno(int id) throws InterruptedException{
        while(turno!=id || vittoria)
            wait();
    }
    
    /**
     * Conclude il turno del giocatore corrente e passa al successivo.
     *
     * @param id L'ID del giocatore
     */
    synchronized public void finisciTurno(int id){
        if(turno==1)
            turno=2;
        else
            turno=1;
        
        notifyAll();
    }
    
    /**
     * Imposta il nome del giocatore 1.
     *
     * @param giocatore1 Il nome del giocatore 1
     */
    synchronized public void setGiocatore1(String giocatore1) {
        this.giocatore1 = giocatore1;
    }

    /**
     * Imposta il nome del giocatore 2.
     *
     * @param giocatore2 Il nome del giocatore 2
     * @throws InterruptedException Se l'attesa viene interrotta
     */
    synchronized public void setGiocatore2(String giocatore2) throws InterruptedException {
        this.giocatore2 = giocatore2;
        
        while(pedina1==' ')
            wait();
    }
    
    /**
     * Imposta la pedina del giocatore 1.
     *
     * @param pedina La pedina del giocatore 1
     */
    synchronized public void setPedina1(char pedina) {
        this.pedina1 = pedina;
        
        notifyAll();
    }
    
    /**
     * Imposta la pedina del giocatore 2.
     *
     * @param pedina La pedina del giocatore 2
     */
    synchronized public void setPedina2(char pedina) {
        this.pedina2 = pedina;
    }

    /**
    * Restituisce la pedina del giocatore 1.
    *
    * @return La pedina del giocatore 1
    */
    public char getPedina1() {
        return pedina1;
    }

    /**
     * Restituisce la pedina del giocatore 2.
     *
     * @return La pedina del giocatore 2
     */
    public char getPedina2() {
        return pedina2;
    }
    
    /**
     * Verifica se la partita è stata vinta.
     *
     * @return true se la partita è stata vinta, altrimenti false
     */
    synchronized public boolean vinta() {
        return vittoria;
    }

    /**
    * Restituisce il turno corrente.
    *
    * @return L'ID del giocatore del turno corrente
    */
    public int getTurno() {
        return turno;
    }
    
    /**
     * Applica una mossa alla partita.
     *
     * @param m La mossa da applicare
     * @return Il risultato dell'applicazione della mossa (0 se la mossa è stata applicata, 1 se la mossa non è valida, 2 se la mossa ha determinato la vittoria)
     */
    public int applicaMossa(Mossa m){
        int risposta=-1;        
        
        //controllo innanzitutto se la cella è occupata da una pedina, ed in caso positivo se da una pedina giusta per il turno al quale si è
        if(turno == 1)
        {
            risposta = campoGioco.EffettuaSpostamento(m, this.pedina1);
        }
        else
        {
            risposta = campoGioco.EffettuaSpostamento(m, this.pedina2);
        }        
        
        if(risposta==2){
            this.vittoria=true;
        }
        
        return risposta;
    }
}
