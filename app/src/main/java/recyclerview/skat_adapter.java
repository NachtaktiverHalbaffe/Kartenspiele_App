package recyclerview;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.example.nachtaktiverhalbaffe.kartenspiele.R;
import com.example.nachtaktiverhalbaffe.kartenspiele.Skat;

import java.util.List;
import java.util.Objects;

import Database.Skat.Skat_Partie;
import Database.Skat.Skat_Spieler;
import Database.Skat.Skat_database;


public class skat_adapter extends ListAdapter<Skat_Partie,skat_adapter.Skat_ListViewHolder> {
    public String mId;
    public static String id;
    private Context mContext;
    View.OnClickListener itemClickListener = null;
    View.OnClickListener longClickListener = null;

    public skat_adapter(Context context, View.OnClickListener itemClickListener, View.OnClickListener longClickListener) {
        super(DIFF_CALLBACK);
        mContext = context;
        this.itemClickListener = itemClickListener;
        this.longClickListener = longClickListener;

    }

    private static final DiffUtil.ItemCallback<Skat_Partie> DIFF_CALLBACK = new DiffUtil.ItemCallback<Skat_Partie>() {
        @Override
        public boolean areItemsTheSame(@NonNull Skat_Partie oldItem, @NonNull Skat_Partie newItem) {
            return oldItem.getPartiebezeichnung().equals(newItem.getPartiebezeichnung());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Skat_Partie oldItem, @NonNull Skat_Partie newItem) {
            return oldItem.getPartiebezeichnung().equals(newItem.getPartiebezeichnung()) && Objects.equals(oldItem.getSkat_spieler(), newItem.getSkat_spieler())
                    && Objects.equals(oldItem.getGewinnstufe(),newItem.getGewinnstufe()) && Objects.equals(oldItem.getTrumpf(),newItem.getTrumpf());
        }};

    class Skat_ListViewHolder extends RecyclerView.ViewHolder {
        public TextView titel, spieler_1, spieler_2, spieler_3, spieler_4,gespielt;
        CardView cv;
        TableLayout Table;
        TableRow Namen, Score, Punkte;

        public Skat_ListViewHolder(View itemView, View.OnClickListener itemClickListener, View.OnClickListener longClickListener) {                                                     //Variablen fÃ¼r cards setzen
            super(itemView);
            titel = itemView.findViewById((R.id.skat_titel));
            spieler_1 = itemView.findViewById((R.id.skat_spieler1));
            spieler_2 = itemView.findViewById((R.id.skat_spieler2));
            spieler_3 = itemView.findViewById((R.id.skat_spieler3));
            spieler_4 = itemView.findViewById((R.id.skat_spieler4));
            gespielt= itemView.findViewById(R.id.skat_spieler_gespielt);
            Table = itemView.findViewById(R.id.skat_table);
            Namen = itemView.findViewById(R.id.skat_card_name);
            Score = itemView.findViewById(R.id.skat_score);
            Punkte = itemView.findViewById(R.id.skat_punkte);
            cv= itemView.findViewById(R.id.skat_cardview);
        }
    }

