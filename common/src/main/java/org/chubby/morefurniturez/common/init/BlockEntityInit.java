package org.chubby.morefurniturez.common.init;

import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.framework.api.registry.RegistryEntry;
import com.mrcrayfish.furniture.refurbished.blockentity.FreezerBlockEntity;
import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.chubby.morefurniturez.common.blockentities.SolarPanelBlockEntity;
import org.chubby.morefurniturez.common.util.ModUtils;

@RegistryContainer
public class BlockEntityInit
{
    public static final RegistryEntry<BlockEntityType<SolarPanelBlockEntity>> SOLAR_PANEL =
            RegistryEntry.blockEntity(ModUtils.resource("solar_panel"),SolarPanelBlockEntity::new ,() -> {
        return new Block[]{BlockInit.SOLAR_PANEL_BLOCK.get()};
    });;


}
