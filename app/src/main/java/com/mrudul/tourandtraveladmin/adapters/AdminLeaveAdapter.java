package com.mrudul.tourandtraveladmin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mrudul.tourandtraveladmin.R;
import com.mrudul.tourandtraveladmin.model.LeaveResponse;

import java.util.List;

public class AdminLeaveAdapter
        extends RecyclerView.Adapter<AdminLeaveAdapter.LeaveViewHolder> {

    private List<LeaveResponse> leaveList;

    private OnLeaveActionListener listener;

    public interface OnLeaveActionListener {
        void onApprove(LeaveResponse leave);
        void onReject(LeaveResponse leave);
    }

    public AdminLeaveAdapter(
            List<LeaveResponse> leaveList,
            OnLeaveActionListener listener) {

        this.leaveList = leaveList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LeaveViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_admin_leave, parent, false);

        return new LeaveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull LeaveViewHolder holder,
            int position) {

        LeaveResponse leave = leaveList.get(position);

        if (leave.getDriverId() != null) {

            holder.applicantText.setText(
                    "Driver ID: " + leave.getDriverId()
            );

        } else if (leave.getGuideId() != null) {

            holder.applicantText.setText(
                    "Tour Guide ID: " + leave.getGuideId()
            );
        }

        holder.leaveDateText.setText(
                leave.getStartDate()
                        + " to "
                        + leave.getEndDate()
        );

        holder.leaveSubjectText.setText(
                "Subject: " + leave.getSubject()
        );

        holder.leaveDescriptionText.setText(
                "Description: " + leave.getDescription()
        );

        holder.approveLeaveButton.setOnClickListener(v ->
                listener.onApprove(leave)
        );

        holder.rejectLeaveButton.setOnClickListener(v ->
                listener.onReject(leave)
        );
    }

    @Override
    public int getItemCount() {
        return leaveList.size();
    }

    static class LeaveViewHolder
            extends RecyclerView.ViewHolder {

        TextView applicantText;
        TextView leaveDateText;
        TextView leaveSubjectText;
        TextView leaveDescriptionText;

        Button approveLeaveButton;
        Button rejectLeaveButton;

        public LeaveViewHolder(@NonNull View itemView) {
            super(itemView);

            applicantText =
                    itemView.findViewById(R.id.applicantText);

            leaveDateText =
                    itemView.findViewById(R.id.leaveDateText);

            leaveSubjectText =
                    itemView.findViewById(R.id.leaveSubjectText);

            leaveDescriptionText =
                    itemView.findViewById(R.id.leaveDescriptionText);

            approveLeaveButton =
                    itemView.findViewById(R.id.approveLeaveButton);

            rejectLeaveButton =
                    itemView.findViewById(R.id.rejectLeaveButton);
        }
    }
}