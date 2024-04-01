package ru.read.reader.fb2format.DescriptionBlock;

import ru.read.reader.fb2format.FictionBook;

import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

public class Author {
	private String firstName = "";
	private String midleName = "";
	private String lastName = "";
	private String nickName;
	private UUID id;
	private List<String> emails;
	private List<String> homePages;
	
	public Author() {
		emails = new ArrayList<String>();
		homePages = new ArrayList<String>();
	}

	public String getFirstName() {
		return firstName;
	}

	public String getMidleName() {
		return midleName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getNickName() {
		return nickName;
	}

	public UUID getId() {
		return id;
	}

	public List<String> getEmails() {
		return emails;
	}

	public List<String> getHomePages() {
		return homePages;
	}

	public void setFirstName(String firstName) {this.firstName = firstName;}
	 public void setMidleNamte(String midleName) {this.midleName = midleName;}
	 public void setLastName(String lastName) {this.lastName = lastName;}
	 public void setNickName(String nickName) {this.nickName = nickName;}
	 public void setUUID(UUID id) {this.id = id;}
	 public void setEmail(String email) {emails.add(email);}
	 public void setHomePage(String homePage) {this.homePages.add(homePage);}
	@Override
	public String toString(){
		return firstName + " " + midleName + " " + lastName;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Author){
			Author obj1 = (Author) obj;
			return(this.firstName.equals(obj1.getFirstName()) && this.midleName.equals(obj1.getMidleName()) && this.lastName.equals(obj1.getLastName()) && this.id.equals(obj1.id));
		}
		return false;
	}
}
