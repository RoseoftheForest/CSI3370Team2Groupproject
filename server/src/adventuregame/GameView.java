package adventuregame;

import javax.print.DocFlavor;
import javax.swing.*;

import adventuregame.Entity.Monster;
import adventuregame.Entity.Player;
import adventuregame.Item.ShopItem;
import adventuregame.Room.FightRoom;
import adventuregame.Room.ShopRoom;

import java.awt.*;
import java.awt.event.*;
public class GameView extends JFrame {
    private Game game;
    private JPanel infoPanel;
    private JPanel roomPanel;
    private JPanel controlPanel;
    
    private int playerID;
    
    public final static Color BACKGROUND_COLOR = new Color(0x222222);
    public final static Color HEALTHBAR_GREEN = new Color(0x54b846);
    public final static Color HEALTHBAR_LIGHT_GREEN = new Color(0x90c73f);
    public final static Color HEALTHBAR_YELLOW = new Color(0xe7dd36);
    public final static Color HEALTHBAR_ORANGE = new Color(0xee9f2f);
    public final static Color HEALTHBAR_RED = new Color(0xd22124);
    public final static Color HEALTHBAR_DARK_RED = new Color(0x791313);
    public final static int WIDTH = 800;
    public final static int HEIGHT = 500;

    public final static Dimension MAIN_DIMENSIONS = new Dimension(WIDTH, HEIGHT);
    public final static Dimension WINDOW_CONTROL_DIMENSIONS = new Dimension(WIDTH, (int)(HEIGHT * 0.1));
    public final static Dimension ROOM_DIMENSIONS = new Dimension(WIDTH, (int)(HEIGHT * 0.4));
    public final static Dimension INFO_DIMENSIONS = new Dimension(WIDTH, (int)(HEIGHT * 0.3));
    public final static Dimension CONTROL_DIMENSIONS = new Dimension(WIDTH, (int)(HEIGHT * 0.1));

    private static GameView uniqueInstance;

    public static GameView instance() {
        if (uniqueInstance == null) {
            uniqueInstance = new GameView();
        }
        return uniqueInstance;
    }


