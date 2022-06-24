package com.example.ChallengersServer.web.basic;

import com.example.ChallengersServer.domain.Board;
import com.example.ChallengersServer.domain.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/basic/Boards")
public class ChallengersController {

    private final BoardRepository boardRepository;

    @Autowired
    public ChallengersController(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @GetMapping //게시판 목록
    public String boards(Model model) {
        List<Board> boards = boardRepository.findAll();
        model.addAttribute("boards", boards);
        return "basic/Boards";
    }

    @GetMapping("/{boardId}") //게시글 내용
    public String board(@PathVariable long boardId, Model model) {
        Board board = boardRepository.findById(boardId);
        model.addAttribute("board", board);
        return "basic/Board";
    }

    @GetMapping("/add") //글작성 화면
    public String addBoard() {
        return "basic/addForm";
    }

    @PostMapping("/add") //글작성 데이터 넘김
    public String addBoard(Board board, RedirectAttributes redirectAttributes) {
        board.setDate(LocalDate.now());
        Board savedBoard = boardRepository.save(board);
        redirectAttributes.addAttribute("boardId", savedBoard.getId());

        return "redirect:/basic/Boards/{boardId}"; //글 상세로 이동
    }

    @GetMapping("/{boardId}/edit") //게시글 수정 요청
    public String editForm(@PathVariable Long boardId, Model model) {
        Board board = boardRepository.findById(boardId);
        model.addAttribute("board", board);
        return "basic/editForm";
    }

    @PostMapping("/{boardId}/edit") //게시글 수정 Post
    public String edit(@PathVariable Long boardId, @ModelAttribute Board board) {
        boardRepository.update(boardId, board);
        return "redirect:/basic/Boards/{boardId}"; //수정후에 게시글로 이동
    }


    @PostConstruct //테스트용 데이터
    public void init() {
        boardRepository.save(new Board("First","whipbaek","게시글 내용입니다."));
        boardRepository.save(new Board("second","JongIn","반갑습니다"));
    }
}
