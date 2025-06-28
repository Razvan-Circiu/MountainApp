package com.mountain.service;

import com.mountain.model.Track;
import com.mountain.model.TrackPoint;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class GpxService {

    // Metoda care generează fișierul GPX pentru un track
    public byte[] generateGpx(Track track) {
        StringBuilder gpx = new StringBuilder();
        gpx.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        gpx.append("<gpx version=\"1.1\" creator=\"MountainApp\">\n");
        gpx.append("  <trk><name>").append(track.getName()).append("</name><trkseg>\n");

        // Iterează prin punctele track-ului pentru a adăuga trkpt
        List<TrackPoint> points = track.getPoints();
        for (TrackPoint point : points) {
            gpx.append("    <trkpt lat=\"")
                .append(point.getLatitude())
                .append("\" lon=\"")
                .append(point.getLongitude())
                .append("\">\n");
            gpx.append("      <ele>").append(point.getElevation()).append("</ele>\n");
            gpx.append("    </trkpt>\n");
        }

        gpx.append("  </trkseg></trk>\n");
        gpx.append("</gpx>");

        // Convertește StringBuilder în array de byte
        return gpx.toString().getBytes(StandardCharsets.UTF_8);
    }
}
