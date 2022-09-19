package br.edu.ifpb.dac.groupd.business.validation.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import br.edu.ifpb.dac.groupd.model.entities.Bracelet;
import br.edu.ifpb.dac.groupd.model.entities.Fence;

public class ModelValidator {
	
	private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	
	public static boolean validBracelet(Bracelet bracelet) {
		Set<ConstraintViolation<Bracelet>> violations = validator.validate(bracelet);

		violations.forEach(System.out::println);
		
		return violations.isEmpty();
	}
	public static boolean validFence(Fence fence) {
		Set<ConstraintViolation<Fence>> violations = validator.validate(fence);
		
		violations.forEach(System.out::println);
		
		return violations.isEmpty();
	}
}
