import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class MainFrame extends JFrame {
    private List<Player> players = new ArrayList<>();
    private List<Card> tableCards = new ArrayList<>();
    private int turn = 0, lastMover = 0;
    private boolean gameOver = false;
    private GamePanel gamePanel;

    public MainFrame() {
        initGame();
        setTitle("Slave 2D - English Version");
        setSize(1000, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        gamePanel = new GamePanel(this);
        add(gamePanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton playBtn = new JButton("PLAY SELECTED");
        JButton passBtn = new JButton("PASS");

        playBtn.addActionListener(e -> {
            if (turn != 0 || gameOver) return;
            List<Card> selected = players.get(0).getHand().stream().filter(Card::isSelected).collect(Collectors.toList());
            if (SlaveGameEngine.isValidMove(selected, tableCards)) {
                tableCards = new ArrayList<>(selected);
                players.get(0).getHand().removeAll(selected);
                lastMover = 0;
                checkWin(players.get(0));
                if (!gameOver) nextTurn();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Move! Cards must match in rank and beat the table.");
            }
        });

        passBtn.addActionListener(e -> {
            if (turn != 0 || gameOver || tableCards.isEmpty()) return;
            players.get(0).setPassed(true);
            nextTurn();
        });

        btnPanel.add(playBtn); btnPanel.add(passBtn);
        add(btnPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(null);
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

    private void checkWin(Player p) {
        if (p.getHand().isEmpty()) {
            gameOver = true;
            gamePanel.repaint();
            JOptionPane.showMessageDialog(this, "Game Over! Winner: " + p.getName());
        }
    }

    public void nextTurn() {
        if (gameOver) return;
        turn = (turn + 1) % 4;
        long activeCount = players.stream().filter(p -> !p.isPassed()).count();
        if (activeCount <= 1) {
            tableCards.clear();
            for (Player p : players) p.setPassed(false);
            turn = lastMover;
        } else if (players.get(turn).isPassed()) {
            nextTurn(); return;
        }
        gamePanel.repaint();
        if (turn != 0 && !gameOver) {
            new javax.swing.Timer(800, e -> {
                List<Card> move = players.get(turn).play(tableCards);
                if (move != null) {
                    tableCards = new ArrayList<>(move);
                    lastMover = turn;
                    checkWin(players.get(turn));
                } else players.get(turn).setPassed(true);
                if (!gameOver) nextTurn();
                ((javax.swing.Timer)e.getSource()).stop();
            }).start();
        }
    }

    public List<Player> getPlayers() { return players; }
    public List<Card> getTableCards() { return tableCards; }
    public int getTurn() { return turn; }
    public boolean isGameOver() { return gameOver; }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}