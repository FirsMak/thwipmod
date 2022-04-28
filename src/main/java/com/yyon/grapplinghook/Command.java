package com.yyon.grapplinghook;

import com.yyon.grapplinghook.items.ClassicWebShooter;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

public class Command extends CommandBase {

    public static final String
            NAME = "endlessweb",//Имя команды, используется при вызове.
            USAGE = "/endlessweb";//Шаблон вызова, выводится при выбрасывании WrongUsageException.
    @Override
    public String getName() {
        return this.NAME;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return this.USAGE;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (sender instanceof EntityPlayer) {
            boolean isHas = false;
            for (String in: args){
                if (in.equals("true")){
                    isHas = true;
                }
            }
            if (isHas){
                EntityPlayer player = this.getCommandSenderAsPlayer(sender);//Получение экземрляра игрока, вызвавшего команду.
                for (ItemStack itemStack : player.inventory.mainInventory){
                    if (itemStack.getItem() instanceof ClassicWebShooter){
                        itemStack.setItemDamage(0);
                        ClassicWebShooter.isEndlessWeb = true;
                    }
                }
            }
            else {
                ClassicWebShooter.isEndlessWeb = false;
            }
        }
    }
}
