package com.thrashed.lubimec_crm.Adapters;

import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.thrashed.lubimec_crm.DAO.Record;
import com.thrashed.lubimec_crm.Fragments.RecordFragment;
import com.thrashed.lubimec_crm.R;
import com.thrashed.lubimec_crm.models.RV_Records;

import java.util.List;

public class RV_Adapter extends RecyclerView.Adapter<RV_Adapter.RecordsViewHolder> {



    public static class RecordsViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        ImageView rv_icon;
        TextView rv_time;
        TextView rv_petname;
        TextView rv_veterinarian;
        TextView rv_fio;
        TextView rv_problem;
        TextView rv_animal;
        LinearLayout rv_expand;
        LinearLayout rv_bottom_sheet;


        RecordsViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            rv_time = itemView.findViewById(R.id.rv_time);
            rv_petname = itemView.findViewById(R.id.rv_petname);
            rv_veterinarian = itemView.findViewById(R.id.rv_vetrinarian);
            rv_fio = itemView.findViewById(R.id.rv_fio);
            rv_problem = itemView.findViewById(R.id.rv_problem);
            rv_animal = itemView.findViewById(R.id.rv_animal);
            rv_expand = itemView.findViewById(R.id.rv_expand_layout);
            rv_icon = itemView.findViewById(R.id.rv_icon);
            rv_bottom_sheet = itemView.findViewById(R.id.bottom_sheet);

        }
    }

    List<Record> records;

    public RV_Adapter(List<Record> record){
        this.records = record;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public RecordsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_item, viewGroup, false);
        RecordsViewHolder pvh = new RecordsViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final RecordsViewHolder recordsViewHolder, int i) {




        recordsViewHolder.rv_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int expanded = recordsViewHolder.rv_expand.getVisibility();
                if (expanded == View.VISIBLE){
                    recordsViewHolder.rv_icon.setRotation(0);
                    recordsViewHolder.rv_expand.setVisibility(View.GONE);
                }
                else  {
                    recordsViewHolder.rv_expand.setVisibility(View.VISIBLE);
                    recordsViewHolder.rv_icon.setRotation(180);

                }


            }
        });

        recordsViewHolder.rv_time.setText(records.get(i).getTime());
        recordsViewHolder.rv_petname.setText(records.get(i).getClient_petname());
        recordsViewHolder.rv_veterinarian.setText("Запись к: " + records.get(i).getVet());
        recordsViewHolder.rv_fio.setText("ФИО клиента: " + records.get(i).getClient_fio());
        recordsViewHolder.rv_problem.setText("Проблема пациента: " + records.get(i).getClient_problem());
        recordsViewHolder.rv_animal.setText("Животное: " + records.get(i).getAnimal());


    }

    @Override
    public int getItemCount() {
        return records.size();
    }



    public void removeItem(int position) {
        records.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, records.size());
    }
}

