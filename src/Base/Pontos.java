package Base;

import Main.Mouse;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Pontos extends Regras {
    private int  pointsRodada = 1;
    private final BufferedImage trucoButton, retrucoButton, valequatroButton, nadaButton;
    private final int xTrucoButton = 200;
    private final int yTrucoButton = 650;
    private final int wTrucoButton = 100;
    private final int hTrucoButton = 50;


    private Player player, enemy;
    public Pontos(Player player, Player enemy){
        this.player = player;
        this.enemy = enemy;
        try {
            this.trucoButton= ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/truco.png")));
            this.retrucoButton= ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/retruco.png")));
            this.valequatroButton= ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/valequatro.png")));
            this.nadaButton= ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/nada.png")));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    public void drawTrucoButton(Graphics2D graphics2D){

        if(isTruco()){
            graphics2D.drawImage(retrucoButton, xTrucoButton, yTrucoButton, wTrucoButton,hTrucoButton,null);

       }
       else if(isRetruco()){
            graphics2D.drawImage(valequatroButton, xTrucoButton, yTrucoButton, wTrucoButton,hTrucoButton,null);
       }
       else if(isValequatro()){
            graphics2D.drawImage(nadaButton, xTrucoButton, yTrucoButton, wTrucoButton,hTrucoButton,null);
        }
       else{
            graphics2D.drawImage(trucoButton, xTrucoButton, yTrucoButton, wTrucoButton,hTrucoButton,null);
       }

    }

    private boolean buttonIsPressed(Mouse mouse, int rangeXmin, int rangeXmax, int rangeYmin, int rangeYmax){
        return mouse.pressed && mouse.posY >= rangeYmin && mouse.posY <= rangeYmax && mouse.posX >= rangeXmin && mouse.posX <= rangeXmax;
    }

    public void update(Mouse mouse){
        updatePoints();

        if(buttonIsPressed(mouse,xTrucoButton, xTrucoButton + wTrucoButton, yTrucoButton, yTrucoButton + hTrucoButton)){ // ToDo
            System.out.println("BOTAO FOI PRESSIONADO");
            setSequenciaTruco();
        }

    }

}
