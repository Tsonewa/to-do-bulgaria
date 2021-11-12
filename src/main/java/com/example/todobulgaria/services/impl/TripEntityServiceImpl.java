package com.example.todobulgaria.services.impl;

import com.example.todobulgaria.models.dto.TripsDto;
import com.example.todobulgaria.models.entities.*;
import com.example.todobulgaria.models.enums.CategoryEnum;
import com.example.todobulgaria.models.service.AddTripServiceModel;
import com.example.todobulgaria.models.views.DetailsEntityViewModel;
import com.example.todobulgaria.models.views.ItinariesDetailsViewModel;
import com.example.todobulgaria.models.views.TripDetailsView;
import com.example.todobulgaria.models.views.TripsArticleViewModel;
import com.example.todobulgaria.repositories.PictureRepository;
import com.example.todobulgaria.repositories.TripRepository;
import com.example.todobulgaria.services.*;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TripEntityServiceImpl implements TripEntityService {

    private final ModelMapper modelMapper;
    private final TripRepository tripRepository;
    private final CategoryEntityService categoryEntityService;
    private final TownEntityService townEntityService;
    private final AttractionEntityService attractionEntityService;
    private final DetailsEntityService detailsEntityService;
    private final UserEntityService userEntityService;
    private final CloudinaryService cloudinaryService;
    private final PictureRepository pictureRepository;

    public TripEntityServiceImpl(ModelMapper modelMapper, TripRepository tripRepository, CategoryEntityService categoryEntityService, TownEntityService townEntityService, AttractionEntityService attractionEntityService, DetailsEntityService detailsEntityService, UserEntityService userEntityService,  CloudinaryService cloudinaryService, PictureRepository pictureRepository) {
        this.modelMapper = modelMapper;
        this.tripRepository = tripRepository;
        this.categoryEntityService = categoryEntityService;
        this.townEntityService = townEntityService;
        this.attractionEntityService = attractionEntityService;
        this.detailsEntityService = detailsEntityService;
        this.userEntityService = userEntityService;
        this.cloudinaryService = cloudinaryService;
        this.pictureRepository = pictureRepository;
    }

    @Override
    public void createTrip(AddTripServiceModel addTripServiceModel) throws IOException {

        TripEntity entity = modelMapper.map(addTripServiceModel, TripEntity.class);

        entity.setCategoryEntity(categoryEntityService
                .getCategoryByName(addTripServiceModel.getCategoryName()));

        List<ItineraryEntity> itineraries = new ArrayList<>();

        for (int i = 0; i < addTripServiceModel.getTownName().size(); i++) {

            ItineraryEntity itineraryEntity = new ItineraryEntity();
            itineraryEntity.setDay(i + 1);

            if (townEntityService.findTownByName(addTripServiceModel.getTownName().get(i)) != null) {
                itineraryEntity.setTown(townEntityService.findTownByName(addTripServiceModel.getTownName().get(i)));
            } else {
                TownEntity newTown = new TownEntity();
                newTown.setName(addTripServiceModel.getTownName().get(i));
                newTown.setRegion(addTripServiceModel.getRegion());

                townEntityService.saveTown(newTown);

            }

            itineraryEntity.setTown(townEntityService.findTownByName(addTripServiceModel.getTownName().get(i)));
            itineraryEntity.setCreatedOn(LocalDate.now());
            itineraryEntity.setBreakfastPlace(addTripServiceModel.getBreakfastPlace().get(i));
            itineraryEntity.setDinnerPlace(addTripServiceModel.getDinnerPlace().get(i));
            itineraryEntity.setCoffeePlace(addTripServiceModel.getCoffeePlace().get(i));
            itineraryEntity.setHotel(addTripServiceModel.getHotel().get(i));
            itineraryEntity.setTrip(entity);

            if (attractionEntityService.attractionExistByName(addTripServiceModel.getAttractionsName().get(i))) {

                itineraryEntity.setAttractions(List.of(attractionEntityService.findAttractionByName(addTripServiceModel.getAttractionsName().get(i))));
            } else {
                AttractionEntity attraction = new AttractionEntity();
                attraction.setName(addTripServiceModel.getAttractionsName().get(i));

                attractionEntityService.saveAttraction(attraction);

                itineraryEntity.setAttractions(List.of(attractionEntityService.findAttractionByName(attraction.getName())));
            }

            itineraries.add(itineraryEntity);
        }

        entity.setItineraries(itineraries);
        entity.setDuration(itineraries.size());

        DetailsEntity details =
                createDetailsEntity(addTripServiceModel.getEquipment(), addTripServiceModel.getFestivals(), addTripServiceModel.getFotoTip());

        detailsEntityService.saveDetailsEntity(details);

        entity.setDetails(details);
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        entity.setUser(userEntityService.findUserByUsername(principal.getUsername()).orElseThrow());

        var picture = createPictureEntity(addTripServiceModel.getUrl());

        pictureRepository.saveAndFlush(picture);

        entity.setPicture(picture);

        tripRepository.save(entity);


    }

    private PictureEntity createPictureEntity(MultipartFile file) throws IOException {
        final CloudinaryImage uploaded = this.cloudinaryService.upload(file);

       PictureEntity picture = new PictureEntity();

        picture.setPublicId(uploaded.getPublicId());
        picture.setTitle(file.getName());
        picture.setUrl(uploaded.getUrl());

        return picture;
    }

    @Override
    public  List<TripsArticleViewModel> findFirstEightBestTripsOrderByRating() {

        List<TripEntity> bestEightTripsOrderByRating = tripRepository.findBestEightTripsOrderByRating();

        return bestEightTripsOrderByRating
                .stream()
                .map(b -> {
                    TripsArticleViewModel map = modelMapper.map(b, TripsArticleViewModel.class);
                    map.setUrl(b.getPicture().getUrl());
                    map.setTownName(b.getItineraries().get(0).getTown().getName());

                    return map;
                }).collect(Collectors.toList());
    }


    private DetailsEntity createDetailsEntity(String equipment, String festivals, String fotoTip) {

         DetailsEntity detailsEntity = new DetailsEntity();
         detailsEntity.setEquipment(equipment);
        detailsEntity.setFestivals(festivals);
        detailsEntity.setFotoTips(fotoTip);

        return detailsEntity;
    }

    private TripsDto asTrip(TripEntity trip) {
        TripsDto tripDto = modelMapper.map(trip, TripsDto.class);
        tripDto.setUrl(trip.getPicture().getUrl());
        tripDto.setTownName(trip.getItineraries().get(0).getTown().getName());

        return tripDto;
    }

    @Override
    public Page<TripsDto> getTrips(int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        return tripRepository.
                findAll(pageable).
                map(this::asTrip);
    }

    @Override
    public TripDetailsView findById(Long id) {

           Optional<TripEntity> tripDetailsViewEntity =
                   tripRepository.findById(id);

        TripDetailsView map = modelMapper.map(tripDetailsViewEntity, TripDetailsView.class);

        map.setItinaries(tripDetailsViewEntity.get()
        .getItineraries().stream()
        .map(i -> modelMapper.map(i, ItinariesDetailsViewModel.class)).collect(Collectors.toList()));
        map.setTownName(tripDetailsViewEntity.get().getItineraries().get(0).getTown().getName());

        DetailsEntityViewModel detailsMap = modelMapper.map(tripDetailsViewEntity.get().getDetails(), DetailsEntityViewModel.class);

        map.setDetails(detailsMap);

        map.setCategoryName(CategoryEnum.valueOf(tripDetailsViewEntity.get().getCategoryEntity().getName().name()).name());
        map.setDuration(tripDetailsViewEntity.get().getItineraries().size());
        map.setUrl(tripDetailsViewEntity.get().getPicture().getUrl());

        return map;

        }


}
