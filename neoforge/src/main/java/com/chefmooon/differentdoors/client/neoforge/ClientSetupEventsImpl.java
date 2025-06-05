package com.chefmooon.differentdoors.client.neoforge;

import com.chefmooon.differentdoors.DifferentDoors;
import com.chefmooon.differentdoors.client.renderer.neoforge.LargeDoorBlockEntityRendererImpl;
import com.chefmooon.differentdoors.common.registry.neoforge.ModBlockEntitiesImpl;
import com.chefmooon.differentdoors.common.registry.neoforge.ModBlocksImpl;
import com.chefmooon.differentdoors.common.util.TextUtil;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;

import java.util.function.Consumer;

@EventBusSubscriber(modid = DifferentDoors.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetupEventsImpl {
    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntitiesImpl.LARGE_DOOR_VARIANTS.get(), LargeDoorBlockEntityRendererImpl::new);
    }

    @SubscribeEvent
    public static void modelLoading(ModelEvent.RegisterAdditional event) {
        onRegisterModels(event::register);
    }

    public static void onRegisterModels(Consumer<ModelResourceLocation> consumer) {
        ModBlocksImpl.LARGE_DOOR_VARIANTS.forEach((doorType, block) -> {
            ModelResourceLocation location = new ModelResourceLocation(TextUtil.res("block/%s_flipped".formatted(BuiltInRegistries.BLOCK.getKey(block.get()).getPath())), "standalone");
            consumer.accept(location);
        });
    }
}
