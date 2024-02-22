//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine\mcpbot_rip"!

//Decompiled by Procyon!

package ru.vidtu.ias.gui;

import net.minecraftforge.fml.client.config.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.*;
import ru.vidtu.ias.*;
import java.util.*;
import java.io.*;

public class IASConfigScreen extends GuiScreen
{
    private final GuiScreen prev;
    private GuiCheckBox titleScreenText;
    private GuiTextField titleScreenTextX;
    private GuiTextField titleScreenTextY;
    private GuiButton titleScreenTextAlignment;
    private GuiCheckBox titleScreenButton;
    private GuiTextField titleScreenButtonX;
    private GuiTextField titleScreenButtonY;
    private GuiCheckBox multiplayerScreenButton;
    private GuiTextField multiplayerScreenButtonX;
    private GuiTextField multiplayerScreenButtonY;
    
    public IASConfigScreen(final GuiScreen prev) {
        this.prev = prev;
    }
    
    public void initGui() {
        this.addButton(new GuiButton(0, this.width / 2 - 75, this.height - 28, 150, 20, I18n.format("gui.done", new Object[0])));
        this.addButton((GuiButton)(this.titleScreenText = new GuiCheckBox(1, 5, 24, I18n.format("ias.configGui.titleScreenText", new Object[0]), Config.titleScreenText)));
        this.titleScreenTextX = new GuiTextField(2, this.fontRenderer, 35 + this.fontRenderer.getStringWidth(I18n.format("ias.configGui.titleScreenText", new Object[0])), 20, 50, 20);
        this.titleScreenTextY = new GuiTextField(3, this.fontRenderer, 35 + this.fontRenderer.getStringWidth(I18n.format("ias.configGui.titleScreenText", new Object[0])) + 54, 20, 50, 20);
        this.addButton(this.titleScreenTextAlignment = new GuiButton(4, 35 + this.fontRenderer.getStringWidth(I18n.format("ias.configGui.titleScreenText", new Object[0])) + 108, 20, this.fontRenderer.getStringWidth(I18n.format("ias.configGui.titleScreenText.alignment", new Object[] { I18n.format(Config.titleScreenTextAlignment.key(), new Object[0]) })) + 20, 20, I18n.format("ias.configGui.titleScreenText.alignment", new Object[] { I18n.format(Config.titleScreenTextAlignment.key(), new Object[0]) })));
        this.addButton((GuiButton)(this.titleScreenButton = new GuiCheckBox(5, 5, 48, I18n.format("ias.configGui.titleScreenButton", new Object[0]), Config.titleScreenButton)));
        this.titleScreenButtonX = new GuiTextField(6, this.fontRenderer, 35 + this.fontRenderer.getStringWidth(I18n.format("ias.configGui.titleScreenButton", new Object[0])), 44, 50, 20);
        this.titleScreenButtonY = new GuiTextField(7, this.fontRenderer, 35 + this.fontRenderer.getStringWidth(I18n.format("ias.configGui.titleScreenButton", new Object[0])) + 54, 44, 50, 20);
        this.addButton((GuiButton)(this.multiplayerScreenButton = new GuiCheckBox(8, 5, 72, I18n.format("ias.configGui.multiplayerScreenButton", new Object[0]), Config.multiplayerScreenButton)));
        this.multiplayerScreenButtonX = new GuiTextField(9, this.fontRenderer, 35 + this.fontRenderer.getStringWidth(I18n.format("ias.configGui.multiplayerScreenButton", new Object[0])), 68, 50, 20);
        this.multiplayerScreenButtonY = new GuiTextField(10, this.fontRenderer, 35 + this.fontRenderer.getStringWidth(I18n.format("ias.configGui.multiplayerScreenButton", new Object[0])) + 54, 68, 50, 20);
        this.titleScreenTextX.setText(Objects.toString(Config.titleScreenTextX, ""));
        this.titleScreenTextY.setText(Objects.toString(Config.titleScreenTextY, ""));
        this.titleScreenButtonX.setText(Objects.toString(Config.titleScreenButtonX, ""));
        this.titleScreenButtonY.setText(Objects.toString(Config.titleScreenButtonY, ""));
        this.multiplayerScreenButtonX.setText(Objects.toString(Config.multiplayerScreenButtonX, ""));
        this.multiplayerScreenButtonY.setText(Objects.toString(Config.multiplayerScreenButtonY, ""));
        this.updateScreen();
    }
    
    protected void actionPerformed(final GuiButton button) throws IOException {
        if (button.id == 0) {
            this.mc.displayGuiScreen(this.prev);
        }
        else if (button.id == 4) {
            this.changeAlignment();
        }
        super.actionPerformed(button);
    }
    
    private void changeAlignment() {
        int i = Config.titleScreenTextAlignment.ordinal() + 1;
        if (i >= Config.Alignment.values().length) {
            i = 0;
        }
        Config.titleScreenTextAlignment = Config.Alignment.values()[i];
        this.titleScreenTextAlignment.displayString = I18n.format("ias.configGui.titleScreenText.alignment", new Object[] { I18n.format(Config.titleScreenTextAlignment.key(), new Object[0]) });
        this.titleScreenTextAlignment.setWidth(this.fontRenderer.getStringWidth(this.titleScreenTextAlignment.displayString) + 20);
    }
    
    protected void mouseClicked(final int mx, final int my, final int btn) throws IOException {
        if (this.titleScreenTextX.mouseClicked(mx, my, btn) || this.titleScreenTextY.mouseClicked(mx, my, btn) || this.titleScreenButtonX.mouseClicked(mx, my, btn) || this.titleScreenButtonY.mouseClicked(mx, my, btn) || this.multiplayerScreenButtonX.mouseClicked(mx, my, btn) || this.multiplayerScreenButtonY.mouseClicked(mx, my, btn)) {
            return;
        }
        super.mouseClicked(mx, my, btn);
    }
    
