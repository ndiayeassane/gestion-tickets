package net.ennov.gestion_ticket.enumeration;

public enum Status {

	ENCOURS("en cours"),
	TERMINER("terminé"),
	ANNULER("annulé");
	
	private final String status;
	
	Status(String status){
		this.status =status;
	}
	
	public String getStatus() {
		return this.status;
	}
}
