package com.cl1ppz12.strike.item;

import com.cl1ppz12.strike.Strike;
import com.cl1ppz12.strike.item.custom.Scythe;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModItems {
    public static Item register(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {

        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Strike.MOD_ID, name));

        Item item = itemFactory.apply(settings.registryKey(itemKey));

        Registry.register(Registries.ITEM, itemKey, item);

        return item;
    }


    public static final Item AMETHYST_INGOT = register("amethyst_ingot", Item::new, new Item.Settings());
    public static final Item SCYTHE = register("scythe", Scythe::new, new Item.Settings());





    public static final RegistryKey<ItemGroup> CUSTOM_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(Strike.MOD_ID, "item_group"));
    public static final ItemGroup STRIKE_TAB = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.AMETHYST_INGOT))
            .displayName(Text.translatable("itemGroup.strike"))
            .build();

    public static void initialize() {

        Registry.register(Registries.ITEM_GROUP, CUSTOM_ITEM_GROUP_KEY, STRIKE_TAB  );

        ItemGroupEvents.modifyEntriesEvent(CUSTOM_ITEM_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(ModItems.SCYTHE);
            itemGroup.add(ModItems.AMETHYST_INGOT);
        });

    }
}
