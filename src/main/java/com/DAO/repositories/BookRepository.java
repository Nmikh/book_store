package com.DAO.repositories;

import com.models.entities.AuthorEntity;
import com.models.entities.BookEntity;
import com.models.entities.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findById(@Param("id") int id);

    List<BookEntity> findByAuthors(@Param("author") AuthorEntity author);

    List<BookEntity> findByGenres(@Param("genre") GenreEntity genreEntity);

    @Modifying
    @Query("update BookEntity u set u.name = ?1, u.description = ?2, u.price = ?3 where u.id = ?4")
    void updateBook(String name, String desc, double price, int id_book);
}
