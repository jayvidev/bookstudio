package com.bookstudio.location.application;

import com.bookstudio.location.LocationApi;
import com.bookstudio.location.application.dto.request.CreateLocationRequest;
import com.bookstudio.location.application.dto.request.CreateShelfRequest;
import com.bookstudio.location.application.dto.request.UpdateLocationRequest;
import com.bookstudio.location.application.dto.request.UpdateShelfRequest;
import com.bookstudio.location.application.dto.response.LocationDetailResponse;
import com.bookstudio.location.application.dto.response.LocationListResponse;
import com.bookstudio.location.domain.model.Location;
import com.bookstudio.location.domain.model.Shelf;
import com.bookstudio.location.infrastructure.repository.LocationRepository;
import com.bookstudio.location.infrastructure.repository.ShelfRepository;
import com.bookstudio.shared.exception.ResourceNotFoundException;
import com.bookstudio.shared.response.OptionResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class LocationService implements LocationApi {
    private final LocationRepository locationRepository;
    private final ShelfRepository shelfRepository;

    @Override
    public void requireShelfExists(Long shelfId) {
        if (!shelfRepository.existsById(shelfId)) {
            throw new ResourceNotFoundException("Shelf not found with ID: " + shelfId);
        }
    }

    @Override
    public List<OptionResponse> getShelfOptions() {
        return shelfRepository.findForOptions();
    }

    public List<LocationListResponse> getList() {
        return locationRepository.findList();
    }

    public LocationDetailResponse getDetailById(Long id) {
        LocationDetailResponse base = locationRepository.findDetailById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found with ID: " + id));

        return base.withShelves(shelfRepository.findShelfItemsByLocationId(id));
    }

    @Transactional
    public LocationListResponse create(CreateLocationRequest request) {
        Location location = new Location();
        location.setName(request.name());
        location.setDescription(request.description());

        Location saved = locationRepository.save(location);

        if (request.shelves() != null) {
            for (CreateShelfRequest shelfDto : request.shelves()) {
                Shelf shelf = new Shelf();
                shelf.setCode(shelfDto.code());
                shelf.setFloor(shelfDto.floor());
                shelf.setDescription(shelfDto.description());
                shelf.setLocation(saved);

                shelfRepository.save(shelf);
            }
        }

        return toListResponse(saved);
    }

    @Transactional
    public LocationListResponse update(Long id, UpdateLocationRequest request) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found with ID: " + id));

        location.setName(request.name());
        location.setDescription(request.description());

        for (UpdateShelfRequest shelfRequest : request.shelves()) {
            Shelf shelf = shelfRepository.findById(shelfRequest.id())
                    .orElseThrow(
                            () -> new ResourceNotFoundException(
                                    "Shelf not found with ID: " + shelfRequest.id()));

            shelf.setCode(shelfRequest.code());
            shelf.setFloor(shelfRequest.floor());
            shelf.setDescription(shelfRequest.description());
        }

        return toListResponse(location);
    }

    private LocationListResponse toListResponse(Location location) {
        return locationRepository.findListItemById(location.getId()).orElseThrow();
    }
}
