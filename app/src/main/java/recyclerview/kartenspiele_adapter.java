package recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nachtaktiverhalbaffe.kartenspiele.R;

import java.util.Objects;

import Database.Kartenspiele.Kartenspiel;

public class kartenspiele_adapter extends ListAdapter<Kartenspiel, kartenspiele_adapter.Kartenspiele_ListViewHolder> {
    public String mId;
    public static String id;
    View.OnClickListener itemClickListener = null;

    public kartenspiele_adapter(View.OnClickListener itemClickListener) {
        super(DIFF_CALLBACK);
        this.itemClickListener = itemClickListener;

    }

    private static final DiffUtil.ItemCallback<Kartenspiel> DIFF_CALLBACK = new DiffUtil.ItemCallback<Kartenspiel>() {
        @Override
        public boolean areItemsTheSame(@NonNull Kartenspiel oldItem, @NonNull Kartenspiel newItem) {
            return oldItem.getName().equals(newItem.getName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Kartenspiel oldItem, @NonNull Kartenspiel newItem) {
            return oldItem.getName().equals(newItem.getName()) && Objects.equals(oldItem.getKartendeck(), newItem.getKartendeck())
                    && Objects.equals(oldItem.getKomplexitaet(),newItem.getKomplexitaet()) && oldItem.getSpieleranzahl_min()==newItem.getSpieleranzahl_min()
                    && oldItem.getSpieleranzahl_max()==newItem.getSpieleranzahl_max() && oldItem.getSpielzeit().equals(newItem.getSpielzeit());
        }};

    class Kartenspiele_ListViewHolder extends RecyclerView.ViewHolder {
        public TextView name, spielzeit, spieleranzahl, komplexitaet, kartendeck;
        ImageView icon;
        CardView cv;

        public Kartenspiele_ListViewHolder(View itemView, View.OnClickListener itemClickListener) {                                                     //Variablen fÃ¼r cards setzen
            super(itemView);
            icon= itemView.findViewById(R.id.kartenspiel_icon);
            name= itemView.findViewById(R.id.spielname);
            spielzeit = itemView.findViewById(R.id.spielzeit);
            spieleranzahl = itemView.findViewById(R.id.spieleranzahl);
            komplexitaet = itemView.findViewById(R.id.komplex);
            kartendeck = itemView.findViewById(R.id.kartendeck);
            cv= itemView.findViewById(R.id.kartenspiel_cardview);
            cv.setClickable(true);
        }
    }

    @Override
    public kartenspiele_adapter.Kartenspiele_ListViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.kartenspiele_card, null);
        Kartenspiele_ListViewHolder holder = new Kartenspiele_ListViewHolder(v, itemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(final kartenspiele_adapter.Kartenspiele_ListViewHolder holder, int position) {
        final Kartenspiel list = getItem(position);

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Id setzen zum eindeutigen identifizieren der Partie
                mId = list.getName();
                id = mId;
                //Neuen Onclicklistener zum editieren und automatisch drücken
                    holder.cv.setOnClickListener(itemClickListener);
                    holder.cv.performClick();}

        });
        //Partiebezeichnung und Spielernamen setzen (Sind fest im Layout Implementiert)
        String spieleranzahl = String.valueOf(list.getSpieleranzahl_min()).toString() + "-" + String.valueOf(list.getSpieleranzahl_max()).toString();
        holder.icon.setImageResource(list.getIcon());
        holder.kartendeck.setText(list.getKartendeck());
        holder.komplexitaet.setText(list.getKomplexitaet());
        holder.spieleranzahl.setText(spieleranzahl);
        holder.spielzeit.setText(list.getSpielzeit());
        holder.name.setText(list.getName());

    }



    @Override
    public int getItemViewType(int position) {
        return position;
    }


}

