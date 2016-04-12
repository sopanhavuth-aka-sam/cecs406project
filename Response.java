package cecs406project;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement( name = "response" )
public class Response

{
    ArrayList <Row>row=new <Row>ArrayList();
    
    @XmlElement( name = "row" )
	public ArrayList<Row> getRow() {
		return row;
	}

	public void setRow(Row row1) {
		row.add(row1);
	}

    /**
07
     * element that is going to be marshaled in the xml
08
     */
}

    
