package ebook.library.views.access;

import java.util.stream.Stream;

import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

import ebook.library.data.entity.UserEntity;

public class RegistrationForm extends FormLayout {
	Binder<UserEntity> binder = new BeanValidationBinder<>(UserEntity.class);

	private H3 title;

	private TextField firstName;
	private TextField lastName;

	private EmailField email;
	private TextField username;

	private PasswordField password;
	private PasswordField passwordConfirm;

	private Span errorMessageField;

	private Button submitButton;

	public RegistrationForm() {
		addClassName("vaadin-login-form");
		
		title = new H3("Sign Up");
		firstName = new TextField("First Name");
		lastName = new TextField("Last Lame");
		email = new EmailField("Email");
		username = new TextField("Username");

		password = new PasswordField("Password");
		passwordConfirm = new PasswordField("Confirm password");

		setRequiredIndicatorVisible(firstName, lastName, username, email, password, passwordConfirm);

		errorMessageField = new Span();

		submitButton = new Button("Sign Up");
		submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		binder.bindInstanceFields(this);
		add(title, firstName, lastName, email, username, password, passwordConfirm, errorMessageField, submitButton);

		// Max width of the Form
		setMaxWidth("500px");

		// Allow the form layout to be responsive.
		// On device widths 0-490px we have one column.
		// Otherwise, we have two columns.
		setResponsiveSteps(new ResponsiveStep("0", 1, ResponsiveStep.LabelsPosition.TOP),
				new ResponsiveStep("490px", 2, ResponsiveStep.LabelsPosition.TOP));

		// These components always take full width
		setColspan(title, 2);
		setColspan(email, 2);
		setColspan(username, 2);
		setColspan(errorMessageField, 2);
		setColspan(submitButton, 2);
	}

	private void setRequiredIndicatorVisible(HasValueAndElement<?, ?>... components) {
		Stream.of(components).forEach(comp -> comp.setRequiredIndicatorVisible(true));
	}

}
