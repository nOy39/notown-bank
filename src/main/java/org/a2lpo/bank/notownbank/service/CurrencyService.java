package org.a2lpo.bank.notownbank.service;

import com.google.common.collect.Iterables;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.a2lpo.bank.notownbank.model.accounts.CurrencyName;
import org.a2lpo.bank.notownbank.payload.CurrentCurseCurrency;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Сервис получения котировок валют с сайта ЦБРФ
 */
@Service
public class CurrencyService {
    @Value("${app.webpage.currentCurseCurrency}")   //сайт центрбанка
    private String webPage;
    /**
     * <b>Метод получения списка валют с сайта центрбанка</b><br>
     * <p>Так как в котировках валют с ЦБРФ нет Российского рубля, то предварительно
     * в список <code>ArrayList<CurrentCurseCurrency> currencyList</code>, который будет возвращать
     * метод, добавляем валюту Российский рубль.</p><br>
     * Метод получает c сайта центрбанка котировки валют на текущий день
     * в формате JSON, далее парсингом из json получаем объект JsonObject.
     * Далее получаем из JsonObject коллекцию <code>Set<Map.Entry<String, JsonElement>> entries</code>
     * И по коллекции <code>entries</code> пробегаясь циклом for
     * проходит десериализация <code>CurrentCurseCurrency</code>, полученый объект
     * помещаем в список <code>ArrayList<CurrentCurseCurrency> currencyList</code>)
     *
     * @return currencyList возвращаемый список содержащий объекты валют.
     * @throws IOException пробрасываемый на уровень вверх IOException
     */
    public List<CurrentCurseCurrency> getCurrentCurrency() throws IOException {
        ArrayList<CurrentCurseCurrency> currencyList = new ArrayList<>();
        currencyList.add(new CurrentCurseCurrency("643",
                "RUB",
                1,
                "Российский рубль",
                1.00,
                1.00));
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        try (InputStream is = new URL(webPage).openStream();
             Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            JsonElement parse = parser.parse(reader);
            JsonObject jObj = (JsonObject) parse.getAsJsonObject().get("Valute");
            Set<Map.Entry<String, JsonElement>> entries = jObj.entrySet();
            for (Map.Entry<String, JsonElement> entry : entries) {
                JsonElement value = entry.getValue();
                JsonObject asJsonObject = value.getAsJsonObject();
                CurrentCurseCurrency currentCurseCurrency = gson.fromJson(asJsonObject, CurrentCurseCurrency.class);
                currencyList.add(currentCurseCurrency);
            }
        }
        return currencyList;
    }

    /**
     * <b>Метод получения запрашиваемой котировки валюты из списка валют</b><br>
     * Итерацией находим запрашиваемую валюту в списке и возвращаем её на уровень выше.
     *
     * @param currencyList список валют, полученый из ЦБ и десерелизован в список объектов
     *                     <code>CurrentCurseCurrency</code>
     * @param name         валюта которую мы ищем в списке.
     * @return возвращаемый объект класса <code>CurrentCurseCurrency</code>
     */
    public CurrentCurseCurrency getCurse(List<CurrentCurseCurrency> currencyList, CurrencyName name) {
        return Iterables.tryFind(currencyList,
                currency -> name.toString().equals(currency.getCharCode())).or(new CurrentCurseCurrency());
    }
}
