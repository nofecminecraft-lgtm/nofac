package dev.lightsmp.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public final class LightClickGui extends Screen {
    private final List<ButtonWidget> moduleButtons = new ArrayList<>();
    private boolean waitingForKey = false;
    private String keyTarget = "";

    public LightClickGui() {
        super(Text.literal("Light Client"));
    }

    @Override
    protected void init() {
        moduleButtons.clear();
        int center = this.width / 2;
        int y = Math.max(48, this.height / 2 - 90);

        addDrawableChild(ButtonWidget.builder(moduleText("Movement HUD"), b -> {
            LightState.toggle("Movement HUD");
            b.setMessage(moduleText("Movement HUD"));
        }).dimensions(center - 150, y, 180, 20).build());

        addDrawableChild(ButtonWidget.builder(keyText("Movement HUD"), b -> {
            waitingForKey = true;
            keyTarget = "Movement HUD";
            b.setMessage(Text.literal("Press a key..."));
        }).dimensions(center + 36, y, 114, 20).build());

        y += 27;
        addDrawableChild(ButtonWidget.builder(moduleText("Cobweb Info"), b -> {
            LightState.toggle("Cobweb Info");
            b.setMessage(moduleText("Cobweb Info"));
        }).dimensions(center - 150, y, 180, 20).build());

        addDrawableChild(ButtonWidget.builder(keyText("Cobweb Info"), b -> {
            waitingForKey = true;
            keyTarget = "Cobweb Info";
            b.setMessage(Text.literal("Press a key..."));
        }).dimensions(center + 36, y, 114, 20).build());

        y += 27;
        addDrawableChild(ButtonWidget.builder(moduleText("Training Overlay"), b -> {
            LightState.toggle("Training Overlay");
            b.setMessage(moduleText("Training Overlay"));
        }).dimensions(center - 150, y, 180, 20).build());

        addDrawableChild(ButtonWidget.builder(keyText("Training Overlay"), b -> {
            waitingForKey = true;
            keyTarget = "Training Overlay";
            b.setMessage(Text.literal("Press a key..."));
        }).dimensions(center + 36, y, 114, 20).build());

        y += 34;
        addDrawableChild(ButtonWidget.builder(
                Text.literal("Training level: " + LightState.trainingLevel + " / 6"),
                b -> {
                    LightState.trainingLevel++;
                    if (LightState.trainingLevel > 6) LightState.trainingLevel = 1;
                    b.setMessage(Text.literal("Training level: " + LightState.trainingLevel + " / 6"));
                }
        ).dimensions(center - 150, y, 300, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.literal("Done"), b -> close())
                .dimensions(center - 60, y + 32, 120, 20).build());
    }

    private Text moduleText(String name) {
        return Text.literal(name + ": " + (LightState.isEnabled(name) ? "ON" : "OFF"));
    }

    private Text keyText(String name) {
        int key = LightState.keyCodes.getOrDefault(name, GLFW.GLFW_KEY_UNKNOWN);
        String label = key == GLFW.GLFW_KEY_UNKNOWN ? "Unbound" : GLFW.glfwGetKeyName(key, 0);
        if (label == null) label = "Key " + key;
        return Text.literal(label.toUpperCase());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        int center = this.width / 2;
        context.fill(center - 166, this.height / 2 - 128, center + 166, this.height / 2 + 126, 0xE8151824);
        context.drawCenteredTextWithShadow(this.textRenderer, "LIGHT CLIENT", center, this.height / 2 - 116, 0xFF48D7FF);
        context.drawCenteredTextWithShadow(this.textRenderer, "Modules                         Keybinds", center, this.height / 2 - 100, 0xFFFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer,
                waitingForKey ? "Press a keyboard key or ESC to cancel" : "J: GUI  |  H: HUD  |  C: Cobweb info",
                center, this.height / 2 + 105, 0xFFB7C5D8);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (waitingForKey) {
            if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
                waitingForKey = false;
                keyTarget = "";
                return true;
            }
            LightState.keyCodes.put(keyTarget, keyCode);
            waitingForKey = false;
            keyTarget = "";
            clearAndInit();
            return true;
        }
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            close();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
