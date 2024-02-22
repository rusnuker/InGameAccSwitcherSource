//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine\mcpbot_rip"!

//Decompiled by Procyon!

package ru.vidtu.ias.gui;

import net.minecraftforge.fml.client.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import java.util.*;

public class IASGuiFactory implements IModGuiFactory
{
    public void initialize(final Minecraft minecraft) {
    }
    
    public boolean hasConfigGui() {
        return true;
    }
    
    public GuiScreen createConfigGui(final GuiScreen guiScreen) {
        return (GuiScreen)new IASConfigScreen(guiScreen);
    }
    
    public Set<IModGuiFactory.RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return Collections.emptySet();
    }
}
