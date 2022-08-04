package Graphics;

import Base.*;
import Main.Mouse;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class PlayerLogic {

    PlayerGraph playerGraph;
    PlayerGraph playerEnemy;
    Player player;
    Player enemy;
    LinkedList<Cartas> first, mid, last;
    ButtonTruco buttonTruco;
    ButtonDecisao buttonDecisao;
    Mouse mouse;
    Pontos pontos;

    public PlayerLogic(Player player, Player enemy, int windowWidth, Mouse mouse, Pontos pontos, PlayerGraph playerGraph, PlayerGraph playerEnemy){
        this.player = player;
        this.enemy = enemy;
        this.first = new LinkedList<>();
        this.mid = new LinkedList<>();
        this.last = new LinkedList<>();
        this.mouse = mouse;
        this.pontos = pontos;
        this.buttonTruco = new ButtonTruco(mouse, pontos);
        this.buttonDecisao = new ButtonDecisao(this.mouse);

        this.playerGraph = playerGraph; //= new PlayerGraph(this.player,windowWidth,450);
        this.playerEnemy = playerEnemy; //= new PlayerGraph(this.enemy, windowWidth, 850);

    }

    public void drawButtonDecisao(Graphics2D graphics2D){
        buttonDecisao.drawButton(graphics2D, enemy.isChamouTruco() && player.isDecisaoUndefined());
    }

    public void drawPlayers(Graphics2D graphics2D){
        if(first.size() < 2 || mid.size() < 2 || last.size() < 2){
            playerEnemy.drawPlayerCard(graphics2D);
            playerGraph.drawPlayerCard(graphics2D);
        }

        if(enemy.isWin1()){
            playerGraph.drawSingleCard(graphics2D, playerGraph.searchCard(first));
            playerEnemy.drawSingleCard(graphics2D, playerEnemy.searchCard(first));}
        else{
            playerEnemy.drawSingleCard(graphics2D, playerEnemy.searchCard(first));
            playerGraph.drawSingleCard(graphics2D, playerGraph.searchCard(first));
        }
        if(enemy.isWin2()){
            playerGraph.drawSingleCard(graphics2D, playerGraph.searchCard(mid));
            playerEnemy.drawSingleCard(graphics2D, playerEnemy.searchCard(mid));}
        else{
            playerEnemy.drawSingleCard(graphics2D, playerEnemy.searchCard(mid));
            playerGraph.drawSingleCard(graphics2D, playerGraph.searchCard(mid));
        }
        if(enemy.isWin3()){
            playerGraph.drawSingleCard(graphics2D, playerGraph.searchCard(last));
            playerEnemy.drawSingleCard(graphics2D, playerEnemy.searchCard(last));}
        else{
            playerEnemy.drawSingleCard(graphics2D, playerEnemy.searchCard(last));
            playerGraph.drawSingleCard(graphics2D, playerGraph.searchCard(last));
        }
    }

    public void reset(){
        this.enemy.resetPlayer();
        this.player.resetPlayer();
        this.first.clear();
        this.mid.clear();
        this.last.clear();
    }
    public void drawButtonTruco(Graphics2D graphics2D){
        this.buttonTruco.drawButton(graphics2D, this.enemy);
    }


    public void drawGanhador(Graphics2D graphics2D, boolean player1, boolean alguemGanhou){
        BufferedImage image;
        try {
            if(alguemGanhou){
                if(player1)
                    image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/player1Win.png")));
                else
                    image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/player2Win.png")));
            }else{
                image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/distribuindo.png")));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        graphics2D.drawImage(image,0,0,1200,700,null);
    }

    private boolean updateTrucoOrEnvido(){
        if(buttonTruco.buttonIsPressed(this.mouse)){
            player.setDecisao(buttonDecisao.isPressed());
            pontos.setSequenciaTruco();
            return true;
        }
        return false;
    }


    public void update(Mouse mouse, int card, boolean inverse){
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        while(player.isDecisaoUndefined() && enemy.isChamouTruco()){
            System.out.println(buttonDecisao.isPressed());
            player.setDecisao(buttonDecisao.isPressed());
        }
        if(enemy.isChamouTruco() && player.isDecisaoDenied()){
            enemy.setWinRodada();
        }

        updateJogada(mouse,card,inverse);
    }

    private void updateJogada(Mouse mouse, int card, boolean inverse){
        if(first.size() < 2){ // Primeira Jogada
            if(inverse){
                if(first.isEmpty()){
                    playerEnemy.update(true, card, this.first, this.mid, this.last);
                    enemy.setMao(true);
                } else{
                    playerGraph.update(mouse, this.first, this.mid, this.last);
                }
            }else {
                if(first.isEmpty()){
                    playerGraph.update(mouse, this.first, this.mid, this.last);
                    player.setMao(true);
                }
                else
                    playerEnemy.update(true, card, this.first, this.mid, this.last);
            }


        }else{
            whoWins(first,player,enemy,1, inverse);
            if(mid.size() < 2){
                if(enemy.isWin1()){
                    if(mid.isEmpty())
                        playerEnemy.update(true, card, this.first, this.mid, this.last);
                    else
                        playerGraph.update(mouse, this.first, this.mid, this.last);
                }else{
                    if(mid.isEmpty())
                        playerGraph.update(mouse, this.first, this.mid, this.last);
                    else
                        playerEnemy.update(true, card, this.first, this.mid, this.last);
                }
            }else{ // Terceira Jogada
                whoWins(mid,player,enemy,2,enemy.isWin1());
                if(last.size() < 2 && !player.isWinRodada() && !enemy.isWinRodada()) {
                    if (enemy.isWin2()) {
                        if (last.isEmpty())
                            playerEnemy.update(true, card, this.first, this.mid, this.last);
                        else
                            playerGraph.update(mouse, this.first, this.mid, this.last);
                    } else {
                        if (last.isEmpty())
                            playerGraph.update(mouse, this.first, this.mid, this.last);
                        else
                            playerEnemy.update(true, card, this.first, this.mid, this.last);
                    }
                }else{
                    if(!player.isWinRodada() && !enemy.isWinRodada())
                        whoWins(last,player,enemy,3, enemy.isWin2());
                }
            }

        }
    }


    private void whoWins(LinkedList<Cartas> cartas, Player player1, Player player2, int rodada, boolean reverso){
        int maior = Cartas.isCartaMaior(cartas.get(0), cartas.get(1));
        if(maior < 0){
            reverso = !reverso;
            maior = 1;
        }
        if(maior > 0){
                if (rodada == 1) {
                    if(reverso){
                        player2.setWin1();
                    }
                    else{
                        player1.setWin1();
                    }

                } else if (rodada == 2) {
                    if(reverso){
                        player2.setWin2();
                        if(player2.isWin1())
                            player2.setWinRodada();
                    }else{
                        player1.setWin2();
                        if(player1.isWin1())
                            player1.setWinRodada();
                    }
                } else if (rodada == 3) {
                    if(reverso){
                        player2.setWin3();
                        if(player2.isWin1() || player2.isWin2())
                            player2.setWinRodada();
                    }else{
                        player1.setWin3();
                        if(player1.isWin1() || player1.isWin2())
                            player1.setWinRodada();
                    }
                }
            }
        if(maior == 0){ // Empate
            if(rodada == 1){ // Empatou na primeira
                player1.setWin1();
                player2.setWin1();
            }
            else if(rodada == 2){
                if(player1.isWin1() && player2.isWin1()){ // Empatou na primeira e na segunda
                    player1.setWin2();
                    player2.setWin2();
                }
                else if(player1.isWin1()){ // Player 1 ganhou a primeira
                    player1.setWin2();
                    player1.setWinRodada();
                }
                else if(player2.isWin1()){ // Player 2 ganhou a primeira
                    player2.setWin2();
                    player2.setWinRodada();
                }
            }
            else if(rodada == 3){
                if(player1.isWin1() && player2.isWin1() && player1.isWin2() && player2.isWin2()){ // Empatou nas tres
                    if(player1.isMao()){ // Player 1 é mao
                        player1.setWin3();
                        player1.setWinRodada();
                    }else if(player2.isMao()){ // Player 2 é mao
                        player2.setWin3();
                        player2.setWinRodada();
                    }
                }else if(player1.isWin1()){ // Player 1 ganhou a primeira
                    player1.setWin3();
                    player1.setWinRodada();
                }else if(player2.isWin1()){ // Player 2 ganhou a primeira
                    player2.setWin3();
                    player2.setWinRodada();
                }

            }
        }
    }

}
