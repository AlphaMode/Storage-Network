package mrriegel.storagenetwork.gui;
import java.util.List;
import com.google.common.collect.Lists;
import mrriegel.storagenetwork.StorageNetwork;
import mrriegel.storagenetwork.data.FilterItem;
import mrriegel.storagenetwork.data.StackWrapper;
import mrriegel.storagenetwork.master.TileMaster;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.PlayerMainInvWrapper;

public abstract class ContainerNetworkBase extends Container {
  public InventoryPlayer playerInv;
  public InventoryCraftResult result;
  public InventoryCrafting matrix;
  public boolean recipeLocked = false;
  public abstract InventoryCrafting getCraftMatrix();
  public abstract TileMaster getTileMaster();
  public abstract void slotChanged();
  boolean test = false;
  @Override
  public void detectAndSendChanges() {
    //   StorageNetwork.log("detectAndSendChanges  ");
    super.detectAndSendChanges();
  }
  @Override
  public void onCraftMatrixChanged(IInventory inventoryIn) {
    StorageNetwork.log("onCraftMatrixChanged  ");
    super.onCraftMatrixChanged(inventoryIn);
  }
  public void craftShift(EntityPlayer player, TileMaster tile) {
    this.recipeLocked = true;
    //  MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
    //    CraftingManager.getRemainingItems(p_180303_0_, craftMatrix)
    //    craftMatrix.getSizeInventory()
    // always false, so always on server
    StorageNetwork.log("Container.craftShift: algo start; CLIENT = " + player.world.isRemote);
    SlotCrafting sl = new SlotCrafting(player, matrix, result, 0, 0, 0);
    int crafted = 0;
    List<ItemStack> recipeCopy = Lists.newArrayList();
    for (int i = 0; i < matrix.getSizeInventory(); i++) {
      recipeCopy.add(matrix.getStackInSlot(i).copy());
    }
    ItemStack res = result.getStackInSlot(0);
    int sizePerCraft = res.getCount();
    int sizeFull = res.getMaxStackSize();
    int numberToCraft = sizeFull / sizePerCraft;
    StorageNetwork.log("numberToCraft = " + numberToCraft);
    // TODO: ?!? a strategy
    // use tile.request(..,..,true) to simulate requests first, and also with 64
    //now what SHOULD happen, is if we request 64 but have 50 is that we get all 50.
    //then check over all ingredients for max # amount, and do single requests for that much every time
    //ALTERNATE: possibly easiers?
    //spin offf thread runners
    while (crafted + res.getCount() <= res.getMaxStackSize()) {
      StorageNetwork.benchmark("while loop top");
      if (!ItemHandlerHelper.insertItemStacked(new PlayerMainInvWrapper(playerInv), res.copy(), true).isEmpty()) {
        break;
      }
      StorageNetwork.benchmark("before insertItemStacked");
      ItemHandlerHelper.insertItemStacked(new PlayerMainInvWrapper(playerInv), res.copy(), false);
      //      server.addScheduledTask(new Runnable() {
      //        public void run() {
      //          StorageNetwork.log("TEST THREAD ");
      //        }
      //      });
      StorageNetwork.benchmark("before onTake REFACTORED");
      //      sl.onTake(player, res);// ontake this does the actaul craft see ContainerRequest
      //START RSF
      //      sl.onCrafting(res);
      NonNullList<ItemStack> remainder = CraftingManager.getRemainingItems(matrix, player.world);
      StorageNetwork.benchmark("after getRemainingItems");
      for (int i = 0; i < remainder.size(); ++i) {
        StorageNetwork.benchmark("before getstackinslot");
        ItemStack slot = this.matrix.getStackInSlot(i);
        StorageNetwork.benchmark("after getstackinslot");
        ItemStack remainderCurrent = remainder.get(i);
        StorageNetwork.benchmark("A");
        if (!remainderCurrent.isEmpty()) {
          if (slot.isEmpty()) {
            StorageNetwork.benchmark("B");
            this.matrix.setInventorySlotContents(i, remainderCurrent);
            StorageNetwork.benchmark("C");
          }
          else if (ItemStack.areItemsEqual(slot, remainderCurrent) && ItemStack.areItemStackTagsEqual(slot, remainderCurrent)) {
            StorageNetwork.benchmark("D");
            remainderCurrent.grow(slot.getCount());
            this.matrix.setInventorySlotContents(i, remainderCurrent);
            StorageNetwork.benchmark("E");
          }
          else if (!player.inventory.addItemStackToInventory(remainderCurrent)) {
            player.dropItem(remainderCurrent, false);
          }
          StorageNetwork.benchmark("F");
        }
        else if (!slot.isEmpty()) {
          StorageNetwork.benchmark("start isempty section");
          this.matrix.decrStackSize(i, 1);
          slot = this.matrix.getStackInSlot(i);
          StorageNetwork.benchmark("after isempty section");
        }
      }
      //END RSF
      StorageNetwork.benchmark("after onTake REFACTORED");
      crafted += res.getCount();
      ItemStack stackInSlot;
      ItemStack recipeStack;
      FilterItem filterItemCurrent;
      // StorageNetwork.benchmark( "before FOR loop");
      for (int i = 0; i < matrix.getSizeInventory(); i++) {
        // StorageNetwork.benchmark( "start of FOR loop");
        stackInSlot = matrix.getStackInSlot(i);
        if (stackInSlot.isEmpty()) {
          recipeStack = recipeCopy.get(i);
          // StorageNetwork.benchmark( "Container.craftShift loop " + crafted + " slotIndex " + i);
          //////////////// booleans are meta, ore(?ignored?), nbt
          filterItemCurrent = !recipeStack.isEmpty() ? new FilterItem(recipeStack, true, false, false) : null;
          //false here means dont simulate
          StorageNetwork.benchmark("before request");
          ItemStack req = tile.request(filterItemCurrent, 1, false);
          StorageNetwork.benchmark("after request & before setInventorySlotContents");
          // AHA! THIS IS THE BIGGEST TIMEKILLER
          // goes from ... 60 to ...76 MS (or 50-59, etc) . not much but adds up in the 9x64 operations happening here
          matrix.setInventorySlotContents(i, req);
          StorageNetwork.benchmark("after setInventorySlotContents");
        }
      }
      StorageNetwork.benchmark("before onCraftMatrixChanged");
      onCraftMatrixChanged(matrix);
      StorageNetwork.benchmark("after onCraftMatrixChanged; before ifElse");
      if (!ItemHandlerHelper.canItemStacksStack(res, result.getStackInSlot(0))) {
        break;
      }
      else {
        res = result.getStackInSlot(0);
      }
      StorageNetwork.benchmark("after ifElse & end of while loop");
    }
    List<StackWrapper> list = tile.getStacks();
    StorageNetwork.log("Container.craftShift: SEND new StacksMessage UNDO what does this change");
    //    PacketRegistry.INSTANCE.sendTo(new StacksMessage(list, tile.getCraftableStacks(list)), (EntityPlayerMP) player);
    detectAndSendChanges();
    StorageNetwork.benchmark("end");
    this.recipeLocked = false;
  }
}
