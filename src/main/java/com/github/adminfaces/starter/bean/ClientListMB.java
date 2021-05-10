package com.github.adminfaces.starter.bean;

import com.github.adminfaces.starter.infra.model.Filter;
import com.github.adminfaces.starter.model.Client;
import com.github.adminfaces.starter.repository.ClientRepository;
import com.github.adminfaces.starter.service.ClientService;
import com.github.adminfaces.template.exception.BusinessException;
import javax.faces.view.ViewScoped;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import static com.github.adminfaces.starter.util.Utils.addDetailMessage;

@Named
@ViewScoped
public class ClientListMB implements Serializable {

	@Inject
	ClientService carService;
	@Inject
	ClientRepository rep;
	Long id;

	LazyDataModel<Client> allCars;

	Filter<Client> filter = new Filter<>(new Client());

	List<Client> selectedCars; // cars selected in checkbox column

	List<Client> filteredValue;// datatable filteredValue attribute (column
								// filters)
	List<Client> clientsss;

	public List<Client> getClientsss() {

		clientsss = carService.getAllCars();
		return clientsss;
	}

	public void setClientsss(List<Client> clientsss) {
		this.clientsss = clientsss;
	}

	@PostConstruct
	public void initDataModel() {
		allCars = new LazyDataModel<Client>() {
			@Override
			public List<Client> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				com.github.adminfaces.starter.infra.model.SortOrder order = null;
				if (sortOrder != null) {
					order = sortOrder.equals(SortOrder.ASCENDING)
							? com.github.adminfaces.starter.infra.model.SortOrder.ASCENDING
							: sortOrder.equals(SortOrder.DESCENDING)
									? com.github.adminfaces.starter.infra.model.SortOrder.DESCENDING
									: com.github.adminfaces.starter.infra.model.SortOrder.UNSORTED;
				}
				filter.setFirst(first).setPageSize(pageSize).setSortField(sortField).setSortOrder(order)
						.setParams(filters);
				List<Client> list = carService.paginate(filter);
				setRowCount((int) carService.count(filter));
				return list;
			}

			@Override
			public int getRowCount() {
				return super.getRowCount();
			}

			@Override
			public Client getRowData(String key) {
				return carService.findById(new Long(key));
			}
		};
	}

	public void clear() {
		filter = new Filter<Client>(new Client());
	}

	public List<String> completeModel(String query) {
		List<String> result = carService.getModels(query);
		return result;
	}

	public void findCarById(Long id) {
		if (id == null) {
			throw new BusinessException("Provide Client ID to load");
		}
		selectedCars.add(carService.findById(id));
	}

	public void delete() {
		int numCars = 0;
		for (Client selectedCar : selectedCars) {
			numCars++;
			
			carService.remove(selectedCar);
			
		}
		selectedCars.clear();
		addDetailMessage(numCars + " Client deleted successfully!");
	
	}

	public List<Client> getSelectedCars() {
		return selectedCars;
	}

	public List<Client> getFilteredValue() {
		return filteredValue;
	}

	public void setFilteredValue(List<Client> filteredValue) {
		this.filteredValue = filteredValue;
	}

	public void setSelectedCars(List<Client> selectedCars) {
		this.selectedCars = selectedCars;
	}

	public LazyDataModel<Client> getAllCars() {
		return allCars;
	}

	public void setAllCars(LazyDataModel<Client> cars) {
		this.allCars = cars;
	}

	public Filter<Client> getFilter() {
		return filter;
	}

	public void setFilter(Filter<Client> filter) {
		this.filter = filter;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
