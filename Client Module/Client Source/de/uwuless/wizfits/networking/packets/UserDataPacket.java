package de.uwuless.wizfits.networking.packets;

import de.uwuless.wizfits.networking.Packet;

public class UserDataPacket extends Packet {

    public UserDataPacket() {
        super("Username", "E-Mail", "Password", "Registration");
    }

}
