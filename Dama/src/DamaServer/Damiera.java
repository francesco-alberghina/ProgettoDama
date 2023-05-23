package DamaServer;

import Utilities.Mossa;

/**
 * La classe `Damiera` rappresenta il campo di gioco del gioco della dama.
 * Contiene la matrice che rappresenta le celle del campo, i metodi per l'inizializzazione del campo e per l'effettuazione degli spostamenti delle pedine.
 */
public class Damiera {
    
    //dimensione righe e colonna damiera
    private final int DIM= 8;
    
    //CAMPO DA GIOCO 
    private char[][] campo;
    
    /**pediana corrente è quella su cui la mossa avviene
     * pedina contraria è quella avversaria
     */
    private char pedinaCorrente='X', pedinaContraria='O';
    
    
    /**
     * Crea un oggetto Damiera e inizializza il campo di gioco.
     */
    public Damiera()
    {
        campo =  new char[DIM][DIM];
        InizializzaCampo('X');
        RovesciaDamiera();
        InizializzaCampo('O');
    }
    
    
    /**
     * Effettua lo spostamento di una pedina sulla damiera.
     *
     * @param m la mossa da effettuare
     * @param c il carattere che rappresenta la pedina corrente ('X' o 'O')
     * @return il codice che rappresenta l'esito dello spostamento:
     *         0 se la mossa è uno spostamento normale,
     *         1 se si ha mangiato una pedina avversaria,
     *         2 se si è arrivati alla fine del campo (e si ha vinto la partita),
     *         -1 se la mossa non è valida
     */
    public int EffettuaSpostamento(Mossa m, char c)
    {      
        //controllo se la coordinata y è uguale a DIM-1 ; in caso positivo restituisco 2
        //devo controllare che tra due celle (nel caso lo spostamento fosse di 2) ci sia una pedina contraria a quella corrente
        // restituisco i seguenti codici: 0 se la mossa è uno spostamento normale, 1 se si ha mangiato una pedina avversaria, 2 se si è arrivati alla fine del campo (e si ha vinto la partita) oppure -1 in caso in cui la pedina non fosse valida
        int esito = -1;
                
        if(c!=pedinaCorrente)
        {
            pedinaCorrente = c;
            RovesciaDamiera();
        }
        
        if(isMossaValida(m, c))
        {
            esito = 0;
            

            //muovo la pedina dalle coordinate iniziali (salvate nell'oggetto mossa) alle coordinate finali            
            char temp = campo[m.getPosizioneIniziale().getCoordX()][m.getPosizioneIniziale().getCoordY()];
            campo[m.getPosizioneIniziale().getCoordX()][m.getPosizioneIniziale().getCoordY()] = campo[m.getPosizioneFinale().getCoordX()][m.getPosizioneFinale().getCoordY()];
            campo[m.getPosizioneFinale().getCoordX()][m.getPosizioneFinale().getCoordY()] = temp;

            if (isMangiata(m.getPosizioneIniziale().getCoordX(), m.getPosizioneIniziale().getCoordY(), m.getPosizioneFinale().getCoordX(), m.getPosizioneFinale().getCoordY())) 
            {
                // La mossa è una mangiata
                esito = 1;
                int posDaTogliereX;
                int posDaTogliereY;
                
                if(m.getPosizioneIniziale().getCoordY() > m.getPosizioneFinale().getCoordY())
                {
                    posDaTogliereX = m.getPosizioneIniziale().getCoordX()-1;
                    posDaTogliereY = m.getPosizioneIniziale().getCoordY()-1;
                }
                else
                {
                    posDaTogliereX = m.getPosizioneIniziale().getCoordX()-1;
                    posDaTogliereY = m.getPosizioneIniziale().getCoordY()+1;
                }
                
                campo[posDaTogliereX][posDaTogliereY] = ' ';
             } 
            if(m.getPosizioneFinale().getCoordX() == 0)
            {
                // LA PEDINA E' ARRIVATA ALLA FINE (ALLA RIGA 0)
                esito = 2;
            }
        }
        
        
        return esito;
    }    
    
