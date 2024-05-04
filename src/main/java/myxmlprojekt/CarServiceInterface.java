package myxmlprojekt;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface CarServiceInterface {

	public List<Car> importXML(InputStream stream) throws WrongFormatException, IOException;

	public void exportXMLTo(List<Car> cars, String filepath) throws IOException;

}
