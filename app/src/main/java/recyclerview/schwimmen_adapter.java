package recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nachtaktiverhalbaffe.kartenspiele.R;
import com.example.nachtaktiverhalbaffe.kartenspiele.Schwimmen;

import java.util.List;
import java.util.Objects;

import Database.Schwimmen.Schwimmen_Database;
import Database.Schwimmen.Schwimmen_Partie;
import Database.Schwimmen.Schwimmen_Spieler;

public class schwimmen_adapter extends ListAdapter<Schwimmen_Partie, schwimmen_adapter.Schwimmen_ListViewHolder> {
    public String mId;
    public static String id;
    private Context mContext;
    View.OnClickListener itemClickListener = null, longClicklistener = null;
    public int f;

    public schwimmen_adapter(Context mContext, View.OnClickListener itemClickListener, View.OnClickListener longClicklistener) {
        super(DIFF_CALLBACK);
        this.mContext=mContext;
        this.itemClickListener = itemClickListener;
        this.longClicklistener = longClicklistener;

    }

    private static final DiffUtil.ItemCallback<Schwimmen_Partie> DIFF_CALLBACK = new DiffUtil.ItemCallback<Schwimmen_Partie>() {
        @Override
        public boolean areItemsTheSame(@NonNull Schwimmen_Partie oldItem, @NonNull Schwimmen_Partie newItem) {
            return oldItem.getTitel().equals(newItem.getTitel());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Schwimmen_Partie oldItem, @NonNull Schwimmen_Partie newItem) {
            return oldItem.getTitel().equals(newItem.getTitel()) && Objects.equals(oldItem.getSchwimmen_spieler(), newItem.getSchwimmen_spieler())
                    && Objects.equals(oldItem.getVerbliebene_spieler(),newItem.getVerbliebene_spieler()) && Objects.equals(oldItem.isGeschwommen(), newItem.isGeschwommen());
        }};

    class Schwimmen_ListViewHolder extends RecyclerView.ViewHolder {
        public TextView titel, spieler_1, spieler_2, spieler_3, spieler_4, spieler_5, spieler_6, spieler_7, spieler_8,
                        leben1, leben2, leben3, leben4, leben5, leben6, leben7, leben8,
                        platzierung1, platzierung2, platzierung3, platzierung4, platzierung5, platzierung6, platzierung7, platzierung8;
        ImageView Edit, Delete;
        CardView cv;

        public Schwimmen_ListViewHolder(View itemView, View.OnClickListener itemClickListener, View.OnClickListener longItemclicklistener) {                                                     //Variablen fÃ¼r cards setzen
            super(itemView);
            titel = itemView.findViewById((R.id.schwimmen_titel));
            spieler_1 = itemView.findViewById((R.id.schwimmen_spieler1));
            spieler_2 = itemView.findViewById((R.id.schwimmen_spieler2));
            spieler_3 = itemView.findViewById((R.id.schwimmen_spieler3));
            spieler_4 = itemView.findViewById((R.id.schwimmen_spieler4));
            spieler_5 = itemView.findViewById((R.id.schwimmen_spieler5));
            spieler_6 = itemView.findViewById((R.id.schwimmen_spieler6));
            spieler_7 = itemView.findViewById((R.id.schwimmen_spieler7));
            spieler_8 = itemView.findViewById((R.id.schwimmen_spieler8));
            leben1 = itemView.findViewById(R.id.leben1);
            leben2 = itemView.findViewById(R.id.leben2);
            leben3 = itemView.findViewById(R.id.leben3);
            leben4 = itemView.findViewById(R.id.leben4);
            leben5 = itemView.findViewById(R.id.leben5);
            leben6 = itemView.findViewById(R.id.leben6);
            leben7 = itemView.findViewById(R.id.leben7);
            leben8 = itemView.findViewById(R.id.leben8);
            platzierung1 = itemView.findViewById(R.id.platzierung1);
            platzierung2 = itemView.findViewById(R.id.platzierung2);
            platzierung3 = itemView.findViewById(R.id.platzierung3);
            platzierung4 = itemView.findViewById(R.id.platzierung4);
            platzierung5 = itemView.findViewById(R.id.platzierung5);
            platzierung6 = itemView.findViewById(R.id.platzierung6);
            platzierung7 = itemView.findViewById(R.id.platzierung7);
            platzierung8 = itemView.findViewById(R.id.platzierung8);
            cv= itemView.findViewById(R.id.schwimmen_cardview);
            cv.setClickable(true);
        }
    }

