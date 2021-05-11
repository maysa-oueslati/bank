
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.adminfaces.starter.service;

import com.github.adminfaces.starter.infra.model.Filter;
import com.github.adminfaces.starter.infra.model.SortOrder;
import com.github.adminfaces.starter.model.Request;
import com.github.adminfaces.template.exception.BusinessException;

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
public class RequestService implements Serializable  {


	@Autowired
	IRequestService reqSer;
	
    List<Request> allRequests;
    
    
    
    public List<Request> list() {
        return allRequests;

    }

    public List<Request> paginate(Filter<Request> filter) {
        List<Request> pagedCars = new ArrayList<>();
        pagedCars=reqSer.retrieveAllRequests();
        if(has(filter.getSortOrder()) && !SortOrder.UNSORTED.equals(filter.getSortOrder())) {
                pagedCars = allRequests.stream().
                    sorted((c1, c2) -> {
                        if (filter.getSortOrder().isAscending()) {
                            return c1.getId_request().compareTo(c2.getId_request());
                        } else {
                            return c2.getId_request().compareTo(c1.getId_request());
                        }
                    })
                    .collect(Collectors.toList());
            }

        int page = filter.getFirst() + filter.getPageSize();
        if (filter.getParams().isEmpty()) {
            pagedCars = pagedCars.subList(filter.getFirst(), page > allRequests.size() ? allRequests.size() : page);
            return pagedCars;
        }

        List<Predicate<Request>> predicates = configFilter(filter);

        List<Request> pagedList = allRequests.stream().filter(predicates
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
                            return c1.getId_request().compareTo(c2.getId_request());
                        } else {
                            return c2.getId_request().compareTo(c1.getId_request());
                        }
                    })
                    .collect(Collectors.toList());
        }
        return pagedList;
    }

    private List<Predicate<Request>> configFilter(Filter<Request> filter) {
        List<Predicate<Request>> predicates = new ArrayList<>();
        if (filter.hasParam("id")) {
            Predicate<Request> idPredicate = (Request c) -> c.getId_request().equals(filter.getParam("id"));
            predicates.add(idPredicate);
        }

        /*if (filter.hasParam("minPrice") && filter.hasParam("maxPrice")) {
            Predicate<Car> minMaxPricePredicate = (Car c) -> c.getPrice()
                    >= Double.valueOf((String) filter.getParam("minPrice")) && c.getPrice()
                    <= Double.valueOf((String) filter.getParam("maxPrice"));
            predicates.add(minMaxPricePredicate);
        } else if (filter.hasParam("minPrice")) {
            Predicate<Car> minPricePredicate = (Car c) -> c.getPrice()
                    >= Double.valueOf((String) filter.getParam("minPrice"));
            predicates.add(minPricePredicate);
        } else if (filter.hasParam("maxPrice")) {
            Predicate<Car> maxPricePredicate = (Car c) -> c.getPrice()
                    <= Double.valueOf((String) filter.getParam("maxPrice"));
            predicates.add(maxPricePredicate);
        }

        if (has(filter.getEntity())) {
            Car filterEntity = filter.getEntity();
            if (has(filterEntity.getModel())) {
                Predicate<Car> modelPredicate = (Car c) -> c.getModel().toLowerCase().contains(filterEntity.getModel().toLowerCase());
                predicates.add(modelPredicate);
            }

            if (has(filterEntity.getPrice())) {
                Predicate<Car> pricePredicate = (Car c) -> c.getPrice().equals(filterEntity.getPrice());
                predicates.add(pricePredicate);
            }

            if (has(filterEntity.getName())) {
                Predicate<Car> namePredicate = (Car c) -> c.getName().toLowerCase().contains(filterEntity.getName().toLowerCase());
                predicates.add(namePredicate);
            }
        }*/
        return predicates;
    }

    /*public List<String> getModels(String query) {
        return allCars.stream().filter(c -> c.getModel()
                .toLowerCase().contains(query.toLowerCase()))
                .map(Car::getModel)
                .collect(Collectors.toList());
    }*/

    public void insert(Request req) {
        validate(req);
        reqSer.addRequest(req);
        allRequests.add(req); 
    }

    public void validate(Request req) {
        
    }


    public void remove(Request req) {
    	reqSer.deleteRequest(req);
        allRequests.remove(req);
    }

    public long count(Filter<Request> filter) {
        return allRequests.stream()
                .filter(configFilter(filter).stream()
                        .reduce(Predicate::or).orElse(t -> true))
                .count();
    }

    public Request findById(Integer id) {
        return allRequests.stream()
                .filter(c -> c.getId_request().equals(id))
                .findFirst()
                .orElseThrow(() -> new BusinessException("Request not found with id " + id));
    }

    public void update(Request req) {
        validate(req);
        reqSer.updateRequest(req);
        allRequests.remove(allRequests.indexOf(req));
        allRequests.add(req);
    }
}
