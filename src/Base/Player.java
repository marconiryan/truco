package Base;

import java.util.LinkedList;

public class Player {
    private LinkedList<Cartas> cartasPlayer;
    private boolean  winRodada, mao;
    private boolean win1, win2, win3;
    private int pontosPartida = 0;
    private int decisao= 0 ;
    private boolean chamouTruco;



    public Player(){
        this.cartasPlayer = new LinkedList<>();
        resetPlayer();

    }

    public boolean isChamouTruco() {
        return chamouTruco;
    }

    public void setChamouTruco(boolean chamouTruco) {
        this.chamouTruco = chamouTruco;
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

    public boolean isMao() {
        return mao;
    }

    public void setMao(boolean mao) {
        this.mao = mao;
    }

    public void resetPlayer(){

    }

    public void setWinRodada() {
        this.winRodada = true;

    }
    public void setDecisao(int decisao){this.decisao = decisao;}
    public void setDecisaoAccepted(){this.decisao = 1;}
    public void setDecisaoDenied(){this.decisao = -1;}
    public void setDecisaoUndefined(){this.decisao = 0;}
    public boolean isDecisaoAccepted(){return this.decisao == 1;}
    public boolean isDecisaoDenied(){return this.decisao == -1;}
    public boolean isDecisaoUndefined(){return this.decisao == 0;}


    public boolean isWin1() {
        return win1;
    }

    public void setWin1() {
        this.win1 = true;
    }

    public boolean isWin2() {
        return win2;
    }

    public void setWin2() {
        this.win2 = true;
    }

    public boolean isWin3() {
        return win3;
    }

    public void setWin3() {
        this.win3 = true;
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
