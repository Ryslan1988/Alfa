package ru.rybkin.alfa_bank.error;

public class ServerException extends RuntimeException {
    public ServerException(int status, String reason) {
        super(status + reason);
    }
}
