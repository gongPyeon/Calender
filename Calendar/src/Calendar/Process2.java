package Calendar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.Calendar;

public class Process2 {
	Calendar_Dao Calendar_Dao = new Calendar_Dao();
	int over = 0;
	Scanner sc = new Scanner(System.in);
	int menu;

	public Process2() {
		int start = 0;
		CalendarVO new_plan = new CalendarVO();// 일정 객체 생성

		start++;
		do {
			// 1. rush 설정 _rush 입력_ : 정수 특정 값 입력 *****1,0 이 아닌 숫자에 대해 오류 구문 추가
			Integer s;
			System.out.print("태그의 중요도를 설정해주세요(한가한/바쁜) > ");
			String rush = sc.nextLine().trim();
			if (rush.compareTo("한가한") == 0) {
				new_plan.setRush(0);
				s = (Integer) new_plan.getRush();
				if (s == 0)
					over++;
			} else if (rush.compareTo("바쁜") == 0) {
				new_plan.setRush(1);
				s = (Integer) new_plan.getRush();
				if (s == 1)
					over++;
			} else {
				System.out.println("입력 가능한 문자열이 아닙니다");
			}
			System.out.println("---------------------------------------------------");
		} while (start != over);

		//
		//
		int repeatSet = 0;
		start++;
		do {
			// 2. 반복 여부 부 프롬프트
			System.out.print("반복 일정을 지정하시겠습니까? > ");
			String repeat = sc.nextLine().trim();
			if (repeat.compareTo("유") == 0) {
				repeatSet = 1;
				over++;
			} else if (repeat.compareTo("무") == 0) {
				over++;
			} else {
				System.out.println("입력 가능한 문자열이 아닙니다");
				System.out.println("---------------------------------------------------");
			}
		} while (start != over);
		//
		//
		// 디버깅용 출력
//		System.out.println(d_split[0]);
//		System.out.println(d_split[1]);
//		System.out.println(d_split[2]);
		// 4.1 date 설정 _년도 입력_ : 기본 4자리 단,50 이하는 2000년대, 50 이상은 1900년대로 바꿔 인식헤 즙니다.
		// 4.2 date 설정 _월 입력_ : 기본 2자리 단, 길이가 하나인 경우는 앞 부분에 '0'을 붙여줍니다.
		// 4.3 date 설정 _일 입력_ : 기본 2자리 단, 길이가 하나인 경우는 앞 부분에 '0'을 붙여줍니다.
		//

		// 3. 반복 주기 입력 부 프롬프트
		String monORwk = "";
		String day = null;
		int date = 0;
		String dt = "";
		while (repeatSet == 1) {
			System.out.println("---------------------------------------------------");
			System.out.print("반복 주기 선택 > ");
			int repeatrepeat = 0;
			monORwk = sc.nextLine().trim();
			if (monORwk.compareTo("월간") == 0) {
				do {
					System.out.println("---------------------------------------------------");
					System.out.print("월 몇일마다 이 일정을 반복하시겠습니까? > ");
					dt = sc.nextLine().trim();
					dt = dt.replaceAll(" ", "");
					try {
						date = Integer.parseInt(dt);
						if ((date > 0 && date < 32)
								&& ((!dt.startsWith("0") && date > 9 && date < 32) || (date < 10 && date > 0))) {
//							System.out.println("일정 반복 등록: 매월 "+ date+"일");
							repeatSet = 0;
							repeatrepeat = 1;
						} else {
							System.out.println("---------------------------------------------------");
							System.out.println("월간 반복일을 다시 확인해주세요");
						}
					} catch (Exception e) {
						System.out.println("---------------------------------------------------");
						System.out.println("월간 반복일을 다시 확인해주세요");
					}
				} while (repeatrepeat == 0);
			} else if (monORwk.compareTo("주간") == 0) {
				do {
					System.out.println("---------------------------------------------------");
					System.out.print("반복할 요일을 입력해주세요 > ");
					day = sc.nextLine().trim();
					if (day.compareTo("월요일") == 0 || day.compareTo("화요일") == 0 || day.compareTo("수요일") == 0
							|| day.compareTo("목요일") == 0 || day.compareTo("금요일") == 0 || day.compareTo("토요일") == 0
							|| day.compareTo("일요일") == 0) {
//								System.out.println("일정 반복 등록: 매주 " + day);
						repeatSet = 0;
					} else {
						System.out.println("---------------------------------------------------");
						System.out.println("요일을 다시 확인해주세요");
					}
				} while (day.compareTo("월요일") != 0 && day.compareTo("화요일") != 0 && day.compareTo("수요일") != 0
						&& day.compareTo("목요일") != 0 && day.compareTo("금요일") != 0 && day.compareTo("토요일") != 0
						&& day.compareTo("일요일") != 0);
			} else
				System.out.println("입력 가능한 문자열이 아닙니다");
		}
		start++;
		String startDate = null;
		String[] startDate_s = null;
		String[] endDate_s = null;
		String endDate = null;
		do {
			try {
				// 4. date 설정 _총 입력_ : 년(공백)월(공백)일
				System.out.println("---------------------------------------------------");
				System.out.print("일정의 시작 날짜를 입력해주세요 > ");
				startDate = sc.nextLine();
				startDate = startDate.trim();
				startDate = startDate.replaceAll("\\s+", " ");

				startDate_s = startDate.split(" ");
				if (startDate_s[0].length() == 4 && startDate_s[0].substring(0, 2).equals("00")) {
					String sub = "";
					String prefix = "20";
					sub = startDate_s[0].substring(startDate_s[0].length() - 2, startDate_s[0].length());
					startDate_s[0] = prefix + sub;
				}

				if (startDate_s[0].length() == 2) {
					String prefix = "20";
					startDate_s[0] = prefix + startDate_s[0];
				} else if (startDate_s[0].length() > 4) {
					startDate_s[0].substring(startDate_s[0].length() - 2, startDate_s[0].length());
				}
				if (startDate_s[1].length() > 2) {
					Exception e = new Exception();
				}
				if (startDate_s[2].length() > 2) {
					Exception e = new Exception();
				}

				startDate = date(startDate_s);
				if (startDate == null && inrange(startDate_s) == 0) {
					System.out.println("---------------------------------------------------");
					System.out.println("날짜를 다시 확인해주세요");
					continue;
				}

				System.out.println("---------------------------------------------------");
				System.out.print("일정의 종료 날짜를 입력해주세요 > ");
				endDate = sc.nextLine();
				endDate = endDate.trim();
				endDate = endDate.replaceAll("\\s+", " ");

				endDate_s = endDate.split(" ");
				if (endDate_s[0].length() == 4 && endDate_s[0].substring(0, 2).equals("00")) {
					String sub = "";
					String prefix = "20";
					sub = endDate_s[0].substring(endDate_s[0].length() - 2, endDate_s[0].length());
					endDate_s[0] = prefix + sub;
				}

				if (endDate_s[0].length() == 2) {
					String prefix = "20";
					endDate_s[0] = prefix + endDate_s[0];
				} else if (endDate_s[0].length() > 4) {
					endDate_s[0].substring(endDate_s[0].length() - 2, endDate_s[0].length());
				}
				if (endDate_s[1].length() > 2) {
					Exception e = new Exception();
				}
				if (endDate_s[2].length() > 2) {
					Exception e = new Exception();
				}

				endDate = date(endDate_s);
				if (endDate == null && inrange(startDate_s) == 0) {
					System.out.println("---------------------------------------------------");
					System.out.println("날짜를 다시 확인해주세요");
					continue;
				}

				else {
					if (inrange(endDate_s) == 0) {
						System.out.println("---------------------------------------------------");
						System.out.println("날짜를 다시 확인해주세요");
						continue;
					}

				}
				if (date_compare(startDate, endDate)) {// 앞서는 경우
					System.out.println("---------------------------------------------------");
					System.out.println("종료 날짜가 시작 날짜를 앞섭니다. 날짜를 다시 확인해주세요");
					continue;
				} else if (startDate.equals(endDate)) {// 같은 경우
					if (monORwk.equals("")) {
						new_plan.setDate(startDate);
						if (new_plan.getRush() == 1) {// 바쁜 태그 저장
							if (!rush_check(new_plan) && new_plan.getRush() == 1) {
								System.out.println("---------------------------------------------------");
								System.out.println("일정을 추가하려는 날짜에 이미 바쁜 태그가 존재합니다. 일정 추가 기능을 종료합니다");
								menu += 1;
								return;
							} else {
								over++;
							}
						} else {// 한가한 태그 저장
							over++;
						}

					} else {
						System.out.println("---------------------------------------------------");
						System.out.println("반복주기를 사용할 수 없습니다.");
						continue;
					}

				} else {// 시작날짜 < 종료날짜 + (월간 반복)
					if (new_plan.getRush() == 1) {
						if (monORwk.compareTo("주간") == 0) {
							if (weekProCheck(new_plan, day, startDate, endDate) == 1)
								System.out.println("---------------------------------------------------");
							return;
						} else if (monORwk.compareTo("월간") == 0) {
							if (monthProCheck(new_plan, date, startDate_s, endDate_s) == 1) {
								System.out.println("---------------------------------------------------");
								return;
							}
						} else {
							if (continueProCheck(new_plan, startDate, endDate) == 1) {
								System.out.println("---------------------------------------------------");
								return;
							}
						}
					}

					over++;
				}
				//

			} catch (Exception e) {
				System.out.println("잘못된 입력입니다");
			}
		} while (start != over);

		//
		System.out.println("---------------------------------------------------");
		start++;
		do {
			// 5. title 설정 _title 입력_ : 문자열
			System.out.print("할 일을 입력해주세요 > ");
			String title = sc.nextLine().trim();
			if (title.length() == 0) {
				System.out.println("공백류만을 사용해 할 일을 입력할 수 없습니다");
			} else {
				new_plan.setTitle(title);
				String str = new_plan.getTitle();
				if (str.length() != 0)
					over++;

			}
		} while (start != over);
		//
		//
		System.out.println("---------------------------------------------------");
		start++;
		ArrayList<String> tag = new ArrayList<String>();
		tag.add("학업");
		tag.add("운동");
		tag.add("여가");
		do {
			// 6. tag 설정 _tag 입력_ : 문자열

			String str = "";
			for (String t : tag) {
				System.out.print(t + " | ");
			}
			System.out.println("카테고리추가");
			System.out.print("카테고리를 입력해주세요 > ");
			str = sc.nextLine().trim();
			if (str.length() > 10) {
				continue;
			}
			String test = str;
			if(!(test.trim().length()==0))
				str = str.trim();
//			System.out.println(str);
			int count_num = 0;
			while (count_num == 0) {
				for (String t : tag) {
					if (str.compareTo(t) == 0) {
						new_plan.setTag(t);
						over++;
						count_num++;
						break;
					}
				}
				if (str.compareTo("카테고리추가") == 0) {
					System.out.print("카테고리를 추가해주세요 > ");
					String s = sc.nextLine();
					if (s.length() > 10) {
						continue;
					}
					test = s;
					if(!(test.trim().length()==0))
						s = s.trim();
					if (!tag.contains(s) && !s.contains(" ")&&s.length()!=0) {
						tag.add(s);
						break;
					}
				}else {
					break;
				}
			}

		} while (start != over);

		System.out.println("---------------------------------------------------");
		if (monORwk.equals("월간"))

		{
			monthProcess(new_plan, date, startDate_s, endDate_s);
			menu += 1;// while문 종료시키기
			System.out.println("일정이 등록되었습니다.");
		} else if (monORwk.equals("주간")) {
			weekProcess(new_plan, day, startDate, endDate);
			menu += 1;
			System.out.println("일정이 등록되었습니다.");
		} else if (monORwk.equals("") && !startDate.equals(endDate)) {
			continuedProcess(new_plan, startDate, endDate);
			menu += 1;
			System.out.println("일정이 등록되었습니다.");
		} else {
			menu += 1;// while문 종료시키기
			Calendar_Dao.Insert_Calendar(new_plan);
			System.out.println("일정이 등록되었습니다.");
		}
		//
		//

	}

