package com.github.adminfaces.starter.bean;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import java.io.Serializable;
import java.util.List;

import org.primefaces.model.chart.PieChartModel;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.adminfaces.starter.model.Client;
import com.github.adminfaces.starter.service.ClientService;

@ViewScoped
@Named

public class PieChart implements Serializable {
	private PieChartModel pieModel;

	@Autowired
	ClientService cs;

	@PostConstruct
	public void init() {
		createPieModels();
	}

	public PieChartModel getPieModel() {
		return pieModel;
	}

	private void createPieModels() {
		createPieModel();
	}

	private void createPieModel() {
		int g1 = 0;
		int g2 = 0;
		int g3 = 0;
		int g4 = 0;

		List<Client> cc = cs.getAllCars();
		for (Client c : cc) {
			if (c.getGroupe().equals("Groupe1")) {
				g1 += 1;

			}
			if (c.getGroupe().equals("Groupe2")) {
				g2 += 1;

			}
			if (c.getGroupe().equals("Groupe3")) {
				g3 += 1;

			}
			if (c.getGroupe().equals("New")) {
				g4 += 1;

			}
			

		}

		pieModel = new PieChartModel();
		pieModel.set("Groupe1 ", g1);
		pieModel.set("Groupe2 ", g2);
		pieModel.set("Groupe3 ", g3);
		pieModel.set("New", g4);
		pieModel.setTitle("Repartion by Group Categories");
		pieModel.setLegendPosition("c");
	}
}
