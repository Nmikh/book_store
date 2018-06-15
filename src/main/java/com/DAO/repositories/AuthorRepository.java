package com.DAO.repositories;

import com.models.entities.AuthorEntity;
import com.models.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {
    @Modifying
    @Query(value = "DELETE FROM BOOK_AUTHOR WHERE ID_BOOK = ?1 AND ID_AUTHOR = ?2", nativeQuery = true)
    void deleteAuthInBook(int id_book, int id_auth);

    @Modifying
    @Query(value = "INSERT INTO BOOK_AUTHOR (ID_BOOK,ID_AUTHOR) VALUES (?1,?2) ", nativeQuery = true)
    void saveAuthInBook(int id_book, int id_auth);
}
