package Utilities;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


/**
 * La classe `OperazioniIOSocket` estende la classe `OperazioniIO` e fornisce metodi per lo scambio di dati tramite socket.
 */
public class OperazioniIOSocket extends OperazioniIO {
    
    
    /**scambio di stringhe, caratteri, interi**/
    private DataInputStream dis;
    private DataOutputStream dos;
    
    /**
     * Costruisce un nuovo oggetto `OperazioniIOSocket`.
     */
    public OperazioniIOSocket(){
        super();
    }
    
    /**
     * Connette l'oggetto `OperazioniIOSocket` a un socket specificato.
     *
     * @param s il socket a cui connettersi
     * @throws IOException se si verifica un errore di I/O durante la connessione
     */
    public void Connetti(Socket s) throws IOException{
     
        
        dis = new DataInputStream(s.getInputStream());
        dos = new DataOutputStream(s.getOutputStream());
    }
    
    /**
     * Invia una stringa tramite il socket.
     *
     * @param str la stringa da inviare
     * @throws IOException se si verifica un errore di I/O durante l'invio
     */
    public void inviaStringa(String str) throws IOException{
        
        dos.writeUTF(str);
        dos.flush();
    }
    
    /**
     * Invia un carattere tramite il socket.
     *
     * @param c il carattere da inviare
     * @throws IOException se si verifica un errore di I/O durante l'invio
     */
    public void inviaCarattere(char c) throws IOException{
        
        dos.writeChar(c);
        dos.flush();
    }
    
    /**
     * Riceve una stringa dal socket.
     *
     * @return la stringa ricevuta
     * @throws IOException se si verifica un errore di I/O durante la ricezione
     */
    public String riceviStringa() throws IOException{
        return dis.readUTF();
    }
    
    /**
     * Riceve un carattere dal socket.
     *
     * @return il carattere ricevuto
     * @throws IOException se si verifica un errore di I/O durante la ricezione
     */
    public char riceviCarattere() throws IOException{
            return dis.readChar();
    }
    
    /**
     * Invia una mossa tramite il socket.
     *
     * @param m la mossa da inviare
     * @throws IOException se si verifica un errore di I/O durante l'invio
     */
    public void inviaMossa(Mossa m) throws IOException{
        
        String s= m.getPosizioneIniziale().getCoordX() + ";" + m.getPosizioneIniziale().getCoordY() + ";" + m.getPosizioneFinale().getCoordX() + ";" + m.getPosizioneFinale().getCoordY();
                
        this.inviaStringa(s);
    }
    
    /**
     * Riceve una mossa dal socket.
     *
     * @return la mossa ricevuta
     * @throws IOException            se si verifica un errore di I/O durante la ricezione
     * @throws ClassNotFoundException se la classe della mossa ricevuta non è trovata
     * @throws Exception              se si verifica un errore generico durante la creazione della mossa
     */
    public Mossa riceviMossa() throws IOException, ClassNotFoundException, Exception {
        
        String s= this.riceviStringa();
        String[] numeriStringa = s.split(";");

        Posizione ini= new Posizione(Integer.parseInt(numeriStringa[0]), Integer.parseInt(numeriStringa[1]));
        Posizione fin= new Posizione(Integer.parseInt(numeriStringa[2]), Integer.parseInt(numeriStringa[3]));

        Mossa m= new Mossa(ini,fin);
        
        
        return m;
    }
    
    /**
     * Invia una risposta intera tramite il socket.
     *
     * @param r la risposta intera da inviare
     * @throws IOException se si verifica un errore di I/O durante l'invio
     */
    public void inviaRisposta(int r) throws IOException{
        dos.writeInt(r);
        dos.flush();
    }
    
    /**
    * Riceve una risposta intera dal socket.
    *
    * @return la risposta intera ricevuta
    * @throws IOException se si verifica un errore di I/O durante la ricezione
    */
    public int riceviRisposta() throws IOException{
        return dis.readInt();
    }
    
}
