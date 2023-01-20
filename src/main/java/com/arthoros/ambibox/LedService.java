package com.arthoros.ambibox;

import java.text.MessageFormat;
import java.util.Optional;
import javax.annotation.PostConstruct;

import com.github.mbelling.ws281x.Color;
import com.github.mbelling.ws281x.LedStrip;
import com.github.mbelling.ws281x.LedStripType;
import com.github.mbelling.ws281x.Ws281xLedStrip;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LedService {
    private Optional<LedStrip> strip = Optional.empty();
    private Color ledColor = Color.BLACK;
    private int brightness = 0;

    @PostConstruct
    public void initStrip() {
        try {
            strip = Optional.of(new Ws281xLedStrip(18, 18, 800000, 10, 0, 0, false, LedStripType.WS2811_STRIP_GRB, false));
        } catch (Throwable e) {
            log.warn("Cannot initialize strip... continue in local mode");
            log.warn(e.getMessage());
        }
    }

    public void startLed() {
        ledColor = Color.WHITE;
        brightness = 100;

        if (strip.isPresent()) {
            strip.get().setBrightness(100);
            strip.get().setStrip(ledColor);
            strip.get().render();
        }
    }

    public void colorLed(String color) {
        switch (color) {
            case "RED": ledColor = Color.RED; break;
            case "GREEN": ledColor = Color.GREEN; break;
            case "BLUE": ledColor = Color.BLUE; break;
        }

        if (strip.isPresent()) {
            strip.get().setStrip(ledColor);
            strip.get().render();
        }
    }

    public void stopLed() {
        ledColor = Color.BLACK;
        brightness = 0;

        if (strip.isPresent()) {
            strip.get().setBrightness(0);
            strip.get().setStrip(ledColor);
            strip.get().render();
        }
    }

    public boolean isLedOn() {
        int brightness = strip.map(LedStrip::getBrightness).orElse(this.brightness);
        log.info("Brightness is: {}", brightness);
        log.info("Black equals led color: {}", Color.BLACK.equals(ledColor));
        return brightness > 0 && !Color.BLACK.equals(ledColor);
    }

    public String getLedColor() {
        return MessageFormat.format("RGB({0}, {1}, {2})", ledColor.getRed(), ledColor.getGreen(), ledColor.getBlue());
    }
}
