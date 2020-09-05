package com.TDL.springboot.web.dto.Memo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemoUpdateRequestDto {
    private Long id;
    private String title;
    private String content;

    @Builder
    public MemoUpdateRequestDto(String title, String content) {
        this.title  = title;
        this.content = content;
    }
}
