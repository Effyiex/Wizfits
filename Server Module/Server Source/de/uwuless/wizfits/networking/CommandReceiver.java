package de.uwuless.wizfits.networking;

import de.uwuless.wizfits.logging.WizfitsLogger;

import java.util.Scanner;

public final class CommandReceiver extends Thread {

    public static final CommandReceiver COMMAND_RECEIVER = new CommandReceiver();

    private CommandReceiver() {
        super(() -> {
            Scanner scanner = new Scanner(System.in);
            while(!WizfitsServerSystem.MAIN_SERVER.socket.isClosed()) {
                String[] cmd = scanner.next().split(String.valueOf(' '));
                switch (cmd[0].toUpperCase()) {

                    case "STOP":
                        WizfitsServerSystem.MAIN_SERVER.stop();
                        break;

                    default: WizfitsLogger.WARNING.print("Unknown command: " + cmd[0]);

                }
            }
        });
    }

}
