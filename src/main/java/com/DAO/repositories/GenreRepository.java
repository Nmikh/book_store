package com.DAO.repositories;

import com.models.entities.GenreEntity;
import com.models.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface GenreRepository extends JpaRepository<GenreEntity, Long> {

    @Modifying
    @Query(value = "DELETE FROM BOOK_GENRE WHERE ID_BOOK = ?1 AND ID_GENRE = ?2", nativeQuery = true)
    void deleteGenreInBook(int id_book, int id_genre);

    @Modifying
    @Query(value = "INSERT INTO BOOK_GENRE (ID_BOOK,ID_GENRE) VALUES (?1,?2) ", nativeQuery = true)
    void saveGenreInBook(int id_book, int id_genre);
}

