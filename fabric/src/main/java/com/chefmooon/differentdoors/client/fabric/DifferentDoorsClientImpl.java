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
        ModItemPropertiesImpl.addCustomItemProperties();
//        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocksImpl.SIMPLE_LARGE_DOOR.get(), RenderType.cutout()); // TODO : remove after testing
    }
}
