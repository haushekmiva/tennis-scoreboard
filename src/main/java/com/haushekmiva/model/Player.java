package com.haushekmiva.model;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "firstPlayer", fetch = FetchType.LAZY)
    private List<Match> firstPlayerMatches = new ArrayList<>();

    @OneToMany(mappedBy = "secondPlayer", fetch = FetchType.LAZY)
    private List<Match> secondPlayerMatches = new ArrayList<>();

    @OneToMany(mappedBy = "winner", fetch = FetchType.LAZY)
    private List<Match> winnerMatches = new ArrayList<>();

    public Player() {
    }

    public Player(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
