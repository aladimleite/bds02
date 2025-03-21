package com.devsuperior.bds02.services;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import java.util.List;
//import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

//import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.entities.Event;
//import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.repositories.EventRepository;
//import com.devsuperior.bds02.services.exceptions.DatabaseException;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;

@Service
public class EventService {
    @Autowired
    private EventRepository repository;

    //@Autowired
    //private CityRepository cityRepository;
    
    @Transactional
    public EventDTO update(Long id, EventDTO dto) {
        try {
            
            Event entity = repository.getOne(id);            
            
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new EventDTO(entity);
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    private void copyDtoToEntity(EventDTO dto, Event entity) {
        entity.setName(dto.getName());        
        entity.setDate(dto.getDate());
        entity.setUrl(dto.getUrl());        
        /*
        entity.getCity().clear();
        for (CityDTO citDto : dto.getCity()) {            
            City city = cityRepository.getById(citDto.getId());
            entity.getCity().add(city);
        }
        */
        entity.setCity(new City(dto.getCityId(), null));
    }            

}
