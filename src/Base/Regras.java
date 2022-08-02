package Base;

import Graphics.PlayerLogic;

public class Regras  {
    private boolean truco, retruco, valequatro;
    private boolean envido, realenvido, faltaenvido;

    public boolean isTruco() {
        return truco;
    }

    public boolean isRetruco() {
        return retruco;
    }

    public boolean isValequatro() {
        return valequatro;
    }

    public void setTruco(boolean truco) {
        this.truco = truco;
    }

    public void setRetruco(boolean retruco) {
        this.retruco = retruco;
    }

    public void setValequatro(boolean valequatro) {
        this.valequatro = valequatro;
    }

    public void sequenciaTruco(){
        if(!isTruco())
            setTruco(true);

        else if(!isRetruco())
            setRetruco(true);

        else if(!isValequatro())
            setValequatro(true);
    }

    public boolean isEnvido() {
        return envido;
    }

    public void setEnvido(boolean envido) {
        this.envido = envido;
    }

    public boolean isRealenvido() {
        return realenvido;
    }

    public void setRealenvido(boolean realenvido) {
        this.realenvido = realenvido;
    }

    public boolean isFaltaenvido() {
        return faltaenvido;
    }

    public void setFaltaenvido(boolean faltaenvido) {
        this.faltaenvido = faltaenvido;
    }
}
