package com.kneeremover.extendedconsumables;

import com.kneeremover.extendedconsumables.block.ModBlocks;
import com.kneeremover.extendedconsumables.block.entity.ModBlockEntities;
import com.kneeremover.extendedconsumables.effect.ModEffects;
import com.kneeremover.extendedconsumables.entity.ModEntities;
import com.kneeremover.extendedconsumables.item.ModItems;
import com.kneeremover.extendedconsumables.potion.ModPotions;
import com.kneeremover.extendedconsumables.recipe.ModRecipes;
import com.kneeremover.extendedconsumables.screen.ConsumableTableScreen;
import com.kneeremover.extendedconsumables.screen.ModMenuTypes;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ExtendedConsumables.MOD_ID)
public class ExtendedConsumables {
    public static final String MOD_ID = "extendedconsumables";
    private static final Logger LOGGER = LogUtils.getLogger();

    public ExtendedConsumables() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(eventBus);
        ModBlocks.register(eventBus);

        ModEntities.register(eventBus);

        ModBlockEntities.register(eventBus);
        ModMenuTypes.register(eventBus);

        ModEffects.register(eventBus);
        ModPotions.register(eventBus);

        ModRecipes.register(eventBus);

        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CONSUMABLE_TABLE.get(), RenderType.translucent());

        // Register GUIs
        MenuScreens.register(ModMenuTypes.CONSUMABLE_TABLE_MENU.get(), ConsumableTableScreen::new);
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
}