	public int inrange(String[] startDate) {
		int result = 1;
		switch (Integer.parseInt(startDate[1])) {
		case 1:
			if (Integer.parseInt(startDate[2]) > 31 || Integer.parseInt(startDate[2]) < 1)
				result = 0;
			break;
		case 2:
			if (Integer.parseInt(startDate[0]) % 4 == 0 && Integer.parseInt(startDate[0]) % 100 != 0
					|| Integer.parseInt(startDate[0]) % 400 == 0) {
				if (Integer.parseInt(startDate[2]) > 29 || Integer.parseInt(startDate[2]) < 1)
					result = 0;
				break;
			} else {
				if (Integer.parseInt(startDate[2]) > 28 || Integer.parseInt(startDate[2]) < 1)
					result = 0;
				break;
			}
		case 3:
			if (Integer.parseInt(startDate[2]) > 31 || Integer.parseInt(startDate[2]) < 1)
				result = 0;
			break;
		case 4:
			if (Integer.parseInt(startDate[2]) > 30 || Integer.parseInt(startDate[2]) < 1)
				result = 0;
			break;
		case 5:
			if (Integer.parseInt(startDate[2]) > 31 || Integer.parseInt(startDate[2]) < 1)
				result = 0;
			break;
		case 6:
			if (Integer.parseInt(startDate[2]) > 30 || Integer.parseInt(startDate[2]) < 1)
				result = 0;
			break;
		case 7:
			if (Integer.parseInt(startDate[2]) > 31 || Integer.parseInt(startDate[2]) < 1)
				result = 0;
			break;
		case 8:
			if (Integer.parseInt(startDate[2]) > 31 || Integer.parseInt(startDate[2]) < 1)
				result = 0;
			break;
		case 9:
			if (Integer.parseInt(startDate[2]) > 30 || Integer.parseInt(startDate[2]) < 1)
				result = 0;
			break;
		case 10:
			if (Integer.parseInt(startDate[2]) > 31 || Integer.parseInt(startDate[2]) < 1)
				result = 0;
			break;
		case 11:
			if (Integer.parseInt(startDate[2]) > 30 || Integer.parseInt(startDate[2]) < 1)
				result = 0;
			break;
		case 12:
			if (Integer.parseInt(startDate[2]) > 31 || Integer.parseInt(startDate[2]) < 1)
				result = 0;
			break;
		}
		return result;

	}

