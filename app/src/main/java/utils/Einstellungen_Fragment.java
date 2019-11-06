package utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.nachtaktiverhalbaffe.kartenspiele.R;

public class Einstellungen_Fragment extends PreferenceFragment {
    public static final String NIGHT_MODE = "NIGHT_MODE";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.einstellungen);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //Binde CheckboxPrefrences an XML-Layout
        CheckBoxPreference erscheinungsbild_auto_checkbox = (CheckBoxPreference) getPreferenceScreen().findPreference(getString(R.string.key_erscheinungsbild_auto));
        CheckBoxPreference erscheinungsbild_hell_checkbox = (CheckBoxPreference) getPreferenceScreen().findPreference(getString(R.string.key_erscheinungsbild_hell));
        CheckBoxPreference erscheinungsbild_dunkel_checkbox = (CheckBoxPreference) getPreferenceScreen().findPreference(getString(R.string.key_erscheinungsbild_dunkel));
        CheckBoxPreference delete = (CheckBoxPreference) getPreferenceScreen().findPreference(getString(R.string.key_delete));
        if (erscheinungsbild_auto_checkbox.isChecked()) {
            erscheinungsbild_dunkel_checkbox.setEnabled(false);
            erscheinungsbild_hell_checkbox.setEnabled(false);
        }
        //Binde OnPrefrenceChangeListener an Prefrences
        erscheinungsbild_hell_checkbox.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference preference) {
                if (erscheinungsbild_hell_checkbox.isChecked()) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    mPrefs.edit().putString(NIGHT_MODE, "DAY").apply();
                    erscheinungsbild_dunkel_checkbox.setChecked(false);
                    erscheinungsbild_auto_checkbox.setChecked(false);
                    Intent intent = getActivity().getIntent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    getActivity().finish();
                    startActivity(intent);
                    return true;
                }

                return false;
            }
        });

        erscheinungsbild_dunkel_checkbox.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if (erscheinungsbild_dunkel_checkbox.isChecked()) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    mPrefs.edit().putString(NIGHT_MODE, "NIGHT").apply();
                    erscheinungsbild_hell_checkbox.setChecked(false);
                    erscheinungsbild_auto_checkbox.setChecked(false);
                    Intent intent = getActivity().getIntent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    getActivity().finish();
                    startActivity(intent);
                    return true;

                }
                return false;
            }
        });

        erscheinungsbild_auto_checkbox.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if (erscheinungsbild_auto_checkbox.isChecked()) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                    mPrefs.edit().putString(NIGHT_MODE, "AUTO").apply();
                    erscheinungsbild_dunkel_checkbox.setChecked(false);
                    erscheinungsbild_hell_checkbox.setChecked(false);
                    erscheinungsbild_dunkel_checkbox.setEnabled(false);
                    erscheinungsbild_hell_checkbox.setEnabled(false);
                    Intent intent = getActivity().getIntent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    getActivity().finish();
                    startActivity(intent);
                } else if (!erscheinungsbild_auto_checkbox.isChecked()) {
                    erscheinungsbild_dunkel_checkbox.setEnabled(true);
                    erscheinungsbild_hell_checkbox.setEnabled(true);
                }
                return false;
            }
        });

        delete.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if(delete.isChecked()){
                mPrefs.edit().putBoolean("ASK_DELETE", true).apply();
                delete.setChecked(true);
                return true;
                }else if(!delete.isChecked()){
                    mPrefs.edit().putBoolean("ASK_DELETE", false).apply();
                    delete.setChecked(false);
                    return true;
                }else return false;
            }
        });


    }
}
