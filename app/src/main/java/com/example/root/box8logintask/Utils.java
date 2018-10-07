package com.example.root.box8logintask;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.example.root.box8logintask.model.LoginUserModel;
import com.example.root.box8logintask.view.MyToast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static Boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Boolean result = email.matches(emailPattern) ? true : false;
        String emailPattern1 = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+\\.+[a-z]+";
        Boolean result1 = email.matches(emailPattern1) ? true : false;
        return (result || result1);
    }

    public static Boolean isValidString(String string) {
        if(string == null || string.trim().equals("")) {
            return false;
        } else {
            return true;
        }
    }

    public static ArrayList<LoginUserModel> getSavedLoginUserLists(Context context) {
        ObjectInputStream input = null;
        ArrayList<LoginUserModel> savedUserList = null;
        File f = new File(context.getFilesDir(),"LoginUserLists.srl");
        try {
            input = new ObjectInputStream(new FileInputStream(f));
            savedUserList = (ArrayList<LoginUserModel>) input.readObject();
            input.close();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return savedUserList;
    }


    public static void saveDataIntoFile(Context context, List<LoginUserModel> loginUserLists) {
        File f = new File(context.getFilesDir(),"LoginUserLists.srl");
        try {
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream objectwrite = new ObjectOutputStream(fos);
            objectwrite.writeObject(loginUserLists);
            fos.close();
            MyToast.makeText(context, "Saved the details", Toast.LENGTH_SHORT);
            if (!f.exists()) {
                f.mkdirs();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void nextFragmentWithLeftRightAnim(FragmentManager fragmentManager, Fragment fragment, int fragContainerResID) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations( R.anim.slide_in_from_right, R.anim.slide_out_to_left, R.anim.slide_in_from_left, R.anim.slide_out_to_right);
        fragmentTransaction.replace(fragContainerResID, fragment, fragment.toString());
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.commit();
    }
}