     /**
     * Verifica se una mossa è valida.
     *
     * @param mossa la mossa da verificare
     * @param pedina il carattere che rappresenta la pedina corrente ('X' o 'O')
     * @return true se la mossa è valida, false altrimenti
     */
    public boolean isMossaValida(Mossa mossa, char pedina) 
    {
        
        int xIniziale = mossa.getPosizioneIniziale().getCoordX();
        int yIniziale = mossa.getPosizioneIniziale().getCoordY();
        
        int xFinale = mossa.getPosizioneFinale().getCoordX();
        int yFinale = mossa.getPosizioneFinale().getCoordY();
        
        // Verifica se la pedina non esce dai limiti del campo (per tutti i 4 lati della damiera)
        boolean limiteValido = (xFinale >= 0 && xFinale < DIM && yFinale >= 0 && yFinale < DIM) && (xIniziale >= 0 && xIniziale < DIM && yIniziale >= 0 && yIniziale < DIM);
        
        boolean cellaGiusta = false;
        
        boolean spostamentoValido=false;
        
        boolean mangiata=false;
        
        if(limiteValido){
            cellaGiusta = (campo[xFinale][yFinale] == ' ') && (campo[xIniziale][yIniziale] == pedina);
        
            // Verifica se la mossa è un solo spostamento a destra o sinistra in avanti

            if(isMangiata(xIniziale, yIniziale, xFinale, yFinale))
                spostamentoValido = (yFinale == yIniziale+2 || yFinale == yIniziale-2) && (xFinale == xIniziale-2); //il controllo della coordinata X è già stato fatto nella classe mossa
            else
                spostamentoValido = (yFinale == yIniziale+1 || yFinale == yIniziale-1) && (xFinale == xIniziale-1);


            // Verifica se la mossa è una mangiata
             mangiata = isMangiata(xIniziale, yIniziale, xFinale, yFinale);
        }
        
        return spostamentoValido && limiteValido && cellaGiusta;
    }
    /**
    * Verifica se una mangiata è possibile tra due coordinate.
    *
    * @param rigaPartenza    la riga di partenza
    * @param colonnaPartenza la colonna di partenza
    * @param rigaDestinazione la riga di destinazione
    * @param colonnaDestinazione la colonna di destinazione
    * @return true se la mangiata è possibile, false altrimenti
    */
    public boolean isMangiata(int rigaPartenza, int colonnaPartenza, int rigaDestinazione, int colonnaDestinazione) 
    {
        // Controllo se la distanza tra partenza e destinazione è di esattamente 2 righe e 2 colonne
        if (Math.abs(rigaPartenza - rigaDestinazione) == 2 && Math.abs(colonnaPartenza - colonnaDestinazione) == 2) 
        {
            int rigaIntermedia = (rigaPartenza + rigaDestinazione) / 2;
            int colonnaIntermedia = (colonnaPartenza + colonnaDestinazione) / 2;

            // Controllo se la posizione intermedia contiene una pedina avversaria
            char pedinaAvversaria;

            if (pedinaCorrente == 'X')             
                pedinaAvversaria = 'O';
            else
                pedinaAvversaria = 'X';
            
            return campo[rigaIntermedia][colonnaIntermedia] == pedinaAvversaria;
        }

        return false;
    }
    
    /**
    * Inverte la disposizione delle pedine sulla dama.
    */
    public void RovesciaDamiera()
    {
        if(pedinaContraria == 'O')
            pedinaContraria = 'X';
        else
            pedinaContraria = 'O';
        
        for(int i = 0; i<DIM/2; i++) //i è la riga corrente
        {
            for(int j = 0; j<DIM; j++) //j è la colonna corrente
            {
                char temp = campo[i][j];
                campo[i][j] = campo[DIM-1-i][DIM-1-j];
                campo[DIM-1-i][DIM-1-j] = temp;
            }
        }
    }
    
    /**
    * Inizializza il campo da gioco con le pedine.
    *
    * @param c il carattere che rappresenta la pedina corrente ('X' o 'O')
    */
    public void InizializzaCampo(char c)
    {
        for(int i = 0; i<3; i++) //i è la riga corrente
        {
            for(int j = 0; j<DIM; j++) //j è la colonna corrente
            {
                if((i == 0 || i == 2) && j%2==0)
                {
                    campo[i][j] = c;
                }
                
                if(i == 1 && j%2 != 0)
                {
                    campo[i][j] = c;
                }
            }
        }
        
        for(int i = 0; i<8; i++) //i è la riga corrente
        {
            for(int j = 0; j<DIM; j++) //j è la colonna corrente
            {
                if(campo[i][j] != 'X' && campo[i][j] != 'O'){
                    campo[i][j]= ' ';
                }
            }
        }
        
    }
    
    /**
    * Restituisce il carattere presente in una cella specifica della dama.
    *
    * @param x la coordinata x della cella
    * @param y la coordinata y della cella
    * @return il carattere presente nella cella specificata
    */
    public char getCella(int x, int y)
    {
        return this.campo[x][y];
    }

    /**
    * Restituisce una rappresentazione testuale della dama.
    *
    * @param c il carattere che rappresenta la pedina corrente ('X' o 'O')
    * @return una stringa che rappresenta la dama
    */
    public String toString(char c) 
    {       
        if(c!=pedinaCorrente)
        {
            pedinaCorrente = c;
            RovesciaDamiera();
        }
        
        //char c= carattere X o O --> per capire se girare la damiera
        StringBuilder sb = new StringBuilder();

        sb.append("  0 1 2 3 4 5 6 7\n");
        
        
        for (int riga = 0; riga < 8; riga++) {
            sb.append(riga).append(" ");
            
            for (int colonna = 0; colonna < 8; colonna++) {

                sb.append(campo[riga][colonna]).append(" ");
            }
            
            sb.append("\n");
        }
        
        return sb.toString();
    
    }    
}
