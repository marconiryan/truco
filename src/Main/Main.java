package Main;


import javax.swing.*;

public class Main {
    public static void main(String[] args){
        JFrame window = new JFrame();
        Game game = new Game();
        window.setVisible(true);
        window.setTitle("Game");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.add(game);
        window.pack();
        game.startGameThread();

    }
}