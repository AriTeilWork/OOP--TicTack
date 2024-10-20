import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseManager {
    private static final String DATABASE_URL = "jdbc:sqlite:tic_tac_toe.db";

    public DatabaseManager() {
        createTable();
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS games (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "player1 TEXT," +
                "player2 TEXT," +
                "currentPlayer TEXT," +
                "board TEXT" +
                ");";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveGame(Player player1, Player player2, Player currentPlayer, char[] board) {
        String sql = "INSERT INTO games(player1, player2, currentPlayer, board) VALUES(?, ?, ?, ?);";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, player1.getName());
            stmt.setString(2, player2.getName());
            stmt.setString(3, currentPlayer.getName());
            stmt.setString(4, new String(board));
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Game loadLatestGame() {
        String sql = "SELECT player1, player2, currentPlayer, board FROM games ORDER BY id DESC LIMIT 1;";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                Player player1 = new Player(rs.getString("player1"), 'X');
                Player player2 = new Player(rs.getString("player2"), 'O');
                String currentPlayerName = rs.getString("currentPlayer");
                char[] board = rs.getString("board").toCharArray();
                Player currentPlayer = currentPlayerName.equals(player1.getName()) ? player1 : player2;
                return new Game(player1, player2, currentPlayer, board);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
