package com.mrudul.tourandtraveladmin.adapters;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mrudul.tourandtraveladmin.R;
import com.mrudul.tourandtraveladmin.model.DestinationSelectionModel;

import java.util.Calendar;
import java.util.List;

public class DestinationSelectionAdapter extends RecyclerView.Adapter<DestinationSelectionAdapter.ViewHolder> {

    private final Context context;
    private final List<DestinationSelectionModel> destinationList;

    public DestinationSelectionAdapter(
            Context context,
            List<DestinationSelectionModel> destinationList) {

        this.context = context;
        this.destinationList = destinationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_destination_selection, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {

        DestinationSelectionModel model = destinationList.get(position);

        holder.cbDestination.setText(model.getDestination().getName());

        // Prevent old listener from affecting recycled rows
        holder.cbDestination.setOnCheckedChangeListener(null);

        holder.cbDestination.setChecked(
                model.isSelected()
        );

        if (model.isSelected()) {
            holder.layoutDetails.setVisibility(View.VISIBLE);
        } else {
            holder.layoutDetails.setVisibility(View.GONE);
        }

        holder.cbDestination.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {

                    model.setSelected(isChecked);

                    if (isChecked) {
                        holder.layoutDetails.setVisibility(
                                View.VISIBLE
                        );
                    } else {
                        holder.layoutDetails.setVisibility(
                                View.GONE
                        );
                    }
                }
        );

        if (model.getDayNumber() != null) {

            holder.etDayNumber.setText(
                    String.valueOf(model.getDayNumber())
            );
        } else {
            holder.etDayNumber.setText("");
        }

        if (model.getVisitOrder() != null) {

            holder.etVisitOrder.setText(
                    String.valueOf(model.getVisitOrder())
            );
        } else {
            holder.etVisitOrder.setText("");
        }

        holder.etVisitDate.setText(
                model.getVisitDate() != null
                        ? model.getVisitDate()
                        : ""
        );

        holder.etDayNumber.setOnFocusChangeListener(
                (v, hasFocus) -> {

                    if (!hasFocus) {

                        String value =
                                holder.etDayNumber
                                        .getText()
                                        .toString()
                                        .trim();

                        if (!value.isEmpty()) {
                            model.setDayNumber(
                                    Integer.parseInt(value)
                            );
                        }
                    }
                }
        );

        holder.etVisitOrder.setOnFocusChangeListener(
                (v, hasFocus) -> {

                    if (!hasFocus) {

                        String value =
                                holder.etVisitOrder
                                        .getText()
                                        .toString()
                                        .trim();

                        if (!value.isEmpty()) {
                            model.setVisitOrder(
                                    Integer.parseInt(value)
                            );
                        }
                    }
                }
        );

        holder.etVisitDate.setOnClickListener(v -> {

            Calendar calendar = Calendar.getInstance();

            DatePickerDialog datePickerDialog =
                    new DatePickerDialog(
                            context,
                            (view, year, month, dayOfMonth) -> {

                                String date =
                                        String.format(
                                                "%04d-%02d-%02d",
                                                year,
                                                month + 1,
                                                dayOfMonth
                                        );

                                model.setVisitDate(date);

                                holder.etVisitDate.setText(date);
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                    );

            datePickerDialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return destinationList.size();
    }

    public List<DestinationSelectionModel> getDestinationList() {
        return destinationList;
    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder {

        CheckBox cbDestination;
        LinearLayout layoutDetails;
        EditText etDayNumber;
        EditText etVisitOrder;
        EditText etVisitDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cbDestination =
                    itemView.findViewById(
                            R.id.cbDestination
                    );

            layoutDetails =
                    itemView.findViewById(
                            R.id.layoutDetails
                    );

            etDayNumber =
                    itemView.findViewById(
                            R.id.etDayNumber
                    );

            etVisitOrder =
                    itemView.findViewById(
                            R.id.etVisitOrder
                    );

            etVisitDate =
                    itemView.findViewById(
                            R.id.etVisitDate
                    );
        }
    }
}
