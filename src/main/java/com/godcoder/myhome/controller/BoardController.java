package com.godcoder.myhome.controller;

import com.godcoder.myhome.model.Board;
import com.godcoder.myhome.repository.BoardRepository;
import com.godcoder.myhome.validator.BoardValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardValidator boardValidator;

    //게시판 열면 보이는 화면
    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 1) Pageable pageable, @RequestParam(required = false, defaultValue = "") String searchText){
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchText,searchText,pageable);
        int[] pageNum = pageNum(boards);
        model.addAttribute("startPage",pageNum[0]);
        model.addAttribute("endPage",pageNum[1]);
        model.addAttribute("boards",boards);
        log.info("searchText={}",searchText);
        return "board/list";
    }

    //index 0 - startNum / index 2 - endNum
    private int[] pageNum(Page<Board> boards) {
        int startPage,endPage;
        if(boards.getPageable().getPageNumber()>= boards.getTotalPages()-3 ){
            startPage= boards.getTotalPages()-4;
            endPage= boards.getTotalPages();
        }else{
            startPage= Math.max(1, boards.getPageable().getPageNumber()-1);
            endPage= Math.min(startPage+4, boards.getTotalPages());
        }

//        int startPage = boards.getPageable().getPageNumber()>=boards.getTotalPages()-3 ? boards.getTotalPages()-4:Math.max(1,boards.getPageable().getPageNumber()-1);
//        int endPage = boards.getPageable().getPageNumber()>=boards.getTotalPages()-3 ? boards.getTotalPages():Math.min(startPage+4,boards.getTotalPages());
        return  new int[]  {startPage, endPage};
    }

    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id){
        if(id == null){
        model.addAttribute("board",new Board());
        }else{
            Board board = boardRepository.findById(id).orElse(null);
            model.addAttribute("board",board);
        }

        return "board/form";
    }

    @PostMapping("/form")
    public String formSubmit(@Valid Board board, BindingResult bindingResult){
        boardValidator.validate(board, bindingResult);
        if (bindingResult.hasErrors()) {
            return "board/form";
        }
        boardRepository.save(board);
        return "redirect:/board/list";
    }
}
