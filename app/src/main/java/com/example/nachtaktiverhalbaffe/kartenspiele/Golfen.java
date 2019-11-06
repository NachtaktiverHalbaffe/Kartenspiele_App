package com.example.nachtaktiverhalbaffe.kartenspiele;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.room.Room;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Database.Golfen.GolfenViewModel;
import Database.Golfen.Golfen_Partie;
import Database.Golfen.Golfen_Spieler;
import Database.Golfen.Golf_database;
import recyclerview.golfen_adapter;

import static java.lang.Integer.valueOf;

/**
 * Created by NachtaktiverHalbaffe on 29.11.2017.
 */

public class Golfen extends Fragment {
    private GolfenViewModel viewModel;
    public static golfen_adapter adapter;
    public Integer i = 0, j = 0;
    List<Golfen_Spieler> golfen_spieler;
    public Golfen_Partie golfen_partien = null;
    static RecyclerView mRecyclerview;
    int f = 0;
    public static boolean alive=false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {   //Fragment Preambel
        View rootView = inflater.inflate(R.layout.golfen, container, false);
        alive=false;
        getActivity().setTitle("Golfen");
        FloatingActionButton anl = rootView.findViewById(R.id.golfen_anleitung);
        anl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String anleitung =getString(R.string.Golfen_Spielanleitung) + "\n\n" + getString(R.string.Golfen_Punktewertung);
                AlertDialog.Builder builder_kon = new AlertDialog.Builder(getActivity());
                builder_kon.setTitle("Golfen");
                builder_kon.setMessage(anleitung);
                AlertDialog dialog = builder_kon.create();
                dialog.show();
            }
        });
        FloatingActionButton add = rootView.findViewById(R.id.golfen_add);
        mRecyclerview = rootView.findViewById(R.id.golfen_card);
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
        adapter = new golfen_adapter(getActivity(), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int k = 0; k < viewModel.getAllGolfen_Partien().getValue().size(); k++) {
                    if (golfen_adapter.id.equals(viewModel.getAllGolfen_Partien().getValue().get(k).getPartiebezeichnung())) {
                        golfen_spieler = new ArrayList<>();
                        alive = true;
                        input_punkte(0, golfen_spieler);
                    }
                }
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int k = 0; k < viewModel.getAllGolfen_Partien().getValue().size(); k++) {
                    if (golfen_adapter.id.equals(viewModel.getAllGolfen_Partien().getValue().get(k).getPartiebezeichnung())) {
                        golfen_spieler = new ArrayList<>();
                        alive = true;
                        update_partie(viewModel.getAllGolfen_Partien().getValue().get(k));
                    }
                }
            }
        });
        mRecyclerview.setAdapter(adapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alive=true;
                new_players(0, new ArrayList<>());
            }
        });
        if (i != 0) {
            adapter.notifyDataSetChanged();
            mRecyclerview.setLayoutManager(team_llm);
            i = 0;
        }

        //ViewModel implementieren und mit Datenbank verlinken
        viewModel = ViewModelProviders.of(getActivity()).get(GolfenViewModel.class);
        viewModel.getAllGolfen_Partien().observe(getActivity(), new Observer<List<Golfen_Partie>>() {
            @Override
            public void onChanged(@Nullable List<Golfen_Partie> golfen_parties) {
                adapter.submitList(golfen_parties);
            }
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                alive=true;
                SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                if (mPrefs.getBoolean("ASK_DELETE",true)){
                    delete_alert(getActivity(), viewHolder.getAdapterPosition());
                }else{
                    viewModel.delete(viewModel.getAllGolfen_Partien().getValue().get(viewHolder.getAdapterPosition()));
                }
            }
        }).attachToRecyclerView(mRecyclerview);
        return rootView;
    }

    void new_players(int nummer, List<Golfen_Spieler> golfen_spieler) {
        if (nummer < 8) {

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
            final EditText input = new EditText(getActivity());
            input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
            dialogBuilder.setView(input);
            dialogBuilder.setTitle("Spieler " + (nummer + 1));
            dialogBuilder.setMessage("Name des " + (nummer + 1) + ". Spielers eingeben oder frei lassen");
            dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    golfen_spieler.add(new Golfen_Spieler(input.getText().toString(), null, 0));
                    j++;
                    new_players(j, golfen_spieler);
                }
            });
            dialogBuilder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog alert = dialogBuilder.create();
            alert.setCancelable(false);
            alert.show();
            TextView pos = alert.getButton(DialogInterface.BUTTON_POSITIVE);
            if(pos!=null)pos.setTextColor(getResources().getColor(R.color.textcolorDark));
            TextView neg =alert.getButton(DialogInterface.BUTTON_NEGATIVE);
            if(neg!=null) neg.setTextColor(getResources().getColor(R.color.textcolorDark));
        } else new_List(golfen_spieler);
    }

    void new_List(final List<Golfen_Spieler> golfen_spieler) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        dialogBuilder.setView(input);
        dialogBuilder.setTitle("Titel");
        dialogBuilder.setMessage("Name der Partie eingeben");
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (!input.getText().toString().equals("")) {
                    viewModel.add(new Golfen_Partie(input.getText().toString(), golfen_spieler));
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.Fragment, new Golfen()).commit();
                    j = 0;
                } else {
                    Toast toast = Toast.makeText(getActivity(), "Bitte einen gültigen Partienamen eingeben", Toast.LENGTH_LONG);
                    toast.show();
                    new_List(golfen_spieler);
                }
            }
        });
        dialogBuilder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

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

    public void input_punkte(int nummer, List<Golfen_Spieler> golfen_spielers) {
        for (int k = 0; k < viewModel.getAllGolfen_Partien().getValue().size(); k++) {
            if (golfen_adapter.id.equals(viewModel.getAllGolfen_Partien().getValue().get(k).getPartiebezeichnung())) {
                if (nummer < 8) {
                    String name = viewModel.getAllGolfen_Partien().getValue().get(k).getGolfen_spieler().get(nummer).getName();
                    Golfen_Spieler golf_spieler = viewModel.getAllGolfen_Partien().getValue().get(k).getGolfen_spieler().get(nummer);
                    if (!golf_spieler.getName().equals("")) {
                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                        final EditText input = new EditText(getActivity());
                        input.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        dialogBuilder.setView(input);
                        dialogBuilder.setTitle(name);
                        dialogBuilder.setMessage("Punkte von "+ name + " von dieser Runde von eingeben");
                        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                int sum = 0;
                                List<Integer> punkte = new ArrayList<>();
                                if (golf_spieler.getPunkte() != null) {
                                    punkte = golf_spieler.getPunkte();
                                } else {
                                    punkte = new ArrayList<>();
                                }
                                if (!input.getText().toString().equals("")) {
                                    punkte.add(Integer.valueOf(input.getText().toString()));
                                    for (int j = 0; j < punkte.size(); j++) {
                                        sum = sum + punkte.get(j);
                                    }
                                    golfen_spielers.add(new Golfen_Spieler(name, punkte, sum));
                                    f = nummer + 1;
                                    input_punkte(f, golfen_spielers);

                                }
                     else {
                        Toast toast = Toast.makeText(getActivity(), "Bitte Punkte eingeben oder 0 eintragen, falls der Spieler die Runde ausgesetzt hat", Toast.LENGTH_LONG);
                        toast.show();
                        input_punkte(nummer, golfen_spielers);
                     }}
                        });
                        dialogBuilder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        AlertDialog alert= dialogBuilder.create();
                        alert.setCancelable(false);
                        alert.show();
                        TextView nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
                        if(nbutton!=null){
                        nbutton.setTextColor(getResources().getColor(R.color.textcolorDark));}
                        TextView pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
                        if(pbutton!=null){
                        pbutton.setTextColor(getResources().getColor(R.color.textcolorDark));}


                }else{
                        golfen_spielers.add(new Golfen_Spieler("",null,0));
                        f=nummer+1;
                        input_punkte(f,golfen_spielers);
                    }
                } else {
                    String partie = viewModel.getAllGolfen_Partien().getValue().get(k).getPartiebezeichnung();
                    viewModel.update(new Golfen_Partie(partie, golfen_spielers));
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.Fragment, new Golfen()).commit();
                }
            }
        }
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
                if (checkBox.isChecked()) {
                    mPrefs.edit().putBoolean("ASK_DELETE", false).apply();
                }
                viewModel.delete(viewModel.getAllGolfen_Partien().getValue().get(position));
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

    public void update_partie(Golfen_Partie partie){
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
                } else if(ausgewählt[0] ==2){
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

    public void update_titel(Golfen_Partie partie){
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        builder.setTitle("Name der Partie bearbeiten");
        builder.setMessage("Bitte geänderten Namen eingeben");
        EditText edit = new EditText(getActivity());
        edit.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        builder.setView(edit);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                viewModel.add(new Golfen_Partie(edit.getText().toString(),partie.getGolfen_spieler()));
                viewModel.delete(partie);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.Fragment, new Golfen()).commit();
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

    public void update_name(Golfen_Partie partie){
        int s=0;
        final int[] selected = {0 };
        for (int i = 0; i < partie.getGolfen_spieler().size() ; i++) {
            if(!partie.getGolfen_spieler().get(i).getName().equals("")){s++;}
        }
        String[] options = new String[s];
        int a=0;
        for (int i = 0; i < partie.getGolfen_spieler().size() ; i++) {
            if(!partie.getGolfen_spieler().get(i).getName().equals("")){
                options[a]=partie.getGolfen_spieler().get(i).getName();
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
                        List<Golfen_Spieler> spieler = new ArrayList<>();
                        for (int j = 0; j < partie.getGolfen_spieler().size() ; j++) {
                            if(j==selected[0]){
                                spieler.add(new Golfen_Spieler(edit.getText().toString(), partie.getGolfen_spieler().get(j).getPunkte(), partie.getGolfen_spieler().get(j).getSumme()));
                            } else  spieler.add(new Golfen_Spieler(partie.getGolfen_spieler().get(j).getName(), partie.getGolfen_spieler().get(j).getPunkte(), partie.getGolfen_spieler().get(j).getSumme()));
                        }
                        viewModel.update(new Golfen_Partie(partie.getPartiebezeichnung(),spieler));
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.Fragment, new Golfen()).commit();
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

    public void update_punkte(Golfen_Partie partie){
        int s=0;
        final int[] selected = { 0 };
        for (int i = 0; i < partie.getGolfen_spieler().size() ; i++) {
            if(!partie.getGolfen_spieler().get(i).getName().equals("")){s++;}
        }
        String[] options = new String[s];
        int a=0;
        for (int i = 0; i < partie.getGolfen_spieler().size() ; i++) {
            if(!partie.getGolfen_spieler().get(i).getName().equals("")){
                options[a]=partie.getGolfen_spieler().get(i).getName();
                a++;
            }
        }
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        builder.setTitle("Auswählen, von wen die Punkte geändert werden soll");
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
                alert.setTitle("Auswählen, welche Runde bearbeitet werden soll");
                CharSequence[] punkte = new CharSequence[partie.getGolfen_spieler().get(selected[0]).getPunkte().size()];
                for (int k = 0; k < partie.getGolfen_spieler().get(selected[0]).getPunkte().size() ; k++) {
                    punkte[k]= k+1 + ". Runde: "+partie.getGolfen_spieler().get(selected[0]).getPunkte().get(k);
                }
                alert.setSingleChoiceItems(punkte, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        choise[0] =i;
                    }
                });
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                        alertDialogBuilder.setTitle("Punkte ändern");
                        alertDialogBuilder.setMessage("Neuen Punktestand der Runde eingeben");
                        EditText edit = new EditText(getActivity());
                        edit.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        alertDialogBuilder.setView(edit);
                        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                List<Integer> punkte = new ArrayList<>();
                                for (int j = 0; j < partie.getGolfen_spieler().get(selected[0]).getPunkte().size() ; j++) {
                                    if(j==choise[0]){
                                        punkte.add(Integer.valueOf(edit.getText().toString()));
                                    }else punkte.add(partie.getGolfen_spieler().get(selected[0]).getPunkte().get(j));
                                }
                                List<Golfen_Spieler> spieler = new ArrayList<>();
                                for (int j = 0; j <partie.getGolfen_spieler().size() ; j++) {
                                    if(j==selected[0]){
                                        int summe =0;
                                        for (int k = 0; k <partie.getGolfen_spieler().get(selected[0]).getPunkte().size() ; k++) {
                                            summe = summe + partie.getGolfen_spieler().get(selected[0]).getPunkte().get(k);
                                        }
                                        spieler.add(new Golfen_Spieler(partie.getGolfen_spieler().get(j).getName(),punkte, summe));
                                    } else spieler.add(partie.getGolfen_spieler().get(j));
                                }
                                viewModel.update(new Golfen_Partie(partie.getPartiebezeichnung(),spieler));
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.Fragment,new Golfen()).commit();
                            }
                        });
                        alertDialogBuilder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        AlertDialog alert = alertDialogBuilder.create();
                        alert.setCancelable(false);
                        alert.show();
                        TextView pos = alert.getButton(DialogInterface.BUTTON_POSITIVE);
                        if(pos!=null)pos.setTextColor(getResources().getColor(R.color.textcolorDark));
                        TextView neg =alert.getButton(DialogInterface.BUTTON_NEGATIVE);
                        if(neg!=null) neg.setTextColor(getResources().getColor(R.color.textcolorDark));
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
}



