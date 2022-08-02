package Graphics;

import Base.Cartas;
import Base.Player;
import Main.Mouse;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;

public class PlayerLogic {

    PlayerGraph playerGraph;
    PlayerGraph playerEnemy;
    Player player;
    Player enemy;
    LinkedList<Cartas> first, mid, last;

    public PlayerLogic(Player player, Player enemy, int windowWidth){
        this.player = player;
        this.enemy = enemy;
        this.first = new LinkedList<>();
        this.mid = new LinkedList<>();
        this.last = new LinkedList<>();
        playerGraph = new PlayerGraph(this.player,windowWidth,450);
        playerEnemy = new PlayerGraph(this.enemy, windowWidth, 850);

    }
    public void drawPlayers(Graphics2D graphics2D){
        playerGraph.drawPlayerCard(graphics2D);
        playerEnemy.drawPlayerCard(graphics2D);
    }

    public void drawGanhador(Graphics2D graphics2D, boolean p1){
        BufferedImage image;
        try {
            if(p1)
                image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/player1Win.png")));
            else
                image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/player2Win.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        graphics2D.drawImage(image,0,0,1200,700,null);
    }
    public void update(Mouse mouse, int card, boolean inverse){
        if ((enemy.isWin1())) {
            System.out.println("Inimigo Ganhou 1");
        } else {
            System.out.println("Inimigo Perdeu 1");
        }

        if ((enemy.isWin2())) {
            System.out.println("Inimigo Ganhou 2");
        } else {
            System.out.println("Inimigo Perdeu 2");
        }
        if ((enemy.isWin3())) {
            System.out.println("Inimigo Ganhou 3");
        } else {
            System.out.println("Inimigo Perdeu 3");
        }

        System.out.println("P1:" + player.isWinRodada() +"|"+"P2:"+enemy.isWinRodada());
        System.out.println(card);

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
            whoWin(first,player,enemy,1, inverse);
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
                whoWin(mid,player,enemy,2,enemy.isWin1());
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
                        whoWin(last,player,enemy,3, enemy.isWin2());
                }
            }

        }
    }


    private void whoWin(LinkedList<Cartas> cartas, Player player1, Player player2, int rodada, boolean reverso){
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
