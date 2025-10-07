package edu.trincoll.repository;

import edu.trincoll.model.Book;
import edu.trincoll.model.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIsbn(String isbn);

    List<Book> findByStatus(BookStatus status);

    List<Book> findByAuthor(String author);

    List<Book> findByTitleContainingIgnoreCase(String title);

    List<Book> findByDueDateBefore(LocalDate date);

    List<Book> findByCheckedOutBy(String memberEmail);

    long countByStatus(BookStatus status);
}
