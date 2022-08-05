package PlayerUtil;

import Base.*;
import Buttons.ButtonDecisao;
import Buttons.ButtonEnvido;
import Buttons.ButtonTruco;
import Main.Mouse;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Logic {

    private final Graph playerGraph, playerEnemy;
    private final Player player, enemy;
    private final LinkedList<Cartas> first, mid, last;
    private final ButtonTruco buttonTruco;
    private final ButtonDecisao buttonDecisao;
    private final ButtonEnvido buttonEnvido;
    private final Mouse mouse;
    private final Pontos pontos;

    public Logic(Player player, Player enemy, Mouse mouse, Pontos pontos, Graph playerGraph, Graph playerEnemy){
        this.player = player;
        this.enemy = enemy;
        this.first = new LinkedList<>();
        this.mid = new LinkedList<>();
        this.last = new LinkedList<>();
        this.mouse = mouse;
        this.pontos = pontos;
        this.buttonTruco = new ButtonTruco(mouse, pontos);
        this.buttonDecisao = new ButtonDecisao(this.mouse);
        this.buttonEnvido = new ButtonEnvido(this.mouse, pontos);
        this.playerGraph = playerGraph;
        this.playerEnemy = playerEnemy;

    }
    public void reset(){
        this.pontos.updatePoints();
        this.pontos.reset();
        this.enemy.resetPlayer();
        this.player.resetPlayer();
        this.first.clear();
        this.mid.clear();
        this.last.clear();
    }
    public void drawButtonDecisao(Graphics2D graphics2D){
        boolean called = (enemy.isChamouTruco() && player.isDecisaoTrucoUndefined()) ^ (enemy.isChamouEnvido() && player.isDecisaoTrucoUndefined() && !pontos.isEnvido());
        buttonDecisao.drawButton(graphics2D, called);
        graphics2D.setColor(Color.ORANGE);
        if(called){
            String frase = (enemy.isChamouTruco() && player.isDecisaoTrucoUndefined())? "Adversário chamou Truco": "Adversário chamou Envido";
            graphics2D.drawString(frase,550,10);
        }

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


    public void drawButtonTruco(Graphics2D graphics2D){
        this.buttonTruco.drawButton(graphics2D, this.enemy);
    }
    public void drawButtonEnvido(Graphics2D graphics2D){
        this.buttonEnvido.drawButton(graphics2D, this.enemy);
    }
    public void drawDecisao(Graphics2D graphics2D){
        if( (!player.isDecisaoTrucoUndefined()|| !enemy.isDecisaoTrucoUndefined())){
            String decisao;
            if(player.isDecisaoTrucoAccepted() || enemy.isDecisaoTrucoAccepted()){
                graphics2D.setColor(Color.GREEN);
                decisao = "Truco aceito";
            }else{
                graphics2D.setColor(Color.RED);
                decisao = "Truco recusado";
            }
            graphics2D.drawString(decisao, 600, 50);
        }

        if( (!player.isDecisaoEnvidoUndefined()|| !enemy.isDecisaoEnvidoUndefined())){
            String decisao;
            if(player.isDecisaoEnvidoAccepted() || enemy.isDecisaoEnvidoAccepted()){
                graphics2D.setColor(Color.GREEN);
                decisao = "Envido aceito";
            }else{
                graphics2D.setColor(Color.RED);
                decisao = "Envido recusado";
            }
            graphics2D.drawString(decisao, 600, 10);
        }


        if(player.isDecisaoEnvidoAccepted() && enemy.isChamouEnvido() || enemy.isDecisaoEnvidoAccepted() && player.isChamouEnvido()){
            String frase;

            if(player.getEnvido() > enemy.getEnvido()){
                String temp = " a " + enemy.getEnvido();
                frase = "Player ganhou o envido de " + player.getEnvido() + temp;
                graphics2D.setColor(Color.GREEN);
                graphics2D.drawString(frase, 535, 30);

            }
            else{
                String temp = " a " + player.getEnvido();
                frase = "Adversario ganhou o envido de " + enemy.getEnvido() + temp;
                graphics2D.setColor(Color.RED);
                graphics2D.drawString(frase, 520, 30);
            }


        }
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


    public void update(Mouse mouse, int card, boolean inverse){
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if(!pontos.isTruco()){
            updateTruco();
        }
        if(!pontos.isEnvido()){
            updateEnvido();
            pontos.setPointsEnvido();
        }
        if(!player.isWinRodada() || !enemy.isWinRodada())
            updateJogada(mouse,card,inverse);
    }

    private void updateTruco(){
        while(player.isDecisaoTrucoUndefined() && enemy.isChamouTruco()){
            System.out.print("");
            player.setDecisaoTruco(buttonDecisao.isPressed());
        }
        if(enemy.isChamouTruco() && player.isDecisaoTrucoDenied()){
            enemy.setWinRodada();

        }else if (enemy.isChamouTruco() && player.isDecisaoTrucoAccepted()){
            pontos.setSequenciaTruco();
        }

        if(buttonTruco.buttonIsPressed(this.mouse, player)){
            while(enemy.isDecisaoTrucoUndefined()){
                Random random = new Random();
                enemy.setDecisaoTruco(random.nextInt(-1,2));
            }
        }
        if(enemy.isDecisaoTrucoDenied() && player.isChamouTruco()){
            this.player.setWinRodada();
        }else if(enemy.isDecisaoTrucoAccepted() && player.isChamouTruco()){
            pontos.setSequenciaTruco();
        }
    }

    private void updateEnvido(){
        while(player.isDecisaoEnvidoUndefined() && enemy.isChamouEnvido() && !pontos.isEnvido()){
            System.out.print("");
            player.setDecisaoEnvido(buttonDecisao.isPressed());

        }
        if(player.isDecisaoEnvidoDenied() && enemy.isChamouEnvido()){
            pontos.setEnvido(true);
        }else if (player.isDecisaoEnvidoAccepted() && enemy.isChamouEnvido()){
            pontos.setEnvido(true);
        }
        if(buttonEnvido.buttonIsPressed(mouse, player)){
            while(enemy.isDecisaoEnvidoUndefined()){
                Random random = new Random();
                enemy.setDecisaoEnvido(random.nextInt(-1,2));
            }
            pontos.setEnvido(true);
        }
        if(enemy.isDecisaoEnvidoDenied() && player.isChamouEnvido()){
            pontos.setEnvido(true);
        }else if(enemy.isDecisaoEnvidoAccepted() && player.isChamouEnvido()){
            pontos.setEnvido(true);
        }
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
