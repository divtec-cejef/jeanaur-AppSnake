package com.example.appsnake.models;

public class RangeInvalide extends RuntimeException{

    public RangeInvalide() {
        super("The ranking cursor is invalid. It must contain the following columns: player, score");
    }
}
