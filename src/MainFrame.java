import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class MainFrame extends JFrame {
    private List<Player> players = new ArrayList<>();
    private List<Card> tableCards = new ArrayList<>();
    private int turn = 0;
    private GamePanel gamePanel;

    public MainFrame() {
        initGame();
        setTitle("Slave 2D Edition - English/Thai Supported");
        setSize(1000, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        gamePanel = new GamePanel(this);
        add(gamePanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton playBtn = new JButton("PLAY SELECTED");
        JButton passBtn = new JButton("PASS");

        playBtn.addActionListener(e -> {
            List<Card> selected = players.get(0).getHand().stream().filter(Card::isSelected).collect(Collectors.toList());
            if (SlaveGameEngine.isValidMove(selected, tableCards)) {
                tableCards = new ArrayList<>(selected);
                players.get(0).getHand().removeAll(selected);
                for(Card c : players.get(0).getHand()) c.setSelected(false);
                nextTurn();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Move! Cards must match in rank and beat the table.");
            }
        });

        passBtn.addActionListener(e -> { players.get(0).setPassed(true); nextTurn(); });
        btnPanel.add(playBtn); btnPanel.add(passBtn);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private void initGame() {
        List<Card> deck = new ArrayList<>();
        for (int r = 0; r < 13; r++) for (int s = 0; s < 4; s++) deck.add(new Card(r, s));
        Collections.shuffle(deck);
        players.add(new HumanPlayer("You"));
        players.add(new AIPlayer("AI 1")); players.add(new AIPlayer("AI 2")); players.add(new AIPlayer("AI 3"));
        for (int i = 0; i < deck.size(); i++) players.get(i % 4).addCard(deck.get(i));
        for (Player p : players) p.sortHand();
    }

    public void nextTurn() {
        turn = (turn + 1) % 4;
        long active = players.stream().filter(p -> !p.isPassed()).count();
        if (active <= 1 && players.get(turn).isPassed()) {
            tableCards.clear();
            for (Player p : players) p.setPassed(false);
        }
        gamePanel.repaint();
        if (turn != 0) {
            javax.swing.Timer timer = new javax.swing.Timer(1000, e -> {
                List<Card> move = players.get(turn).play(tableCards);
                if (move != null) tableCards = new ArrayList<>(move);
                nextTurn();
                ((javax.swing.Timer)e.getSource()).stop();
            });
            timer.setRepeats(false); timer.start();
        }
    }

    public List<Player> getPlayers() { return players; }
    public List<Card> getTableCards() { return tableCards; }
    public int getTurn() { return turn; }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}