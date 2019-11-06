package recyclerview;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.nachtaktiverhalbaffe.kartenspiele.Golfen;
import com.example.nachtaktiverhalbaffe.kartenspiele.R;
import java.util.List;
import java.util.Objects;
import Database.Golfen.Golfen_Partie;
import Database.Golfen.Golfen_Spieler;


public class golfen_adapter extends ListAdapter<Golfen_Partie,golfen_adapter.Golfen_ListViewHolder> {
    public String mId;
    public static String id;
    Context mContext;
    View.OnClickListener itemClickListener = null, longClicklistener= null;
    public int f;

    public golfen_adapter(Context mContext, View.OnClickListener itemClickListener, View.OnClickListener longClicklistener) {
        super(DIFF_CALLBACK);
        this.mContext= mContext;
        this.itemClickListener = itemClickListener;
        this.longClicklistener = longClicklistener;
    }

    private static final DiffUtil.ItemCallback<Golfen_Partie> DIFF_CALLBACK = new DiffUtil.ItemCallback<Golfen_Partie>() {
        @Override
        public boolean areItemsTheSame(@NonNull Golfen_Partie oldItem, @NonNull Golfen_Partie newItem) {
            return oldItem.getPartiebezeichnung().equals(newItem.getPartiebezeichnung());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Golfen_Partie oldItem, @NonNull Golfen_Partie newItem) {
            return oldItem.getPartiebezeichnung().equals(newItem.getPartiebezeichnung()) && Objects.equals(oldItem.getGolfen_spieler(), newItem.getGolfen_spieler());
        }};

    class Golfen_ListViewHolder extends RecyclerView.ViewHolder {
        public TextView titel, spieler_1, spieler_2, spieler_3, spieler_4, spieler_5, spieler_6, spieler_7, spieler_8;
        TableLayout Table;
        TableRow Namen, Score, Punkte;
        CardView cv;

        public Golfen_ListViewHolder(View itemView, View.OnClickListener itemClickListener) {                                                     //Variablen fÃ¼r cards setzen
            super(itemView);
            titel = itemView.findViewById((R.id.golfen_titel));
            spieler_1 = itemView.findViewById((R.id.golfen_spieler1));
            spieler_2 = itemView.findViewById((R.id.golfen_spieler2));
            spieler_3 = itemView.findViewById((R.id.golfen_spieler3));
            spieler_4 = itemView.findViewById((R.id.golfen_spieler4));
            spieler_5 = itemView.findViewById((R.id.golfen_spieler5));
            spieler_6 = itemView.findViewById((R.id.golfen_spieler6));
            spieler_7 = itemView.findViewById((R.id.golfen_spieler7));
            spieler_8 = itemView.findViewById((R.id.golfen_spieler8));
            Table = itemView.findViewById(R.id.golfen_table);
            Namen = itemView.findViewById(R.id.golfen_card_name);
            Score = itemView.findViewById(R.id.score);
            Punkte = itemView.findViewById(R.id.golfen_punkte);
            cv= itemView.findViewById(R.id.golfen_cardview);
        }
    }

