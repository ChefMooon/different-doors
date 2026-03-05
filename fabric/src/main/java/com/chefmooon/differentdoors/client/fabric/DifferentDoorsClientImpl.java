package com.chefmooon.differentdoors.client.fabric;

import com.chefmooon.differentdoors.common.registry.fabric.ModBlocksImpl;
import com.chefmooon.differentdoors.common.util.fabric.ModItemPropertiesImpl;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class DifferentDoorsClientImpl implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(),
                ModBlocksImpl.DOUBLE_DOOR_VARIANTS.values().stream().map(Supplier::get).toArray(Block[]::new));
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(),
                ModBlocksImpl.IRON_DOUBLE_DOOR.get(),
                ModBlocksImpl.COPPER_DOUBLE_DOOR.get(),
                ModBlocksImpl.EXPOSED_COPPER_DOUBLE_DOOR.get(),
                ModBlocksImpl.OXIDIZED_COPPER_DOUBLE_DOOR.get(),
                ModBlocksImpl.WEATHERED_COPPER_DOUBLE_DOOR.get(),
                ModBlocksImpl.WAXED_COPPER_DOUBLE_DOOR.get(),
                ModBlocksImpl.WAXED_EXPOSED_COPPER_DOUBLE_DOOR.get(),
                ModBlocksImpl.WAXED_OXIDIZED_COPPER_DOUBLE_DOOR.get(),
                ModBlocksImpl.WAXED_WEATHERED_COPPER_DOUBLE_DOOR.get());

        ModItemPropertiesImpl.addCustomItemProperties();
    }
}
