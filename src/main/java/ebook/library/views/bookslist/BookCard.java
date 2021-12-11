package ebook.library.views.bookslist;

import java.util.List;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.template.Id;

import ebook.library.data.entity.BookEntity;
import ebook.library.data.entity.RatingEntity;

@JsModule("./views/bookslist/book-card.ts")
@Tag("book-card")
public class BookCard extends LitTemplate {

	private final Icon heartIcon = VaadinIcon.HEART.create();
	private final Icon heartOIcon = VaadinIcon.HEART_O.create();
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    private Image image;

    @Id
    private Anchor header;

    @Id
    private Span subtitle;

    @Id
    private Paragraph text;
    
    @Id private Span badge;

    @Id
    private StarsRating starsRating;
    
    @Id
    private Button delete;
    
    @Id
    private Button favouriteBtn;
    
    private Integer rating = 0;
    
    public BookCard(BookEntity book, BookForm bookForm, Boolean isAddedToFavs) {
    	addClassNames("image-card w-30");
    
        this.image.setSrc(book.getCoverLocation());
        this.image.setAlt(book.getTitle());
        this.header.setText(book.getTitle());
        this.subtitle.setText(book.getAuthorName());
        this.text.setText(book.getDescription());
       
        this.header.getElement().addEventListener("click",l ->{
        	bookForm.openModalForm(book);
        });
        
        this.delete.setIcon(VaadinIcon.CLOSE.create());
        this.delete.setVisible(true);
        this.delete.getElement().addEventListener("click", l-> {
        	bookForm.remove(book);
        });
        
        toggleFavIcon(isAddedToFavs);
        
        this.favouriteBtn.getElement().addEventListener("click", l -> {
        	if(isAddedToFavs) {
        		bookForm.listView.removeBookFromFavourites(book);
        	} else {
        		bookForm.listView.addBookToFavourites(book);
        	}
        });   
        
        starsRating.setNumstars(6);
        starsRating.setManual(true);
        starsRating.addValueChangeListener(event ->{
        	bookForm.listView.addRating(book, event.getValue());
        });
    }
    
    public void setRating(int rating) {
    	 starsRating.setRating(rating);
    }
    
    public void setAvgRating(List<RatingEntity> bookRatings) {
    	this.setAvgRating(calculateBookRating(bookRatings));
    }
    
    
    public void setAvgRating(double rating) {
    	badge.setText(String.valueOf(rating));
    }
    
    private void toggleFavIcon(Boolean isAddedToFavs){
    	 this.favouriteBtn.setIcon(isAddedToFavs ? heartIcon : heartOIcon);
    }
    
    private double calculateBookRating(List<RatingEntity> bookRatings) {
		bookRatings.forEach(r ->{ 
			rating += r.getRating();
			});
		return (rating/bookRatings.size());
	}
    
}
