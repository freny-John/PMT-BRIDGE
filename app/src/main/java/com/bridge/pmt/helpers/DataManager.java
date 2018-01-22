package com.bridge.pmt.helpers;

import com.bridge.pmt.R;
import com.bridge.pmt.models.HoursModel;

import java.util.ArrayList;
import java.util.List;



public class DataManager {

    private static List<HoursModel> sChatModels = new ArrayList<>();

    static {
        sChatModels.add(new HoursModel("Ann Drewer", "Hey, why didn't you call me?", R.drawable.ic_img_chat_one, "5 min ago"));
        sChatModels.add(new HoursModel("Ann Drewer", "Hey, why didn't you call me?", R.drawable.ic_img_chat_one, "5 min ago"));
        sChatModels.add(new HoursModel("Ann Drewer", "Hey, why didn't you call me?", R.drawable.ic_img_chat_one, "5 min ago"));
    }

    public static List<HoursModel> getChatModels() {
        return sChatModels;
    }
}
