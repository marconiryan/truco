package Main;
import Base.Baralho;
import Base.Cartas;
import Base.Player;
import Base.Pontos;
import Graphics.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;

public class Game extends JPanel implements Runnable {
    PlayerGraph playerGraph, playerEnemy;
    Player player, enemy;
    Baralho baralho;
    BufferedImage mesa;
    Mouse mouse = new Mouse();

    Sound sound = new Sound();
    PlayerLogic playerLogic;
    Pontos pontos;

    LinkedList<Cartas> first, mid, last;

    Thread gameThread;


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
        this.first = new LinkedList<>();
        this.mid = new LinkedList<>();
        this.last = new LinkedList<>();
        this.pontos = new Pontos();
        this.playerLogic = new PlayerLogic(player,enemy,windowWidth, mouse, pontos);

        this.playerGraph = new PlayerGraph(this.player, windowWidth, 450);
        this.playerEnemy = new PlayerGraph(this.enemy, windowWidth, 850);

        //this.pontos.setTruco(true);


    }

    public void startGameThread() {
        gameThread = new Thread(this);
        playsoundLoop(0);
        gameThread.start();

    }


    public void paintComponent(Graphics graph) {
        super.paintComponent(graph);
        Graphics2D graphics2D = (Graphics2D) graph;
        graphics2D.drawImage(this.mesa,0,0,windowWidth,windowHeight,null);
        this.playerLogic.drawPlayers(graphics2D,enemy);
        this.playerLogic.drawButtonTruco(graphics2D);
        //this.pontos.drawTrucoButton(graphics2D);
        if(player.isWinRodada() || enemy.isWinRodada())
            playerLogic.drawGanhador(graphics2D, player.isWinRodada());

        graphics2D.dispose();


    }

    public void playsoundLoop(int i){

        sound.Playfile(i);
        sound.play();
        sound.loop();

    }

  /*  public void playSound(int i) {
        sound.Playfile(i);
        sound.play();
    } To-Do
    // Fazer efeitos ( De Vitoria,  e cartas)*/

    public void update() {
        Random random = new Random();
        int i = random.nextInt(1,4);
        //pontos.update(mouse,false,this.player,this.enemy);


        if(!player.isWinRodada() && !enemy.isWinRodada())
            this.playerLogic.updateJogada(this.mouse, i,false);
        else{
            if(player.isWinRodada())
                System.out.println("P1 Ganhou");
            else
                System.out.println("P2 Ganhou");
        }

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
                delta--;
            }
        }
    }
}
