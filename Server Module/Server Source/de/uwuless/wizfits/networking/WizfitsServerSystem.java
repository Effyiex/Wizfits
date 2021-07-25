package de.uwuless.wizfits.networking;

import de.uwuless.wizfits.logging.WizfitsLogger;
import de.uwuless.wizfits.utilities.Vector;

import java.net.ServerSocket;

public final class WizfitsServerSystem {

    public static final WizfitsServerSystem MAIN_SERVER = new WizfitsServerSystem();

    private final Vector<ClientThread> currentUsers = new Vector();

    public final ServerSocket socket;

    static {
        WizfitsLogger.setBinding(arg -> System.out.println(arg));
    }

    // Single Instance
    private WizfitsServerSystem() {
        ServerSocket socket = (ServerSocket) null;
        try {
            socket = new ServerSocket(INetHandler.HOST_PORT);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        this.socket = socket;
    }

    private void loop() {
        WizfitsLogger.INFO.print("Starting Server Loop...");
        CommandReceiver.COMMAND_RECEIVER.start();
        while(true) {
            try {
                currentUsers.add(new ClientThread(socket.accept()));
                currentUsers.forEach(user -> {
                    if(!user.client.isConnected() || user.client.isClosed())
                        currentUsers.remove(user);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        WizfitsLogger.INFO.print("Stopping Server System...");
        System.exit(0);
    }

    public static void main(String[] args) {
        WizfitsLogger.INFO.print("Starting Server System...");
        MAIN_SERVER.loop();
    }

}
