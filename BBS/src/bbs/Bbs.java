package bbs;

import java.sql.Date;

public class Bbs {


	public int getBbsID() {
		return bbsID;
	}
	public void setBbsID(int bbsID) {
		this.bbsID = bbsID;
	}
	public String getBbsTitle() {
		return bbsTitle;
	}
	public void setBbsTitle(String bbsTitle) {
		this.bbsTitle = bbsTitle;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public Date getBbsDate() {
		return bbsDate;
	}
	public void setBbsDate(Date bbsDate) {
		this.bbsDate = bbsDate;
	}
	public String getBbsContent() {
		return bbsContent;
	}
	public void setBbsContent(String bbsContent) {
		this.bbsContent = bbsContent;
	}
	public String getBbsAvailable() {
		return bbsAvailable;
	}
	public void setBbsAvailable(String bbsAvailable) {
		this.bbsAvailable = bbsAvailable;
	}
	private int bbsID;
	private String bbsTitle;
	private String userID;
	private Date bbsDate;
	private String bbsContent;
	private String bbsAvailable;
	
}
