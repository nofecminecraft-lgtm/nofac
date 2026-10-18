package dev.lightsmp.client;

import java.util.LinkedHashMap;
import java.util.Map;

public final class LightState {
    private LightState() {}

    public static boolean hudEnabled = true;
    public static boolean cobwebInfoEnabled = true;
    public static int trainingLevel = 3;

    public static final Map<String, Boolean> modules = new LinkedHashMap<>();
    public static final Map<String, Integer> keyCodes = new LinkedHashMap<>();

    static {
        modules.put("Movement HUD", true);
        modules.put("Cobweb Info", true);
        modules.put("Training Overlay", false);
        keyCodes.put("Movement HUD", org.lwjgl.glfw.GLFW.GLFW_KEY_H);
        keyCodes.put("Cobweb Info", org.lwjgl.glfw.GLFW.GLFW_KEY_C);
        keyCodes.put("Training Overlay", org.lwjgl.glfw.GLFW.GLFW_KEY_UNKNOWN);
    }

    public static boolean isEnabled(String name) {
        return modules.getOrDefault(name, false);
    }

    public static void toggle(String name) {
        modules.put(name, !isEnabled(name));
        if (name.equals("Movement HUD")) hudEnabled = isEnabled(name);
        if (name.equals("Cobweb Info")) cobwebInfoEnabled = isEnabled(name);
    }
}
