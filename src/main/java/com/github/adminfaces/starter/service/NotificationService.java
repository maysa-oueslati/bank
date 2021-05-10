/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.adminfaces.starter.service;

import com.github.adminfaces.starter.infra.model.Filter;
import com.github.adminfaces.starter.infra.model.SortOrder;
import com.github.adminfaces.starter.model.Client;
import com.github.adminfaces.starter.model.Notification;
import com.github.adminfaces.starter.model.TypeNotification;
import com.github.adminfaces.starter.repository.ClientRepository;
import com.github.adminfaces.starter.repository.NotificationRepository;
import com.github.adminfaces.template.exception.BusinessException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.github.adminfaces.template.util.Assert.has;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationService implements Serializable {

	@Autowired
	NotificationRepository rep;
	
    List<Notification> allCars;
    @PostConstruct
    public void init() {
    	allCars =(List<Notification>) rep.findAll();
    }

	public List<Notification> getAllCars() {
		allCars = (List<Notification>) rep.findAll();
		return allCars;
	}

	public void setAllCars(List<Notification> allCars) {
		this.allCars = allCars;
	}
    public List<Notification> listByModel(String model) {
        return allCars.stream()
                .filter(c -> c.getMessage().equalsIgnoreCase(model))
                .collect(Collectors.toList());

    }

    public List<Notification> paginate(Filter<Notification> filter) {
        List<Notification> pagedCars = new ArrayList<>();
        if(has(filter.getSortOrder()) && !SortOrder.UNSORTED.equals(filter.getSortOrder())) {
                pagedCars = allCars.stream().
                    sorted((c1, c2) -> {
                        if (filter.getSortOrder().isAscending()) {
                            return c1.getId().compareTo(c2.getId());
                        } else {
                            return c2.getId().compareTo(c1.getId());
                        }
                    })
                    .collect(Collectors.toList());
            }

        int page = filter.getFirst() + filter.getPageSize();
        if (filter.getParams().isEmpty()) {
            pagedCars = pagedCars.subList(filter.getFirst(), page > allCars.size() ? allCars.size() : page);
            return pagedCars;
        }

        List<Predicate<Notification>> predicates = configFilter(filter);

        List<Notification> pagedList = allCars.stream().filter(predicates
                .stream().reduce(Predicate::or).orElse(t -> true))
                .collect(Collectors.toList());

        if (page < pagedList.size()) {
            pagedList = pagedList.subList(filter.getFirst(), page);
        }

        if (has(filter.getSortField())) {
            pagedList = pagedList.stream().
                    sorted((c1, c2) -> {
                        boolean asc = SortOrder.ASCENDING.equals(filter.getSortOrder());
                        if (asc) {
                            return c1.getId().compareTo(c2.getId());
                        } else {
                            return c2.getId().compareTo(c1.getId());
                        }
                    })
                    .collect(Collectors.toList());
        }
        return pagedList;
    }

    private List<Predicate<Notification>> configFilter(Filter<Notification> filter) {
        List<Predicate<Notification>> predicates = new ArrayList<>();
        if (filter.hasParam("id")) {
            Predicate<Notification> idPredicate = (Notification c) -> c.getId().equals(filter.getParam("id"));
            predicates.add(idPredicate);
        }

      
        if (has(filter.getEntity())) {
            Notification filterEntity = filter.getEntity();
            if (has(filterEntity.getMessage())) {
                Predicate<Notification> modelPredicate = (Notification c) -> c.getMessage().toLowerCase().contains(filterEntity.getMessage().toLowerCase());
                predicates.add(modelPredicate);
            }

            if (has(filterEntity.getType_notification())) {
                Predicate<Notification> modelPredicate = (Notification c) -> c.getType_notification().toString().toLowerCase().contains(filterEntity.getMessage().toLowerCase());
                predicates.add(modelPredicate);
            }

            if (has(filterEntity.getSenton())) {
                Predicate<Notification> namePredicate = (Notification c) -> c.getSenton().toLowerCase().contains(filterEntity.getSenton().toLowerCase());
                predicates.add(namePredicate);
            }
        }
        return predicates;
    }
    public List<TypeNotification> getModels(String query) {
        return allCars.stream().filter(c -> c.getType_notification().toString()
                .toLowerCase().contains(query.toLowerCase()))
                .map(Notification::getType_notification)
                .collect(Collectors.toList());
    }
    
    
    
    public List<String> getMessages(String query) {
        return allCars.stream().filter(c -> c.getMessage()
                .toLowerCase().contains(query.toLowerCase()))
                .map(Notification::getMessage)
                .collect(Collectors.toList());
    }

    public void insert(Notification car) {
        car.setId(allCars.stream()
                .mapToInt(c -> c.getId())
                .max()
                .getAsInt()+1);
        allCars.add(car);
    }


    public void remove(Notification car) {
        allCars.remove(car);
		rep.delete(car);

    }

    public long count(Filter<Notification> filter) {
        return allCars.stream()
                .filter(configFilter(filter).stream()
                        .reduce(Predicate::or).orElse(t -> true))
                .count();
    }

    public Notification findById(Integer id) {
        return allCars.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new BusinessException("Car not found with id " + id));
    }

    public void update(Notification car) {
        allCars.remove(allCars.indexOf(car));
        allCars.add(car);
    }
}
