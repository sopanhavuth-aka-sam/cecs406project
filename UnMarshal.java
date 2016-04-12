package cecs406project;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

class UnMarshal{
	public static void main(String args[]){
		File file = new File("C:/Users/Arunkumar/Eclipse-workspace/Branch-CECS406/src/cecs406project/babyNames.xml");
		
		JAXBContext jaxbContext;
		//Marshal
		Response customer = new Response();
		Set<String> set= new HashSet<String>();
		//Marshal
		

		try {
			jaxbContext = JAXBContext.newInstance(Response.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			
			Response response = (Response) jaxbUnmarshaller.unmarshal(file);
			Iterator itr=response.getRow().iterator();
			int i=0,j=0;
			while(itr.hasNext()){
				Row row=(Row)itr.next();
				int length= set.size();
				set.add(row.getBrth_yr()+row.getNm()+row.getCnt()+row.getEthcty()+row.getNm()+row.getRnk());
				System.out.println(row.getBrth_yr());
				System.out.println(row.getNm());
				
				//Marshal
				if(length!=set.size()){
					Row row1=new Row();
					row1.setBrth_yr(row.getBrth_yr());
					row1.setCnt(row.getCnt());
					row1.setEthcty(row.getEthcty());
					row1.setGndr(row.getGndr());
					row1.setNm(row.getNm());
					row1.setRnk(row.getRnk());
					customer.setRow(row1);
					j++;
				}
				i++;
				
			}
			File file1 = new File("C:/Users/Arunkumar/Eclipse-workspace/Branch-CECS406/src/cecs406project/file1.xml");
			JAXBContext jaxbContext1 = JAXBContext.newInstance(Response.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(customer, file1);
			

			System.out.println("Count: "+i);
			System.out.println("ActualCount:" +j);
			System.out.println("Set");
			
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		

	}
}