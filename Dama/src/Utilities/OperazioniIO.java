/**
 * Utilities rappresenta l'insieme delle classi (risorse) utilizzate da entrambi programmi lato client e server
 */
package Utilities;

import java.io.*;

/**
 * La classe `OperazioniIO` fornisce metodi per l'input/output e la gestione degli errori.
 */
public class OperazioniIO {
    
    protected BufferedReader lettore; 
     
    /**
     * Costruttore della classe OperazioniIO.
     * Inizializza il lettore di input da console.
     */
    public OperazioniIO(){ 
        
        lettore= new BufferedReader(new InputStreamReader(System.in));
    }
     
    /**
     * Stampa una riga di testo seguita da un carattere di nuova linea.
     *
     * @param m il messaggio da stampare
     */
    public void scriviLinea(String m)
    {
        System.out.println(m);
    }
    
    /**
     * Stampa un intero seguito da un carattere di nuova linea.
     *
     * @param m l'intero da stampare
     */
    public void scriviLinea(int m){ 
        System.out.println(m);
    }
    
    /**
     * Stampa un carattere di nuova linea.
     */
    public void scriviLinea(){scriviLinea("");}
    public void scrivi(){scrivi("");}
    
    /**
     * Stampa una stringa.
     *
     * @param m la stringa da stampare
     */
    public void scrivi(String m)
    {
        System.out.print(m);
    }
    
     /**
     * Stampa un intero.
     *
     * @param m l'intero da stampare
     */
    public void scrivi(int m)
    {
        System.out.print(m);
    }
    
    /**
     * Legge una stringa in input da console.
     *
     * @return la stringa letta
     * @throws IOException se si verifica un errore durante la lettura
     */
    public String leggiString() throws IOException{
        String s=lettore.readLine();
        return s;
    }
    
    /**
     * Legge un intero in input da console.
     *
     * @return l'intero letto
     * @throws IOException se si verifica un errore durante la lettura
     */
    public int leggiInt() throws IOException{

        int a = Integer.parseInt(lettore.readLine());
            
        return a;
    }

     /**
     * Stampa un messaggio di errore seguito dalla descrizione dell'eccezione.
     *
     * @param e l'eccezione da gestire
     */
    public void stampaErrore(Exception e){
        this.scriviLinea("Errore! Descrizione: " + e.getMessage());
    }
    
     /**
     * Pulisce la console stampando righe vuote.
     */
    public void clearConsole() {
        for (int i = 0; i < 50; i++) {
            System.out.println(); // Stampa una riga vuota
        }
    }
}
