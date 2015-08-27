package org.apollo.game.io;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apollo.cache.def.EquipmentDefinition;

/**
 * A class that parses the {@code data/equipment-[release].dat} file to create an array of {@link EquipmentDefinition}s.
 *
 * @author Graham
 */
public final class EquipmentDefinitionParser {

	/**
	 * The input stream.
	 */
	private final InputStream is;

	/**
	 * Creates the equipment definition parser.
	 *
	 * @param is The input stream.
	 */
	public EquipmentDefinitionParser(InputStream is) {
		this.is = is;
	}

	/**
	 * Parses the input stream.
	 *
	 * @return The equipment definition array.
	 * @throws IOException If an I/O error occurs.
	 */
	public EquipmentDefinition[] parse() throws IOException {
		DataInputStream dis = new DataInputStream(is);

		int count = dis.readShort() & 0xFFFF;
		EquipmentDefinition[] definitions = new EquipmentDefinition[count];

		for (int id = 0; id < count; id++) {
			int slot = dis.readByte() & 0xFF;
			if (slot != 0xFF) {
				boolean twoHanded = dis.readBoolean();
				boolean fullBody = dis.readBoolean();
				boolean fullHat = dis.readBoolean();
				boolean fullMask = dis.readBoolean();
				int attack = dis.readByte() & 0xFF;
				int strength = dis.readByte() & 0xFF;
				int defence = dis.readByte() & 0xFF;
				int ranged = dis.readByte() & 0xFF;
				int magic = dis.readByte() & 0xFF;

				EquipmentDefinition definition = new EquipmentDefinition(id);
				definition.setLevels(attack, strength, defence, ranged, magic);
				definition.setSlot(slot);
				definition.setFlags(twoHanded, fullBody, fullHat, fullMask);

				definitions[id] = definition;
			}
		}

		return definitions;
	}

}