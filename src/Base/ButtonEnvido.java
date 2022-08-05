package Base;

import Main.Mouse;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ButtonEnvido extends Buttons{
    private final BufferedImage envidoButton;
    private BufferedImage nadaButton;
    private final int xEnvidoButton = 250;
    private final int yEnvidoButton = 650;
    private final int wEnvidoButton = 100;
    private final int hEnvidoButton = 50;
    Pontos pontos;


    public ButtonEnvido(Mouse mouse, Pontos pontos) {
        super(mouse);
        try {
            this.envidoButton = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/envido.png")));
            //this.nadaButton= ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/nada.png")));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.pontos = pontos;
    }

    public void drawButton(Graphics2D graphics2D, Player enemy) {
        if(pontos.isEnvido() || enemy.isChamouEnvido()){
            drawButton(graphics2D,nadaButton, xEnvidoButton, yEnvidoButton, wEnvidoButton, hEnvidoButton);
        }
        else{
            drawButton(graphics2D, envidoButton, xEnvidoButton, yEnvidoButton, wEnvidoButton, hEnvidoButton);
        }
    }

    public boolean buttonIsPressed(Mouse mouse) {
        boolean rangeButton = buttonIsPressed(mouse, xEnvidoButton, xEnvidoButton + wEnvidoButton, yEnvidoButton, yEnvidoButton + hEnvidoButton);
        if(rangeButton){
            pontos.setEnvido(true);
            return true;
        }
        return false;
    }
}

