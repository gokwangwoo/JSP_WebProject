package membership;
/*
 * DTO는 Data Transfer Object는 계층 사이에서 데이터를 교환하기 위해 생성하는 객체
 * 별 다른 로직없이 그 속성에 접근하기 위한 게터/세터 메서드만 갖춘 게 특징입니다.*/
public class MemberDTO {
	//멤버 변수 선언
	private String id;
	private String pass;
	private String name;
	private String regidate;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRegidate() {
		return regidate;
	}
	public void setRegidate(String regidate) {
		this.regidate = regidate;
	}
}
