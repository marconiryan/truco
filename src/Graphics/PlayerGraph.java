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
    int xCard1, xCard2, xCard3;
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
        this.xCard1 = centerWScreen;
        this.xCard2 = centerWScreen + offset;
        this.xCard3 = centerWScreen + offset * 2;
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
        drawCard(graphics2D, card1,xCard1,yCard1,width,height);
        drawCard(graphics2D, card2,xCard2,yCard2,width,height);
        drawCard(graphics2D, card3,xCard3,yCard3,width,height);

    }

    public void update(boolean inverso){
        if(inverso){
            this.posCardY = this.centerHScreen  -100 ;
            this.posCardX = this.centerWScreen -5;
        }
    }
    public void update(Mouse mouse){
        boolean rangeCard1 = mouse.posX >= xCard1 && mouse.posX <= xCard1 + width && mouse.posY >= yCard1 && mouse.posY <= yCard1 + height;
        boolean rangeCard2 = mouse.posX >= xCard2 && mouse.posX <= xCard2+ width && mouse.posY >= yCard2 && mouse.posY <= yCard2 + height;
        boolean rangeCard3 = mouse.posX >= xCard3 && mouse.posX <= xCard3+ width && mouse.posY >= yCard3 && mouse.posY <= yCard3 + height;


        if(mouse.pressed){
            if(rangeCard1){
                this.yCard1 = centerHScreen;
                if(yCard2 == centerHScreen || yCard3 == centerHScreen){
                    if(yCard3 == yCard2)
                        xCard1 = centerWScreen + 2 * offset;
                    else
                        xCard1 = centerWScreen + offset;
                }
                else{
                    xCard1 = centerWScreen;
                }
            }
            else if (rangeCard2){
                yCard2 = centerHScreen;
                if(yCard1 == centerHScreen || yCard3 == centerHScreen){
                    if(yCard3 == yCard2)
                        xCard2 = centerWScreen + 2 * offset;
                    else
                        xCard2 = centerWScreen + offset;
                }
                else{
                    xCard2 = centerWScreen;
                }
            }
            else if(rangeCard3){
                yCard3 = centerHScreen;
                if(yCard1 == centerHScreen || yCard2 == centerHScreen){
                    if(yCard1 == yCard2)
                        xCard3 = centerWScreen + 2 * offset;
                    else
                        xCard3 = centerWScreen + offset;
                }
                else{
                    xCard3 = centerWScreen;
                }
            }
        }
            //this.posCardY = this.centerHScreen ;
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
