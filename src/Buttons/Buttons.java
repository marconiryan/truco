package Buttons;

import Main.Mouse;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Buttons {
    protected Mouse mouse;

    public Buttons(Mouse mouse){
        this.mouse = mouse;
    }

    public boolean buttonIsPressed(Mouse mouse, int rangeXmin, int rangeXmax, int rangeYmin, int rangeYmax){
        return mouse.pressed && mouse.posY >= rangeYmin && mouse.posY <= rangeYmax && mouse.posX >= rangeXmin && mouse.posX <= rangeXmax;
    }

    public void drawButton(Graphics2D graphics2D, BufferedImage image, int x, int y, int w, int h){
        graphics2D.drawImage(image,x,y,w,h,null);
    }

}
