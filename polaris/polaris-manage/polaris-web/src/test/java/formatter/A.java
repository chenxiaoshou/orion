package formatter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.polaris.common.formatter.BeanFormatter;
import com.polaris.common.utils.BeanUtil;

public class A implements Serializable {

	private static final long serialVersionUID = 1992611361810717593L;

	private String name = "a";

	private B b = new B();

	private C[] c;

	private Set<D> d = new HashSet<D>();

	private Map<String, E> map = new ConcurrentHashMap<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public B getB() {
		return b;
	}

	public void setB(B b) {
		this.b = b;
	}

	public C[] getC() {
		return c;
	}

	public void setC(C[] c) {
		this.c = c;
	}

	public Set<D> getD() {
		return d;
	}

	public void setD(Set<D> d) {
		this.d = d;
	}

	public Map<String, E> getMap() {
		return map;
	}

	public void setMap(Map<String, E> map) {
		this.map = map;
	}

	public static void main(String[] args) {
		B b = new B();
		C c = new C();
		D d = new D();
		c.getDds().add(d);
		b.getBmap().put("bmapkey", new C());
		C cc1 = (C) BeanUtil.deepClone(c);
		C cc2 = (C) BeanUtil.deepClone(c);
		C[] c1 = new C[] { cc1, cc2 };
		Set<D> dset = new HashSet<D>();
		D d1 = (D) BeanUtil.deepClone(d);
		D d2 = (D) BeanUtil.deepClone(d);
		D d3 = (D) BeanUtil.deepClone(d);
		dset.add(d1);
		dset.add(d2);
		dset.add(d3);

		A a = new A();
		a.setB(b);
		a.setC(c1);
		a.setD(dset);
		BeanFormatter beanFormatter = new BeanFormatter();
		System.out.println(beanFormatter.toString(a));
	}

}
