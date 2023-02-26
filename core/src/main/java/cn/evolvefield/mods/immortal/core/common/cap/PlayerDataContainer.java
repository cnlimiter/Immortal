package cn.evolvefield.mods.immortal.core.common.cap;

import cn.evolvefield.mods.immortal.core.api.CoreApi;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;

/**
 * Project: Immortal-fabric
 * Author: cnlimiter
 * Date: 2023/2/26 10:57
 * Description:
 */
public final class PlayerDataContainer implements EntityComponentInitializer {

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(CoreApi.PLAYER_DATA, PlayerDataManager::new, RespawnCopyStrategy.ALWAYS_COPY);
    }
}
