package de.uwuless.wizfits.utilities;

public class Vector<E> {

    private Object[] buffer;

    public final static class KernelVector<E> extends Vector<E> {

        // No Exception Pre-Handling. No Auto Concurrent-Modification fix.

    }

    public Vector(int dimension) {
        Vector.this.buffer = new Object[dimension];
        if(!(this instanceof KernelVector)) {
            this.addAfterLoop = new KernelVector();
            this.remAfterLoop = new KernelVector();
        }
    }

    public Vector(E... args) {
        Vector.this.buffer = args;
        if(!(this instanceof KernelVector)) {
            this.addAfterLoop = new KernelVector();
            this.remAfterLoop = new KernelVector();
        }
    }

    public int add(E obj) {
        if(inLoop) {
            this.addAfterLoop.add(obj);
            return buffer.length + addAfterLoop.dimension();
        }
        Object[] latest = buffer;
        buffer = new Object[buffer.length + 1];
        for(int i = 0; i < latest.length; i++)
        buffer[i] = latest[i];
        buffer[latest.length] = obj;
        return latest.length;
    }

    public void remove(int... indexies) {
        for(int index : indexies) {
            if(inLoop) {
                remAfterLoop.add(index);
                continue;
            }
            Object[] latest = buffer;
            buffer = new Object[buffer.length - 1];
            for (int i = 0; i < latest.length; i++)
            if (i < index) buffer[i] = latest[i];
            else if (i > index) buffer[i - 1] = latest[i];
        }
    }

    public void clear() {
        this.buffer = new Object[0];
    }

    public void remove(E obj) {
        this.remove(index(obj));
    }

    public int index(E obj) {
        for(int i = 0; i < buffer.length; i++)
        if(buffer[i].equals(obj)) return i;
        return -1;
    }

    public void update(int index, E value) {
        this.buffer[index] = value;
    }

    public boolean contains(E check) {
        Atomic<Boolean> answer = new Atomic(false);
        this.forEach(obj -> {
            if(obj.equals(check)) answer.set(true);
        });
        return answer.get();
    }

    public E get(int index) {
        return (E) this.buffer[index];
    }

    public int dimension() {
        return buffer.length;
    }

    public Vector<E> clone() {
        Vector<E> cloned = new Vector();
        cloned.buffer = buffer.clone();
        return cloned;
    }

    private KernelVector<E> addAfterLoop;
    private KernelVector<Integer> remAfterLoop;

    private boolean inLoop = false;

    public void forEach(IListener<E> consumer) {
        inLoop = true;
        for(Object obj : buffer)
        consumer.handle((E) obj);
        inLoop = false;
        if(!(this instanceof KernelVector)) {
            addAfterLoop.forEach(obj -> add(obj));
            remAfterLoop.forEach(obj -> remove(obj));
            addAfterLoop.clear();
            remAfterLoop.clear();
        }
    }

}
