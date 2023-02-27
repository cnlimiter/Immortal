package cn.evolvefield.mods.immortal.core.api.net;

import cn.evolvefield.mods.immortal.core.api.data.DaoData;
import com.github.clevernucleus.dataattributes.api.util.Maths;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import java.util.Map;

/**
 * Holds four PacketTypes that tell the server what to do with the incoming client-sent modify attributes packet.
 *
 * @author CleverNucleus
 *
 */
public enum DaoPacketType {

	DEFAULT((byte)0, (server, player, data) -> true),
	DAO((byte)1, DaoPacketType::dao),

	;

	private static final Map<Byte, DaoPacketType> TYPES = Maths.enumLookupMap(DaoPacketType.values(), DaoPacketType::id);
	private final byte id;
	private final DaoPacketFunction function;

	private DaoPacketType(final byte id, DaoPacketFunction function) {
		this.id = id;
		this.function = function;
	}



	private static boolean dao(final MinecraftServer server, final ServerPlayer player, final DaoData data) {
		if(data.daoPoints() >= 1) {
			data.addDaoPoints(-1);
			return true;
		} else {
			return false;
		}
	}


	public static DaoPacketType fromId(final byte id) {
		return TYPES.getOrDefault(id, DEFAULT);
	}


	public byte id() {
		return this.id;
	}

	public boolean test(final MinecraftServer server, final ServerPlayer player, final DaoData data) {
		return this.function.apply(server, player, data);
	}

	@Override
	public String toString() {
		return String.valueOf(this.id);
	}

	@FunctionalInterface
	public static interface DaoPacketFunction {
		boolean apply(final MinecraftServer server, final ServerPlayer player, final DaoData playerData);
	}
}
