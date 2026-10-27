package com.mrudul.tourandtraveladmin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mrudul.tourandtraveladmin.R;
import com.mrudul.tourandtraveladmin.interfaces.OnEmpClickListener;
import com.mrudul.tourandtraveladmin.model.DriverListRequest;
import com.mrudul.tourandtraveladmin.model.TourGuideListRequest;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.ViewHolder> {

    Context context;
    ArrayList<DriverListRequest> driverList;
    ArrayList<TourGuideListRequest> tourGuideList;
    OnEmpClickListener listener;


    public EmployeeListAdapter(Context context, ArrayList<DriverListRequest> driverList,
                               ArrayList<TourGuideListRequest> tourGuideList, OnEmpClickListener listener) {
        this.context = context;
        this.driverList = driverList;
        this.tourGuideList = tourGuideList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EmployeeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.employee_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeListAdapter.ViewHolder holder, int position) {

        LocalDate currentDate = LocalDate.now();

        // DRIVER
        if (position < driverList.size()) {

            DriverListRequest driverData = driverList.get(position);

            holder.name.setText(driverData.getDriverName());

            LocalDate joiningDate =
                    LocalDate.parse(driverData.getJoiningDate());

            int experience =
                    Period.between(joiningDate, currentDate).getYears();

            holder.experience.setText(String.valueOf(experience));

            Glide.with(context)
                    .load(driverData.getProfileImageUrl())
                    .error(R.drawable.ic_add_tour_guide)
                    .placeholder(R.drawable.ic_add_tour_guide)
                    .into(holder.profileImage);

            holder.itemView.setOnClickListener(v->{
                listener.OnEmpClickDriver(driverData);
            });

        }

        // TOUR GUIDE
        else {

            int guidePosition = position - driverList.size();

            TourGuideListRequest tourGuideData =
                    tourGuideList.get(guidePosition);

            holder.name.setText(tourGuideData.getGuideName());

            LocalDate joiningDate =
                    LocalDate.parse(tourGuideData.getJoiningDate());

            int experience =
                    Period.between(joiningDate, currentDate).getYears();

            holder.experience.setText(String.valueOf(experience));

            Glide.with(context)
                    .load(tourGuideData.getProfileImageUrl())
                    .error(R.drawable.ic_add_tour_guide)
                    .placeholder(R.drawable.ic_add_tour_guide)
                    .into(holder.profileImage);

            holder.itemView.setOnClickListener(v->{
                listener.OnEmpClickTourGuide(tourGuideData);
            });
        }
    }

    @Override
    public int getItemCount() {
        return driverList.size()+tourGuideList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView profileImage;
        TextView name,experience;
        ImageButton deleteBtn;

        public ViewHolder(@NonNull View view) {
            super(view);

            profileImage = view.findViewById(R.id.employeeProfileImage);
            name = view.findViewById(R.id.employeeName);
            experience = view.findViewById(R.id.employeeExp);
            deleteBtn = view.findViewById(R.id.deleteEmpBtn);
        }
    }
}
