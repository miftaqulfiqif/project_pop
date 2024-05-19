package com.cakraagro.cakraagroindonesia;

import android.graphics.Color;

import java.util.Random;

public class ColorUtil {

    private static final String[] COLORS = {
            "#F37C66","#F9CB8F","#18BB7C","#4ABCD7", "#B0B0B0", "#5F5F5F"
    };

    public static int getRandomColor() {
        Random random = new Random();
        int randomIndex = random.nextInt(COLORS.length);
        String randomColorString = COLORS[randomIndex];
        return Color.parseColor(randomColorString);
    }
}

