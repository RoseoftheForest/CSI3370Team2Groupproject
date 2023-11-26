package adventuregame;

import javax.swing.*;

import adventuregame.Entity.Monster;
import adventuregame.Entity.Player;
import adventuregame.Entity.Stats;
import adventuregame.Item.Item;
import adventuregame.Item.ShopItem;
import adventuregame.Room.FightRoom;
import adventuregame.Room.HealRoom;
import adventuregame.Room.ShopRoom;

import java.awt.*;
import java.awt.event.*;
public class GameView extends JFrame {
    private Game game;
    private JPanel infoPanel;
    private JPanel roomPanel;
    private JPanel controlPanel;
    
    private int playerID;
    private int health = 100;
    private int maxHealth = 100;
    
    public GameView() {
        game = Game.instance();
        
        setTitle("Rougue Rooms");
        setResizable(false);
        setSize(new Dimension(800, 500));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set basic layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(0x222222));
        setContentPane(mainPanel);

        // Create top panel for player stats and health
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(getWidth(), 100));
        topPanel.setBackground(Color.darkGray);

        // Add player stats
        playerHealth = new JLabel("Player Health: ");
        playerStats = new JLabel("Player Stats: ");
        topPanel.add(playerHealth, BorderLayout.WEST);
        topPanel.add(playerStats, BorderLayout.CENTER);

        // Add health bar for the player
        healthBar = new JProgressBar();
        healthBar.setStringPainted(true);
        topPanel.add(healthBar, BorderLayout.SOUTH);

        // Initialize textLog for room and combat text
        textLog = new JTextArea();
        JScrollPane textScrollPane = new JScrollPane(textLog);
        textScrollPane.setPreferredSize(new Dimension(getWidth(), 150));

        // Initialize room for room information
        roomPanel = new JPanel();
        roomPanel.setBackground(Color.lightGray);
        roomPanel.setPreferredSize(new Dimension(getWidth(), 200));

        JPanel controlPanel = new JPanel(new FlowLayout());

        // add panels to frame
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(textScrollPane, BorderLayout.CENTER);
        mainPanel.add(roomPanel, BorderLayout.SOUTH);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        // Create close and minimize buttons
        JButton closeButton = new JButton("X");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        JButton minimizeButton = new JButton("-");
        minimizeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setState(Frame.ICONIFIED);
            }
        });

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(minimizeButton);
        buttonsPanel.add(closeButton);
        topPanel.add(buttonsPanel, BorderLayout.EAST);

        //this.setUndecorated(true);
    }

    public void start() {
        setVisible(true);
    }

    public static Color HEALTHBAR_GREEN = new Color(0x54b846);
    public static Color HEALTHBAR_LIGHT_GREEN = new Color(0x90c73f);
    public static Color HEALTHBAR_YELLOW = new Color(0xe7dd36);
    public static Color HEALTHBAR_ORANGE = new Color(0xee9f2f);
    public static Color HEALTHBAR_RED = new Color(0xd22124);
    public static Color HEALTHBAR_DARK_RED = new Color(0x791313);
    public void updatePlayerHealth(int health, int maxHealth) {
        playerHealth.setText("Player Health: " + health + " / " + maxHealth);
        healthBar.setValue(health);
        healthBar.setMaximum(maxHealth);

        double healthPercentage = (double)health / maxHealth;
        if (healthPercentage >= 0.83) {
            healthBar.setForeground(HEALTHBAR_GREEN);
        } else if (healthPercentage >= 0.667) {
            healthBar.setForeground(HEALTHBAR_LIGHT_GREEN);
        } else if (healthPercentage >= 0.50) {
            healthBar.setForeground(HEALTHBAR_YELLOW);
        } else if (healthPercentage >= 0.333) {
            healthBar.setForeground(HEALTHBAR_ORANGE);
        } else if (healthPercentage >= 0.167) {
            healthBar.setForeground(HEALTHBAR_RED);
        } else if (healthPercentage >= 0) {
            healthBar.setForeground(HEALTHBAR_DARK_RED);
        } else {
            healthBar.setForeground(Color.BLACK);
        }
    }

    public void updatePlayerStats(Stats stats) {
        playerStats.setText(stats.stringify());
    }

    public void updateCombatLog(String log) {
        textLog.append(log + "\n");
    }

    

    public void setFightRoom(FightRoom room) {
        resetGamePanels();
        infoPanel.add(monsterPanel(room.getMonster()));
        controlPanel.add(basicAttackBtn());
        controlPanel.add(specialAttackBtn());
    }

    public void displayMessage(Response message) {
        // // // // //
        message.nextMessage();
    }

    public void setNextRoom() {
        resetControlPanel();
        controlPanel.add(nextRoomBtn());
    }
    
    public void fightLost() {
        resetControlPanel();
        controlPanel.add(gameOverBtn());
    }
    public void setHealRoom() {
        resetGamePanels();
        controlPanel.add(nextRoomBtn());
    }
    public void setShopRoom(ShopRoom room) {
        resetGamePanels();
        for (int i = 0; i < room.getItemCount(); i++) {
            roomPanel.add(buyItemPanel(i, room.getItem(i)));
        }
        
        controlPanel.add(nextRoomBtn());
    }
    public void setGameOver() {
        resetControlPanel();
        resetRoomPanel();
        controlPanel.add(gameOverBtn());
        roomPanel.add(gameOverLabel());
    }

    public void resetGamePanels() {
        resetRoomPanel();
        resetControlPanel();
        resetInfoPanel();
        Player p = game.getPlayer(playerID);
        infoPanel.add(playerPanel(p), BorderLayout.WEST);
    }
    public void resetRoomPanel() {
        roomPanel.removeAll();
    }
    public void resetControlPanel() {
        controlPanel.removeAll();
    }
    public void resetInfoPanel() {
        infoPanel.removeAll();
    }

    public JLabel gameOverLabel() {
        JLabel gameOver = new JLabel("GAME OVER");
        return gameOver;
    }

    public JButton nextRoomBtn() {
        JButton nextRoomBtn = new JButton("Next Room");
        nextRoomBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.nextRoom(playerID);
            }
        });
        return nextRoomBtn;
    }
    public JButton basicAttackBtn() {
        JButton basicAttackButton = new JButton("Basic Attack");
        basicAttackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateCombatLog("Player performed a basic attack!");
            }
        });
        return basicAttackButton;
    }
    public JButton specialAttackBtn() {
        JButton specialAttackButton = new JButton("Special Attack");
        specialAttackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateCombatLog("Player performed a special attack!");
                updatePlayerHealth(health, maxHealth);
                health -= 10;
            }
        });
        return specialAttackButton;
    }
    public JButton gameOverBtn() {
        JButton gameOverBtn = new JButton("Try Again");
        gameOverBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.startNewGame(playerID);
            }
        });
        return gameOverBtn;
    }

    public JPanel buyItemPanel(int position, ShopItem item) {
        int cost = item.getCost();
        String name = item.getName();
        String desc = item.getDescription();

        JPanel itemPanel = new JPanel(new BorderLayout());
        JLabel itemName = new JLabel(name + " - " + cost + "g");
        JLabel itemDesc = new JLabel(desc);
        JButton buyBtn = new JButton("Buy");

        buyBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.buyItem(playerID, position);
            }
        });

        itemPanel.add(itemName, BorderLayout.NORTH);
        itemPanel.add(itemDesc, BorderLayout.CENTER);
        itemPanel.add(buyBtn, BorderLayout.SOUTH);

        return itemPanel;
    }
    public JPanel monsterPanel(Monster monster) {
        int health = monster.getHealth();
        int maxHealth = monster.getStats().getMaxHealth();
        String name = monster.getName();
        String desc = monster.getDescription();
        
        JPanel monsterPanel = new JPanel(new BorderLayout());
        JLabel monsterName = new JLabel(name);
        JLabel monsterDesc = new JLabel(desc);
        JProgressBar healthBar = healthBar(health, maxHealth);
        
        monsterPanel.add(healthBar, BorderLayout.CENTER);
        monsterPanel.add(monsterName, BorderLayout.NORTH);
        monsterPanel.add(monsterDesc, BorderLayout.SOUTH);
        
        return monsterPanel;
    }

    public JPanel playerPanel(Player player) {
        int health = player.getHealth();
        int maxHealth = player.getStats().getMaxHealth();
        String name = player.getName();
        int money = player.getMoney();
        String stats = player.getStats().stringify();

        JLabel playerName = new JLabel(name);
        JLabel playerMoney = new JLabel(money + "g");

        JPanel playerStats = new JPanel(new BorderLayout());
        JLabel statsLabel = new JLabel(stats);
        playerStats.add(statsLabel, BorderLayout.EAST);
        playerStats.add(playerMoney, BorderLayout.WEST);

        JPanel playerPanel = new JPanel(new BorderLayout());
        playerPanel.add(healthBar(health, maxHealth), BorderLayout.CENTER);
        playerPanel.add(playerName, BorderLayout.NORTH);
        playerPanel.add(playerStats, BorderLayout.SOUTH);

        return playerPanel;
    }

    public JProgressBar healthBar(int health, int maxHealth) {
        JProgressBar healthBar = new JProgressBar();

        healthBar.setValue(health);
        healthBar.setMaximum(maxHealth);

        double healthPercentage = (double)health / maxHealth;
        if (healthPercentage >= 0.83) {
            healthBar.setForeground(HEALTHBAR_GREEN);
        } else if (healthPercentage >= 0.667) {
            healthBar.setForeground(HEALTHBAR_LIGHT_GREEN);
        } else if (healthPercentage >= 0.50) {
            healthBar.setForeground(HEALTHBAR_YELLOW);
        } else if (healthPercentage >= 0.333) {
            healthBar.setForeground(HEALTHBAR_ORANGE);
        } else if (healthPercentage >= 0.167) {
            healthBar.setForeground(HEALTHBAR_RED);
        } else if (healthPercentage >= 0) {
            healthBar.setForeground(HEALTHBAR_DARK_RED);
        } else {
            healthBar.setForeground(Color.BLACK);
        }
        return healthBar;
    }

}
