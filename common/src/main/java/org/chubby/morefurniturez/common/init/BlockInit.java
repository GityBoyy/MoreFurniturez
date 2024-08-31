package org.chubby.morefurniturez.common.init;

import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.framework.api.registry.RegistryEntry;
import com.mrcrayfish.furniture.refurbished.block.MetalType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.chubby.morefurniturez.common.blocks.SolarPanelBlock;
import org.chubby.morefurniturez.common.util.ModUtils;

@RegistryContainer
public class BlockInit
{
    public static final RegistryEntry<SolarPanelBlock> SOLAR_PANEL_BLOCK = RegistryEntry.blockWithItem(ModUtils.resource("solar_panel"),
            () -> new SolarPanelBlock(MetalType.LIGHT, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));
}
