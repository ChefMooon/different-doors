package com.chefmooon.differentdoors.client.renderer.fabric;

import com.chefmooon.differentdoors.common.block.LargeDoorBlock;
import com.chefmooon.differentdoors.common.block.entity.LargeDoorBlockEntity;
import com.chefmooon.differentdoors.common.util.TextUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;

public class LargeDoorBlockEntityRendererImpl implements BlockEntityRenderer<LargeDoorBlockEntity> {
    public LargeDoorBlockEntityRendererImpl(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(LargeDoorBlockEntity entity, float partialTick, PoseStack pose, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        float slide = Mth.lerp(partialTick, entity.lastSlideTicks(), entity.slideTicks()) / 81.0f;
        BlockState state = entity.getBlockState();
        Direction direction = state.getValue(LargeDoorBlock.FACING);
        Minecraft minecraft = Minecraft.getInstance();
        BakedModel model = minecraft.getBlockRenderer().getBlockModel(state);

        pose.translate(0.5f, 1, 0.5f);
        pose.mulPose(Axis.YP.rotationDegrees(direction.toYRot()));
        pose.translate(-0.5f, 0, -0.5f);

        pose.translate(slide, 0, 0.0625f);
        if (direction.getAxis() == Direction.Axis.Z) {
            pose.translate(0, 0, 0.6875f);
        }

        minecraft.getBlockRenderer().getModelRenderer().renderModel(pose.last(),
                bufferSource.getBuffer(Sheets.cutoutBlockSheet()),
                state,
                model,
                1f, 1f, 1f,
                packedLight, packedOverlay);

        pose.translate(-slide - slide, 0, 0);

        ResourceLocation location = TextUtil.res("block/%s_flipped".formatted(BuiltInRegistries.BLOCK.getKey(state.getBlock()).getPath()));
        BakedModel modelFlipped = minecraft.getModelManager().getModel(location);
        minecraft.getBlockRenderer().getModelRenderer().renderModel(
                pose.last(),
                bufferSource.getBuffer(Sheets.cutoutBlockSheet()),
                state,
                modelFlipped,
                1f, 1f, 1f,
                packedLight, packedOverlay);

    }
}
