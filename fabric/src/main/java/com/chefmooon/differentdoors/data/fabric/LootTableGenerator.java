package com.chefmooon.differentdoors.data.fabric;

import com.chefmooon.differentdoors.common.block.LargeDoorBlock;
import com.chefmooon.differentdoors.common.block.properties.DoorPartProperty;
import com.chefmooon.differentdoors.common.registry.fabric.ModBlocksImpl;
import com.chefmooon.differentdoors.common.registry.fabric.ModDataComponentTypesImpl;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyBlockState;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class LootTableGenerator extends FabricBlockLootTableProvider {
    protected LootTableGenerator(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        ModBlocksImpl.LARGE_DOOR_VARIANTS.forEach(((doorType, blockSupplier) -> dropLargeDoor(blockSupplier.get())));
    }

    private void dropLargeDoor(Block block) {
        this.add(block, LootTable.lootTable().withPool(
                (LootPool.Builder)this.applyExplosionCondition(block, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(block))
                        .apply(CopyBlockState.copyState(block).copy(LargeDoorBlock.SWING))
                )));
    }
}
