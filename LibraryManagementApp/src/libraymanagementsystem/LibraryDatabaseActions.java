package libraymanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibraryDatabaseActions {
	   private Connection connection;

	       public LibraryDatabaseActions() {
		   try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarydb","root","Sunny@123");
		    } catch (SQLException e) {
			System.out.println(e);
		}
	}
	       public void addBook(Details d) throws NotUniqueBookException, InvalidISBNException {
		    try {
			if (!isIsbnValid(d.getIsbn())) {
				throw new InvalidISBNException("IYou have entered invalid isbn, Please enter a valid ISBN.");
			}
			if (!isIsbnUnique(d.getIsbn())) {
				throw new NotUniqueBookException("It has already exists.  Use a different ISBN.");
			}

			String sql = "INSERT INTO books (title, author, isbn, publication_year, genre) VALUES (?, ?, ?, ?, ?)";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setString(1, d.getTitle());
				preparedStatement.setString(2, d.getAuthor());
				preparedStatement.setString(3, d.getIsbn());
				preparedStatement.setInt   (4, d.getPublicationYear());
				preparedStatement.setString(5, d.getGenre());
				preparedStatement.executeUpdate();
			}
		}   catch (SQLException e) {
			System.out.println(e);
			
		}
	}
	
	     public void newBook(String isbn, Details dt) throws NotUniqueBookException, InvalidISBNException, BookNotFoundException {
		 System.out.println("update method isbnToEdit: "+isbn);
		 System.out.println(isbn);
		
		try {
			
			  if (!isIsbnValid(dt.getIsbn())) {
			  System.out.println(dt.getIsbn()); 
			    throw new InvalidISBNException("Invalid ISBN. Please enter a valid ISBN."); }
			  
		        if (!doesBookExist(isbn)) {
		            throw new BookNotFoundException("Book with ISBN " + isbn + " not found. Cannot edit.");
		        }

			String sql = "UPDATE books SET title = ?, author = ?, publication_year = ?, genre = ? WHERE isbn = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setString(1, dt.getTitle());
				preparedStatement.setString(2, dt.getAuthor());
				preparedStatement.setInt   (3, dt.getPublicationYear());
				preparedStatement.setString(4, dt.getGenre());
				preparedStatement.setString(5, isbn);
				preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	    public List<Details> getAllBooks() {
		List<Details> details = new ArrayList<>();
		String sql = "select * from books";
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
				ResultSet resultSet = preparedStatement.executeQuery()) {
			while (resultSet.next()) {
				Details dtl = new Details();
				dtl.setId(resultSet.getInt("id"));
				dtl.setTitle(resultSet.getString("title"));
				dtl.setAuthor(resultSet.getString("author"));
				dtl.setIsbn(resultSet.getString("isbn"));
				dtl.setPublicationYear(resultSet.getInt("publication_year"));
				dtl.setGenre(resultSet.getString("genre"));
				details.add(dtl);
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return details;
	}

	    public void deleteBook(String isbn) throws BookNotFoundException {
		
        if (!doesBookExist(isbn)) {
            throw new BookNotFoundException("Book with ISBN " + isbn + " is not found. it cannot delete.");
        }
		String sql = "DELETE FROM books WHERE isbn = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, isbn);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	
	public void closeConnection() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	
		public boolean isIsbnUnique(String isbn) {
			String sql = "SELECT COUNT(*) FROM books WHERE isbn = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setString(1, isbn);
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					if (resultSet.next()) {
						int count = resultSet.getInt(1);
						return count == 0;
					}
				}
			} catch (SQLException e) {
				System.out.println(e);
			}
			return false; 
		}
		
	  public boolean isIsbnValid(String isbn) throws InvalidISBNException {
		System.out.println();
		if (isbn.length() != 13 || !isbn.matches("\\d{13}")) {
			throw new InvalidISBNException("It is invalid ISBN. It must be a 13-digit number.");
		}
		return true;
	}
	
	public boolean doesBookExist(String isbn) {
	    String sql = "SELECT COUNT(*) FROM books WHERE isbn = ?";
	    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	        preparedStatement.setString(1, isbn);
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            if (resultSet.next()) {
	                int count = resultSet.getInt(1);
	                  return count > 0;
	            }
	        }
	    } catch (SQLException e) {
	       System.out.println(e);
	    }
	    return false; 
	}
}
