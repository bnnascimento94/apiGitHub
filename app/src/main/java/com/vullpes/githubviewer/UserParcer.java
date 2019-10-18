package com.vullpes.githubviewer;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class UserParcer {


    public static User parseDados(String content) {
        User user = new User();
        JSONObject jsonObject;

        try {
            JSONArray jsonArray = new JSONArray(content);


            List<HashMap<String,String>> listRepositories = new ArrayList<>();
            jsonObject = jsonArray.getJSONObject(0).getJSONObject("owner");
            user.setNome(jsonObject.getString("login"));
            byte[] bytes = Utils.byteArray(jsonObject.getString("avatar_url"));
            user.setPicByte(bytes);

            HashMap<String, String> mapRepository = new HashMap<>();
            for (int i = 0; i< jsonArray.length(); i++){
                jsonObject = jsonArray.getJSONObject(i);
                mapRepository.put(jsonObject.getString("name"), jsonObject.getString("language"));
            }

            Iterator it = mapRepository.entrySet().iterator();
            while(it.hasNext()){
                HashMap<String,String> resultMap = new HashMap<>();
                Map.Entry pair = (Map.Entry) it.next();
                resultMap.put("First Line",pair.getKey().toString());
                resultMap.put("Second Line",pair.getValue().toString());
                listRepositories.add(resultMap);
            }

            user.setRepositorios(listRepositories);

            return user;
        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }




}
