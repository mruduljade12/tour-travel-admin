package com.mrudul.tourandtraveladmin.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mrudul.tourandtraveladmin.R;
import com.mrudul.tourandtraveladmin.adapters.DestinationSelectionAdapter;
import com.mrudul.tourandtraveladmin.model.BusRequest;
import com.mrudul.tourandtraveladmin.model.DestinationRequest;
import com.mrudul.tourandtraveladmin.model.DestinationSelectionModel;
import com.mrudul.tourandtraveladmin.model.DriverListRequest;
import com.mrudul.tourandtraveladmin.model.DriverModel;
import com.mrudul.tourandtraveladmin.model.GuideModel;
import com.mrudul.tourandtraveladmin.model.PackageDestinationModel;
import com.mrudul.tourandtraveladmin.model.TourGuideListRequest;
import com.mrudul.tourandtraveladmin.model.TourPackageRequestModel;
import com.mrudul.tourandtraveladmin.model.TourPackageResponseModel;
import com.mrudul.tourandtraveladmin.network.ApiService;
import com.mrudul.tourandtraveladmin.network.RetrofitClient;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    CardView addTourGuide,addDriver,addBus,addDestination,addPackage;

    private final List<DestinationSelectionModel> selectedDestinationList = new ArrayList<>();

    private DestinationSelectionAdapter destinationAdapter;

    private Uri selectedImageUri;
    ImageView tourGuideImg, driverImg, destinationImg;
    ImageView ivPackageThumbnail;
    private final ActivityResultLauncher<String> imagePickerTourGuide =
            registerForActivityResult(
                    new ActivityResultContracts.GetContent(),
                    uri -> {
                        if (uri != null) {
                            selectedImageUri = uri;
                            tourGuideImg.setImageURI(uri);
                        }
                    });

    private final ActivityResultLauncher<String> imagePickerDriver =
            registerForActivityResult(
                    new ActivityResultContracts.GetContent(),
                    uri -> {
                        if (uri != null) {
                            selectedImageUri = uri;
                            destinationImg.setImageURI(uri);
                        }
                    });

    private final ActivityResultLauncher<String> destinationImagePicker =
            registerForActivityResult(
                    new ActivityResultContracts.GetContent(),
                    uri -> {
                        if (uri != null) {
                            selectedImageUri = uri;
                            driverImg.setImageURI(uri);
                        }
                    });


    private final ActivityResultLauncher<String> packageImagePicker =
            registerForActivityResult(
                    new ActivityResultContracts.GetContent(),
                    uri -> {
                        if (uri != null) {
                            selectedImageUri = uri;
                            ivPackageThumbnail.setImageURI(uri);
                        }
                    });

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addDriver = view.findViewById(R.id.addDriverCard);
        addTourGuide = view.findViewById(R.id.addTourGuideCard);
        addBus = view.findViewById(R.id.addBusCard);
        addDestination = view.findViewById(R.id.addDestinationCard);
        addPackage = view.findViewById(R.id.addPackageCard);



        addTourGuide.setOnClickListener(v->{
            setAddTourGuide();
        });


        addDriver.setOnClickListener(v->{
            setAddDriver();
        });

        addBus.setOnClickListener(v->{
            setAddBus();
        });

        addDestination.setOnClickListener(v->{
            setAddDestination();
        });

        addPackage.setOnClickListener(v->{
            showAddPackageDialog();
        });
    }

    private void setAddTourGuide(){

        // firebase storage of image
        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference storageReference = storage.getReference().child("tour_guides").child(UUID.randomUUID().toString() + ".jpg");


        // dialog code
        Dialog dialog = new Dialog(requireContext());

        dialog.setContentView(R.layout.add_tour_guide_dialog);

        Window window = dialog.getWindow();

        if (window != null) {
            window.setBackgroundDrawableResource(
                    android.R.color.transparent
            );

            window.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );

            window.setGravity(Gravity.CENTER);
        }


        tourGuideImg = dialog.findViewById(R.id.tourGuideImg);
        EditText tourGuideName = dialog.findViewById(R.id.tourGuideName);
        EditText tourGuideEmail = dialog.findViewById(R.id.tourGuideEmail);
        EditText tourGuidePhone = dialog.findViewById(R.id.tourGuidePhone);
        EditText tourGuideLanguage = dialog.findViewById(R.id.tourGuideLanguage);
        EditText tourGuideAddress = dialog.findViewById(R.id.tourGuideAddress);
        AppCompatButton tourGuideSubmitBtn = dialog.findViewById(R.id.tourGuideSubmitBtn);

        tourGuideImg.setOnClickListener(v -> {
            imagePickerTourGuide.launch("image/*");
        });


        tourGuideSubmitBtn.setOnClickListener(view -> {

            String name = tourGuideName.getText().toString().trim();
            String email = tourGuideEmail.getText().toString().trim();
            String phone = tourGuidePhone.getText().toString().trim();
            String language = tourGuideLanguage.getText().toString().trim();
            String address = tourGuideAddress.getText().toString().trim();
            LocalDate joiningDate = LocalDate.now();
            String joiningDateString = joiningDate.toString();

            if (name.isEmpty()) {

                tourGuideName.setError("Enter full name");
                tourGuideName.requestFocus();
                return;
            }

            if (email.isEmpty()) {

                tourGuideEmail.setError("Enter email address");
                tourGuideEmail.requestFocus();
                return;
            }

            if (phone.isEmpty()) {

                tourGuidePhone.setError("Enter phone number");
                tourGuidePhone.requestFocus();
                return;
            }

            if (phone.length() != 10) {

                tourGuidePhone.setError(
                        "Enter valid 10-digit phone number"
                );

                tourGuidePhone.requestFocus();
                return;
            }

            if (language.isEmpty()) {

                tourGuideLanguage.setError("Enter language");
                tourGuideLanguage.requestFocus();
                return;
            }

            if (address.isEmpty()) {

                tourGuideAddress.setError("Enter address");
                tourGuideAddress.requestFocus();
                return;
            }

            if (selectedImageUri == null) {
                Toast.makeText(requireContext(),
                        "Please select a profile image",
                        Toast.LENGTH_SHORT).show();
                return;
            }


            storageReference.putFile(selectedImageUri)
                    .addOnSuccessListener(taskSnapshot -> {

                        Log.d("TOUR_GUIDE", "IMAGE UPLOAD SUCCESS");

                        storageReference.getDownloadUrl()
                                .addOnSuccessListener(uri -> {

                                    String imageUrl = uri.toString();

                                    Log.d("TOUR_GUIDE", "IMAGE URL = " + imageUrl);

                                    TourGuideListRequest request =
                                            new TourGuideListRequest(
                                                    name,
                                                    email,
                                                    phone,
                                                    language,
                                                    joiningDateString,
                                                    address,
                                                    imageUrl
                                            );

                                    Log.d("TOUR_GUIDE", "SENDING REQUEST TO SPRING");

                                    addTourGuideToServer(request);
                                    dialog.dismiss();

                                })
                                .addOnFailureListener(e -> {
                                    Log.e("TOUR_GUIDE",
                                            "FAILED TO GET IMAGE URL", e);
                                });

                    })
                    .addOnFailureListener(e -> {
                        Log.e("TOUR_GUIDE",
                                "IMAGE UPLOAD FAILED", e);
                    });
        });


        dialog.show();


        if (dialog.getWindow() != null) {

            dialog.getWindow().setLayout(
                    (int) (
                            getResources()
                                    .getDisplayMetrics()
                                    .widthPixels * 0.90
                    ),
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }
    }

    private void addTourGuideToServer(TourGuideListRequest request) {

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        Call<TourGuideListRequest> call = apiService.addTourGuide(request);

        call.enqueue(new Callback<TourGuideListRequest>() {

            @Override
            public void onResponse(
                    Call<TourGuideListRequest> call,
                    Response<TourGuideListRequest> response) {

                Log.d("TOUR_GUIDE",
                        "SPRING RESPONSE CODE = " + response.code());

                if (response.isSuccessful()) {

                    Log.d("TOUR_GUIDE",
                            "TOUR GUIDE SAVED SUCCESSFULLY");

                    Toast.makeText(
                            requireContext(),
                            "Tour Guide Added",
                            Toast.LENGTH_SHORT
                    ).show();

                } else {

                    Log.e("TOUR_GUIDE",
                            "SPRING ERROR = " + response.errorBody());
                }
            }

            @Override
            public void onFailure(
                    Call<TourGuideListRequest> call,
                    Throwable t) {

                Log.e("TOUR_GUIDE",
                        "RETROFIT FAILED", t);
            }
        });
    }

    private void setAddDriver() {

        // firebase storage of image
        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference storageReference = storage.getReference().child("drivers").child(UUID.randomUUID().toString() + ".jpg");


        // dialog code
        Dialog dialog = new Dialog(requireContext());

        dialog.setContentView(R.layout.dialog_add_driver);

        Window window = dialog.getWindow();

        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setGravity(Gravity.CENTER);
        }

        dialog.show();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(
                    (int) (getResources().getDisplayMetrics().widthPixels * 0.90),
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }

        // Find views
        driverImg = dialog.findViewById(R.id.driverImg);
        EditText driverName = dialog.findViewById(R.id.driverName);
        EditText driverEmail = dialog.findViewById(R.id.driverEmail);
        EditText driverPhone = dialog.findViewById(R.id.driverPhone);
        EditText driverLicenseNumber = dialog.findViewById(R.id.driverLicenseNumber);
        EditText driverAddress = dialog.findViewById(R.id.driverAddress);
        Button driverSubmitBtn = dialog.findViewById(R.id.driverSubmitBtn);


        // Image selection
        driverImg.setOnClickListener(v -> {
            imagePickerDriver.launch("image/*");
        });


        // Submit button
        driverSubmitBtn.setOnClickListener(v -> {

            String name = driverName.getText().toString().trim();
            String email = driverEmail.getText().toString().trim();
            String phone = driverPhone.getText().toString().trim();
            String licenseNumber = driverLicenseNumber.getText().toString().trim();
            String address = driverAddress.getText().toString().trim();
            String joiningDate = LocalDate.now().toString();


            // Basic validation
            if (name.isEmpty()) {
                driverName.setError("Enter driver name");
                driverName.requestFocus();
                return;
            }

            if (email.isEmpty()) {
                driverEmail.setError("Enter email address");
                driverEmail.requestFocus();
                return;
            }

            if (phone.isEmpty()) {
                driverPhone.setError("Enter phone number");
                driverPhone.requestFocus();
                return;
            }

            if (licenseNumber.isEmpty()) {
                driverLicenseNumber.setError("Enter license number");
                driverLicenseNumber.requestFocus();
                return;
            }

            if (address.isEmpty()) {
                driverAddress.setError("Enter address");
                driverAddress.requestFocus();
                return;
            }


            storageReference
                    .putFile(selectedImageUri)

                    .addOnSuccessListener(taskSnapshot -> {

                        Log.d(
                                "DRIVER",
                                "IMAGE UPLOAD SUCCESS"
                        );


                        // Get Firebase URL
                        storageReference
                                .getDownloadUrl()
                                .addOnSuccessListener(uri -> {
                                    String imageUrl = uri.toString();


                                    DriverListRequest request = new DriverListRequest(name, email, phone, licenseNumber, joiningDate, address, imageUrl);
                                    addDriverToServer(request);
                                    dialog.dismiss();

                                })

                                .addOnFailureListener(e -> {

                                    Log.e(
                                            "DRIVER",
                                            "FAILED TO GET IMAGE URL",
                                            e
                                    );

                                    Toast.makeText(
                                            requireContext(),
                                            "Failed to get image URL",
                                            Toast.LENGTH_SHORT
                                    ).show();

                                });

                    })

                    .addOnFailureListener(e -> {

                        Log.e(
                                "DRIVER",
                                "IMAGE UPLOAD FAILED",
                                e
                        );

                        Toast.makeText(
                                requireContext(),
                                "Image upload failed",
                                Toast.LENGTH_SHORT
                        ).show();

                    });
        });
    }

    private void addDriverToServer(DriverListRequest request) {

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        Call<DriverListRequest> call = apiService.addDriver(request);

        call.enqueue(new Callback<DriverListRequest>() {

            @Override
            public void onResponse(
                    Call<DriverListRequest> call,
                    Response<DriverListRequest> response) {

                Log.d("TOUR_GUIDE",
                        "SPRING RESPONSE CODE = " + response.code());

                if (response.isSuccessful()) {

                    Log.d("TOUR_GUIDE",
                            "TOUR GUIDE SAVED SUCCESSFULLY");

                    Toast.makeText(
                            requireContext(),
                            "Tour Guide Added",
                            Toast.LENGTH_SHORT
                    ).show();

                } else {

                    Log.e("TOUR_GUIDE",
                            "SPRING ERROR = " + response.errorBody());
                }
            }

            @Override
            public void onFailure(
                    Call<DriverListRequest> call,
                    Throwable t) {

                Log.e("TOUR_GUIDE",
                        "RETROFIT FAILED", t);
            }
        });
    }

    private void setAddBus(){
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.dialog_add_bus);

        Window window = dialog.getWindow();

        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setGravity(Gravity.CENTER);
        }

        dialog.show();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(
                    (int) (getResources().getDisplayMetrics().widthPixels * 0.90),
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }

        // Find Views
        EditText busNumber = dialog.findViewById(R.id.busNumber);
        EditText busName = dialog.findViewById(R.id.busName);
        EditText busType = dialog.findViewById(R.id.busType);
        EditText busCapacity = dialog.findViewById(R.id.busCapacity);

        Button busSubmitBtn = dialog.findViewById(R.id.busSubmitBtn);

        // Submit Button
        busSubmitBtn.setOnClickListener(v -> {

            String number = busNumber.getText().toString().trim();
            String name = busName.getText().toString().trim();
            String type = busType.getText().toString().trim();
            String capacityText = busCapacity.getText().toString().trim();

            // Validation
            if (number.isEmpty()) {
                busNumber.setError("Enter bus number");
                busNumber.requestFocus();
                return;
            }

            if (name.isEmpty()) {
                busName.setError("Enter bus name");
                busName.requestFocus();
                return;
            }

            if (type.isEmpty()) {
                busType.setError("Enter bus type");
                busType.requestFocus();
                return;
            }

            if (capacityText.isEmpty()) {
                busCapacity.setError("Enter bus capacity");
                busCapacity.requestFocus();
                return;
            }

            int capacity;

            try {
                capacity = Integer.parseInt(capacityText);
            } catch (NumberFormatException e) {
                busCapacity.setError("Enter a valid capacity");
                busCapacity.requestFocus();
                return;
            }

            // Create request
            BusRequest busRequest = new BusRequest(
                    number,
                    name,
                    type,
                    capacity
            );

            // Send to Spring
            addBusToSpring(busRequest);
            dialog.dismiss();
        });
    }

    private void addBusToSpring(BusRequest request) {

        ApiService apiService = RetrofitClient
                .getRetrofitInstance()
                .create(ApiService.class);

        apiService.addBus(request).enqueue(new Callback<BusRequest>() {

            @Override
            public void onResponse(
                    Call<BusRequest> call,
                    Response<BusRequest> response) {

                if (response.isSuccessful()) {

                    Toast.makeText(
                            requireContext(),
                            "Bus added successfully",
                            Toast.LENGTH_SHORT
                    ).show();


                } else {

                    Toast.makeText(
                            requireContext(),
                            "Failed to add bus. Code: " + response.code(),
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

            @Override
            public void onFailure(
                    Call<BusRequest> call,
                    Throwable t) {

                Toast.makeText(
                        requireContext(),
                        "Network error: " + t.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    private void setAddDestination() {
        // firebase storage of image
        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference storageReference = storage.getReference().child("destinations").child(UUID.randomUUID().toString() + ".jpg");


        // dialog code

        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.dialog_add_destination);

        Window window = dialog.getWindow();

        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setGravity(Gravity.CENTER);
        }

        dialog.show();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(
                    (int) (getResources().getDisplayMetrics().widthPixels * 0.90),
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }

        destinationImg = dialog.findViewById(R.id.destinationImg);
        EditText destinationName = dialog.findViewById(R.id.destinationName);
        EditText destinationCity = dialog.findViewById(R.id.destinationCity);
        EditText destinationState = dialog.findViewById(R.id.destinationState);
        EditText destinationCountry = dialog.findViewById(R.id.destinationCountry);
        EditText destinationDescription = dialog.findViewById(R.id.destinationDescription);
        EditText destinationLatitude = dialog.findViewById(R.id.destinationLatitude);
        EditText destinationLongitude = dialog.findViewById(R.id.destinationLongitude);
        Button destinationSubmitBtn = dialog.findViewById(R.id.destinationSubmitBtn);



        destinationImg.setOnClickListener(v -> {
            destinationImagePicker.launch("image/*");
        });



        destinationSubmitBtn.setOnClickListener(v -> {

            String name = destinationName.getText().toString().trim();
            String city = destinationCity.getText().toString().trim();
            String state = destinationState.getText().toString().trim();
            String country = destinationCountry.getText().toString().trim();
            String description = destinationDescription.getText().toString().trim();
            String latitude = destinationLatitude.getText().toString().trim();
            String longitude = destinationLongitude.getText().toString().trim();


            if (selectedImageUri == null) {
                Toast.makeText(
                        requireContext(),
                        "Please select destination image",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            if (name.isEmpty()) {
                destinationName.setError("Enter destination name");
                destinationName.requestFocus();
                return;
            }

            if (city.isEmpty()) {
                destinationCity.setError("Enter city");
                destinationCity.requestFocus();
                return;
            }

            if (state.isEmpty()) {
                destinationState.setError("Enter state");
                destinationState.requestFocus();
                return;
            }

            if (country.isEmpty()) {
                destinationCountry.setError("Enter country");
                destinationCountry.requestFocus();
                return;
            }

            if (description.isEmpty()) {
                destinationDescription.setError("Enter description");
                destinationDescription.requestFocus();
                return;
            }

            if (latitude.isEmpty()) {
                destinationLatitude.setError("Enter latitude");
                destinationLatitude.requestFocus();
                return;
            }

            if (longitude.isEmpty()) {
                destinationLongitude.setError("Enter longitude");
                destinationLongitude.requestFocus();
                return;
            }


            storageReference
                    .putFile(selectedImageUri)
                    .addOnSuccessListener(taskSnapshot -> {

                        Toast.makeText(
                                requireContext(),
                                "Image uploaded successfully",
                                Toast.LENGTH_SHORT
                        ).show();


                        // Get Firebase download URL

                        storageReference.getDownloadUrl()
                                .addOnSuccessListener(uri -> {

                                    String imageUrl =
                                            uri.toString();

                                    addDestinationToServer(
                                            name,
                                            city,
                                            state,
                                            country,
                                            description,
                                            latitude,
                                            longitude,
                                            imageUrl
                                    );

                                    dialog.dismiss();

                                })
                                .addOnFailureListener(e -> {

                                    Toast.makeText(
                                            requireContext(),
                                            "Failed to get image URL",
                                            Toast.LENGTH_SHORT
                                    ).show();
                                });

                    })
                    .addOnFailureListener(e -> {

                        Toast.makeText(
                                requireContext(),
                                "Image upload failed",
                                Toast.LENGTH_SHORT
                        ).show();
                    });


        });
    }

    private void addDestinationToServer(String name, String city, String state, String country, String description, String latitude, String longitude,String imageUrl) {
        DestinationRequest request =
                new DestinationRequest();

        request.setName(name);
        request.setCity(city);
        request.setState(state);
        request.setCountry(country);
        request.setDescription(description);
        request.setLatitude(latitude);
        request.setLongitude(longitude);
        request.setImage(imageUrl);


        ApiService apiService =
                RetrofitClient
                        .getRetrofitInstance()
                        .create(ApiService.class);


        apiService.addDestination(request)
                .enqueue(new Callback<DestinationRequest>() {

                    @Override
                    public void onResponse(
                            Call<DestinationRequest> call,
                            Response<DestinationRequest> response) {

                        if (response.isSuccessful()) {

                            Toast.makeText(
                                    requireContext(),
                                    "Destination added successfully",
                                    Toast.LENGTH_SHORT
                            ).show();

                        } else {

                            Toast.makeText(
                                    requireContext(),
                                    "Failed to add destination: "
                                            + response.code(),
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<DestinationRequest> call,
                            Throwable t) {

                        Toast.makeText(
                                requireContext(),
                                "Network error: "
                                        + t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });

    }



    private void showAddPackageDialog() {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference reference = storage.getReference().child("packages").child(UUID.randomUUID().toString() + ".jpg");


        Dialog dialog = new Dialog(requireContext());

        dialog.setContentView(R.layout.dialog_add_package);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(
                    android.R.color.transparent
            );

            dialog.getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }

        // Views
        ivPackageThumbnail = dialog.findViewById(R.id.ivPackageThumbnail);
        EditText etPackName = dialog.findViewById(R.id.etPackName);
        EditText etPackDescription = dialog.findViewById(R.id.etPackDescription);
        EditText etDurationDay = dialog.findViewById(R.id.etDurationDay);
        EditText etDurationNight = dialog.findViewById(R.id.etDurationNight);
        EditText etPackPrice = dialog.findViewById(R.id.etPackPrice);
        EditText etMaxCapacity = dialog.findViewById(R.id.etMaxCapacity);
        Spinner spPackStatus = dialog.findViewById(R.id.spPackStatus);
        EditText etStartDate = dialog.findViewById(R.id.etStartDate);
        Spinner spDriver = dialog.findViewById(R.id.spDriver);
        Spinner spGuide = dialog.findViewById(R.id.spGuide);
        Button btnSelectDestinations = dialog.findViewById(R.id.btnSelectDestinations);
        TextView tvSelectedDestinations = dialog.findViewById(R.id.tvSelectedDestinations);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnAddPackage = dialog.findViewById(R.id.btnAddPackage);


        // driver spinner
        List<DriverModel> driverList = new ArrayList<>();

        List<String> driverNames = new ArrayList<>();

        driverNames.add("Select Driver");

        ArrayAdapter<String> driverAdapter =
                new ArrayAdapter<>(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        driverNames
                );

        driverAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spDriver.setAdapter(driverAdapter);

        ApiService apiService = RetrofitClient.getApiService();

        apiService.getPackageDrivers().enqueue(new Callback<List<DriverModel>>() {

                    @Override
                    public void onResponse(
                            Call<List<DriverModel>> call, Response<List<DriverModel>> response) {

                        if (response.isSuccessful() && response.body() != null) {

                            driverList.clear();

                            driverList.addAll(response.body());

                            driverNames.clear();

                            driverNames.add("Select Driver");

                            for (DriverModel driver : driverList) {

                                driverNames.add(
                                        driver.getDriverName()
                                );
                            }

                            driverAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<List<DriverModel>> call,
                            Throwable t) {

                        Toast.makeText(
                                requireContext(),
                                "Failed to load drivers",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
        );



        // ================= TOUR GUIDE SPINNER =================

        List<GuideModel> guideList = new ArrayList<>();

        List<String> guideNames = new ArrayList<>();

        guideNames.add("Select Tour Guide");

        ArrayAdapter<String> guideAdapter =
                new ArrayAdapter<>(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        guideNames
                );

        guideAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spGuide.setAdapter(guideAdapter);


// Load guides from backend
        ApiService apiService1 = RetrofitClient.getApiService();

        apiService1.getPackageGuides().enqueue(new Callback<List<GuideModel>>() {

            @Override
            public void onResponse(
                    Call<List<GuideModel>> call,
                    Response<List<GuideModel>> response) {

                if (response.isSuccessful()
                        && response.body() != null) {

                    guideList.clear();
                    guideList.addAll(response.body());

                    guideNames.clear();
                    guideNames.add("Select Tour Guide");

                    for (GuideModel guide : guideList) {

                        guideNames.add(
                                guide.getGuideName()
                        );
                    }

                    guideAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(
                    Call<List<GuideModel>> call,
                    Throwable t) {

                Toast.makeText(
                        requireContext(),
                        "Failed to load tour guides",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        // image picker
        ivPackageThumbnail.setOnClickListener(v -> {
            packageImagePicker.launch("image/*");
        });


        // Package status
        String[] status = {
                "available",
                "not available"
        };

        ArrayAdapter<String> statusAdapter =
                new ArrayAdapter<>(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        status
                );

        statusAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spPackStatus.setAdapter(statusAdapter);


        // Start date
        etStartDate.setOnClickListener(v -> {

            Calendar calendar = Calendar.getInstance();

            DatePickerDialog datePickerDialog =
                    new DatePickerDialog(
                            requireContext(),

                            (view, year, month, dayOfMonth) -> {

                                String date =
                                        year + "-" +
                                                String.format("%02d", month + 1) +
                                                "-" +
                                                String.format("%02d", dayOfMonth);

                                etStartDate.setText(date);
                            },

                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                    );

            datePickerDialog.show();
        });


        // Select destinations
        btnSelectDestinations.setOnClickListener(v -> {

            Dialog destinationDialog = new Dialog(requireContext());
            destinationDialog.setContentView(R.layout.dialog_select_destinations);
            RecyclerView rvDestinations = destinationDialog.findViewById(R.id.rvDestinations);
            Button btnDoneDestinations = destinationDialog.findViewById(R.id.btnDoneDestinations);
            rvDestinations.setLayoutManager(new LinearLayoutManager(requireContext()));
            List<DestinationSelectionModel> destinationSelectionList = new ArrayList<>();
            destinationAdapter = new DestinationSelectionAdapter(requireContext(), destinationSelectionList);
            rvDestinations.setAdapter(destinationAdapter);

            // Load destinations from backend
            ApiService apiService2 = RetrofitClient.getApiService();

            apiService2.getDestinations()
                    .enqueue(new Callback<List<DestinationRequest>>() {

                        @Override
                        public void onResponse(
                                Call<List<DestinationRequest>> call, Response<List<DestinationRequest>> response) {

                            if (response.isSuccessful() && response.body() != null) {

                                destinationSelectionList.clear();

                                for (DestinationRequest destination : response.body()) {
                                    destinationSelectionList.add(new DestinationSelectionModel(destination));
                                }

                                destinationAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFailure(
                                Call<List<DestinationRequest>> call,
                                Throwable t) {

                            Toast.makeText(
                                    requireContext(),
                                    "Failed to load destinations",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    });

            btnDoneDestinations.setOnClickListener(view -> {

                selectedDestinationList.clear();

                for (DestinationSelectionModel model
                        : destinationSelectionList) {

                    if (model.isSelected()) {

                        selectedDestinationList.add(model);
                    }
                }

                tvSelectedDestinations.setText(
                        selectedDestinationList.size()
                                + " destination(s) selected"
                );

                destinationDialog.dismiss();
            });

            destinationDialog.show();

            if (destinationDialog.getWindow() != null) {

                destinationDialog.getWindow()
                        .setBackgroundDrawableResource(
                                android.R.color.transparent
                        );

                destinationDialog.getWindow()
                        .setLayout(
                                (int) (
                                        getResources()
                                                .getDisplayMetrics()
                                                .widthPixels * 0.95
                                ),
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        );
            }

        });


        // Cancel
        btnCancel.setOnClickListener(v -> {

            dialog.dismiss();

        });


        // Add package
        btnAddPackage.setOnClickListener(v -> {
            int position = spDriver.getSelectedItemPosition();
            int guidePosition = spGuide.getSelectedItemPosition();

            String packageName = etPackName.getText().toString().trim();
            String description = etPackDescription.getText().toString().trim();
            String days = etDurationDay.getText().toString().trim();
            String nights = etDurationNight.getText().toString().trim();
            String price = etPackPrice.getText().toString().trim();
            String capacity = etMaxCapacity.getText().toString().trim();
            String startDate = etStartDate.getText().toString().trim();
            String packStatus = spPackStatus.getSelectedItem().toString();

            // Validation


            if (position == 0) {

                Toast.makeText(
                        requireContext(),
                        "Please select a driver",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            if (guidePosition == 0) {

                Toast.makeText(
                        requireContext(),
                        "Please select a tour guide",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }


            if (packageName.isEmpty()) {
                etPackName.setError("Enter package name");
                return;
            }

            if (description.isEmpty()) {
                etPackDescription.setError("Enter description");
                return;
            }

            if (days.isEmpty()) {
                etDurationDay.setError("Enter days");
                return;
            }

            if (nights.isEmpty()) {
                etDurationNight.setError("Enter nights");
                return;
            }

            if (price.isEmpty()) {
                etPackPrice.setError("Enter price");
                return;
            }

            if (capacity.isEmpty()) {
                etMaxCapacity.setError("Enter capacity");
                return;
            }

            if (startDate.isEmpty()) {
                etStartDate.setError("Select start date");
                return;
            }

            if (selectedImageUri == null) {

                Toast.makeText(
                        requireContext(),
                        "Please select package thumbnail",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            Integer driverId = driverList.get(position - 1).getDriverId();
            Integer guideId = guideList.get(guidePosition - 1).getGuideId();


            reference
                    .putFile(selectedImageUri)
                    .addOnSuccessListener(taskSnapshot -> {

                        Toast.makeText(
                                requireContext(),
                                "Image uploaded successfully",
                                Toast.LENGTH_SHORT
                        ).show();


                        // Get Firebase download URL

                        reference.getDownloadUrl()
                                .addOnSuccessListener(uri -> {

                                    String imageUrl = uri.toString();

                                    uploadPackageToServer(
                                            packageName,
                                            description,
                                            days,
                                            nights,
                                            price,
                                            capacity,
                                            imageUrl,
                                            packStatus,
                                            startDate,
                                            driverId,
                                            guideId,
                                            selectedDestinationList
                                    );
                                    dialog.dismiss();

                                })
                                .addOnFailureListener(e -> {

                                    Toast.makeText(
                                            requireContext(),
                                            "Failed to get image URL",
                                            Toast.LENGTH_SHORT
                                    ).show();
                                });

                    })
                    .addOnFailureListener(e -> {

                        Toast.makeText(
                                requireContext(),
                                "Image upload failed",
                                Toast.LENGTH_SHORT
                        ).show();
                    });
        });


        dialog.show();

        // Set dialog width AFTER show()
        if (dialog.getWindow() != null) {

            dialog.getWindow().setLayout(
                    (int) (getResources().getDisplayMetrics().widthPixels * 0.95),
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }
    }

    private void uploadPackageToServer(
            String packName,
            String packDescription,
            String durationDay,
            String durationNight,
            String price,
            String maxCapacity,
            String imageUrl,
            String packStatus,
            String startDate,
            Integer driverId,
            Integer guideId,
            List<DestinationSelectionModel> selectedDestinations) {

        // Create request object
        TourPackageRequestModel request =
                new TourPackageRequestModel();

        // Package details
        request.setPackName(packName);
        request.setPackDescription(packDescription);

        request.setPackDurationDay(
                Integer.parseInt(durationDay)
        );

        request.setPackDurationNight(
                Integer.parseInt(durationNight)
        );

        request.setPackPrice(
                Integer.parseInt(price)
        );

        request.setPackMaxCapacity(
                Integer.parseInt(maxCapacity)
        );

        // Firebase image URL
        request.setPackThumbnail(imageUrl);

        // Status and start date
        request.setPackStatus(packStatus);
        request.setPackStartDate(startDate);

        // Driver and guide
        request.setDriverId(driverId);
        request.setGuideId(guideId);


        // -----------------------------------------
        // Convert selected destinations
        // -----------------------------------------

        List<PackageDestinationModel> destinations =
                new ArrayList<>();

        for (DestinationSelectionModel selected
                : selectedDestinations) {

            PackageDestinationModel destination =
                    new PackageDestinationModel();

            DestinationRequest destinationRequest =
                    selected.getSelectionDestination();

            destination.setDestinationId(
                    destinationRequest.getDestinationId()
            );

            destination.setDayNumber(
                    selected.getDayNumber()
            );

            destination.setVisitOrder(
                    selected.getVisitOrder()
            );

            destination.setVisitDate(
                    selected.getVisitDate()
            );

            destinations.add(destination);
        }

        // Set destinations into request
        request.setDestinations(destinations);


        // -----------------------------------------
        // Send request to Spring Boot
        // -----------------------------------------

        ApiService apiService =
                RetrofitClient.getApiService();

        apiService.addPackage(request)
                .enqueue(new Callback<TourPackageResponseModel>() {

                    @Override
                    public void onResponse(
                            Call<TourPackageResponseModel> call,
                            Response<TourPackageResponseModel> response) {

                        if (response.isSuccessful()
                                && response.body() != null) {

                            Toast.makeText(
                                    requireContext(),
                                    "Package added successfully",
                                    Toast.LENGTH_LONG
                            ).show();

                        } else {

                            Toast.makeText(
                                    requireContext(),
                                    "Failed to add package. Code: "
                                            + response.code(),
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<TourPackageResponseModel> call,
                            Throwable t) {

                        Toast.makeText(
                                requireContext(),
                                "Server error: "
                                        + t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }
}