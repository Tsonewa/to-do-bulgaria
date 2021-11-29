package com.example.todobulgaria.services.impl;

import com.example.todobulgaria.exceptions.ObjectNotFoundException;
import com.example.todobulgaria.models.entities.*;
import com.example.todobulgaria.models.enums.CategoryEnum;
import com.example.todobulgaria.models.enums.RoleEnum;
import com.example.todobulgaria.models.service.AddTripServiceModel;
import com.example.todobulgaria.models.views.*;
import com.example.todobulgaria.repositories.PictureRepository;
import com.example.todobulgaria.repositories.TripRepository;
import com.example.todobulgaria.services.*;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
    private final BreakfastPlaceEntityService breakfastPlaceEntityService;
    private final CoffeePlaceEntityService coffeePlaceEntityService;
    private final HotelEntityService hotelEntityService;
    private final DinnerPlaceEntityService dinnerPlaceEntityService;

    public TripEntityServiceImpl(ModelMapper modelMapper, TripRepository tripRepository, CategoryEntityService categoryEntityService, TownEntityService townEntityService, AttractionEntityService attractionEntityService, DetailsEntityService detailsEntityService, UserEntityService userEntityService, CloudinaryService cloudinaryService, PictureRepository pictureRepository, BreakfastPlaceEntityService breakfastPlaceEntityService, CoffeePlaceEntityService coffeePlaceEntityService, HotelEntityService hotelEntityService, DinnerPlaceEntityService dinnerPlaceEntityService) {
        this.modelMapper = modelMapper;
        this.tripRepository = tripRepository;
        this.categoryEntityService = categoryEntityService;
        this.townEntityService = townEntityService;
        this.attractionEntityService = attractionEntityService;
        this.detailsEntityService = detailsEntityService;
        this.userEntityService = userEntityService;
        this.cloudinaryService = cloudinaryService;
        this.pictureRepository = pictureRepository;
        this.breakfastPlaceEntityService = breakfastPlaceEntityService;
        this.coffeePlaceEntityService = coffeePlaceEntityService;
        this.hotelEntityService = hotelEntityService;
        this.dinnerPlaceEntityService = dinnerPlaceEntityService;
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

            if (townEntityService.existTownEntityByName(addTripServiceModel.getTownName().get(i))) {
                itineraryEntity.setTown(townEntityService.findTownByName(addTripServiceModel.getTownName().get(i)));
            } else {
                TownEntity newTown = new TownEntity();
                newTown.setName(addTripServiceModel.getTownName().get(i));

                townEntityService.saveTown(newTown);
            }

            if (breakfastPlaceEntityService.existBreakfastPlaceByName(addTripServiceModel.getBreakfastPlace().get(i))) {
                itineraryEntity.setBreakfastPlace(breakfastPlaceEntityService.findBrekfastPlaceEntityByName(addTripServiceModel.getBreakfastPlace().get(i)));
            } else {
                BreakfastPlaceEntity newBreakfastPlace = new BreakfastPlaceEntity();
                newBreakfastPlace.setName(addTripServiceModel.getBreakfastPlace().get(i));

                breakfastPlaceEntityService.saveBreakfastPlace(newBreakfastPlace);

            }

            if (coffeePlaceEntityService.existCoffeePlaceByName(addTripServiceModel.getCoffeePlace().get(i))) {
                itineraryEntity.setCoffeePlaceEntity(coffeePlaceEntityService.findCoffeePlaceEntityByName(addTripServiceModel.getCoffeePlace().get(i)));
            } else {
                CoffeePlaceEntity newCoffeePlace = new CoffeePlaceEntity();
                newCoffeePlace.setName(addTripServiceModel.getCoffeePlace().get(i));

                coffeePlaceEntityService.saveCoffeePlace(newCoffeePlace);
            }

            if (dinnerPlaceEntityService.existDinnerPlaceByName(addTripServiceModel.getDinnerPlace().get(i))) {
                itineraryEntity.setDinnerPlaceEntity(dinnerPlaceEntityService.findDinnerPlaceEntityByName(addTripServiceModel.getDinnerPlace().get(i)));
            } else {
                DinnerPlaceEntity newDinnerPlace = new DinnerPlaceEntity();
                newDinnerPlace.setName(addTripServiceModel.getDinnerPlace().get(i));

                dinnerPlaceEntityService.saveDinnerPlace(newDinnerPlace);
            }

            if (hotelEntityService.existHotelEntityByName(addTripServiceModel.getHotel().get(i))) {
                itineraryEntity.setHotelEntity(hotelEntityService.findHotelEntityByName(addTripServiceModel.getHotel().get(i)));
            } else {
                HotelEntity newHotel = new HotelEntity();
                newHotel.setName(addTripServiceModel.getHotel().get(i));

                hotelEntityService.saveHotel(newHotel);
            }

            itineraryEntity.setTown(townEntityService.findTownByName(addTripServiceModel.getTownName().get(i)));
            itineraryEntity.setBreakfastPlace(breakfastPlaceEntityService.findBrekfastPlaceEntityByName(addTripServiceModel.getBreakfastPlace().get(i)));
            itineraryEntity.setCreatedOn(LocalDate.now());
            itineraryEntity.setDinnerPlaceEntity(dinnerPlaceEntityService.findDinnerPlaceEntityByName(addTripServiceModel.getDinnerPlace().get(i)));
            itineraryEntity.setCoffeePlaceEntity(coffeePlaceEntityService.findCoffeePlaceEntityByName(addTripServiceModel.getCoffeePlace().get(i)));
            itineraryEntity.setHotelEntity(hotelEntityService.findHotelEntityByName(addTripServiceModel.getHotel().get(i)));

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

        if(addTripServiceModel.getEquipment() != null || addTripServiceModel.getFestivals() != null || addTripServiceModel.getFotoTip() != null){
            DetailsEntity details = createDetailsEntity(addTripServiceModel.getEquipment(), addTripServiceModel.getFestivals(), addTripServiceModel.getFotoTip());
            detailsEntityService.saveDetailsEntity(details);
            entity.setDetails(details);
        }

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
                    TripsArticleViewModel tripsArticleViewModel = asArticleTrip(b);
                    return tripsArticleViewModel;
                }).collect(Collectors.toList());
    }


    private DetailsEntity createDetailsEntity(String equipment, String festivals, String fotoTip) {

            DetailsEntity detailsEntity = new DetailsEntity();
            detailsEntity.setEquipment(equipment);
            detailsEntity.setFestivals(festivals);
            detailsEntity.setFotoTips(fotoTip);

        return detailsEntity;
    }

    private TripsArticleViewModel asArticleTrip(TripEntity trip) {
        TripsArticleViewModel tripsArticleViewModel = modelMapper.map(trip, TripsArticleViewModel.class);
        tripsArticleViewModel.setUrl(trip.getPicture().getUrl());
        tripsArticleViewModel.setStartPoint(trip.getStartPoint());

        return tripsArticleViewModel;
    }

    @Override
    public Page<TripsArticleViewModel> getTrips(int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        return tripRepository.
                findAll(pageable).
                map(this::asArticleTrip);
    }

    @Override
    public TripDetailsView findById(Long id) {

           TripEntity tripEntity =
                   tripRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id));

        TripDetailsView map = new TripDetailsView();

       map.setItinaries(matToItineraryDetailsView
               (tripEntity));

       map.setDescription(tripEntity.getDescription());
        map.setStartPoint(tripEntity.getStartPoint());

        if(tripEntity.getDetails() != null) {
            map.setDetails(modelMapper
                    .map(tripEntity.getDetails(), DetailsEntityViewModel.class));
        }

        map.setCategoryName(CategoryEnum.valueOf(tripEntity.getCategoryEntity().getName().name()).name());
        map.setDuration(tripEntity.getItineraries().size());
        map.setUrl(tripEntity.getPicture().getUrl());
        map.setId(tripEntity.getId());

        return map;

        }

    private List<ItinerariesDetailsViewModel> matToItineraryDetailsView(TripEntity tripEntity) {

      return tripEntity
        .getItineraries().stream()
        .map(i -> {
            ItinerariesDetailsViewModel itinary = modelMapper
                    .map(i, ItinerariesDetailsViewModel.class);

            itinary.setBreakfastPlace(i.getBreakfastPlace().getName());
            itinary.setAttractionName(i.getAttractions().get(0).getName());
            itinary.setCoffeePlace(i.getCoffeePlaceEntity().getName());
            itinary.setDinnerPlace(i.getDinnerPlaceEntity().getName());
            itinary.setHotel(i.getHotelEntity().getName());

            return itinary;

        }).collect(Collectors.toList());
    }

    @Override
    public List<TripCategoryTownDurationViewModel> findAllByUserId(Long id) {

        List<TripEntity> tripEntities = tripRepository.findAllByUserId(id);
        List<TripCategoryTownDurationViewModel> collect;

        try{
            collect = tripEntities.stream().map(e -> {

                return modelMapper.map(e, TripCategoryTownDurationViewModel.class);

            }).collect(Collectors.toList());
        } catch (RuntimeException ex){
            throw new ObjectNotFoundException(id);
        }

        return collect;
    }

    @PreAuthorize("isOwner(#id)")
    @Override
    public void deleteTrip(Long id, String username) {

        UserEntity currentUser = userEntityService
                .findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException
                        ("User with username " + username + " not found!"));

        Set<TripEntity> filteredTrips =  currentUser
                .getFavouriteTrips()
                .stream()
                .filter(t -> !t.getId().equals(id))
                .collect(Collectors.toSet());

        currentUser.setFavouriteTrips(filteredTrips);

        userEntityService.
                updateUser(currentUser);

        tripRepository.deleteById(id);
    }

    @Override
    public List<TripCategoryTownDurationViewModel> findAllTripsById(Set<Long> favouriteTripsSet) {

        return tripRepository.findAllById(favouriteTripsSet)
                .stream()
                .map(m -> modelMapper.map
                        (m, TripCategoryTownDurationViewModel.class))
                .collect(Collectors.toList());

    }

    @Override
    public TripEntity findEntityById(Long id) {

        return tripRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id));

    }

    @Override
    public Integer tripsCount() {

        return tripRepository.findAll().size();
    }

    public boolean isOwner(String userName, Long id) {
        Optional<TripEntity> trip = tripRepository.
                findById(id);
        Optional<UserEntity> caller = userEntityService.
                findUserByUsername(userName);

        if (trip.isEmpty() || caller.isEmpty()) {
            return false;
        } else {
            TripEntity tripEntity = trip.get();

            return isAdmin(caller.get()) ||
                    tripEntity.getUser().getUsername().equals(userName);
        }
    }

    private boolean isAdmin(UserEntity user) {
        return user.
                getRoles().
                stream().
                map(RoleEntity::getRole).
                anyMatch(r -> r == RoleEnum.ADMIN);
    }

    @Override
    public List<TripsArticleViewModel> getByKeywordAndDuration(String keyword, int duration){

        return tripRepository.findByKeywordAndDuration(keyword.trim(), duration)
                .stream()
                .map(t -> {

                    TripsArticleViewModel map = modelMapper.map(t, TripsArticleViewModel.class);
                    map.setUrl(t.getPicture().getUrl());

                    return map;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<TripsArticleViewModel> getByKeyword(String keyword){

        return tripRepository.findByKeyword(keyword.trim())
                .stream()
                .map(t -> {

                    TripsArticleViewModel map = modelMapper.map(t, TripsArticleViewModel.class);
                    map.setUrl(t.getPicture().getUrl());

                    return map;
                })
                .collect(Collectors.toList());
    }
}
