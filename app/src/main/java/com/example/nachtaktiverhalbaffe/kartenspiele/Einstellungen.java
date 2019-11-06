package com.example.nachtaktiverhalbaffe.kartenspiele;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import utils.Einstellungen_Fragment;

public class Einstellungen extends Fragment {
    private FragmentActivity myContext;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.activity_einstellungen, container, false);
        myContext.getFragmentManager().beginTransaction().add(R.id.settings, new Einstellungen_Fragment()).commit();

        //Lese gespeicherte Einsellungen
        /*PreferenceManager.setDefaultValues(getActivity(), R.xml.einstellungen, false);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());*/
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }
}

