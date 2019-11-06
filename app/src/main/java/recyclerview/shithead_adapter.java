package recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nachtaktiverhalbaffe.kartenspiele.R;

import java.util.List;
import java.util.Objects;

import Database.Shithead.Shithead_Partie;
import Database.Shithead.Shithead_Spieler;

public class shithead_adapter extends ListAdapter<Shithead_Partie, shithead_adapter.Shithead_ListViewHolder> {
    public String mId;
    public static String id;
    View.OnClickListener itemClickListener = null, longClickListener = null;

    public shithead_adapter(View.OnClickListener itemClickListener, View.OnClickListener longClickListener) {
        super(DIFF_CALLBACK);
        this.itemClickListener = itemClickListener;
        this.longClickListener = longClickListener;
    }

    private static final DiffUtil.ItemCallback<Shithead_Partie> DIFF_CALLBACK = new DiffUtil.ItemCallback<Shithead_Partie>() {
        @Override
        public boolean areItemsTheSame(@NonNull Shithead_Partie oldItem, @NonNull Shithead_Partie newItem) {
            return oldItem.getPartiebezeichnung().equals(newItem.getPartiebezeichnung());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Shithead_Partie oldItem, @NonNull Shithead_Partie newItem) {
            return oldItem.getPartiebezeichnung().equals(newItem.getPartiebezeichnung()) && Objects.equals(oldItem.getSpieler(), newItem.getSpieler());
        }};

    class Shithead_ListViewHolder extends RecyclerView.ViewHolder {
        TextView titel, spieler_1, spieler_2, spieler_3, spieler_4, spieler_5, spieler_6, spieler_7, spieler_8,
                kingt, king1, king2, king3, king4, king5, king6, king7, king8,
                vkingt, vking1, vking2, vking3, vking4, vking5, vking6, vking7, vking8,
                bauer1t,bauer11,bauer12, bauer13, bauer14, bauer15, bauer16, bauer17, bauer18,
                bauer2t,bauer21,bauer22,bauer23,bauer24,bauer25,bauer26,bauer27,bauer28,
                bauer3t, bauer31,bauer32,bauer33,bauer34,bauer35,bauer36,bauer37,bauer38,
                bauer4t,bauer41,bauer42,bauer43,bauer44,bauer45,bauer46,bauer47,bauer48,
                varscht,varsch1,varsch2,varsch3,varsch4,varsch5,varsch6,varsch7,varsch8,
                arscht,arsch1,arsch2,arsch3,arsch4,arsch5,arsch6,arsch7,arsch8;
        CardView cv;
        TableRow king,vking,bauer1,bauer2,bauer3,bauer4,varsch,arsch;

