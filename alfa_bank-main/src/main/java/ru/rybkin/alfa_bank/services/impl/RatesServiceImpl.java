package ru.rybkin.alfa_bank.services.impl;

import feign.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.rybkin.alfa_bank.SystemUtils;
import ru.rybkin.alfa_bank.feign.DownloadGIF;
import ru.rybkin.alfa_bank.feign.GifFeignClient;
import ru.rybkin.alfa_bank.feign.RatesFeignClient;
import ru.rybkin.alfa_bank.feign.transfer.OneGifDTO;
import ru.rybkin.alfa_bank.feign.transfer.ResponseFeignGiphyDTO;
import ru.rybkin.alfa_bank.feign.transfer.ResponseFeignRatesDTO;
import ru.rybkin.alfa_bank.services.RatesService;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class RatesServiceImpl implements RatesService {

    private final RatesFeignClient ratesFeignClient;
    private final GifFeignClient gifFeignClient;
    private final DownloadGIF downloadGIF;

    @Value("${app_id}")
    private String appId;
    @Value("${api_key}")
    private String api_key;
    @Value("${resource}")
    private String resource;
    @Value("${endpoint}")
    private String endpoing;
    @Value("${rating}")
    private String rating;
    @Value("${lang}")
    private String lang;
    @Value("${limit}")
    private Integer limit;
    @Value("${offset}")
    private Integer offset;


    public RatesServiceImpl(RatesFeignClient ratesFeignClient, GifFeignClient gifFeignClient, DownloadGIF downloadGIF) {
        this.ratesFeignClient = ratesFeignClient;
        this.gifFeignClient = gifFeignClient;
        this.downloadGIF = downloadGIF;
    }

    @Override
    public ResponseEntity changeOfRate(String rate) {

        Calendar calendar = new GregorianCalendar();
        calendar.roll(Calendar.DAY_OF_MONTH, -1);
        String historical;
        try {
            historical = SystemUtils.historicalDate(calendar);
        } catch (NullPointerException ex) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        ResponseFeignRatesDTO responseHistorical = ratesFeignClient.getHistorical(historical, appId);
        ResponseFeignRatesDTO responseLatest = ratesFeignClient.getLatest(appId);
        
        Map<String, Double> yesterdayRates = responseHistorical.getRates();
        Map<String, Double> currentRates = responseLatest.getRates();

        ResponseFeignGiphyDTO responseGIF;
        if (currentRates.get(rate) >= yesterdayRates.get(rate)) {
            String rich = "rich";
            responseGIF = gifFeignClient.getGiphy(resource, endpoing, api_key, rich, limit, offset, rating, lang);
        } else {
            String broke = "broke";
            responseGIF = gifFeignClient.getGiphy(resource, endpoing, api_key, broke, limit, offset, rating, lang);
        }

        ArrayList<OneGifDTO> arrayGifs = responseGIF.getData();
        if (arrayGifs.isEmpty()) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        OneGifDTO oneGif;
        try {
            oneGif = arrayGifs.get(new Random().nextInt(10));
        } catch (IndexOutOfBoundsException ex) {
            oneGif = arrayGifs.get(0);
        }

        //String url = SystemUtils.getURL(oneGif, "original", "mp4");

        Response.Body body = downloadGIF
                .downloadGIF(oneGif.getId()/*oneGif.id*/, oneGif.getType()/*oneGif.type*/)
                .body();

        byte[] gif;
        try (InputStream inputStream = body.asInputStream()) {
            gif = inputStream.readAllBytes();
        } catch (IOException ex) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        ByteArrayResource resourceGIF = new ByteArrayResource(gif);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_GIF)
                .contentLength(gif.length)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=gifka.gif")
                .body(resourceGIF);
    }
}
