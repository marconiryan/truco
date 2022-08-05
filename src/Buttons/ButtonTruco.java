package Buttons;

import Base.Player;
import Base.Pontos;
import Main.Mouse;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ButtonTruco extends Buttons{
    private final BufferedImage trucoButton;
    private final int xTrucoButton = 530;
    private final int yTrucoButton = 650;
    private final int wTrucoButton = 100;
    private final int hTrucoButton = 50;
    private final Pontos pontos;

    public ButtonTruco(Mouse mouse, Pontos pontos) {
        super(mouse);
        try {
            this.trucoButton= ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/truco.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.pontos = pontos;
    }

    public void drawButton(Graphics2D graphics2D, Player enemy) {
        if(pontos.isTruco() || enemy.isChamouTruco()){
            drawButton(graphics2D,null, xTrucoButton, yTrucoButton, wTrucoButton, hTrucoButton);
        }
        else{
            drawButton(graphics2D,trucoButton, xTrucoButton, yTrucoButton, wTrucoButton, hTrucoButton);
        }
    }

    public boolean buttonIsPressed(Mouse mouse, Player player) {
        boolean rangeButton = buttonIsPressed(mouse,xTrucoButton, xTrucoButton + wTrucoButton, yTrucoButton, yTrucoButton + hTrucoButton );
        if(rangeButton){
            player.setChamouTruco(true);
            return true;
        }
        return false;
    }
}

