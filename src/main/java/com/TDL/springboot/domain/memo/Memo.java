package com.TDL.springboot.domain.memo;

import com.TDL.springboot.domain.BaseTimeEntity;
import com.TDL.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Memo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name="USER_id", nullable = false)
    private User user;

    @Builder
    public Memo(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    @Builder
    public Memo(MemoTrash entity) {
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.user = entity.getUser();
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
