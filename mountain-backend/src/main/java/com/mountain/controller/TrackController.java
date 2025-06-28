package com.mountain.controller;

import com.mountain.model.Track;
import com.mountain.service.GpxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrackController {

    @Autowired
    private GpxService gpxService;

    // Endpoint pentru a descărca fișierul GPX pentru un track specific
    @GetMapping("/api/track/{trackId}/download-gpx")
    public ResponseEntity<byte[]> downloadGpx(@PathVariable Long trackId) {
        try {
            // Obține track-ul din baza de date (poți modifica implementarea în funcție de necesități)
            Track track = findTrackById(trackId);

            // Generează fișierul GPX
            byte[] gpxFile = gpxService.generateGpx(track);

            // Setează antetele corespunzătoare pentru descărcarea fișierului GPX
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=track_" + trackId + ".gpx")
                    .header(HttpHeaders.CONTENT_TYPE, "application/gpx+xml")
                    .body(gpxFile);
        } catch (Exception e) {
            // În cazul unei erori la generarea fișierului GPX
            return ResponseEntity.status(500).build();
        }
    }

    // Metodă de căutare a track-ului în baza de date (exemplu simplu)
    private Track findTrackById(Long trackId) {
        // Trebuie să implementezi logica de acces la baza de date (de exemplu, folosind un repository)
        // return trackRepository.findById(trackId).orElseThrow(() -> new TrackNotFoundException(trackId));
        return new Track();  // Exemplu, înlocuiește cu logica reală
    }
}
