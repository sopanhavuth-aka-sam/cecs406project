import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

class UnMarshal{
	public static void main(String args[]){
		File file = new File("C:/Users/Arunkumar/Eclipse-workspace/CECS406/src/babyNames.xml");
		
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(Response.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			
			Response response = (Response) jaxbUnmarshaller.unmarshal(file);
			System.out.println(response.getRow());
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		

	}
}