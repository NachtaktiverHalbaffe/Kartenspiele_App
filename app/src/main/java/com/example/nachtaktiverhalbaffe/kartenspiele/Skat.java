package com.example.nachtaktiverhalbaffe.kartenspiele;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.room.Room;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.google.android.material.chip.Chip;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Database.Golfen.Golfen_Partie;
import Database.Skat.SkatViewModel;
import Database.Skat.Skat_Partie;
import Database.Skat.Skat_Spieler;
import Database.Skat.Skat_database;
import recyclerview.skat_adapter;

import static java.lang.Integer.valueOf;

/**
 * Created by NachtaktiverHalbaffe on 29.11.2017.
 */

public class Skat extends Fragment {

    private SkatViewModel viewModel;
    public static skat_adapter adapter;
    List<Skat_Spieler> skat_spieler;
    public Golfen_Partie golfen_partien = null;
    static RecyclerView mRecyclerview;
    public static boolean alive=false;
    public static List<Integer> size= new ArrayList<>();
    public static List<Integer> position= new ArrayList<>();
    int j=0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {   //Fragment Preambel
        View rootView = inflater.inflate(R.layout.skat, container, false);
        alive=false;
        getActivity().setTitle("Skat");
        FloatingActionButton anl = rootView.findViewById(R.id.skat_anleitung);
        anl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String anleitung = "Ablauf:\n" + getString(R.string.Skat_Anleitung) + "\n\nRegeln:\n" + getString(R.string.Skat_Regeln)+"\n\nWertung:\n" + getString(R.string.Skat_Wertung);
                AlertDialog.Builder builder_kon = new AlertDialog.Builder(getActivity());
                builder_kon.setTitle("Skat");
                builder_kon.setMessage(anleitung);
                AlertDialog dialog = builder_kon.create();
                dialog.show();
            }
        });
        FloatingActionButton add = rootView.findViewById(R.id.skat_add);
        mRecyclerview = rootView.findViewById(R.id.skat_card);
        mRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if (dy > 0){
                    anl.hide();
                    add.hide();}
                else if (dy < 0){
                    anl.show();
                    add.show();}
            }
        });
       final LinearLayoutManager team_llm = new LinearLayoutManager(getActivity());
        mRecyclerview.setLayoutManager(team_llm);
        adapter = new skat_adapter(getContext(), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int k = 0; k < viewModel.getAllSkat_Partien().getValue().size(); k++) {
                    if (skat_adapter.id.equals(viewModel.getAllSkat_Partien().getValue().get(k).getPartiebezeichnung())) {
                        for (int l = 0; l < viewModel.getAllSkat_Partien().getValue().size(); l++) {
                            if (viewModel.getAllSkat_Partien().getValue().get(l).getPartiebezeichnung().equals(skat_adapter.id)) {
                                if (viewModel.getAllSkat_Partien().getValue().get(l).getSkat_spieler().get(0).getPunkte() != null) {
                                    size.add(viewModel.getAllSkat_Partien().getValue().get(l).getSkat_spieler().get(0).getPunkte().size());
                                }
                                position.add(l);
                                input_punkte(viewModel.getAllSkat_Partien().getValue().get(l));
                            }
                        }

                    }
                }
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int k = 0; k < viewModel.getAllSkat_Partien().getValue().size(); k++) {
                    if (skat_adapter.id.equals(viewModel.getAllSkat_Partien().getValue().get(k).getPartiebezeichnung())) {
                        for (int l = 0; l < viewModel.getAllSkat_Partien().getValue().size(); l++) {
                            if (viewModel.getAllSkat_Partien().getValue().get(l).getPartiebezeichnung().equals(skat_adapter.id)) {
                                if (viewModel.getAllSkat_Partien().getValue().get(l).getSkat_spieler().get(0).getPunkte() != null) {
                                    size.add(viewModel.getAllSkat_Partien().getValue().get(l).getSkat_spieler().get(0).getPunkte().size());
                                }
                                position.add(l);
                                update_partie(viewModel.getAllSkat_Partien().getValue().get(l));

                            }
                        }

                    }
                }
            }
        });
        mRecyclerview.setAdapter(adapter);
        //ViewModel implementieren und mit Datenbank verlinken
        viewModel = ViewModelProviders.of(getActivity()).get(SkatViewModel.class);
        viewModel.getAllSkat_Partien().observe(getActivity(), new Observer<List<Skat_Partie>>() {
            @Override
            public void onChanged(@Nullable List<Skat_Partie> skat_parties) {
                    adapter.submitList(skat_parties);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_players(0, new ArrayList<>());
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                alive=true;
                position.add(viewHolder.getLayoutPosition());
                if (mPrefs.getBoolean("ASK_DELETE",true)){
                    delete_alert(getActivity(), viewHolder.getAdapterPosition());
                }else{
                    viewModel.delete(viewModel.getAllSkat_Partien().getValue().get(viewHolder.getAdapterPosition()));
                }
            }
        }).attachToRecyclerView(mRecyclerview);
        return rootView;
    }


    void new_players(int nummer, List<Skat_Spieler> skat_spieler) {
        if (nummer < 4) {

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
            final EditText input = new EditText(getActivity());
            input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
            dialogBuilder.setView(input);
            dialogBuilder.setTitle("Spieler " + (nummer + 1));
            dialogBuilder.setMessage("Name des " + (nummer + 1) + ". Spielers eingeben oder frei lassen");
            dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    skat_spieler.add(new Skat_Spieler(input.getText().toString(), null, 0));
                    j++;
                    new_players(j, skat_spieler);
                }
            });
            dialogBuilder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    skat_spieler.add(new Skat_Spieler("", null, 0));
                    j++;
                    new_players(j, skat_spieler);
                }
            });
            AlertDialog alert = dialogBuilder.create();
            alert.setCancelable(false);
            alert.show();
            TextView pos = alert.getButton(DialogInterface.BUTTON_POSITIVE);
            if(pos!=null)pos.setTextColor(getResources().getColor(R.color.textcolorDark));
            TextView neg =alert.getButton(DialogInterface.BUTTON_NEGATIVE);
            if(neg!=null) neg.setTextColor(getResources().getColor(R.color.textcolorDark));
        } else new_List(skat_spieler);
    }

    void new_List(final List<Skat_Spieler> skat_spieler) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        dialogBuilder.setView(input);
        dialogBuilder.setTitle("Titel");
        dialogBuilder.setMessage("Name der Partie eingeben");
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (!input.getText().toString().equals("")) {
                    alive=true;
                    viewModel.add(new Skat_Partie(input.getText().toString(), skat_spieler,new ArrayList<>(),new ArrayList<>()));
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.Fragment,new Skat()).commit();
                    j = 0;
                } else {
                    Toast toast = Toast.makeText(getActivity(), "Bitte einen gültigen Partienamen eingeben", Toast.LENGTH_LONG);
                    toast.show();
                    new_List(skat_spieler);
                }
            }
        });
        dialogBuilder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            j=0;
            }
        });
        AlertDialog alert = dialogBuilder.create();
        alert.setCancelable(false);
        alert.show();
        TextView pos = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        if(pos!=null)pos.setTextColor(getResources().getColor(R.color.textcolorDark));
        TextView neg =alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        if(neg!=null) neg.setTextColor(getResources().getColor(R.color.textcolorDark));
    }

    public void input_punkte(Skat_Partie skat_partie) {
        //Variabeln initialisieren
        final String[] gespielt = {""};
        List<Skat_Spieler> updated_skat_spieler = new ArrayList<>();

        //Auswählen, wer die Runde Alleinspieler ist
        MaterialAlertDialogBuilder alert = new MaterialAlertDialogBuilder(getActivity());
        alert.setTitle("Alleinspieler auswählen");
        int h=0;
        for (int k = 0; k <skat_partie.getSkat_spieler().size() ; k++) {
            if(!skat_partie.getSkat_spieler().get(k).getName().equals("")) h++;
        }
        switch(h) {
            case 1: {
                CharSequence[] namen = {skat_partie.getSkat_spieler().get(0).getName()};
                alert.setSingleChoiceItems(namen, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    gespielt[0] = skat_partie.getSkat_spieler().get(i).getName();
                    }
                });
                break;
            }
            case 2: {
                CharSequence[] namen = {skat_partie.getSkat_spieler().get(0).getName(), skat_partie.getSkat_spieler().get(1).getName()};
                alert.setSingleChoiceItems(namen, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        gespielt[0] = skat_partie.getSkat_spieler().get(i).getName();
                    }
                });

                break;
            }
            case 3: {
                CharSequence[] namen = {skat_partie.getSkat_spieler().get(0).getName(), skat_partie.getSkat_spieler().get(1).getName(), skat_partie.getSkat_spieler().get(2).getName()};
                alert.setSingleChoiceItems(namen, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        gespielt[0] = skat_partie.getSkat_spieler().get(i).getName();
                    }
                });
                break;
            }
            case 4: {
                CharSequence[] namen = {skat_partie.getSkat_spieler().get(0).getName(), skat_partie.getSkat_spieler().get(1).getName(), skat_partie.getSkat_spieler().get(2).getName(),skat_partie.getSkat_spieler().get(3).getName()};
                alert.setSingleChoiceItems(namen, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        gespielt[0] = skat_partie.getSkat_spieler().get(i).getName();
                    }
                });
            }
        }
        alert.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(!gespielt[0].equals("")){
                //Spielwert eingeben
                //Layout inflaten
                boolean mode;
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                View eingabe = inflater.inflate(R.layout.input_skat, null);
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                Chip re = eingabe.findViewById(R.id.skat_re);
                Chip kontra = eingabe.findViewById(R.id.skat_kontra);
                Chip bock = eingabe.findViewById(R.id.skat_bock);
                final NumberPicker gewinnstufe = eingabe.findViewById(R.id.skat_gewinnstufe);
                final NumberPicker grundwert =eingabe.findViewById(R.id.skat_grundwert);
                final NumberPicker bonus_np = eingabe.findViewById(R.id.skat_bonus);
                final CheckBox gewonnen = eingabe.findViewById(R.id.skat_gewonnen);
                final CheckBox skat= eingabe.findViewById(R.id.skat_skat);
                bonus_np.setMinValue(1);
                bonus_np.setMaxValue(6);
                bonus_np.setDisplayedValues(new String[]{"Einfach","Schneider","Schneider angesagt","Schwarz","Schwarz angesagt","Offen"});
                //Gewinnstufen anpassen, wenn Skat genommen oder liegen gelassen wurde
                skat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(b){
                            bonus_np.setMinValue(0);
                            bonus_np.setMaxValue(2);
                            bonus_np.setValue(0);
                            bonus_np.setDisplayedValues(new String[]{"Einfach","Schneider","Schwarz"});}
                    else{
                            bonus_np.setMinValue(1);
                            bonus_np.setMaxValue(6);
                            bonus_np.setDisplayedValues(new String[]{"Einfach","Schneider","Schneider angesagt","Schwarz","Schwarz angesagt","Offen"});}

                    skat.setClickable(false);
                    }
                });
                grundwert.setMinValue(9);
                grundwert.setMaxValue(15);
                grundwert.setDisplayedValues(new String[]{"♦","♥","♠","♣","Grand","Null","Null Ouvert"});
                gewinnstufe.setMinValue(2);
                gewinnstufe.setMaxValue(5);
                gewinnstufe.setDisplayedValues(new String[]{"mit/ohne 1","mit/ohne 2","mit/ohne 3","mit/ohne 4"});
                dialogBuilder.setView(eingabe);
                dialogBuilder.setTitle("Spielwert");
                dialogBuilder.setMessage("Spielwert der Runde eingeben und ob der Alleinspieler gewonnen oder verloren hat");
                dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                int u = 0;
                                int bonus = 1;
                                //Punkteberechnung
                                if (re.isChecked()) {
                                    bonus = bonus * 2;
                                }
                                if (kontra.isChecked()) {
                                    bonus = bonus * 2;
                                }
                                if (bock.isChecked()) {
                                    bonus = bonus * 2;
                                }
                                if (!gewonnen.isChecked()) {
                                    if(grundwert.getValue()==13){
                                        u = 24* (-2) * (gewinnstufe.getValue() + bonus_np.getValue()) * bonus;
                                    }
                                    else if (grundwert.getValue()==14) {
                                     u=-46;
                                    }

                                    else if(grundwert.getValue()==15){
                                        u=-92;
                                    }
                                     else   u = grundwert.getValue() * (-2) * (gewinnstufe.getValue() + bonus_np.getValue()) * bonus;
                                } else{
                                if(grundwert.getValue()==13){
                                    u = 24 * (gewinnstufe.getValue() + bonus_np.getValue()) * bonus;
                                }
                                else if (grundwert.getValue()==14) {
                                    u=23;
                                }
                                else if(grundwert.getValue()==15){
                                    u=46;
                                }
                                else u = grundwert.getValue() * (gewinnstufe.getValue() + bonus_np.getValue()) * bonus;}
                                //Punkte der Runde in Variaeln zwischenspeichern und diese in Datenbank schreiben
                                for (int k = 0; k < skat_partie.getSkat_spieler().size(); k++) {
                                    if (skat_partie.getSkat_spieler().get(k).getName().equals(gespielt[0])) {
                                        List<Integer> punkte = new ArrayList<>();
                                        if (skat_partie.getSkat_spieler().get(k).getPunkte() != null) {
                                            punkte = skat_partie.getSkat_spieler().get(k).getPunkte();
                                        }
                                        punkte.add(u);
                                        int sum = 0;
                                        for (int l = 0; l < punkte.size(); l++) {
                                            sum = sum + punkte.get(l);
                                        }
                                        updated_skat_spieler.add(new Skat_Spieler(skat_partie.getSkat_spieler().get(k).getName(), punkte, sum));
                                    } else {
                                        List<Integer> punkte = new ArrayList<>();
                                        if (skat_partie.getSkat_spieler().get(k).getPunkte() != null) {
                                            punkte = skat_partie.getSkat_spieler().get(k).getPunkte();
                                        }
                                        punkte.add(0);
                                        int sum = 0;
                                        for (int l = 0; l < punkte.size(); l++) {
                                            sum = sum + punkte.get(l);
                                        }
                                        updated_skat_spieler.add(new Skat_Spieler(skat_partie.getSkat_spieler().get(k).getName(), punkte, sum));
                                    }
                                }
                                List<String> update_trumpf = new ArrayList<>();
                                if (skat_partie.getTrumpf() != null) {
                                    update_trumpf = skat_partie.getTrumpf();
                                }
                                update_trumpf.add(String.valueOf(grundwert.getValue()));
                                List<String> update_gewinnstufe = new ArrayList<>();
                                if (skat_partie.getGewinnstufe() != null) {
                                    update_gewinnstufe = skat_partie.getGewinnstufe();
                                }
                                if (skat.isChecked()) {
                                    switch (bonus_np.getValue()) {
                                        case 0: {
                                            update_gewinnstufe.add("Einfach");
                                            break;
                                        }
                                        case 1: {
                                            update_gewinnstufe.add("Schneider");
                                            break;
                                        }
                                        case 2: {
                                            update_gewinnstufe.add("Schneider angesagt");
                                            break;
                                        }
                                    }
                                } else if (!skat.isChecked()) {
                                    switch (bonus_np.getValue()) {
                                        case 1: {
                                            update_gewinnstufe.add("Einfach");
                                            break;
                                        }
                                        case 2: {
                                            update_gewinnstufe.add("Schneider");
                                            break;
                                        }
                                        case 3: {
                                            update_gewinnstufe.add("Schneider angesagt");
                                            break;
                                        }
                                        case 4: {
                                            update_gewinnstufe.add("Schwarz");
                                            break;
                                        }
                                        case 5: {
                                            update_gewinnstufe.add("Schwarz angesagt");
                                            break;
                                        }
                                        case 6: {
                                            update_gewinnstufe.add("Offene Hand");
                                            break;
                                        }
                                    }
                                }
                                viewModel.update(new Skat_Partie(skat_partie.getPartiebezeichnung(),updated_skat_spieler,update_trumpf, update_gewinnstufe));
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.Fragment,new Skat()).commit();
                    }
                });
                dialogBuilder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog alert_final2 = dialogBuilder.create();
                alert_final2.setCancelable(false);
                alert_final2.show();
                TextView pos2 = alert_final2.getButton(DialogInterface.BUTTON_POSITIVE);
                if(pos2!=null)pos2.setTextColor(getResources().getColor(R.color.textcolorDark));
                TextView neg2 =alert_final2.getButton(DialogInterface.BUTTON_NEGATIVE);
                if(neg2!=null) neg2.setTextColor(getResources().getColor(R.color.textcolorDark));
            }else {
                    Toast.makeText(getActivity(),"Kein Alleinspieler ausgewählt. Es muss ein Alleinspieler ausgewählt werden",Toast.LENGTH_LONG).show();
                    input_punkte(skat_partie);
                };
        }});
        AlertDialog alert_final = alert.create();
        alert_final.setCancelable(false);
        alert_final.show();
        TextView pos = alert_final.getButton(DialogInterface.BUTTON_POSITIVE);
        if(pos!=null)pos.setTextColor(getResources().getColor(R.color.textcolorDark));
        TextView neg =alert_final.getButton(DialogInterface.BUTTON_NEGATIVE);
        if(neg!=null) neg.setTextColor(getResources().getColor(R.color.textcolorDark));


}

    public void delete_alert(Activity activity, int position) {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(activity);
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(activity);
        builder.setTitle("Löschen");
        builder.setMessage("Wirklich löschen?");
        CheckBox checkBox = new CheckBox(activity);
        checkBox.setText("Nicht mehr nachfragen");
        builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                viewModel.delete(viewModel.getAllSkat_Partien().getValue().get(position));
                if (checkBox.isChecked()) {
                    mPrefs.edit().putBoolean("ASK_DELETE", false).apply();
                }
            }
        });
        builder.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (checkBox.isChecked()) {
                    SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(activity);
                    mPrefs.edit().putBoolean("ASK_DELETE", false).apply();
                }
            }
        });
        builder.setView(checkBox);
        AlertDialog alert = builder.create();
        alert.setCancelable(false);
        alert.show();
        TextView pos = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        if (pos != null)
            pos.setTextColor(activity.getResources().getColor(R.color.textcolorDark));
        TextView neg = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        if (neg != null)
            neg.setTextColor(activity.getResources().getColor(R.color.textcolorDark));

    }

    public void update_partie(Skat_Partie partie){
        CharSequence[] optionen = {"Namen der Partie", "Namen der Spieler", "Punkte der Spieler"};
        final int[] ausgewählt = {0};
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        builder.setTitle("Eingeben, was geändert werden soll");
        builder.setSingleChoiceItems(optionen, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               ausgewählt[0] = i;
            }
        });
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(ausgewählt[0]==0){
                    update_titel(partie);
                } else if(ausgewählt[0]==1){
                    update_name(partie);
                } else if(ausgewählt[0]==2){
                    update_punkte(partie);
                }
            }
        });
        builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alert = builder.create();
        alert.setCancelable(false);
        alert.show();
        TextView pos = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        if(pos!=null)pos.setTextColor(getResources().getColor(R.color.textcolorDark));
        TextView neg =alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        if(neg!=null) neg.setTextColor(getResources().getColor(R.color.textcolorDark));
    }

    public void update_titel(Skat_Partie partie){
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        builder.setTitle("Name der Partie bearbeiten");
        builder.setMessage("Bitte geänderten Namen eingeben");
        EditText edit = new EditText(getActivity());
        edit.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        builder.setView(edit);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            viewModel.add(new Skat_Partie(edit.getText().toString(),partie.getSkat_spieler(),partie.getTrumpf(),partie.getGewinnstufe()));
            viewModel.delete(partie);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.Fragment, new Skat()).commit();
            }
        });
        builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alert = builder.create();
        alert.setCancelable(false);
        alert.show();
        TextView pos = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        if(pos!=null)pos.setTextColor(getResources().getColor(R.color.textcolorDark));
        TextView neg =alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        if(neg!=null) neg.setTextColor(getResources().getColor(R.color.textcolorDark));
    }

    public void update_name(Skat_Partie partie){
        int s=0;
        final int[] selected = {0 };
        for (int i = 0; i < partie.getSkat_spieler().size() ; i++) {
            if(!partie.getSkat_spieler().get(i).getName().equals("")){s++;}
        }
        String[] options = new String[s];
        int a=0;
        for (int i = 0; i < partie.getSkat_spieler().size() ; i++) {
            if(!partie.getSkat_spieler().get(i).getName().equals("")){
                options[a]=partie.getSkat_spieler().get(i).getName();
                a++;
            }
        }
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        builder.setTitle("Auswählen, welcher Name geändert werden soll");
        builder.setSingleChoiceItems(options, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                selected[0] =i;
            }
        });
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MaterialAlertDialogBuilder alert = new MaterialAlertDialogBuilder(getActivity());
                alert.setTitle("Name bearbeiten");
                alert.setMessage("Neuen Namen eingeben");
                EditText edit = new EditText(getActivity());
                edit.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
                alert.setView(edit);
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        List<Skat_Spieler> spieler = new ArrayList<>();
                        for (int j = 0; j < partie.getSkat_spieler().size() ; j++) {
                            if(j==selected[0]){
                                spieler.add(new Skat_Spieler(edit.getText().toString(), partie.getSkat_spieler().get(j).getPunkte(), partie.getSkat_spieler().get(j).getSumme()));
                            } else  spieler.add(new Skat_Spieler(partie.getSkat_spieler().get(j).getName(), partie.getSkat_spieler().get(j).getPunkte(), partie.getSkat_spieler().get(j).getSumme()));
                        }
                        viewModel.update(new Skat_Partie(partie.getPartiebezeichnung(),spieler,partie.getTrumpf(),partie.getGewinnstufe()));
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.Fragment, new Skat()).commit();
                    }
                });
                alert.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog Alert = alert.create();
                Alert.setCancelable(false);
                Alert.show();
                TextView pos = Alert.getButton(DialogInterface.BUTTON_POSITIVE);
                if(pos!=null)pos.setTextColor(getResources().getColor(R.color.textcolorDark));
                TextView neg =Alert.getButton(DialogInterface.BUTTON_NEGATIVE);
                if(neg!=null) neg.setTextColor(getResources().getColor(R.color.textcolorDark));
            }
        });
        builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alert = builder.create();
        alert.setCancelable(false);
        alert.show();
        TextView pos = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        if(pos!=null)pos.setTextColor(getResources().getColor(R.color.textcolorDark));
        TextView neg =alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        if(neg!=null) neg.setTextColor(getResources().getColor(R.color.textcolorDark));
    }

    public void update_punkte(Skat_Partie partie){
        int s=0;
        final int[] selected = { 0 };
        for (int i = 0; i < partie.getSkat_spieler().size() ; i++) {
            if(!partie.getSkat_spieler().get(i).getName().equals("")){s++;}
        }
        String[] options = new String[s];
        int a=0;
        for (int i = 0; i < partie.getSkat_spieler().size() ; i++) {
            if(!partie.getSkat_spieler().get(i).getName().equals("")){
                options[a]=partie.getSkat_spieler().get(i).getName();
                a++;
            }
        }
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        builder.setTitle("Auswählen, von wen die Platzierungen geändert werden soll");
        builder.setSingleChoiceItems(options, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                selected[0] =i;
            }
        });
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final int[] choise = {0};
                MaterialAlertDialogBuilder alert = new MaterialAlertDialogBuilder(getActivity());
                alert.setTitle("Auswählen, welche Platzierung bearbeitet werden soll");
                ;
                CharSequence[] punkte = new CharSequence[partie.getSkat_spieler().get(selected[0]).getPunkte().size()];
                for (int k = 0; k < partie.getSkat_spieler().get(selected[0]).getPunkte().size(); k++) {
                    punkte[k] = k+1+ ". Runde: "+partie.getSkat_spieler().get(selected[0]).getPunkte().get(k);
                }
                alert.setSingleChoiceItems(punkte, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        choise[0] = i;
                    }
                });
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        LayoutInflater inflater = LayoutInflater.from(getActivity());
                        View eingabe = inflater.inflate(R.layout.input_skat, null);
                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                        Chip re = eingabe.findViewById(R.id.skat_re);
                        Chip kontra = eingabe.findViewById(R.id.skat_kontra);
                        Chip bock = eingabe.findViewById(R.id.skat_bock);
                        final NumberPicker gewinnstufe = eingabe.findViewById(R.id.skat_gewinnstufe);
                        final NumberPicker grundwert = eingabe.findViewById(R.id.skat_grundwert);
                        final NumberPicker bonus_np = eingabe.findViewById(R.id.skat_bonus);
                        final CheckBox gewonnen = eingabe.findViewById(R.id.skat_gewonnen);
                        final CheckBox skat = eingabe.findViewById(R.id.skat_skat);
                        bonus_np.setMinValue(1);
                        bonus_np.setMaxValue(6);
                        bonus_np.setDisplayedValues(new String[]{"Einfach", "Schneider", "Schneider angesagt", "Schwarz", "Schwarz angesagt", "Offen"});
                        //Gewinnstufen anpassen, wenn Skat genommen oder liegen gelassen wurde
                        skat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    bonus_np.setMinValue(0);
                                    bonus_np.setMaxValue(2);
                                    bonus_np.setValue(0);
                                    bonus_np.setDisplayedValues(new String[]{"Einfach", "Schneider", "Schwarz"});
                                } else {
                                    bonus_np.setMinValue(1);
                                    bonus_np.setMaxValue(6);
                                    bonus_np.setDisplayedValues(new String[]{"Einfach", "Schneider", "Schneider angesagt", "Schwarz", "Schwarz angesagt", "Offen"});
                                }

                                skat.setClickable(false);
                            }
                        });
                        grundwert.setMinValue(9);
                        grundwert.setMaxValue(15);
                        grundwert.setDisplayedValues(new String[]{"♦", "♥", "♠", "♣", "Grand", "Null", "Null Ouvert"});
                        gewinnstufe.setMinValue(2);
                        gewinnstufe.setMaxValue(5);
                        gewinnstufe.setDisplayedValues(new String[]{"mit/ohne 1", "mit/ohne 2", "mit/ohne 3", "mit/ohne 4"});
                        dialogBuilder.setView(eingabe);
                        dialogBuilder.setTitle("Spielwert");
                        dialogBuilder.setMessage("Spielwert der Runde eingeben und ob der Alleinspieler gewonnen oder verloren hat");
                        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                int u = 0;
                                int bonus = 1;
                                //Punkteberechnung
                                if (re.isChecked()) {
                                    bonus = bonus * 2;
                                }
                                if (kontra.isChecked()) {
                                    bonus = bonus * 2;
                                }
                                if (bock.isChecked()) {
                                    bonus = bonus * 2;
                                }
                                if (!gewonnen.isChecked()) {
                                    if (grundwert.getValue() == 13) {
                                        u = 24 * (-2) * (gewinnstufe.getValue() + bonus_np.getValue()) * bonus;
                                    } else if (grundwert.getValue() == 14) {
                                        u = -46;
                                    } else if (grundwert.getValue() == 15) {
                                        u = -92;
                                    } else
                                        u = grundwert.getValue() * (-2) * (gewinnstufe.getValue() + bonus_np.getValue()) * bonus;
                                } else {
                                    if (grundwert.getValue() == 13) {
                                        u = 24 * (gewinnstufe.getValue() + bonus_np.getValue()) * bonus;
                                    } else if (grundwert.getValue() == 14) {
                                        u = 23;
                                    } else if (grundwert.getValue() == 15) {
                                        u = 46;
                                    } else
                                        u = grundwert.getValue() * (gewinnstufe.getValue() + bonus_np.getValue()) * bonus;
                                }
                                //Punkte der Runde in Variaeln zwischenspeichern und diese in Datenbank schreiben
                                List<Skat_Spieler> spieler = new ArrayList<>();
                                for (int k = 0; k < partie.getSkat_spieler().size(); k++) {
                                    if (k == selected[0]) {
                                        List<Integer> punkte = new ArrayList<>();
                                        for (int j = 0; j < partie.getSkat_spieler().get(k).getPunkte().size(); j++) {
                                            if (choise[0] == j) {
                                                punkte.add(u);
                                            } else
                                                punkte.add(partie.getSkat_spieler().get(k).getPunkte().get(j));
                                        }
                                        int sum = 0;
                                        for (int l = 0; l < punkte.size(); l++) {
                                            sum = sum + punkte.get(l);
                                        }
                                        spieler.add(new Skat_Spieler(partie.getSkat_spieler().get(k).getName(), punkte, sum));
                                    } else {
                                        spieler.add(partie.getSkat_spieler().get(k));
                                    }
                                }
                                List<String> update_trumpf = new ArrayList<>();
                                for (int j = 0; j < partie.getTrumpf().size(); j++) {
                                    if (j == choise[0]) {
                                        update_trumpf.add(String.valueOf(grundwert.getValue()));
                                    } else update_trumpf.add(partie.getTrumpf().get(j));
                                }
                                List<String> update_gewinnstufe = new ArrayList<>();
                                for (int j = 0; j < partie.getGewinnstufe().size(); j++) {
                                    if (j == choise[0]) {

                                        if (skat.isChecked()) {
                                            switch (bonus_np.getValue()) {
                                                case 0: {
                                                    update_gewinnstufe.add("Einfach");
                                                    break;
                                                }
                                                case 1: {
                                                    update_gewinnstufe.add("Schneider");
                                                    break;
                                                }
                                                case 2: {
                                                    update_gewinnstufe.add("Schneider angesagt");
                                                    break;
                                                }
                                            }
                                        } else if (!skat.isChecked()) {
                                            switch (bonus_np.getValue()) {
                                                case 1: {
                                                    update_gewinnstufe.add("Einfach");
                                                    break;
                                                }
                                                case 2: {
                                                    update_gewinnstufe.add("Schneider");
                                                    break;
                                                }
                                                case 3: {
                                                    update_gewinnstufe.add("Schneider angesagt");
                                                    break;
                                                }
                                                case 4: {
                                                    update_gewinnstufe.add("Schwarz");
                                                    break;
                                                }
                                                case 5: {
                                                    update_gewinnstufe.add("Schwarz angesagt");
                                                    break;
                                                }
                                                case 6: {
                                                    update_gewinnstufe.add("Offene Hand");
                                                    break;
                                                }
                                            }
                                        }
                                    } else update_gewinnstufe.add(partie.getGewinnstufe().get(j));
                                }
                                viewModel.update(new Skat_Partie(partie.getPartiebezeichnung(), spieler, update_trumpf, update_gewinnstufe));
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.Fragment, new Skat()).commit();
                            }
                        });
                        dialogBuilder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        AlertDialog alert_final2 = dialogBuilder.create();
                        alert_final2.setCancelable(false);
                        alert_final2.show();
                        TextView pos2 = alert_final2.getButton(DialogInterface.BUTTON_POSITIVE);
                        if (pos2 != null)
                            pos2.setTextColor(getResources().getColor(R.color.textcolorDark));
                        TextView neg2 = alert_final2.getButton(DialogInterface.BUTTON_NEGATIVE);
                        if (neg2 != null)
                            neg2.setTextColor(getResources().getColor(R.color.textcolorDark));
                        }
                    });
                alert.setNegativeButton("Abbrechen", (dialogInterface13, i13) -> {

                });
                AlertDialog Alert = alert.create();
                Alert.setCancelable(false);
                Alert.show();
                TextView pos = Alert.getButton(DialogInterface.BUTTON_POSITIVE);
                if(pos!=null)pos.setTextColor(getResources().getColor(R.color.textcolorDark));
                TextView neg =Alert.getButton(DialogInterface.BUTTON_NEGATIVE);
                if(neg!=null) neg.setTextColor(getResources().getColor(R.color.textcolorDark));
                }
                });
                builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog Alert = builder.create();
                Alert.setCancelable(false);
                Alert.show();
                TextView pos = Alert.getButton(DialogInterface.BUTTON_POSITIVE);
                if (pos != null) pos.setTextColor(getResources().getColor(R.color.textcolorDark));
                TextView neg = Alert.getButton(DialogInterface.BUTTON_NEGATIVE);
                if (neg != null) neg.setTextColor(getResources().getColor(R.color.textcolorDark));

    }
}