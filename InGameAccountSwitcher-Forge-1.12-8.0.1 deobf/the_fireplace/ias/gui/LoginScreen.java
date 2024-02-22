//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine\mcpbot_rip"!

//Decompiled by Procyon!

package the_fireplace.ias.gui;

import java.util.function.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.*;
import java.io.*;
import ru.vidtu.ias.*;
import ru.vidtu.ias.gui.*;
import net.minecraft.util.text.*;
import net.minecraft.client.*;
import java.awt.*;
import java.net.*;
import org.lwjgl.*;
import ru.vidtu.ias.account.*;

public class LoginScreen extends GuiScreen
{
    private final GuiScreen prev;
    private final String title;
    private final String buttonText;
    private final String buttonTip;
    private final Consumer<Account> handler;
    private final MicrosoftAuthCallback callback;
    private GuiTextField username;
    private GuiButton offline;
    private GuiButton microsoft;
    private String state;
    
    public LoginScreen(final GuiScreen prev, final String title, final String buttonText, final String buttonTip, final Consumer<Account> handler) {
        this.callback = new MicrosoftAuthCallback();
        this.prev = prev;
        this.title = title;
        this.buttonText = buttonText;
        this.buttonTip = buttonTip;
        this.handler = handler;
    }
    
    public void initGui() {
        super.initGui();
        this.addButton(this.offline = new GuiButton(0, this.width / 2 - 152, this.height - 28, 150, 20, this.buttonText));
        this.offline.enabled = false;
        this.addButton(new GuiButton(1, this.width / 2 + 2, this.height - 28, 150, 20, I18n.format("gui.cancel", new Object[0])));
        (this.username = new GuiTextField(2, this.fontRenderer, this.width / 2 - 100, this.height / 2 - 12, 200, 20)).setMaxStringLength(16);
        this.addButton(this.microsoft = new GuiButton(3, this.width / 2 - 50, this.height / 2 + 12, 100, 20, I18n.format("ias.loginGui.microsoft", new Object[0])));
    }
    
    public void actionPerformed(final GuiButton button) throws IOException {
        if (button.id == 0) {
            this.loginOffline();
        }
        else if (button.id == 1) {
            this.mc.displayGuiScreen(this.prev);
        }
        else if (button.id == 3) {
            this.loginMicrosoft();
        }
        super.actionPerformed(button);
    }
    
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        if (this.username.mouseClicked(mouseX, mouseY, mouseButton)) {
            return;
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    public void drawScreen(final int mx, final int my, final float delta) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, this.title, this.width / 2, 5, -1);
        this.drawCenteredString(this.fontRenderer, I18n.format("ias.loginGui.nickname", new Object[0]), this.width / 2, this.height / 2 - 22, -1);
        if (this.state != null) {
            this.drawCenteredString(this.fontRenderer, this.state, this.width / 2, this.height / 3 * 2, -26368);
            this.drawCenteredString(this.fontRenderer, SharedIAS.LOADING[(int)(System.currentTimeMillis() / 50L % SharedIAS.LOADING.length)], this.width / 2, this.height / 3 * 2 + 10, -26368);
        }
        this.username.drawTextBox();
        super.drawScreen(mx, my, delta);
        if (this.offline.isMouseOver()) {
            this.drawHoveringText(this.fontRenderer.listFormattedStringToWidth(this.buttonTip, 150), mx, my);
        }
    }
    
    public void keyTyped(final char c, final int key) throws IOException {
        if (key == 1) {
            this.mc.displayGuiScreen(this.prev);
            return;
        }
        if (this.username.textboxKeyTyped(c, key)) {
            return;
        }
        super.keyTyped(c, key);
    }
    
    public void onGuiClosed() {
        SharedIAS.EXECUTOR.execute(this.callback::close);
        super.onGuiClosed();
    }
    
    public void updateScreen() {
        this.offline.enabled = (!this.username.getText().trim().isEmpty() && this.state == null);
        this.username.setEnabled(this.state == null);
        this.microsoft.enabled = (this.state == null);
        this.username.updateCursorCounter();
        super.updateScreen();
    }
    
    private void loginMicrosoft() {
        this.state = "";
        Minecraft mc;
        final GuiScreen guiScreen;
        SharedIAS.EXECUTOR.execute(() -> {
            this.state = I18n.format("ias.loginGui.microsoft.checkBrowser", new Object[0]);
            this.openURI("https://login.live.com/oauth20_authorize.srf?client_id=54fd49e4-2103-4044-9603-2b028c814ec3&response_type=code&scope=XboxLive.signin%20XboxLive.offline_access&redirect_uri=http://localhost:59125&prompt=select_account");
            this.callback.start((s, o) -> this.state = I18n.format(s, o), I18n.format("ias.loginGui.microsoft.canClose", new Object[0])).whenComplete((acc, t) -> {
                if (this.mc.currentScreen == this) {
                    if (t != null) {
                        this.mc.addScheduledTask(() -> {
                            mc = this.mc;
                            new IASAlertScreen(() -> this.mc.displayGuiScreen(this.prev), TextFormatting.RED + I18n.format("ias.error", new Object[0]), String.valueOf(t));
                            mc.displayGuiScreen(guiScreen);
                        });
                    }
                    else if (acc == null) {
                        this.mc.addScheduledTask(() -> this.mc.displayGuiScreen(this.prev));
                    }
                    else {
                        this.mc.addScheduledTask(() -> {
                            this.handler.accept(acc);
                            this.mc.displayGuiScreen(this.prev);
                        });
                    }
                }
            });
        });
    }
    
    private void loginOffline() {
        this.state = "";
        final Account account;
        SharedIAS.EXECUTOR.execute(() -> {
            this.state = I18n.format("ias.loginGui.offline.progress", new Object[0]);
            account = (Account)new OfflineAccount(this.username.getText(), Auth.resolveUUID(this.username.getText()));
            this.mc.addScheduledTask(() -> {
                this.handler.accept(account);
                this.mc.displayGuiScreen(this.prev);
            });
        });
    }
    
    private void openURI(final String uri) {
        try {
            Desktop.getDesktop().browse(new URI(uri));
        }
        catch (Throwable t) {
            Sys.openURL(uri);
        }
    }
}
