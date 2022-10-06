package me.drex.vanillapermissions.mc119.mixin.bypass.player_limit;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.authlib.GameProfile;
import me.drex.vanillapermissions.Constants;
import me.drex.vanillapermissions.event.permission.OfflinePermissions;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.server.dedicated.DedicatedPlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Restriction(
        require = @Condition(
                value = "minecraft",
                versionPredicates = ">=1.14"
        )
)
@Mixin(DedicatedPlayerList.class)
public abstract class DedicatedPlayerListMixin {

    @ModifyExpressionValue(
            method = "canBypassPlayerLimit",
            at = @At(
                    value = "INVOKE", target = "Lnet/minecraft/server/players/ServerOpList;canBypassPlayerLimit(Lcom/mojang/authlib/GameProfile;)Z"
            )
    )
    public boolean vanillaPermissions_addBypassPlayerLimitPermission(boolean original, GameProfile gameProfile) {
       return OfflinePermissions.check(gameProfile, Constants.BYPASS_PLAYER_LIMIT, original).join();
    }

}
