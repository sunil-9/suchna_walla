package com.example.suchnawalla.helper;

import android.text.InputType;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.suchnawalla.R;

public class PasswordHelper {
    public static boolean showPassword(EditText etPassword, ImageView ivEye, boolean isShowPassword){
        if(isShowPassword){
            isShowPassword=false;
            ivEye.setImageResource(R.drawable.ic_eye);
            etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);

        } else{
            isShowPassword=true;
            ivEye.setImageResource(R.drawable.ic_eye_close);
            etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
        return isShowPassword;
    }
}
