package ebook.library.data.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import ebook.library.data.entity.BookEntity;
import ebook.library.data.repositories.BookRepository;

@Service
public class BookService extends CrudService<BookEntity, Integer> {

	private BookRepository bookRepository;

	public BookService(@Autowired BookRepository repository) {
		this.bookRepository = repository;
	}
	
	@Override
	protected BookRepository getRepository() {
		return bookRepository;
	}

	public Collection<BookEntity> findAll() {
		return bookRepository.findAll();
	}
	
	public void saveAll(List<BookEntity> books) {
		bookRepository.saveAll(books);
	}
	
	
}
