package br.edu.ifpb.dac.groupd.business.validation.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.edu.ifpb.dac.groupd.business.validation.constraints.ValidEmail;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {
  private static final String PATTERN_EMAIL = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
  
  @Override
  public void initialize(ValidEmail constraintAnnotation) {
  }
  @Override
  public boolean isValid(String email, ConstraintValidatorContext context){
	  if(email == null) {
		  return false;
	  }
      return (validateEmail(email));
  } 
  private boolean validateEmail(String email) {
  Pattern pattern = Pattern.compile(PATTERN_EMAIL);
  Matcher matcher = pattern.matcher(email);
      
      return matcher.matches();
  }
}