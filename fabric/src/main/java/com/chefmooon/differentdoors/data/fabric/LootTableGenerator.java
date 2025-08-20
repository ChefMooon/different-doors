package com.chefmooon.differentdoors.data.fabric;

import com.chefmooon.differentdoors.common.block.DoubleDoorBlock;
import com.chefmooon.differentdoors.common.block.properties.DoorPartProperty;
import com.chefmooon.differentdoors.common.registry.fabric.ModBlocksImpl;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyBlockState;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.concurrent.CompletableFuture;

public class LootTableGenerator extends FabricBlockLootTableProvider {
    protected LootTableGenerator(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        ModBlocksImpl.DOUBLE_DOOR_VARIANTS.forEach(((doorInfoRecord, blockSupplier) -> dropDoubleDoor(blockSupplier.get())));
//        ModBlocksImpl.METAL_DOUBLE_DOOR_VARIANTS.forEach(((doorInfoRecord, blockSupplier) -> dropDoubleDoor(blockSupplier.get())));

        dropDoubleDoor(ModBlocksImpl.IRON_DOUBLE_DOOR.get());
        dropDoubleDoor(ModBlocksImpl.COPPER_DOUBLE_DOOR.get());
        dropDoubleDoor(ModBlocksImpl.EXPOSED_COPPER_DOUBLE_DOOR.get());
        dropDoubleDoor(ModBlocksImpl.OXIDIZED_COPPER_DOUBLE_DOOR.get());
        dropDoubleDoor(ModBlocksImpl.WEATHERED_COPPER_DOUBLE_DOOR.get());
        dropDoubleDoor(ModBlocksImpl.WAXED_COPPER_DOUBLE_DOOR.get());
        dropDoubleDoor(ModBlocksImpl.WAXED_EXPOSED_COPPER_DOUBLE_DOOR.get());
        dropDoubleDoor(ModBlocksImpl.WAXED_OXIDIZED_COPPER_DOUBLE_DOOR.get());
        dropDoubleDoor(ModBlocksImpl.WAXED_WEATHERED_COPPER_DOUBLE_DOOR.get());
    }

    private void dropDoubleDoor(Block block) {
        this.add(block, LootTable.lootTable().withPool(
                (LootPool.Builder)this.applyExplosionCondition(block, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(block))
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoubleDoorBlock.PART, DoorPartProperty.BOTTOM)))
                        .apply(CopyBlockState.copyState(block).copy(DoubleDoorBlock.SWING))
                )));
    }
}
