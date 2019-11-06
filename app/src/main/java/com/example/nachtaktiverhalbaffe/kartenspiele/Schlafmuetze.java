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
import android.widget.NumberPicker;
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

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import Database.Schlafmuetze.SchlafmuetzeViewModel;
import Database.Schlafmuetze.Schlafmuetze_Partie;
import Database.Schlafmuetze.Schlafmuetze_Spieler;
import recyclerview.schlafmuetze_adapter;

public class Schlafmuetze extends Fragment {
    private SchlafmuetzeViewModel viewModel;
    public Integer j = 0;
    static RecyclerView mRecyclerview;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {   //Fragment Preambel
        View rootView = inflater.inflate(R.layout.arschloch, container, false);
        getActivity().setTitle("Schlafmütze");
        FloatingActionButton anl = rootView.findViewById(R.id.arschloch_anleitung);
        anl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String anleitung = "Ablauf:\n"+getString(R.string.Schlafmuetze_Anleitung) + "\n\nWertung:\n" + getString(R.string.Schlafmuetze_Wertung);
                AlertDialog.Builder builder_kon = new AlertDialog.Builder(getActivity());
                builder_kon.setTitle("Shithead");
                builder_kon.setMessage(anleitung);
                AlertDialog dialog = builder_kon.create();
                dialog.show();
            }
        });
        FloatingActionButton add = rootView.findViewById(R.id.arschloch_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_players(0, new ArrayList<>());
            }
        });
        //recyclerview initialisieren
        mRecyclerview = rootView.findViewById(R.id.arschloch_recyclerview);
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
        final schlafmuetze_adapter adapter = new schlafmuetze_adapter(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                for (int k = 0; k < viewModel.getAllSchlafmuetze_Partien().getValue().size(); k++) {
                    if (schlafmuetze_adapter.id.equals(viewModel.getAllSchlafmuetze_Partien().getValue().get(k).getPartiebezeichnung())) {
                        input_punkte(viewModel.getAllSchlafmuetze_Partien().getValue().get(k));
                    }
                }
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int k = 0; k < viewModel.getAllSchlafmuetze_Partien().getValue().size(); k++) {
                    if (schlafmuetze_adapter.id.equals(viewModel.getAllSchlafmuetze_Partien().getValue().get(k).getPartiebezeichnung())) {
                        update_partie(viewModel.getAllSchlafmuetze_Partien().getValue().get(k));
                    }
                }
            }
        });
        mRecyclerview.setAdapter(adapter);
        //ViewModel implementieren und mit Datenbank verlinken
        viewModel = ViewModelProviders.of(getActivity()).get(SchlafmuetzeViewModel.class);
        viewModel.getAllSchlafmuetze_Partien().observe(getActivity(), new Observer<List<Schlafmuetze_Partie>>() {
            @Override
            public void onChanged(@Nullable List<Schlafmuetze_Partie> schlafmuetze_parties) {
                adapter.submitList(schlafmuetze_parties);
            }
        });
        //Swipe implementierung
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder dragged, @NonNull RecyclerView.ViewHolder target) {

                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                if (mPrefs.getBoolean("ASK_DELETE",true)){
                    delete_alert(getActivity(), viewHolder.getAdapterPosition());
                }else{
                    viewModel.delete(viewModel.getAllSchlafmuetze_Partien().getValue().get(viewHolder.getAdapterPosition()));
                }
            }}).attachToRecyclerView(mRecyclerview);
        return rootView;
    }
    //ACHTUNG_ Pfusch am Werk :D
    void new_players(int nummer, List<String> schlafmuetze_spieler) {
        if (nummer < 8) {

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
            final EditText input = new EditText(getActivity());
            input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
            dialogBuilder.setView(input);
            dialogBuilder.setTitle("Spieler " + (nummer + 1));
            dialogBuilder.setMessage("Name des " + (nummer + 1) + ". Spielers eingeben oder frei lassen");
            dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    schlafmuetze_spieler.add(input.getText().toString());
                    j++;
                    new_players(j, schlafmuetze_spieler);
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
        } else new_List(schlafmuetze_spieler);
    }
    //ACHTUNG_ Pfusch am Werk :D
    void new_List(final List<String> schlafmuetze_spieler) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        dialogBuilder.setView(input);
        dialogBuilder.setTitle("Titel");
        dialogBuilder.setMessage("Name der Partie eingeben");
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (!input.getText().toString().equals("")) {
                    List<Schlafmuetze_Spieler> spieler = new ArrayList<>();
                    for (int k = 0; k <  schlafmuetze_spieler.size(); k++) {
                        spieler.add(new Schlafmuetze_Spieler(schlafmuetze_spieler.get(k),0));
                    }
                    viewModel.add(new Schlafmuetze_Partie(input.getText().toString(), spieler));
                    j = 0;
                } else {
                    Toast toast = Toast.makeText(getActivity(), "Bitte einen gültigen Partienamen eingeben", Toast.LENGTH_LONG);
                    toast.show();
                    new_List(schlafmuetze_spieler);
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


    public void input_punkte(Schlafmuetze_Partie schlafmuetze_partie) {
        String[] gespielt = {""};
        MaterialAlertDialogBuilder alertdialog = new MaterialAlertDialogBuilder(getActivity());
        alertdialog.setTitle("Verlierer der Runde auswählen");
        int h=0;
        for (int k = 0; k <schlafmuetze_partie.getSpieler().size() ; k++) {
            if(!schlafmuetze_partie.getSpieler().get(k).getName().equals(""))h++;
        }
        switch (h) {
            case 1: {
                CharSequence[] namen = {schlafmuetze_partie.getSpieler().get(0).getName()};
                alertdialog.setSingleChoiceItems(namen, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                            gespielt[0] = schlafmuetze_partie.getSpieler().get(i).getName();

                    }
                });
                break;
            }
            case 2: {
                CharSequence[] namen = {schlafmuetze_partie.getSpieler().get(0).getName(), schlafmuetze_partie.getSpieler().get(1).getName()};
                alertdialog.setSingleChoiceItems(namen, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        gespielt[0] = schlafmuetze_partie.getSpieler().get(i).getName();
                    }
                });

                break;
            }
            case 3: {
                CharSequence[] namen = {schlafmuetze_partie.getSpieler().get(0).getName(), schlafmuetze_partie.getSpieler().get(1).getName(), schlafmuetze_partie.getSpieler().get(2).getName()};
                alertdialog.setSingleChoiceItems(namen, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        gespielt[0] = schlafmuetze_partie.getSpieler().get(i).getName();
                    }
                });
                break;
            }
            case 4: {
                CharSequence[] namen = {schlafmuetze_partie.getSpieler().get(0).getName(), schlafmuetze_partie.getSpieler().get(1).getName(), schlafmuetze_partie.getSpieler().get(2).getName(), schlafmuetze_partie.getSpieler().get(3).getName()};
                alertdialog.setSingleChoiceItems(namen, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        gespielt[0] = schlafmuetze_partie.getSpieler().get(i).getName();
                    }
                });
                break;
            }

            case 5: {
                CharSequence[] namen = {schlafmuetze_partie.getSpieler().get(0).getName(), schlafmuetze_partie.getSpieler().get(1).getName(), schlafmuetze_partie.getSpieler().get(2).getName(), schlafmuetze_partie.getSpieler().get(3).getName(), schlafmuetze_partie.getSpieler().get(4).getName()};
                alertdialog.setSingleChoiceItems(namen, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        gespielt[0] = schlafmuetze_partie.getSpieler().get(i).getName();
                    }
                });
                break;
            }

            case 6: {
                CharSequence[] namen = {schlafmuetze_partie.getSpieler().get(0).getName(), schlafmuetze_partie.getSpieler().get(1).getName(), schlafmuetze_partie.getSpieler().get(2).getName(), schlafmuetze_partie.getSpieler().get(3).getName(), schlafmuetze_partie.getSpieler().get(4).getName(), schlafmuetze_partie.getSpieler().get(5).getName()};
                alertdialog.setSingleChoiceItems(namen, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        gespielt[0] = schlafmuetze_partie.getSpieler().get(i).getName();
                    }
                });
                break;
            }

            case 7: {
                CharSequence[] namen = {schlafmuetze_partie.getSpieler().get(0).getName(),schlafmuetze_partie.getSpieler().get(1).getName(), schlafmuetze_partie.getSpieler().get(2).getName(), schlafmuetze_partie.getSpieler().get(3).getName(), schlafmuetze_partie.getSpieler().get(4).getName(), schlafmuetze_partie.getSpieler().get(5).getName(), schlafmuetze_partie.getSpieler().get(6).getName()};
                alertdialog.setSingleChoiceItems(namen, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        gespielt[0] = schlafmuetze_partie.getSpieler().get(i).getName();
                    }
                });
                break;
            }

            case 8: {
                CharSequence[] namen = {schlafmuetze_partie.getSpieler().get(0).getName(), schlafmuetze_partie.getSpieler().get(1).getName(), schlafmuetze_partie.getSpieler().get(2).getName(), schlafmuetze_partie.getSpieler().get(3).getName(), schlafmuetze_partie.getSpieler().get(4).getName(), schlafmuetze_partie.getSpieler().get(5).getName(), schlafmuetze_partie.getSpieler().get(6).getName(), schlafmuetze_partie.getSpieler().get(7).getName()};
                alertdialog.setSingleChoiceItems(namen, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        gespielt[0] = schlafmuetze_partie.getSpieler().get(i).getName();
                    }
                });
                break;
            }
        }
        alertdialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                List<Schlafmuetze_Spieler> schlafmuetze_spieler = new ArrayList<>();

                for (int k = 0; k < schlafmuetze_partie.getSpieler().size(); k++) {
                   if(schlafmuetze_partie.getSpieler().get(k).getName().equals(gespielt[0])){
                       schlafmuetze_spieler.add(new Schlafmuetze_Spieler(schlafmuetze_partie.getSpieler().get(k).getName(),schlafmuetze_partie.getSpieler().get(k).getVerloren()+1));
                   } else schlafmuetze_spieler.add(new Schlafmuetze_Spieler(schlafmuetze_partie.getSpieler().get(k).getName(),schlafmuetze_partie.getSpieler().get(k).getVerloren()));
                }
                viewModel.update(new Schlafmuetze_Partie(schlafmuetze_partie.getPartiebezeichnung(), schlafmuetze_spieler));
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
                viewModel.delete(viewModel.getAllSchlafmuetze_Partien().getValue().get(position));
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

    public void update_partie(Schlafmuetze_Partie partie){
        CharSequence[] optionen = {"Namen der Partie", "Namen der Spieler", "Platzierung der Spieler"};
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

    public void update_titel(Schlafmuetze_Partie partie){
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        builder.setTitle("Name der Partie bearbeiten");
        builder.setMessage("Bitte geänderten Namen eingeben");
        EditText edit = new EditText(getActivity());
        edit.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        builder.setView(edit);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                viewModel.add(new Schlafmuetze_Partie(edit.getText().toString(),partie.getSpieler()));
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

    public void update_name(Schlafmuetze_Partie partie){
        int s=0;
        final int[] selected = {0 };
        for (int i = 0; i < partie.getSpieler().size() ; i++) {
            if(!partie.getSpieler().get(i).getName().equals("")){s++;}
        }
        String[] options = new String[s];
        int a=0;
        for (int i = 0; i < partie.getSpieler().size() ; i++) {
            if(!partie.getSpieler().get(i).getName().equals("")){
                options[a]=partie.getSpieler().get(i).getName();
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
                        List<Schlafmuetze_Spieler> spieler = new ArrayList<>();
                        for (int j = 0; j < partie.getSpieler().size() ; j++) {
                            if(j==selected[0]){
                                spieler.add(new Schlafmuetze_Spieler(edit.getText().toString(), partie.getSpieler().get(j).getVerloren()));
                            } else  spieler.add(new Schlafmuetze_Spieler(partie.getSpieler().get(j).getName(), partie.getSpieler().get(j).getVerloren()));
                        }
                        viewModel.update(new Schlafmuetze_Partie(partie.getPartiebezeichnung(),spieler));
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

    public void update_punkte(Schlafmuetze_Partie partie){
        int s=0;
        final int[] selected = { 0 };
        for (int i = 0; i < partie.getSpieler().size() ; i++) {
            if(!partie.getSpieler().get(i).getName().equals("")){s++;}
        }
        String[] options = new String[s];
        int a=0;
        for (int i = 0; i < partie.getSpieler().size() ; i++) {
            if(!partie.getSpieler().get(i).getName().equals("")){
                options[a]=partie.getSpieler().get(i).getName();
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
                        MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                        alertDialogBuilder.setTitle("Platzierung ändern");
                        alertDialogBuilder.setMessage("Neue Platzierung eingeben");
                        EditText edit = new EditText(getActivity());
                        edit.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        alertDialogBuilder.setView(edit);
                        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                List<Schlafmuetze_Spieler> spieler = new ArrayList<>();
                                for (int j = 0; j <partie.getSpieler().size() ; j++) {
                                    if(j==selected[0]){
                                        spieler.add(new Schlafmuetze_Spieler(partie.getSpieler().get(j).getName(),Integer.valueOf(edit.getText().toString())));
                                    } else spieler.add(partie.getSpieler().get(j));
                                }
                                viewModel.update(new Schlafmuetze_Partie(partie.getPartiebezeichnung(),spieler));
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

