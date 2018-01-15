package com.example.Annot.AnnotUseage;

import com.example.Annot.AnnotModel.FruitColor;
import com.example.Annot.AnnotModel.FruitName;
import com.example.Annot.AnnotModel.FruitProvider;

/**
 * Created by fqzhang on 2017/8/19.
 */

public class Apple {
    @FruitName("Apple")
    private String appleName;
    @FruitColor(fruitColor = FruitColor.Color.GREEN)
    private String appleColor;
    @FruitProvider(id = 1,name = "�츻ʿ����",address = "���Ͽ���")
    private String appleProvider;
    public void setAppleColor(String appleColor) {
        this.appleColor = appleColor;
    }
    public String getAppleColor() {
        return appleColor;
    }

    public void setAppleProvider(String appleProvider) {
        this.appleProvider = appleProvider;
    }

    public String getAppleProvider() {
        return appleProvider;
    }

    public void setAppleName(String appleName) {
        this.appleName = appleName;
    }
    public String getAppleName() {
        return appleName;
    }

    public void displayName(){
        System.out.println("ˮ���������ǣ�ƻ��");
    }
}
