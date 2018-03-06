package com.logicbig.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

@Controller
public class TheController {

    @RequestMapping(value = "/",
              method = {RequestMethod.POST, RequestMethod.GET})
    public String handleGet (Model model, Locale locale) {

        FormData formData = new FormData();
        formData.setLocaleCode(locale.toString());
        model.addAttribute("formData", formData);

        Map<String, String> localeChoices = new LinkedHashMap<>();
        Locale l = Locale.US;
        localeChoices.put(l.toString(), l.getDisplayLanguage());
        l = Locale.GERMANY;
        localeChoices.put(l.toString(), l.getDisplayLanguage());
        l = Locale.FRANCE;
        localeChoices.put(l.toString(), l.getDisplayLanguage());
        model.addAttribute("localeChoices", localeChoices);

        return "main";
    }

    @RequestMapping(value = "/page1")
    public String handlePage1 () {
        return "page1";
    }

    @RequestMapping(value = "/page2")
    public String handlePage2 () {
        return "page2";
    }


    public static class FormData {
        private String localeCode;

        public String getLocaleCode () {
            return localeCode;
        }

        public void setLocaleCode (String localeCode) {
            this.localeCode = localeCode;
        }
    }
}