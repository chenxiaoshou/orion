package formatter;

import java.io.Serializable;
import java.util.ArrayList;

public class C implements Serializable {

	private static final long serialVersionUID = 2385007703042938296L;

	private int age = 61;
	
	private ArrayList<D> dds = new ArrayList<>();

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public ArrayList<D> getDds() {
		return dds;
	}

	public void setDds(ArrayList<D> dds) {
		this.dds = dds;
	}
	
}
