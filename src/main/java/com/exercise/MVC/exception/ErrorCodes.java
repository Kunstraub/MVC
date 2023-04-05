package com.exercise.MVC.exception;

public enum ErrorCodes {
    USER_NOT_FOUND(1000),
    EXERCISE_NOT_FOUND(1001),
    USER_NOT_VALID(2000),
    EXERCISE_NOT_VALID(2001);


    private int code;

    ErrorCodes(int code){
       this.code = code;
    }

    public int getCode() {
        return code;
    }
}