    @Override
    public schwimmen_adapter.Schwimmen_ListViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.schwimmen_card, null);
        Schwimmen_ListViewHolder holder = new Schwimmen_ListViewHolder(v, itemClickListener, longClicklistener);
        f = viewType;
        return holder;
    }

    @Override
    public void onBindViewHolder(final schwimmen_adapter.Schwimmen_ListViewHolder holder, int position) {
        final Schwimmen_Partie schwimmen_partie = getItem(position);
        List<Schwimmen_Spieler> list = schwimmen_partie.getSchwimmen_spieler();
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Id setzen zum eindeutigen identifizieren der Partie
                mId = schwimmen_partie.getTitel();
                id = mId;
                //Neuen COnclicklistener zum editieren und automatisch drücken
                if(schwimmen_partie.getVerbliebene_spieler()>1){
                holder.cv.setOnClickListener(itemClickListener);
                holder.cv.performClick();}
            }
        });
        holder.cv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //Id setzen zum eindeutigen identifizieren der Partie
                mId = schwimmen_partie.getTitel();
                id = mId;
                //Neuen COnclicklistener zum editieren und automatisch drücken
                if(schwimmen_partie.getVerbliebene_spieler()>1){
                    holder.cv.setOnClickListener(longClicklistener);
                    holder.cv.performClick();
            }return true;}
        });
        //Partiebezeichnung und Spielernamen setzen (Sind fest im Layout Implementiert)
        holder.titel.setText(schwimmen_partie.getTitel());
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
            holder.leben1.setText(String.valueOf(list.get(0).getLeben()));
        } else holder.leben1.setVisibility(View.GONE);
        if (!list.get(1).getName().equals("")) {
            holder.leben2.setText(String.valueOf(list.get(1).getLeben()));
        } else holder.leben2.setVisibility(View.GONE);
        if (!list.get(2).getName().equals("")) {
            holder.leben3.setText(String.valueOf(list.get(2).getLeben()));
        } else holder.leben3.setVisibility(View.GONE);
        if (!list.get(3).getName().equals("")) {
            holder.leben4.setText(String.valueOf(list.get(3).getLeben()));
        } else holder.leben4.setVisibility(View.GONE);
        if (!list.get(4).getName().equals("")) {
            holder.leben5.setText(String.valueOf(list.get(4).getLeben()));
        } else holder.leben5.setVisibility(View.GONE);
        if (!list.get(5).getName().equals("")) {
            holder.leben6.setText(String.valueOf(list.get(5).getLeben()));
        } else holder.leben6.setVisibility(View.GONE);
        if (!list.get(6).getName().equals("")) {
            holder.leben7.setText(String.valueOf(list.get(6).getLeben()));
        } else holder.leben7.setVisibility(View.GONE);
        if (!list.get(7).getName().equals("")) {
            holder.leben8.setText(String.valueOf(list.get(7).getLeben()));
        } else holder.leben8.setVisibility(View.GONE);


        holder.platzierung1.setText(schwimmen_partie.getSchwimmen_spieler().get(0).getPlatzierung());
        holder.platzierung2.setText(schwimmen_partie.getSchwimmen_spieler().get(1).getPlatzierung());
        holder.platzierung3.setText(schwimmen_partie.getSchwimmen_spieler().get(2).getPlatzierung());
        holder.platzierung4.setText(schwimmen_partie.getSchwimmen_spieler().get(3).getPlatzierung());
        holder.platzierung5.setText(schwimmen_partie.getSchwimmen_spieler().get(4).getPlatzierung());
        holder.platzierung6.setText(schwimmen_partie.getSchwimmen_spieler().get(5).getPlatzierung());
        holder.platzierung7.setText(schwimmen_partie.getSchwimmen_spieler().get(6).getPlatzierung());
        holder.platzierung8.setText(schwimmen_partie.getSchwimmen_spieler().get(7).getPlatzierung());

        if (schwimmen_partie.getVerbliebene_spieler()==1 || schwimmen_partie.getVerbliebene_spieler() ==0){
            for (int i = 0; i < list.size() ; i++) {
                if(!list.get(i).isTod()){

                    switch (i){
                        case 0:{
                            holder.platzierung1.setText("1.");
                            break;
                        }
                        case 1:{
                            holder.platzierung2.setText("1.");
                            break;
                        }
                        case 2:{
                            holder.platzierung3.setText("1.");
                            break;
                        }
                        case 3:{
                            holder.platzierung4.setText("1.");
                            break;
                        }
                        case 4:{
                            holder.platzierung5.setText("1.");
                            break;
                        }

                        case 5:{
                            holder.platzierung6.setText("1.");
                            break;
                        }
                        case 6:{
                            holder.platzierung7.setText("1.");
                            break;
                        }
                        case 7:{
                            holder.platzierung8.setText("1.");
                            break;
                        }
                    }
                }
                }
            }
        }



    @Override
    public int getItemViewType(int position) {
        return position;
    }


}
