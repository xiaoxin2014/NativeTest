package com.example.nativetest;

import com.alibaba.fastjson.JSON;
import com.example.nativetest.db.model.ProfileInfo;

import java.util.HashMap;
import java.util.Map;

public class ProfileUtils {
    public static ProfileInfo sProfileInfo = new ProfileInfo();

    public static HashMap<String,Object> getUpdateInfo(int type,String key,String value){
//        {
//            "Skip": 0,
//                "Take": 0,
//                "Data": {
//            "Head": {
//                "UID": 0,
//                        "Name": "string",
//                        "NameColor": "string",
//                        "UserIcon": "string",
//                        "Gender": true
//            },
//            "Bio": "string",
//                    "Location": "string",
//                    "School": "string",
//                    "DOB": "2020-07-15T01:47:10.243Z"
//        }
//        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("Skip",0);
        map.put("Take",0);
        if(type==1) {
            map.put(key,value);
        }

        HashMap<String, Object> dataMap = new HashMap<>();

        dataMap.put("Bio","");
        dataMap.put("Location","");
        dataMap.put("School","");
        dataMap.put("DOB","1990-07-15T01:47:10.243Z");

        if(type==2) {
            dataMap.put(key,value);
        }

        HashMap<String, Object> headMap = new HashMap<>();
        headMap.put("UID",sProfileInfo.getHead().getUID());
        headMap.put("Name","Niko");
        headMap.put("NameColor",sProfileInfo.getHead().getNameColor());
        headMap.put("UserIcon",sProfileInfo.getHead().getUserIcon());
        headMap.put("Gender",sProfileInfo.getHead().isGender());

        if(type==3) {
            headMap.put(key,value);
        }

        dataMap.put("Head", JSON.toJSON(headMap));

        map.put("Data",JSON.toJSON(dataMap));
        return map;
    }
}
