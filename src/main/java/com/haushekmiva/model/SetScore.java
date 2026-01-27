package com.haushekmiva.model;

public class SetScore {

    private final int firstPlayerResult;
    private final int secondPlayerResult;


    public SetScore(int firstPlayerResult, int secondPlayerResult) {
        this.firstPlayerResult = firstPlayerResult;
        this.secondPlayerResult = secondPlayerResult;
    }

    public int getFirstPlayerResult() {
        return firstPlayerResult;
    }

    public int getSecondPlayerResult() {
        return secondPlayerResult;
    }
}