        public Shithead_ListViewHolder(View itemView, View.OnClickListener itemClickListener) {                                                     //Variablen fÃ¼r cards setzen
            super(itemView);

            titel = itemView.findViewById((R.id.arschloch_titel));
            spieler_1 = itemView.findViewById((R.id.arschloch_spieler1));
            spieler_2 = itemView.findViewById((R.id.arschloch_spieler2));
            spieler_3 = itemView.findViewById((R.id.arschloch_spieler3));
            spieler_4 = itemView.findViewById((R.id.arschloch_spieler4));
            spieler_5 = itemView.findViewById((R.id.arschloch_spieler5));
            spieler_6 = itemView.findViewById((R.id.arschloch_spieler6));
            spieler_7 = itemView.findViewById((R.id.arschloch_spieler7));
            spieler_8 = itemView.findViewById((R.id.arschloch_spieler8));
            kingt = itemView.findViewById(R.id.king_text);
            king1 = itemView.findViewById(R.id.king1);
            king2 = itemView.findViewById(R.id.king2);
            king3 = itemView.findViewById(R.id.king3);
            king4 = itemView.findViewById(R.id.king4);
            king5 = itemView.findViewById(R.id.king5);
            king6 = itemView.findViewById(R.id.king6);
            king7 = itemView.findViewById(R.id.king7);
            king8 = itemView.findViewById(R.id.king8);
            vkingt = itemView.findViewById(R.id.vizeking_text);
            vking1 = itemView.findViewById(R.id.vking1);
            vking2 = itemView.findViewById(R.id.vking2);
            vking3 = itemView.findViewById(R.id.vking3);
            vking4 = itemView.findViewById(R.id.vking4);
            vking5 = itemView.findViewById(R.id.vking5);
            vking6 = itemView.findViewById(R.id.vking6);
            vking7 = itemView.findViewById(R.id.vking7);
            vking8 = itemView.findViewById(R.id.vking8);
            bauer1t = itemView.findViewById(R.id.bauer1_text);
            bauer11 = itemView.findViewById(R.id.bauer11);
            bauer12 = itemView.findViewById(R.id.bauer12);
            bauer13 = itemView.findViewById(R.id.bauer13);
            bauer14 = itemView.findViewById(R.id.bauer14);
            bauer15 = itemView.findViewById(R.id.bauer15);
            bauer16 = itemView.findViewById(R.id.bauer16);
            bauer17 = itemView.findViewById(R.id.bauer17);
            bauer18 = itemView.findViewById(R.id.bauer18);
            bauer2t = itemView.findViewById(R.id.bauer2_text);
            bauer21 = itemView.findViewById(R.id.bauer21);
            bauer22 = itemView.findViewById(R.id.bauer22);
            bauer23 = itemView.findViewById(R.id.bauer23);
            bauer24 = itemView.findViewById(R.id.bauer24);
            bauer25 = itemView.findViewById(R.id.bauer25);
            bauer26 = itemView.findViewById(R.id.bauer26);
            bauer27 = itemView.findViewById(R.id.bauer27);
            bauer28 = itemView.findViewById(R.id.bauer28);
            bauer3t = itemView.findViewById(R.id.bauer3_text);
            bauer31 = itemView.findViewById(R.id.bauer31);
            bauer32 = itemView.findViewById(R.id.bauer32);
            bauer33 = itemView.findViewById(R.id.bauer33);
            bauer34 = itemView.findViewById(R.id.bauer34);
            bauer35 = itemView.findViewById(R.id.bauer35);
            bauer36 = itemView.findViewById(R.id.bauer36);
            bauer37 = itemView.findViewById(R.id.bauer37);
            bauer38 = itemView.findViewById(R.id.bauer38);
            bauer4t = itemView.findViewById(R.id.bauer4_text);
            bauer41 = itemView.findViewById(R.id.bauer41);
            bauer42 = itemView.findViewById(R.id.bauer42);
            bauer43 = itemView.findViewById(R.id.bauer43);
            bauer44 = itemView.findViewById(R.id.bauer44);
            bauer45 = itemView.findViewById(R.id.bauer45);
            bauer46 = itemView.findViewById(R.id.bauer46);
            bauer47 = itemView.findViewById(R.id.bauer47);
            bauer48 = itemView.findViewById(R.id.bauer48);
            varscht = itemView.findViewById(R.id.vizearsch_text);
            varsch1 = itemView.findViewById(R.id.varsch1);
            varsch2 = itemView.findViewById(R.id.varsch2);
            varsch3 = itemView.findViewById(R.id.varsch3);
            varsch4 = itemView.findViewById(R.id.varsch4);
            varsch5 = itemView.findViewById(R.id.varsch5);
            varsch6 = itemView.findViewById(R.id.varsch6);
            varsch7 = itemView.findViewById(R.id.varsch7);
            varsch8 = itemView.findViewById(R.id.varsch8);
            arscht = itemView.findViewById(R.id.arsch_text);
            arsch1 = itemView.findViewById(R.id.arsch1);
            arsch2 = itemView.findViewById(R.id.arsch2);
            arsch3 = itemView.findViewById(R.id.arsch3);
            arsch4 = itemView.findViewById(R.id.arsch4);
            arsch5 = itemView.findViewById(R.id.arsch5);
            arsch6 = itemView.findViewById(R.id.arsch6);
            arsch7 = itemView.findViewById(R.id.arsch7);
            arsch8 = itemView.findViewById(R.id.arsch8);
            king = itemView.findViewById(R.id.king);
            vking = itemView.findViewById(R.id.vize_king);
            bauer1 = itemView.findViewById(R.id.bauer1);
            bauer2 = itemView.findViewById(R.id.bauer2);
            bauer3 = itemView.findViewById(R.id.bauer3);
            bauer4 = itemView.findViewById(R.id.bauer4);
            varsch = itemView.findViewById(R.id.vizearsch);
            arsch = itemView.findViewById(R.id.arsch);
            cv= itemView.findViewById(R.id.arschloch_cardview);
            cv.setClickable(true);
        }
    }

