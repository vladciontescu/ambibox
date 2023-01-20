package com.arthoros.ambibox;

import java.util.Optional;
import javax.annotation.PostConstruct;

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

    @PostConstruct
    public void initStrip() {
        try {
            strip = Optional.of(new Ws281xLedStrip(18, 18, 800000, 10, 100, 0, false, LedStripType.WS2811_STRIP_GRB, false));
        } catch (UnsatisfiedLinkError e) {
            log.warn("Cannot initialize strip... continue in local mode");
            log.warn(e.getMessage());
        }
    }

    public void startLed() {
        ledColor = Color.WHITE;

        if (strip.isPresent()) {
            strip.get().setStrip(ledColor);
            strip.get().render();
        }
    }

    public boolean isLedOn() {
        int brightness = strip.map(LedStrip::getBrightness).orElse(0);
        return brightness > 0 && !Color.BLACK.equals(ledColor);
    }
}
