package com.TDL.springboot.service;

import com.TDL.springboot.config.auth.dto.SessionUser;
import com.TDL.springboot.domain.memo.Memo;
import com.TDL.springboot.domain.memo.MemoRepository;
import com.TDL.springboot.domain.memo.MemoTrash;
import com.TDL.springboot.domain.memo.MemoTrashRepository;
import com.TDL.springboot.domain.user.User;
import com.TDL.springboot.domain.user.UserRepository;
import com.TDL.springboot.web.dto.Memo.MemoResponseDto;
import com.TDL.springboot.web.dto.Memo.MemoSaveRequestDto;
import com.TDL.springboot.web.dto.Memo.MemoTrashResponseDto;
import com.TDL.springboot.web.dto.Memo.MemoUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemoService {
    private final MemoRepository memoRepository;
    private final UserRepository userRepository;
    private final MemoTrashRepository memoTrashRepository;

    //저장
    @Transactional
    public Long save(MemoSaveRequestDto requestDto,  SessionUser sessionUser) {
        User user = userRepository.findByEmail(sessionUser.getEmail()).get();
        requestDto.setUser(user);
        return memoRepository.save(requestDto.toEntity()).getId();
    }

    //수정
    @Transactional
    public Long update(MemoUpdateRequestDto requestDto) {
        Long id = requestDto.getId();
        Memo memo = memoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));
        memo.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    //삭제
    @Transactional
    public Long delete(Long id){
        Memo memo = memoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" +id));
        MemoTrash memoTrash = new MemoTrash(memo);
        memoRepository.delete(memo);
        Long trashId = memoTrashRepository.save(memoTrash).getId();
        return trashId;
    }

    @Transactional(readOnly = true)
    public MemoResponseDto findById(Long id) {
        Memo entity = memoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        return new MemoResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<MemoResponseDto> findAllDesc() {
        return memoRepository.findAllDesc().stream()
                .map(MemoResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<Memo> getMemoListTen(SessionUser sessionUser, int page) {
        User user = userRepository.findByEmail(sessionUser.getEmail()).get();

        // 한 페이지당 10개의 글을 가져오므로 가져올 게시물 시작 번호는 페이지 X 10으로 구합니다.
        int start = page * 5;

        // 그리고 이번엔 Query문을 직접 써서 ArrayList 형태의 Memo 리스트를 불러옵니다.
        return memoRepository.getMemoListTenByUser(user.getId(), start);
    }
    @Transactional
    public List<MemoTrash> getMemoTrashListTen(SessionUser sessionUser, int page) {
        User user = userRepository.findByEmail(sessionUser.getEmail()).get();

        // 한 페이지당 10개의 글을 가져오므로 가져올 게시물 시작 번호는 페이지 X 10으로 구합니다.
        int start = page * 5;

        // 그리고 이번엔 Query문을 직접 써서 ArrayList 형태의 Memo 리스트를 불러옵니다.
        return memoTrashRepository.getMemoListTenByUser(user.getId(), start);
    }
    @Transactional
    public MemoTrashResponseDto trashFindById(Long id) {
        MemoTrash entity = memoTrashRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        return new MemoTrashResponseDto(entity);
    }
    @Transactional
    public void trashDelete(Long id) {
        MemoTrash memo = memoTrashRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" +id));
        memoTrashRepository.delete(memo);
    }
    @Transactional
    public Long restoreTrash(Long id) {
        MemoTrash memoTrash = memoTrashRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" +id));
        Memo memo = new Memo(memoTrash);
        memoTrashRepository.delete(memoTrash);
        Long restoreId = memoRepository.save(memo).getId();
        return restoreId;
    }
}
