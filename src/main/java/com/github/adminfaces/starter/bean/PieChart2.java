package com.github.adminfaces.starter.bean;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.PieChartModel;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.adminfaces.starter.model.Client;
import com.github.adminfaces.starter.service.ClientService;

@ViewScoped
@Named

public class PieChart2 implements Serializable {
	private HorizontalBarChartModel horizontalBarModel;
	private BarChartModel barModel;

	@Autowired
	ClientService cs;

	@PostConstruct
	public void init() {

		createBarModels();

	}

	public BarChartModel getBarModel() {
		return barModel;
	}

	public HorizontalBarChartModel getHorizontalBarModel() {
		return horizontalBarModel;
	}

	private void createBarModels() {
		createHorizontalBarModel();
	}

	private void createHorizontalBarModel() {
		List<Client> cc = cs.getAllCars();
		int g1 = 0;
		int g2 = 0;
		int g3 = 0;
		int g4 = 0;
		int g5 = 0;
		int g6 = 0;
		int g7 = 0;
		int g8 = 0;
		int g9 = 0;
		int g10 = 0;
		
		for (Client c : cc) {
			if (c.getScore() >= 0 && c.getScore() < 20) {
				g1 += 1;
				long ccc = ChronoUnit.MONTHS.between(LocalDate.now(), c.getJoin_date())	;
				int i = Math.abs( (int) ccc);
               g6+=i;


			}
			if (c.getScore() >= 20 && c.getScore() < 40) {
				g2 += 1;
				long ccc = ChronoUnit.MONTHS.between(LocalDate.now(), c.getJoin_date())	;
				int i = Math.abs( (int) ccc);
               g7+=i;
			}
			if (c.getScore() >= 40 && c.getScore() < 60) {
				g3 += 1;
				long ccc = ChronoUnit.MONTHS.between(LocalDate.now(), c.getJoin_date())	;
				int i = Math.abs( (int) ccc);
               g8+=i;
			}
			if (c.getScore() >= 60 && c.getScore() < 80) {
				g4 += 1;
				long ccc = ChronoUnit.MONTHS.between(LocalDate.now(), c.getJoin_date())	;
				int i = Math.abs( (int) ccc);
               g9+=i;
			}
			if (c.getScore() >= 80 && c.getScore() < 100) {
				g5 += 1;
				long ccc = ChronoUnit.MONTHS.between(LocalDate.now(), c.getJoin_date())	;
				int i = Math.abs( (int) ccc);
               g10+=i;
			}

		}
		horizontalBarModel = new HorizontalBarChartModel();

		ChartSeries b = new ChartSeries();
		b.setLabel("Nb  of Clients");
		b.set("0-20", g1);
		b.set("20-40", g2);
		b.set("40-60", g3);
		b.set("60-80", g4);
		b.set("80-100", g5);
		ChartSeries c = new ChartSeries();
		c.setLabel("Average nb months of seniority ");
		c.set("0-20", g6);
		c.set("20-40", g7);
		c.set("40-60", g8);
		System.out.print(g8);
		c.set("60-80", g9);
		c.set("80-100", g10);

		horizontalBarModel.addSeries(b);
		horizontalBarModel.addSeries(c);

		horizontalBarModel.setTitle("Score repartiton by gap and averega senority");
		horizontalBarModel.setLegendPosition("e");
		horizontalBarModel.setStacked(true);

		Axis xAxis = horizontalBarModel.getAxis(AxisType.X);
		xAxis.setLabel("Nb Clients");
		xAxis.setMin(0);
		xAxis.setMax(10);

		Axis yAxis = horizontalBarModel.getAxis(AxisType.Y);
		yAxis.setLabel("Gap of score");
	}
}
