package Base;

public class Regras  {
    private int truco = 0;
    private boolean envido, realenvido, faltaenvido;

    public boolean isTruco() {
        return truco == 1;
    }

    public boolean isRetruco() {
        return truco == 2;
    }

    public boolean isValequatro() {
        return truco == 3;
    }

    public void setTruco() {
        this.truco = 1;
    }

    public void setRetruco() {
        this.truco = 2;
    }

    public void setValequatro() {
        this.truco = 3;
    }
    public void setSequenciaTruco(){
        if(this.truco < 3)
            this.truco += 1;
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
