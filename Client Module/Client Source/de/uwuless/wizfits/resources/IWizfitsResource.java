package de.uwuless.wizfits.resources;

import de.uwuless.wizfits.networking.INetHandler;
import de.uwuless.wizfits.networking.IPacketListener;
import de.uwuless.wizfits.networking.Packet;
import de.uwuless.wizfits.networking.packets.ResourceStreamPacket;
import de.uwuless.wizfits.utilities.Atomic;
import de.uwuless.wizfits.utilities.Vector;

public interface IWizfitsResource {

    Atomic<Integer> ID_COUNTER = new Atomic(0);
    Vector<IWizfitsResource> PIPELINE = new Vector();

    static void startPipeline() {
        INetHandler.LISTENER.add(new IPacketListener() {

            @Override
            public void onReceive(Packet packet) {
                ResourceStreamPacket rsp = (ResourceStreamPacket) packet;

            }

            @Override
            public Class<? extends Packet> listenTo() {
                return ResourceStreamPacket.class;
            }

        });
    }

    default String getIdentifier() {
        return "resource_" + String.valueOf(ID_COUNTER.getAndSet(ID_COUNTER.get() + 1));
    }

    void construct(byte[] bytes);

}
