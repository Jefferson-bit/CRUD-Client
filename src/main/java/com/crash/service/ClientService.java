package com.crash.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crash.domain.Client;
import com.crash.domain.dto.ClientDTO;
import com.crash.repositories.ClientRepository;
import com.crash.service.exceptions.ResourceNotFound;

@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepository;
	
	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional<Client> client = clientRepository.findById(id);
		Client clientOptional = client.orElseThrow(() -> new ResourceNotFound("Not Found " + id));
		return new ClientDTO(clientOptional);
	}
	
	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(Pageable pageable) {
		Page<Client> pageClient = clientRepository.findAll(pageable);
		Page<ClientDTO> dtoPageClient = pageClient.map(dto -> new ClientDTO(dto));
		return dtoPageClient;
	}
	
	@Transactional
	public ClientDTO insert(ClientDTO dto) {
		Client entity = new Client();
		dtoForEntity(entity, dto);
		entity = clientRepository.save(entity);
		return new ClientDTO(entity);
	}
	
	@Transactional
	public ClientDTO update(ClientDTO dto, Long id) {
		try {
			Client entity = clientRepository.getOne(id);
			dtoForEntity(entity, dto);
			entity = clientRepository.save(entity);
			return new ClientDTO(entity);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFound("ID not found " + id);
		}
	}
	
	@Transactional
	public void delete(Long id) {
		try {
			clientRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFound("ID not found " + id);
		}
	}

	private void dtoForEntity(Client entity, ClientDTO dto) {
		entity.setName(dto.getName());
		entity.setIncome(dto.getIncome());
		entity.setCpf(dto.getCpf());
		entity.setBirthDate(dto.getBirthDate());
		entity.setChildren(dto.getChildren());
	}
}
