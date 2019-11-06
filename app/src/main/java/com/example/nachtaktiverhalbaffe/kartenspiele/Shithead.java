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
import Database.Shithead.ShitheadViewModel;
import Database.Shithead.Shithead_Partie;
import Database.Shithead.Shithead_Spieler;
import recyclerview.shithead_adapter;

public class Shithead extends Fragment {
    private ShitheadViewModel viewModel;
    public Integer j = 0;
    List<Shithead_Spieler> shithead_spieler;
    public Shithead_Partie shithead_partien = null;
    static RecyclerView mRecyclerview;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {   //Fragment Preambel
        View rootView = inflater.inflate(R.layout.arschloch, container, false);
        getActivity().setTitle("Shithead");
        FloatingActionButton anl = rootView.findViewById(R.id.arschloch_anleitung);
        anl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String anleitung = "Ablauf:\n"+getString(R.string.Shithead_Anleitung) + "\n\nWertung:\n" + getString(R.string.Arschloch_Wertung);
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
        final shithead_adapter adapter = new shithead_adapter(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                for (int k = 0; k < viewModel.getAllShithead_Partien().getValue().size(); k++) {
                    if (shithead_adapter.id.equals(viewModel.getAllShithead_Partien().getValue().get(k).getPartiebezeichnung())) {
                        shithead_spieler = new ArrayList<>();
                        int s = 0;
                        for (int i = 0; i < viewModel.getAllShithead_Partien().getValue().get(k).getSpieler().size(); i++) {
                            if (!viewModel.getAllShithead_Partien().getValue().get(k).getSpieler().get(i).getName().equals("")) {
                                s++;
                            }
                        }
                        String[] namen = new String[s];
                        s = 0;
                        for (int i = 0; i < viewModel.getAllShithead_Partien().getValue().get(k).getSpieler().size(); i++) {
                            if (!viewModel.getAllShithead_Partien().getValue().get(k).getSpieler().get(i).getName().equals("")) {
                                namen[s] = viewModel.getAllShithead_Partien().getValue().get(k).getSpieler().get(i).getName();
                                s++;
                            }
                        }
                        input_punkte(viewModel.getAllShithead_Partien().getValue().get(k), 0, namen.length, namen);
                    }
                }
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int k = 0; k < viewModel.getAllShithead_Partien().getValue().size(); k++) {
                    if (shithead_adapter.id.equals(viewModel.getAllShithead_Partien().getValue().get(k).getPartiebezeichnung())) {
                        shithead_spieler = new ArrayList<>();
                        int s = 0;
                        for (int i = 0; i < viewModel.getAllShithead_Partien().getValue().get(k).getSpieler().size(); i++) {
                            if (!viewModel.getAllShithead_Partien().getValue().get(k).getSpieler().get(i).getName().equals("")) {
                                s++;
                            }
                        }
                        String[] namen = new String[s];
                        s = 0;
                        for (int i = 0; i < viewModel.getAllShithead_Partien().getValue().get(k).getSpieler().size(); i++) {
                            if (!viewModel.getAllShithead_Partien().getValue().get(k).getSpieler().get(i).getName().equals("")) {
                                namen[s] = viewModel.getAllShithead_Partien().getValue().get(k).getSpieler().get(i).getName();
                                s++;
                            }
                        }
                        update_partie(viewModel.getAllShithead_Partien().getValue().get(k));
                    }
                }
            }
        });
        mRecyclerview.setAdapter(adapter);
        //ViewModel implementieren und mit Datenbank verlinken
        viewModel = ViewModelProviders.of(getActivity()).get(ShitheadViewModel.class);
        viewModel.getAllShithead_Partien().observe(getActivity(), new Observer<List<Shithead_Partie>>() {
            @Override
            public void onChanged(@Nullable List<Shithead_Partie> shithead_parties) {
                adapter.submitList(shithead_parties);
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
                    viewModel.delete(viewModel.getAllShithead_Partien().getValue().get(viewHolder.getAdapterPosition()));
                }
            }}).attachToRecyclerView(mRecyclerview);
        return rootView;
    }
    //ACHTUNG_ Pfusch am Werk :D
    void new_players(int nummer, List<String> shithead_spieler) {
        if (nummer < 8) {

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
            final EditText input = new EditText(getActivity());
            input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
            dialogBuilder.setView(input);
            dialogBuilder.setTitle("Spieler " + (nummer + 1));
            dialogBuilder.setMessage("Name des " + (nummer + 1) + ". Spielers eingeben oder frei lassen");
            dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    shithead_spieler.add(input.getText().toString());
                    j++;
                    new_players(j, shithead_spieler);
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
        } else new_List(shithead_spieler);
    }
    //ACHTUNG_ Pfusch am Werk :D
    void new_List(final List<String> durak_spieler) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        dialogBuilder.setView(input);
        dialogBuilder.setTitle("Titel");
        dialogBuilder.setMessage("Name der Partie eingeben");
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (!input.getText().toString().equals("")) {
                    List<Shithead_Spieler> spieler = new ArrayList<>();
                    List<Integer> platzierungen = new ArrayList<>();
                    for (int k = 0; k < durak_spieler.size(); k++) {
                        if(!durak_spieler.get(k).equals("")){
                            platzierungen.add(0);
                        }
                    }
                    for (int k = 0; k <  durak_spieler.size(); k++) {
                        spieler.add(new Shithead_Spieler(durak_spieler.get(k),platzierungen));
                    }
                    viewModel.add(new Shithead_Partie(input.getText().toString(), spieler));
                    j = 0;
                } else {
                    Toast toast = Toast.makeText(getActivity(), "Bitte einen gültigen Partienamen eingeben", Toast.LENGTH_LONG);
                    toast.show();
                    new_List(durak_spieler);
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


    public void input_punkte(Shithead_Partie shithead_partie, int iteration, int size, String[] namen) {
        if(iteration<size){
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
            builder.setTitle("Bitte Rangfolge eingeben");
            builder.setMessage("Bitte den Spieler auswählen, der den "+ (iteration+1) +". Platz erzielt hat." );
            NumberPicker nb = new NumberPicker(getActivity());
            nb.setMinValue(0);
            nb.setMaxValue(namen.length-1);
            nb.setDisplayedValues(namen);
            builder.setView(nb);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    List<Shithead_Spieler> spieler =new ArrayList<>();
                    for (int j = 0; j < shithead_partie.getSpieler().size(); j++){
                        List<Integer> platzierungen = new ArrayList<>();
                        if(shithead_partie.getSpieler().get(j).getPlatzierungen()!=null){
                            for (int k = 0; k < shithead_partie.getSpieler().get(j).getPlatzierungen().size(); k++) {
                                if (iteration ==  k && namen[nb.getValue()].equals(shithead_partie.getSpieler().get(j).getName())){
                                    platzierungen.add(shithead_partie.getSpieler().get(j).getPlatzierungen().get(k) + 1);
                                } else
                                    platzierungen.add(shithead_partie.getSpieler().get(j).getPlatzierungen().get(k));
                            }
                            spieler.add(new Shithead_Spieler(shithead_partie.getSpieler().get(j).getName(), platzierungen));
                        }else spieler.add(new Shithead_Spieler("",null));}
                    String[] update_namen = new String[namen.length-1];
                    int r =0;
                    for (int k = 0; k < namen.length ; k++) {
                        if(nb.getValue()!=k){
                            update_namen[k-r] = namen[k];}
                        else if(nb.getValue()==k){r++;}
                    }

                    input_punkte(new Shithead_Partie(shithead_partie.getPartiebezeichnung(),spieler),iteration+1,size, update_namen);
                }
            });

            builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog alert_final = builder.create();
            alert_final.setCancelable(false);
            alert_final.show();
            TextView pos = alert_final.getButton(DialogInterface.BUTTON_POSITIVE);
            if(pos!=null)pos.setTextColor(getResources().getColor(R.color.textcolorDark));
            TextView neg =alert_final.getButton(DialogInterface.BUTTON_NEGATIVE);
            if(neg!=null) neg.setTextColor(getResources().getColor(R.color.textcolorDark));}
        else  viewModel.update(shithead_partie);
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
                viewModel.delete(viewModel.getAllShithead_Partien().getValue().get(position));
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

    public void update_partie(Shithead_Partie partie){
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

    public void update_titel(Shithead_Partie partie){
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        builder.setTitle("Name der Partie bearbeiten");
        builder.setMessage("Bitte geänderten Namen eingeben");
        EditText edit = new EditText(getActivity());
        edit.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        builder.setView(edit);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                viewModel.add(new Shithead_Partie(edit.getText().toString(),partie.getSpieler()));
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

    public void update_name(Shithead_Partie partie){
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
                        List<Shithead_Spieler> spieler = new ArrayList<>();
                        for (int j = 0; j < partie.getSpieler().size() ; j++) {
                            if(j==selected[0]){
                                spieler.add(new Shithead_Spieler(edit.getText().toString(), partie.getSpieler().get(j).getPlatzierungen()));
                            } else  spieler.add(new Shithead_Spieler(partie.getSpieler().get(j).getName(), partie.getSpieler().get(j).getPlatzierungen()));
                        }
                        viewModel.update(new Shithead_Partie(partie.getPartiebezeichnung(),spieler));
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

    public void update_punkte(Shithead_Partie partie){
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
                final int[] choise = {0};
                MaterialAlertDialogBuilder alert = new MaterialAlertDialogBuilder(getActivity());
                alert.setTitle("Auswählen, welche Platzierung bearbeitet werden soll");
                CharSequence[] punkte = new CharSequence[partie.getSpieler().get(selected[0]).getPlatzierungen().size()];
                for (int k = 0; k < partie.getSpieler().get(selected[0]).getPlatzierungen().size() ; k++) {
                    punkte[k]= k+1 +". Platzierung: " + partie.getSpieler().get(selected[0]).getPlatzierungen().get(k);
                }
                alert.setSingleChoiceItems(punkte, -1, (dialogInterface1, i1) -> choise[0] = i1);
                alert.setPositiveButton("Ok", (dialogInterface12, i12) -> {
                    MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                    alertDialogBuilder.setTitle("Platzierung ändern");
                    alertDialogBuilder.setMessage("Neue Platzierung eingeben");
                    EditText edit = new EditText(getActivity());
                    edit.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    alertDialogBuilder.setView(edit);
                    alertDialogBuilder.setPositiveButton("Ok", (dialogInterface121, i121) -> {
                        List<Integer> punkte1 = new ArrayList<>();
                        for (int j = 0; j < partie.getSpieler().get(selected[0]).getPlatzierungen().size() ; j++) {
                            if(j==choise[0]){
                                punkte1.add(Integer.valueOf(edit.getText().toString()));
                            }else punkte1.add(partie.getSpieler().get(selected[0]).getPlatzierungen().get(j));
                        }
                        List<Shithead_Spieler> spieler = new ArrayList<>();
                        for (int j = 0; j <partie.getSpieler().size() ; j++) {
                            if(j==selected[0]){
                                spieler.add(new Shithead_Spieler(partie.getSpieler().get(j).getName(), punkte1));
                            } else spieler.add(partie.getSpieler().get(j));
                        }
                        viewModel.update(new Shithead_Partie(partie.getPartiebezeichnung(),spieler));
                    });
                    alertDialogBuilder.setNegativeButton("Abbrechen", (dialogInterface1212, i1212) -> {

                    });
                    AlertDialog alert1 = alertDialogBuilder.create();
                    alert1.setCancelable(false);
                    alert1.show();
                    TextView pos = alert1.getButton(DialogInterface.BUTTON_POSITIVE);
                    if(pos!=null)pos.setTextColor(getResources().getColor(R.color.textcolorDark));
                    TextView neg = alert1.getButton(DialogInterface.BUTTON_NEGATIVE);
                    if(neg!=null) neg.setTextColor(getResources().getColor(R.color.textcolorDark));
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
        builder.setNegativeButton("Abbrechen", (dialogInterface, i) -> {

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
