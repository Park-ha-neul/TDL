package com.TDL.springboot.domain.memo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> {

    @Query("SELECT p FROM Memo p ORDER BY p.id DESC")
    List<Memo> findAllDesc();

    @Query(value = "select * from memo WHERE user_id = :id order by id DESC LIMIT :start,5", nativeQuery = true)
    List<Memo> getMemoListTenByUser(Long id, int start);
}
