package Calendar;

import java.util.ArrayList;
import java.util.Scanner;

public class Main_Program {
	Calendar_Dao Calendar_Dao = new Calendar_Dao();
	int menu;
	private Process1 process1;
	private Process2 process2;
	private Process3 process3;
	private Process4 process4;
	private String process_input;

	public Main_Program() {
		while (true) {
			System.out.println("> B02 Calender Menu");
			System.out.println("1. Calendar View (달력보기)");
			System.out.println("2. Schedule registration (일정등록)");
			System.out.println("3. Edit (일정 및 태그 편집)"); // 추후 구현 예정
			System.out.println("4. Search for tags (태그검색)"); // 추후 구현 예정
			System.out.println("5. Help(도움말)");
			System.out.println("6. Quit (종료)");
			System.out.println("---------------------------------------------------");
			System.out.print("B02 Calender : menu > ");
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(System.in);

			try {
				process_input = sc.nextLine();
				System.out.println("---------------------------------------------------");
				if (Is_valid(process_input)) {
					menu = Integer.parseInt(process_input);
					if (menu == 1) 
						process1 = new Process1();
					else if (menu == 2) 
						process2 = new Process2();
					else if (menu == 3) 
						process3 = new Process3();
					else if (menu == 4) 
						process4 = new Process4();
					else if (menu == 5) { 
						Help help = new Help();
					}
					else if (menu == 6) {
						System.out.println("B02 캘린더를 종료합니다.");
						break;
					}else {
						System.out.println("잘못 입력했습니다. 범위(1~6) 안에서 다시 선택해주세요");
						System.out.println("---------------------------------------------------");
					}
				}else {
					System.out.println("잘못 입력했습니다. 범위(1~6) 안에서 다시 선택해주세요");
					System.out.println("---------------------------------------------------");
				}
			} catch (Exception e) {
				System.out.println("잘못 입력했습니다. 범위(1~6) 안에서 다시 선택해주세요");
				System.out.println("---------------------------------------------------");

			}

		}

	}

	public boolean Is_valid(String e) {
		if (e.length() != 1) {
			e = e.trim();
			if(e.length()!=1)
				return false;
			else {
				int analysis = Integer.parseInt(e);
				if (analysis < 1 || analysis > 6)
					return false;
				else
					this.process_input = Integer.toString(analysis);
					return true;
			}
			}
		int analysis = Integer.parseInt(e);
		if (analysis < 1 || analysis > 6)
			return false;
		else
			return true;
	}

}
