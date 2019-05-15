package it.polito.tdp.borders.model;

public class Country implements Comparable<Country>{
	
	private int ccode;
	private String StateAbb;
	private String StateNme;
	
	public Country(int ccode, String stateAbb, String stateNme) {
		super();
		this.ccode = ccode;
		StateAbb = stateAbb;
		StateNme = stateNme;
	}

	public int getCcode() {
		return ccode;
	}

	public void setCcode(int ccode) {
		this.ccode = ccode;
	}

	public String getStateAbb() {
		return StateAbb;
	}

	public void setStateAbb(String stateAbb) {
		StateAbb = stateAbb;
	}

	public String getStateNme() {
		return StateNme;
	}

	public void setStateNme(String stateNme) {
		StateNme = stateNme;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ccode;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (ccode != other.ccode)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("%s", StateNme);
	}

	@Override
	public int compareTo(Country other) {
		return this.getStateNme().compareTo(other.getStateNme());
	}
	

}
