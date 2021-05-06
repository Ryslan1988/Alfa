package ru.rybkin.alfa_bank.feign;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.rybkin.alfa_bank.error.MyErrorDecoder;

@FeignClient(name = "saveGIF", url = "https://media0.giphy.com", configuration = DownloadGIF.Config.class)
public interface DownloadGIF {

    @GetMapping("/media/{id}/giphy.{type}")
    Response downloadGIF(@PathVariable String id, @PathVariable String type);

    @Configuration
    class Config {
        @Bean
        public MyErrorDecoder myErrorDecoder() {
            return new MyErrorDecoder();
        }
    }
}
