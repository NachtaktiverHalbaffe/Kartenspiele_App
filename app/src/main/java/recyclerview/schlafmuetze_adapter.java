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

import Database.Schlafmuetze.Schlafmuetze_Partie;
import Database.Schlafmuetze.Schlafmuetze_Spieler;

public class schlafmuetze_adapter extends ListAdapter<Schlafmuetze_Partie, schlafmuetze_adapter.Schlafmuetze_ListViewHolder> {
    public String mId;
    public static String id;
    View.OnClickListener itemClickListener = null, longClicklistener = null;

    public schlafmuetze_adapter(View.OnClickListener itemClickListener, View.OnClickListener longClicklistener) {
        super(DIFF_CALLBACK);
        this.itemClickListener = itemClickListener;
        this.longClicklistener = longClicklistener;
    }

    private static final DiffUtil.ItemCallback<Schlafmuetze_Partie> DIFF_CALLBACK = new DiffUtil.ItemCallback<Schlafmuetze_Partie>() {
        @Override
        public boolean areItemsTheSame(@NonNull Schlafmuetze_Partie oldItem, @NonNull Schlafmuetze_Partie newItem) {
            return oldItem.getPartiebezeichnung().equals(newItem.getPartiebezeichnung());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Schlafmuetze_Partie oldItem, @NonNull Schlafmuetze_Partie newItem) {
            return oldItem.getPartiebezeichnung().equals(newItem.getPartiebezeichnung()) && Objects.equals(oldItem.getSpieler(), newItem.getSpieler());
        }};

    class Schlafmuetze_ListViewHolder extends RecyclerView.ViewHolder {
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

        public Schlafmuetze_ListViewHolder(View itemView, View.OnClickListener itemClickListener, View.OnClickListener longclicklistener) {                                                     //Variablen fÃ¼r cards setzen
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
    public schlafmuetze_adapter.Schlafmuetze_ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.arschloch_card, null);
        schlafmuetze_adapter.Schlafmuetze_ListViewHolder holder = new schlafmuetze_adapter.Schlafmuetze_ListViewHolder(v, itemClickListener, longClicklistener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull schlafmuetze_adapter.Schlafmuetze_ListViewHolder holder, int position) {
        final Schlafmuetze_Partie schlafmuetze_partie = getItem(position);
        List<Schlafmuetze_Spieler> list = schlafmuetze_partie.getSpieler();
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Id setzen zum eindeutigen identifizieren der Partie
                mId =schlafmuetze_partie.getPartiebezeichnung();
                id = mId;
                //Neuen COnclicklistener zum editieren und automatisch drücken
                holder.cv.setOnClickListener(itemClickListener);
                holder.cv.performClick();}

        });
        holder.cv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //Id setzen zum eindeutigen identifizieren der Partie
                mId =schlafmuetze_partie.getPartiebezeichnung();
                id = mId;
                //Neuen COnclicklistener zum editieren und automatisch drücken
                holder.cv.setOnClickListener(longClicklistener);
                holder.cv.performClick();
                return true;
            }
        });
        //Partiebezeichnung und Spielernamen setzen (Sind fest im Layout Implementiert)
        holder.titel.setText(schlafmuetze_partie.getPartiebezeichnung());
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
            holder.king1.setText(String.valueOf(list.get(0).getVerloren()));
        } else holder.king1.setVisibility(View.GONE);
        if (!list.get(1).getName().equals("")) {
            holder.king2.setText(String.valueOf(list.get(1).getVerloren()));
        } else holder.king2.setVisibility(View.GONE);
        if (!list.get(2).getName().equals("")) {
            holder.king3.setText(String.valueOf(list.get(2).getVerloren()));
        } else holder.king3.setVisibility(View.GONE);
        if (!list.get(3).getName().equals("")) {
            holder.king4.setText(String.valueOf(list.get(3).getVerloren()));
        } else holder.king4.setVisibility(View.GONE);
        if (!list.get(4).getName().equals("")) {
            holder.king5.setText(String.valueOf(list.get(4).getVerloren()));
        } else holder.king5.setVisibility(View.GONE);
        if (!list.get(5).getName().equals("")) {
            holder.king6.setText(String.valueOf(list.get(5).getVerloren()));
        } else holder.king6.setVisibility(View.GONE);
        if (!list.get(6).getName().equals("")) {
            holder.king7.setText(String.valueOf(list.get(6).getVerloren()));
        } else holder.king7.setVisibility(View.GONE);
        if (!list.get(7).getName().equals("")) {
            holder.king8.setText(String.valueOf(list.get(7).getVerloren()));
        } else holder.king8.setVisibility(View.GONE);

        holder.vking.setVisibility(View.GONE);
        holder.bauer1.setVisibility(View.GONE);
        holder.bauer2.setVisibility(View.GONE);
        holder.bauer3.setVisibility(View.GONE);
        holder.bauer4.setVisibility(View.GONE);
        holder.varsch.setVisibility(View.GONE);
        holder.arsch.setVisibility(View.GONE);
        holder.kingt.setText("Verlierer");
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}

