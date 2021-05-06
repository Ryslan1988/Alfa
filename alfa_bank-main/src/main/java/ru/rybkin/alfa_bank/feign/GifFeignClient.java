package ru.rybkin.alfa_bank.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.rybkin.alfa_bank.error.MyErrorDecoder;
import ru.rybkin.alfa_bank.feign.transfer.ResponseFeignGiphyDTO;

@FeignClient(name = "giphy", url = "https://api.giphy.com", configuration = GifFeignClient.Config.class)
public interface GifFeignClient {

    @GetMapping("/v1/{resource}/{endpoint}")
    ResponseFeignGiphyDTO getGiphy(@PathVariable String resource, @PathVariable String endpoint,
                                          @RequestParam String api_key, @RequestParam String q, @RequestParam Integer limit,
                                          @RequestParam Integer offset, @RequestParam String rating, @RequestParam String lang);
    @Configuration
    class Config {
        @Bean
        public MyErrorDecoder myErrorDecoder() {
            return new MyErrorDecoder();
        }
    }
}
