package org.chubby.morefurniturez.client.menu;

import com.mrcrayfish.furniture.refurbished.blockentity.IPowerSwitch;
import com.mrcrayfish.furniture.refurbished.inventory.IPowerSwitchMenu;
import com.mrcrayfish.furniture.refurbished.inventory.SimpleContainerMenu;
import com.mrcrayfish.furniture.refurbished.platform.Services;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import org.chubby.morefurniturez.common.init.MenuInit;
import org.jetbrains.annotations.Nullable;

public class SolarPanelMenu extends SimpleContainerMenu implements IPowerSwitchMenu {

    public SolarPanelMenu(int windowId, Inventory playerInventory) {
        this(windowId, playerInventory, new SimpleContainer(0), new SimpleContainerData(6));
    }
    private final ContainerData data;
    public SolarPanelMenu(int windowId, Inventory playerInv, Container container, ContainerData data) {
        super(MenuInit.SOLAR_PANEL_MENU.get(), windowId, container);
        checkContainerSize(container,0);
        checkContainerDataCount(data,6);
        container.startOpen(playerInv.player);
        this.data = data;
        this.addPlayerInventorySlots(8, 84, playerInv);
        this.addDataSlots(data);
    }



    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }


    public int getEnergy() {
        return this.data.get(0);
    }

    public int getTotalEnergy() {
        return this.data.get(1);
    }

    public boolean isEnabled() {
        return this.data.get(2) != 0;
    }

    public boolean isOverloaded() {
        return this.data.get(3) != 0;
    }

    public boolean isPowered() {
        return this.data.get(4) != 0;
    }

    public int getNodeCount() {
        return this.data.get(5);
    }

    public void toggle() {
        Container var2 = this.container;
        if (var2 instanceof IPowerSwitch powerSwitch) {
            powerSwitch.togglePower();
        }

    }

}
