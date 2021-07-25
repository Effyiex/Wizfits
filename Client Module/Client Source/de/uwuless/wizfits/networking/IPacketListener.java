package de.uwuless.wizfits.networking;

public interface IPacketListener {

    default void onSend(Packet packet) {
        return;
    }

    default void onReceive(Packet packet) {
        return;
    }

    Class<? extends Packet> listenTo();

}
