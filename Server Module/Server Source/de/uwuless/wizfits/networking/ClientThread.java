package de.uwuless.wizfits.networking;

import de.uwuless.wizfits.logging.WizfitsLogger;
import de.uwuless.wizfits.networking.packets.DisconnectPacket;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientThread extends Thread {

    public final Socket client;

    private final InputStream input;
    private final OutputStream output;

    public ClientThread(Socket client) {
        this.client = client;
        InputStream input = null;
        OutputStream output = null;
        try {
            input = client.getInputStream();
            output = client.getOutputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.input = input;
        this.output = output;
        this.start();
    }

    public boolean sendPacket(Packet packet) {
        try {
            this.output.write(Packet.encrypt(packet));
            this.output.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            return true;
        }
    }

    private Packet getLatestPacket() {
        try {
            byte[] buffer = new byte[input.available()];
            input.read(buffer);
            if(buffer.length > 0) return Packet.decrypt(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String address;

    @Override
    public void run() {
        this.address = client.getLocalAddress().getHostAddress();
        WizfitsLogger.INFO.print("Client connected: " + address);
        thread_loop : while(client.isConnected() && !client.isClosed()) {
            try {
                Thread.sleep(32L);
                Packet packet = getLatestPacket();
                if(packet != null) {
                    switch (packet.getName().trim()) {

                        case "DisconnectPacket":
                            client.close();
                            break thread_loop;

                        default: WizfitsLogger.WARNING.print("Unknown Packet-Type: " + packet.getName());

                    }
                }
            } catch (Exception ex1) {
                ex1.printStackTrace();
                try {
                    client.close();
                } catch (Exception ex2) {
                    ex2.printStackTrace();
                }
            }
        }
        WizfitsLogger.INFO.print("Client disconnected: " + address);
    }

}
