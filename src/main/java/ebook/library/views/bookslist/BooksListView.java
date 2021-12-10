package ebook.library.views.bookslist;

import java.util.Collection;

import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import ebook.library.data.entity.BookEntity;
import ebook.library.data.service.AuthorService;
import ebook.library.data.service.BookService;
import ebook.library.views.MainLayout;

@PermitAll
@PageTitle("Books List")
@Route(value = "", layout = MainLayout.class)
@Tag("books-list-view")
@JsModule("./views/bookslist/books-list-view.ts")
public class BooksListView extends LitTemplate implements HasComponents, HasStyle  {
	private static final long serialVersionUID = 1L;
	private BookService bookService;
	private AuthorService authorService;
	private ListDataProvider<BookEntity> dataProvider;
	private Collection<BookEntity> books;
	private BookForm bookForm;

	@Id
	private Button addBookBtn;

	public BooksListView(@Autowired BookService bookService, AuthorService authorService) {
		addClassNames("image-list-view", "flex", "flex-col", "h-full");
		this.bookService = bookService;
		this.authorService = authorService;
		this.addListener(BookEvent.class, l -> resetGrid());
		bookForm = new BookForm(bookService, authorService);
		init();
	}

	private void init() {
		books = bookService.findAll();
		dataProvider = new ListDataProvider<BookEntity>(books);

		addBookBtn.addClickListener(l -> openModalForm(new BookEntity()));

		generateCards();
	}

	private void generateCards() {
		dataProvider.getItems().forEach(book -> {
			add(new BookCard(book, bookForm));
		});
	}

	private void openModalForm(BookEntity bookEntity) {
		bookForm.openModalForm(bookEntity);
	}

	public void resetGrid() {
		dataProvider.clearFilters();
		books.clear();
		books.addAll(bookService.findAll());
		dataProvider.refreshAll();
	}

}