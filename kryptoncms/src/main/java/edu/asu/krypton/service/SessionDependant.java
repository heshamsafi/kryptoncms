package edu.asu.krypton.service;


/**
 * aop opens session for a thread before it enters any function annotated with this annotation
 * and closes the session after it returns
 * @author hesham
 */
public @interface SessionDependant {}
