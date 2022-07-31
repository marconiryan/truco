package Main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
public class Mouse implements MouseListener{
    public boolean pressed = false, restart = false;
    public int posY, posX;

    @Override
    public void mouseClicked(MouseEvent e) {


    }

    @Override
    public void mousePressed(MouseEvent e) {
        int code = e.getButton();
        if(code == MouseEvent.BUTTON1){
            pressed = true;
            this.posX = e.getX();
            this.posY = e.getY();
        }
        if(code == MouseEvent.BUTTON2){
            restart = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int code = e.getButton();
        if(code == MouseEvent.BUTTON1){
            pressed = false;
            this.posX = e.getX();
            this.posY = e.getY();
        }
        if(code == MouseEvent.BUTTON2){
            restart = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
