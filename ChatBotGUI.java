import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class ChatBotGUI extends JFrame {
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;
    private HashMap<String, String> knowledgeBase;

    public ChatBotGUI() {
        // Initialize knowledge base
        knowledgeBase = new HashMap<>();
        knowledgeBase.put("hello", "Hi there! How can I help you?");
        knowledgeBase.put("hi", "Hello! What’s up?");
        knowledgeBase.put("how are you", "I’m just a bot, but I’m doing great!");
        knowledgeBase.put("bye", "Goodbye! Have a nice day!");
        knowledgeBase.put("your name", "I’m CodeAlpha Chatbot.");

        // Setup GUI
        setTitle("CodeAlpha Chatbot");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        inputField = new JTextField();
        sendButton = new JButton("Send");

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        // Action listener for send button
        sendButton.addActionListener(e -> processInput());
        inputField.addActionListener(e -> processInput()); // Press Enter to send
    }

    private void processInput() {
        String userInput = inputField.getText().toLowerCase().trim();
        inputField.setText("");

        if (userInput.isEmpty()) return;

        chatArea.append("You: " + userInput + "\n");

        String response = getResponse(userInput);
        chatArea.append("Bot: " + response + "\n\n");
    }

    private String getResponse(String input) {
        // Learning new answers: teach question = answer
        if (input.startsWith("teach ")) {
            String[] parts = input.substring(6).split("=", 2);
            if (parts.length == 2) {
                knowledgeBase.put(parts[0].trim(), parts[1].trim());
                return "Got it! I learned a new response.";
            } else {
                return "Format to teach: teach question = answer";
            }
        }

        // Search in knowledge base
        for (String key : knowledgeBase.keySet()) {
            if (input.contains(key)) {
                return knowledgeBase.get(key);
            }
        }

        return "Sorry, I don’t understand. You can teach me using: teach question = answer";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ChatBotGUI().setVisible(true);
        });
    }
}
