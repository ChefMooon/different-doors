package com.chefmooon.differentdoors.client.fabric;

import com.chefmooon.differentdoors.client.renderer.fabric.LargeDoorBlockEntityRendererImpl;
import com.chefmooon.differentdoors.common.registry.fabric.ModBlockEntitiesImpl;
import com.chefmooon.differentdoors.common.registry.fabric.ModBlocksImpl;
import com.chefmooon.differentdoors.common.util.TextUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class DifferentDoorsClientImpl implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModelLoadingPlugin.register(ctx -> onRegisterModels(ctx::addModels));

        BlockEntityRenderers.register(ModBlockEntitiesImpl.LARGE_DOOR_VARIANTS, LargeDoorBlockEntityRendererImpl::new);

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(),
                ModBlocksImpl.LARGE_DOOR_VARIANTS.values().stream().map(Supplier::get).toArray(Block[]::new));
    }

    public static void onRegisterModels(Consumer<ResourceLocation> consumer) {
        ModBlocksImpl.LARGE_DOOR_VARIANTS.forEach((doorType, block) -> {
            ResourceLocation location = TextUtil.res("block/%s_flipped".formatted(BuiltInRegistries.BLOCK.getKey(block.get()).getPath()));
            consumer.accept(location);
        });
    }
}
