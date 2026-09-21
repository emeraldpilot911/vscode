import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class game extends JFrame {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel mainPanel = new JPanel(cardLayout);
    private final JPanel startPanel = new JPanel();
    private final JPanel modePanel = new JPanel();
    private final JPanel heroPanel = new JPanel();
    private final JPanel gamePanel = new JPanel();

    private final List<Hero> heroes = new ArrayList<>();
    private Hero selectedHero;
    private final List<Enemy> enemies = new ArrayList<>();
    private int wave = 1;
    private int score = 0;
    private int ammo = 12;
    private boolean bossWave = false;
    private boolean fpsMode = false;
    private final Random random = new Random();

    private final JTextArea logArea = new JTextArea();
    private final JLabel heroInfo = new JLabel();
    private final JLabel enemyInfo = new JLabel();
    private final JLabel waveInfo = new JLabel();
    private final JLabel scoreInfo = new JLabel();

    private JButton attackButton;
    private JButton specialButton;
    private JButton healButton;
    private JButton nextWaveButton;

    public game() {
        setTitle("Skyline Adventure FPS");
        setSize(1100, 760);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        buildHeroes();
        buildStartScreen();
        buildModeSelectionScreen();
        buildHeroSelectionScreen();
        buildGameScreen();

        add(mainPanel);
        showStartScreen();
    }

    private void buildHeroes() {
        heroes.add(new Hero("Blaze", "Burns hotter with every hit.", 120, 18, 12, "Flame Burst", new Color(255, 111, 97)));
        heroes.add(new Hero("Nova", "Fast striker with cosmic energy.", 100, 22, 10, "Star Rush", new Color(129, 140, 248)));
        heroes.add(new Hero("Guardian", "Tank built to absorb pressure.", 150, 14, 16, "Shield Break", new Color(82, 196, 150)));
        heroes.add(new Hero("Volt", "Electric attacks chain through crowds.", 110, 20, 11, "Lightning Storm", new Color(78, 208, 255)));
        heroes.add(new Hero("Viper", "Sneaky assassin with fast combos.", 95, 25, 9, "Shadow Fang", new Color(196, 118, 255)));
        heroes.add(new Hero("Astra", "Balanced master of precision damage.", 115, 19, 13, "Orbit Breaker", new Color(255, 204, 102)));
    }

    private void buildStartScreen() {
        startPanel.setLayout(new BorderLayout());
        startPanel.setBackground(new Color(11, 16, 28));
        startPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel title = new JLabel("SKYLINE ADVENTURE", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 44));
        title.setForeground(new Color(255, 214, 102));
        title.setBorder(BorderFactory.createEmptyBorder(18, 0, 10, 0));

        JLabel subtitle = new JLabel("3D World • Adventure Mode • FPS Missions", SwingConstants.CENTER);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 20));
        subtitle.setForeground(new Color(210, 226, 255));

        JLabel version = new JLabel("Explore, fight, survive, and conquer the city", SwingConstants.CENTER);
        version.setFont(new Font("Arial", Font.PLAIN, 16));
        version.setForeground(new Color(147, 168, 208));

        JButton startButton = make3DButton("Start Game");
        startButton.setPreferredSize(new Dimension(260, 64));
        startButton.addActionListener(e -> showModeScreen());

        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setAlignmentX(CENTER_ALIGNMENT);
        centerPanel.add(title);
        centerPanel.add(subtitle);
        centerPanel.add(version);
        centerPanel.add(Box.createVerticalStrut(22));
        centerPanel.add(startButton);

        startPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(startPanel, "start");
    }

    private void buildModeSelectionScreen() {
        modePanel.setLayout(new BorderLayout());
        modePanel.setBackground(new Color(15, 20, 34));
        modePanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel header = new JLabel("Choose Your Game Mode", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 32));
        header.setForeground(new Color(255, 214, 102));

        JPanel chooser = new JPanel(new GridLayout(1, 2, 25, 25));
        chooser.setOpaque(false);

        JButton adventureButton = make3DButton("Adventure Mode");
        adventureButton.addActionListener(e -> {
            fpsMode = false;
            showHeroScreen();
        });

        JButton fpsButton = make3DButton("FPS Mission");
        fpsButton.addActionListener(e -> {
            fpsMode = true;
            showHeroScreen();
        });

        chooser.add(adventureButton);
        chooser.add(fpsButton);

        modePanel.add(header, BorderLayout.NORTH);
        modePanel.add(chooser, BorderLayout.CENTER);
        mainPanel.add(modePanel, "mode");
    }

    private void buildHeroSelectionScreen() {
        heroPanel.setLayout(new BorderLayout());
        heroPanel.setBackground(new Color(12, 18, 30));
        heroPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel header = new JLabel("Choose Your Hero", SwingConstants.CENTER);
        header.setForeground(new Color(255, 214, 102));
        header.setFont(new Font("Arial", Font.BOLD, 32));
        header.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JLabel subtitle = new JLabel("Each hero has a unique playstyle and special move.", SwingConstants.CENTER);
        subtitle.setForeground(new Color(198, 214, 247));
        subtitle.setFont(new Font("Arial", Font.PLAIN, 16));

        JPanel grid = new JPanel(new GridLayout(0, 2, 20, 20));
        grid.setOpaque(false);
        grid.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));

        for (Hero hero : heroes) {
            grid.add(createHeroCard(hero));
        }

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
        northPanel.setOpaque(false);
        northPanel.add(header);
        northPanel.add(subtitle);

        heroPanel.add(northPanel, BorderLayout.NORTH);
        heroPanel.add(grid, BorderLayout.CENTER);
        mainPanel.add(heroPanel, "heroes");
    }

    private JPanel createHeroCard(Hero hero) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(0, 12));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 214, 102), 2),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)));
        card.setBackground(hero.color.darker().darker());
        card.setOpaque(true);

        JLabel name = new JLabel(hero.name, SwingConstants.CENTER);
        name.setFont(new Font("Arial", Font.BOLD, 28));
        name.setForeground(Color.WHITE);

        JLabel lore = new JLabel("<html><center>" + hero.story + "</center></html>");
        lore.setForeground(new Color(233, 239, 255));
        lore.setFont(new Font("Arial", Font.PLAIN, 13));
        lore.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel stats = new JPanel(new GridLayout(4, 2, 6, 6));
        stats.setOpaque(false);
        stats.add(makeStatLabel("HP", String.valueOf(hero.hp)));
        stats.add(makeStatLabel("ATK", String.valueOf(hero.attack)));
        stats.add(makeStatLabel("DEF", String.valueOf(hero.shield)));
        stats.add(makeStatLabel("Skill", hero.specialName));

        JButton chooseButton = make3DButton("Select Hero");
        chooseButton.addActionListener(e -> {
            selectedHero = hero;
            startGame();
        });

        card.add(name, BorderLayout.NORTH);
        card.add(lore, BorderLayout.CENTER);
        card.add(stats, BorderLayout.CENTER);
        card.add(chooseButton, BorderLayout.SOUTH);
        return card;
    }

    private JPanel makeStatLabel(String label, String value) {
        JPanel box = new JPanel();
        box.setOpaque(false);
        JLabel key = new JLabel(label + ":");
        key.setFont(new Font("Arial", Font.BOLD, 12));
        key.setForeground(new Color(238, 241, 255));
        JLabel val = new JLabel(value);
        val.setFont(new Font("Arial", Font.PLAIN, 12));
        val.setForeground(new Color(255, 214, 102));
        box.setLayout(new BorderLayout());
        box.add(key, BorderLayout.WEST);
        box.add(val, BorderLayout.EAST);
        return box;
    }

    private JButton make3DButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(new Color(48, 82, 130));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(93, 125, 168), 2),
                BorderFactory.createEmptyBorder(12, 18, 12, 18)));
        return button;
    }

    private void buildGameScreen() {
        gamePanel.setLayout(new BorderLayout(12, 12));
        gamePanel.setBackground(new Color(12, 18, 30));
        gamePanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JPanel topBar = new JPanel(new GridLayout(1, 4, 12, 0));
        topBar.setOpaque(false);
        heroInfo.setForeground(new Color(144, 238, 144));
        enemyInfo.setForeground(new Color(255, 130, 130));
        waveInfo.setForeground(new Color(255, 214, 102));
        scoreInfo.setForeground(new Color(170, 220, 255));
        heroInfo.setFont(new Font("Arial", Font.BOLD, 18));
        enemyInfo.setFont(new Font("Arial", Font.BOLD, 18));
        waveInfo.setFont(new Font("Arial", Font.BOLD, 18));
        scoreInfo.setFont(new Font("Arial", Font.BOLD, 18));
        topBar.add(heroInfo);
        topBar.add(enemyInfo);
        topBar.add(waveInfo);
        topBar.add(scoreInfo);

        JPanel actionPanel = new JPanel(new GridLayout(1, 4, 12, 12));
        actionPanel.setOpaque(false);

        attackButton = make3DButton("Attack");
        specialButton = make3DButton("Special");
        healButton = make3DButton("Heal");
        nextWaveButton = make3DButton("Next Wave");

        attackButton.addActionListener(e -> performAction("attack"));
        specialButton.addActionListener(e -> performAction("special"));
        healButton.addActionListener(e -> performAction("heal"));
        nextWaveButton.addActionListener(e -> nextWave());

        actionPanel.add(attackButton);
        actionPanel.add(specialButton);
        actionPanel.add(healButton);
        actionPanel.add(nextWaveButton);

        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
        logArea.setBackground(new Color(10, 15, 23));
        logArea.setForeground(new Color(220, 235, 255));
        logArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scroll = new JScrollPane(logArea);
        scroll.setPreferredSize(new Dimension(980, 500));
        scroll.setBorder(BorderFactory.createLineBorder(new Color(72, 110, 155), 2));

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.add(scroll, BorderLayout.CENTER);

        gamePanel.add(topBar, BorderLayout.NORTH);
        gamePanel.add(bottomPanel, BorderLayout.CENTER);
        gamePanel.add(actionPanel, BorderLayout.SOUTH);

        mainPanel.add(gamePanel, "game");
    }

    private void showStartScreen() {
        cardLayout.show(mainPanel, "start");
    }

    private void showModeScreen() {
        cardLayout.show(mainPanel, "mode");
    }

    private void showHeroScreen() {
        cardLayout.show(mainPanel, "heroes");
    }

    private void updateBattleButtons() {
        if (fpsMode) {
            attackButton.setText("Fire");
            specialButton.setText("Grenade");
            healButton.setText("Reload");
            nextWaveButton.setText("Mission");

            attackButton.setActionCommand("fire");
            specialButton.setActionCommand("grenade");
            healButton.setActionCommand("reload");
            nextWaveButton.setActionCommand("mission");

            attackButton.removeActionListener(attackButton.getActionListeners()[0]);
            specialButton.removeActionListener(specialButton.getActionListeners()[0]);
            healButton.removeActionListener(healButton.getActionListeners()[0]);
            nextWaveButton.removeActionListener(nextWaveButton.getActionListeners()[0]);

            attackButton.addActionListener(e -> performAction("fire"));
            specialButton.addActionListener(e -> performAction("grenade"));
            healButton.addActionListener(e -> performAction("reload"));
            nextWaveButton.addActionListener(e -> performAction("mission"));
        } else {
            attackButton.setText("Attack");
            specialButton.setText("Special");
            healButton.setText("Heal");
            nextWaveButton.setText("Next Wave");

            attackButton.removeActionListener(attackButton.getActionListeners()[0]);
            specialButton.removeActionListener(specialButton.getActionListeners()[0]);
            healButton.removeActionListener(healButton.getActionListeners()[0]);
            nextWaveButton.removeActionListener(nextWaveButton.getActionListeners()[0]);

            attackButton.addActionListener(e -> performAction("attack"));
            specialButton.addActionListener(e -> performAction("special"));
            healButton.addActionListener(e -> performAction("heal"));
            nextWaveButton.addActionListener(e -> nextWave());
        }
    }

    private void startGame() {
        enemies.clear();
        wave = 1;
        score = 0;
        bossWave = false;
        ammo = 12;
        updateBattleButtons();
        spawnWave();
        updateHud();
        logArea.setText("Hero chosen: " + selectedHero.name + "\n" + (fpsMode ? "FPS mission online." : "Adventure mode online.") + "\n");
        cardLayout.show(mainPanel, "game");
    }

    private void spawnWave() {
        enemies.clear();
        bossWave = (wave % 3 == 0);

        if (bossWave) {
            int bossHp = 120 + wave * 30;
            int bossAtk = 15 + wave * 4;
            enemies.add(new Enemy("Boss: Warden of the Rift", bossHp, bossAtk));
            logArea.append("!!! BOSS WAVE !!!\n");
            logArea.append("Enemy: " + enemies.get(0).name + " | HP " + enemies.get(0).hp + " | ATK " + enemies.get(0).attack + "\n\n");
        } else {
            int count = 1 + (wave / 2);
            for (int i = 0; i < count; i++) {
                String[] names = {"Robo Fang", "Magma Bot", "Storm Prowler", "Shadow Runner", "Void Warden"};
                String name = names[random.nextInt(names.length)];
                int hp = 30 + wave * 18 + random.nextInt(20);
                int attack = 8 + wave * 3 + random.nextInt(6);
                enemies.add(new Enemy(name, hp, attack));
            }
            logArea.append("Wave " + wave + " begins!\n");
            for (Enemy e : enemies) {
                logArea.append("Enemy: " + e.name + " | HP " + e.hp + " | ATK " + e.attack + "\n");
            }
            logArea.append("\n");
        }
        updateHud();
    }

    private void performAction(String action) {
        if (selectedHero == null || enemies.isEmpty()) {
            return;
        }

        Enemy target = enemies.get(0);

        switch (action) {
            case "fire" -> {
                if (fpsMode) {
                    if (ammo <= 0) {
                        logArea.append("[EMPTY] Magazine empty. Reload first.\n");
                        return;
                    }
                    ammo--;
                    int fireDamage = selectedHero.attack + 10 + random.nextInt(12);
                    target.hp -= fireDamage;
                    logArea.append("[FIRE] " + selectedHero.name + " blasts " + target.name + " for " + fireDamage + " damage. Ammo: " + ammo + "\n");
                }
            }
            case "grenade" -> {
                if (fpsMode) {
                    int grenadeDamage = selectedHero.attack + 25 + random.nextInt(15);
                    target.hp -= grenadeDamage;
                    logArea.append("[GRENADE] Explosive hit! " + target.name + " takes " + grenadeDamage + " damage.\n");
                }
            }
            case "reload" -> {
                if (fpsMode) {
                    ammo = 12;
                    logArea.append("[RELOAD] Weapon reloaded. Ammo full.\n");
                    return;
                }
            }
            case "mission" -> {
                if (fpsMode) {
                    logArea.append("[MISSION] Sweep the area and clear the remaining enemies.\n");
                    if (enemies.isEmpty()) {
                        winWave();
                        return;
                    }
                    return;
                }
            }
            case "attack" -> {
                if (!fpsMode) {
                    int attackDamage = selectedHero.attack + random.nextInt(10);
                    target.hp -= attackDamage;
                    logArea.append("[ATTACK] " + selectedHero.name + " deals " + attackDamage + " damage to " + target.name + "\n");
                }
            }
            case "special" -> {
                if (!fpsMode) {
                    if (selectedHero.specialCooldown > 0) {
                        logArea.append("[WAIT] " + selectedHero.specialName + " is cooling down.\n");
                        return;
                    }
                    int specialDamage = selectedHero.attack + 18 + random.nextInt(12);
                    target.hp -= specialDamage;
                    selectedHero.specialCooldown = 2;
                    logArea.append("[SPECIAL] " + selectedHero.name + " uses " + selectedHero.specialName + " for " + specialDamage + " damage!\n");
                }
            }
            case "heal" -> {
                if (!fpsMode) {
                    int healAmount = 22;
                    selectedHero.hp = Math.min(selectedHero.hp + healAmount, selectedHero.maxHp);
                    logArea.append("[HEAL] " + selectedHero.name + " restores " + healAmount + " HP.\n");
                }
            }
            default -> {
            }
        }

        if (target.hp <= 0) {
            enemies.remove(target);
            score += bossWave ? 120 : 20;
            logArea.append("[VICTORY] " + target.name + " was defeated!\n");
            if (enemies.isEmpty()) {
                winWave();
                return;
            }
        }

        if (selectedHero.specialCooldown > 0) {
            selectedHero.specialCooldown--;
        }

        if (!enemies.isEmpty()) {
            Enemy attacker = enemies.get(random.nextInt(enemies.size()));
            int damage = attacker.attack + random.nextInt(7);
            int blocked = Math.min(selectedHero.shield, damage);
            selectedHero.hp -= Math.max(0, damage - blocked);
            selectedHero.shield = Math.max(0, selectedHero.shield - blocked);
            logArea.append("[ENEMY] " + attacker.name + " hits for " + damage + " damage.\n");

            if (selectedHero.hp <= 0) {
                selectedHero.hp = 0;
                logArea.append("[DEFEAT] Your hero was knocked out.\n");
                JOptionPane.showMessageDialog(this, "Your hero fell in battle. Game over.", "Defeat", JOptionPane.ERROR_MESSAGE);
                showHeroScreen();
                return;
            }
        }

        updateHud();
    }

    private void winWave() {
        score += bossWave ? 150 : 50;
        logArea.append("[WAVE CLEAR] Wave " + wave + " complete!\n");
        if (bossWave) {
            logArea.append("[BOSS] Rift Warden defeated. Arena stabilized.\n");
        }
        wave++;
        selectedHero.coins += bossWave ? 40 : 25;
        selectedHero.maxHp += bossWave ? 12 : 8;
        selectedHero.hp = selectedHero.maxHp;
        selectedHero.attack += bossWave ? 4 : 2;
        selectedHero.shield += bossWave ? 3 : 1;
        ammo = 12;
        spawnWave();
        updateHud();
    }

    private void nextWave() {
        if (enemies.isEmpty()) {
            winWave();
        } else {
            logArea.append("[INFO] Enemies still remain. Finish the current wave first.\n");
        }
    }

    private void updateHud() {
        if (selectedHero == null) {
            return;
        }

        String enemyText = enemies.isEmpty() ? "No enemies" : enemies.get(0).name + " HP: " + enemies.get(0).hp;
        heroInfo.setText("Hero: " + selectedHero.name + "   HP: " + selectedHero.hp + "/" + selectedHero.maxHp + (fpsMode ? "   Ammo: " + ammo : ""));
        enemyInfo.setText("Enemy: " + enemyText + (bossWave ? "  [BOSS]" : ""));
        waveInfo.setText("Wave: " + wave + (bossWave ? "  BOSS" : ""));
        scoreInfo.setText("Score: " + score + "   Coins: " + selectedHero.coins + "   Mode: " + (fpsMode ? "FPS" : "Adventure"));
    }

    static class Hero {
        String name;
        String story;
        int hp;
        int maxHp;
        int attack;
        int shield;
        String specialName;
        int specialCooldown;
        int coins;
        Color color;

        Hero(String name, String story, int hp, int attack, int shield, String specialName, Color color) {
            this.name = name;
            this.story = story;
            this.hp = hp;
            this.maxHp = hp;
            this.attack = attack;
            this.shield = shield;
            this.specialName = specialName;
            this.specialCooldown = 0;
            this.coins = 0;
            this.color = color;
        }
    }

    static class Enemy {
        String name;
        int hp;
        int attack;

        Enemy(String name, int hp, int attack) {
            this.name = name;
            this.hp = hp;
            this.attack = attack;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            game frame = new game();
            frame.setVisible(true);
        });
    }
}
