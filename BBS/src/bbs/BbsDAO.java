package bbs;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BbsDAO {

	private Connection conn;
	private ResultSet rs;

	public BbsDAO() {
		try {
			String dbURL = "jdbc:oracle:thin:@localhost:1521:XE";
			String dbID = "BBS";
			String dbPassword = "BBS";
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Date getDate() {
		String SQL = "SELECT NOW()";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getDate(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null; // DB오류
	}

	public int getNext() {
		String SQL = "SELECT bbsID from bbs order by bbsID desc";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) + 1;
			}
			return 1; // 현재가 첫번째 게시물인 경우
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // DB오류
	}

	public int write(String bbsTitle, String userID, String bbsContent) {
		String SQL = "insert into bbs values(?,?,?,sysdate,?,?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext());
			pstmt.setString(2, bbsTitle);
			pstmt.setString(3, userID);
			pstmt.setString(4, bbsContent);
			pstmt.setInt(5, 1);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // DB오류

	}

	public ArrayList<Bbs> getList(int pageNumber) {
		String SQL = "select * from ( select rownum as num, a.* from (select bbsid,bbstitle,userid,bbsdate,bbscontent,bbsavailable from bbs) a where bbsavailable = 1 order by to_number(bbsid) desc) where num <=10";
		
		// "SELECT * from (select rownum as num, a.* from (select bbsid,bbstitle,userid,bbsdate,bbscontent,bbsavailable from bbs order by to_number(bbsid)) a where BBSAVAILABLE = 1 ORDER BY bbsId desc) where num <= 10 order by num";
		// 위에서 10개까지만 출력 삭제되지 않은 게시글만 보이게하기
		ArrayList<Bbs> list = new ArrayList<Bbs>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			// pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Bbs bbs = new Bbs();
				bbs.setBbsID(rs.getInt(1));
				bbs.setBbsTitle(rs.getString(3));
				bbs.setUserID(rs.getString(4));
				bbs.setBbsDate(rs.getDate(5));
				bbs.setBbsContent(rs.getString(6));
				bbs.setBbsAvailable(rs.getString(7));
				list.add(bbs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	public String page() {
		String sql = "SELECT * from (select rownum as num, a.* from (select bbsid,bbstitle,userid,bbsdate,bbscontent,bbsavailable from bbs order by to_number(bbsid)) a where BBSAVAILABLE = 1 ORDER BY bbsId) order by num";
		int flag = 0;

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			// pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				flag = Integer.parseInt(rs.getString(1)) / 10;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		flag = flag + 1;
		return Integer.toString(flag);
	}

	public boolean nextPage(int pageNumber) {
		String SQL = "SELECT * FROM BBS WHERE bbsid < ? and bbsavailable = 1 ";
		ArrayList<Bbs> list = new ArrayList<Bbs>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public Bbs getBbs(int bbsID) {
		String SQL = "SELECT * FROM BBS WHERE bbsid = ? order by bbsID desc ";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, bbsID);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				Bbs bbs = new Bbs();
				bbs.setBbsID(rs.getInt(1));
				bbs.setBbsTitle(rs.getString(2));
				bbs.setUserID(rs.getString(3));
				bbs.setBbsDate(rs.getDate(4));
				bbs.setBbsContent(rs.getString(5));
				bbs.setBbsAvailable(rs.getString(6));
				return bbs;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int update(int bbsID, String bbsTitle, String bbsContent) {
		String SQL = "update bbs set bbsTitle = ?, bbsContent =  ? where bbsID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, bbsTitle);
			pstmt.setString(2, bbsContent);
			pstmt.setInt(3, bbsID);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // DB오류

	}
}
