package de.uwuless.wizfits.utilities;

public class LoopThread {

    private boolean state;
    private Runnable layer;
    private long tickDelay;
    private Thread baseThread;

    public LoopThread(Runnable layer, int tps) {
        this.setLayer(layer);
        this.setTps(tps);
    }

    private boolean alive;

    public void start() {
        this.state = true;
        this.baseThread = new Thread(() -> {
            alive = true;
            long lastUpdate = System.nanoTime();
            while(state) {
                if(System.nanoTime() - lastUpdate < tickDelay) Thread.yield();
                else {
                    this.layer.run();
                    lastUpdate = System.nanoTime();
                }
            }
            alive = false;
        });
        this.baseThread.start();
    }

    public void stop() {
        this.state = false;
        while(alive) {
            try {
                Thread.sleep(128L);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public void setTps(int tps) {
        this.tickDelay = (long) Math.floor(1000000000.0D / tps);
    }

    public int getTps() {
        return (int) Math.floor(1000000000.0D / tickDelay);
    }

    public void setLayer(Runnable layer) {
        this.layer = layer;
    }

    public Runnable getLayer() {
        return layer;
    }

    public boolean isAlive() {
        return alive;
    }

}

