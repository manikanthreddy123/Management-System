package libraymanagementsystem;

import java.util.List;
import java.util.Scanner;

public class LibraryManagement {
    private LibraryDatabaseActions libraryDB;

    public LibraryManagement() {
           libraryDB = new LibraryDatabaseActions();
    }

        public void proceed() throws NotUniqueBookException,  BookNotFoundException , InvalidISBNException{
    	Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Library Management System :");
            System.out.println("1. To add a book");
            System.out.println("2. To display all books");
            System.out.println("3. To update book details");
            System.out.println("4. To delete a book");
            System.out.println("5. To exit from it");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    // To add a book
                    System.out.println("Enter the book details:");
                    Details newBook = new Details();
                    System.out.print("Title: ");
                    newBook.setTitle(scanner.nextLine());
                    System.out.print("Author: ");
                    newBook.setAuthor(scanner.nextLine());
                    System.out.print("ISBN: ");
                    newBook.setIsbn(scanner.nextLine());
                    System.out.print("Publication Year: ");
                    newBook.setPublicationYear(scanner.nextInt());
                    scanner.nextLine(); 
                    System.out.print("Genre: ");
                    newBook.setGenre(scanner.nextLine());
                    libraryDB.addBook(newBook);
                    System.out.println("Book is 1added successfully!");
                    break;

                case 2:
                    // To display all the books
                    List<Details> d = libraryDB.getAllBooks();
                    if (d.isEmpty()) {
                        System.out.println("The library is empty.");
                    } else {
                        System.out.println("List of all books in the library:");
                        for (Details details : d) {
                        System.out.println(details.getTitle());
                        }
                    }
                    break;

                case 3:
                    // To update book details
                    System.out.println("Enter the ISBN of the book you want to edit:");
                    String isbnToEdit = scanner.nextLine();
                    Details updatedBook = new Details();
                    updatedBook.setIsbn(isbnToEdit);
                    System.out.print("New Title: ");
                    updatedBook.setTitle(scanner.nextLine());
                    System.out.print("New Author: ");
                    updatedBook.setAuthor(scanner.nextLine());
                    System.out.print("New Publication Year: ");
                    updatedBook.setPublicationYear(scanner.nextInt());
                    scanner.nextLine(); // Consume the newline character
                    System.out.print("New Genre: ");
                    updatedBook.setGenre(scanner.nextLine());
                    libraryDB.newBook(isbnToEdit, updatedBook);
                    System.out.println("Book details updated successfully!");
                    break;

                case 4:
                    // To delete a book
                    System.out.println("Enter the ISBN of the book you want to delete:");
                    String isbnToDelete = scanner.nextLine();
                    libraryDB.deleteBook(isbnToDelete);
                    System.out.println("Book deleted successfully!");
                    break;

                case 5:
                    libraryDB.closeConnection();
                    System.out.println("Exiting the program.");
                   // System.exit(0);
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

        public static void main(String[] args) {
       
    	LibraryManagement lm = new LibraryManagement();
        try {
            lm.proceed();
        } catch (NotUniqueBookException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InvalidISBNException e)   {
            System.out.println("Error: " + e.getMessage());
        } catch (BookNotFoundException e)  {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
