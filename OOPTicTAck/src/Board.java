public class Board {
    private char[] board;

    public Board() {
        board = new char[9]; // Создание пустой доски
    }

    public boolean makeMove(int index, char symbol) {
        if (board[index] == '\u0000') { // Проверка на пустую клетку
            board[index] = symbol;
            return true;
        }
        return false;
    }

    public boolean checkWinner(char currentPlayer) {
        int[][] winConditions = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                {0, 4, 8}, {2, 4, 6}
        };

        for (int[] condition : winConditions) {
            if (board[condition[0]] == currentPlayer &&
                    board[condition[1]] == currentPlayer &&
                    board[condition[2]] == currentPlayer) {
                return true;
            }
        }
        return false;
    }

    public boolean isBoardFull() {
        for (char cell : board) {
            if (cell == '\u0000') {
                return false;
            }
        }
        return true;
    }

    public char[] getBoard() {
        return board;
    }

    public void reset() {
        for (int i = 0; i < board.length; i++) {
            board[i] = '\u0000';
        }
    }
}
