import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToe extends JFrame {
    private JButton[] buttons;
    private Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private JLabel statusLabel;
    private JButton saveButton;
    private boolean gameOver;
    private DatabaseManager dbManager;

    public TicTacToe() {
        dbManager = new DatabaseManager();
        setTitle("Tic Tac Toe");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 3));

        board = new Board();
        buttons = new JButton[9];

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton("");
            buttons[i].setFont(new Font("Arial", Font.PLAIN, 60));
            buttons[i].setFocusPainted(false);
            buttons[i].addActionListener(new ButtonClickListener(i));
            add(buttons[i]);
        }

        statusLabel = new JLabel("Welcome to Tic Tac Toe!");
        add(statusLabel);

        saveButton = new JButton("Save Game");
        saveButton.addActionListener(e -> saveGameState());
        add(saveButton);

        displayMenu();
    }

    private void displayMenu() {
        String[] options = {"New Game", "Load Game", "Exit"};
        int choice = JOptionPane.showOptionDialog(this,
                "Choose an option:",
                "Menu",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);

        if (choice == 0) {
            inputPlayerNames();
            resetGame();
        } else if (choice == 1) {
            loadGameFromDatabase();
        } else {
            System.exit(0);
        }
    }

    private void inputPlayerNames() {
        String player1Name = JOptionPane.showInputDialog(this, "Enter Player 1 name (X):");
        String player2Name = JOptionPane.showInputDialog(this, "Enter Player 2 name (O):");
        player1 = new Player(player1Name, 'X');
        player2 = new Player(player2Name, 'O');
        currentPlayer = player1;
        statusLabel.setText(currentPlayer.getName() + " (" + currentPlayer.getSymbol() + ")'s turn.");
    }

    private class ButtonClickListener implements ActionListener {
        private int index;

        public ButtonClickListener(int index) {
            this.index = index;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!gameOver && board.makeMove(index, currentPlayer.getSymbol())) {
                buttons[index].setText(String.valueOf(currentPlayer.getSymbol()));

                if (board.checkWinner(currentPlayer.getSymbol())) {
                    statusLabel.setText(currentPlayer.getName() + " wins!");
                    gameOver = true;
                    askForReplay();
                } else if (board.isBoardFull()) {
                    statusLabel.setText("It's a draw!");
                    gameOver = true;
                    askForReplay();
                } else {
                    switchPlayer();
                }
            }
        }
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
        statusLabel.setText(currentPlayer.getName() + " (" + currentPlayer.getSymbol() + ")'s turn.");
    }

    private void resetGame() {
        board.reset();
        for (JButton button : buttons) {
            button.setText("");
        }
        gameOver = false;
        currentPlayer = player1;
        statusLabel.setText(currentPlayer.getName() + " (" + currentPlayer.getSymbol() + ")'s turn.");
    }

    private void askForReplay() {
        int response = JOptionPane.showConfirmDialog(this, "Game Over. Play again?", "Game Over", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            resetGame();
        } else {
            System.exit(0);
        }
    }

    private void saveGameState() {
        dbManager.saveGame(player1, player2, currentPlayer, board.getBoard());
        statusLabel.setText("Game saved!");
    }

    private void loadGameFromDatabase() {
        Game loadedGame = dbManager.loadLatestGame();
        if (loadedGame != null) {
            player1 = loadedGame.getPlayer1();
            player2 = loadedGame.getPlayer2();
            currentPlayer = loadedGame.getCurrentPlayer();
            board = new Board();
            System.arraycopy(loadedGame.getBoard(), 0, board.getBoard(), 0, 9);
            updateButtons();
            statusLabel.setText(currentPlayer.getName() + " (" + currentPlayer.getSymbol() + ")'s turn.");
        } else {
            JOptionPane.showMessageDialog(this, "No saved game found.");
        }
    }

    private void updateButtons() {
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setText(String.valueOf(board.getBoard()[i]));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TicTacToe game = new TicTacToe();
            game.setVisible(true);
        });
    }
}
