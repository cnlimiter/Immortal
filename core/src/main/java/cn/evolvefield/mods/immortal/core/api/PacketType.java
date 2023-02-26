package cn.evolvefield.mods.immortal.core.api;

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
public enum PacketType {
	/**
	 * Does nothing extra - just allows the server to modify PlayerEx's attribute modifiers.
	 */
	DEFAULT((byte)0, (server, player, data) -> true),
	/**
	 * Only allows modification if the player's experience levels are greater than or equal to the required xp to level up.
	 * Also removes those experience levels from the player and adds 1 skill point.
	 */
//	LEVEL((byte)1, PacketType::level),
	/**
	 * Only allows modification if the player's skill points are greater than or equal to 1.
	 * Also subtracts one skill points from the player.
	 */
	SKILL((byte)2, PacketType::skill),
	/**
	 * Only allows modification if the player's refund points are greater than or equal to 1.
	 * Also subtracts one refund points and adds one skill point.
	 */
	REFUND((byte)3, PacketType::refund);

	private static final Map<Byte, PacketType> TYPES = Maths.enumLookupMap(PacketType.values(), v -> v.id());
	private final byte id;
	private final PacketFunction function;

	private PacketType(final byte id, PacketFunction function) {
		this.id = id;
		this.function = function;
	}

//	private static boolean level(final MinecraftServer server, final ServerPlayer player, final PlayerData data) {
//		ExConfig config = ExAPI.getConfig();
//		int requiredXp = config.requiredXp(player);
//		int skillPoint = config.skillPointsPerLevelUp();
//
//		if(player.experienceLevel >= requiredXp) {
//			player.addExperienceLevels(-requiredXp);
//			data.addSkillPoints(skillPoint);
//			return true;
//		} else {
//			return false;
//		}
//	}

	private static boolean skill(final MinecraftServer server, final ServerPlayer player, final PlayerData data) {
		if(data.skillPoints() >= 1) {
			data.addSkillPoints(-1);
			return true;
		} else {
			return false;
		}
	}

	private static boolean refund(final MinecraftServer server, final ServerPlayer player, final PlayerData data) {
		if(data.refundPoints() >= 1) {
			data.addRefundPoints(-1);
			data.addSkillPoints(1);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Gets the correct PacketType from the input. Or {@link #DEFAULT}.
	 * @param id
	 * @return
	 */
	public static PacketType fromId(final byte id) {
		return TYPES.getOrDefault(id, DEFAULT);
	}

	/**
	 * @return The PacketType's byte id.
	 */
	public byte id() {
		return this.id;
	}

	/**
	 *
	 * @param server
	 * @param player
	 * @param data
	 * @return
	 */
	public boolean test(final MinecraftServer server, final ServerPlayer player, final PlayerData data) {
		return this.function.apply(server, player, data);
	}

	@Override
	public String toString() {
		return String.valueOf(this.id);
	}

	@FunctionalInterface
	public static interface PacketFunction {
		boolean apply(final MinecraftServer server, final ServerPlayer player, final PlayerData playerData);
	}
}
