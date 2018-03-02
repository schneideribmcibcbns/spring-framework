package com.logicbig.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("trades")
public class TradeController {

    @RequestMapping("/{trade}")
    public String handleTradeRequest (Trade trade, Model model) {
        System.out.println(trade);
        if (trade.getTradeId() == 0) {
            model.addAttribute("msg", "No trade found");
            return "no-trade-page";
        }
        return "trade-page";
    }
}