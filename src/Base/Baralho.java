package Base;

import java.util.LinkedList;
import java.util.Random;
public class Baralho{
    private final LinkedList<Cartas> baralho;

    public Baralho(){
        this.baralho = new LinkedList<>();
        setBaralho();
    }
    public LinkedList<Cartas> getBaralho() {
        return baralho;
    }
    private void embaralhar(LinkedList<Cartas> baralho){
        Random random = new Random();
        for(Cartas carta: baralho){
            int numero_aleatorio =  random.nextInt(baralho.size());
            int index_atual = baralho.indexOf(carta);
            Cartas temp = baralho.get(numero_aleatorio);
            baralho.set(numero_aleatorio,carta);
            baralho.set(index_atual,temp);
        }
    }
    public void setBaralho() {
        this.baralho.clear();
        for(int naipe = 1; naipe < 5; naipe++){
            for(int numero = 1; numero < 13; numero++){
                int peso = setPesoTruco(naipe,numero);
                if(peso != 0)
                    this.baralho.add(new Cartas(naipe, numero, peso));
            }
        }
            embaralhar(this.baralho);
    }


    public void distribuirCartasPlayer(Player player){
        LinkedList<Cartas> cartasPlayer = new LinkedList<>();
        for(int i = 0; i < 3; i++ ){
            cartasPlayer.add(baralho.getFirst());
            baralho.removeFirst();
            embaralhar(baralho);
        }
        player.setCartasPlayer(cartasPlayer);

    }

    public static LinkedList<Cartas> getMesmoTipo(LinkedList<Cartas> cartas){
        LinkedList<Cartas> mesmoTipo = new LinkedList<>();
        for(Cartas carta1: cartas){
            for(Cartas cartas2 : cartas){
                if(!carta1.equals(cartas2) && carta1.naipe() == cartas2.naipe() && !mesmoTipo.contains(carta1)){
                    mesmoTipo.add(carta1);
                }
            }
        }
        return mesmoTipo;
    }


    private int setPesoTruco(int naipe, int numero){
        boolean espadao = naipe == 1 && numero == 1;
        boolean bastiao = naipe == 2 && numero == 1;
        boolean sete_espada = naipe == 1 && numero == 7;
        boolean sete_ouro = naipe == 3 && numero == 7;
        switch (numero){
            case 1 -> {
                if (espadao)
                    return 14;

                else if(bastiao)
                    return 13;

                return 8;

            }
            case 2 -> {
                return 9;
            }

            case 3 -> {
                return 10;
            }

            case 4 -> {
                return 1;
            }

            case 5 -> {
                return 2;
            }

            case 6 -> {
                return 3;
            }

            case 7 -> {
                if(sete_espada)
                    return 12;
                else if (sete_ouro)
                    return 11;
                return 4;
            }
            case 10 -> {
                return 5;
            }
            case 11 -> {
                return 6;
            }
            case 12 -> {
                return 7;
            }
        }

        return 0;
    }


}