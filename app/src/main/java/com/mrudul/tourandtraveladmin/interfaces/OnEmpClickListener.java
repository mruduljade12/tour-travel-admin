package com.mrudul.tourandtraveladmin.interfaces;

import com.mrudul.tourandtraveladmin.model.DriverListRequest;
import com.mrudul.tourandtraveladmin.model.TourGuideListRequest;

public interface OnEmpClickListener {
    void OnEmpClickDriver(DriverListRequest data);
    void OnEmpClickTourGuide(TourGuideListRequest data);
}
