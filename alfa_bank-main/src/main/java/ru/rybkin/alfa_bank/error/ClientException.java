package ru.rybkin.alfa_bank.error;

public class ClientException extends RuntimeException {
    public ClientException(int status, String reason) {
        super(status + " " + reason);
    }
}
