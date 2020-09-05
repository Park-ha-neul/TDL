package com.TDL.springboot.web;

import com.TDL.springboot.config.auth.LoginUser;
import com.TDL.springboot.config.auth.dto.SessionUser;
import com.TDL.springboot.service.PostsService;
import com.TDL.springboot.web.dto.Posts.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RequiredArgsConstructor
@Controller
public class springbootController {
    private final PostsService postsService;

    //게시글 조회
   @GetMapping("/springboot")
   public String index(Model model, @LoginUser SessionUser user){
       model.addAttribute("posts", postsService.findAllDesc());
       if (user != null) {
          model.addAttribute("userName", user.getName());
        }

     return "springboot";
  }

    //게시글 등록
    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    //게시글 수정
    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {

        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
