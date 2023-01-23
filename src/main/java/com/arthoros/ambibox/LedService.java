package com.arthoros.ambibox;

import java.text.MessageFormat;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.github.mbelling.ws281x.Color;
import com.github.mbelling.ws281x.LedStrip;
import com.github.mbelling.ws281x.LedStripType;
import com.github.mbelling.ws281x.Ws281xLedStrip;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LedService {
    private Optional<LedStrip> strip = Optional.empty();
    @Getter
    private Color ledColor = Color.BLACK;
    private Color lastActiveColor = null;
    private int brightness = 0;

    @PostConstruct
    public void initStrip() {
        try {
            log.debug("Initializing LED");
            strip = Optional.of(new Ws281xLedStrip(170, 18, 800000, 10, 0, 0, false, LedStripType.WS2811_STRIP_GRB, false));
            initColors();
        } catch (Throwable e) {
            log.warn("Cannot initialize strip... continue in local mode");
            log.warn(e.getMessage());
        }
    }

    @PreDestroy
    public void destroyStrip() {
        log.debug("Destroying LED");
        if (strip.isPresent()) {
            strip.get().setBrightness(0);
            strip.get().setStrip(Color.BLACK);
            strip.get().render();
        }
    }

    public void startLed() {
        log.debug("Starting LED");
        ledColor = Optional.ofNullable(lastActiveColor).orElse(Color.WHITE);
        brightness = 100;

        if (strip.isPresent()) {
            strip.get().setBrightness(brightness);
            strip.get().setStrip(ledColor);
            strip.get().render();
        }
    }

    public void colorLed(String color) {
        log.debug("Coloring LED: {}", color);
        switch (color) {
            case "RED": ledColor = Color.RED; break;
            case "GREEN": ledColor = Color.GREEN; break;
            case "BLUE": ledColor = Color.BLUE; break;
        }

        if (strip.isPresent()) {
            strip.get().setBrightness(brightness);
            strip.get().setStrip(ledColor);
            strip.get().render();
        }
    }

    public void stopLed() {
        log.debug("Stopping LED");
        lastActiveColor = ledColor;
        ledColor = Color.BLACK;
        brightness = 0;

        if (strip.isPresent()) {
            strip.get().setBrightness(brightness);
            strip.get().setStrip(ledColor);
            strip.get().render();
        }
    }

    public boolean isLedOn() {
        log.debug("Brightness is: {}", brightness);
        log.debug("Black equals led color: {}", Color.BLACK.equals(ledColor));
        return brightness > 0 && !Color.BLACK.equals(ledColor);
    }

    public String getLedColorString() {
        return MessageFormat.format("RGB({0}, {1}, {2})", ledColor.getRed(), ledColor.getGreen(), ledColor.getBlue());
    }

    private void initColors() {
        brightness = 100;
        colorLed("RED");
        wait(500);
        colorLed("GREEN");
        wait(500);
        colorLed("BLUE");
        wait(500);
        stopLed();
    }

    private void wait(int millis) {
        try {
            Thread.sleep(millis);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void colorLed(java.awt.Color rgb) {
        ledColor = new Color(rgb.getRed(), rgb.getGreen(), rgb.getBlue());

        if (strip.isPresent()) {
            strip.get().setBrightness(brightness);
            strip.get().setStrip(ledColor);
            strip.get().render();
        }
    }
}