    private GameView() {
        game = Game.instance();
        
        setTitle("Rougue Rooms");
        setResizable(false);
        setSize(MAIN_DIMENSIONS);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set basic layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
        setContentPane(mainPanel);

        infoPanel = new JPanel(new GridLayout(1, 2));
        infoPanel.setPreferredSize(INFO_DIMENSIONS);
        //infoPanel.setBackground(Color.LIGHT_GRAY);
        //infoPanel.setPreferredSize(new Dimension(INFO_DIMENSIONS));

        // Initialize room for room information
        roomPanel = new JPanel();
        // roomPanel.setBackground(BACKGROUND_COLOR);
        roomPanel.setPreferredSize(new Dimension(ROOM_DIMENSIONS));

        controlPanel = new JPanel(new FlowLayout());
        controlPanel.setPreferredSize(CONTROL_DIMENSIONS);

        // add panels to frame
        // mainPanel.add(windowPanel(), BorderLayout.NORTH);
        JPanel midPanel = new JPanel(new BorderLayout());
        midPanel.add(infoPanel, BorderLayout.NORTH);
        midPanel.add(roomPanel, BorderLayout.CENTER);
        mainPanel.add(midPanel, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        //this.setUndecorated(true);
    }

    public void start() {
        setVisible(true);
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }
    
    public void setFightRoom(FightRoom room) {
        System.out.println("Setting FightRoom");
        resetGamePanels();
        infoPanel.add(monsterPanel(room.getMonster()));
        System.out.println("Added monster to view");
        controlPanel.add(basicAttackBtn());
        controlPanel.add(specialAttackBtn());
        controlPanel.add(menuBtn());

        controlPanel.setVisible(true);
        controlPanel.revalidate();
        infoPanel.revalidate();
        controlPanel.repaint();
        infoPanel.repaint();
        System.out.println("Added attack buttons to view");
    }

    public void displayMessage(Response message) {
        resetRoomPanel();
        
        GridLayout layout = new GridLayout(message.size()/2, 1);
        JPanel textPanel = new JPanel(layout);
        
        JPanel textRow;
        while (message.hasNext()) {
            textRow = new JPanel(new BorderLayout(50, 20));

            JLabel label = new JLabel();
            String str = message.nextMessage();
            System.out.println(str);
            label.setText(formatLines(str));
            textRow.add(label, BorderLayout.WEST);

            if (message.hasNext()) {
                label = new JLabel();
                str = message.nextMessage();
                System.out.println(str);
                label.setText(formatLines(str));
                textRow.add(label, BorderLayout.EAST);
            }

            textPanel.add(textRow);
        }
        roomPanel.add(textPanel, BorderLayout.CENTER);
        roomPanel.revalidate();
        roomPanel.repaint();
    }

    public void setNextRoom() {
        resetControlPanel();
        controlPanel.add(nextRoomBtn());
        controlPanel.revalidate();
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
        controlPanel.add(menuBtn());
        roomPanel.add(gameOverLabel());
        controlPanel.revalidate();
        controlPanel.repaint();
        roomPanel.revalidate();
        roomPanel.repaint();
    }

    public void setPlayerScreen() {
        resetControlPanel();
        resetRoomPanel();
        resetInfoPanel();
        controlPanel.add(menuBtn());
        controlPanel.revalidate();
        controlPanel.repaint();
        JPanel nameInput = new JPanel(new GridLayout(1, 2));
        //label.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        JTextField nameField = new JTextField();
        JLabel nameInfo = new JLabel("Type in your name and press Enter: ");
        nameInput.setBorder(BorderFactory.createEmptyBorder(60, 180, 60, 180));
        
        nameField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    JTextField textField = (JTextField) e.getSource();
                    String text = textField.getText();
                    textField.setText(text.toUpperCase());
                    GameController.newPlayer(text);
                }
            }
        });
        nameInput.add(nameInfo);
        nameInput.add(nameField);
        infoPanel.add(nameInput);
        infoPanel.revalidate();
        infoPanel.repaint();     
    }

    public void setMenuScreen() {
        resetControlPanel();
        resetRoomPanel();
        resetInfoPanel();
        JLabel title = new JLabel("Rogue Rooms");
        title.setBorder(BorderFactory.createEmptyBorder(100, 300, 100, 300));
        title.setFont(new Font("Sans Serif", Font.BOLD, 24));
        infoPanel.add(title);
        
        JButton start = new JButton("Enter the Dungeon");
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setPlayerScreen();
            }
        });

        JButton settings = new JButton("Settings");
        settings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GameController.loadSettings(playerID);
            }
        });
        settings.setEnabled(false);

        JButton about = new JButton("About");
        about.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setAboutScreen();
            }
        });

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.add(start);
        buttons.add(settings);
        buttons.add(about);

        //controlPanel.add(title, BorderLayout.CENTER);

        controlPanel.add(buttons, BorderLayout.SOUTH);
        controlPanel.revalidate();

    }
    public void setAboutScreen() {
        resetControlPanel();
        resetInfoPanel();
        resetRoomPanel();

        JTextArea text = new JTextArea("Rogue Rooms");
        JLabel contributors = new JLabel("Created by: ");
        infoPanel.add(text);
        infoPanel.add(contributors);
    }

    public void resetGamePanels() {
        resetRoomPanel();
        resetControlPanel();
        resetInfoPanel();
        Player p = game.getPlayer(playerID);
        infoPanel.add(playerPanel(p));
        infoPanel.revalidate();
        infoPanel.repaint();
    }
    public void resetRoomPanel() {
        roomPanel.removeAll();
        roomPanel.revalidate();
        roomPanel.repaint();
    }
    public void resetControlPanel() {
        controlPanel.removeAll();
        controlPanel.revalidate();
        controlPanel.repaint();
    }
    public void resetInfoPanel() {
        infoPanel.removeAll();
        infoPanel.revalidate();
        infoPanel.repaint();
    }

    public JLabel gameOverLabel() {
        JLabel gameOver = new JLabel("GAME OVER");
        return gameOver;
    }

    public JButton nextRoomBtn() {
        JButton nextRoomBtn = new JButton("Next Room");
        nextRoomBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GameController.nextRoom(playerID);
            }
        });
        return nextRoomBtn;
    }
    public JButton basicAttackBtn() {
        JButton basicAttackButton = new JButton("Basic Attack");
        basicAttackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Player performed a basic attack!");
                GameController.useBasicAttack(playerID);
            }
        });
        return basicAttackButton;
    }
    public JButton specialAttackBtn() {
        JButton specialAttackButton = new JButton("Special Attack");
        specialAttackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Player performed a special attack!");
                GameController.useSpecialAttack(playerID);
            }
        });
        return specialAttackButton;
    }
    public JButton gameOverBtn() {
        JButton gameOverBtn = new JButton("Restart");
        gameOverBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Starting new game");
                GameController.startNewGame(playerID);
            }
        });
        return gameOverBtn;
    }
    public JButton menuBtn() {
        JButton menuBtn = new JButton("Menu");
        menuBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Returning to menu");
                setMenuScreen();
            }
        });
        System.out.println("Menu Button Created!");
        return menuBtn;
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
                System.out.println("Buying item");
                GameController.buyItem(playerID, position);
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
        
        JLabel monsterDesc = new JLabel(formatLines(fillLines(desc, 7)));

        JPanel monsterInfo = new JPanel(new GridLayout(1, 2));
        JLabel monsterName = new JLabel(name);
        JLabel monsterHealth = new JLabel(health + " / " + maxHealth);
        monsterInfo.add(monsterName);
        monsterInfo.add(monsterHealth);

        JProgressBar healthBar = healthBar(health, maxHealth);
        
        JPanel monsterPanel = new JPanel(new BorderLayout());
        monsterPanel.add(monsterInfo, BorderLayout.NORTH);
        monsterPanel.add(healthBar, BorderLayout.CENTER);
        monsterPanel.add(monsterDesc, BorderLayout.SOUTH);
        
        return monsterPanel;
    }
    public String fillLines(String str, int lines) {
        int lineCount = 1;
        String strCopy = str;
        while(strCopy.contains("\n")) {
            strCopy = strCopy.replaceFirst("\n", "");
            lineCount++;
        }
        for (int i = lineCount; i < lines; i++) {
            str += "\n";
        }
        return str;
        
    }
    
    public JPanel playerPanel(Player player) {
        int health = player.getHealth();
        int maxHealth = player.getStats().getMaxHealth();
        String name = player.getName();
        int money = player.getMoney();
        String stats = player.getStats().stringify("\n");
        stats = fillLines(stats, 7);

        // Player Info
        JPanel playerInfo = new JPanel(new GridLayout(1, 2));

        JLabel playerName = new JLabel(name);
        JLabel playerHealth = new JLabel(health + " / " + maxHealth);

        playerInfo.add(playerName);
        playerInfo.add(playerHealth);

        // Player Stats & Money
        JPanel playerStats = new JPanel(new GridLayout(1, 2));

        JLabel playerMoney = new JLabel(money + "g");
        playerMoney.setHorizontalAlignment(JLabel.CENTER);
        JLabel statsLabel = new JLabel(formatLines(stats));
        statsLabel.setHorizontalAlignment(JLabel.CENTER);
        
        playerStats.add(playerMoney);
        playerStats.add(statsLabel);
        
        // Combined Player Panel
        JPanel playerPanel = new JPanel(new BorderLayout());
        playerPanel.add(playerInfo, BorderLayout.NORTH);
        playerPanel.add(healthBar(health, maxHealth), BorderLayout.CENTER);
        playerPanel.add(playerStats, BorderLayout.SOUTH);

        return playerPanel;
    }

    public String formatLines(String str) {
        str = str.replaceAll("\t", "&emsp;");
        return "<html>" + str.replaceAll("<","&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</html>";
    }

    public JProgressBar healthBar(int health, int maxHealth) {
        JProgressBar healthBar = new JProgressBar();
        double healthPercentage = (double)health / maxHealth;
        healthBar.setValue((int)(healthPercentage*100));
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
        healthBar.validate();
        healthBar.repaint();
        return healthBar;
    }


    public JPanel windowPanel() {
        JPanel windowPanel = new JPanel(new BorderLayout());
        windowPanel.setPreferredSize(WINDOW_CONTROL_DIMENSIONS);
        windowPanel.setBackground(BACKGROUND_COLOR);
        
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

        windowPanel.add(buttonsPanel, BorderLayout.EAST);
        return windowPanel;
    }

}
