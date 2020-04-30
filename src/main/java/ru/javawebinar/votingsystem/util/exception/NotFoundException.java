package ru.javawebinar.votingsystem.util.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String entityName, int id) {
        super(String.format("Not found %s with id = %d", entityName, id));
    }
}
