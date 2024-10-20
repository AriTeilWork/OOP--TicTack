import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerInputDialog extends JDialog {
    private JTextField player1Field;
    private JTextField player2Field;
    private JButton okButton;
    private String player1Name;
    private String player2Name;

    public PlayerInputDialog(Frame owner) {
        super(owner, "Enter Player Names", true);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Player 1 (X):"));
        player1Field = new JTextField(20);
        add(player1Field);

        add(new JLabel("Player 2 (O):"));
        player2Field = new JTextField(20);
        add(player2Field);

        okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player1Name = player1Field.getText();
                player2Name = player2Field.getText();
                dispose();
            }
        });
        add(okButton);

        setSize(300, 150);
        setLocationRelativeTo(owner);
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }
}
