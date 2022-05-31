package com.godcoder.myhome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class BoardController {

    //게시판 열면 보이는 화면
    @GetMapping("/list")
    public String list(){
        return "board/list";
    }
}
