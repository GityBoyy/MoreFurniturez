package org.chubby.morefurniturez;

import com.mrcrayfish.framework.FrameworkSetup;
import com.mrcrayfish.furniture.refurbished.data.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class MoreFurniturez implements ModInitializer, DataGeneratorEntrypoint {
    
    @Override
    public void onInitialize() {
        FrameworkSetup.run();
        CommonClass.init();
    }



    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider(FurnitureModelProvider::new);

    }
}
