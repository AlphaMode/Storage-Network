package mrriegel.storagenetwork.init;

import mrriegel.storagenetwork.StorageNetwork;
import mrriegel.storagenetwork.items.ItemRemote;
import mrriegel.storagenetwork.items.ItemUpgrade;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(value = StorageNetwork.MODID)
public class ModItems {
	public static final Item upgrade = new ItemUpgrade();
	public static final Item remote = new ItemRemote();

	public static void init() {
		GameRegistry.registerItem(upgrade, "upgrade");
		GameRegistry.registerItem(remote, "remote");
	}

}