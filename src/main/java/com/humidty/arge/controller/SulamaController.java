package com.humidty.arge.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SulamaController {

    @MessageMapping("/sulama/start")
    @SendTo("/topic/status")
    public String startSulama(String message) throws Exception {
        // Sulama başlatma işlemleri burada yapılır.
        // Örneğin, bir cihazı açabilir veya bir veritabanı durumunu güncelleyebilirsiniz.
        // Daha sonra, sulamanın başladığını bildiren bir mesaj döndürülür:
        return "Sulama başladı: " + message;
    }

}