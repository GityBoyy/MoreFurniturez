package org.chubby.morefurniturez.client.screens;

import com.mrcrayfish.furniture.refurbished.Components;
import com.mrcrayfish.furniture.refurbished.Config;
import com.mrcrayfish.furniture.refurbished.client.gui.widget.IconButton;
import com.mrcrayfish.furniture.refurbished.client.gui.widget.OnOffSlider;
import com.mrcrayfish.furniture.refurbished.client.util.ScreenHelper;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageTogglePower;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.chubby.morefurniturez.client.menu.SolarPanelMenu;

public class SolarPanelScreen extends AbstractContainerScreen<SolarPanelMenu> {
    private static final ResourceLocation TEXTURE = Utils.resource("textures/gui/container/electricity_generator.png");
    protected OnOffSlider slider;
    public SolarPanelScreen(SolarPanelMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }
    
    protected void init() {
        super.init();
        this.slider = (OnOffSlider)this.addRenderableWidget(new OnOffSlider(this.leftPos + this.imageWidth - 22 - 6, this.topPos + 5, Utils.translation("gui", "generator_toggle", new Object[0]), (btn) -> {
            Network.getPlay().sendToServer(new MessageTogglePower());
        }));
    }
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.slider.setEnabled((this.menu).isEnabled());
        super.render(graphics, mouseX, mouseY, partialTick);
        this.renderTooltip(graphics, mouseX, mouseY);
    }

    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        graphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        if (((SolarPanelMenu)this.menu).getEnergy() > 0 && ((SolarPanelMenu)this.menu).getTotalEnergy() > 0) {
            float normalEnergy = (float)((SolarPanelMenu)this.menu).getEnergy() / (float)((SolarPanelMenu)this.menu).getTotalEnergy();
            int v = (int)Math.ceil((double)(14.0F * normalEnergy));
            graphics.blit(TEXTURE, this.leftPos + 26, this.topPos + 25 + 14 - v, 176, 14 - v, 14, v);
        }

        SolarPanelScreen.Status status = this.getStatus();
        graphics.blit(IconButton.ICON_TEXTURES, this.leftPos + 66, this.topPos + 29, (float)status.iconU, (float)status.iconV, 10, 10, 64, 64);
        graphics.blit(IconButton.ICON_TEXTURES, this.leftPos + 66, this.topPos + 46, 0.0F, 10.0F, 10, 10, 64, 64);
        if (((SolarPanelMenu)this.menu).getEnergy() > 0 && ((SolarPanelMenu)this.menu).getTotalEnergy() > 0 && ScreenHelper.isMouseWithinBounds((double)mouseX, (double)mouseY, this.leftPos + 26, this.topPos + 25, 14, 14)) {
            this.setTooltipForNextRenderPass(Utils.translation("gui", "progress", new Object[]{((SolarPanelMenu)this.menu).getEnergy(), Components.GUI_SLASH, ((SolarPanelMenu)this.menu).getTotalEnergy()}));
        }

    }

    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        super.renderLabels(graphics, mouseX, mouseY);
        SolarPanelScreen.Status status = this.getStatus();
        graphics.drawString(this.font, status.label, 80, 30, status.textColour, true);
        Pair<Component, Integer> pair = this.getNodeCount();
        graphics.drawString(this.font, (Component)pair.left(), 80, 47, (Integer)pair.right(), true);
    }

    private Pair<Component, Integer> getNodeCount() {
        int nodeCount = ((SolarPanelMenu)this.menu).getNodeCount();
        int maxNodeCount = (Integer) Config.SERVER.electricity.maximumNodesInNetwork.get();
        Component label = Utils.translation("gui", "node_count", new Object[]{nodeCount, maxNodeCount});
        int textColour = nodeCount > maxNodeCount ? -3983818 : -1;
        return Pair.of(label, textColour);
    }

    private SolarPanelScreen.Status getStatus() {
        if (((SolarPanelMenu)this.menu).isOverloaded()) {
            return SolarPanelScreen.Status.OVERLOADED;
        } else {
            if (((SolarPanelMenu)this.menu).isEnabled()) {
                if (((SolarPanelMenu)this.menu).isPowered()) {
                    return SolarPanelScreen.Status.ONLINE;
                }

                if (((SolarPanelMenu)this.menu).getEnergy() == 0) {
                    return SolarPanelScreen.Status.NO_FUEL;
                }
            }

            return SolarPanelScreen.Status.OFFLINE;
        }
    }

    private static enum Status {
        ONLINE(-8799453, 50, 0, Utils.translation("gui", "status.online", new Object[0])),
        OFFLINE(-3983818, 40, 0, Utils.translation("gui", "status.offline", new Object[0])),
        OVERLOADED(-2711764, 30, 0, Utils.translation("gui", "status.overloaded", new Object[0])),
        NO_FUEL(-2711764, 10, 10, Utils.translation("gui", "status.no_fuel", new Object[0]));

        private final int textColour;
        private final int iconU;
        private final int iconV;
        private final Component label;

        private Status(int textColour, int iconU, int iconV, Component label) {
            this.textColour = textColour;
            this.iconU = iconU;
            this.iconV = iconV;
            this.label = label;
        }
    }


}
