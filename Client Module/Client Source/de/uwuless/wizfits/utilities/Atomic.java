package de.uwuless.wizfits.utilities;

public final class Atomic<Type> {

    private Type value;

    private boolean finalized = false;

    public Atomic() {
        this((Type) null);
    }

    public Atomic(Type value) {
        this.value = value;
    }

    public Atomic<Type> settle() {
        this.finalized = true;
        return this;
    }

    public Type getAndSet(Type value) {
        Type buffer = (Type) this.value;
        this.value = value;
        return buffer;
    }

    public void set(Type value) {
        if(this.value == null || !finalized)
        this.value = value;
    }

    public Type get() {
        return value;
    }

    public boolean isSet() {
        return value != null;
    }

}
