package dev.dubhe.lucky.block;

import net.minecraft.world.level.block.Block;

public class LuckyBlock extends Block {
    private final int defaultLucky;
    public LuckyBlock(int defaultLucky, Properties properties) {
        super(properties);
        this.defaultLucky = defaultLucky;
    }

    public int getDefaultLucky() {
        return defaultLucky;
    }
}
