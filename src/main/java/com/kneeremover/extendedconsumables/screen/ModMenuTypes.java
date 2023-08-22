package com.kneeremover.extendedconsumables.screen;

import com.kneeremover.extendedconsumables.ExtendedConsumables;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
	public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.CONTAINERS, ExtendedConsumables.MOD_ID);

	public static final RegistryObject<MenuType<ConsumableTableMenu>> CONSUMABLE_TABLE_MENU = registerMenuType(ConsumableTableMenu::new, "consumable_table_menu");
	public static final RegistryObject<MenuType<StackCombinerMenu>> STACK_COMBINER_MENU = registerMenuType(StackCombinerMenu::new, "stack_combiner_menu");

	private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory, @SuppressWarnings("SameParameterValue") String name) {
		return MENUS.register(name, () -> IForgeMenuType.create(factory));
	}

	public static void register(IEventBus eventBus) {
		MENUS.register(eventBus);
	}
}
