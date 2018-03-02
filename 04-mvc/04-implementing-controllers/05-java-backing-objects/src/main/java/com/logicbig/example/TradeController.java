package com.logicbig.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.logging.Logger;

@Controller
@RequestMapping("trades")
public class TradeController {

    private static final Logger LOGGER = Logger.getLogger(TradeController.class.getName());

    @RequestMapping
    public String handleTradeRequest (Trade trade, Model model) {
        String msg = String.format("trade request. buySell: %s, buyCurrency: %s, sellCurrency: %s",
                                   trade.getBuySell(), trade.getBuyCurrency(),
                                   trade.getSellCurrency());
        LOGGER.info(msg);
        model.addAttribute("msg", msg);
        return "my-page2";
    }

    @RequestMapping("{buySell}/{buyCurrency}/{sellCurrency}")
    public String handleTradeRequest2 (Trade trade, Model model) {
        String msg = String.format("trade request. buySell: %s, buyCurrency: %s, sellCurrency: %s",
                                   trade.getBuySell(), trade.getBuyCurrency(),
                                   trade.getSellCurrency());
        LOGGER.info(msg);
        model.addAttribute("msg", msg);
        return "my-page";
    }


    @RequestMapping("paramTest")
    public String handleTradeRequest (@RequestParam("buySell") String buySell,
                                      @RequestParam("buyCurrency") String buyCurrency,
                                      @RequestParam("sellCurrency") String sellCurrency,
                                      Model map) {
        String msg = String.format("trade request. buySell: %s, buyCurrency: %s, sellCurrency: %s",
                                   buySell, buyCurrency, sellCurrency);
        LOGGER.info(msg);
        map.addAttribute("msg", msg);
        return "my-page";
    }

    @RequestMapping("pathTest/{buySell}/{buyCurrency}/{sellCurrency}")
    public String handleTradeRequest3 (@PathVariable("buySell") String buySell,
                                       @PathVariable("buyCurrency") String buyCurrency,
                                       @PathVariable("sellCurrency") String sellCurrency,
                                       Model map) {
        String msg = String.format("trade request. buySell: %s, buyCurrency: %s, sellCurrency: %s",
                                   buySell, buyCurrency, sellCurrency);
        LOGGER.info(msg);
        map.addAttribute("msg", msg);
        return "my-page";
    }
}