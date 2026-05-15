package ru.psu.coursework.additional.messaging;

public interface Commands {
    int REGISTRATION_REQUEST = 1;
    int LOGIN_REQUEST = 2;
    int AUTHENTICATION_SUCCESS = 3;
    int CREATE_ROOM = 4;
    int JOIN_ROOM = 5;
    int GAME_START = 6;
    int DICE_ROLL = 7;
    int MAKE_MOVE = 8;
    int UPDATE_BOARD = 9;
    int ERROR = 0;

}