	public int monthProCheck(CalendarVO new_plan, int date, String[] startDate_s, String[] endDate_s) {
		String startDate = startDate_s[0] + " " + startDate_s[1] + " " + startDate_s[2];
		String endDate = endDate_s[0] + " " + endDate_s[1] + " " + endDate_s[2];
		Calendar c = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyy MM dd");
		Date start = null, end = null;
		try {
			start = df.parse(startDate);
			end = df.parse(endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		c.setTime(start);

		String addDate = "";
		addDate = df.format(c.getTime());
		c.add(Calendar.DAY_OF_MONTH, -1);
		do {
			c.add(Calendar.DAY_OF_MONTH, 1);
			addDate = df.format(c.getTime());
			if (c.get(c.DAY_OF_MONTH) == date && c.getTime().before(end)) {
				CalendarVO new_plan_copy1 = new CalendarVO(new_plan.getRush(), new_plan.getTitle(), addDate,
						new_plan.getTag());
				if (!rush_check(new_plan_copy1)) {
					System.out.println("일정을 추가하려는 날짜에 이미 바쁜 태그가 존재합니다. 일정 추가 기능을 종료합니다");
					menu += 1;
					return 1;
				}
			}
		} while (c.getTime().before(end));
		return 0;
	}

	public void monthProcess(CalendarVO new_plan, int date, String[] startDate_s, String[] endDate_s) {

		String startDate = startDate_s[0] + " " + startDate_s[1] + " " + startDate_s[2];
		String endDate = endDate_s[0] + " " + endDate_s[1] + " " + endDate_s[2];
		Calendar c = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyy MM dd");
		Date start = null, end = null;
		try {
			start = df.parse(startDate);
			end = df.parse(endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		c.setTime(start);

		String addDate = "";
		addDate = df.format(c.getTime());
		c.add(Calendar.DAY_OF_MONTH, -1);
		do {
			c.add(Calendar.DAY_OF_MONTH, 1);
			addDate = df.format(c.getTime());
			if (c.get(Calendar.DAY_OF_MONTH) == date && c.getTime().before(end)) {
				CalendarVO new_plan_copy1 = new CalendarVO(new_plan.getRush(), new_plan.getTitle(), addDate,
						new_plan.getTag());
				Calendar_Dao.Insert_Calendar(new_plan_copy1);
			} else if (date > 28 && c.getTime().before(end)
					&& c.getActualMaximum(Calendar.DAY_OF_MONTH) == c.get(Calendar.DAY_OF_MONTH)) {
				CalendarVO new_plan_copy1 = new CalendarVO(new_plan.getRush(), new_plan.getTitle(), addDate,
						new_plan.getTag());
				Calendar_Dao.Insert_Calendar(new_plan_copy1);
			}
		} while (c.getTime().before(end));

	}

	public int weekProCheck(CalendarVO new_plan, String day, String startDate, String endDate) {
		Calendar c = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyy MM dd");
		Date start = null, end = null;
		try {
			start = df.parse(startDate);
			end = df.parse(endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		c.setTime(start);

		String addDate = "";
		addDate = df.format(c.getTime());
		c.add(Calendar.DAY_OF_MONTH, -1);
		do {
			c.add(Calendar.DAY_OF_MONTH, 1);
			addDate = df.format(c.getTime());
			if (getDayOfWeek(addDate).equals(day) && c.getTime().before(end)) {
				CalendarVO new_plan_copy1 = new CalendarVO(new_plan.getRush(), new_plan.getTitle(), addDate,
						new_plan.getTag());
				if (!rush_check(new_plan_copy1)) {
					System.out.println("일정을 추가하려는 날짜에 이미 바쁜 태그가 존재합니다. 일정 추가 기능을 종료합니다");
					menu += 1;
					return 1;
				}
			}
		} while (c.getTime().before(end));
		return 0;
	}

	public void weekProcess(CalendarVO new_plan, String day, String startDate, String endDate) {
		Calendar c = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyy MM dd");
		Date start = null, end = null;
		try {
			start = df.parse(startDate);
			end = df.parse(endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		c.setTime(start);

		String addDate = "";
		addDate = df.format(c.getTime());
		c.add(Calendar.DAY_OF_MONTH, -1);
		do {
			c.add(Calendar.DAY_OF_MONTH, 1);
			addDate = df.format(c.getTime());
			if (getDayOfWeek(addDate).equals(day) && c.getTime().before(end)) {
				CalendarVO new_plan_copy1 = new CalendarVO(new_plan.getRush(), new_plan.getTitle(), addDate,
						new_plan.getTag());
				Calendar_Dao.Insert_Calendar(new_plan_copy1);
			}
		} while (c.getTime().before(end));
	}

	private int continueProCheck(CalendarVO new_plan, String startDate, String endDate) {
		Calendar c = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyy MM dd");
		Date start = null, end = null;
		try {
			start = df.parse(startDate);
			end = df.parse(endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		c.setTime(start);

		String addDate = "";
		addDate = df.format(c.getTime());
		c.add(Calendar.DAY_OF_MONTH, -1);
		do {
			c.add(Calendar.DAY_OF_MONTH, 1);
			addDate = df.format(c.getTime());
			CalendarVO new_plan_copy1 = new CalendarVO(new_plan.getRush(), new_plan.getTitle(), addDate,
					new_plan.getTag());
			if (!rush_check(new_plan_copy1)) {
				System.out.println("일정을 추가하려는 날짜에 이미 바쁜 태그가 존재합니다. 일정 추가 기능을 종료합니다");
				return 1;
			}
		} while (c.getTime().before(end));
		return 0;
	}

	private void continuedProcess(CalendarVO new_plan, String startDate, String endDate) {
		// TODO Auto-generated method stub
		Calendar c = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyy MM dd");
		Date start = null, end = null;
		try {
			start = df.parse(startDate);
			end = df.parse(endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		c.setTime(start);

		String addDate = "";
		addDate = df.format(c.getTime());
		c.add(Calendar.DAY_OF_MONTH, -1);
		do {
			c.add(Calendar.DAY_OF_MONTH, 1);
			addDate = df.format(c.getTime());
			CalendarVO new_plan_copy1 = new CalendarVO(new_plan.getRush(), new_plan.getTitle(), addDate,
					new_plan.getTag());
			Calendar_Dao.Insert_Calendar(new_plan_copy1);
		} while (c.getTime().before(end));
	}

	public String getDayOfWeek(String dateStr) { // 해당 날짜 요일 찾아주는 함수 0부터 일요일 ~ 6이 토요일
		SimpleDateFormat format = new SimpleDateFormat("yyyy MM dd");
		Date date = null;
		try {
			date = format.parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		String[] daysOfWeek = new String[] { "일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일" };
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == 1)
			return "일요일";
		if (dayOfWeek == 2)
			return "월요일";
		if (dayOfWeek == 3)
			return "화요일";
		if (dayOfWeek == 4)
			return "수요일";
		if (dayOfWeek == 5)
			return "목요일";
		if (dayOfWeek == 6)
			return "금요일";
		if (dayOfWeek == 7)
			return "토요일";
		else
			return "";
	}

	public boolean rush_check(CalendarVO calendarVO) {
		int count = 0;
		ArrayList<CalendarVO> list = new ArrayList<CalendarVO>();
		list = Calendar_Dao.get_Schedule_By_Day(calendarVO.getDate());
		for (CalendarVO VO : list) {
			if (VO.getRush() == 1)
				count++;
		}
		if (count >= 1)
			return false;
		else
			return true;
	}

	public String date(String d_split[]) {
		String p = null;
		if (d_split.length != 3) {
		} else if (Integer.parseInt(d_split[0]) < 1902 || Integer.parseInt(d_split[0]) > 2037
				|| Integer.parseInt(d_split[1]) > 12 || Integer.parseInt(d_split[1]) < 1
				|| Integer.parseInt(d_split[2]) > 31 || Integer.parseInt(d_split[2]) < 1) {
		} else if (Integer.parseInt(d_split[0]) % 4 == 0
				&& (Integer.parseInt(d_split[1]) == 2 || Integer.parseInt(d_split[1]) == 02)
				&& Integer.parseInt(d_split[2]) == 29) {
			p = d_split[0] + " 02 29";
		} else if (Integer.parseInt(d_split[1]) == 2 && Integer.parseInt(d_split[2]) > 28) {
		} else if ((Integer.parseInt(d_split[1]) == 4 && Integer.parseInt(d_split[2]) > 30)
				&& (Integer.parseInt(d_split[1]) == 6 && Integer.parseInt(d_split[2]) > 30)
				&& (Integer.parseInt(d_split[1]) == 9 && Integer.parseInt(d_split[2]) > 30)
				&& (Integer.parseInt(d_split[1]) == 11 && Integer.parseInt(d_split[2]) > 30)) {
		} else if (d_split[0].length() == 4 && d_split[1].length() == 2 && d_split[2].length() == 2) {
			p = d_split[0] + " " + d_split[1] + " " + d_split[2];
		} else if (d_split[0].length() == 4 && d_split[1].length() == 2 && d_split[2].length() == 1) {
			d_split[2] = "0" + d_split[2];
			p = d_split[0] + " " + d_split[1] + " " + d_split[2];
		} else if (d_split[0].length() == 4 && d_split[1].length() == 1 && d_split[2].length() == 2) {
			d_split[1] = "0" + d_split[1];
			p = d_split[0] + " " + d_split[1] + " " + d_split[2];

		} else if (d_split[0].length() == 4 && d_split[1].length() == 1 && d_split[2].length() == 1) {
			d_split[1] = "0" + d_split[1];

			d_split[2] = "0" + d_split[2];
			p = d_split[0] + " " + d_split[1] + " " + d_split[2];

		} else if (d_split[0].length() == 2 && d_split[1].length() == 2 && d_split[2].length() == 2) {
			if (Integer.parseInt(d_split[0]) < 50) {
				d_split[0] = "20" + d_split[0];
			} else if (Integer.parseInt(d_split[0]) > 50) {
				d_split[0] = "19" + d_split[0];
			}

			p = d_split[0] + " " + d_split[1] + " " + d_split[2];

		} else if (d_split[0].length() == 2 && d_split[1].length() == 2 && d_split[2].length() == 1) {
			if (Integer.parseInt(d_split[0]) < 50) {
				d_split[0] = "20" + d_split[0];
			} else if (Integer.parseInt(d_split[0]) > 50) {
				d_split[0] = "19" + d_split[0];
			}

			d_split[2] = "0" + d_split[2];

			p = d_split[0] + " " + d_split[1] + " " + d_split[2];

		} else if (d_split[0].length() == 2 && d_split[1].length() == 1 && d_split[2].length() == 2) {
			if (Integer.parseInt(d_split[0]) < 50) {
				d_split[0] = "20" + d_split[0];
			} else if (Integer.parseInt(d_split[0]) > 50) {
				d_split[0] = "19" + d_split[0];
			}

			d_split[1] = "0" + d_split[1];

			p = d_split[0] + " " + d_split[1] + " " + d_split[2];

		} else if (d_split[0].length() == 2 && d_split[1].length() == 1 && d_split[2].length() == 1) {
			if (Integer.parseInt(d_split[0]) < 50) {
				d_split[0] = "20" + d_split[0];
			} else if (Integer.parseInt(d_split[0]) > 50) {
				d_split[0] = "19" + d_split[0];
			}

			d_split[1] = "0" + d_split[1];
			d_split[2] = "0" + d_split[2];

			p = d_split[0] + " " + d_split[1] + " " + d_split[2];

		} else {
		}
		return p;
	}

	public boolean date_compare(String date1, String date2) {
		String d1_split[] = date1.split(" ");
		String d2_split[] = date2.split(" ");
		if (Integer.parseInt(d1_split[0]) > Integer.parseInt(d2_split[0]))
			return true;
		else if (Integer.parseInt(d1_split[0]) == Integer.parseInt(d2_split[0])) {
			if (Integer.parseInt(d1_split[1]) > Integer.parseInt(d2_split[1])) {
				return true;
			} else if (Integer.parseInt(d1_split[1]) == Integer.parseInt(d2_split[1])) {
				if (Integer.parseInt(d1_split[2]) > Integer.parseInt(d2_split[2])) {
					return true;
				} else
					return false;
			} else
				return false;
		} else
			return false;
	}
}
