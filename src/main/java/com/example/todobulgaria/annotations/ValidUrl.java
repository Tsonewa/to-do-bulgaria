package com.example.todobulgaria.annotations;

import com.example.todobulgaria.utils.EmailValidator;
import com.example.todobulgaria.utils.PictureValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD,ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = PictureValidator.class)
@Documented
public @interface ValidUrl {

    String message() default "Profile picture is required";
    Class<?> [] groups() default {};
    Class<? extends Payload> [] payload() default {};
}
