package com.TDL.springboot.web.dto.Memo;

import com.TDL.springboot.domain.memo.Memo;
import com.TDL.springboot.domain.memo.MemoTrash;
import com.TDL.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemoTrashResponseDto {
    private Long id;
    private String title;
    private String content;
    private User user;

    public MemoTrashResponseDto(MemoTrash entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.user = entity.getUser();
    }
}
