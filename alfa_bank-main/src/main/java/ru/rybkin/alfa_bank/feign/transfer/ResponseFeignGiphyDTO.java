package ru.rybkin.alfa_bank.feign.transfer;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Map;

@Data
@NoArgsConstructor
public class ResponseFeignGiphyDTO {

    private ArrayList<OneGifDTO> data;
    private Map<String, Integer> pagination;
    private Map<String, String> meta;
}
