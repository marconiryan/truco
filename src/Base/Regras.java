package Base;

public class Regras  {
    private int truco = 0;
    private boolean envido;

    public boolean isTruco() {
        return truco == 1;
    }

    public void setSequenciaTruco(){
        if(this.truco < 2)
            this.truco += 1;
    }

    public boolean isEnvido() {
        return envido;
    }

    public void setEnvido(boolean envido) {
        this.envido = envido;
    }

}
