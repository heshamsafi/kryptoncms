package edu.asu.krypton.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
	//private final Logger logger =  Logger.getLogger(LoggingAspect.class);
	
	//elly yshoof el expressions el pointcut expressions de yesalla7ha :)
//	@Before("allMethods() && allClasses()")
//	public void enteringMethod(JoinPoint joinPoint){
//		logger.info("entering method "+joinPoint.getSignature());
//	}
//	@After("allMethods() && allClasses()")
//	public void returningFromFunction(JoinPoint joinPoint){
//		logger.info("returning from method "+joinPoint.getSignature());
//	}
//	
//	@Pointcut("execution(* *(..))")
//	public void allMethods(){}
//	
//	@Pointcut("within(edu.asu.krypton...)")
//	public void allClasses(){}
}
