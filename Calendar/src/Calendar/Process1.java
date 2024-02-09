package Calendar;

import java.util.Scanner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Process1 {
	static boolean next_move = true;

	public Process1() {

		while (next_move) {
			int date[] = date_input_func(); // 사용자로부터 날짜를 입력받는 함수입니다.
			if (date[0] != 0 && date[1] == -1 && date[2] == -1)
				print_year_calander(date);
			else if (date[0] != 0 && date[1] != -1 && date[2] == -1)
				print_month_calender(date);
			else if (date[0] != 0 && date[1] != -1 && date[2] != -1)
				print_day_calendar(date);
		}
		next_move = true;
	}

	public void print_day_calendar(int date[]) {
		Calendar_Dao Calendar_Dao = new Calendar_Dao();
		Scanner scan = new Scanner(System.in);
		String input_String = "";
		if (date[1] <= 9) {
			if (date[2] <= 9)
				input_String += Integer.toString(date[0]) + " 0" + Integer.toString(date[1]) + " 0"
						+ Integer.toString(date[2]);
			else if (date[2] > 9)
				input_String += Integer.toString(date[0]) + " 0" + Integer.toString(date[1]) + " "
						+ Integer.toString(date[2]);
		} else if (date[1] > 9) {
			if (date[2] <= 9)
				input_String += Integer.toString(date[0]) + " " + Integer.toString(date[1]) + " 0"
						+ Integer.toString(date[2]);
			else if (date[2] > 9)
				input_String += Integer.toString(date[0]) + " " + Integer.toString(date[1]) + " "
						+ Integer.toString(date[2]);
		}
		ArrayList<CalendarVO> result = new ArrayList<>();
		result = Calendar_Dao.get_Schedule_By_Day(input_String);
		for (CalendarVO k : result) {
			System.out.println("일정:" + k.getTitle());
		}
		if (result.size() == 0)
			System.out.println("해당 날짜에 등록된 일정이 없습니다");
		System.out.println();
		System.out.println("1) 날짜 다시 입력하기");
		System.out.println("2) 메인화면으로 돌아가기 ");
		boolean lego = true;
		String choice = new String();
		while (lego) {
			System.out.print("입력 > ");
			choice = scan.nextLine();
			choice = choice.trim();
			if (choice.equals("1")) {
				lego = false;
			} else if (choice.equals("2")) {
				lego = false;
				next_move = false;
			}
		}
	}

	public void print_month_calender(int date[]) {

		Calendar_Dao Calendar_Dao = new Calendar_Dao();
		Scanner scan = new Scanner(System.in);
		int year = date[0];
		int month = date[1];
		int dayOfWeek = 0;
		int daysOfMonth[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		int count = 1;
		int date_input[];
		boolean wrong = false;
		ArrayList result = new ArrayList<Calendar>();

		if (is_leap_year(year)) {
			daysOfMonth[1] = 29;
		}

		while (wrong == false) { // 월에 해당하는 달력 갖고와서 출력하는 구문
			if (year < 1902 || year > 2037 || month > 12 || month < 1) {
				System.out.println("1902~2037 사이에서 년도를 입력하고, 01~12 사이에서 월을 입력해주세요.");
				break;
			}
			System.out.printf("\n<<%4d년 %2d월>>\n", year, month);
			System.out.println("---------------------------------------------------");
			System.out.println("일\t월\t화\t수\t목\t금\t토");
			System.out.println("---------------------------------------------------");
			date_input = new int[] { year, month, 1 };
			dayOfWeek = getDayOfWeek(String.format("%d-%02d-%02d", year, month, 1));
			int[] result2 = new int[daysOfMonth[month - 1]];
			for (int i = 0; i < dayOfWeek; i++) {
				result2[i] = 0;
				System.out.print("00\t");
			}
			for (int i = 1; i <= daysOfMonth[month - 1]; i++) {
				String input_String = "";
				if (month <= 9) {
					input_String += String.format("%d 0%d %02d", year, month, i);
				} else {
					input_String += String.format("%d %d %02d", year, month, i);
				}
				result = Calendar_Dao.get_Schedule_By_Day(input_String);
				System.out.printf("%02d\t", i);
				if (count % 7 == (7 - dayOfWeek) % 7) {
					System.out.println();
				}
				count++;
				result2[i - 1] = result.size();
			}
			System.out.println("\n");
			int[][] calendar = new int[5][7];

			// 일 수만큼 반복하여 이차원 배열에 값을 저장합니다.
			int row = 0, col = 0;
			for (int i = 1; i <= daysOfMonth[month - 1]; i++) {
				calendar[row][col] = i;
				col++;
				if (col == 7) {
					row++;
					col = 0;
				}
			}

			// 이차원 배열의 값을 출력합니다.
			for (int i = 0; i < 7; i++) {
				for (int j = 0; j < 5; j++) {
					if (calendar[j][i] == 0) {
						System.out.print("     \t");
					} else {
						System.out.printf("%02d : %-2d\t", calendar[j][i], result2[calendar[j][i] - 1]);
					}
				}
				System.out.println();
			}
			break;
		}
		System.out.println();
		System.out.println("1) 날짜 다시 입력하기");
		System.out.println("2) 메인화면으로 돌아가기 ");
		boolean lego = true;
		String choice = new String();
		while (lego) {
			System.out.print("입력 > ");
			choice = scan.nextLine();
			choice = choice.trim();
			if (choice.equals("1")) {
				lego = false;
			} else if (choice.equals("2")) {
				lego = false;
				next_move = false;
			}
		}

	}

	public static void print_year_calander(int[] date_year) { // 날짜 데이터 입력받음 이전에 미리 무슨 함수 호출할지 정해야함 즉 미리 월 일데이터가 0 0
																// 이여야한다는 뜻
		int year = date_year[0];
		Scanner scan = new Scanner(System.in);
		boolean leap_year = is_leap_year(year);// 윤년계산 함수 먼저 만들고 윤년인지 아닌지 판단하기
		String str = Integer.toString(year) + "-";
		int limit_Feb;
		int yoil_of_Jan_first = getDayOfWeek(str + "01-01");
		int yoil_of_Feb_first = getDayOfWeek(str + "02-01");
		int yoil_of_Mar_first = getDayOfWeek(str + "03-01");
		int yoil_of_Apr_first = getDayOfWeek(str + "04-01");
		int yoil_of_May_first = getDayOfWeek(str + "05-01");
		int yoil_of_Jun_first = getDayOfWeek(str + "06-01");
		int yoil_of_Jul_first = getDayOfWeek(str + "07-01");
		int yoil_of_Aug_first = getDayOfWeek(str + "08-01");
		int yoil_of_Sep_first = getDayOfWeek(str + "09-01");
		int yoil_of_Oct_first = getDayOfWeek(str + "10-01");
		int yoil_of_Nov_first = getDayOfWeek(str + "11-01");
		int yoil_of_Dec_first = getDayOfWeek(str + "12-01");

		int month1_count = 01;
		int month2_count = 01;
		int month3_count = 01;
		int month4_count = 01;
		int month5_count = 01;
		int month6_count = 01;
		int month7_count = 01;
		int month8_count = 01;
		int month9_count = 01;
		int month10_count = 01;
		int month11_count = 01;
		int month12_count = 01;

		if (leap_year) {
			limit_Feb = 29;
		} else {
			limit_Feb = 28;
		}
		int count1 = 0;
		int count2 = 0;
		int count3 = 0;
		int count4 = 0;
		int count5 = 0;
		int count6 = 0;
		int count7 = 0;
		int count8 = 0;
		int count9 = 0;
		int count10 = 0;
		int count11 = 0;
		int count12 = 0;

		System.out.println(date_year[0] + "년도 달력입니다.");
		System.out.println();

		System.out.println("1월                     2월                     3월                    4월                ");
		System.out
				.println("--------------------   --------------------   --------------------  ----------------------");
		System.out.println(" 일 월 화 수 목 금 토       일 월 화 수 목 금 토        일 월 화 수 목 금 토      일 월 화 수 목 금 토  ");
		System.out
				.println("--------------------   --------------------   --------------------  ----------------------");
		for (int j = 0; j < 6; j++) {
			boolean start1 = true;
			boolean start2 = true;
			boolean start3 = true;
			boolean start4 = true;

			if (month1_count < 32) {
				for (int i = 0; i < yoil_of_Jan_first; i++) {
					System.out.print(String.format("%02d ", 0));
				}
				while (start1) {
					if (month1_count > 31) {
						count1 = (count1 + 1) % 7;
					} else {
						System.out.print(String.format("%02d ", month1_count));
					}
					yoil_of_Jan_first = (yoil_of_Jan_first + 1) % 7;
					if (0 == yoil_of_Jan_first % 7) {
						start1 = false;
					}
					++month1_count;
				}
				if (count1 != 0) {
					for (int k = 0; k < count1; k++) {
						System.out.print("   ");
					}
				}

			} else {
				System.out.print("                     ");
			}
			System.out.print("  ");
			// 1월 달력 한줄

			if (month2_count < limit_Feb + 1) {
				for (int i = 0; i < yoil_of_Feb_first; i++) {
					System.out.print(String.format("%02d ", 0));
				}
				while (start2) {
					if (month2_count > limit_Feb) {
						count2 = (count2 + 1) % 7;
					} else {
						System.out.print(String.format("%02d ", month2_count));
					}
					yoil_of_Feb_first = (yoil_of_Feb_first + 1) % 7;
					if (0 == yoil_of_Feb_first % 7) {
						start2 = false;
					}
					++month2_count;
				}
				if (count2 != 0) {
					for (int k = 0; k < count2; k++) {
						System.out.print("   ");
					}
				}
			} else {
				System.out.print("                     ");
			}

			System.out.print("  ");
			// 2월 달력 한줄

			if (month3_count < 32) {
				for (int i = 0; i < yoil_of_Mar_first; i++) {
					System.out.print(String.format("%02d ", 0));
				}
				while (start3) {
					if (month3_count > 31) {
						count3 = (count3 + 1) % 7;
					} else {
						System.out.print(String.format("%02d ", month3_count));
					}
					yoil_of_Mar_first = (yoil_of_Mar_first + 1) % 7;
					if (0 == yoil_of_Mar_first % 7) {
						start3 = false;
					}
					++month3_count;
				}
				if (count3 != 0) {
					for (int k = 0; k < count3; k++) {
						System.out.print("   ");
					}
				}

			} else {
				System.out.print("                     ");
			}
			System.out.print("  ");
			// 3월 달력 한줄

			if (month4_count < 31) {
				for (int i = 0; i < yoil_of_Apr_first; i++) {
					System.out.print(String.format("%02d ", 0));
				}
				while (start4) {
					if (month4_count > 30) {
						count4 = (count4 + 1) % 7;
					} else {
						System.out.print(String.format("%02d ", month4_count));
					}
					yoil_of_Apr_first = (yoil_of_Apr_first + 1) % 7;
					if (0 == yoil_of_Apr_first % 7) {
						start4 = false;
					}
					++month4_count;
				}
				if (count4 != 0) {
					for (int k = 0; k < count4; k++) {
						System.out.print("   ");
					}
				}

			} else {
				System.out.print("                     ");
			}
			System.out.print("  ");
			// 4월 달력 한줄

			System.out.println();
		}
		System.out.println();
		// ==================================================1~4월===========================================================
		System.out.println();
		System.out.println("5월                     6월                     7월                    8월                ");
		System.out
				.println("--------------------   --------------------   --------------------  ----------------------");
		System.out.println(" 일 월 화 수 목 금 토       일 월 화 수 목 금 토        일 월 화 수 목 금 토      일 월 화 수 목 금 토  ");
		System.out
				.println("--------------------   --------------------   --------------------  ----------------------");

		for (int j = 0; j < 6; j++) {
			boolean start1 = true;
			boolean start2 = true;
			boolean start3 = true;
			boolean start4 = true;

			if (month5_count < 32) {
				for (int i = 0; i < yoil_of_May_first; i++) {
					System.out.print(String.format("%02d ", 0));
				}
				while (start1) {
					if (month5_count > 31) {
						count5 = (count5 + 1) % 7;
					} else {
						System.out.print(String.format("%02d ", month5_count));
					}
					yoil_of_May_first = (yoil_of_May_first + 1) % 7;
					if (0 == yoil_of_May_first % 7) {
						start1 = false;
					}
					++month5_count;
				}
				if (count5 != 0) {
					for (int k = 0; k < count5; k++) {
						System.out.print("   ");
					}
				}

			} else {
				System.out.print("                     ");
			}
			System.out.print("  ");
			// 5월 달력 한줄

			if (month6_count < 31) {
				for (int i = 0; i < yoil_of_Jun_first; i++) {
					System.out.print(String.format("%02d ", 0));
				}
				while (start2) {
					if (month6_count > 30) {
						count6 = (count6 + 1) % 7;
					} else {
						System.out.print(String.format("%02d ", month6_count));
					}
					yoil_of_Jun_first = (yoil_of_Jun_first + 1) % 7;
					if (0 == yoil_of_Jun_first % 7) {
						start2 = false;
					}
					++month6_count;
				}
				if (count6 != 0) {
					for (int k = 0; k < count6; k++) {
						System.out.print("   ");
					}
				}
			} else {
				System.out.print("                     ");
			}

			System.out.print("  ");
			// 6월 달력 한줄

			if (month7_count < 32) {
				for (int i = 0; i < yoil_of_Jul_first; i++) {
					System.out.print(String.format("%02d ", 0));
				}
				while (start3) {
					if (month7_count > 31) {
						count7 = (count7 + 1) % 7;
					} else {
						System.out.print(String.format("%02d ", month7_count));
					}
					yoil_of_Jul_first = (yoil_of_Jul_first + 1) % 7;
					if (0 == yoil_of_Jul_first % 7) {
						start3 = false;
					}
					++month7_count;
				}
				if (count7 != 0) {
					for (int k = 0; k < count7; k++) {
						System.out.print("   ");
					}
				}

			} else {
				System.out.print("                     ");
			}
			System.out.print("  ");
			// 7월 달력 한줄

			if (month8_count < 32) {
				for (int i = 0; i < yoil_of_Aug_first; i++) {
					System.out.print(String.format("%02d ", 0));
				}
				while (start4) {
					if (month8_count > 31) {
						count8 = (count8 + 1) % 7;
					} else {
						System.out.print(String.format("%02d ", month8_count));
					}
					yoil_of_Aug_first = (yoil_of_Aug_first + 1) % 7;
					if (0 == yoil_of_Aug_first % 7) {
						start4 = false;
					}
					++month8_count;
				}
				if (count8 != 0) {
					for (int k = 0; k < count8; k++) {
						System.out.print("   ");
					}
				}

			} else {
				System.out.print("                     ");
			}
			System.out.print("  ");
			// 8월 달력 한줄

			System.out.println();
		}
		System.out.println();
		// ==================================================5~8월===========================================================

		System.out.println();
		System.out.println("9월                     10월                    11월                   12월                ");
		System.out
				.println("--------------------   --------------------   --------------------  ----------------------");
		System.out.println(" 일 월 화 수 목 금 토       일 월 화 수 목 금 토        일 월 화 수 목 금 토      일 월 화 수 목 금 토  ");
		System.out
				.println("--------------------   --------------------   --------------------  ----------------------");

		for (int j = 0; j < 6; j++) {
			boolean start1 = true;
			boolean start2 = true;
			boolean start3 = true;
			boolean start4 = true;

			if (month9_count < 31) {
				for (int i = 0; i < yoil_of_Sep_first; i++) {
					System.out.print(String.format("%02d ", 0));
				}
				while (start1) {
					if (month9_count > 30) {
						count9 = (count9 + 1) % 7;
					} else {
						System.out.print(String.format("%02d ", month9_count));
					}
					yoil_of_Sep_first = (yoil_of_Sep_first + 1) % 7;
					if (0 == yoil_of_Sep_first % 7) {
						start1 = false;
					}
					++month9_count;
				}
				if (count9 != 0) {
					for (int k = 0; k < count9; k++) {
						System.out.print("   ");
					}
				}

			} else {
				System.out.print("                     ");
			}
			System.out.print("  ");
			// 9월 달력 한줄

			if (month10_count < 32) {
				for (int i = 0; i < yoil_of_Oct_first; i++) {
					System.out.print(String.format("%02d ", 0));
				}
				while (start2) {
					if (month10_count > 31) {
						count10 = (count10 + 1) % 7;
					} else {
						System.out.print(String.format("%02d ", month10_count));
					}
					yoil_of_Oct_first = (yoil_of_Oct_first + 1) % 7;
					if (0 == yoil_of_Oct_first % 7) {
						start2 = false;
					}
					++month10_count;
				}
				if (count10 != 0) {
					for (int k = 0; k < count10; k++) {
						System.out.print("   ");
					}
				}
			} else {
				System.out.print("                     ");
			}

			System.out.print("  ");
			// 10월 달력 한줄

			if (month11_count < 31) {
				for (int i = 0; i < yoil_of_Nov_first; i++) {
					System.out.print(String.format("%02d ", 0));
				}
				while (start3) {
					if (month11_count > 30) {
						count11 = (count11 + 1) % 7;
					} else {
						System.out.print(String.format("%02d ", month11_count));
					}
					yoil_of_Nov_first = (yoil_of_Nov_first + 1) % 7;
					if (0 == yoil_of_Nov_first % 7) {
						start3 = false;
					}
					++month11_count;
				}
				if (count11 != 0) {
					for (int k = 0; k < count11; k++) {
						System.out.print("   ");
					}
				}

			} else {
				System.out.print("                     ");
			}
			System.out.print("  ");
			// 11월 달력 한줄

			if (month12_count < 32) {
				for (int i = 0; i < yoil_of_Dec_first; i++) {
					System.out.print(String.format("%02d ", 0));
				}
				while (start4) {
					if (month12_count > 31) {
						count12 = (count12 + 1) % 7;
					} else {
						System.out.print(String.format("%02d ", month12_count));
					}
					yoil_of_Dec_first = (yoil_of_Dec_first + 1) % 7;
					if (0 == yoil_of_Dec_first % 7) {
						start4 = false;
					}
					++month12_count;
				}
				if (count12 != 0) {
					for (int k = 0; k < count12; k++) {
						System.out.print("   ");
					}
				}

			} else {
				System.out.print("                     ");
			}
			System.out.print("  ");
			// 12월 달력 한줄

			System.out.println();
		}
		System.out.println();
		System.out.println("1) 날짜 다시 입력하기");
		System.out.println("2) 메인화면으로 돌아가기 ");
		boolean lego = true;
		String choice = new String();
		while (lego) {
			System.out.print("입력 > ");
			choice = scan.nextLine();
			choice = choice.trim();
			if (choice.equals("1")) {
				lego = false;
			} else if (choice.equals("2")) {
				lego = false;
				next_move = false;
			}
		}

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

//	public static boolean is_valid_date(int[] date) { // 유효한 날짜인지 예외처리 함수
//		if (date[0] < 1902 || date[0] > 2037) {
////			System.out.println("허용되지 않는 년도를 입력하셨습니다. 날짜를 다시 입력해주세요.");
//			System.out.println("1902~2037 사이에서 연도를 입력해주세요");
//			return true; // 년도가 1902~2037 범위를 벗어남
//		}
//		if (date[1] < 1 || date[1] > 12) {
//			if (date[1] != -1) {
////				System.out.println("존재하지 않는 달을 입려하셨습니다. 날짜를 다시 입력해주세요.");
//				System.out.println("1902~2037 사이에서 년도를 입력하고, 01~12 사이에서 월을 입력해주세요.");
//				return true; // 월이 1~12를 벗어남
//			}
//		}
//		if (date[2] < 1 || date[2] > 31) {
//			if (date[2] != -1) {
////				System.out.println("존재하지 않는 일수를 입력하셨습니다. 날짜를 다시 입력해주세요.");
//				System.out.println("1902~2037 사이에서 년도를 입력하고, 01~12 사이에서 월과 01~31 사이의 일을 입력해주세요.");
//				return true; // 날이 1~31을 벗어남
//			}
//		}
//		if (is_leap_year(date[0])) {
//			if (date[1] == 2) {
//				if (date[2] < 1 || date[2] > 29) {
//					if (date[2] != -1) {
//						System.out.println("윤년인 해는 날수를 1~29까지 입력할 수 있습니다. 날짜를 다시 입력해주세요.");
//						return true; // 윤년일때 1~29 범위를 벗어남
//					}
//				}
//			}
//		} else {
//			if (date[1] == 2) {
//				if (date[2] < 1 || date[2] > 28) {
//					if (date[2] != -1) {
//						System.out.println("윤년이 아닌 해는 날수를 1~28까지 입력할 수 있습니다. 날짜를 다시 입력해주세요.");
//						return true; // 윤년아닐때 1~28 범위를 벗어남
//					}
//				}
//			}
//		}
//		return false;
//	}

//	public static boolean is_valid_date(int[]date) {  //유효한 날짜인지 예외처리 함수
//		if(date[0]<1902||date[0]>2037) {
//			System.out.println("1902~2037 사이에서 연도를 입력해주세요");
//			//System.out.println("허용되지 않는 년도를 입력하셨습니다. 날짜를 다시 입력해주세요.");
//			return true; // 년도가 1902~2037 범위를 벗어남 
//		}
//		if(date[1]<1||date[1]>12) {
//			if(date[1]!=-1) {
//				System.out.println("1902~2037 사이에서 년도를 입력하고, 01~12 사이에서 월을 입력해주세요.");
//				//System.out.println("존재하지 않는 달을 입려하셨습니다. 날짜를 다시 입력해주세요.");
//			    return true; // 월이 1~12를 벗어남
//			}
//		}
//    if(date[1]==1||date[1]==3||date[1]==5||date[1]==7||date[1]==8||date[1]==10||date[1]==12){
//      if(date[2]<1||date[2]>31){
//    	  System.out.println("해당 날짜가 존재하지 않습니다");
//        return true;
//      }
//    }else if(date[1]==4||date[1]==6||date[1]==9||date[1]==11){
//      if(date[2]<1||date[2]>30){
////    	  if(date[2]>=1 && date[2]<=31) {
////    		  System.out.println("해당 날짜가 존재하지 않습니다");
////    		  return true;
////    	  }
//    	  System.out.println("1902~2037 사이에서 년도를 입력하고, 01~12 사이에서 월과 01~31 사이의 일을 입력해주세요.");
//    	  return true;
//      }
//    }else if (date[1]==2){
//       if(is_leap_year(date[0])){
//         if(date[2]<1||date[2]>29){
////        	 if(date[2]>=1 && date[2]<=31) {
////        		 System.out.println("해당 날짜가 존재하지 않습니다");
////        		 return true;
////        	 }
//        	 System.out.println("1902~2037 사이에서 년도를 입력하고, 01~12 사이에서 월과 01~31 사이의 일을 입력해주세요.");
//            //System.out.println("윤년인 해는 날수를 1~29까지 입력할 수 있습니다. 날짜를 다시 입력해주세요.");
//            return true;
//         }
//       }else{
//         if(date[2]<1||date[2]>28){
////        	 if(date[2]>=1 && date[2]<=31) {
////        		 System.out.println("해당 날짜가 존재하지 않습니다");
////        		 return true;
////        	 }
//        	 System.out.println("1902~2037 사이에서 년도를 입력하고, 01~12 사이에서 월과 01~31 사이의 일을 입력해주세요.");
//            //System.out.println("윤년이 아닌 해는 날수를 1~28까지 입력할 수 있습니다. 날짜를 다시 입력해주세요");
//            return true;
//         }
//       }
//    }
//		return false;
//	}
	
	public static boolean is_valid_date(int[] date) { // 유효한 날짜인지 예외처리 함수
		if (date[0] < 1902 || date[0]>2037) {
			System.out.println("허용되지 않는 년도를 입력하셨습니다. 날짜를 다시 입력해주세요.");
			return true; // 년도가 1902~2037 범위를 벗어남
		}
		if (date[1] < 1 || date[1] > 12) {
			if (date[1] != -1) {
				System.out.println("존재하지 않는 달을 입력하셨습니다. 날짜를 다시 입력해주세요.");
				return true; // 월이 1~12를 벗어남
			}
		}
		if (date[1] == 1 || date[1] == 3 || date[1] == 5 || date[1] == 7 || date[1] == 8 || date[1] == 10
				|| date[1] == 12) {
			if(date[2]!=-1) {
				if (date[2] < 1 || date[2] > 31) {
					System.out.println("존재하지 않는 일수를 입력하셨습니다. 날짜를 다시 입력해주세요.");
					return true;
				}
			}
		} else if (date[1] == 4 || date[1] == 6 || date[1] == 9 || date[1] == 11) {
			if(date[2]!=-1) {
				if (date[2] < 1 || date[2] > 30) {
					System.out.println("존재하지 않는 일수를 입력하셨습니다. 날짜를 다시 입력해주세요.");
					return true;
				}
			}
		} else if (date[1] == 2) {
			if (is_leap_year(date[0])) {
				if(date[2]!=-1) {
					if (date[2] < 1 || date[2] > 29) {
						System.out.println("윤년인 해는 날수를 1~29까지 입력할 수 있습니다. 날짜를 다시 입력해주세요.");
						return true;
					}
				}
			} else {
				if(date[2]!=-1) {
					if (date[2] < 1 || date[2] > 28||date[2]!=-1) {
						System.out.println("윤년이 아닌 해는 날수를 1~28까지 입력할 수 있습니다. 날짜를 다시 입력해주세요");
						return true;
					}
				}
			}
		}
		return false;
	}

	@SuppressWarnings("resource")
	public static int[] date_input_func() {          // 날짜 입력 클래스
		Scanner scan = new Scanner(System.in);
		boolean wrong = true;
		boolean unfinished = true;
		while(unfinished) {
			System.out.print("정렬방식을 입력하세요 > ");
			String str = new String();
			str=scan.nextLine();
			if(is_numeric(str)) {
				boolean under_two_four=true;
				StringBuffer sb = new StringBuffer();
				String str_split[];
				int result[] = { 0, -1, -1 };
				str = str.replaceAll("\t", "");
				for (int i = 0; i < str.length(); i++) { 
					str = str.replace("  ", " ");
				}
				str = str.replace(" ", "a");
				if (str.charAt(0) == 'a') { //맨앞의 공백제거 
					sb.append(str);
					sb.deleteCharAt(0);
					str = sb.toString();
				}
				str_split = str.split("a");
				
				for(int i=0;i<str_split.length;i++) {
					if(i==0) {
						if(str_split[i].length()==2||str_split[i].length()==4) { // 다음 단계로 ㄱㄱ 
//							System.out.println("split[0] = 2 or 4");
						}else { //다음 단계x
							System.out.println("허용되지 않는 년도를 입력하셨습니다. 날짜를 다시 입력해주세요.");
							under_two_four=false;
							break;
						}
					}else if(i==1) {
						if(str_split[i].length()==2||str_split[i].length()==1) {
//							System.out.println("split[0] = 2");
						}else {
							System.out.println("잘못 입력하셨습니다.");
							under_two_four=false;
							break;
						}
					}else if(i==2) {
						if(str_split[i].length()==2||str_split[i].length()==1) {
//							System.out.println("split[0] = 2 or 4");
						}else {
							System.out.println("잘못 입력하셨습니다.");
							under_two_four=false;
							break;
						}
					}
				}
				if(under_two_four) {
					for (int i = 0; i < str_split.length; i++) {
						result[i] = Integer.parseInt(str_split[i]);
					}
					if(result[0]<100&&result[0]>0) {
						result[0]+=2000;
					}
					unfinished = is_valid_date(result); //날짜 유효하게 입력됬는지 판단해서 반복문 수행할지 안할지 정함 
					if(!unfinished) {
						return result;
					}
				}
			}else {
				System.out.println("잘못 입력하셨습니다.");
			}
		}
		return null;
	}

	public static boolean is_numeric(String str) {
		if (str == null) {
			return false;
		}
		return str.matches("[\\d\\s]+");
	}

	public static int getDayOfWeek(String dateStr) { // 해당 날짜 요일 찾아주는 함수 0부터 일요일 ~ 6이 토요일
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = format.parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		String[] daysOfWeek = new String[] { "일", "월", "화", "수", "목", "금", "토" };
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		return dayOfWeek - 1; // 일부터 토 차례로 0~6
	}

}
