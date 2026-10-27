package com.mrudul.tourandtraveladmin.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mrudul.tourandtraveladmin.R;
import com.mrudul.tourandtraveladmin.adapters.EmployeeListAdapter;
import com.mrudul.tourandtraveladmin.interfaces.OnEmpClickListener;
import com.mrudul.tourandtraveladmin.model.DriverListRequest;
import com.mrudul.tourandtraveladmin.model.TourGuideListRequest;
import com.mrudul.tourandtraveladmin.network.ApiService;
import com.mrudul.tourandtraveladmin.network.RetrofitClient;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;

import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmployeeListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmployeeListFragment extends Fragment implements OnEmpClickListener {

    RecyclerView recyclerView;
    ArrayList<DriverListRequest> driverList;
    ArrayList<TourGuideListRequest> tourGuideList;
    EmployeeListAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EmployeeListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EmployeeListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EmployeeListFragment newInstance(String param1, String param2) {
        EmployeeListFragment fragment = new EmployeeListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_employee_list, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.employeeListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        driverList = new ArrayList<>();
        tourGuideList = new ArrayList<>();
        adapter = new EmployeeListAdapter(view.getContext(),driverList,tourGuideList,this);
        recyclerView.setAdapter(adapter);


        getDrivers();
        getTourGuides();
    }


    private void getTourGuides() {

        ApiService apiService =
                RetrofitClient.getClient()
                        .create(ApiService.class);

        apiService.getTourGuides().enqueue(
                new Callback<List<TourGuideListRequest>>() {

                    @Override
                    public void onResponse(
                            Call<List<TourGuideListRequest>> call,
                            Response<List<TourGuideListRequest>> response) {

                        if (response.isSuccessful()
                                && response.body() != null) {

                            tourGuideList.clear();

                            tourGuideList.addAll(response.body());

                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<List<TourGuideListRequest>> call,
                            Throwable t) {

                        Log.e("GUIDE_API", t.getMessage());
                    }
                });
    }

    private void getDrivers() {

        ApiService apiService =
                RetrofitClient.getClient()
                        .create(ApiService.class);

        apiService.getDrivers().enqueue(
                new Callback<List<DriverListRequest>>() {

                    @Override
                    public void onResponse(
                            Call<List<DriverListRequest>> call,
                            Response<List<DriverListRequest>> response) {

                        if (response.isSuccessful()
                                && response.body() != null) {

                            driverList.clear();

                            driverList.addAll(response.body());

                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<List<DriverListRequest>> call,
                            Throwable t) {

                        Log.e("DRIVER_API", t.getMessage());
                    }
                });
    }

    @Override
    public void OnEmpClickDriver(DriverListRequest data) {

    }

    @Override
    public void OnEmpClickTourGuide(TourGuideListRequest data) {

    }
}