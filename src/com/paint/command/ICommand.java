package com.paint.command;

public interface ICommand {
    void execute();
    void undo();
}