package com.northvik.quickCamp.managers;

import com.northvik.quickCamp.utils.GuiButtonIndexes;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GuiCustomSize {
    List<Integer> inputSlotsIndexes = new ArrayList<>();
    List<Integer> nonUsableSlotIndex = new ArrayList<>();

    GuiButtonIndexes gbi = new GuiButtonIndexes();
    List<String> allSlots = gbi.getInventorySlots();


    public void convertSlotsToInt (int choice){

        inputSlotsIndexes.clear();
        nonUsableSlotIndex.clear();

        for (int i = 0; i < choice + 2; i++) {
            String[] line = allSlots.get(i).split(",");
            for (int j = 0; j < choice + 2; j++) {
                int sizedSlots = Integer.parseInt(line[j]);
                inputSlotsIndexes.add(sizedSlots);

            }
        }

        List<Integer> allParsedIntSlots = new ArrayList<>();
        for (String row : allSlots){
            for(String slot : row.split(",")){
                allParsedIntSlots.add(Integer.parseInt(slot.trim()));
            }
        }
        //CREATE NON_USABLE SLOTS
        Iterator<Integer> iterator = allParsedIntSlots.iterator();
        int [] buttonsIndex = gbi.buttonsIndexList();
        while (iterator.hasNext()) {
            Integer slot = iterator.next();
            boolean isNonUsable = false;

            for (Integer x : inputSlotsIndexes) {
                for (int i : buttonsIndex ) {
                    if (slot.equals(x) || slot.equals(i) ) {
                        isNonUsable = true;
                        break;
                    }
                }
            }if (isNonUsable) {
                iterator.remove();
            } else {
                nonUsableSlotIndex.add(slot);
            }
        }
    }

    //SLOTS
    public List<Integer> getInputSlotsIndexes(){
        return inputSlotsIndexes;
    }
    public List<Integer> getNonUsableSlotsIndexes(){
        return nonUsableSlotIndex;
    }

}
