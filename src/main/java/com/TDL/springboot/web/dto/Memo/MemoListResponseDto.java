package com.TDL.springboot.web.dto.Memo;

import com.TDL.springboot.domain.memo.Memo;
import com.TDL.springboot.domain.user.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemoListResponseDto {
    private Long id;
    private String title;
    private User user;
    private LocalDateTime modifiedDate;

    public MemoListResponseDto(Memo entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.user = entity.getUser();
        this.modifiedDate = entity.getModifiedDate();
    }
}
