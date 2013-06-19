package edu.asu.krypton.model.message_proxies;

public class MembershipStatus extends Message {
	private boolean isLoggedin;
	private boolean isAdmin;
	public boolean isAdmin() {
		return isAdmin;
	}
	public MembershipStatus setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
		return this;
	}
	public boolean isLoggedin() {
		return isLoggedin;
	}
	public MembershipStatus setLoggedin(boolean isLoggedin) {
		this.isLoggedin = isLoggedin;
		return this;
	}
}
