package com.mrudul.tourandtraveladmin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mrudul.tourandtraveladmin.R;
import com.mrudul.tourandtraveladmin.adapters.AdminLeaveAdapter;
import com.mrudul.tourandtraveladmin.model.LeaveResponse;
import com.mrudul.tourandtraveladmin.model.LeaveStatusRequest;
import com.mrudul.tourandtraveladmin.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminLeaveFragment extends Fragment {

    private RecyclerView leaveRecyclerView;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        return inflater.inflate(
                R.layout.fragment_admin_leave,
                container,
                false
        );
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        leaveRecyclerView =
                view.findViewById(R.id.adminLeaveRecyclerView);

        leaveRecyclerView.setLayoutManager(
                new LinearLayoutManager(requireContext())
        );

        loadPendingLeaves();
    }

    private void loadPendingLeaves() {

        Call<List<LeaveResponse>> call =
                RetrofitClient
                        .getApiService()
                        .getPendingLeaves();

        call.enqueue(new Callback<List<LeaveResponse>>() {

            @Override
            public void onResponse(
                    @NonNull Call<List<LeaveResponse>> call,
                    @NonNull Response<List<LeaveResponse>> response) {

                if (response.isSuccessful() && response.body() != null) {

                    List<LeaveResponse> leaveList =
                            response.body();

                    AdminLeaveAdapter adapter =
                            new AdminLeaveAdapter(
                                    leaveList,
                                    new AdminLeaveAdapter.OnLeaveActionListener() {

                                        @Override
                                        public void onApprove(
                                                LeaveResponse leave) {

                                            updateLeaveStatus(
                                                    leave,
                                                    "APPROVED"
                                            );
                                        }

                                        @Override
                                        public void onReject(
                                                LeaveResponse leave) {

                                            updateLeaveStatus(
                                                    leave,
                                                    "REJECTED"
                                            );
                                        }
                                    }
                            );

                    leaveRecyclerView.setAdapter(adapter);

                } else {

                    Toast.makeText(
                            requireContext(),
                            "Failed to load leaves",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

            @Override
            public void onFailure(
                    @NonNull Call<List<LeaveResponse>> call,
                    @NonNull Throwable t) {

                Toast.makeText(
                        requireContext(),
                        "Error: " + t.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    private void updateLeaveStatus(
            LeaveResponse leave,
            String status) {

        LeaveStatusRequest request =
                new LeaveStatusRequest(status);

        Call<LeaveResponse> call =
                RetrofitClient
                        .getApiService()
                        .updateLeaveStatus(
                                leave.getLeaveId(),
                                request
                        );

        call.enqueue(new Callback<LeaveResponse>() {

            @Override
            public void onResponse(
                    @NonNull Call<LeaveResponse> call,
                    @NonNull Response<LeaveResponse> response) {

                if (response.isSuccessful()) {

                    Toast.makeText(
                            requireContext(),
                            "Leave " + status.toLowerCase(),
                            Toast.LENGTH_SHORT
                    ).show();

                    // Reload pending leaves
                    loadPendingLeaves();

                } else {

                    Toast.makeText(
                            requireContext(),
                            "Failed to update leave",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

            @Override
            public void onFailure(
                    @NonNull Call<LeaveResponse> call,
                    @NonNull Throwable t) {

                Toast.makeText(
                        requireContext(),
                        "Error: " + t.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }
}