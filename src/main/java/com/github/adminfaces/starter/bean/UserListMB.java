package com.github.adminfaces.starter.bean;

import com.github.adminfaces.starter.infra.model.Filter;
import com.github.adminfaces.starter.model.Role;
import com.github.adminfaces.starter.model.User;
import com.github.adminfaces.starter.repository.UserRepository;
import com.github.adminfaces.starter.service.UserService;
import com.github.adminfaces.template.exception.BusinessException;
import javax.faces.view.ViewScoped;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.github.adminfaces.starter.util.Utils.addDetailMessage;

@Named
@ViewScoped
public class UserListMB implements Serializable {

	@Inject
	UserService carService;
	@Inject
	UserRepository rep;
	Long id;

    private int counter;

	List<User> selectedCars; // cars selected in checkbox column

	List<User> filteredValue;// datatable filteredValue attribute (column
								// filters)
	List<User> clientsss;

	public List<User> getClientsss() {
	

		clientsss = carService.getAllCars();
		return clientsss;
	}

	public void setClientsss(List<User> clientsss) {
		this.clientsss = clientsss;
	}


	 public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public void increment() {
        System.out.println("counter " + counter);

	        counter += 1;
	}


	public void findCarById(Long id) {
		if (id == null) {
			throw new BusinessException("Provide User ID to load");
		}
		selectedCars.add(carService.findById(id));
		carService.getAuthus();
	}

	public void delete() {
		int numCars = 0;
		for (User selectedCar : selectedCars) {
			numCars++;
			
			carService.remove(selectedCar);
			
		}
		selectedCars.clear();
		addDetailMessage(numCars + " User deleted successfully!");
	
	}

	public List<User> getSelectedCars() {
		return selectedCars;
	}

	public List<User> getFilteredValue() {
		return filteredValue;
	}

	public void setFilteredValue(List<User> filteredValue) {
		this.filteredValue = filteredValue;
	}

	public void setSelectedCars(List<User> selectedCars) {
		this.selectedCars = selectedCars;
	}

	public void	getAuthus(){
		carService.getAuthus();
	}




	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
