package Main;
import Base.Baralho;
import Base.Player;
import Base.Pontos;
import PlayerUtil.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Game extends JPanel implements Runnable {
    private Thread gameThread;
    private final Graph playerGraph, playerEnemy;
    private final Player player, enemy;
    private final Baralho baralho;
    private final BufferedImage mesa;
    private final Mouse mouse = new Mouse();
    private final Logic logic;
    private final Pontos pontos;
    private boolean enemyComeca;
    private final int windowWidth = 1200, windowHeight = 700;
    final double FPS = 60;

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
        this.playerGraph = new Graph(this.player, windowWidth, 450);
        this.playerEnemy = new Graph(this.enemy, windowWidth, 850);
        this.logic = new Logic(player,enemy,mouse, pontos,playerGraph, playerEnemy);
        this.enemyComeca = false;

    }

    public void resetGame(){
        logic.reset();
        baralho.setBaralho();
        playerGraph.resetGraph(450);
        playerEnemy.resetGraph(850);
        baralho.distribuirCartasPlayer(this.player);
        baralho.distribuirCartasPlayer(this.enemy);
        enemyComeca = !enemyComeca;

    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();

    }

    public void paintComponent(Graphics graph) {
        super.paintComponent(graph);
        Graphics2D graphics2D = (Graphics2D) graph;
        graphics2D.drawImage(this.mesa,0,0,windowWidth,windowHeight,null);
        logic.drawDecisao(graphics2D);
        if(player.getPontosPartida() > 9 || enemy.getPontosPartida() > 9){
            logic.drawGanhador(graphics2D, player.getPontosPartida() > enemy.getPontosPartida(), true);
        }
        else if(player.isWinRodada() || enemy.isWinRodada()){
            logic.drawGanhador(graphics2D, player.getPontosPartida() > enemy.getPontosPartida(), false);
            resetGame();

        }else {
            this.logic.drawPlayers(graphics2D);
            this.logic.drawButtonTruco(graphics2D);
            this.logic.drawButtonDecisao(graphics2D);
            this.logic.drawButtonEnvido(graphics2D);

        }
        this.pontos.drawPoints(graphics2D);
        if(mouse.restart){
            resetGame();
        }

        graphics2D.dispose();
    }
    public void update() {
        Random random = new Random();
        int card = random.nextInt(1,4);
        this.logic.update(this.mouse, card, enemyComeca);
    }

    private boolean momentEnvido(){
        return  (!player.isDecisaoTrucoUndefined() && enemy.isChamouEnvido()) || (!enemy.isDecisaoTrucoUndefined() && player.isChamouEnvido());
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
