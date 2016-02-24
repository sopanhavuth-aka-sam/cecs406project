import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement( name = "response" )

public class Response

{

    List row;
    

    @XmlElement( name = "row" )
	public List getRow() {
		return row;
	}

	public void setRow(List row) {
		this.row = row;
	}

    /**
07
     * element that is going to be marshaled in the xml
08
     */
}

    
