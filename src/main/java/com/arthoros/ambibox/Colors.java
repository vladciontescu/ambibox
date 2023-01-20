package com.arthoros.ambibox;

import java.awt.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Colors {
    private String hex;
    private String rgb;
    private String hsl;

    public Color getRgb() {
        return Color.decode(hex);
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public void setHex(int rgb) {
        hex = "#" + Integer.toHexString(rgb).substring(2);
    }
}