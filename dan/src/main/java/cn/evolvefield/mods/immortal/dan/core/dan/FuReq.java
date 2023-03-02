package cn.evolvefield.mods.immortal.dan.core.dan;

import cn.evolvefield.mods.immortal.dan.core.cao.FuType;

import javax.annotation.Nullable;

/**
 * Project: Immortal
 * Author: cnlimiter
 * Date: 2023/3/3 2:10
 * Description:
 */
public record FuReq(FuType fu1, int need1, @Nullable FuType fu2, int need2) {

}
