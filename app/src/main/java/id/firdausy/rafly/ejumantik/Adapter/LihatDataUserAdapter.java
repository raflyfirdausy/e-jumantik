package id.firdausy.rafly.ejumantik.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.firdausy.rafly.ejumantik.Helper.Waktu;
import id.firdausy.rafly.ejumantik.Model.InputModel;
import id.firdausy.rafly.ejumantik.R;
import id.firdausy.rafly.ejumantik.User.DetailInputanActivity;

public class LihatDataUserAdapter extends RecyclerView.Adapter<LihatDataUserAdapter.ViewHolder> {
    private Context context;
    private List<InputModel> list;

    public LihatDataUserAdapter(Context context, List<InputModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lihat_data_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final int posisi = list.size() - position - 1;
        holder.tvWaktuInput.setText(new Waktu(context).getWaktuLengkapIndonesia(Long.parseLong(list.get(posisi).getWaktu())));
        holder.tvTotalKontainer.setText("Total Kontainer : " + list.get(posisi).getTotalKontainer());
        holder.tvTotalPositifJentik.setText("Total Positif Jentik : " + list.get(posisi).getTotalPositifJentik());

        holder.layoutParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailInputanActivity.class);
                intent.putExtra("data", list.get(posisi));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvWaktuInput;
        private TextView tvTotalKontainer;
        private TextView tvTotalPositifJentik;
        private LinearLayout layoutParent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvWaktuInput = itemView.findViewById(R.id.tvWaktuInput);
            tvTotalKontainer = itemView.findViewById(R.id.tvTotalKontainer);
            tvTotalPositifJentik = itemView.findViewById(R.id.tvTotalPositifJentik);
            layoutParent = itemView.findViewById(R.id.layoutParent);
        }
    }
}
