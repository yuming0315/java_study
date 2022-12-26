package prob01;

public class Gugudan {
	private int lValue;
	private int rValue;
	
	public Gugudan(int lValue,int rValue) {
		this.lValue=lValue;
		this.rValue=rValue;
	}

	public int getValue() {
		return lValue*rValue;
	}
	@Override
	public int hashCode() {
		return lValue*rValue;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Gugudan other = (Gugudan) obj;
		return lValue*rValue == other.lValue*other.rValue;
	}
	
	
}