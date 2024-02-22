//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine\mcpbot_rip"!

//Decompiled by Procyon!

package the_fireplace.ias;

import net.minecraftforge.fml.common.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.common.*;
import net.minecraft.client.*;
import net.minecraftforge.client.event.*;
import ru.vidtu.ias.*;
import net.minecraft.client.renderer.*;
import net.minecraftforge.fml.common.eventhandler.*;
import the_fireplace.ias.gui.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.*;
import java.util.*;

@Mod(modid = "ias", name = "In-Game Account Switcher", version = "D7.29 :P", clientSideOnly = true, useMetadata = true, guiFactory = "ru.vidtu.ias.gui.IASGuiFactory", acceptedMinecraftVersions = "1.12.2", updateJSON = "https://github.com/The-Fireplace-Minecraft-Mods/In-Game-Account-Switcher/raw/main/updater-forge.json")
public class IAS
{
    public static final ResourceLocation IAS_BUTTON;
    public static final Map<UUID, ResourceLocation> SKIN_CACHE;
    private static int tx;
    private static int ty;
    private static GuiButton button;
    
    @Mod.EventHandler
    public void onPreInit(final FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register((Object)this);
        Config.load(Minecraft.getMinecraft().gameDir.toPath());
    }
    
    @SubscribeEvent
    public void onScreenInit(final GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.getGui() instanceof GuiMultiplayer && Config.multiplayerScreenButton) {
            int bx = event.getGui().width / 2 + 4 + 76 + 79;
            int by = event.getGui().height - 28;
            try {
                bx = (int)Expression.parseWidthHeight(Config.titleScreenButtonX, event.getGui().width, event.getGui().height);
                by = (int)Expression.parseWidthHeight(Config.titleScreenButtonY, event.getGui().width, event.getGui().height);
            }
            catch (Throwable t) {
                bx = event.getGui().width / 2 + 4 + 76 + 79;
                by = event.getGui().height - 28;
            }
            event.getButtonList().add(IAS.button = (GuiButton)new GuiButtonImage(104027, bx, by, 20, 20, 0, 0, 20, IAS.IAS_BUTTON) {
                public void drawButton(final Minecraft mc, final int mouseX, final int mouseY, final float partialTicks) {
                    GlStateManager.color(1.0f, 1.0f, 1.0f);
                    super.drawButton(mc, mouseX, mouseY, partialTicks);
                }
            });
        }
        if (event.getGui() instanceof GuiMainMenu) {
            if (Config.titleScreenButton) {
                int bx = event.getGui().width / 2 + 104;
                int by = event.getGui().height / 4 + 48 + 72 - 24;
                try {
                    bx = (int)Expression.parseWidthHeight(Config.titleScreenButtonX, event.getGui().width, event.getGui().height);
                    by = (int)Expression.parseWidthHeight(Config.titleScreenButtonY, event.getGui().width, event.getGui().height);
                }
                catch (Throwable t) {
                    bx = event.getGui().width / 2 + 104;
                    by = event.getGui().height / 4 + 48 + 72 - 24;
                }
                event.getButtonList().add(IAS.button = (GuiButton)new GuiButtonImage(104027, bx, by, 20, 20, 0, 0, 20, IAS.IAS_BUTTON) {
                    public void drawButton(final Minecraft mc, final int mouseX, final int mouseY, final float partialTicks) {
                        GlStateManager.color(1.0f, 1.0f, 1.0f);
                        super.drawButton(mc, mouseX, mouseY, partialTicks);
                    }
                });
            }
            if (Config.titleScreenText) {
                try {
                    IAS.tx = (int)Expression.parseWidthHeight(Config.titleScreenTextX, event.getGui().width, event.getGui().height);
                    IAS.ty = (int)Expression.parseWidthHeight(Config.titleScreenTextY, event.getGui().width, event.getGui().height);
                }
                catch (Throwable t2) {
                    IAS.tx = event.getGui().width / 2;
                    IAS.ty = event.getGui().height / 4 + 48 + 72 + 12 + 22;
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onScreenAction(final GuiScreenEvent.ActionPerformedEvent.Post event) {
        if ((event.getGui() instanceof GuiMainMenu || event.getGui() instanceof GuiMultiplayer) && event.getButton().id == 104027) {
            event.getGui().mc.displayGuiScreen((GuiScreen)new AccountListScreen(event.getGui()));
        }
    }
    
    @SubscribeEvent
    public void onScreenRender(final GuiScreenEvent.DrawScreenEvent.Post event) {
        if (event.getGui() instanceof GuiMainMenu && Config.titleScreenText) {
            if (Config.titleScreenTextAlignment == Config.Alignment.LEFT) {
                event.getGui().drawString(event.getGui().mc.fontRenderer, I18n.format("ias.title", new Object[] { event.getGui().mc.getSession().getUsername() }), IAS.tx, IAS.ty, -3372920);
            }
            else if (Config.titleScreenTextAlignment == Config.Alignment.RIGHT) {
                event.getGui().drawString(event.getGui().mc.fontRenderer, I18n.format("ias.title", new Object[] { event.getGui().mc.getSession().getUsername() }), IAS.tx - event.getGui().mc.fontRenderer.getStringWidth(I18n.format("ias.title", new Object[] { event.getGui().mc.getSession().getUsername() })), IAS.ty, -3372920);
            }
            else {
                event.getGui().drawCenteredString(event.getGui().mc.fontRenderer, I18n.format("ias.title", new Object[] { event.getGui().mc.getSession().getUsername() }), IAS.tx, IAS.ty, -3372920);
            }
        }
        if (IAS.button != null && event.getGui().buttonList.contains(IAS.button) && IAS.button.isMouseOver()) {
            event.getGui().drawHoveringText("In-Game Account Switcher", event.getMouseX(), event.getMouseY());
        }
    }
    
    static {
        IAS_BUTTON = new ResourceLocation("ias", "textures/gui/iasbutton.png");
        SKIN_CACHE = new HashMap<UUID, ResourceLocation>();
    }
}
