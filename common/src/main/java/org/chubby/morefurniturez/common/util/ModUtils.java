package org.chubby.morefurniturez.common.util;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.chubby.morefurniturez.Constants;

public class ModUtils
{
    public static ResourceLocation resource(String name) {
        return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name);
    }

    public static MutableComponent translation(String category, String path, Object... params) {
        return Component.translatable(String.format("%s.%s.%s", category, Constants.MOD_ID, path), params);
    }
}
