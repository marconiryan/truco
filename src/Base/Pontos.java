package Base;

import Main.Mouse;

public class Pontos extends Regras {
    private int  pointsRodada = 1;

    private Player player, enemy;
    public Pontos(Player player, Player enemy){
        this.player = player;
        this.enemy = enemy;

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

    private boolean buttonIsPressed(Mouse mouse, boolean rangeButton){
        return mouse.pressed && rangeButton;
    }

    public void update(Mouse mouse){
        updatePoints();
        if(buttonIsPressed(mouse, false)){ // ToDo
            sequenciaTruco();
        }

    }

}
