package com.chefmooon.differentdoors.common.tag;

import com.chefmooon.differentdoors.common.util.TextUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {

    public static final TagKey<Item> WOODEN_DOUBLE_DOORS_ITEM = getItemTagKey("wooden_double_doors");
    public static final TagKey<Item> DOUBLE_DOORS_ITEM = getItemTagKey("double_doors");

    public static final TagKey<Block> WOODEN_DOUBLE_DOORS = getBlockTagKey("wooden_double_doors");
    public static final TagKey<Block> DOUBLE_DOORS = getBlockTagKey("double_doors");
    public static TagKey<Item> getItemTagKey(String path) {
        return TagKey.create(Registries.ITEM, TextUtil.res(path));
    }
    public static TagKey<Block> getBlockTagKey(String path) {
        return TagKey.create(Registries.BLOCK, TextUtil.res(path));
    }
}
