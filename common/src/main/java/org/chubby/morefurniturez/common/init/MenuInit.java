package org.chubby.morefurniturez.common.init;

import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.framework.api.registry.RegistryEntry;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.component.CustomData;
import org.chubby.morefurniturez.client.menu.SolarPanelMenu;
import org.chubby.morefurniturez.common.util.ModUtils;

@RegistryContainer
public class MenuInit
{
    public static final RegistryEntry<MenuType<SolarPanelMenu>> SOLAR_PANEL_MENU = RegistryEntry.menuType(ModUtils.resource("solar_panel_menu"),
           SolarPanelMenu::new);
}
