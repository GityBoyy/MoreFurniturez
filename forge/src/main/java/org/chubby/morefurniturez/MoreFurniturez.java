package org.chubby.morefurniturez;

import com.mrcrayfish.framework.FrameworkSetup;
import com.mrcrayfish.furniture.refurbished.Bootstrap;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.chubby.morefurniturez.client.ClientBootstrap;

@Mod(Constants.MOD_ID)
public class MoreFurniturez {

    public MoreFurniturez() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::onCommonSetup);
        modEventBus.addListener(this::onClientSetup);
    }
    private void onClientSetup(FMLClientSetupEvent event)
    {
        event.enqueueWork(ClientBootstrap::init);
    }
    private void onCommonSetup(FMLCommonSetupEvent event)
    {
        event.enqueueWork(Bootstrap::init);
    }

}