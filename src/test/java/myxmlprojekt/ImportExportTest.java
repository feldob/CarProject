package myxmlprojekt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.cactoos.io.ResourceOf;
import org.junit.Test;

public class ImportExportTest {

	private final CarServiceInterface service;
	
	public ImportExportTest() {
		this.service = new CarService();
	}
	
	@Test
	public void testCorrectImport() {	
		try {
			InputStream stream = new ResourceOf("mycars.xml").stream();
			List<Car> cars = service.importXML(stream);
			assertEquals(2, cars.size()); // vi skapade filen så vi vet facit
		} catch (WrongFormatException e) {
			e.printStackTrace();
			fail("the test file seems wrongly formatted.");
		} catch (Exception e) {
			e.printStackTrace();
			fail("file could not be found - error in test setup.");
		}
	}
	
	@Test
	public void testFileWrongFormatError() {
		try {
			InputStream stream = new ResourceOf("mycars_wrongformat.xml").stream();
			service.importXML(stream);
			fail("the file format is wrong, so this should not happen.");
		} catch (WrongFormatException e) {
			assertTrue(true); // det väntade beteendet
		} catch (Exception e) {
			e.printStackTrace();
			fail("wrong exception.");
		}
	}
	
	@Test
	public void testCorrectExport() {
		
		try {
			List<Car> cars = Arrays.asList(new Car("Bärgaren", "grönt"));
			service.exportXMLTo(cars, "acar.xml");
			
			List<Car> carsFile = service.importXML(new FileInputStream("acar.xml")); // importera igen
			assertEquals(cars.size(), carsFile.size()); // samma längd efter export och import
		} catch (Exception e) {
			e.printStackTrace();
			fail("test didn't go as expected");
		} finally { // städa upp efter testet
			File tmpfile = new File("acar.xml");
			if (tmpfile.exists())
				tmpfile.delete();
		}
	}
	
	// TODO många fler möjliga tester, som tex att kunna hantera extremfall som xml filer med tomma billistor, kolla att inte bara antalet bilar är korrekt i filerna, men även innehåll etc...

}
