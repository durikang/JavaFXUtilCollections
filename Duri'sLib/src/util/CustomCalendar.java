package util;

import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class CustomCalendar {
	// 미리 기준 연도와 , 기준월을 정해서 출력하는 방법을 선택함.

	int base_year = 1980; // 기준 연도
	int base_month = 1; // 기준 월 입니다. 실제로는 1980년 1월 1을 기준으로 계산
	int total_sum = 0; // 기준 년과 월에서 입력받은 날짜까지 총 일수를 여기에 저장

	int[] month_table = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 }; // 각 월마다 총 일수

	
	
	public CustomCalendar() {
	}
	
	

	public static CustomCalendar getInstance() {
		return LazyHolder.cc;
	}

	private static class LazyHolder {
		private static final CustomCalendar cc = new CustomCalendar();
	}

	public int is_leap_year(int n) { // 윤년이 있는 년도를 조사하는 함수입니다. 윤년인경우는 1

		if (n % 4 == 0) { // 4의 배수는 윤년

			if (n % 100 == 0) // 그런데 100의 배수는 윤년이 아님 (평년)
			{
				if (n % 400 == 0) // 그중에서 400의 배수는 윤년
					return 1;

				return 0;
			}

			return 1;
		}

		else
			return 0;

	}

	public int total_to_month(int total) // 기준 날짜부터 입력받은 날짜까지 총 일수를 구해서 반환,
	{

		boolean CHK = false; // 무한 루프를 돌리기위한 불리안 값입니다.
		int i = 0; // i변수는 월입니다.
		int cnt = 0; // 총 일수를 월로 바꾸어서 cnt 저장시킵니다.
		int chk_leap_year = base_year; // 총 일수를 월로 바꾸려면 윤년이 있는 날을 고려해야합니다.
										// 기준연도부터 시작해서 윤년을 조사합니다.

		while (CHK != true) {
			if (is_leap_year(chk_leap_year) == 1) // 만약 지금 연도가 윤년이라면
				month_table[1] = 29; // 2월달은 하루가 더 늘어납니다.

			if (total >= month_table[i]) // 총 일수가 month_table[]배열의 총일수 보다 작다면,
			{
				total -= month_table[i++]; // 총일수를 month_table 배열의 현재 월의 일수로 빼줍니다.
											// 그리고 i를 증가시킵니다. 즉 다음달로 증가됩니다.
				cnt++; // 그리고 월이 증가합니다.
				if (i == 12) // 만약 12월이된다면 계절이 변해 다시 제자리로 오는것처럼
				{
					i -= 12; // 다시 12를빼서 0으로 만들어줍니다.
					chk_leap_year++; // 그리고 12개월이 지났으니 연도도 증가시켜줘야함
				}

				month_table[1] = 28; // 윤년을 평년의 해로 바꾸어주어야합니다.

			}

			else
				break;

		}

		cnt %= 12; // 위의 무한루프를 통해 총일수를 총월수로 계산됨, 이제 12 나머지 연산을 해주면
					// 몇년도 몇월이라는 값으로 바꿀수있습니다.

		return (cnt + 1); // 그리고 바꾼 월을 반환

	}

	public int count_leap(int n) // 기준 연도부터 시작해서 입력받은 연도까지의 윤년이 있는날을 셉니다.
	{
		int i; // 기준연도를 저장합니다.
		int cnt = 0; // 윤년의 개수입니다.

		for (i = base_year; i < (base_year - n); i++)
		// i(기준연도) 부터 입력받은 연도까지 i를 증가시키며 윤년이낀 갯수를 구함.
		{

			if (is_leap_year(i) == 1) // 위에서 구현한 윤년인지를 판단하는 함수를 사용
			{
				cnt++; // 윤년이라면 총 윤년의 개수를 증가시킵니다.
			}

		}

		return cnt; // 카운트한 윤년의 갯수를 리턴합니다.

	}

	public void convert_to_day(int nYear) { // 기준 연도부터 입력받은 연도까지 총일수를 구해서 리턴합니다.
		total_sum = ((nYear - base_year) * 365) + count_leap((base_year - nYear));
	}

	public void printCalendar(String Year, String months, GridPane gp) {
		
		int nYear=Integer.parseInt(Year);
		int mth=Integer.parseInt(months);
		
		int cnt_i, cnt_j; // 카운트를 위한 변수입니다.
		int month;

		int dy = count_leap(base_year - nYear); // dy는 기준연도부터 현재연도까지 낀 윤년의 갯수입니다.
		convert_to_day(nYear); // ★ 우선 기준연도부터 현재 연도까지 년 단위로 총일수를 구합니다. ★

		int day; // 이변수는 요일을 결정하기위해 존재합니다.예를들어 기준일부터 현재일까지 차이가 7이고
					// 기준일이 월요일이면 7로 나눠서 나머지가 0이되니까 월요일임을 알수 있듯이
					// day는 숫자로서 요일을 결정할수있습니다.
		if (nYear >= base_year) {

			if (is_leap_year(nYear) == 1) // 윤달이 낀날의 2월은 하루 증가
				month_table[1] = 29;

			for (cnt_i = 0; cnt_i < (mth - base_month); cnt_i++)
				total_sum += month_table[cnt_i];
			// 위에 ★ 에서 기준연도부터 현재연도까지의 일수를 구했습니다.
			// 이 for루프를 통해 나머지 기준월부터 현재월까지의 총일수를 구합니다.
			// 즉 이루프를 통해 기준 연도와 월부터 현재 연도와 월까지의 총일수를 구함.

			day = (total_sum + 2) % 7;
			// 현재까지의 총일수를 7의 나머지로 연산해줍니다. 2를 더해준 이유는 1980년도 1월 1일 = 화요일

			System.out.println("총 일수 = " + total_sum + "윤달이 낀 숫자 = " + dy);
			month = total_to_month(total_sum); // 입력받은 해당 날짜의 정확한 달을 구해서 저장합니다.

			System.out.println(+month + " 월의 달력");
			
			
			System.out.println("일	월	화	수	목	금	토");
			
			/*
			 * 커스텀 내용 시작
			*/
			String[] str= {"일","월","화","수","목","금","토"};
			ArrayList<Label> days = new ArrayList<>(); // 요일 저장용
			ArrayList<Label> date = new ArrayList<>(); // 일수 저장용
			
			
			
			
			
			//요일을 ArrayList<Label>에 저장
			for(int i=0;i<str.length;i++) {
				days.add(new Label(str[i]));
				days.get(i).setMaxWidth(Double.MAX_VALUE);
				days.get(i).setMaxHeight(Double.MAX_VALUE);
				days.get(i).setAlignment(Pos.CENTER);
			}
//			저장한 요일을 gridpane에 출력
			for(int i=0;i<str.length;i++) {
				gp.add(days.get(i), i, 0);
			}
			
			// day 변수는 요일 입니다. 갯수만큼 공백을 만들어줍니다.
			for (cnt_i = 0; cnt_i < day; cnt_i++) {
				date.add(new Label(""));
			}
			for (cnt_j = 1; cnt_j <= month_table[month - 1]; cnt_j++)
			// month변수에서 1을 빼준이유는 배열은 0부터 시작하기때문입니다.
			{
				date.add(new Label(String.valueOf(cnt_j)));// j를 증가시켜가며 차례대로 날짜를 출력합니다.
			
			}
			
			int a=0;
			int b=0;
			int c=1;
			int cnt=date.size();
			
			
			while(cnt !=0	) {
				if(b>6) {
					b=0;				
					c++;
				}				
				gp.add(date.get(a), b, c);
				
				b++;
				a++;
				cnt--;
			}
			
//			*Label에 스타일 지정하는 부분
			
			// day 변수는 요일 입니다. 갯수만큼 공백을 만들어줍니다.
			for (cnt_i = 0; cnt_i < day; cnt_i++) {
				date.get(cnt_i).setStyle("-fx-background-color: rgb(253,205,205)");
			}

			
			for(int property=0;property<date.size();property++) {
				date.get(property).setMaxWidth(Double.MAX_VALUE);
				date.get(property).setMaxHeight(Double.MAX_VALUE);
				date.get(property).setAlignment(Pos.CENTER);
			}
			
			
			
			
//			커스텀 내용 끝 부분
			
			
			for (cnt_i = 0; cnt_i < day; cnt_i++) // day 변수는 요일 입니다. 갯수만큼 \t로 공백을 만들어줍니다.
				System.out.print("\t");
			// 요일 초기화
			

			for (cnt_j = 1; cnt_j <= month_table[month - 1]; cnt_j++)
			// month변수에서 1을 빼준이유는 배열은 0부터 시작하기때문입니다.
			{
				System.out.print(cnt_j + "\t");// j를 증가시켜가며 차례대로 날짜를 출력합니다.

				if (((cnt_j + day) % 7) == 0)
					System.out.println();
				// 그리고 처음 요일을 출력하기위한 공백만큼 계산해서 출력
			}

			System.out.println();

			month_table[1] = 28; // 윤년이었다면 다시 평년으로 바꾸어줍니다.

		}
	}

}
