package de.uwuless.wizfits.utilities;

import de.uwuless.wizfits.IWizfitsClient;
import de.uwuless.wizfits.WizfitsLogger;

public final class GameError {

    public final String cause;
    public final String[] formattings;

    public GameError(String cause) {
        this.cause = cause;
        if(cause.contains(String.valueOf('{'))) {
            int pre = 0, post = 0;
            for(char ch : cause.toCharArray())
            if(ch == '{') pre++;
            else if(ch == '}') post++;
            if(pre != post) new GameError("Wrong Error-Formatting.").crash();
            this.formattings = new String[(pre + post) / 2];
        } else this.formattings = new String[0];
    }

    public void format(int index, String filler) {
        this.formattings[index] = filler;
    }

    public void crash() {
        String cause = this.cause;
        for(int i = 0; i < formattings.length; i++)
        if(formattings[i] != null)
        cause.replace("{" + i + "}", formattings[i]);
        WizfitsLogger.ERROR.print(cause);
        IWizfitsClient.getInstance().stop();
    }

}
