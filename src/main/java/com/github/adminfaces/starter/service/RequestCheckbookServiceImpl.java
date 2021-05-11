package com.github.adminfaces.starter.service;

import java.util.List;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.adminfaces.starter.model.RequestCheckbook;
import com.github.adminfaces.starter.repository.RequestCheckbookRepository;


@Service
public class RequestCheckbookServiceImpl implements IRequestCheckbookService{
	@Autowired
   RequestCheckbookRepository requestrepo; 
	@Override
	public List<RequestCheckbook> retrieveAllRequestCheckbooks() {
		List<RequestCheckbook> requestlist =(List<RequestCheckbook>)requestrepo.findAll();
		return requestlist;
	}

	@Override
	public RequestCheckbook addRequestCheckbook(RequestCheckbook request) {
		
		return requestrepo.save(request);
	}

	@Override
	public void deleteRequestCheckbook(RequestCheckbook request) {
		requestrepo.deleteById(request.getId_request());
		
	}

	@Override
	public RequestCheckbook updateRequestCheckbook(RequestCheckbook request) {
		return requestrepo.save(request);
	}

	@Override
	public Optional<RequestCheckbook> retrieveRequestCheckbook(Long id_request) {
		return requestrepo.findById(id_request);
	}

}
