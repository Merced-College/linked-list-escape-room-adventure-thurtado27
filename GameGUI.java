import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameGUI extends JFrame {

    private SceneLinkedList scenes;
    private Scene currentScene;
    private ArrayList<Item> inventory;

    private JLabel titleLabel;
    private JTextArea descriptionArea;
    private JTextArea inventoryArea;

    private JButton choice1Button;
    private JButton choice2Button;

    public GameGUI(SceneLinkedList scenes, Scene startScene) {

        this.scenes = scenes;
        this.currentScene = startScene;
        this.inventory = new ArrayList<>();

        setTitle("Escape Room Game");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        setupUI();
        updateScene();

        setVisible(true);
    }

    private void setupUI() {

        titleLabel = new JLabel("", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));

        descriptionArea = new JTextArea();
        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);

        inventoryArea = new JTextArea("Inventory:\n");
        inventoryArea.setEditable(false);

        choice1Button = new JButton();
        choice2Button = new JButton();

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(choice1Button);
        buttonPanel.add(choice2Button);

        add(titleLabel, BorderLayout.NORTH);
        add(new JScrollPane(descriptionArea), BorderLayout.CENTER);
        add(inventoryArea, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);

        choice1Button.addActionListener(e -> handleChoice(0));
        choice2Button.addActionListener(e -> handleChoice(1));
    }

    private void updateScene() {

        titleLabel.setText(currentScene.getTitle());
        descriptionArea.setText(currentScene.getDescription());

        // update buttons
        choice1Button.setText(currentScene.getChoices().get(0).getText());
        choice2Button.setText(currentScene.getChoices().get(1).getText());

        // item pickup
        if (currentScene.getItem() != null) {
            inventory.add(currentScene.getItem());
            currentScene.removeItem();
            updateInventory();
        }

        // win check
        if (currentScene.getSceneId() == 5) {
            if (hasItem("Keycard") || hasItem("Code Note")) {
                JOptionPane.showMessageDialog(this, "You escaped! YOU WIN 🎉");
            } else {
                JOptionPane.showMessageDialog(this, "Door locked. You are missing required items.");
            }
        }
    }

    private void handleChoice(int index) {

        Choice choice = currentScene.getChoices().get(index);
        int nextId = choice.getNextSceneId();

        Scene nextScene = scenes.findSceneById(nextId);

        if (nextScene != null) {
            currentScene = nextScene;
            updateScene();
        }
    }

    private void updateInventory() {

        StringBuilder sb = new StringBuilder("Inventory:\n");

        for (Item item : inventory) {
            sb.append("- ").append(item.getName()).append("\n");
        }

        inventoryArea.setText(sb.toString());
    }

    private boolean hasItem(String name) {
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
}