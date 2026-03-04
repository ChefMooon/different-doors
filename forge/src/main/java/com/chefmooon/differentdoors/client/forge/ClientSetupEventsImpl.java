package com.chefmooon.differentdoors.client.forge;

import com.chefmooon.differentdoors.DifferentDoors;
import com.chefmooon.differentdoors.common.util.forge.ModItemPropertiesImpl;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = DifferentDoors.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetupEventsImpl {
    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
//        event.registerBlockEntityRenderer(ModBlockEntitiesImpl.LARGE_DOOR_VARIANTS.get(), LargeDoorBlockEntityRendererImpl::new);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ModItemPropertiesImpl.addCustomItemProperties();
        });
    }

    @SubscribeEvent
    public static void modelLoading(ModelEvent.RegisterAdditional event) {
//        onRegisterModels(event::register);
    }
//
//    public static void onRegisterModels(Consumer<ModelResourceLocation> consumer) {
//        ModBlocksImpl.LARGE_DOOR_VARIANTS.forEach((doorType, block) -> {
//            ModelResourceLocation location = new ModelResourceLocation(TextUtil.res("block/%s_flipped".formatted(BuiltInRegistries.BLOCK.getKey(block.get()).getPath())), "standalone");
//            consumer.accept(location);
//        });
//    }
}
