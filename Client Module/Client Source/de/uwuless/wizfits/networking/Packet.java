package de.uwuless.wizfits.networking;

public class Packet {

    private static final byte[] shift(byte[] bytes) {
        byte[] buffer = new byte[bytes.length];
        for(int i = 0; i < bytes.length; i++) {
            int pre = bytes[i] - Byte.MIN_VALUE;
            while(buffer[i] > Byte.MAX_VALUE) buffer[i] -= Byte.MAX_VALUE - Byte.MIN_VALUE;
            buffer[i] = (byte) (pre);
        }
        return buffer;
    }

    public static final byte[] encrypt(Packet packet) {
        String info = packet.name;
        for(String data : packet.data)
        info += '\n' + data;
        return shift(info.getBytes());
    }

    public static final Packet decrypt(byte[] bytes) {
        String[] info = new String(shift(bytes)).split(String.valueOf('\n'));
        Packet buffer = new Packet();
        buffer.name = info[0];
        buffer.data = new String[info.length - 1];
        for(int i = 1; i < info.length; i++) buffer.data[i - 1] = info[i];
        return buffer;
    }

    private String name;
    private String[] data;

    public Packet(String... info) {
        this.name = this.getClass().getSimpleName();
        this.data = new String[info.length * 2];
    }

    public void setValue(String info, Object value) {
        for(int i = 0; i < data.length; i += 2)
        if(data[i].equals(info)) data[i + 1] = String.valueOf(value);
    }

    public String getValue(String info) {
        for(int i = 0; i < data.length; i += 2)
        if(data[i].equals(info)) return data[i + 1];
        return null;
    }

    public final String getName() {
        return name;
    }

    public final byte[] encrypt() {
        return encrypt(this);
    }

}
