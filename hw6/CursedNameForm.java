/*import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class hw6 extends JFrame {

    private JTextField nameField;
    private JRadioButton maleButton, femaleButton;
    private JTextArea jobDescriptionArea;
    private JScrollBar experienceScrollBar;
    private JTextField experienceField;
    private JComboBox<String> locationComboBox;
    private JButton addButton, showAllButton, saveAllButton;

    private ArrayList<Person> personList = new ArrayList<>();

    public hw6() {
        // Setting up the frame
        setTitle("Person Form");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Name Label and Field
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Name"), gbc);
        nameField = new JTextField(15);
        gbc.gridx = 1;
        add(nameField, gbc);

        // Gender Radio Buttons
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Gender"), gbc);
        maleButton = new JRadioButton("Male");
        femaleButton = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
        JPanel genderPanel = new JPanel();
        genderPanel.add(maleButton);
        genderPanel.add(femaleButton);
        gbc.gridx = 1;
        add(genderPanel, gbc);

        // Job Description Text Area
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Job Description"), gbc);
        jobDescriptionArea = new JTextArea(4, 15);
        JScrollPane jobScrollPane = new JScrollPane(jobDescriptionArea);
        gbc.gridx = 1;
        add(jobScrollPane, gbc);

        // Experience Scroll Bar and Field
        gbc.gridx = 0;
        gbc.gridy = 3;
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

        // Location ComboBox
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Location"), gbc);
        locationComboBox = new JComboBox<>(new String[]{"New York", "London", "Paris", "Tokyo", "Sydney"});
        gbc.gridx = 1;
        add(locationComboBox, gbc);

        // Add, Show All, and Save All Buttons
        addButton = new JButton("Add");
        showAllButton = new JButton("Show All");
        saveAllButton = new JButton("Save All");
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(addButton, gbc);
        
        gbc.gridx = 1;
        add(showAllButton, gbc);
        
        gbc.gridx = 2;
        add(saveAllButton, gbc);

        // Add Button Action
        addButton.addActionListener(e -> {
            String name = nameField.getText();
            String gender = maleButton.isSelected() ? "Male" : femaleButton.isSelected() ? "Female" : "";
            String jobDescription = jobDescriptionArea.getText();
            int experience = experienceScrollBar.getValue();
            String location = (String) locationComboBox.getSelectedItem();

            // Add a new Person object to the list
            Person person = new Person(name, gender, jobDescription, experience, location);
            personList.add(person);

            // Clear fields
            nameField.setText("");
            jobDescriptionArea.setText("");
            experienceScrollBar.setValue(0);
            genderGroup.clearSelection();
            locationComboBox.setSelectedIndex(0);
        });

        // Show All Button Action - Displays in a new JFrame
        showAllButton.addActionListener(e -> showAllPersonsInFrame());

        // Save All Button Action - Saves to a text file
        saveAllButton.addActionListener(e -> saveAllPersonsToFile());
    }

    private void showAllPersonsInFrame() {
        JFrame showAllFrame = new JFrame("All Persons");
        showAllFrame.setSize(400, 300);
        showAllFrame.setLayout(new BorderLayout());

        JTextArea displayArea = new JTextArea();
        displayArea.setEditable(false);

        StringBuilder displayText = new StringBuilder();
        for (Person person : personList) {
            displayText.append(person).append("\n");
        }
        displayArea.setText(displayText.toString());

        showAllFrame.add(new JScrollPane(displayArea), BorderLayout.CENTER);
        showAllFrame.setVisible(true);
    }

    private void saveAllPersonsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("persons.txt"))) {
            for (Person person : personList) {
                writer.write(person.toString());
                writer.newLine();
            }
            JOptionPane.showMessageDialog(this, "Data saved to persons.txt", "Save Successful", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving data to file", "Save Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            hw6 frame = new hw6();
            frame.setVisible(true);
        });
    }
}

// Person class to store data
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
*/



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

public class CursedNameForm extends JFrame {

    private JSlider letterSlider;
    private JButton setLetterButton, clearNameButton;
    private JLabel nameDisplayLabel;
    private StringBuilder nameBuilder;
    
    private JRadioButton maleButton, femaleButton;
    private JTextArea jobDescriptionArea;
    private JScrollBar experienceScrollBar;
    private JTextField experienceField;
    private JComboBox<String> locationComboBox;
    private JButton addButton, showAllButton, saveAllButton;

    private ArrayList<Person> personList = new ArrayList<>();

