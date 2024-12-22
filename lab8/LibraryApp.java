import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

class Book implements Serializable {
    private String title;
    private String author;
    private String genre;
    private String isbn;
    private String shortDescription;

    public Book(String title, String author, String genre, String isbn, String shortDescription) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isbn = isbn;
        this.shortDescription = shortDescription;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getShortDescription() { return shortDescription; }
    public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s, %s", title, author, genre, isbn, shortDescription);
    }
}

class LibraryManager {
    private final String FILE_PATH = "books.csv";
    private List<Book> books;

    public LibraryManager() {
        books = new ArrayList<>();
        loadBooksFromCSV();
    }

    public void loadBooksFromCSV() {
        books.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 5) {
                    books.add(new Book(data[0], data[1], data[2], data[3], data[4]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading books: " + e.getMessage());
        }
    }

    public void saveBooksToCSV() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Book book : books) {
                bw.write(String.join(",", book.getTitle(), book.getAuthor(), book.getGenre(), book.getIsbn(), book.getShortDescription()));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving books: " + e.getMessage());
        }
    }

    public void addBook(Book book) {
        books.add(book);
        saveBooksToCSV();
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public List<Book> findBooks(String query) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(query.toLowerCase()) || book.getAuthor().toLowerCase().contains(query.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    public void editBook(String isbn, Book updatedBook) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getIsbn().equals(isbn)) {
                books.set(i, updatedBook);
                saveBooksToCSV();
                return;
            }
        }
    }

    public void deleteBook(String isbn) {
        books.removeIf(book -> book.getIsbn().equals(isbn));
        saveBooksToCSV();
    }
}

public class LibraryApp {
    private LibraryManager libraryManager;

    public LibraryApp() {
        libraryManager = new LibraryManager();
        initUI();
    }

    private void initUI() {
        JFrame frame = new JFrame("Library Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JButton addBookButton = new JButton("Add Book");
        JButton viewBooksButton = new JButton("View All Books");
        JButton findBooksButton = new JButton("Find Books");

        addBookButton.addActionListener(e -> openAddBookFrame());
        viewBooksButton.addActionListener(e -> openViewBooksFrame());
        findBooksButton.addActionListener(e -> openFindBooksFrame());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        addBookButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewBooksButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        findBooksButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(addBookButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(viewBooksButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(findBooksButton);
        

        frame.add(panel);
        frame.setVisible(true);
    }

    private void openAddBookFrame() {
        JFrame addBookFrame = new JFrame("Add Book");
        addBookFrame.setSize(400, 300);
        addBookFrame.setLayout(new GridLayout(6, 2));

        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        JTextField genreField = new JTextField();
        JTextField isbnField = new JTextField();
        JTextField descriptionField = new JTextField();

        addBookFrame.add(new JLabel("Title:"));
        addBookFrame.add(titleField);

        addBookFrame.add(new JLabel("Author:"));
        addBookFrame.add(authorField);

        addBookFrame.add(new JLabel("Genre:"));
        addBookFrame.add(genreField);

        addBookFrame.add(new JLabel("ISBN:"));
        addBookFrame.add(isbnField);

        addBookFrame.add(new JLabel("Description:"));
        addBookFrame.add(descriptionField);

        JButton addButton = new JButton("Add Book");
        addButton.addActionListener(e -> {
            Book book = new Book(titleField.getText(), authorField.getText(), genreField.getText(),
                    isbnField.getText(), descriptionField.getText());
            libraryManager.addBook(book);
            JOptionPane.showMessageDialog(addBookFrame, "Book added successfully!");
            addBookFrame.dispose();
        });

        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        addBookFrame.add(buttonPanel);
        addBookFrame.setVisible(true);
    }

    private void openViewBooksFrame() {
        JFrame viewBooksFrame = new JFrame("View All Books");
        viewBooksFrame.setSize(800, 600);

        DefaultTableModel model = new DefaultTableModel(new String[]{"Title", "Author", "Genre", "ISBN", "Description"}, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        for (Book book : libraryManager.getAllBooks()) {
            model.addRow(new Object[]{book.getTitle(), book.getAuthor(), book.getGenre(), book.getIsbn(), book.getShortDescription()});
        }

        JButton editButton = new JButton("Edit");
        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String isbn = (String) model.getValueAt(selectedRow, 3);
                openEditBookFrame(isbn);
                viewBooksFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(viewBooksFrame, "Please select a book to edit.");
            }
        });

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String isbn = (String) model.getValueAt(selectedRow, 3);
                libraryManager.deleteBook(isbn);
                model.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(viewBooksFrame, "Please select a book to delete.");
            }
        });

        JPanel panel = new JPanel();
        panel.add(editButton);
        panel.add(deleteButton);

        viewBooksFrame.add(scrollPane, BorderLayout.CENTER);
        viewBooksFrame.add(panel, BorderLayout.SOUTH);
        viewBooksFrame.setVisible(true);
    }

    private void openEditBookFrame(String isbn) {
        Book book = libraryManager.getAllBooks().stream().filter(b -> b.getIsbn().equals(isbn)).findFirst().orElse(null);
        if (book == null) return;

        JFrame editBookFrame = new JFrame("Edit Book");
        editBookFrame.setSize(400, 300);
        editBookFrame.setLayout(new GridLayout(6, 2));

        JTextField titleField = new JTextField(book.getTitle());
        JTextField authorField = new JTextField(book.getAuthor());
        JTextField genreField = new JTextField(book.getGenre());
        JTextField isbnField = new JTextField(book.getIsbn());
        isbnField.setEditable(false);
        JTextField descriptionField = new JTextField(book.getShortDescription());

        editBookFrame.add(new JLabel("Title:"));
        editBookFrame.add(titleField);

        editBookFrame.add(new JLabel("Author:"));
        editBookFrame.add(authorField);

        editBookFrame.add(new JLabel("Genre:"));
        editBookFrame.add(genreField);

        editBookFrame.add(new JLabel("ISBN:"));
        editBookFrame.add(isbnField);

        editBookFrame.add(new JLabel("Description:"));
        editBookFrame.add(descriptionField);

        JButton saveButton = new JButton("Save Changes");
        saveButton.addActionListener(e -> {
            Book updatedBook = new Book(titleField.getText(), authorField.getText(), genreField.getText(),
                    isbnField.getText(), descriptionField.getText());
            libraryManager.editBook(isbn, updatedBook);
            JOptionPane.showMessageDialog(editBookFrame, "Book updated successfully!");
            editBookFrame.dispose();
        });

        editBookFrame.add(saveButton);
        editBookFrame.setVisible(true);
    }

    private void openFindBooksFrame() {
        JFrame findBooksFrame = new JFrame("Find Books");
        findBooksFrame.setSize(800, 600);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel searchLabel = new JLabel("Search by Title:");
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        DefaultTableModel model = new DefaultTableModel(new String[]{"Title", "Author", "Genre", "ISBN", "Description"}, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        searchButton.addActionListener(e -> {
            model.setRowCount(0);
            List<Book> results = libraryManager.findBooks(searchField.getText());
            for (Book book : results) {
                model.addRow(new Object[]{book.getTitle(), book.getAuthor(), book.getGenre(), book.getIsbn(), book.getShortDescription()});
            }
        });

        findBooksFrame.add(searchPanel, BorderLayout.NORTH);
        findBooksFrame.add(scrollPane, BorderLayout.CENTER);

        findBooksFrame.setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(LibraryApp::new);
    }
}
