package ru.rybkin.alfa_bank.controllers;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rybkin.alfa_bank.services.RatesService;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "rates")
public class RatesController {

    private final RatesService ratesService;

    public RatesController(RatesService ratesService) {
        this.ratesService = ratesService;
    }

    @GetMapping("check")
    public ResponseEntity<ByteArrayResource> getReviseOfRates(@RequestParam String rate) {
        return ratesService.changeOfRate(rate);
    }


}
