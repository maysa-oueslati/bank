package com.github.adminfaces.starter.service;

import java.text.SimpleDateFormat;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.adminfaces.starter.model.Request;
import com.github.adminfaces.starter.repository.RequestRepository;




@Service
public class RequestServiceImpl implements IRequestService{
	@Autowired
   RequestRepository requestrepo;
	
	
	@Autowired
	private static final Logger LOG = LoggerFactory.getLogger(RequestServiceImpl.class);
	@Override
	public List<Request> retrieveAllRequests() {
		List<Request> requestlist =(List<Request>)requestrepo.findAll();
		return requestlist;
	}

	@Override
	public Request addRequest(Request request ) {
		/*if(request.getClient().getScore()>50){
			request.setStatus("Validée");
		
			return requestrepo.save(request);

		}
		else 
		{*/request.setStatus("Encours");
		 return requestrepo.save(request);
		//}		
		
	}
	
		
		


	@Override
	public void deleteRequest(Request request) {
		requestrepo.deleteById(request.getId_request());
		
	}

	
	@Override
	public Request updateRequest(Request request) {
		return requestrepo.save(request);
	}

	@Override
	public Optional<Request> retrieveRequest(Long id_request) {
		return requestrepo.findById(id_request);
	}

	@Override
	public Request acceptRequest(Request request) {
		
		//return requestrepo.save(request) ;
		if(request.getClient().getScore()>50){
			request.setStatus("Validée");
		
			return requestrepo.save(request);

		}
		
		else 
		{request.setStatus("Encours");
		 return requestrepo.save(request);
		}
	}

	@Override
	public String RecommendRequest() {
	long  a ,b ,c ;
		a=requestrepo.countcreditrequest("request_credit", 1);
	

		b=requestrepo.countcreditrequestcard("request_credit_card",1);
		c=requestrepo.countcheckbookrequest("request_checkbook",1);
		
		if(a>b && a>c) {
			return "request_credit";
			
		}
		else if (b>a && b>c) {
			return "request_credit_card";
			
		}else if (c>b && c>a) {
			return "request_checkbook";

		}
		return null;
	}
		

		@Override
		public Map<Integer,Integer> StatisticRequestByType(int type) {
			Map<Integer, Integer> MapRequest = new HashMap<Integer, Integer>();
			int checkbook_c = 0;
			int creditcard_c = 0;
			int credit_c = 0;
				
			List<Request> ListRequests = listRequest(type)  ;
			for (int i = 0; i < ListRequests.size(); i++) {
				if (type == 1)
				{
					checkbook_c += 1;
					MapRequest.put(1, checkbook_c);
				}
				else if (type == 2)
				{
					creditcard_c += 1;
					MapRequest.put(2, creditcard_c);
				}
				else if (type == 3)
				{
					credit_c += 1;
					MapRequest.put(3,  credit_c);
				}
			}
			return MapRequest;
		}
		
		@SuppressWarnings({ "null", "deprecation" })
		@Override
		public List<Request> listRequest(int type) {

			List<Request> LR = (List<Request>)requestrepo.findAll();
			List<Request> ListR = new ArrayList<Request>();
			for (int i = 0; i < LR.size(); i++) {
				System.out.print("Type " + LR.get(i).getType() + "\n");

				if (LR.get(i).getType() == "credit" || LR.get(i).getType() == "credit_card" || LR.get(i).getType() == "checkbook") {

					ListR.add(LR.get(i));

					System.out.print("Add Type " + LR.get(i).getType());

				}
			}
			return ListR;
		}
		
		@Override
		public Map<Double, Double> StatisticCreatedPerMonth(int year) {
			Map<Double, Double> MA = new HashMap<Double, Double>();
			double sum1 = 0;
			double sum2 = 0;
			double sum3 = 0;
			double sum4 = 0;
			double sum5 = 0;
			double sum6 = 0;
			double sum7 = 0;
			double sum8 = 0;
			double sum9 = 0;
			double sum10 = 0;
			double sum11 = 0;
			double sum12 = 0;
			List<Request> L = listRequestByDate(year);
			for (int i = 0; i < L.size(); i++) {
				String dateToStr = String.format("%1$tY-%1$tm-%1$td", L.get(i).getCr_date());
				String[] dateParts = dateToStr.split("-");
				String month = dateParts[1];
				if (month.equals("01"))

				{
					sum1 += 1;
					MA.put((double) 1, sum1);
				}

				else if (month.equals("02")) {
					sum2 += 1;
					MA.put((double) 2, sum2);
				}
				
				else if (month.equals("03")) {
					sum3 += 1;
					MA.put((double) 3, sum3);
				}

				else if (month.equals("04")) {
					sum4 += 1;
					MA.put((double) 4, sum4);
				}

				else if (month.equals("05")) {
					sum5 += 1;
					MA.put((double) 5, sum5);
				}
			
				
				else if (month.equals("06")) {
					sum6 += 1;
					MA.put((double) 6, sum6);
				}
				
				else if (month.equals("07")) {
					sum7 += 1;
					MA.put((double) 7, sum7);
				}
				
				else if (month.equals("08")) {
					sum8 += 1;
					MA.put((double) 8, sum8);
				}
				
				else if (month.equals("09")) {
					sum9 += 1;
					MA.put((double) 9, sum9);
				}
				
				else if (month.equals("10")) {
					sum10 += 1;
					MA.put((double) 10, sum10);
				}
				
				else if (month.equals("11")) {
					sum11 += 1;
					MA.put((double) 11, sum11);
				}
				
				else if (month.equals("12")) {
					sum12 += 1;
					MA.put((double) 12, sum12);
				}
				
			}

			return MA;
		}
		
		@SuppressWarnings({ "null", "deprecation" })
		@Override
		public List<Request> listRequestByDate(int year) {

			List<Request> L = (List<Request>) requestrepo.findAll();
			List<Request> ListR = new ArrayList<Request>();
			for (int i = 0; i < L.size(); i++) {
				System.out.print("YEAR " + Integer.parseInt(new SimpleDateFormat("yyyy").format(L.get(i).getCr_date())) + "\n");

				if (Integer.parseInt(new SimpleDateFormat("yyyy").format(L.get(i).getCr_date())) == year) {

					String dateToStr = String.format("%1$tY-%1$tm-%1$td", L.get(i).getCr_date());

					ListR.add(L.get(i));

					System.out.print("Add year  "	+ Integer.parseInt(new SimpleDateFormat("yyyy").format(L.get(i).getCr_date())));

				}
			}
			return ListR;
		}

		@Override
		public Optional<SavedRequests> retrieveSavedRequest(Long id_request) {
			// TODO Auto-generated method stub
			return null;
		}
	}

