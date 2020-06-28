package ru.javawebinar.votingsystem.util.exception;

public class CannotChangeVoteException extends RuntimeException {
    public CannotChangeVoteException(String message) {
        super(message);
    }

    public CannotChangeVoteException() {
    }
}
