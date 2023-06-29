package com.humidty.arge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.socket.TextMessage;

@Controller
public class WebSocketController {

    public TextMessage handleHumidity(String deviceID,double humidity) {
        //        save db humidity

        // Bu değerleri kullanarak istediğiniz işlemleri yapabilirsiniz
        if (humidity < 50) {
            return new TextMessage("LED is ON");
        } else {
            return new TextMessage("LED is ON");
        }
    }
}