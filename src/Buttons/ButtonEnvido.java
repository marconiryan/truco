package Buttons;

import Base.Player;
import Base.Pontos;
import Main.Mouse;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ButtonEnvido extends Buttons{
    private final BufferedImage envidoButton;
    private final int xEnvidoButton = 630;
    private final int yEnvidoButton = 650;
    private final int wEnvidoButton = 100;
    private final int hEnvidoButton = 50;
    private final Pontos pontos;


    public ButtonEnvido(Mouse mouse, Pontos pontos) {
        super(mouse);
        try {
            this.envidoButton = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/envido.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.pontos = pontos;
    }

    public void drawButton(Graphics2D graphics2D, Player enemy) {
        if(pontos.isEnvido() || enemy.isChamouEnvido() && !pontos.isEnvido()){
            drawButton(graphics2D,null, xEnvidoButton, yEnvidoButton, wEnvidoButton, hEnvidoButton);
        }
        else{
            drawButton(graphics2D, envidoButton, xEnvidoButton, yEnvidoButton, wEnvidoButton, hEnvidoButton);
        }
    }

    public boolean buttonIsPressed(Mouse mouse, Player player) {
        boolean rangeButton = buttonIsPressed(mouse, xEnvidoButton, xEnvidoButton + wEnvidoButton, yEnvidoButton, yEnvidoButton + hEnvidoButton);
        if(rangeButton){
            player.setChamouEnvido(true);
            return true;
        }
        return false;
    }
}

