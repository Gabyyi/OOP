import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class lab6 extends JFrame {

    private JButton setLetterButton, clearNameButton;
    private JLabel nameDisplayLabel;
    private StringBuilder nameBuilder;
    private JRadioButton maleButton, femaleButton, attackHelicopter, walmartBag, wompWomp;
    private JTextArea jobDescriptionArea;
    private JScrollBar experienceScrollBar;
    private JTextField experienceField;
    private JComboBox<String> locationComboBox;
    private JButton addButton, showAllButton, saveAllButton;
    private DrawingPanel drawingPanel;
    private ArrayList<Person> personList = new ArrayList<>();
    private Timer colorTimer;

    public lab6() {
        setTitle("Person Form");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Name"), gbc);

        drawingPanel = new DrawingPanel();
        drawingPanel.setPreferredSize(new Dimension(200, 200));
        drawingPanel.setBackground(Color.WHITE);
        
        setLetterButton = new JButton("Set Letter");
        clearNameButton = new JButton("Clear Name");
        nameDisplayLabel = new JLabel("Name: ");
        nameBuilder = new StringBuilder();

        setLetterButton.addActionListener(e -> {
            char letter = drawingPanel.getLetterBasedOnInk();
            nameBuilder.append(letter);
            nameDisplayLabel.setText("Name: " + nameBuilder.toString());
            drawingPanel.clearInk();
        });

        clearNameButton.addActionListener(e -> {
            nameBuilder.setLength(0);
            nameDisplayLabel.setText("Name: ");
        });

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        add(drawingPanel, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(setLetterButton, gbc);

        gbc.gridx = 2;
        add(clearNameButton, gbc);

        gbc.gridy = 2;
        gbc.gridx = 1;
        add(nameDisplayLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Gender"), gbc);
        
        maleButton = new JRadioButton("Male");
        femaleButton = new JRadioButton("Female");
        attackHelicopter = new JRadioButton("Attack Helicopter");
        walmartBag = new JRadioButton("Walmart Bag");
        wompWomp = new JRadioButton("Womp Womp");

        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
        genderGroup.add(attackHelicopter);
        genderGroup.add(walmartBag);
        genderGroup.add(wompWomp);

        JPanel genderPanel = new JPanel();
        genderPanel.add(maleButton);
        genderPanel.add(femaleButton);
        genderPanel.add(attackHelicopter);
        genderPanel.add(walmartBag);
        genderPanel.add(wompWomp);
        gbc.gridx = 1;
        add(genderPanel, gbc);

        ActionListener flashListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color randomColor = new Color((int)(Math.random() * 0x1000000));
                getContentPane().setBackground(randomColor);
            }
        };

        colorTimer = new Timer(100, flashListener);

        ActionListener genderChangeListener = e -> {
            if (maleButton.isSelected() || femaleButton.isSelected()) {
                colorTimer.stop();
                getContentPane().setBackground(Color.WHITE);
            } else {
                colorTimer.start();
            }
        };

        maleButton.addActionListener(genderChangeListener);
        femaleButton.addActionListener(genderChangeListener);
        attackHelicopter.addActionListener(genderChangeListener);
        walmartBag.addActionListener(genderChangeListener);
        wompWomp.addActionListener(genderChangeListener);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Job Description"), gbc);
        jobDescriptionArea = new JTextArea(4, 15);
        JScrollPane jobScrollPane = new JScrollPane(jobDescriptionArea);
        gbc.gridx = 1;
        add(jobScrollPane, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Experience in years"), gbc);
        experienceField = new JTextField(3);
        experienceField.setEditable(false);
        experienceScrollBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 1, 0, 70);
        experienceScrollBar.addAdjustmentListener(e -> {
            int value = experienceScrollBar.getValue();
            experienceField.setText(String.valueOf(value));
        });
        JPanel experiencePanel = new JPanel();
        experiencePanel.add(experienceField);
        experiencePanel.add(experienceScrollBar);
        gbc.gridx = 1;
        add(experiencePanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        add(new JLabel("Location"), gbc);
        locationComboBox = new JComboBox<>(new String[]{"Bucharest", "Miami", "Warsow", "New York", "Frankfurt"});
        gbc.gridx = 1;
        add(locationComboBox, gbc);

        addButton = new JButton("Add");
        showAllButton = new JButton("Show All");
        saveAllButton = new JButton("Save All");
        
        gbc.gridx = 0;
        gbc.gridy = 7;
        add(addButton, gbc);
        gbc.gridx = 1;
        add(showAllButton, gbc);
        gbc.gridx = 2;
        add(saveAllButton, gbc);

        addButton.addActionListener(e -> {
            String name = nameBuilder.toString();
            String gender = maleButton.isSelected() ? "Male" : femaleButton.isSelected() ? "Female" : 
                            attackHelicopter.isSelected() ? "Attack Helicopter" : 
                            walmartBag.isSelected() ? "Walmart Bag" : 
                            wompWomp.isSelected() ? "Womp Womp" : "";
            String jobDescription = jobDescriptionArea.getText();
            int experience = experienceScrollBar.getValue();
            String location = (String) locationComboBox.getSelectedItem();

            Person person = new Person(name, gender, jobDescription, experience, location);
            personList.add(person);

            nameBuilder.setLength(0);
            nameDisplayLabel.setText("Name: ");
            jobDescriptionArea.setText("");
            experienceScrollBar.setValue(0);
            genderGroup.clearSelection();
            locationComboBox.setSelectedIndex(0);
            getContentPane().setBackground(Color.WHITE);
            colorTimer.stop();
        });

        showAllButton.addActionListener(e -> {
            System.out.println("All Persons:");
            for (Person person : personList) {
                System.out.println(person);
            }
        });

        saveAllButton.addActionListener(e -> {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("people.txt"))) {
                for (Person person : personList) {
                    writer.write(person.toString());
                    writer.newLine();
                }
                JOptionPane.showMessageDialog(this, "Data saved to people.txt", "Save Successful", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving data to file", "Save Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            lab6 frame = new lab6();
            frame.setVisible(true);
        });
    }

    private class DrawingPanel extends JPanel {
        private int inkAmount;

        public DrawingPanel() {
            inkAmount = 0;

            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    inkAmount++;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawString("Ink: " + inkAmount, 10, 20);
        }

        public char getLetterBasedOnInk() {
            int level = Math.min(inkAmount / 10, 25);
            return (char) ('A' + level);
        }

        public void clearInk() {
            inkAmount = 0;
            repaint();
        }
    }
}

class Person {
    private String name;
    private String gender;
    private String jobDescription;
    private int experience;
    private String location;

    public Person(String name, String gender, String jobDescription, int experience, String location) {
        this.name = name;
        this.gender = gender;
        this.jobDescription = jobDescription;
        this.experience = experience;
        this.location = location;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Gender: " + gender + ", Job Description: " + jobDescription
                + ", Experience: " + experience + " years, Location: " + location;
    }
}
