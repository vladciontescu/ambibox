package com.arthoros.ambibox;

import com.github.mbelling.ws281x.Color;
import com.github.mbelling.ws281x.LedStrip;
import com.github.mbelling.ws281x.LedStripType;
import com.github.mbelling.ws281x.Ws281xLedStrip;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
public class LedService {
    private final LedStrip strip = new Ws281xLedStrip(18, 18, 800000, 10, 100, 0, false, LedStripType.WS2811_STRIP_GRB, false);
    @Getter
    private Color ledColor = null;

    public void startLed() {
        ledColor = Color.WHITE;
        strip.setStrip(ledColor);
        strip.render();
    }

    public boolean isLedOn() {
        int brightness = strip.getBrightness();
        return brightness > 0 && !Color.BLACK.equals(ledColor);
    }
}
