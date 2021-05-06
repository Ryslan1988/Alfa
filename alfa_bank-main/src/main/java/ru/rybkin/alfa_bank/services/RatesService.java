package ru.rybkin.alfa_bank.services;

import org.springframework.http.ResponseEntity;

public interface RatesService {

    ResponseEntity changeOfRate(String rate);
}
