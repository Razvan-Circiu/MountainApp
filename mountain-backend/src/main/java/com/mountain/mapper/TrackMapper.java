package com.mountain.mapper;

import com.mountain.dto.TrackDTO;
import com.mountain.model.Track;

public class TrackMapper {

    public static TrackDTO toDto(Track track) {
        TrackDTO dto = new TrackDTO();
        dto.setId(track.getId());
        dto.setName(track.getName());
        dto.setDescription(track.getDescription());
        if (track.getUser() != null) {
            dto.setUserId(track.getUser().getId());
        }
        return dto;
    }

    public static Track fromDto(TrackDTO dto) {
        Track track = new Track();
        track.setName(dto.getName());
        track.setDescription(dto.getDescription());
        return track;
    }
}
