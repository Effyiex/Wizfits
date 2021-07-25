package de.uwuless.wizfits.networking;

import de.uwuless.wizfits.IWizfitsClient;
import de.uwuless.wizfits.networking.packets.DisconnectPacket;
import de.uwuless.wizfits.utilities.Atomic;
import de.uwuless.wizfits.utilities.Vector;

public interface INetHandler {

    String HOST_ADDRESS = "127.0.0.1";
    int HOST_PORT = 7343;

    Atomic<INetHandler> INSTANCE = new Atomic().settle();

    Vector<IPacketListener> LISTENER = new Vector();

    static void create(Class<? extends INetHandler> clazz) {
        INSTANCE.set((INetHandler) IWizfitsClient.Instances.create(clazz));
    }

    static boolean connect(int port) {
        return INSTANCE.get().establishConnection(port);
    }

    static void disconnect() {
        sendPacket(new DisconnectPacket());
        INSTANCE.get().disposeConnection();
    }

    static boolean sendPacket(Packet packet) {
        LISTENER.forEach(listener -> {
            if(listener.listenTo() == packet.getClass())
            listener.onSend(packet);
        });
        return INSTANCE.get().send(Packet.encrypt(packet));
    }

    static void receiveBytes(byte[] bytes) {
        if(bytes.length <= 0) return;
        Packet packet = Packet.decrypt(bytes);
        LISTENER.forEach(listener -> {
            if(listener.listenTo() == packet.getClass())
            listener.onReceive(packet);
        });
    }

    static boolean isConnected() {
        return INSTANCE.isSet() && INSTANCE.get().checkConnection();
    }

    boolean checkConnection();
    boolean establishConnection(int port);
    boolean send(byte[] bytes);
    void disposeConnection();
    byte[] read();

}
