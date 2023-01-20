package com.arthoros.ambibox;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LedController {
    private final LedService ledService;

    @GetMapping("/")
    public String showColorPicker(Model model) {
        model.addAttribute("isLedOn", ledService.isLedOn());
        return "index";
    }

    @GetMapping("/start")
    public String startLed(Model model) {
        log.info("Requested LED start");
        ledService.startLed();
        return "index";
    }
}
