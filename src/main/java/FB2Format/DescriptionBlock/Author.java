package FB2Format.DescriptionBlock;

import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

public class Author {
	private String firstName;
	private String midleName;
	private String lastName;
	private String nickName;
	private UUID id;
	private List<String> emails;
	private List<String> homePages;
	
	public Author() {
		emails = new ArrayList<String>();
		homePages = new ArrayList<String>();
	}
	
	 public void setFirstName(String firstName) {this.firstName = firstName;}
	 public void setMidleNamte(String midleName) {this.midleName = midleName;}
	 public void setLastName(String lastName) {this.lastName = lastName;}
	 public void setNickName(String nickName) {this.nickName = nickName;}
	 public void setUUID(UUID id) {this.id = id;}
	 public void setEmail(String email) {emails.add(email);}
	 public void setHomePage(String homePage) {this.homePages.add(homePage);}
}
