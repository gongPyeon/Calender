package Calendar;

public class CalendarVO {
	private int cal_no;
	private int rush;
	private String title;
	private String date;
	private String tag;

	public CalendarVO(int cal_no, int rush, String title, String date, String tag) {
		super();
		this.cal_no = cal_no;
		this.rush = rush;
		this.title = title;
		this.date = date;
		this.tag = tag;
	}

	public CalendarVO(int rush, String title, String date, String tag) {
		super();
		this.rush = rush;
		this.title = title;
		this.date = date;
		this.tag = tag;
	}

	public CalendarVO() {

	}

	public int getCal_no() {
		return cal_no;
	}

	public void setCal_no(int cal_no) {
		this.cal_no = cal_no;
	}

	public int getRush() {
		return rush;
	}

	public void setRush(int rush) {
		this.rush = rush;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
}
