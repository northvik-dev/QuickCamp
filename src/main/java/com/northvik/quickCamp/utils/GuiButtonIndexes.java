package com.northvik.quickCamp.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GuiButtonIndexes {
    List<String> inventorySlots = new ArrayList<>(
            Arrays.asList("0,1,2,3,4,5,6,7,8,",
                    "9,10,11,12,13,14,15,16,17,",
                    "18,19,20,21,22,23,24,25,26,",
                    "27,28,29,30,31,32,33,34,35,",
                    "36,37,38,39,40,41,42,43,44,",
                    "45,46,47,48,49,50,51,52,53"
            ));


    int closeButton = 8;
    //MAIN MENU
    int createTemplate = 21;
    int loadTemplates = 23;

    ///TEMPLATE MENU
    int sizeButton = 44;
    int saveButton = 51;
    int clearButton = 52;
    int infoButton = 53;

    int itemLinkSlot = 25;

    int itemLinkButton = 26;
    int[] buttonsIndex = {8,44,51,52,53,25,26};

    public int[] buttonsIndexList(){
        return buttonsIndex;
    }
    public int getItemLinkSlot() {
        return itemLinkSlot;
    }

    public int getItemLinkButton() {
        return itemLinkButton;
    }
    public List<String> getInventorySlots() {
        return inventorySlots;
    }
    public int getSizeButton() {
        return sizeButton;
    }
    public int getCloseButton() {
        return closeButton;
    }

    public int getCreateTemplate() {
        return createTemplate;
    }

    public int getLoadTemplates() {
        return loadTemplates;
    }

    public int getSaveButton() {
        return saveButton;
    }

    public int getClearButton() {
        return clearButton;
    }

    public int getInfoButton() {
        return infoButton;
    }


}
