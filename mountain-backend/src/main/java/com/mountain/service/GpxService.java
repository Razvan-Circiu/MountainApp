package com.mountain.service;

import com.mountain.model.Track;
import com.mountain.model.TrackPoint;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import com.mountain.model.User;

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

    public Track parseGpx(InputStream inputStream, User user) {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);
            doc.getDocumentElement().normalize();

            NodeList trkptNodes = doc.getElementsByTagName("trkpt");
            List<TrackPoint> points = new ArrayList<>();

            for (int i = 0; i < trkptNodes.getLength(); i++) {
                Element trkpt = (Element) trkptNodes.item(i);
                double lat = Double.parseDouble(trkpt.getAttribute("lat"));
                double lon = Double.parseDouble(trkpt.getAttribute("lon"));

                double ele = 0;
                String time = "";

                NodeList children = trkpt.getChildNodes();
                for (int j = 0; j < children.getLength(); j++) {
                    Node child = children.item(j);
                    if (child.getNodeName().equals("ele")) {
                        ele = Double.parseDouble(child.getTextContent());
                    } else if (child.getNodeName().equals("time")) {
                        time = child.getTextContent();
                    }
                }

                TrackPoint point = new TrackPoint();
                point.setLatitude(lat);
                point.setLongitude(lon);
                point.setElevation(ele);
                point.setTime(time);
                points.add(point);
            }

            Track track = new Track();
            track.setName("Traseu din GPX");
            track.setDescription("Importat din fișier GPX");
            track.setUser(user);
            track.setPoints(points);
            points.forEach(p -> p.setTrack(track));

            return track;
        } catch (Exception e) {
            throw new RuntimeException("Eroare la parsarea fișierului GPX", e);
        }
    }


}
