package ru.rybkin.alfa_bank;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import ru.rybkin.alfa_bank.feign.transfer.OneGifDTO;
import java.util.Calendar;
import java.util.Map;

@UtilityClass
public class SystemUtils {
    private static final String HISTORICAL = "%04d-%02d-%02d.json";

    public String historicalDate(@NonNull Calendar calendar) throws NullPointerException {
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        return String.format(HISTORICAL, year, month, day);
    }

    public String getURL(OneGifDTO data, String typeGIF, String typeURL) {
        Map<String, Map<String,String>> images = data.getImages();
        Map<String, String> image = images.get(typeGIF);
        return image.get(typeURL);
    }

}
