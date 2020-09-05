package com.TDL.springboot.web;

import com.TDL.springboot.config.auth.LoginUser;
import com.TDL.springboot.config.auth.dto.SessionUser;
import com.TDL.springboot.domain.memo.Memo;
import com.TDL.springboot.domain.memo.MemoTrash;
import com.TDL.springboot.service.MemoService;
import com.TDL.springboot.service.WeatherService;
import com.TDL.springboot.web.dto.Memo.MemoResponseDto;
import com.TDL.springboot.web.dto.Memo.MemoSaveRequestDto;
import com.TDL.springboot.web.dto.Memo.MemoTrashResponseDto;
import com.TDL.springboot.web.dto.Memo.MemoUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class MemoApiController {

    private final MemoService memoService;

    //등록
    @PostMapping("/api/v1/memo")
    public Long save(@RequestBody MemoSaveRequestDto requestDto, @LoginUser SessionUser sessionUser) {
        return memoService.save(requestDto, sessionUser);
    }

    //수정
    @PatchMapping("/api/v1/memo")
    public Long update(@RequestBody MemoUpdateRequestDto requestDto) {
        return memoService.update(requestDto);
    }

    //삭제
    @DeleteMapping("/api/v1/memo/{id}")
    public Long delete(@PathVariable Long id) {
        return memoService.delete(id);
    }

    //조회
    @GetMapping("/api/v1/memo")
    public List<Memo> getMemoListTen(@LoginUser SessionUser sessionUser, @RequestParam("page") int page) {
        return memoService.getMemoListTen(sessionUser, page);
    }
    //조회
    @GetMapping("/api/v1/memo/{id}")
    public MemoResponseDto findById(@PathVariable Long id) {
        return memoService.findById(id);
    }

    //쓰레기조회
    @GetMapping("/api/v1/trash")
    public List<MemoTrash> getMemoTrashListTen(@LoginUser SessionUser sessionUser, @RequestParam("page") int page) {
        return memoService.getMemoTrashListTen(sessionUser, page);
    }
    //쓰레기 단일조회
    @GetMapping("/api/v1/trash/{id}")
    public MemoTrashResponseDto trashFindById(@PathVariable Long id) {
        return memoService.trashFindById(id);
    }
    // 쓰레기복원
    @GetMapping("/api/v1/trash/restore/{id}")
    public Long restoreTrash(@PathVariable Long id) {
        return memoService.restoreTrash(id);
    }

    //삭제
    @DeleteMapping("/api/v1/trash/{id}")
    public void trashDelete(@PathVariable Long id) {
        memoService.trashDelete(id);
    }
}
