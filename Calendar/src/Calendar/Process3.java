package Calendar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Process3 {
	
	Scanner scan = new Scanner(System.in);
	
	ArrayList<String> tag = new ArrayList<String>();
	ArrayList<String> tempTag = new ArrayList<String>();
	Calendar_Dao calendar_Dao = new Calendar_Dao();
	
	public Process3() {
        
//        System.out.println("일정이 성공적으로 업데이트되었습니다.");
		if(tempTag.contains("운동")) {
			tempTag.remove("운동");
		}
		if(tempTag.contains("학업")) {
			tempTag.remove("학업");
		}
		if(tempTag.contains("여가")) {
			tempTag.remove("여가");
		}
		tag.add("학업");
		tag.add("운동");
		tag.add("여가");
		
		tag.addAll(tempTag);

		String date_string = "";
		CalendarVO edit_event=null;
		Scanner scan = new Scanner(System.in);
		int date[] = date_input_func();
		if (date[1] <= 9) { // 날짜 년월일 단위로 띄어쓰기 후 & 0붙여준 후 스트링으로 만듦
			if (date[2] <= 9)
				date_string += Integer.toString(date[0]) + " 0" + Integer.toString(date[1]) + " 0"
						+ Integer.toString(date[2]);
			else if (date[2] > 9)
				date_string += Integer.toString(date[0]) + " 0" + Integer.toString(date[1]) + " "
						+ Integer.toString(date[2]);
		} else if (date[1] > 9) {
			if (date[2] <= 9)
				date_string += Integer.toString(date[0]) + " " + Integer.toString(date[1]) + " 0"
						+ Integer.toString(date[2]);
			else if (date[2] > 9)
				date_string += Integer.toString(date[0]) + " " + Integer.toString(date[1]) + " "
						+ Integer.toString(date[2]);
		}
		System.out.println(date_string);

		ArrayList<CalendarVO> event = new ArrayList<>(); // 해당 날짜에 있는 일정들 저장할 arrayList
		event = calendar_Dao.get_Schedule_By_Day(date_string); // 이상하게도 이 부분에서 오류가 나서 CalanderDao에 있는
													// get_schedule_by_day함수와 conn를 static으로 바꿈
		System.out.println("---------------------------------------------------");
		System.out.println("등록된 일정 확인 >");
		if (event.size() == 0) { // 해당 날짜에 아무 일정도 없을때
			System.out.println("해당 날짜에 아무 일정도 없습니다.");
		} else { // 해당 날짜에 일정이 있을떄
			boolean not_exist = true;
			for (CalendarVO k : event) {
				System.out.println(k.getTitle());
			}
			while (not_exist) { // 존재하는 일정 제목 입력할떄까지 반복
				System.out.print("편집할 일정 제목을 입력해주세요 >");
				String eventName = scan.nextLine().trim();				
				for (CalendarVO k : event) {
					if (k.getTitle().equals(eventName)) {
						edit_event = k; // 편집할 일정 저장
						not_exist = false;
					}
				}
			}
			while(true) {
				System.out.println("---------------------------------------");
				System.out.println("편집할 내용의 번호를 입력해주세요.");
				System.out.println("1) 일정 제목");
				System.out.println("2) 중요도 태그");
				System.out.println("3) 카테고리 태그");
				System.out.println("4) 해당 일정 삭제");
				System.out.println("5) 나가기");
				boolean correctAnsw=false;
				int answ=0;
				while(correctAnsw==false) {
					System.out.print("> ");
					String str = scan.nextLine().trim();
					if(is_numeric_for_process3(str)) {
						answ=Integer.parseInt(str);
						if(answ>0&&answ<6) {
							correctAnsw=true;
						}
					}
				}
				if(answ==1) {
					edit_event_title(edit_event);
				}else if(answ==2) {
					edit_importance_tag(edit_event);
				}else if(answ==3) {
					edit_category_tag(edit_event);
				}else if(answ==4) {
					delete_event(edit_event);
					break;
				}else if(answ==5) {
					System.out.println("일정 편집이 종료되었습니다 ");
					break;
				}
			}
			
		}

	}
	
	
	public void edit_event_title(CalendarVO k) {
		while(true) {
			try {
				System.out.print("수정될 일정 제목을 입력해주세요 > ");
		        String newTitle = scan.nextLine().trim();
		        System.out.println("---------------------------------------------------");
		        if(newTitle.length()==0) {
		        	throw new Exception();
		        }
		        k.setTitle(newTitle);
		        int cal_no = k.getCal_no();
		        calendar_Dao.update_Title(newTitle, cal_no);
		        System.out.println("일정이 편집되었습니다.");
		        break;
			} catch (Exception e) {
				System.out.println("공백류만을 사용해 할 일을 입력할 수 없습니다");
			}
		}
	}
    

	public void edit_category_tag(CalendarVO k) { //카테고리 태그 편집 함수 
		boolean correctAnsw=false;
		System.out.println();
		while(correctAnsw==false) {
			for (String t : tag) {
				System.out.print(t + " | ");
			}
			System.out.println("카테고리추가");
			System.out.print("카테고리를 입력해주세요 >");
			String str = null; 
			str = scan.nextLine();
			if(is_valid_tag(str)) {
				str = str.trim();
				for(int i=0;i<tag.size();i++) {
					if(str.equals(tag.get(i))) {
						correctAnsw=true;
						k.setTag(str);
						calendar_Dao.update_Tag(k);
						System.out.println("일정이 편집되었습니다.");
						break;
					}else if(str.equals("카테고리추가")) {
						System.out.print("카테고리를 추가해주세요 >");
						String temp = scan.nextLine();
						if(is_valid_additional_tag(temp)) 
							tag.add(temp.trim());
							break;	
					}
				}
			}
		}
		
	}
	public boolean is_valid_additional_tag(String inputString) {
		if(inputString.length()>10)
			return false;
		inputString = inputString.trim();
		if(inputString.contains(" "))
			return false;
		if(inputString.length()==0)
			return false;
		else
			return true;
	}

	public boolean is_valid_tag(String inputString) {

		if(inputString.length()>10)
			return false;
		String input =  inputString.trim();
		if(input.equals("카테고리추가"))
			return true;
		if(input.length()==0)
			return false;
		for(String e : tag) {
			if(e.equals(input))
				return true;
		}
		if(input.equals("카테고리추가"))
			return true;
		else
			return false;
	}
	
	
	public void delete_event(CalendarVO k) { //일정 삭제 함수 (미완성) 
		Calendar_Dao dao= new Calendar_Dao();
		dao.delete_schedule(k.getCal_no());
		System.out.println("해당 일정이 삭제되었습니다");
	}
	
	public static boolean is_leap_year(int year) { // 윤년인지 아닌지 확인하는 함수
		if (year % 4 == 0) {
			if (year % 100 == 0 && year % 400 == 0) {
				return true;
			} else if (year % 100 == 0 && year % 400 != 0) {
				return false;
			} else if (year % 100 != 0) {
				return true;
			}
		}
		return false;
	}

	public static boolean is_valid_date(int[] date) { // 유효한 날짜인지 예외처리 함수
		if (date[0] < 1902 || date[0] > 2037) {
			System.out.println("허용되지 않는 년도를 입력하셨습니다. 날짜를 다시 입력해주세요.");
			return true; // 년도가 1902~2037 범위를 벗어남
		}
		if (date[1] < 1 || date[1] > 12) {
			if (date[1] != -1) {
				System.out.println("존재하지 않는 달을 입려하셨습니다. 날짜를 다시 입력해주세요.");
				return true; // 월이 1~12를 벗어남
			}
		}
		if (date[1] == 1 || date[1] == 3 || date[1] == 5 || date[1] == 7 || date[1] == 8 || date[1] == 10
				|| date[1] == 12) {
			if (date[2] != -1) {
				if (date[2] < 1 || date[2] > 31) {
					System.out.println("존재하지 않는 일수를 입력하셨습니다. 날짜를 다시 입력해주세요.");
					return true;
				}
			}
		} else if (date[1] == 4 || date[1] == 6 || date[1] == 9 || date[1] == 11) {
			if (date[2] != -1) {
				if (date[2] < 1 || date[2] > 30) {
					System.out.println("존재하지 않는 일수를 입력하셨습니다. 날짜를 다시 입력해주세요.");
					return true;
				}
			}
		} else if (date[1] == 2) {
			if (is_leap_year(date[0])) { // 윤년일떄
				if (date[2] != -1) {
					if (date[2] < 1 || date[2] > 29) {
						System.out.println("윤년인 해는 날수를 1~29까지 입력할 수 있습니다. 날짜를 다시 입력해주세요.");
						return true;
					}
				}
			} else { // 윤년이 아닐떄
				if (date[2] != -1) {
					if (date[2] < 1 || date[2] > 28) {
						System.out.println("윤년이 아닌 해는 날수를 1~28까지 입력할 수 있습니다. 날짜를 다시 입력해주세요");
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean is_numeric(String str) {
		if (str == null) {
			return false;
		}
		return str.matches("[\\d\\s]+");
	}
	
	public static boolean is_numeric_for_process3(String str) {
		if (str == null) {
			return false;
		}else if(str.contains("0"))
			return false;
		return str.matches("[\\d\\s]+");
	}
	
    public static boolean isStringEmpty(String str) {
    	return str==null||str.trim().isEmpty();
    }


	@SuppressWarnings("resource")
	public static int[] date_input_func() { // 날짜 입력 클래스
		Scanner scan = new Scanner(System.in);
		boolean wrong = true;
		boolean unfinished = true;
		while (unfinished) {
			System.out.print("날짜를 입력하세요 > ");
			String str = new String();
			str = scan.nextLine();
			if (is_numeric(str)) {
				boolean under_two_four = true;
				StringBuffer sb = new StringBuffer();
				String str_split[];
				int result[] = { 0, -1, -1 };
				str = str.replaceAll("\t", "");
				for (int i = 0; i < str.length(); i++) {
					str = str.replace("  ", " ");
				}
				str = str.replace(" ", "a");
				if (str.charAt(0) == 'a') { // 맨앞의 공백제거
					sb.append(str);
					sb.deleteCharAt(0);
					str = sb.toString();
				}
				str_split = str.split("a");

				for (int i = 0; i < str_split.length; i++) {
					if (i == 0) {
						if (str_split[i].length() == 2 || str_split[i].length() == 4) { // 다음 단계로 ㄱㄱ
//							System.out.println("split[0] = 2 or 4");
						} else { // 다음 단계x
							System.out.println("잘못 입력하셨습니다.");
							under_two_four = false;
							break;
						}
					} else if (i == 1) {
						if (str_split[i].length() == 2 || str_split[i].length() == 1) {
//							System.out.println("split[0] = 2");
						} else {
							System.out.println("잘못 입력하셨습니다.");
							under_two_four = false;
							break;
						}
					} else if (i == 2) {
						if (str_split[i].length() == 2 || str_split[i].length() == 1) {
//							System.out.println("split[0] = 2 or 4");
						} else {
							System.out.println("잘못 입력하셨습니다.");
							under_two_four = false;
							break;
						}
					}
				}
				if (under_two_four) {
					for (int i = 0; i < str_split.length; i++) {
						result[i] = Integer.parseInt(str_split[i]);
					}
					if (result[0] < 100 && result[0] > 0) {
						result[0] += 2000;
					}
					unfinished = is_valid_date(result); // 날짜 유효하게 입력됬는지 판단해서 반복문 수행할지 안할지 정함
					if (!unfinished) {
						return result;
					}
				}
			} else {
				System.out.println("잘못 입력하셨습니다.");
			}
		}
		return null;
	}
	
	
	public static boolean isWhitespaceString(String str) {
	    if (str == null) {
	        return false;
	    }
	    
	    for (int i = 0; i < str.length(); i++) {
	        if (!Character.isWhitespace(str.charAt(i))) {
	            return false;
	        }
	    }
	    
	    return true;
	}
	

	public boolean rush_check(CalendarVO calendarVO) {
		int count = 0;
		ArrayList<CalendarVO> list = new ArrayList<CalendarVO>();
		list = calendar_Dao.get_Schedule_By_Day(calendarVO.getDate());
		for (CalendarVO VO : list) {
			if (VO.getRush() == 1)
				count++;
		}
		if (count >= 1)
			return false;
		else
			return true;
	}
	
	public boolean containsWhitespace(String str) {
	    if (str == null) {
	        return false;
	    }
	    
	    for (int i = 0; i < str.length(); i++) {
	        if (Character.isWhitespace(str.charAt(i))) {
	            return true;
	        }
	    }
	    
	    return false;
	}
	
	
	public void edit_importance_tag(CalendarVO k) {
		while(true) {
		    System.out.print("태그의 중요도를 설정해주세요(한가한/바쁜) >");
		    String Rush = scan.next().trim();
		    System.out.println("---------------------------------------------------");
		    int x = 0;
		    if (Rush.compareTo("한가한") == 0) {
		        x = 0;
		        k.setRush(x);
		        System.out.println("일정이 편집되었습니다.");
		        System.out.println("---------------------------------------------------");
		        int cal_no = k.getCal_no();
		        calendar_Dao.update_rush(x, cal_no);
		        break;
		    } else if (Rush.compareTo("바쁜") == 0) {
		        x = 1;
		        if (rush_check(k)) {
		            k.setRush(x);
		            System.out.println("일정이 편집되었습니다.");
		            System.out.println("---------------------------------------------------");
		            int cal_no = k.getCal_no();
		            calendar_Dao.update_rush(x, cal_no);
		            break;
		        } else {
		            System.out.println("해당 날짜에 이미 '바쁜'태그가 존재합니다.");
		            System.out.println("---------------------------------------------------");
		            break;
		        } 
		    } else if (isWhitespaceString(Rush)==true){
		        System.out.println("입력 가능한 문자열이 아닙니다.");
		        System.out.println("---------------------------------------------------");	
		    } else {
		        System.out.println("입력 가능한 문자열이 아닙니다.");
		        System.out.println("---------------------------------------------------");
		    }
		}
	}
}
