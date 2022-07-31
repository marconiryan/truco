package Graphics;
import Base.Cartas;
import Base.Player;
import Main.Mouse;


import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class PlayerGraph {

    Player basePlayer;
    int posCardX, posCardY, width, height;
    int yCard1, yCard2, yCard3;
    BufferedImage card1, card2, card3;
    private final int  centerWScreen = 400, centerHScreen = 233, offset = 150;



    public PlayerGraph(Player player, int posCardX, int posCardY) {
        this.basePlayer = player;
        this.posCardX = posCardX;
        this.posCardY = posCardY;
        this.yCard1 = posCardY;
        this.yCard2 = posCardY;
        this.yCard3 = posCardY;
        this.width = 120;
        this.height = 180;
    }
    protected int getCentroY(){
        return this.posCardY / 3;
    }
    protected int getCentroX(){
        return this.posCardX / 3;
    }

    public void drawPlayerCard(Graphics2D graphics2D){
        try {
            this.card1= ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(Cartas.getNameSprite(basePlayer.getCartasPlayer().get(0)))));
            this.card2= ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(Cartas.getNameSprite(basePlayer.getCartasPlayer().get(1)))));
            this.card3= ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(Cartas.getNameSprite(basePlayer.getCartasPlayer().get(2)))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //
        drawCard(graphics2D, card1,this.posCardX,yCard1,width,height);
        drawCard(graphics2D, card2,this.posCardX+offset,yCard2,width,height);
        drawCard(graphics2D, card3,this.posCardX+offset*2,yCard3,width,height);

    }

    public void update(boolean inverso){
        if(inverso){
            this.posCardY = this.centerHScreen  -100 ;
            this.posCardX = this.centerWScreen -5;
        }
    }
    public void update(Mouse mouse){
        boolean rangeCard1 = mouse.posX >= this.posCardX && mouse.posX <= this.posCardX + width && mouse.posY >= yCard1 && mouse.posY <= yCard1 + height;
        boolean rangeCard2 = mouse.posX >= this.posCardX + offset && mouse.posX <= this.posCardX + width + offset && mouse.posY >= yCard2 && mouse.posY <= yCard2 + height;
        boolean rangeCard3 = mouse.posX >= this.posCardX + offset*2 && mouse.posX <= this.posCardX + width + offset*2 && mouse.posY >= yCard3 && mouse.posY <= yCard3 + height;

        if(mouse.pressed){
            if(rangeCard1)
                yCard1 = centerHScreen;
            else if (rangeCard2)
                yCard2 = centerHScreen;
            else if(rangeCard3)
                yCard3 = centerHScreen;
        }
            //this.posCardY = this.centerHScreen ;
        this.posCardX = this.centerWScreen ;
    }

    private void drawCard(Graphics2D graphics2D, BufferedImage card,int cardX, int cardY,int w, int h){

        graphics2D.setColor(Color.white);
        graphics2D.fillRoundRect(cardX-6,cardY-6,w+12,h+12,15,15);
        graphics2D.setColor(Color.black);
        graphics2D.drawImage(card,cardX,cardY,w,h,null);
        graphics2D.drawRect(cardX,cardY,w,h);
        graphics2D.drawRoundRect(cardX-6,cardY-6,w+12,h+12,15,15);


    }

}
