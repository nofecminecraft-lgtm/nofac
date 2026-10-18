package dev.lightsmp.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public final class LightClient implements ClientModInitializer {
    public static KeyBinding openGuiKey;
    public static KeyBinding hudToggleKey;
    public static KeyBinding cobwebInfoKey;

    @Override
    public void onInitializeClient() {
        LightHud.register();
        openGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.lightclient.open_gui", InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_J, "category.lightclient"
        ));
        hudToggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.lightclient.toggle_hud", InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_H, "category.lightclient"
        ));
        cobwebInfoKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.lightclient.cobweb_info", InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_C, "category.lightclient"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openGuiKey.wasPressed()) {
                if (client.currentScreen == null) {
                    client.setScreen(new LightClickGui());
                }
            }
            while (hudToggleKey.wasPressed()) {
                LightState.hudEnabled = !LightState.hudEnabled;
            }
            while (cobwebInfoKey.wasPressed()) {
                LightState.cobwebInfoEnabled = !LightState.cobwebInfoEnabled;
            }
        });
    }
}
