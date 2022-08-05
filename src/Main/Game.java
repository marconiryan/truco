package Main;
import Base.Baralho;
import Base.Player;
import Base.Pontos;
import Graphics.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Game extends JPanel implements Runnable {
    Thread gameThread;
    PlayerGraph playerGraph, playerEnemy;
    Player player, enemy;
    Baralho baralho;
    BufferedImage mesa;
    Mouse mouse = new Mouse();
    PlayerLogic playerLogic;
    Pontos pontos;

    final int windowWidth = 1200, windowHeight = 700;
    final double FPS = 622220;

    // Load mesa
    {
        try {
            this.mesa = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/mesa.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Game() {
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setPreferredSize(new Dimension(windowWidth,windowHeight));
        this.setFocusable(true);
        this.addMouseListener(this.mouse);
        this.baralho = new Baralho();
        this.player = new Player();
        this.enemy = new Player();
        this.baralho.distribuirCartasPlayer(this.player);
        this.baralho.distribuirCartasPlayer(this.enemy);
        this.pontos = new Pontos(player,enemy);
        this.playerGraph = new PlayerGraph(this.player, windowWidth, 450);
        this.playerEnemy = new PlayerGraph(this.enemy, windowWidth, 850);
        this.playerLogic = new PlayerLogic(player,enemy,windowWidth, mouse, pontos,playerGraph, playerEnemy);
        this.enemy.setChamouEnvido(true);

    }

    public void resetGame(){
        playerLogic.reset();
        baralho.setBaralho();
        playerGraph.resetGraph(450);
        playerEnemy.resetGraph(850);
        baralho.distribuirCartasPlayer(this.player);
        baralho.distribuirCartasPlayer(this.enemy);

    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();

    }

    public void paintComponent(Graphics graph) {
        super.paintComponent(graph);
        Graphics2D graphics2D = (Graphics2D) graph;
        graphics2D.drawImage(this.mesa,0,0,windowWidth,windowHeight,null);
        playerLogic.drawDecisao(graphics2D);
        if(player.isWinRodada() || enemy.isWinRodada()){
            playerLogic.drawGanhador(graphics2D, player.isWinRodada(), false);
            resetGame();

        }else {
            this.playerLogic.drawPlayers(graphics2D);
            this.playerLogic.drawButtonTruco(graphics2D);
            this.playerLogic.drawButtonDecisao(graphics2D);
            this.playerLogic.drawButtonEnvido(graphics2D);

        }
        this.pontos.drawPoints(graphics2D);
        if(mouse.restart){
            resetGame();
        }

        graphics2D.dispose();
    }
    public void update() {
        Random random = new Random();
        int i = random.nextInt(1,4);
        this.playerLogic.update(this.mouse, i,false);
        System.out.println(enemy.isChamouTruco());

    }

    private boolean momentEnvido(){
        return  (!player.isDecisaoUndefined() && enemy.isChamouEnvido()) || (!enemy.isDecisaoUndefined() && player.isChamouEnvido());
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();

                if((player.isWinRodada() || enemy.isWinRodada()) || momentEnvido()){
                    try {
                        TimeUnit.MILLISECONDS.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                delta--;
            }
        }
    }
}
