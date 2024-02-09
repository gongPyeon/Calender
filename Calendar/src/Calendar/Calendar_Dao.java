package Calendar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Calendar_Dao{
	private Connection conn;
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";

	private static final String URL = "jdbc:mysql://localhost/Calendar";

	public Calendar_Dao() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

		} catch (Exception e) {
			System.out.println("DB연결이 실패했습니다. 오류를 수정 후 다시 시도해주세요" + e);
		} 

	}

	public void Insert_Calendar(CalendarVO Calendar) {

		String sql = "insert into Calendar(rush,title,date,tag) values(?,?,?,?);";
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Calendar.getRush());
			pstmt.setString(2, Calendar.getTitle());
			pstmt.setString(3, Calendar.getDate());
			pstmt.setString(4, Calendar.getTag());

			pstmt.executeUpdate();
			System.out.print("");//"일정이 등록되었습니다.
		} catch (Exception e) {
			System.out.println("데이터 삽입 실패");

		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}

		}
	}

	public ArrayList<CalendarVO> get_Schedule_By_Day(String date) {
		String sql = "select * from Calendar where date = ?;";
		PreparedStatement pstmt = null;
		ArrayList<CalendarVO> result = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, date);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CalendarVO re = new CalendarVO();
				re.setCal_no(rs.getInt("cal_no"));
				re.setRush(rs.getInt("rush"));
				re.setTitle(rs.getString("title"));
				re.setDate(rs.getString("date"));
				re.setTag(rs.getString("tag"));

				result.add(re);
			}
		} catch (Exception e) {
			System.out.println("데이터베이스 작업 중 에러발생");
		}
		return result;
	}
	public int Count_Of_schedule(String date, int rush,String tag) {
		String sql = "select * from Calendar where date = ? and rush =? and tag =?;";
		PreparedStatement pstmt = null;
		ArrayList<CalendarVO> result = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, date);
			pstmt.setInt(2, rush);
			pstmt.setString(3, tag);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CalendarVO re = new CalendarVO();
				re.setCal_no(rs.getInt("cal_no"));
				re.setRush(rs.getInt("rush"));
				re.setTitle(rs.getString("title"));
				re.setDate(rs.getString("date"));
				re.setTag(rs.getString("tag"));

				result.add(re);
			}
		} catch (Exception e) {
			System.out.println("데이터베이스 작업 중 에러발생");
		}
		return result.size();
	}
	public void update_rush(int rush,int cal_no) {
		String sql = "update Calendar set rush=? where cal_no=?;";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rush);
			pstmt.setInt(2, cal_no);
			pstmt.executeUpdate();
			} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt !=null && !pstmt.isClosed())
					pstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	public void update_Title(String title,int cal_no) {
		String sql = "update Calendar set title=? where cal_no=?;";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setInt(2, cal_no);
			pstmt.executeUpdate();
			} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt !=null && !pstmt.isClosed())
					pstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	public void update_Tag(CalendarVO calendarVO) {
		String sql = "update Calendar set tag=? where cal_no=?;";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, calendarVO.getTag());
			pstmt.setInt(2, calendarVO.getCal_no());
			pstmt.executeUpdate();
			} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt !=null && !pstmt.isClosed())
					pstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	public ArrayList Group_Of_Tag() {
		String sql = "select tag from Calendar;";
		PreparedStatement pstmt = null;
		ArrayList<String> result = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String re = rs.getString("tag");
				result.add(re);
			}
			result = new ArrayList<String>(
					result.stream().distinct()
		                       .collect(Collectors.toList())
		    );
		} catch (Exception e) {
			System.out.println("데이터베이스 작업 중 에러발생");
		}
		return result;
	}
	public void delete_schedule(int cal_no) {
			String sql = "delete from Calendar where cal_no=?;";
			PreparedStatement pstmt = null;
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,cal_no);
				pstmt.executeUpdate();
			} catch (Exception e) {
				System.out.println("데이터베이스 에러발생");
			}finally {
				try {
					if(pstmt !=null && !pstmt.isClosed())
						pstmt.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}

	} 

}
