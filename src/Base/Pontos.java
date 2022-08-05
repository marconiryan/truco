package Base;

import java.awt.*;

public class Pontos{
    private boolean truco = false;
    private boolean envido;
    private final Player player, enemy;

    public Pontos(Player player, Player enemy){
        this.player = player;
        this.enemy = enemy;
    }


    public boolean isTruco() {
        return truco;
    }

    public void setSequenciaTruco(){
        if(!this.truco)
            this.truco = true;
    }
    public void reset(){
        this.envido = false;
        this.truco = false;
    }

    public boolean isEnvido() {
        return envido;
    }

    public void setEnvido(boolean envido) {
        this.envido = envido;
    }



    public void drawPoints(Graphics2D graphics2D){
        graphics2D.setColor(Color.CYAN);
        graphics2D.drawString("Player: "+ player.getPontosPartida(),10,20);
        graphics2D.drawString("Adversário: "+ enemy.getPontosPartida(),10,50);


    }

    public void updatePoints(){
        setPointsTruco();
        setPointsEnvido();

    }


    private void setPointsTruco(){
        if(isTruco()){
            if(player.isWinRodada()){
                player.updatePontosPartida(2);
            }else{
                enemy.updatePontosPartida(2);
            }
        }else if(player.isWinRodada()){
            player.updatePontosPartida(1);
        }else if(enemy.isWinRodada()){
            enemy.updatePontosPartida(1);
        }


    }

    private void setPointsEnvido(){
        //updatePoints();
        if(isEnvido()){
            if(player.getEnvido() > enemy.getEnvido()){
                player.updatePontosPartida(2);
            }
            else if(player.getEnvido() < enemy.getEnvido()){
                enemy.updatePontosPartida(2);
            }
            else{
                if(player.isMao()){
                    player.updatePontosPartida(2);
                }else{
                    enemy.updatePontosPartida(2);
                }
            }
        }else if(enemy.isChamouEnvido()){
            enemy.updatePontosPartida(1);
        }else if(player.isChamouEnvido()){
            player.updatePontosPartida(1);
        }
    }

}
