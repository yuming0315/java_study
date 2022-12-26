package com.douzone.paint.shape;

public class Circle extends Shape {
	private int x, y;
	private int radius;

	@Override
	public void draw() {
		System.out.println("원을 그렸습니다.");
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}
}
