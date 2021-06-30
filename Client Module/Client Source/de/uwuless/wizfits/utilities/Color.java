package de.uwuless.wizfits.utilities;

public final class Color {

    private float r, g, b, a;

    public static final String HEX_CIRCUIT = "0123456789ABCDEF";

    public Color(String hex) {
        if(!hex.startsWith(String.valueOf('#'))) hex = '#' + hex;
        switch (hex.length()) {

            case 2: for(int i = 0; i < 5; i++) hex += hex.charAt(1);
            case 4: hex = String.valueOf('#') + hex.charAt(1) + hex.charAt(1) + hex.charAt(2) + hex.charAt(2) + hex.charAt(3) + hex.charAt(3);
            case 7:
                int a = (int) Math.floor(240) / (HEX_CIRCUIT.length() - 1) * HEX_CIRCUIT.indexOf(hex.charAt(1));
                int b = (int) Math.floor(16) / (HEX_CIRCUIT.length() - 1) * HEX_CIRCUIT.indexOf(hex.charAt(2));
                int c = (int) Math.floor(240) / (HEX_CIRCUIT.length() - 1) * HEX_CIRCUIT.indexOf(hex.charAt(3));
                int d = (int) Math.floor(16) / (HEX_CIRCUIT.length() - 1) * HEX_CIRCUIT.indexOf(hex.charAt(4));
                int e = (int) Math.floor(240) / (HEX_CIRCUIT.length() - 1) * HEX_CIRCUIT.indexOf(hex.charAt(5));
                int f = (int) Math.floor(16) / (HEX_CIRCUIT.length() - 1) * HEX_CIRCUIT.indexOf(hex.charAt(6));
                this.r = (a + b) / 256.0F;
                this.g = (b + c) / 256.0F;
                this.b = (d + e) / 256.0F;
                this.a = 1.0F;
                break;

            default: new GameError("False Hex-Formatting.").crash();

        }
    }

    public Color(int hex) {
        this.r = ((hex >> 16) & 0xFF) / 255.0F;
        this.g = ((hex >> 8) & 0xFF) / 255.0F;
        this.b = ((hex) & 0xFF) / 255.0F;
        this.a = ((hex >> 24) & 0xFF) / 255.0F;
    }

    public Color(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public Color(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = 1.0F;
    }

    public int toHex() {
        int red = (Math.round(r * 255.0F) << 16) & 0x00FF0000;
        int green = (Math.round(g * 255.0F) << 8) & 0x0000FF00;
        int blue = (Math.round(b * 255.0F) << 0) & 0x000000FF;
        int alpha = (Math.round(a * 255.0F) << 24) & 0xFF000000;
        return alpha | red | green | blue;
    }

}
