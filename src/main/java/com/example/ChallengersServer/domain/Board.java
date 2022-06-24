package com.example.ChallengersServer.domain;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Board {
    private Long id;
    private String title;
    private String userid;
    private LocalDate date;
    private String content;

    public Board() {
    }

    public Board(String title, String userid, String content) {
        this.title = title;
        this.userid = userid;
        this.content = content;
        this.date = LocalDate.now();
    }
}



