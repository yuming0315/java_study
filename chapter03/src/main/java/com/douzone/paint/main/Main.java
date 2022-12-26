package com.douzone.paint.main;

import com.douzone.paint.i.Drawable;
import com.douzone.paint.point.ColorPoint;
import com.douzone.paint.point.Point;
import com.douzone.paint.shape.Circle;
import com.douzone.paint.shape.Rect;
import com.douzone.paint.shape.Shape;
import com.douzone.paint.shape.Triangle;
import com.douzone.paint.text.GraphicText;

public class Main {

	public static void main(String[] args) {
		Point point1 = new Point(10, 10);
		// point1.setX(10);
		// point1.setY(10);
		
		//drawPoint(point1);
		draw(point1);
		// point1.disappear();
		point1.show(false);
		
		Point point2 = new ColorPoint(20, 20, "red");
//		point2.setX(20);
//		point2.setY(20);
//		((ColorPoint)point2).setColor("red");

		//drawPoint(point2);
		draw(point2);
		
		Rect rect = new Rect();
		//drawRect(rect);
		//drawShape(rect);
		draw(rect);
		
		Triangle triangle = new Triangle();
		// drawTriangle(triangle);
		// drawShape(triangle);
		draw(triangle);
		
		Circle circle = new Circle();
		// drawShape(circle);
		draw(circle);
		
		draw(new GraphicText("Hello Wolrd"));
		
		
		// instanceof 연산자 Test
		System.out.println(circle instanceof Object);
		System.out.println(circle instanceof Shape);
		System.out.println(circle instanceof Circle);

		// 오류: 연산자 우측항이 클래스인 경우,
		//      레퍼런스 하고 있는 class 타입의  hierachy 상의 하위와 상위만
		//      instanceof 연산자를 사용할 수 있다.
		// System.out.println(circle instanceof Rect);
		Object o = circle;
		System.out.println(o instanceof String);
		
		// 연산자 우측항이 인터페이스인 경우,
		// Hierachy 상관 없이 instanceof 연산자를 사용할 수 있다.
		System.out.println(circle instanceof Drawable);
		System.out.println(circle instanceof Runnable);
	}

	public static void draw(Drawable drawable) {
		drawable.draw();
	}
	
//	public static void drawPoint(Point point) {
//		point.show();
//	}
//	public static void drawColorPoint(ColorPoint colorPoint) {
//		colorPoint.show();
//	}
	
//	public static void drawShape(Shape shape) {
//		shape.draw();
//	}
//	public static void drawRect(Rect rect) {
//		rect.draw();
//	}
//	public static void drawTriangle(Triangle triangle) {
//		triangle.draw();
//	}
//	public static void drawCircle(Circle circle) {
//		circle.draw();
//	}
}
