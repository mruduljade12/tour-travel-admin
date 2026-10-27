package com.mrudul.tourandtraveladmin.network;

import com.mrudul.tourandtraveladmin.model.BusRequest;
import com.mrudul.tourandtraveladmin.model.DestinationRequest;
import com.mrudul.tourandtraveladmin.model.DriverListRequest;
import com.mrudul.tourandtraveladmin.model.DriverModel;
import com.mrudul.tourandtraveladmin.model.GuideModel;
import com.mrudul.tourandtraveladmin.model.LeaveRequest;
import com.mrudul.tourandtraveladmin.model.LeaveResponse;
import com.mrudul.tourandtraveladmin.model.LeaveStatusRequest;
import com.mrudul.tourandtraveladmin.model.LoginRequest;
import com.mrudul.tourandtraveladmin.model.LoginResponse;
import com.mrudul.tourandtraveladmin.model.TourGuideListRequest;
import com.mrudul.tourandtraveladmin.model.TourPackageRequestModel;
import com.mrudul.tourandtraveladmin.model.TourPackageResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    // login api
    @POST("api/admin/login")
    Call<LoginResponse> login(@Body LoginRequest request);


    // tour guide api
    @GET("api/tour_guides")
    Call<List<TourGuideListRequest>> getTourGuides();

    @POST("api/tour_guides")
    Call<TourGuideListRequest> addTourGuide(
            @Body TourGuideListRequest request
    );

    // driver api
    @GET("api/drivers")
    Call<List<DriverListRequest>> getDrivers();

    @POST("api/drivers")
    Call<DriverListRequest> addDriver(
            @Body DriverListRequest request
    );


    // bus api
    @GET("api/buses")
    Call<List<BusRequest>> getBus();

    @POST("api/buses")
    Call<BusRequest> addBus(
            @Body BusRequest request
    );


    // destination api
    @POST("api/destinations")
    Call<DestinationRequest> addDestination(@Body DestinationRequest request);

    @GET("api/destinations")
    Call<List<DestinationRequest>> getDestinations();



    // package creation apis
    @GET("api/drivers")
    Call<List<DriverModel>> getPackageDrivers();

    @GET("api/tour_guides")
    Call<List<GuideModel>> getPackageGuides();

    @POST("api/packages")
    Call<TourPackageResponseModel> addPackage(
            @Body TourPackageRequestModel request
    );


    // leave api
    @POST("api/leaves")
    Call<LeaveResponse> applyLeave(@Body LeaveRequest request);

    @GET("api/leaves/pending")
    Call<List<LeaveResponse>> getPendingLeaves();

    @PUT("api/leaves/{leaveId}/status")
    Call<LeaveResponse> updateLeaveStatus(
            @Path("leaveId") Integer leaveId,
            @Body LeaveStatusRequest request
    );
}
