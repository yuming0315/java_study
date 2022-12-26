package prob03;

import java.util.Objects;

public class Money {
	private int amount;
	
	/* 코드 작성 */
	public Money(int amount) {
		this.amount = amount;
	}

	
	
	@Override
	public int hashCode() {
		return Objects.hash(amount);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Money other = (Money) obj;
		return amount == other.amount;
	}
@Override
public String toString() {
	return Integer.toString(amount);
}


	public Object add(Money m) {
		return new Money(this.amount+m.amount);
	}



	public Object minus(Money m) {
		return new Money(this.amount-m.amount);
	}



	public Object multiply(Money m) {
		return new Money(this.amount*m.amount);
	}



	public Object devide(Money m) {
		return new Money(this.amount/m.amount);
	}	
	
}
