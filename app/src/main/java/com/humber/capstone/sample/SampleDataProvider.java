package com.humber.capstone.sample;

import com.humber.capstone.model.EmojiItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SampleDataProvider {
    public static List<EmojiItem> emojieItemList;
    public static Map<String, EmojiItem> dataItemMap;

    static {
        emojieItemList = new ArrayList<>();
        dataItemMap = new HashMap<>();

    }
        private static void addItem(EmojiItem item){
            emojieItemList.add(item);
            dataItemMap.put(item.getItemId(), item);
        }

}
