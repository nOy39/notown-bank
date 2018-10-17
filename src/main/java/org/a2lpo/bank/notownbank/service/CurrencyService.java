package org.a2lpo.bank.notownbank.service;

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
import java.util.*;

@Service
public class CurrencyService {
    @Value("${app.webpage.currentCurseCurrency}")
    private String webPage;

    /**
     * Получаем список валют с курсом на текущую дату с сайта центрбанка
     *todo задокументировать
     * @return
     * @throws IOException
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
     * Получаем курс валюты из списка валют.
     *  todo задокументировать
     * @param currencyList
     * @param name
     * @return
     */
    public CurrentCurseCurrency getCurrencyCurse(List<CurrentCurseCurrency> currencyList, CurrencyName name) {

        for (CurrentCurseCurrency currentCurseCurrency : currencyList) {
            boolean equals = currentCurseCurrency.getCharCode().equals(name.toString());
            if (equals) {
                return currentCurseCurrency;
            }
        }
        return new CurrentCurseCurrency();
    }
}
