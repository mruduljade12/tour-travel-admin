package com.mrudul.tourandtraveladmin.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentContainerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.mrudul.tourandtraveladmin.R;
import com.mrudul.tourandtraveladmin.fragments.AdminLeaveFragment;
import com.mrudul.tourandtraveladmin.fragments.EmployeeListFragment;
import com.mrudul.tourandtraveladmin.fragments.HomeFragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int itemId = menuItem.getItemId();

                if (itemId == R.id.home) {

                    fragmentNavigation(new HomeFragment());
                    return true;

                } else if (itemId == R.id.employee_list) {

                    fragmentNavigation(new EmployeeListFragment());
                    return true;

                } else if ((itemId == R.id.leave)){
                    fragmentNavigation(new AdminLeaveFragment());
                    return true;
                }

                return false;
            }
        });
    }


    public void fragmentNavigation(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(
                        R.id.fragmentContainerView,
                        fragment
                )
                .commit();
    }
}