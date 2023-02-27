package cn.evolvefield.mods.immortal.core.api.net;

import cn.evolvefield.mods.immortal.core.api.data.DaoData;
import cn.evolvefield.mods.immortal.core.api.data.PlayerData;
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
public enum PlayerPacketType {

	DEFAULT((byte)0, (server, player, data) -> true),

	MONEY((byte)1, PlayerPacketType::money),


//	REFUND((byte)3, PacketType::refund)
	;

	private static final Map<Byte, PlayerPacketType> TYPES = Maths.enumLookupMap(PlayerPacketType.values(), PlayerPacketType::id);
	private final byte id;
	private final PlayerPacketFunction function;

	private PlayerPacketType(final byte id, PlayerPacketFunction function) {
		this.id = id;
		this.function = function;
	}


	private static boolean money(final MinecraftServer server, final ServerPlayer player, final PlayerData data) {
		if(data.moneyPoints() >= 1) {
			data.addMoneyPoints(-1);
			return true;
		} else {
			return false;
		}
	}

//	private static boolean refund(final MinecraftServer server, final ServerPlayer player, final PlayerData data) {
//		if(data.refundPoints() >= 1) {
//			data.addRefundPoints(-1);
//			data.addSkillPoints(1);
//			return true;
//		} else {
//			return false;
//		}
//	}


	public static PlayerPacketType fromId(final byte id) {
		return TYPES.getOrDefault(id, DEFAULT);
	}

	public byte id() {
		return this.id;
	}


	public boolean test(final MinecraftServer server, final ServerPlayer player, final PlayerData data) {
		return this.function.apply(server, player, data);
	}


	@Override
	public String toString() {
		return String.valueOf(this.id);
	}

	@FunctionalInterface
	public static interface PlayerPacketFunction {
		boolean apply(final MinecraftServer server, final ServerPlayer player, final PlayerData playerData);
	}


}
