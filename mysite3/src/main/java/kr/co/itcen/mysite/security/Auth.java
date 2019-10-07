package kr.co.itcen.mysite.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {
	public enum Role{USER, ADMIN};
	
	//public Role role() default Role.USER; db랑 session에 role이 있어야 하마.
	public String value() default "USER";
	
	public int test() default 1;
	
	
}
