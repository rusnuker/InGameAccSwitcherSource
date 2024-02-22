//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine\mcpbot_rip"!

//Decompiled by Procyon!

package the_fireplace.ias.gui;

import net.minecraft.client.resources.*;
import java.io.*;
import ru.vidtu.ias.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.player.*;
import the_fireplace.ias.*;
import ru.vidtu.ias.gui.*;
import net.minecraft.util.text.*;
import com.mojang.util.*;
import net.minecraft.util.*;
import ru.vidtu.ias.account.*;
import net.minecraft.client.*;
import java.nio.charset.*;
import java.util.*;
import net.minecraft.client.gui.*;

public class AccountListScreen extends GuiScreen
{
    private static long nextSkinUpdate;
    private final GuiScreen prev;
    private AccountList list;
    private GuiButton add;
    private GuiButton login;
    private GuiButton loginOffline;
    private GuiButton delete;
    private GuiButton edit;
    private GuiButton reloadSkins;
    private GuiTextField search;
    private String state;
    
    public AccountListScreen(final GuiScreen prev) {
        this.prev = prev;
    }
    
    public void initGui() {
        this.list = new AccountList(this.mc, this.width, this.height);
        this.addButton(this.reloadSkins = new GuiButton(0, 2, 2, 120, 20, I18n.format("ias.listGui.reloadSkins", new Object[0])));
        this.search = new GuiTextField(1, this.fontRenderer, this.width / 2 - 80, 14, 160, 16);
        this.addButton(this.add = new GuiButton(2, this.width / 2 + 4 + 40, this.height - 52, 120, 20, I18n.format("ias.listGui.add", new Object[0])));
        this.addButton(this.login = new GuiButton(3, this.width / 2 - 154 - 10, this.height - 52, 120, 20, I18n.format("ias.listGui.login", new Object[0])));
        this.addButton(this.loginOffline = new GuiButton(4, this.width / 2 - 154 - 10, this.height - 28, 110, 20, I18n.format("ias.listGui.loginOffline", new Object[0])));
        this.addButton(this.edit = new GuiButton(5, this.width / 2 - 40, this.height - 52, 80, 20, I18n.format("ias.listGui.edit", new Object[0])));
        this.addButton(this.delete = new GuiButton(6, this.width / 2 - 50, this.height - 28, 100, 20, I18n.format("ias.listGui.delete", new Object[0])));
        this.addButton(new GuiButton(7, this.width / 2 + 4 + 50, this.height - 28, 110, 20, I18n.format("gui.cancel", new Object[0])));
        this.updateButtons();
        this.search.setGuiResponder((GuiPageButtonList.GuiResponder)new GuiPageButtonList.GuiResponder() {
            public void setEntryValue(final int id, final boolean value) {
            }
            
            public void setEntryValue(final int id, final float value) {
            }
            
            public void setEntryValue(final int id, final String value) {
                AccountListScreen.this.list.updateAccounts(value);
            }
        });
        this.list.updateAccounts(this.search.getText());
    }
    
    public void actionPerformed(final GuiButton button) throws IOException {
        if (button.id == 0) {
            this.reloadSkins();
        }
        else if (button.id == 2) {
            this.add();
        }
        else if (button.id == 3) {
            this.login();
        }
        else if (button.id == 4) {
            this.loginOffline();
        }
        else if (button.id == 5) {
            this.edit();
        }
        else if (button.id == 6) {
            this.delete();
        }
        else if (button.id == 7) {
            this.mc.displayGuiScreen(this.prev);
        }
        super.actionPerformed(button);
    }
    
    public void mouseClicked(final int mx, final int my, final int btn) throws IOException {
        if (this.list.mouseClicked(mx, my, btn) || this.search.mouseClicked(mx, my, btn)) {
            return;
        }
        super.mouseClicked(mx, my, btn);
    }
    
    public void handleMouseInput() throws IOException {
        this.list.handleMouseInput();
        super.handleMouseInput();
    }
    
    public void updateScreen() {
        this.search.updateCursorCounter();
        this.updateButtons();
    }
    
    public void onGuiClosed() {
        Config.save(this.mc.gameDir.toPath());
    }
    
