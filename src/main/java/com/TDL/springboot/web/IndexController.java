package com.TDL.springboot.web;

import com.TDL.springboot.config.auth.LoginUser;
import com.TDL.springboot.config.auth.dto.SessionUser;
import com.TDL.springboot.service.PostsService;
import com.TDL.springboot.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        if (user != null) {
            return "redirect:new";
        } else {
            return "index";
        }
    }

    @GetMapping("/main")
    public String main(){
        return "main";
    }

   @GetMapping("/new")
    public String new2() {
        return "new";
    }

    @GetMapping("/memo")
    public String memo() {
        return "memo";
    }

    @GetMapping("/share")
    public String share() {
        return "share";
    }

    @GetMapping("/day")
    public String day() {
        return "day";
    }

    @GetMapping("/myPage")
    public String myPage(@LoginUser SessionUser sessionUser, Model model) {
        model.addAttribute("user", sessionUser);
        return "myPage";
    }
}
