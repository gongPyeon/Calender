package Calendar;

import java.util.Scanner;

public class Help {
	String Help_input;
	Scanner sc = new Scanner(System.in);
	int menu;

	public Help() {
		System.out.println("> B02 Calender Help");
		System.out.println();
		System.out.println("1) Calendar View (달력보기)");
		System.out.println("2) Schedule registration (일정등록)");
		System.out.println("3) Edit (일정 및 태그 편집) ");
		System.out.println("4) Search for tags (태그검색)");
		System.out.println("5) Help (도움말)");
		System.out.println("6) Quit (종료)");
		System.out.println("---------------------------------------------------");
		while(true) {
		try {
				System.out.print("Help > ");
				Help_input = sc.nextLine();
				if (Is_valid_Help(Help_input)) {
					menu = Integer.parseInt(Help_input);
					if (menu == 1) {
						this.process1();
						break;
						}
					else if (menu == 2) {
						this.process2();
						break;
					}
					else if (menu == 3) {
						this.process3();
						break;
					}
					else if (menu == 4) {
						this.process4();
						break;
					}
					else if (menu == 5) {
						this.process5();
						break;
					}
					else if (menu == 6) {
						this.process6();
						break;
					}

				}
			}
		 catch (Exception e) {
			System.out.println("! 다시 입력해주세요 !");
		 	}
		}
	}

	public void process1() {
		System.out.println("[달력 보기]");
		System.out.println("정렬방식으로 입력하실 수 있는 값은 세 가지 입니다. (년 / 년 + 월 / 년 + 월 + 일)");
		System.out.println("'년'만 입력하시면 해당 연도의 캘린더를 출력합니다. 정렬방식이 ‘년’인 캘린더에서는 일" + "정의 개수와 내용을 보실 수 없습니다.");
		System.out.println("'년과 월'을 입력하시면 해당 연도의 해당 월의 월간 캘린더를 보실 수 있습니다. 바로 각 " + "일자의 할 일 개수를 확인할 수 있습니다.");
		System.out.println("'년,월,일'을 입력하시면 해당 날짜의 일정을 확인할 수 있습니다.");
	}

	public void process2() {
		System.out.println("[일정 등록]");
		System.out.println("입력하셔야 하는 값은 순서대로 한가한/바쁜 일정 중 택1, 반복 유무 선택, 날짜, 일정 제" + "목, 카테고리 태그 선택입니다. ");
		System.out.println("한가한/바쁜 일정 선택은 말 그대로 일정에 한가한/바쁜 특성을 부여하는 것입니다. 입력"
				+ "값은 정확히 “한가한”, “바쁜” 만 허용됩니다. “바쁜” 일정을 서로 겹칠 수 없습니다. ");
		System.out.println(
				"반복은 월/주간으로 나누어지며, 월간으로 반복 주기를 설정할 시 시작 날짜와 종료 날짜, 반복 일자를 입력해야 합니다. 주간으로 반복 주기를 설정할 시 시작 날짜와 종료 날짜, 반복 요일을 입력해야 합니다. 이때 날짜는 년, 월, 일을 각각 <횡공백류열1>로 구분해서"
						+ "입력해주셔야 합니다.");
		System.out.println("할 일은 그 어떠한 문자를 입력해도 됩니다. 단, 공백류만을 입력하시면 안됩니다.");
		System.out.println(
				"카테고리 태그 선택은 일정들을 몇 가지 태그들로 구분하기 위해(카테고리) 부여하는 특성" + "입니다. 카테고리 입력 창에 입장하면 사용 가능한 태그들이 출력됩니다. 원하는 카테고리 "
						+ "목록이 없는 경우, 카테고리추가를 입력해 카테고리 태그를 추가할 수 있습니다. 새로운 "
						+ "태그를 등록할 땐 하나의 단어를 입력하셔야 합니다. 공백류만을 사용하는 것을 허용하지 " + "않습니다.");

	}

	public void process3() {
		System.out.println("[일정 및 태그 편집]\n"
				+ "일정 및 태그를 편집할 수 있는 메뉴입니다. \n일정 편집 기능을 선택하면, 일정 제목, 반복 요일, 태그 변경 등 2번 메뉴에서 작성된 것"
				+ "들을 편집 가능합니다. 2번 메뉴에서 적용되었던 모든 규칙은 반드시 따라야 합니다.\n태그 편집 기능을 선택하면, 카테고리 태그를 삭제, 추가하거나 수정할 수 있습니다. 단, 프로그램에서 기본적으로 제공하는 3가지 카테고리 태그(운동,학업,여가)는 편집, 삭제할 "
				+ "수 없습니다. 새로운 태그를 등록할 땐 하나의 단어를 입력하셔야 합니다. (공백류만 입력" + "하는 것을 허용되지 않습니다)");
	}

	public void process4() {
		System.out.println("[태그 검색]\n" + "태그검색은 (카테고리)태그와 ‘바쁜’과 ‘한가한’을 모두 포괄하고 있습니다. 이때, 두 문자"
				+ "열 모두 내부의 탭 혹은 개행 문자들을 전부 무시한 채로, 문자열 자체가 서로 완전히 일"
				+ "치해야만 같은 태그로 간주하여 검색결과를 나타냅니다. 특정 태그를 검색하면, 해당 태그" + "를 가진 일정의 개수가 월별로 출력됩니다. ");
	}

	public void process5() {
		System.out.println("[도움말]\n프로그램의 전반적인 기능들에 대한 소개와 사용법이 나와 있습니다.");
	}

	public void process6() {
		System.out.println("[종료]\n프로그램을 종료시킵니다.");
	}

	public boolean Is_valid_Help(String e) {
		if (e.length() != 1) {
			e = e.trim();
			if(e.length()!=1) {
				System.out.println("! 다시 입력해주세요 !");
				return false; // 문자열 길이가 1이상 
			}
			else {
				int analysis = Integer.parseInt(e);
				if (analysis < 1 || analysis > 6) {
					System.out.println("! " + analysis + "번 항목이 존재하지 않습니다 !");
					return false; //입력값이 1~6이 아님 
				}
				else {
					this.Help_input = Integer.toString(analysis);
					return true; // 입력값도 참 
				}
			}
			}
		int analysis = Integer.parseInt(e);
		if (analysis < 1 || analysis > 6) {
			System.out.println("! " + analysis + "번 항목이 존재하지 않습니다 !");
			return false; // 입력값 1~6이 아님 
		}
		else
			return true; // 입력값 참. 
	}
}
