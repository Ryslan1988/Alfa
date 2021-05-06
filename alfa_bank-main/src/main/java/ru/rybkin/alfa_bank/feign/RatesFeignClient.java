package ru.rybkin.alfa_bank.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.rybkin.alfa_bank.error.MyErrorDecoder;
import ru.rybkin.alfa_bank.feign.transfer.ResponseFeignRatesDTO;


@FeignClient(name = "changeRates", url = "https://openexchangerates.org", configuration = DownloadGIF.Config.class)
public interface RatesFeignClient {

    @GetMapping("/api/historical/{date}")
    ResponseFeignRatesDTO getHistorical(@PathVariable String date, @RequestParam String app_id);

    @GetMapping("/api/latest.json")
    ResponseFeignRatesDTO getLatest(@RequestParam String app_id);

    @Configuration
    class Config {
        @Bean
        public MyErrorDecoder myErrorDecoder() {
            return new MyErrorDecoder();
        }
    }
}
