package com.TDL.springboot.domain.memo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemoTrashRepository extends JpaRepository<MemoTrash, Long> {

    @Query(value = "select * from memo_trash WHERE user_id = :id order by id DESC LIMIT :start,5", nativeQuery = true)
    List<MemoTrash> getMemoListTenByUser(Long id, int start);
}
