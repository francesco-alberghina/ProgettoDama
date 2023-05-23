package Utilities;

/**
 * La classe `Mossa` rappresenta una mossa all'interno del gioco.
 * Contiene le informazioni sulla posizione iniziale e finale della mossa,
 * nonché il flag di validità della mossa.
 */
public class Mossa{
    private boolean valida;
    private Posizione posizioneIniziale, posizioneFinale;
    
    /**
     * Crea un oggetto Mossa con la posizione iniziale e finale specificate.
     * Verifica anche la validità della mossa.
     *
     * @param posI la posizione iniziale della mossa
     * @param posF la posizione finale della mossa
     * @throws Exception se le posizioni non sono valide
     */
    public Mossa(Posizione posI, Posizione posF) throws Exception
    {
        setPosizioneIniziale(posI);
        setPosizioneFinale(posF);
        this.valida = mossaValida(posizioneIniziale, posizioneFinale);        
    }
    
    /**
     * Verifica se una mossa è valida confrontando le coordinate X delle due posizioni.
     *
     * @param p1 la posizione iniziale della mossa
     * @param p2 la posizione finale della mossa
     * @return true se la mossa è valida, false altrimenti
     */
    public boolean mossaValida(Posizione p1, Posizione p2) //verifica se la coordinata Y della posizione finale è maggiore di quella della posizione finale
    {
        boolean temp = true;
        
        if(posizioneIniziale.getCoordX() < posizioneFinale.getCoordX())
            temp = true;
            
        return temp;
    }
    
    /**
     * Restituisce la posizione iniziale della mossa.
     *
     * @return la posizione iniziale della mossa
     */
    public Posizione getPosizioneIniziale()
    {
        return this.posizioneIniziale;
    }
    
    /**
     * Restituisce la posizione finale della mossa.
     *
     * @return la posizione finale della mossa
     */
    public Posizione getPosizioneFinale()
    {
        return this.posizioneFinale;
    }
    
    /**
     * Restituisce il flag di validità della mossa (se è valida o meno).
     *
     * @return true se la mossa è valida, false altrimenti
     */
    public boolean getValida()
    {
        return this.valida;
    }
    
    /**
     * Imposta la posizione finale della mossa.
     *
     * @param a la posizione finale della mossa
     * @throws Exception se la posizione non è valida
     */
    public void setPosizioneIniziale(Posizione a) throws Exception
    {
        if(a.posizionePositiva())
            this.posizioneIniziale = a;
        
        else
            throw new Exception("Il parametro posizione iniziale deve avere entrambe le coordinate positive!");
    }
    
    /**
     * Imposta la posizione iniziale della mossa.
     *
     * @param a la posizione finale della mossa
     * @throws Exception se la posizione non è valida
     */
     public void setPosizioneFinale(Posizione a) throws Exception
    {
        if(a.posizionePositiva())
            this.posizioneFinale = a;
        
        else
            throw new Exception("Il parametro posizione finale deve avere entrambe le coordinate positive!");
    }
        
}
