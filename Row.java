import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="row")
public class Row {
	private String brth_yr;
	private String gndr;
    private String ethcty;
    private String nm;
    private String cnt;
    private String rnk;
    
    
    //setters getters
    
    @XmlElement(name="brth_yr")
    public String getBrth_yr() {
		return brth_yr;
	}
	public void setBrth_yr(String brth_yr) {
		this.brth_yr = brth_yr;
	}
	
	@XmlElement(name="gndr")
	public String getGndr() {
		return gndr;
	}
	public void setGndr(String gndr) {
		this.gndr = gndr;
	}
	
	@XmlElement(name="ethcty")
	public String getEthcty() {
		return ethcty;
	}
	public void setEthcty(String ethcty) {
		this.ethcty = ethcty;
	}
	
	@XmlElement(name="nm")
	public String getNm() {
		return nm;
	}
	public void setNm(String nm) {
		this.nm = nm;
	}
	
	@XmlElement(name="cnt")
	public String getCnt() {
		return cnt;
	}
	public void setCnt(String cnt) {
		this.cnt = cnt;
	}
	
	@XmlElement(name="rnk")
	public String getRnk() {
		return rnk;
	}
	public void setRnk(String rnk) {
		this.rnk = rnk;
	}

    
    //setters getters
}