package ebook.library.views.bookslist;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.template.Id;

import ebook.library.data.entity.BookEntity;

@JsModule("./views/bookslist/book-card.ts")
@Tag("book-card")
public class BookCard extends LitTemplate {

    @Id
    private Image image;

    @Id
    private Anchor header;

    @Id
    private Span subtitle;

    @Id
    private Paragraph text;

    @Id
    private Span badge;

    public BookCard(BookEntity book, BookForm bookForm) {
    	addClassNames("image-card w-30");
    
        this.image.setSrc(book.getCoverLocation());
        this.image.setAlt(book.getTitle());
        this.header.setText(book.getTitle());
        this.subtitle.setText(book.getAuthorName());
//        this.text.setText(
//                "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut.");
//        this.badge.setText("Label");
       
        this.header.getElement().addEventListener("click",l ->{
        	bookForm.openModalForm(book);
        });
    }
}
