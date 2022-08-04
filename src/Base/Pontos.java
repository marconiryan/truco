package Base;

public class Pontos extends Regras {
    private int  pointsRodada = 1;

    public Pontos(){

    }


    private void updatePoints(){
        if(isTruco()){
            this.pointsRodada = 2;
        }
    }


    public void update(){
        updatePoints();

    }

}
