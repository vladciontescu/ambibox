package com.arthoros.ambibox;

import java.awt.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LedController {
    private final LedService ledService;

    @GetMapping("/")
    public String showColorPicker(Model model) {
        log.info("LED color is: {}", ledService.getLedColorString());
        model.addAttribute("isLedOn", ledService.isLedOn());

        com.github.mbelling.ws281x.Color ledColor = ledService.getLedColor();
        Color color = new Color(ledColor.getRed(), ledColor.getGreen(), ledColor.getBlue());

        Colors colors = new Colors();
        colors.setHex(color.getRGB());
        model.addAttribute("colors", colors);
        return "index";
    }

    @PostMapping("/")
    public String save(Colors colors) {
        log.info("Set color: {}", colors.getHex());
        ledService.colorLed(colors.getRgb());
        return "redirect:/";
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
