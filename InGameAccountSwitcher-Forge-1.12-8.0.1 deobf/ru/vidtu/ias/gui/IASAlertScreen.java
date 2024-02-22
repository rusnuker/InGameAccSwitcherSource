//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine\mcpbot_rip"!

//Decompiled by Procyon!

package ru.vidtu.ias.gui;

import java.util.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;
import java.io.*;

public class IASAlertScreen extends GuiScreen
{
    private final Runnable ok;
    private final String title;
    private final String text;
    private List<String> textList;
    
    public IASAlertScreen(final Runnable ok, final String title, final String text) {
        this.ok = ok;
        this.title = title;
        this.text = text;
    }
    
    public void initGui() {
        this.addButton(new GuiButton(0, this.width / 2 - 50, this.height - 28, 100, 20, I18n.format("gui.back", new Object[0])));
        this.textList = (List<String>)this.fontRenderer.listFormattedStringToWidth(this.text, this.width - 50);
        super.initGui();
    }
    
    protected void actionPerformed(final GuiButton button) throws IOException {
        if (button.id == 0) {
            this.ok.run();
        }
        super.actionPerformed(button);
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, this.title, this.width / 2, 30, -1);
        if (this.textList != null) {
            for (int i = 0; i < this.textList.size(); ++i) {
                this.drawCenteredString(this.fontRenderer, (String)this.textList.get(i), this.width / 2, 50 + i * 10, -1);
            }
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
