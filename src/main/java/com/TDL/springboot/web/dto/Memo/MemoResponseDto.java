package com.TDL.springboot.web.dto.Memo;

import com.TDL.springboot.domain.memo.Memo;
import com.TDL.springboot.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemoResponseDto {
    private Long id;
    private String title;
    private String content;
    private User user;

    public MemoResponseDto(Memo entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.user = entity.getUser();
    }
}

