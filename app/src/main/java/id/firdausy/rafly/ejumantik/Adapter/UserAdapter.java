package id.firdausy.rafly.ejumantik.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.List;

import id.firdausy.rafly.ejumantik.Activity.InputDataAdminActivity;
import id.firdausy.rafly.ejumantik.Activity.LihatDataUserFragmentActivity;
import id.firdausy.rafly.ejumantik.Model.UserModel;
import id.firdausy.rafly.ejumantik.R;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context context;
    private List<UserModel> list;
    private String mode;

    public UserAdapter(Context context, List<UserModel> list, String mode) {
        this.context = context;
        this.list = list;
        this.mode = mode;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_kepala_keluarga, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvNamaKepalaKeluarga.setText(list.get(position).getNamaLengkap().toUpperCase());
        holder.tvNomerHp.setText(list.get(position).getNomerHp());

        String firstLetter = String.valueOf(list.get(position).getNamaLengkap().charAt(0)).toUpperCase();
        ColorGenerator generator = ColorGenerator.DEFAULT;
        int color = generator.getRandomColor();
        TextDrawable drawable = TextDrawable.builder().buildRound(firstLetter, color);
        holder.ivHuruf.setImageDrawable(drawable);

        if (mode.equalsIgnoreCase("input")) {
            holder.layoutParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, InputDataAdminActivity.class);
                    intent.putExtra("data", list.get(position));
                    context.startActivity(intent);
                }
            });
        } else if (mode.equalsIgnoreCase("lihat")) {
            holder.layoutParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, LihatDataUserFragmentActivity.class);
                    intent.putExtra("data", list.get(position));
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout layoutParent;
        private ImageView ivHuruf;
        private TextView tvNamaKepalaKeluarga;
        private TextView tvNomerHp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutParent = itemView.findViewById(R.id.layoutParent);
            ivHuruf = itemView.findViewById(R.id.ivHuruf);
            tvNamaKepalaKeluarga = itemView.findViewById(R.id.tvNamaKepalaKeluarga);
            tvNomerHp = itemView.findViewById(R.id.tvNomerHp);
        }
    }
}
