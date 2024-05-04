package myxmlprojekt;

public class Car {

	private String name;
	private String color;

	public Car(String name, String color) {
		this.name = name;
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public String getColor() {
		return color;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Car))
			return false;

		Car other = (Car) obj;
		return other.getName().equals(name);
	}
}
