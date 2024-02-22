//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine\mcpbot_rip"!

//Decompiled by Procyon!

package the_fireplace.ias.gui;

import net.minecraft.client.*;
import ru.vidtu.ias.*;
import ru.vidtu.ias.account.*;
import net.minecraft.util.*;
import the_fireplace.ias.*;
import net.minecraft.client.resources.*;
import ru.vidtu.ias.legacy.*;
import java.awt.image.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.client.audio.*;
import java.util.*;

public class AccountList extends GuiListExtended
{
    public final List<AccountEntry> entries;
    
    public AccountList(final Minecraft mc, final int width, final int height) {
        super(mc, width, height, 32, height - 64, 14);
        this.entries = new ArrayList<AccountEntry>();
    }
    
    public void updateAccounts(final String query) {
        this.entries.clear();
        Config.accounts.stream().filter(acc -> query.trim().isEmpty() || acc.name().toLowerCase(Locale.ROOT).startsWith(query.toLowerCase(Locale.ROOT))).forEach(acc -> this.entries.add(new AccountEntry(acc)));
        this.selectedElement = (this.entries.isEmpty() ? -1 : 0);
    }
    
    public void swap(final int first, final int second) {
        final Account account = Config.accounts.get(first);
        Config.accounts.set(first, Config.accounts.get(second));
        Config.accounts.set(second, account);
        Config.save(this.mc.gameDir.toPath());
        final AccountEntry entry = this.entries.get(first);
        this.entries.set(first, this.entries.get(second));
        this.entries.set(second, entry);
        this.selectedElement = second;
    }
    
    public int getSize() {
        return this.entries.size();
    }
    
    public GuiListExtended.IGuiListEntry getListEntry(final int index) {
        return (GuiListExtended.IGuiListEntry)this.entries.get(index);
    }
    
    public int selectedElement() {
        return this.selectedElement;
    }
    
    protected boolean isSelected(final int slotIndex) {
        return slotIndex == this.selectedElement;
    }
    
    public class AccountEntry implements GuiListExtended.IGuiListEntry
    {
        private final Account account;
        private ResourceLocation skin;
        private boolean slimSkin;
        
        public AccountEntry(final Account account) {
            this.account = account;
            if (IAS.SKIN_CACHE.containsKey(account.uuid())) {
                this.skin = IAS.SKIN_CACHE.get(account.uuid());
                return;
            }
            this.skin = DefaultPlayerSkin.getDefaultSkin(account.uuid());
            this.slimSkin = DefaultPlayerSkin.getSkinType(account.uuid()).equalsIgnoreCase("slim");
            BufferedImage skinImage;
            SkinLoader.loadSkin(account.uuid()).thenAccept(en -> {
                if (en != null) {
                    skinImage = new ImageBufferDownload().parseUserSkin((BufferedImage)en.getKey());
                    this.slimSkin = (boolean)en.getValue();
                    AccountList.this.mc.addScheduledTask(() -> {
                        this.skin = AccountList.this.mc.getTextureManager().getDynamicTextureLocation("ias_skin:" + account.uuid().toString().replace("-", ""), new DynamicTexture(skinImage));
                        IAS.SKIN_CACHE.put(account.uuid(), this.skin);
                    });
                }
            });
        }
        
        public Account account() {
            return this.account;
        }
        
        public ResourceLocation skin() {
            return this.skin;
        }
        
        public boolean slimSkin() {
            return this.slimSkin;
        }
        
        public void drawEntry(final int i, final int x, final int y, final int w, final int h, final int mx, final int my, final boolean hover, final float delta) {
            int color = -1;
            if (AccountList.this.mc.getSession().getUsername().equals(this.account.name())) {
                color = 65280;
            }
            AccountList.this.mc.fontRenderer.drawString(this.account.name(), x + 10, y + 1, color);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            AccountList.this.mc.getTextureManager().bindTexture(this.skin());
            GuiScreen.drawModalRectWithCustomSizedTexture(x, y + 1, 8.0f, 8.0f, 8, 8, 64.0f, 64.0f);
            if (AccountList.this.mc.gameSettings.getModelParts().contains(EnumPlayerModelParts.HAT)) {
                GuiScreen.drawModalRectWithCustomSizedTexture(x, y + 1, 40.0f, 8.0f, 8, 8, 64.0f, 64.0f);
            }
            if (AccountList.this.selectedElement == i) {
                AccountList.this.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/server_selection.png"));
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                final boolean movableDown = i + 1 < AccountList.this.entries.size();
                final boolean movableUp = i > 0;
                if (movableDown) {
                    final boolean hoveredDown = mx > x + w - 16 && mx < x + w - 6 && hover;
                    GuiScreen.drawModalRectWithCustomSizedTexture(x + w - 35, y - 18, 48.0f, hoveredDown ? 32.0f : 0.0f, 32, 32, 256.0f, 256.0f);
                }
                if (movableUp) {
                    final boolean hoveredUp = mx > x + w - (movableDown ? 28 : 16) && mx < x + w - (movableDown ? 16 : 6) && hover;
                    GuiScreen.drawModalRectWithCustomSizedTexture(x + w - (movableDown ? 30 : 19), y - 3, 96.0f, hoveredUp ? 32.0f : 0.0f, 32, 32, 256.0f, 256.0f);
                }
            }
        }
        
        public boolean mousePressed(final int i, final int mx, final int my, final int button, final int rx, final int ry) {
            if (button == 0 && AccountList.this.selectedElement == i) {
                final int w = AccountList.this.getListWidth();
                final boolean movableDown = i + 1 < AccountList.this.entries.size();
                final boolean movableUp = i > 0;
                if (movableDown) {
                    final boolean hoveredDown = rx > w - 16 && rx < w - 6;
                    if (hoveredDown) {
                        AccountList.this.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
                        AccountList.this.swap(i, i + 1);
                    }
                }
                if (movableUp) {
                    final boolean hoveredUp = rx > w - (movableDown ? 28 : 16) && rx < w - (movableDown ? 16 : 6);
                    if (hoveredUp) {
                        AccountList.this.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
                        AccountList.this.swap(i, i - 1);
                    }
                }
                return true;
            }
            AccountList.this.selectedElement = i;
            return true;
        }
        
        public void mouseReleased(final int i, final int x, final int y, final int btn, final int rx, final int ry) {
        }
        
        public void updatePosition(final int slotIndex, final int x, final int y, final float partialTicks) {
        }
    }
}
