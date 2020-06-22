package com.lothrazar.storagenetwork.registry;

import com.lothrazar.storagenetwork.StorageNetwork;
import com.lothrazar.storagenetwork.block.cable.BlockCable;
import com.lothrazar.storagenetwork.block.cable.TileCable;
import com.lothrazar.storagenetwork.block.cable.export.ContainerCableExportFilter;
import com.lothrazar.storagenetwork.block.cable.input.TileCableIO;
import com.lothrazar.storagenetwork.block.cable.inputfilter.ContainerCableImportFilter;
import com.lothrazar.storagenetwork.block.cable.inputfilter.TileCableImportFilter;
import com.lothrazar.storagenetwork.block.cable.storagefilter.ContainerCableFilter;
import com.lothrazar.storagenetwork.block.cable.storagefilter.TileCableFilter;
import com.lothrazar.storagenetwork.block.cablelink.TileCableLink;
import com.lothrazar.storagenetwork.block.inventory.ContainerNetworkInventory;
import com.lothrazar.storagenetwork.block.inventory.TileInventory;
import com.lothrazar.storagenetwork.block.main.BlockMain;
import com.lothrazar.storagenetwork.block.main.TileMain;
import com.lothrazar.storagenetwork.block.request.BlockRequest;
import com.lothrazar.storagenetwork.block.request.ContainerNetworkCraftingTable;
import com.lothrazar.storagenetwork.block.request.TileRequest;
import com.lothrazar.storagenetwork.item.ItemUpgrade;
import com.lothrazar.storagenetwork.item.remote.ContainerNetworkCraftingRemote;
import com.lothrazar.storagenetwork.item.remote.ContainerNetworkRemote;
import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

public class SsnRegistry {

  public static final int UPGRADE_COUNT = 4;
  public static ItemGroup itemGroup = new ItemGroup(StorageNetwork.MODID) {

    @Override
    public ItemStack createIcon() {
      return new ItemStack(SsnRegistry.request);
    }
  };
  @ObjectHolder(StorageNetwork.MODID + ":inventory_remote")
  public static Item inventory_remote;
  @ObjectHolder(StorageNetwork.MODID + ":crafting_remote")
  public static Item crafting_remote;
  //
  @ObjectHolder(StorageNetwork.MODID + ":speed_upgrade")
  public static ItemUpgrade speed_upgrade;
  @ObjectHolder(StorageNetwork.MODID + ":stack_upgrade")
  public static ItemUpgrade stack_upgrade;
  @ObjectHolder(StorageNetwork.MODID + ":master")
  public static TileEntityType<TileMain> mainTileentity;
  @ObjectHolder(StorageNetwork.MODID + ":master")
  public static BlockMain main;
  @ObjectHolder(StorageNetwork.MODID + ":inventory")
  public static Block inventory;
  @ObjectHolder(StorageNetwork.MODID + ":inventory")
  public static TileEntityType<TileInventory> inventorytile;
  @ObjectHolder(StorageNetwork.MODID + ":inventory")
  public static ContainerType<ContainerNetworkInventory> inventorycontainer;
  //request
  @ObjectHolder(StorageNetwork.MODID + ":request")
  public static BlockRequest request;
  @ObjectHolder(StorageNetwork.MODID + ":request")
  public static TileEntityType<TileRequest> requesttile;
  @ObjectHolder(StorageNetwork.MODID + ":request")
  public static ContainerType<ContainerNetworkCraftingTable> requestcontainer;
  @ObjectHolder(StorageNetwork.MODID + ":kabel")
  public static BlockCable kabel;
  @ObjectHolder(StorageNetwork.MODID + ":kabel")
  public static TileEntityType<TileCable> kabeltile;
  @ObjectHolder(StorageNetwork.MODID + ":storage_kabel")
  public static Block storagekabel;
  @ObjectHolder(StorageNetwork.MODID + ":storage_kabel")
  public static TileEntityType<TileCableLink> storagekabeltile;
  @ObjectHolder(StorageNetwork.MODID + ":import_kabel")
  public static Block importkabel;
  @ObjectHolder(StorageNetwork.MODID + ":import_kabel")
  public static TileEntityType<TileCableIO> importkabeltile;
  @ObjectHolder(StorageNetwork.MODID + ":filter_kabel")
  public static Block filterkabel;
  @ObjectHolder(StorageNetwork.MODID + ":filter_kabel")
  public static TileEntityType<TileCableFilter> filterkabeltile;
  @ObjectHolder(StorageNetwork.MODID + ":filter_kabel")
  public static ContainerType<ContainerCableFilter> filterContainer;
  @ObjectHolder(StorageNetwork.MODID + ":import_filter_kabel")
  public static Block importfilterkabel;
  @ObjectHolder(StorageNetwork.MODID + ":import_filter_kabel")
  public static TileEntityType<TileCableImportFilter> filterimportkabeltile;
  @ObjectHolder(StorageNetwork.MODID + ":import_filter_kabel")
  public static ContainerType<ContainerCableImportFilter> filterimportContainer;
  @ObjectHolder(StorageNetwork.MODID + ":export_kabel")
  public static Block exportkabel;
  @ObjectHolder(StorageNetwork.MODID + ":export_kabel")
  public static TileEntityType<TileCableImportFilter> exportkabeltile;
  @ObjectHolder(StorageNetwork.MODID + ":export_kabel")
  public static ContainerType<ContainerCableExportFilter> filterexportContainer;
  @ObjectHolder(StorageNetwork.MODID + ":inventory_remote")
  public static ContainerType<ContainerNetworkRemote> remote;
  @ObjectHolder(StorageNetwork.MODID + ":crafting_remote")
  public static ContainerType<ContainerNetworkCraftingRemote> craftingremote;
}
