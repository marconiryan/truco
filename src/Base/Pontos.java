package Base;

import Main.Mouse;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Pontos extends Regras {
    private int  pointsRodada = 1;

    public Pontos(){

    }


    private void updatePoints(){
        if(isValequatro()){
            this.pointsRodada = 4;
        }
        else if(isRetruco()){
                this.pointsRodada = 3;
        }
        else if(isTruco()){
            this.pointsRodada = 2;
        }
    }


    public void update(){
        updatePoints();


    }

}
