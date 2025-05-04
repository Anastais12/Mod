package com.cl1ppz12.strike.util;

import com.cl1ppz12.strike.Strike;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> NEEDS_AMETHYST_TOOL = createTag("needs_amethyst_tool");
        public static final TagKey<Block> INCORRECT_AMETHYST_TOOL = createTag("incorrect_amethyst_tool");
        public static final TagKey<Block> INCORRECT_SCYTHE_TOOL = createTag("incorrect_scythe_tool");

        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(Strike.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> TRANSFORMABLE_ITEMS = createTag("transformable_items");

        public static final TagKey<Item> AMETHYST_TOOL_MATERIALS = createTag("amethyst_tool_materials");
        public static final TagKey<Item> SCYTHE_TOOL_MATERIALS = createTag("scythe_tool_materials");

        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(Strike.MOD_ID, name));
        }
    }
}
