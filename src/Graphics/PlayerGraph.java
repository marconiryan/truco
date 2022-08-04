package Graphics;
import Base.Cartas;
import Base.Player;
import Main.Mouse;


import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Objects;

public class PlayerGraph {

    Player basePlayer;
    int posCardX, posCardY, width, height;
    int yCard1, yCard2, yCard3;
    int xCard1, xCard2, xCard3;
    BufferedImage card1, card2, card3;
    private final int  centerWScreen = 400, centerHScreen = 233, offset = 200;


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

    public int searchCard(LinkedList<Cartas> cartas){
        for(Cartas c: cartas){
            if(c == basePlayer.getCartasPlayer().get(0))
                return 1;
            else if(c == basePlayer.getCartasPlayer().get(1))
                return 2;
            else if(c == basePlayer.getCartasPlayer().get(2))
                return 3;
        }
        return 0;
    }
    public void drawSingleCard(Graphics2D graphics2D, int card){
        if(card == 1)
            drawCard(graphics2D, card1,xCard1,yCard1,width,height);
        else if(card == 2)
            drawCard(graphics2D, card2,xCard2,yCard2,width,height);
        else if(card == 3)
            drawCard(graphics2D, card3,xCard3,yCard3,width,height);
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
    public void resetGraph(int height){
        xCard1 = centerWScreen;
        xCard2 = centerWScreen + offset;
        xCard3 = centerWScreen + offset * 2;
        yCard1 = yCard2 = yCard3 = height;
    }

    public void update(boolean jogou, int card, LinkedList<Cartas> first, LinkedList<Cartas> mid, LinkedList<Cartas> last){
        int newCenterHScreen = centerHScreen - 75;
        int newCenterWScreen = centerWScreen - 20;

        if(jogou){
            if(card == 1 && !(yCard1 == newCenterHScreen)){
                this.yCard1 = newCenterHScreen;
                if(yCard2 == newCenterHScreen || yCard3 == newCenterHScreen){
                    if(yCard3 == yCard2){
                        xCard1 = newCenterWScreen + 2 * offset;
                        last.add(basePlayer.getCartasPlayer().get(0));
                    }
                    else{
                        xCard1 = newCenterWScreen + offset;
                        mid.add(basePlayer.getCartasPlayer().get(0));
                    }
                }
                else{
                    xCard1 = newCenterWScreen;
                    first.add(basePlayer.getCartasPlayer().get(0));
                }
            }
            else if (card == 2 && !(yCard2 == newCenterHScreen)){
                yCard2 = newCenterHScreen;
                if(yCard1 == newCenterHScreen || yCard3 == newCenterHScreen){
                    if(yCard3 == yCard1){
                        xCard2 = newCenterWScreen + 2 * offset;
                        last.add(basePlayer.getCartasPlayer().get(1));
                    }
                    else{
                        xCard2 = newCenterWScreen + offset;
                        mid.add(basePlayer.getCartasPlayer().get(1));
                    }
                }
                else{
                    xCard2 = newCenterWScreen;
                    first.add(basePlayer.getCartasPlayer().get(1));
                }
            }
            else if(card == 3 && !(yCard3 == newCenterHScreen)){
                yCard3 = newCenterHScreen;
                if(yCard1 == newCenterHScreen || yCard2 == newCenterHScreen){
                    if(yCard1 == yCard2){
                        xCard3 = newCenterWScreen + 2 * offset;
                        last.add(basePlayer.getCartasPlayer().get(2));
                    }
                    else{
                        xCard3 = newCenterWScreen + offset;
                        mid.add(basePlayer.getCartasPlayer().get(2));
                    }
                }
                else{
                    xCard3 = newCenterWScreen;
                    first.add(basePlayer.getCartasPlayer().get(2));
                }
            }
        }
    }

    public void update(Mouse mouse,LinkedList<Cartas> first, LinkedList<Cartas> mid, LinkedList<Cartas> last){
        boolean rangeCard1 = mouse.posX >= xCard1 && mouse.posX <= xCard1 + width && mouse.posY >= yCard1 && mouse.posY <= yCard1 + height;
        boolean rangeCard2 = mouse.posX >= xCard2 && mouse.posX <= xCard2+ width && mouse.posY >= yCard2 && mouse.posY <= yCard2 + height;
        boolean rangeCard3 = mouse.posX >= xCard3 && mouse.posX <= xCard3+ width && mouse.posY >= yCard3 && mouse.posY <= yCard3 + height;


        if(mouse.pressed){
            if(rangeCard1 && !(yCard1 == centerHScreen)){

                this.yCard1 = centerHScreen;
                if(yCard2 == centerHScreen || yCard3 == centerHScreen){
                    if(yCard3 == yCard2){
                        xCard1 = centerWScreen + 2 * offset;
                        last.add(basePlayer.getCartasPlayer().get(0));
                    }
                    else{
                        xCard1 = centerWScreen + offset;
                        mid.add(basePlayer.getCartasPlayer().get(0));
                    }
                }
                else{
                    xCard1 = centerWScreen;
                    first.add(basePlayer.getCartasPlayer().get(0));
                }
            }
            else if (rangeCard2 && !(yCard2 == centerHScreen)){
                yCard2 = centerHScreen;
                if(yCard1 == centerHScreen || yCard3 == centerHScreen){
                    if(yCard3 == yCard1){
                        xCard2 = centerWScreen + 2 * offset;
                        last.add(basePlayer.getCartasPlayer().get(1));
                    }
                    else{
                        xCard2 = centerWScreen + offset;
                        mid.add(basePlayer.getCartasPlayer().get(1));
                    }
                }
                else{
                    xCard2 = centerWScreen;
                    first.add(basePlayer.getCartasPlayer().get(1));
                }
            }
            else if(rangeCard3 && !(yCard3 == centerHScreen)){
                yCard3 = centerHScreen;
                if(yCard1 == centerHScreen || yCard2 == centerHScreen){
                    if(yCard1 == yCard2){
                        xCard3 = centerWScreen + 2 * offset;
                        last.add(basePlayer.getCartasPlayer().get(2));
                    }
                    else{
                        xCard3 = centerWScreen + offset;
                        mid.add(basePlayer.getCartasPlayer().get(2));
                    }
                }
                else{
                    xCard3 = centerWScreen;
                    first.add(basePlayer.getCartasPlayer().get(2));
                }
            }
        }
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
