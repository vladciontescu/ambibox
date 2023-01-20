package com.arthoros.ambibox;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LedController {
    private final LedService ledService;

    @GetMapping("/")
    public String showColorPicker(Model model) {
        log.info("LED color is: {}", ledService.getLedColor());
        model.addAttribute("isLedOn", ledService.isLedOn());
        return "index";
    }

    @GetMapping("/start")
    public String startLed() {
        log.info("Requested LED start");
        ledService.startLed();
        return "redirect:/";
    }

    @GetMapping("/colorLed")
    public String colorLed(@RequestParam String color) {
        log.info("Requested LED color: {}", color);
        ledService.colorLed(color);
        return "redirect:/";
    }

    @GetMapping("/stop")
    public String stopLed() {
        log.info("Requested LED stop");
        ledService.stopLed();
        return "redirect:/";
    }
}