    public void drawScreen(final int mx, final int my, final float delta) {
        this.drawDefaultBackground();
        this.list.drawScreen(mx, my, delta);
        this.search.drawTextBox();
        if (this.search.getText().isEmpty()) {
            this.drawString(this.fontRenderer, I18n.format("ias.listGui.search", new Object[0]), this.search.x + 4, this.search.y + (this.search.height - 8) / 2, -8355712);
        }
        super.drawScreen(mx, my, delta);
        this.drawCenteredString(this.fontRenderer, "In-Game Account Switcher", this.width / 2, 4, -1);
        if (this.list.selectedElement() >= 0) {
            this.mc.getTextureManager().bindTexture(this.list.entries.get(this.list.selectedElement()).skin());
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            final boolean slim = this.list.entries.get(this.list.selectedElement()).slimSkin();
            GlStateManager.pushMatrix();
            GlStateManager.scale(4.0f, 4.0f, 4.0f);
            GlStateManager.translate(1.0, this.height / 8.0 - 16.0 - 4.0, 0.0);
            GuiScreen.drawModalRectWithCustomSizedTexture(4, 0, 8.0f, 8.0f, 8, 8, 64.0f, 64.0f);
            GuiScreen.drawModalRectWithCustomSizedTexture(4, 8, 20.0f, 20.0f, 8, 12, 64.0f, 64.0f);
            GuiScreen.drawModalRectWithCustomSizedTexture((int)(slim ? 1 : 0), 8, 44.0f, 20.0f, slim ? 3 : 4, 12, 64.0f, 64.0f);
            GuiScreen.drawModalRectWithCustomSizedTexture(12, 8, 36.0f, 52.0f, slim ? 3 : 4, 12, 64.0f, 64.0f);
            GuiScreen.drawModalRectWithCustomSizedTexture(4, 20, 4.0f, 20.0f, 4, 12, 64.0f, 64.0f);
            GuiScreen.drawModalRectWithCustomSizedTexture(8, 20, 20.0f, 52.0f, 4, 12, 64.0f, 64.0f);
            if (this.mc.gameSettings.getModelParts().contains(EnumPlayerModelParts.HAT)) {
                GuiScreen.drawModalRectWithCustomSizedTexture(4, 0, 40.0f, 8.0f, 8, 8, 64.0f, 64.0f);
            }
            if (this.mc.gameSettings.getModelParts().contains(EnumPlayerModelParts.RIGHT_SLEEVE)) {
                GuiScreen.drawModalRectWithCustomSizedTexture((int)(slim ? 1 : 0), 8, 44.0f, 36.0f, slim ? 3 : 4, 12, 64.0f, 64.0f);
            }
            if (this.mc.gameSettings.getModelParts().contains(EnumPlayerModelParts.LEFT_SLEEVE)) {
                GuiScreen.drawModalRectWithCustomSizedTexture(12, 8, 52.0f, 52.0f, slim ? 3 : 4, 12, 64.0f, 64.0f);
            }
            if (this.mc.gameSettings.getModelParts().contains(EnumPlayerModelParts.RIGHT_PANTS_LEG)) {
                GuiScreen.drawModalRectWithCustomSizedTexture(4, 20, 4.0f, 36.0f, 4, 12, 64.0f, 64.0f);
            }
            if (this.mc.gameSettings.getModelParts().contains(EnumPlayerModelParts.LEFT_PANTS_LEG)) {
                GuiScreen.drawModalRectWithCustomSizedTexture(8, 20, 4.0f, 52.0f, 4, 12, 64.0f, 64.0f);
            }
            GlStateManager.popMatrix();
        }
        if (this.state != null) {
            this.drawCenteredString(this.fontRenderer, this.state, this.width / 2, this.height - 62, -26368);
        }
    }
    
    private void reloadSkins() {
        if (this.list.entries.isEmpty() || System.currentTimeMillis() <= AccountListScreen.nextSkinUpdate || this.state != null) {
            return;
        }
        IAS.SKIN_CACHE.clear();
        this.list.updateAccounts(this.search.getText());
        AccountListScreen.nextSkinUpdate = System.currentTimeMillis() + 15000L;
    }
    
    private void login() {
        if (this.list.selectedElement() < 0 || this.state != null) {
            return;
        }
        final Account acc = this.list.entries.get(this.list.selectedElement()).account();
        this.updateButtons();
        this.state = "";
        Minecraft mc;
        final GuiScreen guiScreen;
        acc.login((s, o) -> this.state = I18n.format(s, o)).whenComplete((d, t) -> {
            this.state = null;
            if (t != null) {
                this.mc.addScheduledTask(() -> {
                    mc = this.mc;
                    new IASAlertScreen(() -> this.mc.displayGuiScreen((GuiScreen)this), TextFormatting.RED + I18n.format("ias.error", new Object[0]), String.valueOf(t));
                    mc.displayGuiScreen(guiScreen);
                });
            }
            else {
                this.mc.addScheduledTask(() -> this.mc.session = new Session(d.name(), UUIDTypeAdapter.fromUUID(d.uuid()), d.accessToken(), d.userType()));
            }
        });
    }
    
