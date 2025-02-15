package dev.dylanxyz.termite.mixin;

import com.github.leawind.thirdperson.ThirdPerson;
import com.github.leawind.thirdperson.ThirdPersonEvents;
import com.github.leawind.thirdperson.ThirdPersonStatus;
import com.github.leawind.thirdperson.core.rotation.RotateTargetEnum;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.valkyrienskies.mod.common.entity.ShipMountingEntity;

@Mixin(LocalPlayer.class)
public class MixinLocalPlayer {
    @Inject(
        method = "startRiding",
        at = @At(value = "RETURN")
    )
    private void termite$startRiding(Entity entity, boolean p_108668_, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue() && entity instanceof ShipMountingEntity) {
            var config = ThirdPerson.getConfig();

            if (ThirdPersonStatus.isRenderingInThirdPerson() && config.is_mod_enabled) {
                ThirdPerson.ENTITY_AGENT.setRotateTarget(RotateTargetEnum.CAMERA_ROTATION);
            }

            config.is_mod_enabled = false;
        }
    }

    @Inject(
        method = "removeVehicle",
        at = @At(value = "RETURN")
    )
    private void termite$removeVehicle(CallbackInfo ci) {
        var config = ThirdPerson.getConfig();

        if (ThirdPersonStatus.isRenderingInThirdPerson() && !config.is_mod_enabled) {
            Minecraft.getInstance().gameRenderer.checkEntityPostEffect(null);
            ThirdPersonEvents.resetPlayer();
        }

        config.is_mod_enabled = true;
    }
}
