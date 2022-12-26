package tv;

public class TV {
	private int channel;    // 1~255 
	private int volume;     // 0~100
	private boolean power;
	
	public TV(int channel, int volume, boolean power) {
		this.power = power;
		if(notPower()) {
			return;
		}
		channel(channel);
		volume(volume);
	}
	
	public void power(boolean on) {
		power = on;
	}
	
	public void channel(boolean up) {
		 channel(channel + (up ? 1 : -1));
	}

	public boolean notPower() {
		System.out.println("전원없음 변경 불가");
		return !power;
	}
	public void channel(int channel) {
		if(notPower()) {
			return;
		}
		channel += channel<1 ? 255 : channel>255 ? -255 : 0;
		this.channel = channel;
	}
	
	public void volume(boolean up) {
		volume(volume + (up ? 1 : -1));
	}

	public void volume(int volume) {
		if(notPower()) {
			return;
		}
		volume += volume<0 ? 100 : volume>100 ? -100: 0;
		this.volume = volume;
	}

	public void status() {
		System.out.println(
			"TV[power=" + (power ? "on" : "off") + ", " + 
			"channel=" + channel + ", " +
			"volume=" + volume + "]");
	}
	
}
