package Base;

import Main.Mouse;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ButtonTruco extends Buttons{
    private final BufferedImage trucoButton, retrucoButton, valequatroButton, nadaButton;
    private final int xTrucoButton = 200;
    private final int yTrucoButton = 650;
    private final int wTrucoButton = 100;
    private final int hTrucoButton = 50;
    Pontos pontos;


    public ButtonTruco(Mouse mouse, Pontos pontos) {
        super(mouse);
        try {
            this.trucoButton= ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/truco.png")));
            this.retrucoButton= ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/retruco.png")));
            this.valequatroButton= ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/valequatro.png")));
            this.nadaButton= ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/nada.png")));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.pontos = pontos;
    }

    public void drawButton(Graphics2D graphics2D) {
        if(pontos.isTruco()){
            drawButton(graphics2D,retrucoButton, xTrucoButton, yTrucoButton, wTrucoButton, hTrucoButton);
        }
        else if(pontos.isRetruco()){
            drawButton(graphics2D,valequatroButton, xTrucoButton, yTrucoButton, wTrucoButton, hTrucoButton);
        }
        else if(pontos.isValequatro()){
            drawButton(graphics2D,nadaButton, xTrucoButton, yTrucoButton, wTrucoButton, hTrucoButton);
        }
        else{
            drawButton(graphics2D,trucoButton, xTrucoButton, yTrucoButton, wTrucoButton, hTrucoButton);
        }
    }

    public boolean buttonIsPressed(Mouse mouse) {
        return buttonIsPressed(mouse,xTrucoButton, xTrucoButton + wTrucoButton, yTrucoButton, yTrucoButton + hTrucoButton );
    }
}

