public class Game {
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private char[] board;

    public Game(Player player1, Player player2, Player currentPlayer, char[] board) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = currentPlayer;
        this.board = board;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public char[] getBoard() {
        return board;
    }
}
