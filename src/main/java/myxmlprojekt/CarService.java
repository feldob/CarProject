package myxmlprojekt;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;

public class CarService implements CarServiceInterface {

	@Override
	public List<Car> importXML(InputStream stream) throws WrongFormatException, IOException {
		List<Car> cars = new ArrayList<>();
		XML xml = new XMLDocument(stream);

		checkValidFileFormat(xml);

		List<XML> carNodes = xml.nodes("/Cars/Car");
		for (XML carNode : carNodes) {
			Car car = new Car(carNode.xpath("@color").get(0), carNode.xpath("text()").get(0));
			cars.add(car);
		}
		return cars;
	}

	/**
	 * Skapar XML filen här utan verktyg för att demonstrera att även det funkar fint som alternativ. Ibland will man inte va beroende av externa libs, så då kan en sådan kod va just det man behöver - denna var tom rätt så översiktlig.
	 */
	@Override
	public void exportXMLTo(List<Car> cars, String filePath) throws IOException {
		StringBuilder xmlBuilder = new StringBuilder();
		xmlBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		xmlBuilder.append("<Cars>\n");

		for (Car car : cars) {
			xmlBuilder
					.append("  <Car color=\"")
					.append(car.getColor())
					.append("\">")
					.append(car.getName())
					.append("</Car>\n");
		}

		xmlBuilder.append("</Cars>");

		try (FileWriter writer = new FileWriter(filePath)) {
			writer.write(xmlBuilder.toString());
		}
	}

	private void checkValidFileFormat(XML xml) throws WrongFormatException {
		if (xml.nodes("/Cars").isEmpty())
			throw new WrongFormatException("The xml file lacks a Cars entry.");
	}

}
