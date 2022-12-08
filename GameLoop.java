package manager;
import container.Board;

public class GameLoop extends Thread {
/// Attributs
    Board board;

/// Encapsulation
    public void setBoard(Board board) {this.board = board;}
    public Board getBoard() {return this.board;}

/// Constructeur
    public GameLoop(Board board) {
        setBoard(board);
        this.start();
    }

/// Fonctions de classe
    public void run() {
        long now;
        long wait;

        final int TARGET_FPS = 135;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

        while(true) {
            now = System.nanoTime();

            wait = OPTIMAL_TIME / 1000000;
            getBoard().repaint();
            try {
                Thread.sleep(wait);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
