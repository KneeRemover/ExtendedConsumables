package com.kneeremover.extendedconsumables.item.custom;


import com.google.common.collect.Lists;
import com.kneeremover.extendedconsumables.item.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Vanishable;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Predicate;

public class ECCrossbow extends CrossbowItem implements Vanishable {


	public ECCrossbow(Item.Properties pProperties) {
		super(pProperties);
	}

	@Override
	public @NotNull Predicate<ItemStack> getSupportedHeldProjectiles() {
		return ARROW_OR_FIREWORK.or((itemStack) -> itemStack.is(ModItems.TIPPED_BOLT_ITEM.get())).or((itemStack) -> itemStack.is(ModItems.DULL_TIPPED_BOLT_ITEM.get()));
	}
	public static List<ItemStack> getChargedProjectiles(ItemStack pCrossbowStack) {
		List<ItemStack> list = Lists.newArrayList();
		CompoundTag compoundtag = pCrossbowStack.getTag();
		if (compoundtag != null && compoundtag.contains("ChargedProjectiles", 9)) {
			ListTag listtag = compoundtag.getList("ChargedProjectiles", 10);
			if (listtag != null) {
				for(int i = 0; i < listtag.size(); ++i) {
					CompoundTag compoundtag1 = listtag.getCompound(i);
					list.add(ItemStack.of(compoundtag1));
				}
			}
		}

		return list;
	}
}