    public void keyTyped(final char c, final int key) throws IOException {
        if (key == 1) {
            this.mc.displayGuiScreen(this.prev);
            return;
        }
        if (this.titleScreenTextX.textboxKeyTyped(c, key) || this.titleScreenTextY.textboxKeyTyped(c, key) || this.titleScreenButtonX.textboxKeyTyped(c, key) || this.titleScreenButtonY.textboxKeyTyped(c, key) || this.multiplayerScreenButtonX.textboxKeyTyped(c, key) || this.multiplayerScreenButtonY.textboxKeyTyped(c, key)) {
            return;
        }
        super.keyTyped(c, key);
    }
    
    public void onGuiClosed() {
        Config.titleScreenText = this.titleScreenText.isChecked();
        Config.titleScreenTextX = (this.titleScreenTextX.getText().trim().isEmpty() ? null : this.titleScreenTextX.getText());
        Config.titleScreenTextY = (this.titleScreenTextY.getText().trim().isEmpty() ? null : this.titleScreenTextY.getText());
        Config.titleScreenButton = this.titleScreenButton.isChecked();
        Config.titleScreenButtonX = (this.titleScreenButtonX.getText().trim().isEmpty() ? null : this.titleScreenButtonX.getText());
        Config.titleScreenButtonY = (this.titleScreenButtonY.getText().trim().isEmpty() ? null : this.titleScreenButtonY.getText());
        Config.multiplayerScreenButton = this.multiplayerScreenButton.isChecked();
        Config.multiplayerScreenButtonX = (this.multiplayerScreenButtonX.getText().trim().isEmpty() ? null : this.multiplayerScreenButtonX.getText());
        Config.multiplayerScreenButtonY = (this.multiplayerScreenButtonY.getText().trim().isEmpty() ? null : this.multiplayerScreenButtonY.getText());
        Config.save(this.mc.gameDir.toPath());
    }
    
    public void updateScreen() {
        this.titleScreenTextX.setVisible(this.titleScreenText.isChecked());
        this.titleScreenTextY.setVisible(this.titleScreenText.isChecked());
        this.titleScreenTextAlignment.visible = this.titleScreenText.isChecked();
        this.titleScreenButtonX.setVisible(this.titleScreenButton.isChecked());
        this.titleScreenButtonY.setVisible(this.titleScreenButton.isChecked());
        this.multiplayerScreenButtonX.setVisible(this.multiplayerScreenButton.isChecked());
        this.multiplayerScreenButtonY.setVisible(this.multiplayerScreenButton.isChecked());
        this.titleScreenTextX.updateCursorCounter();
        this.titleScreenTextY.updateCursorCounter();
        this.titleScreenButtonX.updateCursorCounter();
        this.titleScreenButtonY.updateCursorCounter();
        this.multiplayerScreenButtonX.updateCursorCounter();
        this.multiplayerScreenButtonY.updateCursorCounter();
        super.updateScreen();
    }
    
    public void drawScreen(final int mx, final int my, final float delta) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, I18n.format("ias.configGui.title", new Object[0]), this.width / 2, 5, -1);
        this.titleScreenTextX.drawTextBox();
        this.titleScreenTextY.drawTextBox();
        this.titleScreenButtonX.drawTextBox();
        this.titleScreenButtonY.drawTextBox();
        this.multiplayerScreenButtonX.drawTextBox();
        this.multiplayerScreenButtonY.drawTextBox();
        if (this.titleScreenTextX.getVisible() && this.titleScreenTextX.getText().isEmpty()) {
            this.drawString(this.fontRenderer, "X", this.titleScreenTextX.x + 4, this.titleScreenTextX.y + (this.titleScreenTextX.height - 8) / 2, -8355712);
        }
        if (this.titleScreenTextY.getVisible() && this.titleScreenTextY.getText().isEmpty()) {
            this.drawString(this.fontRenderer, "Y", this.titleScreenTextY.x + 4, this.titleScreenTextY.y + (this.titleScreenTextY.height - 8) / 2, -8355712);
        }
        if (this.titleScreenButtonX.getVisible() && this.titleScreenButtonX.getText().isEmpty()) {
            this.drawString(this.fontRenderer, "X", this.titleScreenButtonX.x + 4, this.titleScreenButtonX.y + (this.titleScreenButtonX.height - 8) / 2, -8355712);
        }
        if (this.titleScreenButtonY.getVisible() && this.titleScreenButtonY.getText().isEmpty()) {
            this.drawString(this.fontRenderer, "Y", this.titleScreenButtonY.x + 4, this.titleScreenButtonY.y + (this.titleScreenButtonY.height - 8) / 2, -8355712);
        }
        if (this.multiplayerScreenButtonX.getVisible() && this.multiplayerScreenButtonX.getText().isEmpty()) {
            this.drawString(this.fontRenderer, "X", this.multiplayerScreenButtonX.x + 4, this.multiplayerScreenButtonX.y + (this.multiplayerScreenButtonX.height - 8) / 2, -8355712);
        }
        if (this.multiplayerScreenButtonY.getVisible() && this.multiplayerScreenButtonY.getText().isEmpty()) {
            this.drawString(this.fontRenderer, "Y", this.multiplayerScreenButtonY.x + 4, this.multiplayerScreenButtonY.y + (this.multiplayerScreenButtonY.height - 8) / 2, -8355712);
        }
        super.drawScreen(mx, my, delta);
    }
}
