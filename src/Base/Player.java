package Base;

import java.util.*;
import java.util.stream.Stream;

public class Player {
    private LinkedList<Cartas> cartasPlayer;
    private boolean  winRodada, mao;
    private boolean win1, win2, win3;
    private int pontosPartida = 0;
    private int decisao = 0;
    private boolean chamouTruco;
    private boolean chamouEnvido;



    public Player(){
        this.cartasPlayer = new LinkedList<>();
        resetPlayer();

    }

    public boolean isChamouTruco() {
        return chamouTruco;
    }
    public boolean isChamouEnvido() {
        return chamouEnvido;
    }
    public void setChamouEnvido(boolean envido){
        this.chamouEnvido = envido;
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

    public void updatePontosPartida(int pontos){
        setPontosPartida(getPontosPartida() + pontos);
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
        this.cartasPlayer.clear();
        this.win1 = false;
        this.win2 = false;
        this.win3 = false;
        setDecisaoUndefined();
        this.winRodada = false;
        this.mao = false;
        this.chamouTruco = false;
        this.chamouEnvido = false;
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
        LinkedList<Cartas> mesmoTipo = Baralho.getMesmoTipo(getCartasPlayer());
        if(!mesmoTipo.isEmpty()){
            int soma = 20;
            List<Cartas> envido = new ArrayList<>(mesmoTipo.stream().filter(cartas -> cartas.numero() < 10).toList());
            for(Cartas cartas: envido){
                System.out.println(cartas.numero());
            }
            if(envido.size() > 2){ // Se for flor
                Cartas min = envido.get(0);
                for(Cartas c: envido){
                    if(c.numero() < min.numero()){
                        min = c;
                    }
                }
                envido.remove(min);
            }
            for(Cartas cartas: envido){
                soma += cartas.numero();
            }

            return soma;
        }
        Stream<Cartas> envido = getCartasPlayer().stream().filter(cartas -> cartas.numero()  < 10);
        Optional<Cartas> maiorCarta = envido.max(Comparator.comparing(Cartas::numero));
        return maiorCarta.isEmpty() ? 0: maiorCarta.get().numero();

    }
}
