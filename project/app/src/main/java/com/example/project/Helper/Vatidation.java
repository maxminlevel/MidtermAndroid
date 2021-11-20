package com.example.project.Helper;

import android.util.Patterns;
import android.widget.EditText;

import java.util.regex.Pattern;

public class Vatidation {
    public static boolean isEmpty(EditText editText) {
        if (editText.getText().toString().isEmpty()) {
            editText.setError("Không được để trống");
            editText.requestFocus();
            return false;
        }
        return true;
    }

    public static boolean haveSpace(EditText editText) {
        String text = editText.getText().toString();
        if (text.startsWith(" ") || text.endsWith(" ")){
            editText.setError("Không được chưa ký tự trống ở đầu và cuối");
            return false;
        }
        return true;
    }

    public static boolean isPattern(EditText editText, Pattern pattern) {
        if (!pattern.matcher(editText.getText().toString()).matches()) {
            editText.setError("Email không hợp lệ. Vui lòng nhập lại");
            editText.requestFocus();
            return false;
        }
        return true;
    }

    public static boolean longer(EditText editText, int lenght) {
        if(editText.getText().toString().length() < lenght){
            editText.setError("Phải chứa ít nhất "+ lenght +" ký tự");
            editText.requestFocus();
            return false;
        }
        return true;
    }

    public static boolean checkFormRegister(EditText name, EditText email, EditText tel, EditText pass){
        return isEmpty(name) && isEmpty(email) && isEmpty(pass) && isPattern(email, Patterns.EMAIL_ADDRESS) && longer(pass, 6);
    }
}
