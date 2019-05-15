package it.polito.tdp.borders.model;

public class Border {
	
	Country country1;
	Country country2;
	
	public Border(Country country1, Country country2) {
		super();
		this.country1 = country1;
		this.country2 = country2;
	}

	public Country getCountry1() {
		return country1;
	}

	public void setCountry1(Country country1) {
		this.country1 = country1;
	}

	public Country getCountry2() {
		return country2;
	}

	public void setCountry2(Country country2) {
		this.country2 = country2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((country1 == null) ? 0 : country1.hashCode());
		result = prime * result + ((country2 == null) ? 0 : country2.hashCode());
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
		Border other = (Border) obj;
		if (country1 == null) {
			if (other.country1 != null)
				return false;
		} else if (!country1.equals(other.country1))
			return false;
		if (country2 == null) {
			if (other.country2 != null)
				return false;
		} else if (!country2.equals(other.country2))
			return false;
		return true;
	}
	

}
