package edu.asu.krypton.exceptions;

import edu.asu.krypton.service.crypto.ecc.ECPoint;

public class NotOnMotherException extends Exception{

	private static final long serialVersionUID = 1L;
	private ECPoint sender;

    public NotOnMotherException(ECPoint sender){
	this.sender = sender;
    }

    public String getErrorString(){
	return "NotOnMother";
    }

    public ECPoint getSource(){
	return sender;
    }
}
