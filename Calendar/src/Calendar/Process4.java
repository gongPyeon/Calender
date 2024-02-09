package Calendar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Calendar;

public class Process4 {
	Calendar_Dao Calendar_Dao = new Calendar_Dao();
	int over = 0;
	Scanner sc = new Scanner(System.in);
	int menu;

	public Process4() {
		int start = 0;
//			6.7 태그검색 명령군
//			0. 태그 검색 시작
		System.out.println("Search for tags (태그검색)");
		start++;
//			6.7.1 부 프롬프트 1: 검색 기간 입력 부 프롬프트
//			1. 검색 기간 입력
		String period;
		String period_s[];
		int year = 0;
		int month = 0;
		do {
			try {
				System.out.println("---------------------------------------------------");
				System.out.print("검색 기간을 입력해주세요 (년/년+월) > ");
				period = sc.nextLine().trim().replaceAll("\\s+", " ");

				period_s = period.split(" ");

				if (period_s.length > 2) {
//					System.out.println("문자열 배열 길이가 2를 넘음");
					throw new Exception();
				}

				if (period_s[0].length() > 4) {
//					System.out.println("연도 문자열 길이가 4자릿수를 넘음");
					throw new Exception();
				}
				if (period_s.length == 2) {
//					입력된 문자열이 2단어(띄어쓰기 기준)일 때
					if (period_s[1].length() > 2) {
//						System.out.println("월 문자열 길이가 2자릿수를 넘음 ");
						throw new Exception();
					}
				}

//				연도가 00XX 일때 20XX로 변경
				if (period_s[0].length() == 4 && period_s[0].substring(0, 2).equals("00")) {
					String sub = "";
					String prefix = "20";
					sub = period_s[0].substring(period_s[0].length() - 2, period_s[0].length());
					period_s[0] = prefix + sub;
				}
//				연도가 2자릿수일 때 2000년대로 변경
				if (period_s[0].length() == 2) {
					String prefix = "20";
					period_s[0] = prefix + period_s[0];
				}

//				숫자 변환 테스트(숫자 아니라면 오류 -> 다시 입력)
				year = Integer.parseInt(period_s[0]);
				month = 0;
				if (period_s.length == 2)
					month = Integer.parseInt(period_s[1]);

//				연도와 월이 범위에 속하지 않은 경우 오류
				if (!(year > 1902 && year < 2037)) {
					throw new Exception();
				}
				if (period_s.length == 2) {
					if (!(month > 0 && month < 13)) {
						throw new Exception();
					}
				}
			} catch (Exception e) {
				System.out.println("잘못 입력하셨습니다.");
				continue;
			}
//			System.out.println(period);
			over++;
		} while (start != over);

//		6.7.2 부 프롬프트 2: 태그 입력(한가한, 바쁜) 부 프롬프트
//		2. 검색할 한가한/바쁜 입력
		start++;
		String rush;
		int rushNum = 0;
		do {
			System.out.println("---------------------------------------------------");
			System.out.print("태그(한가한/바쁜)를 입력해주세요 > ");
			rush = sc.nextLine();
			if (rush.length() > 10) {
				continue;
			}
			rush = rush.trim().replaceAll("\\s", "");
			if (rush.equals("한가한") || rush.equals("바쁜")) {
				if (rush.equals("바쁜")) {
					rushNum = 1;
				}
				over++;
			}
		} while (start != over);

//		6.7.3 부 프롬프트 3: 태그 입력(운동, 여가, 학업, 사용자 추가 카테고리 태그) 부 프롬프트
//		3. 검색할 특정 태그 입력
		start++;
		ArrayList<String> taglist = new ArrayList<String>();
		taglist.add("학업");
		taglist.add("운동");
		taglist.add("여가");
		ArrayList result = Calendar_Dao.Group_Of_Tag();
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd");
		do {
			System.out.println("---------------------------------------------------");
			System.out.print("태그(");
			for (String t : taglist) {
				System.out.print(t);
				if (t == taglist.get(taglist.size() - 1))
					continue;
				System.out.print("/");
			}
			System.out.print(")를 입력해주세요 > ");
			String tag = sc.nextLine();
			if (tag.length() > 10)
				continue;
			tag = tag.trim().replaceAll(" ", "");
			if (!result.contains(tag) && !taglist.contains(tag)) {
				continue;
			}
			System.out.println("---------------------------------------------------");
			c.set(year, 0, 1);
			if (month == 0) {
				System.out.println(year);
				for (int i = 1; i < 13; i++) {
					c.set(year, i - 1, 1);
					System.out.println(i + "월 : <" + rush + ", " + tag + "> 태그 할일 개수");
					for (int j = 1; j < 8; j++) {
						for (int k = j; k <= c.getActualMaximum(Calendar.DATE); k += 7) {
							c.set(year, i - 1, k);
							if (k < 10) {
								System.out.print("0" + k);
							} else {
								System.out.print(k);
							}
							System.out.print(" : ");
							System.out.print(Calendar_Dao.Count_Of_schedule(sdf.format(c.getTime()), rushNum, tag));
							System.out.print("\t");
						}
						System.out.println();
					}
					if (i < 12)
						System.out.println();
					else
						System.out.println("---------------------------------------------------");
				}
				over++;
			} else {
				if (month < 10)
					System.out.println(year + " 0" + month);
				else
					System.out.println(year + " " + month);
				c.set(year, month - 1, 1);
				System.out.println(month + "월 : <" + rush + ", " + tag + "> 태그 할일 개수");
				for (int j = 1; j < 8; j++) {
					for (int k = j; k <= c.getActualMaximum(Calendar.DATE); k += 7) {
						c.set(year, month - 1, k);
						if (k < 10) {
							System.out.print("0" + k);
						} else {
							System.out.print(k);
						}
						System.out.print(" : ");
						System.out.print(Calendar_Dao.Count_Of_schedule(sdf.format(c.getTime()), rushNum, tag));
						System.out.print("\t");
					}
					System.out.println();
				}
				System.out.println("---------------------------------------------------");
				/*
				 * Calendar_Dao dao = new Calendar_Dao(); ArrayList result = dao.Group_Of_Tag();
				 * for(Object e : result) System.out.println((String)e);
				 */
				over++;
			}

		} while (start != over);

	}
}