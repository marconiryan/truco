package Base;

public record Cartas(int naipe, int numero, int peso) {
    public Cartas(int naipe, int numero, int peso) {
        this.naipe = setNaipe(naipe);
        this.numero = setNumero(numero);
        this.peso = peso;
    }

    private int setNaipe(int naipe) {
        boolean isNaipe = naipe > 0 && naipe < 5;
        if (!isNaipe) {
            throw new RuntimeException("Naipe Inválido");
        }
        return naipe;
    }
    public static int isCartaMaior(Cartas carta1, Cartas carta2){
        return Integer.compare(carta1.peso(), carta2.peso());

    }
    public static String getNameSprite(Cartas carta){
        String var = "/Sprites/"+ carta.numero;
        if(carta.naipe == 1)
            var = var.concat("Espada.png");
        else if (carta.naipe == 2)
            var = var.concat("Pau.png");
        else if (carta.naipe == 3)
            var = var.concat("Ouro.png");
        else if(carta.naipe == 4)
            var = var.concat("Copo.png");
        return var;
    }
    private int setNumero(int numero) {
        if (numero < 0 || numero >= 14) {
            throw new RuntimeException("Numero Inválido");
        }
        return numero;
    }


}