package com.yyon.grapplinghook.entities;

import net.minecraft.util.math.BlockPos;

public class Web {
    public BlockPos blockPos;
    public int time;
    public Web(BlockPos blockPos, int time){
        this.blockPos = blockPos;
        this.time = time;
    }
}
