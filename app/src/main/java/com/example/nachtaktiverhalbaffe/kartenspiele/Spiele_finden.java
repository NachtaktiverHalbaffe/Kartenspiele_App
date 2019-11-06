package com.example.nachtaktiverhalbaffe.kartenspiele;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

import Database.Durak.DurakViewModel;
import Database.Durak.Durak_Partie;
import Database.Kartenspiele.Kartenspiel;
import Database.Kartenspiele.KartenspieleViewModel;
import recyclerview.kartenspiele_adapter;

/**
 * Created by NachtaktiverHalbaffe on 28.11.2017.
 */

public class Spiele_finden extends Fragment implements Filterable {
    KartenspieleViewModel viewModel;
    kartenspiele_adapter adapter;
    RecyclerView mRecyclerview;
    List<Kartenspiel> filtered , list= null;
    public String anzahl="",zeit="",komplex="",deck="";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {   //Fragment Preambel
        View rootView = inflater.inflate(R.layout.kartenspiele, container, false);

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //AlertDialog nach Installation, wo App erklärt wird
        if (mPrefs.getBoolean("FIRST_INSTALL",true)) {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
            builder.setTitle("Einführung");
            builder.setMessage(getString(R.string.home_text));
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    mPrefs.edit().putBoolean("FIRST_INSTALL", false).apply();
                }
            });
            AlertDialog alert_final2 = builder.create();
            alert_final2.setCancelable(false);
            alert_final2.show();
            TextView pos2 = alert_final2.getButton(DialogInterface.BUTTON_POSITIVE);
            if (pos2 != null) pos2.setTextColor(getResources().getColor(R.color.textcolorDark));
            TextView neg2 = alert_final2.getButton(DialogInterface.BUTTON_NEGATIVE);
            if (neg2 != null) neg2.setTextColor(getResources().getColor(R.color.textcolorDark));
        }
        //Implementierung Spinner
        Spinner spieleranzal = rootView.findViewById(R.id.spieleranzahl);
        ArrayAdapter<CharSequence> adapter_number_players = ArrayAdapter.createFromResource(getActivity(),R.array.Spieleranzahl , android.R.layout.simple_spinner_item);
        adapter_number_players.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spieleranzal.setAdapter(adapter_number_players);
        spieleranzal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                anzahl = adapterView.getItemAtPosition(i).toString();
                new updateTask().execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spieleranzal.setSelection(2);

        Spinner spielzeit = rootView.findViewById(R.id.spielzeit);
        ArrayAdapter<CharSequence> adapter_spielzeit = ArrayAdapter.createFromResource(getActivity(),R.array.Spielzeit, android.R.layout.simple_spinner_item);
        adapter_spielzeit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spielzeit.setAdapter(adapter_spielzeit);
        spielzeit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                zeit= adapterView.getItemAtPosition(i).toString();
                new updateTask().execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spielzeit.setSelection(3);

        Spinner komplexitaet = rootView.findViewById(R.id.komplexitaet);
        ArrayAdapter<CharSequence> adapter_komplexitaet = ArrayAdapter.createFromResource(getActivity(),R.array.Komplexitaet, android.R.layout.simple_spinner_item);
        adapter_komplexitaet.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        komplexitaet.setAdapter(adapter_komplexitaet);
        komplexitaet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                komplex= adapterView.getItemAtPosition(i).toString();
                new updateTask().execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        komplexitaet.setSelection(2);

        Spinner kartendeck = rootView.findViewById(R.id.kartendeck);
        ArrayAdapter<CharSequence> adapter_kartendeck = ArrayAdapter.createFromResource(getActivity(),R.array.Kartendeck, android.R.layout.simple_spinner_item);
        adapter_kartendeck.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kartendeck.setAdapter(adapter_kartendeck);
        kartendeck.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            deck = adapterView.getItemAtPosition(i).toString();
                new updateTask().execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //Implementierung Recyclerview
        mRecyclerview= rootView.findViewById(R.id.spiele_recyclerview);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter=new kartenspiele_adapter(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Fragment starten, wenn auf Spiel geklickt
                if(kartenspiele_adapter.id.equals("Golfen")) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.Fragment,new Golfen()).commit();
                } else if(kartenspiele_adapter.id.equals("Arschloch")) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.Fragment,new Arschloch()).commit();
                } else if(kartenspiele_adapter.id.equals("Shithead")) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.Fragment,new Shithead()).commit();
                } else if(kartenspiele_adapter.id.equals("Schlafmütze")) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.Fragment,new Schlafmuetze()).commit();
                } else if(kartenspiele_adapter.id.equals("Schwimmen")) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.Fragment,new Schwimmen()).commit();
                } else if(kartenspiele_adapter.id.equals("Skat")) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.Fragment,new Skat()).commit();
                } else if(kartenspiele_adapter.id.equals("Durak")) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.Fragment,new Durak()).commit();
                }
            }
        });
        mRecyclerview.setAdapter(adapter);
        //Viewmodel implementieren un observen
        viewModel = ViewModelProviders.of(getActivity()).get(KartenspieleViewModel.class);
        viewModel.getAllKartenspiele().observe(getActivity(), new Observer<List<Kartenspiel>>() {
            @Override
            public void onChanged(@Nullable List<Kartenspiel> kartenspiel) {
                adapter.submitList(kartenspiel);
            }
        });
        //Datenbank bestücken
        viewModel.add(new Kartenspiel("Golfen", 2,8,"Halbe Stunde","Alle","Einfach", R.drawable.ic_golf_course_black_24dp));
        viewModel.add(new Kartenspiel("Arschloch", 3,8,"Für Zwischendurch","Alle","Einfach", R.drawable.ic_sentiment_very_dissatisfied_black_24dp));
        viewModel.add(new Kartenspiel("Shithead", 2,5,"Für Zwischendurch","Poker","Mittel", R.drawable.ic_face_black_24dp));
        viewModel.add(new Kartenspiel("Schlafmütze", 2,8,"Für Zwischendurch","Alle","Einfach", R.drawable.ic_hotel_black_24dp));
        viewModel.add(new Kartenspiel("Schwimmen", 2,8,"Halbe Stunde","Skat","Einfach", R.drawable.ic_pool_black_24dp));
        viewModel.add(new Kartenspiel("Skat", 3,4,"Einen Abend","Skat","Anspruchsvoll", R.drawable.ic_king_of_spades_50));
        viewModel.add(new Kartenspiel("Durak", 3,8,"Für Zwischendurch","Alle","Mittel", R.drawable.sword_cross));

        SearchView search = rootView.findViewById(R.id.searchbar);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                list=viewModel.getAllKartenspiele().getValue();
                getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //TODO Suche live machen ohne abstürze
                //list=viewModel.getAllKartenspiele().getValue();
                //getFilter().filter(s);
                return false;
            }
        });
        return rootView;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence eingabe) {
            List<Kartenspiel> temp=new ArrayList<>();
            if(eingabe == null || eingabe.length()==0){
                temp.addAll(list);
            } else{

                for(Kartenspiel kartenspiel :list){
                    if(kartenspiel.getName().toLowerCase().contains(eingabe.toString().toLowerCase().trim())){
                        temp.add(kartenspiel);
                    }
                }
            }
            FilterResults ergebnis = new FilterResults();
            ergebnis.values = temp;
            return ergebnis;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            filtered.clear();
            adapter.submitList((List<Kartenspiel>)filterResults.values);
        }
    };

    public class updateTask extends AsyncTask<Void, Void, Void>{

        public updateTask(){}
    @Override
    protected Void doInBackground(Void... voids) {
        filtered = new ArrayList<>();
        if(!anzahl.equals("")){
            int zahl =3;
            if(anzahl.equals("Einer")){
                zahl=1;
            } else if(anzahl.equals("Zwei")){
                zahl=2;
            } else if(anzahl.equals("Drei")){
                zahl=3;
            } else if(anzahl.equals("Vier")){
                zahl=4;
            } else if(anzahl.equals("Fünf")){
                zahl=5;
            } else if(anzahl.equals("Sechs")){
                zahl=6;
            } else if(anzahl.equals("Sieben")){
                zahl=7;
            } else if(anzahl.equals("Acht")){
                zahl=8;
            }
            if(filtered==null || filtered.size()==0){
                for (int j = 0; j < viewModel.getAllKartenspiele().getValue().size(); j++) {
                    if(zahl>= viewModel.getAllKartenspiele().getValue().get(j).getSpieleranzahl_min() && zahl<viewModel.getAllKartenspiele().getValue().get(j).getSpieleranzahl_max()){
                        filtered.add(viewModel.getAllKartenspiele().getValue().get(j));
                    }
                }}
            else {
                List<Kartenspiel> temp=new ArrayList<>();
                for (int j = 0; j < filtered.size(); j++) {
                    if(zahl>=filtered.get(j).getSpieleranzahl_min() && zahl<filtered.get(j).getSpieleranzahl_max()){
                        temp.add(filtered.get(j));
                    }
                }
                filtered.clear();
                filtered.addAll(temp);
            }

        }
        if(!zeit.equals("")){
            //TODO Halbe Stunde wird bei zwischendurch angezeigt => Fixen!
            if(filtered==null || filtered.size()==0){
                for (int i = 0; i <viewModel.getAllKartenspiele().getValue().size() ; i++) {
                    if(zeit.equals("Für Zwischendurch") && viewModel.getAllKartenspiele().getValue().get(i).getSpielzeit().equals("Für Zwischendurch")){
                        filtered.add(viewModel.getAllKartenspiele().getValue().get(i));
                    }
                    else if(zeit.equals("Halbe Stunde") &&(viewModel.getAllKartenspiele().getValue().get(i).getSpielzeit().equals("Für Zwischendurch")  || viewModel.getAllKartenspiele().getValue().get(i).getSpielzeit().equals("Halbe Stunde"))){
                        filtered.add(viewModel.getAllKartenspiele().getValue().get(i));
                    }
                    else if(zeit.equals("Eine Stunde") && (viewModel.getAllKartenspiele().getValue().get(i).getSpielzeit().equals("Für Zwischendurch")  || viewModel.getAllKartenspiele().getValue().get(i).getSpielzeit().equals("Halbe Stunde") || viewModel.getAllKartenspiele().getValue().get(i).getSpielzeit().equals("Eine Stunde"))){
                        filtered.add(viewModel.getAllKartenspiele().getValue().get(i));
                    }
                    else if(zeit.equals("Einen Abend") && (viewModel.getAllKartenspiele().getValue().get(i).getSpielzeit().equals("Für Zwischendurch")  || viewModel.getAllKartenspiele().getValue().get(i).getSpielzeit().equals("Halbe Stunde") || viewModel.getAllKartenspiele().getValue().get(i).getSpielzeit().equals("Eine Stunde") || viewModel.getAllKartenspiele().getValue().get(i).getSpielzeit().equals("Einen Abend"))){
                        filtered.add(viewModel.getAllKartenspiele().getValue().get(i));
                    }
                }
            }else{
                List<Kartenspiel> temp=new ArrayList<>();
                for (int i = 0; i <filtered.size() ; i++) {
                    if(zeit.equals("Für Zwischendurch") && filtered.get(i).getSpielzeit().equals("Für Zwischendurch")){
                        temp.add(filtered.get(i));
                    }
                    else if(zeit.equals("Halbe Stunde") && (filtered.get(i).getSpielzeit().equals("Für Zwischendurch")|| filtered.get(i).getSpielzeit().equals("Halbe Stunde"))){
                        temp.add(filtered.get(i));
                    }
                    else if(zeit.equals("Eine Stunde") && (filtered.get(i).getSpielzeit().equals("Für Zwischendurch")|| filtered.get(i).getSpielzeit().equals("Halbe Stunde") || filtered.get(i).getSpielzeit().equals("Eine Stunde"))){
                        temp.add(filtered.get(i));
                    }
                    else if(zeit.equals("Einen Abend") && (filtered.get(i).getSpielzeit().equals("Für Zwischendurch")|| filtered.get(i).getSpielzeit().equals("Halbe Stunde") || filtered.get(i).getSpielzeit().equals("Eine Stunde") || filtered.get(i).getSpielzeit().equals("Einen Abend"))){
                        temp.add(filtered.get(i));
                    }
                }
                filtered.clear();
                filtered.addAll(temp);
            }
        }
        if(!komplex.equals("")){
            if(filtered== null || filtered.size()==0){
                for (int i = 0; i <viewModel.getAllKartenspiele().getValue().size() ; i++) {
                    if(komplex.equals("Einfach") && viewModel.getAllKartenspiele().getValue().get(i).getKomplexitaet().equals("Einfach")){
                        filtered.add(viewModel.getAllKartenspiele().getValue().get(i));
                    }else if(komplex.equals("Mittel") && (viewModel.getAllKartenspiele().getValue().get(i).getKomplexitaet().equals("Einfach") ||viewModel.getAllKartenspiele().getValue().get(i).getKomplexitaet().equals("Mittel") )){
                        filtered.add(viewModel.getAllKartenspiele().getValue().get(i));
                    }else if(komplex.equals("Anspruchsvoll") && (viewModel.getAllKartenspiele().getValue().get(i).getKomplexitaet().equals("Einfach") ||viewModel.getAllKartenspiele().getValue().get(i).getKomplexitaet().equals("Mittel") || viewModel.getAllKartenspiele().getValue().get(i).getKomplexitaet().equals("Anspruchsvoll"))){
                        filtered.add(viewModel.getAllKartenspiele().getValue().get(i));
                    }
                }
            } else{
                List<Kartenspiel> temp=new ArrayList<>();
                for (int i = 0; i <filtered.size() ; i++) {
                    if(komplex.equals("Einfach") && filtered.get(i).getKomplexitaet().equals("Einfach")){
                        temp.add(viewModel.getAllKartenspiele().getValue().get(i));
                    }else if(komplex.equals("Mittel") && (filtered.get(i).getKomplexitaet().equals("Einfach") ||filtered.get(i).getKomplexitaet().equals("Mittel") )){
                        temp.add(viewModel.getAllKartenspiele().getValue().get(i));
                    }else if(komplex.equals("Anspruchsvoll") && (filtered.get(i).getKomplexitaet().equals("Einfach") ||filtered.get(i).getKomplexitaet().equals("Mittel") || filtered.get(i).getKomplexitaet().equals("Anspruchsvoll"))){
                        temp.add(viewModel.getAllKartenspiele().getValue().get(i));
                    }
                }
                filtered.clear();
                filtered.addAll(temp);
            }
        }
        if(!deck.equals("")){
            if(filtered== null || filtered.size()==0){
                for (int i = 0; i <viewModel.getAllKartenspiele().getValue().size() ; i++) {
                    if(deck.equals("Alle")){
                        filtered.add(viewModel.getAllKartenspiele().getValue().get(i));
                    } else if(deck.equals("Skatdeck") && viewModel.getAllKartenspiele().getValue().get(i).getKartendeck().equals("Skat")){
                        filtered.add(viewModel.getAllKartenspiele().getValue().get(i));
                    } else if(deck.equals("Pokerdeck") && (viewModel.getAllKartenspiele().getValue().get(i).getKartendeck().equals("Poker")|| viewModel.getAllKartenspiele().getValue().get(i).getKartendeck().equals("Alle"))){
                        filtered.add(viewModel.getAllKartenspiele().getValue().get(i));
                    }
                }
            }else {
                List<Kartenspiel> temp=new ArrayList<>();
                for (int i = 0; i <filtered.size() ; i++) {
                    if(deck.equals("Alle")){
                        temp.add(filtered.get(i));
                    } else if(deck.equals("Skatdeck") && filtered.get(i).getKartendeck().equals("Skat")){
                        temp.add(filtered.get(i));
                    } else if(deck.equals("Pokerdeck") && (filtered.get(i).getKartendeck().equals("Poker")|| filtered.get(i).getKartendeck().equals("Alle") || filtered.get(i).getKartendeck().equals("Skat"))){
                        temp.add(filtered.get(i));
                    }
                }
                filtered.clear();
                filtered.addAll(temp);
            }
        }
        adapter.submitList(filtered);
        return null;
    }
}
}
