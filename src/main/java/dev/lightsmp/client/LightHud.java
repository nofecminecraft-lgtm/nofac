package dev.lightsmp.client;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public final class LightHud {
    private LightHud() {}

    public static void register() {
        HudRenderCallback.EVENT.register((context, tickCounter) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == null || client.options.hudHidden) return;

            if (LightState.hudEnabled && LightState.isEnabled("Movement HUD")) {
                double speed = Math.sqrt(
                        client.player.getVelocity().x * client.player.getVelocity().x
                        + client.player.getVelocity().z * client.player.getVelocity().z
                ) * 20.0;
                context.drawTextWithShadow(client.textRenderer,
                        Text.literal(String.format("Speed: %.2f", speed)),
                        8, 8, 0xFF48D7FF);
            }

            if (LightState.cobwebInfoEnabled && LightState.isEnabled("Cobweb Info")
                    && client.player.isInCobweb()) {
                context.drawTextWithShadow(client.textRenderer,
                        Text.literal("Cobweb detected — movement slowed"),
                        8, 22, 0xFFFFD166);
            }

            if (LightState.isEnabled("Training Overlay")) {
                context.drawTextWithShadow(client.textRenderer,
                        Text.literal("Training level: " + LightState.trainingLevel + "/6"),
                        8, 36, 0xFFB7C5D8);
            }
        });
    }
}
