package ebook.library.data.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import ebook.library.data.entity.UserEntity;
import ebook.library.data.repositories.UserRepository;

@Service
public class UserService extends CrudService<UserEntity, Integer> {

	private UserRepository userRepository;

	public UserService(@Autowired UserRepository repository) {
		this.userRepository = repository;
	}

	@Override
	protected UserRepository getRepository() {
		return userRepository;
	}

	public Collection<UserEntity> findAll() {
		return userRepository.findAll();
	}

	public void saveAll(List<UserEntity> users) {
		userRepository.saveAll(users);
	}

	
}
