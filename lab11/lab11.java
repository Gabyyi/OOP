import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class lab11{
    private static final String FILE_NAME="persons.json";

    static class Person{
        String name, surname, gender, city, country;
        int age;

        public Person(String name, String surname, String gender, int age, String city, String country) {
            this.name=name;
            this.surname=surname;
            this.gender=gender;
            this.age=age;
            this.city=city;
            this.country=country;
        }

        public String toJson(){
            return "{\n" +
                   "  \"name\": \"" + name + "\",\n" +
                   "  \"surname\": \"" + surname + "\",\n" +
                   "  \"gender\": \"" + gender + "\",\n" +
                   "  \"age\": " + age + ",\n" +
                   "  \"city\": \"" + city + "\",\n" +
                   "  \"country\": \"" + country + "\"\n" +
                   "}";
        }

        public static Person fromJson(String json){
            try{
                json=json.trim().replace("{", "").replace("}", "").trim();
                String[] parts=json.split(",\\s*");
                if(parts.length!=6){
                    System.out.println("Invalid JSON format, skipping this entry: "+json);
                    return null;
                }
        
                String name=parts[0].split(":")[1].replace("\"", "").trim();
                String surname=parts[1].split(":")[1].replace("\"", "").trim();
                String gender=parts[2].split(":")[1].replace("\"", "").trim();
                int age=Integer.parseInt(parts[3].split(":")[1].trim());
                String city=parts[4].split(":")[1].replace("\"", "").trim();
                String country=parts[5].split(":")[1].replace("\"", "").trim();
        
                return new Person(name, surname, gender, age, city, country);
            }catch(Exception e){
                System.out.println("Error parsing JSON: "+e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
    }

    public static void main(String[] args){
        JFrame frame=new JFrame("Person Form");
        frame.setSize(350, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel nameLabel=new JLabel("Name:");
        nameLabel.setBounds(20, 20, 80, 25);
        JTextField nameField=new JTextField();
        nameField.setBounds(100, 20, 150, 25);

        JLabel surnameLabel=new JLabel("Surname:");
        surnameLabel.setBounds(20, 60, 80, 25);
        JTextField surnameField=new JTextField();
        surnameField.setBounds(100, 60, 150, 25);

        JLabel genderLabel=new JLabel("Gender:");
        genderLabel.setBounds(20, 100, 80, 25);
        JRadioButton maleButton=new JRadioButton("M");
        maleButton.setBounds(100, 100, 40, 25);
        JRadioButton femaleButton=new JRadioButton("F");
        femaleButton.setBounds(150, 100, 40, 25);
        ButtonGroup genderGroup=new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);

        JLabel ageLabel=new JLabel("Age:");
        ageLabel.setBounds(20, 140, 80, 25);
        JScrollBar ageScrollBar=new JScrollBar(JScrollBar.HORIZONTAL, 1, 1, 1, 100);
        ageScrollBar.setBounds(100, 140, 100, 20);
        JTextField ageField=new JTextField("1");
        ageField.setBounds(210, 140, 30, 25);
        ageField.setEditable(false);

        JLabel cityLabel=new JLabel("City:");
        cityLabel.setBounds(20, 180, 80, 25);
        JTextField cityField=new JTextField();
        cityField.setBounds(100, 180, 150, 25);

        JLabel countryLabel=new JLabel("Country:");
        countryLabel.setBounds(20, 220, 80, 25);
        String[] countries={"Romania", "France", "UK", "Poland", "Germany"};
        JComboBox<String> countryBox=new JComboBox<>(countries);
        countryBox.setBounds(100, 220, 150, 25);

        JButton addButton=new JButton("Add");
        addButton.setBounds(50, 270, 80, 30);
        JButton showAllButton=new JButton("ShowAll");
        showAllButton.setBounds(150, 270, 100, 30);

        ageScrollBar.addAdjustmentListener(e -> ageField.setText(String.valueOf(e.getValue())));

        frame.add(nameLabel);
        frame.add(nameField);
        frame.add(surnameLabel);
        frame.add(surnameField);
        frame.add(genderLabel);
        frame.add(maleButton);
        frame.add(femaleButton);
        frame.add(ageLabel);
        frame.add(ageScrollBar);
        frame.add(ageField);
        frame.add(cityLabel);
        frame.add(cityField);
        frame.add(countryLabel);
        frame.add(countryBox);
        frame.add(addButton);
        frame.add(showAllButton);

        addButton.addActionListener(e -> {
            String name=nameField.getText();
            String surname=surnameField.getText();
            String gender=maleButton.isSelected() ? "M" : "F";
            int age=Integer.parseInt(ageField.getText());
            String city=cityField.getText();
            String country=(String) countryBox.getSelectedItem();

            Person person=new Person(name, surname, gender, age, city, country);
            writePersonToFile(person);
            JOptionPane.showMessageDialog(frame, "Person added successfully!");
        });

        showAllButton.addActionListener(e -> {
            List<Person> people=readPersonsFromFile();
            showAllPersons(people);
        });

        frame.setVisible(true);
    }

    private static void writePersonToFile(Person person){
        try(FileWriter writer=new FileWriter(FILE_NAME, true)){
            writer.write(person.toJson() + "\n");
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    private static List<Person> readPersonsFromFile(){
        List<Person> people=new ArrayList<>();
        try(BufferedReader reader=new BufferedReader(new FileReader(FILE_NAME))){
            String line;
            StringBuilder jsonBuilder=new StringBuilder();
            while((line=reader.readLine())!=null){
                line=line.trim();
                if(line.isEmpty()) continue;
                jsonBuilder.append(line);
                if(line.endsWith("}")){
                    String json=jsonBuilder.toString();
                    Person person=Person.fromJson(json);
                    if(person!=null){
                        people.add(person);
                    }
                    jsonBuilder.setLength(0);
                }
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
        return people;
    }

    private static void showAllPersons(List<Person> people){
        JFrame showFrame=new JFrame("All Persons");
        showFrame.setSize(300, 400);
    
        JTextArea textArea=new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
    
        for(Person p : people){
            textArea.append(p.toJson()+"\n\n");
        }
    
        JScrollPane scrollPane=new JScrollPane(textArea);
        showFrame.add(scrollPane);
        showFrame.setVisible(true);
    }
}
