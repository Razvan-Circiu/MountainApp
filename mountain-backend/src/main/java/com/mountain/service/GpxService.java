package com.mountain.service;

import com.mountain.model.Track;
import com.mountain.model.TrackPoint;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class GpxService {

    public byte[] generateGpx(Track track) {
        StringBuilder gpx = new StringBuilder();
        gpx.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        gpx.append("<gpx version=\"1.1\" creator=\"MountainApp\">\n");
        gpx.append("  <trk><name>").append(track.getName()).append("</name><trkseg>\n");

        for (TrackPoint point : track.getPoints()) {
            gpx.append("    <trkpt lat=\"")
                    .append(point.getLatitude())
                    .append("\" lon=\"")
                    .append(point.getLongitude())
                    .append("\">\n")
                    .append("      <ele>").append(point.getElevation()).append("</ele>\n")
                    .append("      <time>").append(point.getTime()).append("</time>\n")
                    .append("    </trkpt>\n");
        }

        gpx.append("  </trkseg></trk>\n");
        gpx.append("</gpx>");

        return gpx.toString().getBytes();
    }

    public void writeTrackAsGpx(Track track, HttpServletResponse response) {
        try {
            byte[] gpxData = generateGpx(track);
            response.setContentType("application/gpx+xml");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + track.getName() + ".gpx\"");
            response.getOutputStream().write(gpxData);
            response.flushBuffer();
        } catch (IOException e) {
            throw new RuntimeException("Eroare la scrierea fișierului GPX", e);
        }
    }
}
