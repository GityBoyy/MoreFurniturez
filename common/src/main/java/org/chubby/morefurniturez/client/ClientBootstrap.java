package org.chubby.morefurniturez.client;


import com.mrcrayfish.furniture.refurbished.client.registration.ScreenRegister;
import org.chubby.morefurniturez.client.screens.SolarPanelScreen;
import org.chubby.morefurniturez.common.init.MenuInit;

public class ClientBootstrap
{
    public static void init()
    {}

    public static void registerScreens(ScreenRegister register)
    {
        register.apply(MenuInit.SOLAR_PANEL_MENU.get(), SolarPanelScreen::new);
    }
}