    @Override
    public golfen_adapter.Golfen_ListViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.golfen_card, null);
        Golfen_ListViewHolder holder = new Golfen_ListViewHolder(v, itemClickListener);
        f = viewType;
        return holder;
    }

    @Override
    public void onBindViewHolder(final golfen_adapter.Golfen_ListViewHolder holder, int position) {
        final Golfen_Partie golfen_partie = getItem(position);
        List<Golfen_Spieler> list = golfen_partie.getGolfen_spieler();
        TableLayout table = holder.Table;
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Id setzen zum eindeutigen identifizieren der Partie
                mId=golfen_partie.getPartiebezeichnung();
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
                mId=golfen_partie.getPartiebezeichnung();
                id=mId;
                //Neuen COnclicklistener zum editieren und automatisch drücken
                holder.cv.setOnClickListener(longClicklistener);
                holder.cv.performClick();
                return true;
            }
        });
        //Partiebezeichnung und Spielernamen setzen (Sind fest im Layout Implementiert)
        holder.titel.setText(golfen_partie.getPartiebezeichnung());
        holder.spieler_1.setText(list.get(0).getName());
        holder.spieler_2.setText(list.get(1).getName());
        holder.spieler_3.setText(list.get(2).getName());
        holder.spieler_4.setText(list.get(3).getName());
        holder.spieler_5.setText(list.get(4).getName());
        holder.spieler_6.setText(list.get(5).getName());
        holder.spieler_7.setText(list.get(6).getName());
        holder.spieler_8.setText(list.get(7).getName());

        if (list.get(0).getPunkte() != null) {
            for (int i = 0; i < list.get(0).getPunkte().size(); i++) {
                if(Golfen.alive){break;}
                //Tabellenzeile Punkte dynamisch hinzufügen
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View tr = inflater.inflate(R.layout.tablerow_punkte, null, false);
                TextView punkte1 = tr.findViewById(R.id.punkte1);
                TextView punkte2 = tr.findViewById(R.id.punkte2);
                TextView punkte3 = tr.findViewById(R.id.punkte3);
                TextView punkte4 = tr.findViewById(R.id.punkte4);
                TextView punkte5 = tr.findViewById(R.id.punkte5);
                TextView punkte6 = tr.findViewById(R.id.punkte6);
                TextView punkte7 = tr.findViewById(R.id.punkte7);
                TextView punkte8 = tr.findViewById(R.id.punkte8);

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
                if (!list.get(4).getName().equals("")) {
                    punkte5.setText(String.valueOf(list.get(4).getPunkte().get(i)));
                } else punkte5.setText("");
                if (!list.get(5).getName().equals("")) {
                    punkte6.setText(String.valueOf(list.get(5).getPunkte().get(i)));
                } else punkte6.setText("");
                if (!list.get(6).getName().equals("")) {
                    punkte7.setText(String.valueOf(list.get(6).getPunkte().get(i)));
                } else punkte7.setText("");
                if (!list.get(7).getName().equals("")) {
                    punkte8.setText(String.valueOf(list.get(7).getPunkte().get(i)));
                } else punkte8.setText("");
                //Runde Tabbelenzeile Punkte hinzufügen
                table.addView(tr);
            }
        }

        //Tabellenzeile Punkte dynamisch hinzufügen
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View tr = inflater.inflate(R.layout.tablerow_score, null, false);
        TextView score1 = tr.findViewById(R.id.score1);
        TextView score2 = tr.findViewById(R.id.score2);
        TextView score3 = tr.findViewById(R.id.score3);
        TextView score4 = tr.findViewById(R.id.score4);
        TextView score5 = tr.findViewById(R.id.score5);
        TextView score6 = tr.findViewById(R.id.score6);
        TextView score7 = tr.findViewById(R.id.score7);
        TextView score8 = tr.findViewById(R.id.score8);
        //Score setzen
        if(!Golfen.alive){
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
            if (!list.get(4).getName().equals("")) {
                score5.setText("0");
            } else score5.setText("");
            if (!list.get(5).getName().equals("")) {
                score6.setText("0");
            } else score6.setText("");
            if (!list.get(6).getName().equals("")) {
                score7.setText("0");
            } else score7.setText("");
            if (!list.get(7).getName().equals("")) {
                score8.setText("0");
            } else score8.setText("");
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
            if (!list.get(4).getName().equals("")) {
                score5.setText(String.valueOf(list.get(4).getSumme()));
            } else score5.setText("");
            if (!list.get(5).getName().equals("")) {
                score6.setText(String.valueOf(list.get(5).getSumme()));
            } else score6.setText("");
            if (!list.get(6).getName().equals("")) {
                score7.setText(String.valueOf(list.get(6).getSumme()));
            } else score7.setText("");
            if (!list.get(7).getName().equals("")) {
                score8.setText(String.valueOf(list.get(7).getSumme()));
            } else score8.setText("");
        }
        //Runde Tabbelenzeile Punkte hinzufügen
        table.addView(tr);
    }}



    @Override
    public int getItemViewType(int position) {
        return position;
    }



}