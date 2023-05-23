package Utilities;

/**
 * La classe `Posizione` rappresenta una posizione in un sistema di coordinate bidimensionale.
 */
public class Posizione{
    private int coordX, coordY;
    
    
    /**
     * Costruisce un nuovo oggetto `Posizione` con le coordinate specificate.
     *
     * @param x la coordinata x
     * @param y la coordinata y
     */
    public Posizione(int x, int y)
    {
        setCoordX(x);
        setCoordY(y);
    }
        
    /**
     * Verifica se la posizione è positiva, cioè se le coordinate sono entrambe non negative.
     *
     * @return true se la posizione è positiva, false altrimenti
     */
    public boolean posizionePositiva()
    {
        return (this.getCoordX() >= 0 && this.getCoordY() >= 0);
    }
        
    /**
     * Restituisce la coordinata x.
     *
     * @return la coordinata x
     */
    public int getCoordX()
    {
        return this.coordX;
    }
    
    /**
     * Restituisce la coordinata y.
     *
     * @return la coordinata y
     */
    public int getCoordY()
    {
        return this.coordY;
    }    
    
    /**
     * Imposta la coordinata x.
     *
     * @param a il valore da assegnare alla coordinata x
     */
    public void setCoordX(int a)
    {
        this.coordX = a;
    }
    
    /**
     * Imposta la coordinata y.
     *
     * @param a il valore da assegnare alla coordinata y
     */
    public void setCoordY(int a)
    {
        this.coordY = a;
    }
    
}
