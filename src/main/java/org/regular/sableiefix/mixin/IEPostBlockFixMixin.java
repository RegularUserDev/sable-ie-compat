package org.regular.sableiefix.mixin;

import blusunrize.immersiveengineering.common.blocks.generic.PostBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PostBlock.class)
public class IEPostBlockFixMixin {

    @Inject(
            method = "hasArmFor(Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/world/level/block/Block;)Z",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private static void efs$preventSableNPE(BlockPos center, Direction side, BlockGetter world, Block BlockExpected, CallbackInfoReturnable<Boolean> cir) {
        if (world == null) {
            cir.setReturnValue(false);
            return;
        }

        BlockState centerState = world.getBlockState(center);

        if (centerState == null) {
            cir.setReturnValue(false);
            return;
        }

        if (centerState.getBlock() == BlockExpected) {
            BlockState armState = world.getBlockState(center.relative(side));

            if (armState != null && armState.getBlock() == BlockExpected) {
                cir.setReturnValue(true);
                return;
            }
        }

        cir.setReturnValue(false);
    }
}