    @Override
    public skat_adapter.Skat_ListViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.skat_card, null);
        Skat_ListViewHolder holder = new Skat_ListViewHolder(v, itemClickListener, longClickListener);
        return holder;
    }



    @Override
    public void onBindViewHolder(final skat_adapter.Skat_ListViewHolder holder, int position) {
        final Skat_Partie skat_partie = getItem(position);
        List<Skat_Spieler> list = skat_partie.getSkat_spieler();
        TableLayout table = holder.Table;
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Id setzen zum eindeutigen identifizieren der Partie
                mId=skat_partie.getPartiebezeichnung();
                id=mId;
                //Neuen COnclicklistener zum editieren und automatisch drücken
                holder.cv.setOnClickListener(itemClickListener);
                holder.cv.performClick();
            }
        });
        holder.cv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //Id setzen zum eindeutigen identifizieren der Partie
                mId=skat_partie.getPartiebezeichnung();
                id=mId;
                //Neuen COnclicklistener zum editieren und automatisch drücken
                holder.cv.setOnClickListener(longClickListener);
                holder.cv.performClick();
                return true;
            }
        });
        //Partiebezeichnung und Spielernamen setzen (Sind fest im Layout Implementiert)
        holder.titel.setText(skat_partie.getPartiebezeichnung());
        holder.spieler_1.setText(list.get(0).getName());
        holder.spieler_2.setText(list.get(1).getName());
        holder.spieler_3.setText(list.get(2).getName());
        holder.spieler_4.setText(list.get(3).getName());
        holder.gespielt.setText("Gespielt");

        if (list.get(0).getPunkte() != null) {
            for (int i = 0; i < list.get(0).getPunkte().size(); i++) {
                if(Skat.alive){break;}
                //Tabellenzeile Punkte dynamisch hinzufügen
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View tr = inflater.inflate(R.layout.skat_punkte, null, false);
                TextView punkte1 = tr.findViewById(R.id.skat_punkte1);
                TextView punkte2 = tr.findViewById(R.id.skat_punkte2);
                TextView punkte3 = tr.findViewById(R.id.skat_punkte3);
                TextView punkte4 = tr.findViewById(R.id.skat_punkte4);
                TextView boni =tr.findViewById(R.id.skat_boni);
                ImageView punkte_gespielt = tr.findViewById(R.id.punkt_gespielt);

                //Punkte setzen (wenn vorhanden)
                if (!list.get(0).getName().equals("")) {
                    punkte1.setText(String.valueOf(list.get(0).getPunkte().get(i)));
                } else punkte1.setText("");
                if (!list.get(1).getName().equals("")) {
                    punkte2.setText(String.valueOf(list.get(1).getPunkte().get(i)));
                } else punkte2.setText("");
                if (!list.get(2).getName().equals("")) {
                    punkte3.setText(String.valueOf(list.get(2).getPunkte().get(i)));
                } else punkte3.setText("");
                if (!list.get(3).getName().equals("")) {
                    punkte4.setText(String.valueOf(list.get(3).getPunkte().get(i)));
                } else punkte4.setText("");
                if(skat_partie.getTrumpf()!=null){
                    switch (Integer.valueOf(skat_partie.getTrumpf().get(i))){
                        case 9:{
                            punkte_gespielt.setImageResource(R.drawable.cards_diamond);
                            punkte_gespielt.setColorFilter(mContext.getResources().getColor(R.color.colorCardRed));
                            break;
                        }
                        case 10:{
                            punkte_gespielt.setImageResource(R.drawable.cards_heart);
                            punkte_gespielt.setColorFilter(mContext.getResources().getColor(R.color.colorCardRed));
                            break;
                        }
                        case 11:{
                            punkte_gespielt.setImageResource(R.drawable.cards_spade);
                            punkte_gespielt.setColorFilter(mContext.getResources().getColor(R.color.colorCardBlack));
                            break;
                        }
                        case 12:{
                            punkte_gespielt.setImageResource(R.drawable.cards_club);
                            punkte_gespielt.setColorFilter(mContext.getResources().getColor(R.color.colorCardBlack));
                            break;
                        }
                        case 13:{
                            punkte_gespielt.setImageResource(R.drawable.alpha_g_box_outline);
                            punkte_gespielt.setColorFilter(mContext.getResources().getColor(R.color.colorCardBlack));
                            break;
                        }
                        case 14:{
                            punkte_gespielt.setImageResource(R.drawable.alpha_n_box_outline);
                            punkte_gespielt.setColorFilter(mContext.getResources().getColor(R.color.colorCardBlack));
                            break;
                        }
                        case 15:{
                            punkte_gespielt.setImageResource(R.drawable.alpha_g_box_outline);
                            punkte_gespielt.setColorFilter(mContext.getResources().getColor(R.color.colorCardBlack));
                            break;
                        }
                       }
                }else punkte_gespielt.setImageResource(R.drawable.cards_diamond);
                if(skat_partie.getGewinnstufe().size()!=0){
                    boni.setText(skat_partie.getGewinnstufe().get(i));
                }else boni.setText("");
                //Runde Tabbelenzeile Punkte hinzufügen
                table.addView(tr);
            }


        //Tabellenzeile Punkte dynamisch hinzufügen
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View tr = inflater.inflate(R.layout.skat_score, null, false);
        TextView score1 = tr.findViewById(R.id.skat_score1);
        TextView score2 = tr.findViewById(R.id.skat_score2);
        TextView score3 = tr.findViewById(R.id.skat_score3);
        TextView score4 = tr.findViewById(R.id.skat_score4);
        TextView zuletz_gespielt = tr.findViewById(R.id.skat_gerade_gespielt);

        //Score setzen
            if(!Skat.alive){
        if (list.get(0).getPunkte() == null) {
            if (!list.get(0).getName().equals("")) {
                score1.setText("0");
            } else score1.setText("");
            if (!list.get(1).getName().equals("")) {
                score2.setText("0");
            } else score2.setText("");
            if (!list.get(2).getName().equals("")) {
                score3.setText("0");
            } else score3.setText("");
            if (!list.get(3).getName().equals("")) {
                score4.setText("0");
            } else score4.setText("");
            zuletz_gespielt.setText("");

        } else {
            if (!list.get(0).getName().equals("")) {
                score1.setText(String.valueOf(list.get(0).getSumme()));
            } else score1.setText("");
            if (!list.get(1).getName().equals("")) {
                score2.setText(String.valueOf(list.get(1).getSumme()));
            } else score2.setText("");
            if (!list.get(2).getName().equals("")) {
                score3.setText(String.valueOf(list.get(2).getSumme()));
            } else score3.setText("");
            if (!list.get(3).getName().equals("")) {
                score4.setText(String.valueOf(list.get(3).getSumme()));
            } else score4.setText("");
            zuletz_gespielt.setText("");
        }
        //Runde Tabbelenzeile Punkte hinzufügen
        table.addView(tr);
    }}}

    @Override
    public int getItemViewType(int position) {
        return position;
    }


}