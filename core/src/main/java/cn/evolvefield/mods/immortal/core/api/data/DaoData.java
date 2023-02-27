package cn.evolvefield.mods.immortal.core.api.data;

import cn.evolvefield.mods.immortal.core.common.cap.dao.DaoType;
import dev.onyxstudios.cca.api.v3.component.Component;

/**
 * Project: Immortal-fabric
 * Author: cnlimiter
 * Date: 2023/2/27 12:56
 * Description:
 */
public interface DaoData extends Component {
    double get(final DaoType type);
    void set(final DaoType type, final double valueIn);
    void add(final DaoType type, final double valueIn);
    void reset();
    void addDaoPoints(final int pointsIn);
    int daoPoints();

}
