package com.TDL.springboot.web.dto.Memo;

import com.TDL.springboot.domain.memo.Memo;
import com.TDL.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemoSaveRequestDto {
    private String title;
    private String content;
    private User user;

    @Builder
    public MemoSaveRequestDto(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public Memo toEntity() {
        return Memo.builder()
                .title(title)
                .content(content)
                .user(user)
                .build();
    }
}
