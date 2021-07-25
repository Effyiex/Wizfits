package de.uwuless.wizfits.networking.packets;

import de.uwuless.wizfits.networking.Packet;
import de.uwuless.wizfits.resources.IWizfitsResource;
import de.uwuless.wizfits.resources.WizfitsTexture;

public class ResourceStreamPacket extends Packet {

    public ResourceStreamPacket(byte[] bytes, Class<? extends IWizfitsResource> type) {
        super("Bytes", "Type");
        this.setValue("Bytes", new String(bytes));
        this.setValue("Type", type.getSimpleName());
    }

    public byte[] getBytes() {
        return getValue("Bytes").getBytes();
    }

    public Class<? extends IWizfitsResource> getType() {
        switch (getValue("Type")) {

            case "WizfitsTexture": return WizfitsTexture.class;

            default: return null;

        }
    }

}