    private void loginOffline() {
        if (this.list.selectedElement() < 0 || this.state != null) {
            return;
        }
        final Account acc = this.list.entries.get(this.list.selectedElement()).account();
        this.mc.session = new Session(acc.name(), UUIDTypeAdapter.fromUUID(UUID.nameUUIDFromBytes("OfflinePlayer".concat(acc.name()).getBytes(StandardCharsets.UTF_8))), "0", "legacy");
    }
    
    private void add() {
        if (this.state != null) {
            return;
        }
        this.mc.displayGuiScreen((GuiScreen)new LoginScreen(this, I18n.format("ias.loginGui.add", new Object[0]), I18n.format("ias.loginGui.add.button", new Object[0]), I18n.format("ias.loginGui.add.button.tooltip", new Object[0]), acc -> {
            Config.accounts.add(acc);
            Config.save(this.mc.gameDir.toPath());
            this.list.updateAccounts(this.search.getText());
        }));
    }
    
    public void edit() {
        if (this.list.selectedElement() < 0 || this.state != null) {
            return;
        }
        final Account acc = this.list.entries.get(this.list.selectedElement()).account();
        this.mc.displayGuiScreen((GuiScreen)new LoginScreen(this, I18n.format("ias.loginGui.edit", new Object[0]), I18n.format("ias.loginGui.edit.button", new Object[0]), I18n.format("ias.loginGui.edit.button.tooltip", new Object[0]), newAcc -> {
            Config.accounts.set(Config.accounts.indexOf(acc), newAcc);
            Config.save(this.mc.gameDir.toPath());
        }));
    }
    
    public void delete() {
        if (this.list.selectedElement() < 0 || this.state != null) {
            return;
        }
        final Account acc = this.list.entries.get(this.list.selectedElement()).account();
        if (isShiftKeyDown()) {
            Config.accounts.remove(acc);
            Config.save(this.mc.gameDir.toPath());
            this.updateButtons();
            this.list.updateAccounts(this.search.getText());
            return;
        }
        this.mc.displayGuiScreen((GuiScreen)new GuiYesNo((b, id) -> {
            if (b) {
                Config.accounts.remove(acc);
                this.updateButtons();
                this.list.updateAccounts(this.search.getText());
            }
            this.mc.displayGuiScreen((GuiScreen)this);
        }, I18n.format("ias.deleteGui.title", new Object[0]), I18n.format("ias.deleteGui.text", new Object[] { acc.name() }), 0));
    }
    
    private void updateButtons() {
        this.login.enabled = (this.list.selectedElement() >= 0 && this.state == null);
        this.loginOffline.enabled = (this.list.selectedElement() >= 0);
        this.add.enabled = (this.state == null);
        this.edit.enabled = (this.list.selectedElement() >= 0 && this.state == null);
        this.delete.enabled = (this.list.selectedElement() >= 0 && this.state == null);
        this.reloadSkins.enabled = (this.list.selectedElement() >= 0 && this.state == null && System.currentTimeMillis() > AccountListScreen.nextSkinUpdate);
    }
    
    public void keyTyped(final char c, final int key) throws IOException {
        if (this.search.textboxKeyTyped(c, key)) {
            return;
        }
        if (key == 1) {
            this.mc.displayGuiScreen(this.prev);
            return;
        }
        if (key == 63 || key == 19) {
            this.reloadSkins();
            return;
        }
        if (key == 28 || key == 156) {
            if (GuiScreen.isShiftKeyDown()) {
                this.loginOffline();
            }
            else {
                this.login();
            }
            return;
        }
        if (key == 30 || key == 13 || key == 78) {
            this.add();
            return;
        }
        if (key == 52 || key == 181) {
            this.edit();
            return;
        }
        if (key == 211 || key == 12 || key == 74) {
            this.delete();
            return;
        }
        super.keyTyped(c, key);
    }
    
    static {
        AccountListScreen.nextSkinUpdate = System.currentTimeMillis();
    }
}
