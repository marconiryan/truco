package Buttons;

import Main.Mouse;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ButtonDecisao extends  Buttons{
    private final BufferedImage accepted, denied;
    private final int buttonX = 500, buttonY = 20, buttonW = 100, buttonH = 50, offset = 150;
    Mouse mouse;

    public ButtonDecisao(Mouse mouse) {
        super(mouse);
        try {
            this.accepted = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/aceitar.png")));
            this.denied = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/recusar.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.mouse = mouse;
    }


    public void drawButton(Graphics2D graphics2D, boolean called) {
        if(called){
            drawButton(graphics2D, accepted, buttonX, buttonY, buttonW,buttonH);
            drawButton(graphics2D, denied, buttonX + offset, buttonY, buttonW,buttonH);
        }
    }
    public int isPressed(){
        boolean rangeAccepted = mouse.pressed && mouse.posX >= buttonX && mouse.posX <= buttonX + buttonW && mouse.posY >= buttonY && mouse.posY <= buttonY + buttonH;
        boolean rangeDenied = mouse.pressed && mouse.posX >= buttonX + offset && mouse.posX <= buttonX + offset + buttonW && mouse.posY >= buttonY && mouse.posY <= buttonY + buttonH;
        if(rangeAccepted)
            return 1;
        else if(rangeDenied)
            return -1;
        return 0;
    }
    
}