    public CursedNameForm() {
        // Frame setup
        setTitle("Cursed Name Form");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Name selection components
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Name"), gbc);

        letterSlider = new JSlider(JSlider.HORIZONTAL, 0, 25, 0);
        letterSlider.setMajorTickSpacing(1);
        letterSlider.setPaintTicks(true);
        letterSlider.setPaintLabels(true);
        letterSlider.setLabelTable(createLetterLabels());

        setLetterButton = new JButton("Set Letter");
        clearNameButton = new JButton("Clear Name");
        nameDisplayLabel = new JLabel("Name: ");
        nameBuilder = new StringBuilder();

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        add(letterSlider, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(setLetterButton, gbc);

        gbc.gridx = 2;
        add(clearNameButton, gbc);

        gbc.gridy = 2;
        gbc.gridx = 1;
        add(nameDisplayLabel, gbc);

        // Gender Radio Buttons
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Gender"), gbc);
        maleButton = new JRadioButton("Male");
        femaleButton = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
        JPanel genderPanel = new JPanel();
        genderPanel.add(maleButton);
        genderPanel.add(femaleButton);
        gbc.gridx = 1;
        add(genderPanel, gbc);

        // Job Description Text Area
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Job Description"), gbc);
        jobDescriptionArea = new JTextArea(4, 15);
        JScrollPane jobScrollPane = new JScrollPane(jobDescriptionArea);
        gbc.gridx = 1;
        add(jobScrollPane, gbc);

        // Experience Scroll Bar and Field
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

        // Location ComboBox
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(new JLabel("Location"), gbc);
        locationComboBox = new JComboBox<>(new String[]{"New York", "London", "Paris", "Tokyo", "Sydney"});
        gbc.gridx = 1;
        add(locationComboBox, gbc);

        // Add, Show All, and Save All Buttons
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

        // Button Actions
        setLetterButton.addActionListener(e -> setLetter());
        clearNameButton.addActionListener(e -> clearName());

        addButton.addActionListener(e -> addPerson());
        showAllButton.addActionListener(e -> showAllPersonsInFrame());
        saveAllButton.addActionListener(e -> saveAllPersonsToFile());
    }

    private void setLetter() {
        char selectedLetter = (char) ('A' + letterSlider.getValue());
        nameBuilder.append(selectedLetter);
        nameDisplayLabel.setText("Name: " + nameBuilder.toString());
    }

    private void clearName() {
        nameBuilder.setLength(0);
        nameDisplayLabel.setText("Name: ");
    }

    private void addPerson() {
        if (nameBuilder.length() == 0) {
            JOptionPane.showMessageDialog(this, "Please complete the name!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String name = nameBuilder.toString();
        String gender = maleButton.isSelected() ? "Male" : femaleButton.isSelected() ? "Female" : "";
        String jobDescription = jobDescriptionArea.getText();
        int experience = experienceScrollBar.getValue();
        String location = (String) locationComboBox.getSelectedItem();

        Person person = new Person(name, gender, jobDescription, experience, location);
        personList.add(person);

        // Clear form fields for next entry
        clearName();
        jobDescriptionArea.setText("");
        experienceScrollBar.setValue(0);
        locationComboBox.setSelectedIndex(0);
        maleButton.setSelected(false);
        femaleButton.setSelected(false);
    }

    private void showAllPersonsInFrame() {
        JFrame showAllFrame = new JFrame("All Persons");
        showAllFrame.setSize(400, 300);
        showAllFrame.setLayout(new BorderLayout());

        JTextArea displayArea = new JTextArea();
        displayArea.setEditable(false);

        StringBuilder displayText = new StringBuilder();
        for (Person person : personList) {
            displayText.append(person).append("\n");
        }
        displayArea.setText(displayText.toString());

        showAllFrame.add(new JScrollPane(displayArea), BorderLayout.CENTER);
        showAllFrame.setVisible(true);
    }

    private void saveAllPersonsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("persons.txt"))) {
            for (Person person : personList) {
                writer.write(person.toString());
                writer.newLine();
            }
            JOptionPane.showMessageDialog(this, "Data saved to persons.txt", "Save Successful", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving data to file", "Save Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private Hashtable<Integer, JLabel> createLetterLabels() {
        Hashtable<Integer, JLabel> labels = new Hashtable<>();
        for (int i = 0; i < 26; i++) {
            labels.put(i, new JLabel(String.valueOf((char) ('A' + i))));
        }
        return labels;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CursedNameForm frame = new CursedNameForm();
            frame.setVisible(true);
        });
    }
}

// Person class to store data
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
