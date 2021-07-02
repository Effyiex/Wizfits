package de.uwuless.wizfits.logging;

import de.uwuless.wizfits.IWizfitsClient;
import de.uwuless.wizfits.utilities.IListener;

public enum WizfitsLogger {

    INFO, WARNING, ERROR;

    public static final String PREFIX = "[" + IWizfitsClient.GAME_NAME + "-{INFO_TYPE}]: ";

    private static IListener binding;

    public static void setBinding(IListener binding) {
        if(WizfitsLogger.binding != null) return;
        WizfitsLogger.binding = binding;
        for(String line : buffer) binding.handle(line);
        buffer = null;
    }

    private static String[] buffer = new String[0];

    private static void addToBuffer(String line) {
        String[] replacement = new String[buffer.length + 1];
        for(int i = 0; i < buffer.length; i++)
        replacement[i] = buffer[i];
        replacement[buffer.length] = line;
        buffer = replacement;
    }

    public void print(String text) {
        String prefix = PREFIX.replace("{INFO_TYPE}", this.name());
        for(String line : text.split(String.valueOf('\n'))) {
            line = prefix + line;
            if(binding != null) binding.handle(line);
            else WizfitsLogger.addToBuffer(line);
        }
    }

}
