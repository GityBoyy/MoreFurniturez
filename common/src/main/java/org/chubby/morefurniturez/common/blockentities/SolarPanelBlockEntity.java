package org.chubby.morefurniturez.common.blockentities;

import com.mrcrayfish.furniture.refurbished.Config;
import com.mrcrayfish.furniture.refurbished.blockentity.*;
import com.mrcrayfish.furniture.refurbished.inventory.BuildableContainerData;
import com.mrcrayfish.furniture.refurbished.inventory.ElectricityGeneratorMenu;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.chubby.morefurniturez.client.menu.SolarPanelMenu;
import org.chubby.morefurniturez.common.blockentities.enums.EnvStates;
import org.chubby.morefurniturez.common.blocks.SolarPanelBlock;
import org.chubby.morefurniturez.common.init.BlockEntityInit;

public class SolarPanelBlockEntity extends ElectricitySourceLootBlockEntity implements IProcessingBlock, IPowerSwitch {

    protected int energyAmt;
    protected final int DEFAULT_ENERGY_AMOUNT = 100;
    protected ContainerData data;
    protected boolean enabled;
    protected int nodeCount;
    private static EnvStates envStates;

    public SolarPanelBlockEntity(BlockPos pos, BlockState state)
    {
        this(pos,state,1);
    }
    public SolarPanelBlockEntity(BlockPos pos, BlockState state,int contSize) {
        super(BlockEntityInit.SOLAR_PANEL.get(), pos, state,contSize);
        this.energyAmt = DEFAULT_ENERGY_AMOUNT;
        this.data = new BuildableContainerData((builder) -> {
            builder.add(0, () -> {
                return (int) this.energyAmt;
            }, (value) -> {
            });
            builder.add(1, () -> {
                return (int) this.DEFAULT_ENERGY_AMOUNT;
            }, (value) -> {
            });
            builder.add(2, () -> {
                return this.enabled ? 1 : 0;
            }, (value) -> {
            });
            builder.add(3, () -> {
                return this.overloaded ? 1 : 0;
            }, (value) -> {
            });
            builder.add(4, () -> {
                return this.isNodePowered() ? 1 : 0;
            }, (value) -> {
            });
            builder.add(5, () -> {
                return this.nodeCount;
            }, (value) -> {
            });
        });
    }

    public int getNodeMaximumConnections() {
        return (Integer) Config.SERVER.electricity.maximumLinksPerElectricityGenerator.get();
    }

    protected Component getDefaultName() {
        return Utils.translation("container", "electricity_generator", new Object[0]);
    }

    protected AbstractContainerMenu createMenu(int windowId, Inventory playerInventory) {
        if (!this.enabled) {
            this.searchNodeNetwork(false);
        }

        return new SolarPanelMenu(windowId, playerInventory, this, this.data);
    }

    public boolean isMatchingContainerMenu(AbstractContainerMenu menu) {
        boolean var10000;
        if (menu instanceof ElectricityGeneratorMenu generator) {
            if (generator.getContainer() == this) {
                var10000 = true;
                return var10000;
            }
        }

        var10000 = false;
        return var10000;
    }
    @Override
    public boolean isNodePowered() {
        if (this.level == null) return false;
        return this.level.getBlockState(this.getBlockPos()).getValue(SolarPanelBlock.POWERED);
    }

    @Override
    public void setNodePowered(boolean powered) {
        if (this.level == null) return;

        this.energyAmt = Mth.clamp(this.energyAmt, envStates.getMin(), envStates.getMax());
        this.level.setBlock(this.getBlockPos(), this.level.getBlockState(this.getBlockPos()).setValue(SolarPanelBlock.POWERED, powered), 3);
    }

    public static void updateEnv(Level level, BlockPos pos) {
        if (level.isClientSide) return;

        if (level.isDay()) {
            envStates = EnvStates.DAY;
        } else if (level.isThundering()) {
            envStates = EnvStates.THUNDER;
        } else if (level.isRainingAt(pos)) {
            envStates = EnvStates.RAIN;
        } else if (level.isRaining()) {
            envStates = EnvStates.CLOUDY;
        } else if (level.isNight()) {
            envStates = EnvStates.NIGHT;
        } else {
            envStates = EnvStates.DAY;
        }
    }

    public static void clientTick(Level level, BlockPos blockPos, BlockState blockState, SolarPanelBlockEntity solarPanelBlockEntity) {
        if (!level.isClientSide) {
            updateEnv(level, blockPos);
            solarPanelBlockEntity.setNodePowered(envStates != EnvStates.NIGHT);
        }
    }

    @Override
    public void togglePower() {

    }

    @Override
    public int getEnergy() {
        return (int) this.energyAmt;
    }

    @Override
    public void addEnergy(int amount) {
        this.energyAmt = Mth.clamp(this.energyAmt + amount, 0, this.DEFAULT_ENERGY_AMOUNT);
    }

    @Override
    public boolean requiresEnergy() {
        return true;
    }

    @Override
    public int retrieveEnergy(boolean simulated)
    {
        int energyRetrieved = this.energyAmt;
        if(!simulated)
        {
            return 0;
        }
        return energyRetrieved;
    }

    @Override
    public int updateAndGetTotalProcessingTime() {
        return 0;
    }

    @Override
    public int getTotalProcessingTime() {
        return 0;
    }

    @Override
    public int getProcessingTime() {
        return 0;
    }

    @Override
    public void setProcessingTime(int i) {

    }

    @Override
    public void onCompleteProcess() {

    }

    @Override
    public boolean canProcess() {
        return false;
    }
}
