package com.hmarka.kloop1996.transferelegance.model;

import java.util.List;

/**
 * Created by kloop1996 on 14.06.2016.
 */
public class ResponseRoute {

    public List<Route> routes;

    public String getPoints() {
        return this.routes.get(0).overview_polyline.points;
    }

    class Route {
        OverviewPolyline overview_polyline;
    }

    class OverviewPolyline {
        String points;
    }
}
