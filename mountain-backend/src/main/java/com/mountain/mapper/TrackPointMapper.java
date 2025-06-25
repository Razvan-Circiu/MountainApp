package com.mountain.mapper;

import com.mountain.dto.TrackPointDTO;
import com.mountain.model.TrackPoint;

public class TrackPointMapper {
    public static TrackPoint toEntity(TrackPointDTO dto) {
        TrackPoint point = new TrackPoint();
        point.setLatitude(dto.getLatitude());
        point.setLongitude(dto.getLongitude());
        point.setElevation(dto.getElevation());
        point.setTime(dto.getTime());
        return point;
    }

    public static TrackPointDTO toDTO(TrackPoint point) {
        TrackPointDTO dto = new TrackPointDTO();
        dto.setLatitude(point.getLatitude());
        dto.setLongitude(point.getLongitude());
        dto.setElevation(point.getElevation());
        dto.setTime(point.getTime());
        return dto;
    }
}
