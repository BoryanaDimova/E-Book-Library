package ebook.library.views.bookslist;

import java.util.Collection;
import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import ebook.library.data.entity.BookEntity;
import ebook.library.data.entity.RatingEntity;
import ebook.library.data.entity.UserEntity;
import ebook.library.data.service.AuthorService;
import ebook.library.data.service.BookService;
import ebook.library.data.service.UserService;
import ebook.library.security.AuthenticatedUser;
import ebook.library.views.MainLayout;

@PermitAll
@PageTitle("Books List")
@Route(value = "", layout = MainLayout.class)
@Tag("books-list-view")
@JsModule("./views/bookslist/books-list-view.ts")
public class BooksListView extends LitTemplate implements HasComponents, HasStyle {
	private static final long serialVersionUID = 1L;

	private static final String ALL = "All";
	private static final String FAVOURITES = "Favourites";
	
	private UserEntity loggedUser;
	private BookService bookService;
	private UserService userService;
	private AuthenticatedUser authenticatedUser;

	private ListDataProvider<BookEntity> dataProvider;
	private Collection<BookEntity> userFavouriteBooks;
	private Collection<RatingEntity> ratings;
	private Collection<BookEntity> books;
	private BookForm bookForm;

	@Id
	private Select<String> filter;
	
	@Id
	private Button addBookBtn;
	
	public BooksListView(@Autowired BookService bookService, @Autowired AuthorService authorService,
			@Autowired AuthenticatedUser authenticatedUser,@Autowired UserService userService) {
		addClassNames("image-list-view", "flex", "flex-col", "h-full");

		this.authenticatedUser = authenticatedUser;
		setLoggedUser();
		this.bookService = bookService;
		this.userService = userService;

		bookForm = new BookForm(bookService, authorService, this);
		init();
	}

	private void init() {
		books = bookService.findAll();
		userFavouriteBooks = loggedUser.getFavouriteBooks();
		ratings = loggedUser.getRatings();
		dataProvider = new ListDataProvider<BookEntity>(books);

		addBookBtn.addClickListener(l -> openModalForm(new BookEntity()));

		filter.setItems(ALL, FAVOURITES);
		filter.setValue(ALL);
		filter.addValueChangeListener(evt->{
			System.out.println(evt);
			if(evt.getValue() == ALL) {
				dataProvider = new ListDataProvider<BookEntity>(books);
			} else {
				dataProvider = new ListDataProvider<BookEntity>(userFavouriteBooks);
			}
			resetGrid();
		});
		
		generateCards();
	}

	private void generateCards() {
		dataProvider.getItems().forEach(book -> {
			BookCard bookCard = new BookCard(book, bookForm, userFavouriteBooks.contains(book));
			RatingEntity rating = ratings.stream().filter(r->r.getBook().getId().equals(book.getId())).findFirst().get();
			if(rating != null) {
				bookCard.setRating(rating.getRating());
			}
			bookCard.setAvgRating(book.getRatings());
			add(bookCard);
		});
	}

	private void openModalForm(BookEntity bookEntity) {
		bookForm.openModalForm(bookEntity);
	}

	private void setLoggedUser() {
		this.loggedUser = authenticatedUser.get().orElse(new UserEntity());
	}
	
	public void resetGrid() {
//		setLoggedUser();
		getElement().removeAllChildren();
		books.clear();
		books.addAll(bookService.findAll());
//		userFavouriteBooks.clear();
//		userFavouriteBooks.addAll(loggedUser.getFavouriteBooks());
		dataProvider.refreshAll();
		generateCards();
	}

	public void addBookToFavourites(BookEntity book) {
		if (!userFavouriteBooks.contains(book)) {
			userFavouriteBooks.add(book);
			this.loggedUser.getFavouriteBooks().add(book);
			userService.update(this.loggedUser);
			resetGrid();
		}
	}

	public void removeBookFromFavourites(BookEntity book) {
		if (userFavouriteBooks.contains(book)) {
			userFavouriteBooks.add(book);
			this.loggedUser.getFavouriteBooks().remove(book);
			userService.update(this.loggedUser);
			resetGrid();
		}
	}

	public void addRating(BookEntity book, int rating) {
		RatingEntity ratingEntity = new RatingEntity();
		ratingEntity.setUser(loggedUser);
		ratingEntity.setRating(rating);
		
		book.getRatings().add(ratingEntity);
		bookService.update(book);
		resetGrid();
	}
}