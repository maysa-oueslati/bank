package com.github.adminfaces.starter.bean;

import com.github.adminfaces.starter.infra.model.Filter;
import com.github.adminfaces.starter.model.Client;
import com.github.adminfaces.starter.model.Notification;
import com.github.adminfaces.starter.model.TypeNotification;
import com.github.adminfaces.starter.service.NotificationService;
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
public class NotificationListMB implements Serializable {

	@Inject
	NotificationService carService;

	Integer id;

	LazyDataModel<Notification> allCars;

	Filter<Notification> filter = new Filter<>(new Notification());

	List<Notification> selectedCars;

	List<Notification> filteredValue;

	@PostConstruct
	public void initDataModel() {
		allCars = new LazyDataModel<Notification>() {
			@Override
			public List<Notification> load(int first, int pageSize, String sortField, SortOrder sortOrder,
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
				List<Notification> list = carService.paginate(filter);
				setRowCount((int) carService.count(filter));
				return list;
			}

			@Override
			public int getRowCount() {
				return super.getRowCount();
			}

			@Override
			public Notification getRowData(String key) {
				return carService.findById(new Integer(key));
			}
		};
	}

	public void clear() {
		filter = new Filter<Notification>(new Notification());
	}

	public List<String> completeModel(String query) {
		List<String> result = carService.getMessages(query);

		return result;
	}
	public List<TypeNotification> completeModel1(String query) {
		List<TypeNotification> result = carService.getModels(query);

		return result;
	}

	public void findCarById(Integer id) {
		if (id == null) {
			throw new BusinessException("Provide Notification ID to load");
		}
		selectedCars.add(carService.findById(id));
	}

	public void delete() {
		int numCars = 0;
		for (Notification selectedCar : selectedCars) {
			numCars++;
			carService.remove(selectedCar);
		}
		selectedCars.clear();
		addDetailMessage(numCars + " cars deleted successfully!");
	}

	public List<Notification> getSelectedCars() {
		return selectedCars;
	}

	public List<Notification> getFilteredValue() {
		return filteredValue;
	}

	public void setFilteredValue(List<Notification> filteredValue) {
		this.filteredValue = filteredValue;
	}

	public void setSelectedCars(List<Notification> selectedCars) {
		this.selectedCars = selectedCars;
	}

	public LazyDataModel<Notification> getAllCars() {
		return allCars;
	}

	public void setAllCars(LazyDataModel<Notification> cars) {
		this.allCars = cars;
	}

	public Filter<Notification> getFilter() {
		return filter;
	}

	public void setFilter(Filter<Notification> filter) {
		this.filter = filter;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
