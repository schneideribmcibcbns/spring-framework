package com.logicbig.example;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.Map;
import java.util.logging.Logger;

@Controller
@RequestMapping("trades")
public class TradeController {

    private static final Logger LOGGER = Logger.getLogger(TradeController.class.getName());

    @RequestMapping(headers = "User-Agent")
    public String handleAllTradesRequests (@RequestHeader("User-Agent") String userAgent,
                                           Model model) {
        LOGGER.info("all trades requests, User-Agent header  : " + userAgent);
        model.addAttribute("msg", "all trades requests, User-Agent header  : " + userAgent);
        return "my-page";
    }


    /**
     * This won't be mapped if code is not compiled with debugging information
     */
    @RequestMapping(headers = "From")
    public String handleRequestByFromHeader (@RequestHeader String From, Model model) {
        LOGGER.info("trade request by From header  : " + From);
        model.addAttribute("msg", "trade request by From header  : " + From);
        return "my-page";
    }

    @RequestMapping
    public String handleRequestWithAllHeaders (@RequestHeader Map<String, String> header,
                                               Model model) {
        String msg = "Trade request with all headers : " + header;
        LOGGER.info(msg);
        model.addAttribute("msg", msg);
        return "my-page";
    }

    @RequestMapping("tradeCurrencies")
    public String handleRequestWithAllHeaders2 (@RequestHeader HttpHeaders httpHeaders,
                                                Model model) {
        String msg = "Trade request with all headers : " + httpHeaders;
        LOGGER.info(msg);
        model.addAttribute("msg", msg);
        return "my-page";
    }

    @RequestMapping(headers = {"User-Agent", "Accept-Language"})
    public String handleRequestByTwoHeaders (@RequestHeader("User-Agent") String userAgent,
                                             @RequestHeader("Accept-Language") String
                                                                 acceptLanguage,
                                             Model map) {
        String msg = "Trade request by User-Agent and Accept headers : " + userAgent + ", " +
                            acceptLanguage;
        LOGGER.info(msg);
        map.addAttribute("msg", msg);
        return "my-page";
    }

    @RequestMapping(value = "{tradeId}")
    public String handleRequestById (@PathVariable("tradeId") String tradeId,
                                     @RequestHeader("If-Modified-Since") Date date, Model model) {
        String msg = "Trade request by trade id and If-Modified-Since header : " + tradeId + ", "
                            + date;
        LOGGER.info(msg);
        model.addAttribute("msg", msg);
        return "my-page";
    }

    @RequestMapping(value = "exchangeRates")
    public String handleExchangeRatesRequest (
                        @RequestHeader(value = "Accept", required = false) String acceptHeader,
                        Model model) {

        String msg = "exchange rates request.  Accept header: " + acceptHeader;
        LOGGER.info(msg);
        model.addAttribute("msg", msg);
        return "my-page";
    }
}