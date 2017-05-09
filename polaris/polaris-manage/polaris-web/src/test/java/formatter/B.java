package formatter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class B implements Serializable {

	private static final long serialVersionUID = 6208465008085233543L;

	private String name = "b";
	
	private Map<String,C> bmap = new LinkedHashMap<>();
	
	private List<D> ds = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, C> getBmap() {
		return bmap;
	}

	public void setBmap(Map<String, C> bmap) {
		this.bmap = bmap;
	}

	public List<D> getDs() {
		return ds;
	}

	public void setDs(List<D> ds) {
		this.ds = ds;
	}
	
}
