package com.example.nachtaktiverhalbaffe.kartenspiele;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import Database.Schwimmen.SchwimmenViewModel;
import Database.Schwimmen.Schwimmen_Database;
import Database.Schwimmen.Schwimmen_Partie;
import Database.Schwimmen.Schwimmen_Spieler;
import recyclerview.schwimmen_adapter;

public class Schwimmen extends Fragment {
    private SchwimmenViewModel viewModel;
    public static schwimmen_adapter adapter;
    public Integer i = 0, j = 0;
    public Schwimmen_Partie schwimmen_partien = null;
    static RecyclerView mRecyclerview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {   //Fragment Preambel
        View rootView = inflater.inflate(R.layout.schwimmen, container, false);
        getActivity().setTitle("Schwimmen");
        FloatingActionButton anl = rootView.findViewById(R.id.schwimmen_anleitung);
        anl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String anleitung =getString(R.string.Schwimmen_Spielanleitung) + "\n\n" + getString(R.string.Schwimmen_Punktewertung);
                AlertDialog.Builder builder_kon = new AlertDialog.Builder(getActivity());
                builder_kon.setTitle("Schwimmen");
                builder_kon.setMessage(anleitung);
                AlertDialog dialog = builder_kon.create();
                dialog.show();
            }
        });
        FloatingActionButton add = rootView.findViewById(R.id.schwimmen_add);
        mRecyclerview = rootView.findViewById(R.id.schwimmen_card);
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
        mRecyclerview.hasFixedSize();
        adapter = new schwimmen_adapter(getActivity(), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int k = 0; k < viewModel.getAllSchwimmen_Partien().getValue().size(); k++) {
                    if (schwimmen_adapter.id.equals(viewModel.getAllSchwimmen_Partien().getValue().get(k).getTitel())) {
                        input_punkte(viewModel.getAllSchwimmen_Partien().getValue().get(k));
                    }
                }
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int k = 0; k < viewModel.getAllSchwimmen_Partien().getValue().size(); k++) {
                    if (schwimmen_adapter.id.equals(viewModel.getAllSchwimmen_Partien().getValue().get(k).getTitel())) {
                        update_partie(viewModel.getAllSchwimmen_Partien().getValue().get(k));
                    }
                }
            }
        });
        mRecyclerview.setAdapter(adapter);
        //ViewModel implementieren und mit Datenbank verlinken
        viewModel = ViewModelProviders.of(getActivity()).get(SchwimmenViewModel.class);
        viewModel.getAllSchwimmen_Partien().observe(getActivity(), new Observer<List<Schwimmen_Partie>>() {
            @Override
            public void onChanged(@Nullable List<Schwimmen_Partie> schwimmen_parties) {
                adapter.submitList(schwimmen_parties);
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_players(0, new ArrayList<>());
            }
        });
        if (i != 0) {
            adapter.notifyDataSetChanged();
            mRecyclerview.setLayoutManager(team_llm);
            i = 0;
        }

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                if (mPrefs.getBoolean("ASK_DELETE",true)){
                    delete_alert(getActivity(), viewHolder.getAdapterPosition());
                }else{
                    viewModel.delete(viewModel.getAllSchwimmen_Partien().getValue().get(viewHolder.getAdapterPosition()));
                }
            }
        }).attachToRecyclerView(mRecyclerview);
        return rootView;
    }

    void new_players(int nummer, List<Schwimmen_Spieler> schwimmen_spieler) {
        if (nummer < 8) {

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
            final EditText input = new EditText(getActivity());
            input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
            dialogBuilder.setView(input);
            dialogBuilder.setTitle("Spieler " + (nummer + 1));
            dialogBuilder.setMessage("Name des " + (nummer + 1) + ". Spielers eingeben oder frei lassen");
            dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    schwimmen_spieler.add(new Schwimmen_Spieler(input.getText().toString(), 3, "",false));
                    j++;
                    new_players(j, schwimmen_spieler);
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
        } else new_List(schwimmen_spieler);
    }

    void new_List(final List<Schwimmen_Spieler> schwimmen_spieler) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        dialogBuilder.setView(input);
        dialogBuilder.setTitle("Titel");
        dialogBuilder.setMessage("Name der Partie eingeben");
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (!input.getText().toString().equals("")) {
                    int h=0;
                    for (int k = 0; k <schwimmen_spieler.size() ; k++) {
                        if(!schwimmen_spieler.get(k).getName().equals(""))h++;
                    }
                    viewModel.add(new Schwimmen_Partie(input.getText().toString(), schwimmen_spieler, h,false));
                    j = 0;
                } else {
                    Toast toast = Toast.makeText(getActivity(), "Bitte einen gültigen Partienamen eingeben", Toast.LENGTH_LONG);
                    toast.show();
                    new_List(schwimmen_spieler);
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

    public void input_punkte(Schwimmen_Partie schwimmen_partie) {
        String[] gespielt = {""};
        final boolean[] geschwommen = {schwimmen_partie.isGeschwommen()};
        int[] verblieben = {schwimmen_partie.getVerbliebene_spieler()};
        MaterialAlertDialogBuilder alertdialog = new MaterialAlertDialogBuilder(getActivity());
        alertdialog.setTitle("Verlierer der Runde auswählen");
        int h=0;
        for (int k = 0; k <schwimmen_partie.getSchwimmen_spieler().size() ; k++) {
            if(!schwimmen_partie.getSchwimmen_spieler().get(k).getName().equals(""))h++;
        }
        switch (h) {
            case 1: {
                CharSequence[] namen = {schwimmen_partie.getSchwimmen_spieler().get(0).getName(), "Feuer (3 Asse)"};
                alertdialog.setSingleChoiceItems(namen, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == schwimmen_partie.getSchwimmen_spieler().size()) {
                            gespielt[0] = "Feuer";
                        } else if (!schwimmen_partie.getSchwimmen_spieler().get(i).isTod()) {
                            gespielt[0] = schwimmen_partie.getSchwimmen_spieler().get(i).getName();
                        } else {
                            Toast toast = Toast.makeText(getActivity(), "Der Spieler hat schon verloren", Toast.LENGTH_LONG);
                            toast.show();
                            input_punkte(schwimmen_partie);
                        }
                    }
                });
                break;
            }
            case 2: {
                CharSequence[] namen = {schwimmen_partie.getSchwimmen_spieler().get(0).getName(), schwimmen_partie.getSchwimmen_spieler().get(1).getName(), "Feuer (3 Asse)"};
                alertdialog.setSingleChoiceItems(namen, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == schwimmen_partie.getSchwimmen_spieler().size()) {
                            gespielt[0] = "Feuer";
                        } else if (!schwimmen_partie.getSchwimmen_spieler().get(i).isTod()) {
                            gespielt[0] = schwimmen_partie.getSchwimmen_spieler().get(i).getName();
                        } else {
                            Toast toast = Toast.makeText(getActivity(), "Der Spieler hat schon verloren", Toast.LENGTH_LONG);
                            toast.show();
                            input_punkte(schwimmen_partie);
                        }
                    }
                });

                break;
            }
            case 3: {
                CharSequence[] namen = {schwimmen_partie.getSchwimmen_spieler().get(0).getName(), schwimmen_partie.getSchwimmen_spieler().get(1).getName(), schwimmen_partie.getSchwimmen_spieler().get(2).getName(), "Feuer (3 Asse)"};
                alertdialog.setSingleChoiceItems(namen, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == schwimmen_partie.getSchwimmen_spieler().size()) {
                            gespielt[0] = "Feuer";
                        } else if (!schwimmen_partie.getSchwimmen_spieler().get(i).isTod()) {
                            gespielt[0] = schwimmen_partie.getSchwimmen_spieler().get(i).getName();
                        } else {
                            Toast toast = Toast.makeText(getActivity(), "Der Spieler hat schon verloren", Toast.LENGTH_LONG);
                            toast.show();
                            input_punkte(schwimmen_partie);
                        }
                    }
                });
                break;
            }
            case 4: {
                CharSequence[] namen = {schwimmen_partie.getSchwimmen_spieler().get(0).getName(), schwimmen_partie.getSchwimmen_spieler().get(1).getName(), schwimmen_partie.getSchwimmen_spieler().get(2).getName(), schwimmen_partie.getSchwimmen_spieler().get(3).getName(), "Feuer (3 Asse)"};
                alertdialog.setSingleChoiceItems(namen, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == schwimmen_partie.getSchwimmen_spieler().size()) {
                            gespielt[0] = "Feuer";
                        } else if (!schwimmen_partie.getSchwimmen_spieler().get(i).isTod()) {
                            gespielt[0] = schwimmen_partie.getSchwimmen_spieler().get(i).getName();
                        } else {
                            Toast toast = Toast.makeText(getActivity(), "Der Spieler hat schon verloren", Toast.LENGTH_LONG);
                            toast.show();
                            input_punkte(schwimmen_partie);
                        }
                    }
                });
                break;
            }

            case 5: {
                CharSequence[] namen = {schwimmen_partie.getSchwimmen_spieler().get(0).getName(), schwimmen_partie.getSchwimmen_spieler().get(1).getName(), schwimmen_partie.getSchwimmen_spieler().get(2).getName(), schwimmen_partie.getSchwimmen_spieler().get(3).getName(), schwimmen_partie.getSchwimmen_spieler().get(4).getName(), "Feuer (3 Asse)"};
                alertdialog.setSingleChoiceItems(namen, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == schwimmen_partie.getSchwimmen_spieler().size()) {
                            gespielt[0] = "Feuer";
                        } else if (!schwimmen_partie.getSchwimmen_spieler().get(i).isTod()) {
                            gespielt[0] = schwimmen_partie.getSchwimmen_spieler().get(i).getName();
                        } else {
                            Toast toast = Toast.makeText(getActivity(), "Der Spieler hat schon verloren", Toast.LENGTH_LONG);
                            toast.show();
                            input_punkte(schwimmen_partie);
                        }
                    }
                });
                break;
            }

            case 6: {
                CharSequence[] namen = {schwimmen_partie.getSchwimmen_spieler().get(0).getName(), schwimmen_partie.getSchwimmen_spieler().get(1).getName(), schwimmen_partie.getSchwimmen_spieler().get(2).getName(), schwimmen_partie.getSchwimmen_spieler().get(3).getName(), schwimmen_partie.getSchwimmen_spieler().get(4).getName(), schwimmen_partie.getSchwimmen_spieler().get(5).getName(), "Feuer (3 Asse)"};
                alertdialog.setSingleChoiceItems(namen, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == schwimmen_partie.getSchwimmen_spieler().size()) {
                            gespielt[0] = "Feuer";
                        } else if (!schwimmen_partie.getSchwimmen_spieler().get(i).isTod()) {
                            gespielt[0] = schwimmen_partie.getSchwimmen_spieler().get(i).getName();
                        } else {
                            Toast toast = Toast.makeText(getActivity(), "Der Spieler hat schon verloren", Toast.LENGTH_LONG);
                            toast.show();
                            input_punkte(schwimmen_partie);
                        }
                    }
                });
                break;
            }

            case 7: {
                CharSequence[] namen = {schwimmen_partie.getSchwimmen_spieler().get(0).getName(), schwimmen_partie.getSchwimmen_spieler().get(1).getName(), schwimmen_partie.getSchwimmen_spieler().get(2).getName(), schwimmen_partie.getSchwimmen_spieler().get(3).getName(), schwimmen_partie.getSchwimmen_spieler().get(4).getName(), schwimmen_partie.getSchwimmen_spieler().get(5).getName(), schwimmen_partie.getSchwimmen_spieler().get(6).getName(), "Feuer (3 Asse)"};
                alertdialog.setSingleChoiceItems(namen, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == schwimmen_partie.getSchwimmen_spieler().size()) {
                            gespielt[0] = "Feuer";
                        } else if (!schwimmen_partie.getSchwimmen_spieler().get(i).isTod()) {
                            gespielt[0] = schwimmen_partie.getSchwimmen_spieler().get(i).getName();
                        } else {
                            Toast toast = Toast.makeText(getActivity(), "Der Spieler hat schon verloren", Toast.LENGTH_LONG);
                            toast.show();
                            input_punkte(schwimmen_partie);
                        }
                    }
                });
                break;
            }

            case 8: {
                CharSequence[] namen = {schwimmen_partie.getSchwimmen_spieler().get(0).getName(), schwimmen_partie.getSchwimmen_spieler().get(1).getName(), schwimmen_partie.getSchwimmen_spieler().get(2).getName(), schwimmen_partie.getSchwimmen_spieler().get(3).getName(), schwimmen_partie.getSchwimmen_spieler().get(4).getName(), schwimmen_partie.getSchwimmen_spieler().get(5).getName(), schwimmen_partie.getSchwimmen_spieler().get(6).getName(), schwimmen_partie.getSchwimmen_spieler().get(7).getName(), "Feuer (3 Asse)"};
                alertdialog.setSingleChoiceItems(namen, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == schwimmen_partie.getSchwimmen_spieler().size()) {
                            gespielt[0] = "Feuer";
                        } else if (!schwimmen_partie.getSchwimmen_spieler().get(i).isTod()) {
                            gespielt[0] = schwimmen_partie.getSchwimmen_spieler().get(i).getName();
                        } else {
                            Toast toast = Toast.makeText(getActivity(), "Der Spieler hat schon verloren", Toast.LENGTH_LONG);
                            toast.show();
                            input_punkte(schwimmen_partie);
                        }
                    }
                });
                break;
            }
        }
        alertdialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                List<Schwimmen_Spieler> schwimmen_spieler = new ArrayList<>();
                if (!gespielt[0].equals("Feuer")) {
                    for (int k = 0; k < schwimmen_partie.getSchwimmen_spieler().size(); k++) {
                        if (gespielt[0].equals(schwimmen_partie.getSchwimmen_spieler().get(k).getName())) {
                            if (schwimmen_partie.getSchwimmen_spieler().get(k).getLeben() > 1) {
                                schwimmen_spieler.add(new Schwimmen_Spieler(schwimmen_partie.getSchwimmen_spieler().get(k).getName(), schwimmen_partie.getSchwimmen_spieler().get(k).getLeben() - 1, "", false));
                            } else if (schwimmen_partie.getSchwimmen_spieler().get(k).getLeben() == 1 && geschwommen[0] == false) {
                                schwimmen_spieler.add(new Schwimmen_Spieler(schwimmen_partie.getSchwimmen_spieler().get(k).getName(), 0, "Schwimmt", false));
                                geschwommen[0] = true;
                            } else{
                                schwimmen_spieler.add(new Schwimmen_Spieler(schwimmen_partie.getSchwimmen_spieler().get(k).getName(), 0, schwimmen_partie.getVerbliebene_spieler() + ".", true));
                                verblieben[0] = schwimmen_partie.getVerbliebene_spieler() - 1;

                            }
                        } else
                            schwimmen_spieler.add(schwimmen_partie.getSchwimmen_spieler().get(k));
                    }
                } else {
                    for (int k = 0; k < schwimmen_partie.getSchwimmen_spieler().size(); k++) {
                        if (schwimmen_partie.getSchwimmen_spieler().get(k).getLeben() > 1) {
                            schwimmen_spieler.add(new Schwimmen_Spieler(schwimmen_partie.getSchwimmen_spieler().get(k).getName(), schwimmen_partie.getSchwimmen_spieler().get(k).getLeben() - 1, "", false));
                        } else if (schwimmen_partie.getSchwimmen_spieler().get(k).getLeben()==1 && geschwommen[0] == false) {
                            schwimmen_spieler.add(new Schwimmen_Spieler(schwimmen_partie.getSchwimmen_spieler().get(k).getName(), 0, "Schwimmt", false));
                            geschwommen[0] = true;
                        } else if(schwimmen_partie.getSchwimmen_spieler().get(k).isTod()){
                            schwimmen_spieler.add(schwimmen_partie.getSchwimmen_spieler().get(k));
                        } else {
                            schwimmen_spieler.add(new Schwimmen_Spieler(schwimmen_partie.getSchwimmen_spieler().get(k).getName(), 0, schwimmen_partie.getVerbliebene_spieler() + ".", true));
                            verblieben[0] = schwimmen_partie.getVerbliebene_spieler() - 1;
                        }
                    }
                }
                viewModel.update(new Schwimmen_Partie(schwimmen_partie.getTitel(), schwimmen_spieler, verblieben[0], geschwommen[0]));
            }
        });

        alertdialog.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alert = alertdialog.create();
        alert.setCancelable(false);
        alert.show();
        TextView pos = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        if (pos != null) pos.setTextColor(getResources().getColor(R.color.textcolorDark));
        TextView neg = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        if (neg != null) neg.setTextColor(getResources().getColor(R.color.textcolorDark));
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
                viewModel.delete(viewModel.getAllSchwimmen_Partien().getValue().get(position));
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

    public void update_partie(Schwimmen_Partie partie){
        CharSequence[] optionen = {"Namen der Partie", "Namen der Spieler", "Leben der Spieler"};
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

    public void update_titel(Schwimmen_Partie partie){
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        builder.setTitle("Name der Partie bearbeiten");
        builder.setMessage("Bitte geänderten Namen eingeben");
        EditText edit = new EditText(getActivity());
        edit.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        builder.setView(edit);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                viewModel.add(new Schwimmen_Partie(edit.getText().toString(),partie.getSchwimmen_spieler(), partie.getVerbliebene_spieler(),partie.isGeschwommen()));
                viewModel.delete(partie);
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

    public void update_name(Schwimmen_Partie partie){
        int s=0;
        final int[] selected = {0 };
        for (int i = 0; i < partie.getSchwimmen_spieler().size() ; i++) {
            if(!partie.getSchwimmen_spieler().get(i).getName().equals("")){s++;}
        }
        String[] options = new String[s];
        int a=0;
        for (int i = 0; i < partie.getSchwimmen_spieler().size() ; i++) {
            if(!partie.getSchwimmen_spieler().get(i).getName().equals("")){
                options[a]=partie.getSchwimmen_spieler().get(i).getName();
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
                        List<Schwimmen_Spieler> spieler = new ArrayList<>();
                        for (int j = 0; j < partie.getSchwimmen_spieler().size() ; j++) {
                            if(j==selected[0]){
                                spieler.add(new Schwimmen_Spieler(edit.getText().toString(), partie.getSchwimmen_spieler().get(j).getLeben(), partie.getSchwimmen_spieler().get(j).getPlatzierung()));
                            } else  spieler.add(new Schwimmen_Spieler(partie.getSchwimmen_spieler().get(j).getName(), partie.getSchwimmen_spieler().get(j).getLeben(), partie.getSchwimmen_spieler().get(j).getPlatzierung()));
                        }
                        viewModel.update(new Schwimmen_Partie(partie.getTitel(),spieler,partie.getVerbliebene_spieler(),partie.isGeschwommen()));
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

    public void update_punkte(Schwimmen_Partie partie){
        int s=0;
        final int[] selected = { 0 };
        for (int i = 0; i < partie.getSchwimmen_spieler().size() ; i++) {
            if(!partie.getSchwimmen_spieler().get(i).getName().equals("")){s++;}
        }
        String[] options = new String[s];
        int a=0;
        for (int i = 0; i < partie.getSchwimmen_spieler().size() ; i++) {
            if(!partie.getSchwimmen_spieler().get(i).getName().equals("")){
                options[a]=partie.getSchwimmen_spieler().get(i).getName();
                a++;
            }
        }
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        builder.setTitle("Auswählen, von wen die verbleibenden Leben geändert werden soll");
        builder.setSingleChoiceItems(options, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                selected[0] =i;
            }
        });
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                        MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                        alertDialogBuilder.setTitle("Leben ändern");
                        alertDialogBuilder.setMessage("Neue Anzahl an verbleibenden Leben eingeben");
                        EditText edit = new EditText(getActivity());
                        edit.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        alertDialogBuilder.setView(edit);
                        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                List<Schwimmen_Spieler> spieler = new ArrayList<>();
                                for (int j = 0; j <partie.getSchwimmen_spieler().size() ; j++) {
                                    if(j==selected[0]){
                                        spieler.add(new Schwimmen_Spieler(partie.getSchwimmen_spieler().get(j).getName(),Integer.valueOf(edit.getText().toString()),partie.getSchwimmen_spieler().get(j).getPlatzierung(), partie.getSchwimmen_spieler().get(j).isTod()));
                                    } else spieler.add(partie.getSchwimmen_spieler().get(j));
                                }
                                viewModel.update(new Schwimmen_Partie(partie.getTitel(),spieler, partie.getVerbliebene_spieler(), partie.isGeschwommen()));
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
