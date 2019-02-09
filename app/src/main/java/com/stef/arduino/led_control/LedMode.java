package com.stef.arduino.led_control;

public enum LedMode {
    SOUND_REACTIVE {
        @Override public String toString() { return "Sound Reactive Mode"; }
    },
    STARS {
        @Override public String toString() { return "Star Mode"; }
    },
    STATIC_COLOR {
        @Override public String toString() { return "Static Color Mode"; }
    },
    COLOR_CYCLING {
        @Override public String toString() { return "Color Cycling Mode"; }
    }
}
