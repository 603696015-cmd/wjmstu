package sync.domin;

import java.util.List;

public class JSONObj {
	private String adcode;
	
	private String center;
	
	private String citycode;
	
	private List<JSONObj> districts;
	
	private String level;
	
	private String name;

	public String getAdcode() {
		return adcode;
	}

	public void setAdcode(String adcode) {
		this.adcode = adcode;
	}

	public String getCenter() {
		return center;
	}

	public void setCenter(String center) {
		this.center = center;
	}

	public String getCitycode() {
		return citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}

	public List<JSONObj> getDistricts() {
		return districts;
	}

	public void setDistricts(List<JSONObj> districts) {
		this.districts = districts;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
