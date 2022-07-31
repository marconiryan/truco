package Base;

import java.util.LinkedList;

public class Player {
    private LinkedList<Cartas> cartasPlayer;
    private boolean decisao, winRodada;
    private int somaRodada = 0;
    private int pontosPartida = 0;



    public Player(){
        this.cartasPlayer = new LinkedList<>();
        resetPlayer();

    }
    public Player(LinkedList<Cartas> cartasPlayer) {
        this.cartasPlayer = cartasPlayer;
        resetPlayer();
    }

    public int getPontosPartida() {
        return pontosPartida;
    }

    public void setPontosPartida(int pontosPartida) {
        this.pontosPartida = pontosPartida;
    }

    public boolean isWinRodada() {
        return winRodada;
    }

    public void resetPlayer(){
        setSomaRodada(0);
        setWinRodada();

    }

    public void setWinRodada() {
        if(getSomaRodada() > 2)
            this.winRodada = true;
        this.winRodada = false;
    }

    public int getSomaRodada() {
        return somaRodada;
    }

    public void setSomaRodada(int somaRodada) {
        this.somaRodada = somaRodada;
    }

    public boolean isDecisao() {
        return decisao;
    }

    public void setDecisao(boolean decisao) {
        this.decisao = decisao;
    }


    public LinkedList<Cartas> getCartasPlayer() {
        return cartasPlayer;
    }

    public void setCartasPlayer(LinkedList<Cartas> cartasPlayer) {
        this.cartasPlayer = cartasPlayer;
    }
    public int getEnvido(){
        int pontos = 0;

        for(Cartas cartas: getCartasPlayer()){
            if(cartas.numero() < 10){
                pontos += cartas.numero();
            }
        }
        return pontos;
    }
}