    @NonNull
    @Override
    public shithead_adapter.Shithead_ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.arschloch_card, null);
        shithead_adapter.Shithead_ListViewHolder holder = new shithead_adapter.Shithead_ListViewHolder(v, itemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull shithead_adapter.Shithead_ListViewHolder holder, int position) {
        final Shithead_Partie shithead_partie = getItem(position);
        List<Shithead_Spieler> list = shithead_partie.getSpieler();
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Id setzen zum eindeutigen identifizieren der Partie
                mId =shithead_partie.getPartiebezeichnung();
                id = mId;
                //Neuen COnclicklistener zum editieren und automatisch drücken
                holder.cv.setOnClickListener(itemClickListener);
                holder.cv.performClick();}

        });
        holder.cv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //Id setzen zum eindeutigen identifizieren der Partie
                mId =shithead_partie.getPartiebezeichnung();
                id = mId;
                //Neuen COnclicklistener zum editieren und automatisch drücken
                holder.cv.setOnClickListener(longClickListener);
                holder.cv.performClick();
                return true;
            }
        });
        //Partiebezeichnung und Spielernamen setzen (Sind fest im Layout Implementiert)
        holder.titel.setText(shithead_partie.getPartiebezeichnung());
        holder.spieler_1.setText(list.get(0).getName());
        holder.spieler_2.setText(list.get(1).getName());
        holder.spieler_3.setText(list.get(2).getName());
        holder.spieler_4.setText(list.get(3).getName());
        holder.spieler_5.setText(list.get(4).getName());
        holder.spieler_6.setText(list.get(5).getName());
        holder.spieler_7.setText(list.get(6).getName());
        holder.spieler_8.setText(list.get(7).getName());
        //Punkte setzen (wenn vorhanden)
        if (!list.get(0).getName().equals("")) {
            holder.king1.setText(String.valueOf(list.get(0).getPlatzierungen().get(0)));
        } else holder.king1.setVisibility(View.GONE);
        if (!list.get(1).getName().equals("")) {
            holder.king2.setText(String.valueOf(list.get(1).getPlatzierungen().get(0)));
        } else holder.king2.setVisibility(View.GONE);
        if (!list.get(2).getName().equals("")) {
            holder.king3.setText(String.valueOf(list.get(2).getPlatzierungen().get(0)));
        } else holder.king3.setVisibility(View.GONE);
        if (!list.get(3).getName().equals("")) {
            holder.king4.setText(String.valueOf(list.get(3).getPlatzierungen().get(0)));
        } else holder.king4.setVisibility(View.GONE);
        if (!list.get(4).getName().equals("")) {
            holder.king5.setText(String.valueOf(list.get(4).getPlatzierungen().get(0)));
        } else holder.king5.setVisibility(View.GONE);
        if (!list.get(5).getName().equals("")) {
            holder.king6.setText(String.valueOf(list.get(5).getPlatzierungen().get(0)));
        } else holder.king6.setVisibility(View.GONE);
        if (!list.get(6).getName().equals("")) {
            holder.king7.setText(String.valueOf(list.get(6).getPlatzierungen().get(0)));
        } else holder.king7.setVisibility(View.GONE);
        if (!list.get(7).getName().equals("")) {
            holder.king8.setText(String.valueOf(list.get(7).getPlatzierungen().get(0)));
        } else holder.king8.setVisibility(View.GONE);

        if(!list.get(1).getName().equals("")){
            if (!list.get(0).getName().equals("")) {
                holder.vking1.setText(String.valueOf(list.get(0).getPlatzierungen().get(1)));
            } else holder.vking1.setVisibility(View.GONE);
            if (!list.get(1).getName().equals("")) {
                holder.vking2.setText(String.valueOf(list.get(1).getPlatzierungen().get(1)));
            } else holder.vking2.setVisibility(View.GONE);
            if (!list.get(2).getName().equals("")) {
                holder.vking3.setText(String.valueOf(list.get(2).getPlatzierungen().get(1)));
            } else holder.vking3.setVisibility(View.GONE);
            if (!list.get(3).getName().equals("")) {
                holder.vking4.setText(String.valueOf(list.get(3).getPlatzierungen().get(1)));
            } else holder.vking4.setVisibility(View.GONE);
            if (!list.get(4).getName().equals("")) {
                holder.vking5.setText(String.valueOf(list.get(4).getPlatzierungen().get(1)));
            } else holder.vking5.setVisibility(View.GONE);
            if (!list.get(5).getName().equals("")) {
                holder.vking6.setText(String.valueOf(list.get(5).getPlatzierungen().get(1)));
            } else holder.vking6.setVisibility(View.GONE);
            if (!list.get(6).getName().equals("")) {
                holder.vking7.setText(String.valueOf(list.get(6).getPlatzierungen().get(1)));
            } else holder.vking7.setVisibility(View.GONE);
            if (!list.get(7).getName().equals("")) {
                holder.vking8.setText(String.valueOf(list.get(7).getPlatzierungen().get(1)));
            } else holder.vking8.setVisibility(View.GONE);
        }else holder.vking.setVisibility(View.GONE);

        if(!list.get(2).getName().equals("")){
            if (!list.get(0).getName().equals("")) {
                holder.bauer11.setText(String.valueOf(list.get(0).getPlatzierungen().get(2)));
            } else holder.bauer11.setVisibility(View.GONE);
            if (!list.get(1).getName().equals("")) {
                holder.bauer12.setText(String.valueOf(list.get(1).getPlatzierungen().get(2)));
            } else holder.bauer12.setVisibility(View.GONE);
            if (!list.get(2).getName().equals("")) {
                holder.bauer13.setText(String.valueOf(list.get(2).getPlatzierungen().get(2)));
            } else holder.bauer13.setVisibility(View.GONE);
            if (!list.get(3).getName().equals("")) {
                holder.bauer14.setText(String.valueOf(list.get(3).getPlatzierungen().get(2)));
            } else holder.bauer14.setVisibility(View.GONE);
            if (!list.get(4).getName().equals("")) {
                holder.bauer15.setText(String.valueOf(list.get(4).getPlatzierungen().get(2)));
            } else holder.bauer15.setVisibility(View.GONE);
            if (!list.get(5).getName().equals("")) {
                holder.bauer16.setText(String.valueOf(list.get(5).getPlatzierungen().get(2)));
            } else holder.bauer16.setVisibility(View.GONE);
            if (!list.get(6).getName().equals("")) {
                holder.bauer17.setText(String.valueOf(list.get(6).getPlatzierungen().get(2)));
            } else holder.bauer17.setVisibility(View.GONE);
            if (!list.get(7).getName().equals("")) {
                holder.bauer18.setText(String.valueOf(list.get(7).getPlatzierungen().get(2)));
            } else holder.bauer18.setVisibility(View.GONE);
        }else holder.bauer1.setVisibility(View.GONE);

        if(!list.get(3).getName().equals("")){
            if (!list.get(0).getName().equals("")) {
                holder.bauer21.setText(String.valueOf(list.get(0).getPlatzierungen().get(3)));
            } else holder.bauer21.setVisibility(View.GONE);
            if (!list.get(1).getName().equals("")) {
                holder.bauer22.setText(String.valueOf(list.get(1).getPlatzierungen().get(3)));
            } else holder.bauer22.setVisibility(View.GONE);
            if (!list.get(2).getName().equals("")) {
                holder.bauer23.setText(String.valueOf(list.get(2).getPlatzierungen().get(3)));
            } else holder.bauer23.setVisibility(View.GONE);
            if (!list.get(3).getName().equals("")) {
                holder.bauer24.setText(String.valueOf(list.get(3).getPlatzierungen().get(3)));
            } else holder.bauer24.setVisibility(View.GONE);
            if (!list.get(4).getName().equals("")) {
                holder.bauer25.setText(String.valueOf(list.get(4).getPlatzierungen().get(3)));
            } else holder.bauer25.setVisibility(View.GONE);
            if (!list.get(5).getName().equals("")) {
                holder.bauer26.setText(String.valueOf(list.get(5).getPlatzierungen().get(3)));
            } else holder.bauer26.setVisibility(View.GONE);
            if (!list.get(6).getName().equals("")) {
                holder.bauer27.setText(String.valueOf(list.get(6).getPlatzierungen().get(3)));
            } else holder.bauer27.setVisibility(View.GONE);
            if (!list.get(7).getName().equals("")) {
                holder.bauer28.setText(String.valueOf(list.get(7).getPlatzierungen().get(3)));
            } else holder.bauer28.setVisibility(View.GONE);
        }else holder.bauer2.setVisibility(View.GONE);

        if(!list.get(4).getName().equals("")){
            if (!list.get(0).getName().equals("")) {
                holder.bauer31.setText(String.valueOf(list.get(0).getPlatzierungen().get(4)));
            } else holder.bauer31.setVisibility(View.GONE);
            if (!list.get(1).getName().equals("")) {
                holder.bauer32.setText(String.valueOf(list.get(1).getPlatzierungen().get(4)));
            } else holder.bauer32.setVisibility(View.GONE);
            if (!list.get(2).getName().equals("")) {
                holder.bauer33.setText(String.valueOf(list.get(2).getPlatzierungen().get(4)));
            } else holder.bauer33.setVisibility(View.GONE);
            if (!list.get(3).getName().equals("")) {
                holder.bauer34.setText(String.valueOf(list.get(3).getPlatzierungen().get(4)));
            } else holder.bauer34.setVisibility(View.GONE);
            if (!list.get(4).getName().equals("")) {
                holder.bauer35.setText(String.valueOf(list.get(4).getPlatzierungen().get(4)));
            } else holder.bauer35.setVisibility(View.GONE);
            if (!list.get(5).getName().equals("")) {
                holder.bauer36.setText(String.valueOf(list.get(5).getPlatzierungen().get(4)));
            } else holder.bauer36.setVisibility(View.GONE);
            if (!list.get(6).getName().equals("")) {
                holder.bauer37.setText(String.valueOf(list.get(6).getPlatzierungen().get(4)));
            } else holder.bauer37.setVisibility(View.GONE);
            if (!list.get(7).getName().equals("")) {
                holder.bauer38.setText(String.valueOf(list.get(7).getPlatzierungen().get(4)));
            } else holder.bauer38.setVisibility(View.GONE);
        }else holder.bauer3.setVisibility(View.GONE);

        if(!list.get(5).getName().equals("")){
            if (!list.get(0).getName().equals("")) {
                holder.bauer41.setText(String.valueOf(list.get(0).getPlatzierungen().get(5)));
            } else holder.bauer41.setVisibility(View.GONE);
            if (!list.get(1).getName().equals("")) {
                holder.bauer42.setText(String.valueOf(list.get(1).getPlatzierungen().get(5)));
            } else holder.bauer42.setVisibility(View.GONE);
            if (!list.get(2).getName().equals("")) {
                holder.bauer43.setText(String.valueOf(list.get(2).getPlatzierungen().get(5)));
            } else holder.bauer43.setVisibility(View.GONE);
            if (!list.get(3).getName().equals("")) {
                holder.bauer44.setText(String.valueOf(list.get(3).getPlatzierungen().get(5)));
            } else holder.bauer44.setVisibility(View.GONE);
            if (!list.get(4).getName().equals("")) {
                holder.bauer45.setText(String.valueOf(list.get(4).getPlatzierungen().get(5)));
            } else holder.bauer45.setVisibility(View.GONE);
            if (!list.get(5).getName().equals("")) {
                holder.bauer46.setText(String.valueOf(list.get(5).getPlatzierungen().get(5)));
            } else holder.bauer46.setVisibility(View.GONE);
            if (!list.get(6).getName().equals("")) {
                holder.bauer47.setText(String.valueOf(list.get(6).getPlatzierungen().get(5)));
            } else holder.bauer47.setVisibility(View.GONE);
            if (!list.get(7).getName().equals("")) {
                holder.bauer48.setText(String.valueOf(list.get(7).getPlatzierungen().get(5)));
            } else holder.bauer48.setVisibility(View.GONE);
        }else holder.bauer4.setVisibility(View.GONE);

        if(!list.get(6).getName().equals("")){
            if (!list.get(0).getName().equals("")) {
                holder.varsch1.setText(String.valueOf(list.get(0).getPlatzierungen().get(6)));
            } else holder.varsch1.setVisibility(View.GONE);
            if (!list.get(1).getName().equals("")) {
                holder.varsch2.setText(String.valueOf(list.get(1).getPlatzierungen().get(6)));
            } else holder.varsch2.setVisibility(View.GONE);
            if (!list.get(2).getName().equals("")) {
                holder.varsch3.setText(String.valueOf(list.get(2).getPlatzierungen().get(6)));
            } else holder.varsch3.setVisibility(View.GONE);
            if (!list.get(3).getName().equals("")) {
                holder.varsch4.setText(String.valueOf(list.get(3).getPlatzierungen().get(6)));
            } else holder.varsch4.setVisibility(View.GONE);
            if (!list.get(4).getName().equals("")) {
                holder.varsch5.setText(String.valueOf(list.get(4).getPlatzierungen().get(6)));
            } else holder.varsch5.setVisibility(View.GONE);
            if (!list.get(5).getName().equals("")) {
                holder.varsch6.setText(String.valueOf(list.get(5).getPlatzierungen().get(6)));
            } else holder.varsch6.setVisibility(View.GONE);
            if (!list.get(6).getName().equals("")) {
                holder.varsch7.setText(String.valueOf(list.get(6).getPlatzierungen().get(6)));
            } else holder.varsch7.setVisibility(View.GONE);
            if (!list.get(7).getName().equals("")) {
                holder.varsch8.setText(String.valueOf(list.get(7).getPlatzierungen().get(6)));
            } else holder.varsch8.setVisibility(View.GONE);
        }else holder.varsch.setVisibility(View.GONE);

        if(!list.get(7).getName().equals("")){
            if (!list.get(0).getName().equals("")) {
                holder.arsch1.setText(String.valueOf(list.get(0).getPlatzierungen().get(7)));
            } else holder.arsch1.setVisibility(View.GONE);
            if (!list.get(1).getName().equals("")) {
                holder.arsch2.setText(String.valueOf(list.get(1).getPlatzierungen().get(7)));
            } else holder.arsch2.setVisibility(View.GONE);
            if (!list.get(2).getName().equals("")) {
                holder.arsch3.setText(String.valueOf(list.get(2).getPlatzierungen().get(7)));
            } else holder.arsch3.setVisibility(View.GONE);
            if (!list.get(3).getName().equals("")) {
                holder.arsch4.setText(String.valueOf(list.get(3).getPlatzierungen().get(7)));
            } else holder.arsch4.setVisibility(View.GONE);
            if (!list.get(4).getName().equals("")) {
                holder.arsch5.setText(String.valueOf(list.get(4).getPlatzierungen().get(7)));
            } else holder.arsch5.setVisibility(View.GONE);
            if (!list.get(5).getName().equals("")) {
                holder.arsch6.setText(String.valueOf(list.get(5).getPlatzierungen().get(7)));
            } else holder.arsch6.setVisibility(View.GONE);
            if (!list.get(6).getName().equals("")) {
                holder.arsch7.setText(String.valueOf(list.get(6).getPlatzierungen().get(7)));
            } else holder.arsch7.setVisibility(View.GONE);
            if (!list.get(7).getName().equals("")) {
                holder.arsch8.setText(String.valueOf(list.get(7).getPlatzierungen().get(7)));
            } else holder.arsch8.setVisibility(View.GONE);
        }else holder.arsch.setVisibility(View.GONE);

        int s=0;
        for (int i = 0; i <list.size() ; i++) {
            if(!list.get(i).getName().equals("")){
                s++;
            }
        }

        if(s==8){
            holder.kingt.setText("1.");
            holder.vkingt.setText("2.");
            holder.bauer1t.setText("3.");
            holder.bauer2t.setText("4.");
            holder.bauer3t.setText("5.");
            holder.bauer4t.setText("6.");
            holder.varscht.setText("7.");
            holder.arscht.setText("8.");
        }
        if(s==7){
            holder.kingt.setText("1.");
            holder.vkingt.setText("2.");
            holder.bauer1t.setText("3.");
            holder.bauer2t.setText("4.");
            holder.bauer3t.setText("5.");
            holder.bauer4t.setText("6.");
            holder.varscht.setText("9.");
        } else if(s==6){
            holder.kingt.setText("1.");
            holder.vkingt.setText("2.");
            holder.bauer1t.setText("3.");
            holder.bauer2t.setText("4.");
            holder.bauer3t.setText("5.");
            holder.bauer4t.setText("6.");
        } else if(s==5){
            holder.kingt.setText("1.");
            holder.vkingt.setText("2.");
            holder.bauer1t.setText("3.");
            holder.bauer2t.setText("4.");
            holder.bauer3t.setText("5.");
        }else if(s==4){
            holder.kingt.setText("1.");
            holder.vkingt.setText("2.");
            holder.bauer1t.setText("3.");
            holder.bauer2t.setText("4.");
        } else if(s==3){
            holder.kingt.setText("1.");
            holder.vkingt.setText("2.");
            holder.bauer1t.setText("3.");
        } else if(s==2){
            holder.kingt.setText("1.");
            holder.vkingt.setText("2.");

        } else if (s==1) holder.kingt.setText("1.");

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
