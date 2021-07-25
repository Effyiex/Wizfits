package de.uwuless.wizfits.desktop;

import de.uwuless.wizfits.networking.INetHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public final class DesktopNetHandler implements INetHandler {

    private Socket client;

    @Override
    public boolean checkConnection() {
        return client != null && client.isConnected();
    }

    @Override
    public boolean establishConnection(int port) {
        try {
            this.client = new Socket(HOST_ADDRESS, port);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean send(byte[] bytes) {
        try {
            this.client.getOutputStream().write(bytes);
            this.client.getOutputStream().flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void disposeConnection() {
        try {
            this.client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.client = (Socket) null;
    }

    @Override
    public byte[] read() {
        try {
            InputStream stream = client.getInputStream();
            byte[] buffer = new byte[stream.available()];
            stream.read(buffer);
            return buffer;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}
