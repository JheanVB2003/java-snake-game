import model.SnakeGame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame janela = new JFrame("Snake Game");
            janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            janela.add(new SnakeGame());
            janela.pack();
            janela.setResizable(false);
            janela.setLocationRelativeTo(null);
            janela.setVisible(true);

        });
    }
}