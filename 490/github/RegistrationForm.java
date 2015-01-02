package com.cs490;
import java.util.HashMap;
import java.util.Map;


public class RegistrationForm extends Form {

	private int formId = 0;
	String year;
	String term;
	String reg1;
	String reg2;
	String reg3;
	String reg4;
	String reg5;
	String reg6;
	String dept1;
	String dept2;
	String dept3;
	String dept4;
	String dept5;
	String dept6;
	String course1;
	String course2;
	String course3;
	String course4;
	String course5;
	String course6;
	String section1;
	String section2;
	String section3;
	String section4;
	String section5;
	String section6;
	String credit1;
	String credit2;
	String credit3;
	String credit4;
	String credit5;
	String credit6;
	String time1;
	String time2;
	String time3;
	String time4;
	String time5;
	String time6;

	String reg11;
	String reg12;
	String reg13;
	String reg14;
	String reg15;
	String reg16;
	String dept11;
	String dept12;
	String dept13;
	String dept14;
	String dept15;
	String dept16;
	String course11;
	String course12;
	String course13;
	String course14;
	String course15;
	String course16;
	String section11;
	String section12;
	String section13;
	String section14;
	String section15;
	String section16;
	String credit11;
	String credit12;
	String credit13;
	String credit14;
	String credit15;
	String credit16;
	String time11;
	String time12;
	String time13;
	String time14;
	String time15;
	String time16;
	
	public int getFormId() {
		return formId;
	}
	public void setFormId(int formId) {
		this.formId = formId;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public String getReg1() {
		return reg1;
	}
	public void setReg1(String reg1) {
		this.reg1 = reg1;
	}
	public String getReg2() {
		return reg2;
	}
	public void setReg2(String reg2) {
		this.reg2 = reg2;
	}
	public String getReg3() {
		return reg3;
	}
	public void setReg3(String reg3) {
		this.reg3 = reg3;
	}
	public String getReg4() {
		return reg4;
	}
	public void setReg4(String reg4) {
		this.reg4 = reg4;
	}
	public String getReg5() {
		return reg5;
	}
	public void setReg5(String reg5) {
		this.reg5 = reg5;
	}
	public String getReg6() {
		return reg6;
	}
	public void setReg6(String reg6) {
		this.reg6 = reg6;
	}
	public String getDept1() {
		return dept1;
	}
	public void setDept1(String dept1) {
		this.dept1 = dept1;
	}
	public String getDept2() {
		return dept2;
	}
	public void setDept2(String dept2) {
		this.dept2 = dept2;
	}
	public String getDept3() {
		return dept3;
	}
	public void setDept3(String dept3) {
		this.dept3 = dept3;
	}
	public String getDept4() {
		return dept4;
	}
	public void setDept4(String dept4) {
		this.dept4 = dept4;
	}
	public String getDept5() {
		return dept5;
	}
	public void setDept5(String dept5) {
		this.dept5 = dept5;
	}
	public String getDept6() {
		return dept6;
	}
	public void setDept6(String dept6) {
		this.dept6 = dept6;
	}
	public String getCourse1() {
		return course1;
	}
	public void setCourse1(String course1) {
		this.course1 = course1;
	}
	public String getCourse2() {
		return course2;
	}
	public void setCourse2(String course2) {
		this.course2 = course2;
	}
	public String getCourse3() {
		return course3;
	}
	public void setCourse3(String course3) {
		this.course3 = course3;
	}
	public String getCourse4() {
		return course4;
	}
	public void setCourse4(String course4) {
		this.course4 = course4;
	}
	public String getCourse5() {
		return course5;
	}
	public void setCourse5(String course5) {
		this.course5 = course5;
	}
	public String getCourse6() {
		return course6;
	}
	public void setCourse6(String course6) {
		this.course6 = course6;
	}
	public String getSection1() {
		return section1;
	}
	public void setSection1(String section1) {
		this.section1 = section1;
	}
	public String getSection2() {
		return section2;
	}
	public void setSection2(String section2) {
		this.section2 = section2;
	}
	public String getSection3() {
		return section3;
	}
	public void setSection3(String section3) {
		this.section3 = section3;
	}
	public String getSection4() {
		return section4;
	}
	public void setSection4(String section4) {
		this.section4 = section4;
	}
	public String getSection5() {
		return section5;
	}
	public void setSection5(String section5) {
		this.section5 = section5;
	}
	public String getSection6() {
		return section6;
	}
	public void setSection6(String section6) {
		this.section6 = section6;
	}
	public String getCredit1() {
		return credit1;
	}
	public void setCredit1(String credit1) {
		this.credit1 = credit1;
	}
	public String getCredit2() {
		return credit2;
	}
	public void setCredit2(String credit2) {
		this.credit2 = credit2;
	}
	public String getCredit3() {
		return credit3;
	}
	public void setCredit3(String credit3) {
		this.credit3 = credit3;
	}
	public String getCredit4() {
		return credit4;
	}
	public void setCredit4(String credit4) {
		this.credit4 = credit4;
	}
	public String getCredit5() {
		return credit5;
	}
	public void setCredit5(String credit5) {
		this.credit5 = credit5;
	}
	public String getCredit6() {
		return credit6;
	}
	public void setCredit6(String credit6) {
		this.credit6 = credit6;
	}
	public String getTime1() {
		return time1;
	}
	public void setTime1(String time1) {
		this.time1 = time1;
	}
	public String getTime2() {
		return time2;
	}
	public void setTime2(String time2) {
		this.time2 = time2;
	}
	public String getTime3() {
		return time3;
	}
	public void setTime3(String time3) {
		this.time3 = time3;
	}
	public String getTime4() {
		return time4;
	}
	public void setTime4(String time4) {
		this.time4 = time4;
	}
	public String getTime5() {
		return time5;
	}
	public void setTime5(String time5) {
		this.time5 = time5;
	}
	public String getTime6() {
		return time6;
	}
	public void setTime6(String time6) {
		this.time6 = time6;
	}
	public String getReg11() {
		return reg11;
	}
	public void setReg11(String reg11) {
		this.reg11 = reg11;
	}
	public String getReg12() {
		return reg12;
	}
	public void setReg12(String reg12) {
		this.reg12 = reg12;
	}
	public String getReg13() {
		return reg13;
	}
	public void setReg13(String reg13) {
		this.reg13 = reg13;
	}
	public String getReg14() {
		return reg14;
	}
	public void setReg14(String reg14) {
		this.reg14 = reg14;
	}
	public String getReg15() {
		return reg15;
	}
	public void setReg15(String reg15) {
		this.reg15 = reg15;
	}
	public String getReg16() {
		return reg16;
	}
	public void setReg16(String reg16) {
		this.reg16 = reg16;
	}
	public String getDept11() {
		return dept11;
	}
	public void setDept11(String dept11) {
		this.dept11 = dept11;
	}
	public String getDept12() {
		return dept12;
	}
	public void setDept12(String dept12) {
		this.dept12 = dept12;
	}
	public String getDept13() {
		return dept13;
	}
	public void setDept13(String dept13) {
		this.dept13 = dept13;
	}
	public String getDept14() {
		return dept14;
	}
	public void setDept14(String dept14) {
		this.dept14 = dept14;
	}
	public String getDept15() {
		return dept15;
	}
	public void setDept15(String dept15) {
		this.dept15 = dept15;
	}
	public String getDept16() {
		return dept16;
	}
	public void setDept16(String dept16) {
		this.dept16 = dept16;
	}
	public String getCourse11() {
		return course11;
	}
	public void setCourse11(String course11) {
		this.course11 = course11;
	}
	public String getCourse12() {
		return course12;
	}
	public void setCourse12(String course12) {
		this.course12 = course12;
	}
	public String getCourse13() {
		return course13;
	}
	public void setCourse13(String course13) {
		this.course13 = course13;
	}
	public String getCourse14() {
		return course14;
	}
	public void setCourse14(String course14) {
		this.course14 = course14;
	}
	public String getCourse15() {
		return course15;
	}
	public void setCourse15(String course15) {
		this.course15 = course15;
	}
	public String getCourse16() {
		return course16;
	}
	public void setCourse16(String course16) {
		this.course16 = course16;
	}
	public String getSection11() {
		return section11;
	}
	public void setSection11(String section11) {
		this.section11 = section11;
	}
	public String getSection12() {
		return section12;
	}
	public void setSection12(String section12) {
		this.section12 = section12;
	}
	public String getSection13() {
		return section13;
	}
	public void setSection13(String section13) {
		this.section13 = section13;
	}
	public String getSection14() {
		return section14;
	}
	public void setSection14(String section14) {
		this.section14 = section14;
	}
	public String getSection15() {
		return section15;
	}
	public void setSection15(String section15) {
		this.section15 = section15;
	}
	public String getSection16() {
		return section16;
	}
	public void setSection16(String section16) {
		this.section16 = section16;
	}
	public String getCredit11() {
		return credit11;
	}
	public void setCredit11(String credit11) {
		this.credit11 = credit11;
	}
	public String getCredit12() {
		return credit12;
	}
	public void setCredit12(String credit12) {
		this.credit12 = credit12;
	}
	public String getCredit13() {
		return credit13;
	}
	public void setCredit13(String credit13) {
		this.credit13 = credit13;
	}
	public String getCredit14() {
		return credit14;
	}
	public void setCredit14(String credit14) {
		this.credit14 = credit14;
	}
	public String getCredit15() {
		return credit15;
	}
	public void setCredit15(String credit15) {
		this.credit15 = credit15;
	}
	public String getCredit16() {
		return credit16;
	}
	public void setCredit16(String credit16) {
		this.credit16 = credit16;
	}
	public String getTime11() {
		return time11;
	}
	public void setTime11(String time11) {
		this.time11 = time11;
	}
	public String getTime12() {
		return time12;
	}
	public void setTime12(String time12) {
		this.time12 = time12;
	}
	public String getTime13() {
		return time13;
	}
	public void setTime13(String time13) {
		this.time13 = time13;
	}
	public String getTime14() {
		return time14;
	}
	public void setTime14(String time14) {
		this.time14 = time14;
	}
	public String getTime15() {
		return time15;
	}
	public void setTime15(String time15) {
		this.time15 = time15;
	}
	public String getTime16() {
		return time16;
	}
	public void setTime16(String time16) {
		this.time16 = time16;
	}
	public RegistrationForm(int formId, String year, String term, String reg1,
			String reg2, String reg3, String reg4, String reg5, String reg6,
			String dept1, String dept2, String dept3, String dept4,
			String dept5, String dept6, String course1, String course2,
			String course3, String course4, String course5, String course6,
			String section1, String section2, String section3, String section4,
			String section5, String section6, String credit1, String credit2,
			String credit3, String credit4, String credit5, String credit6,
			String time1, String time2, String time3, String time4,
			String time5, String time6, String reg11, String reg12,
			String reg13, String reg14, String reg15, String reg16,
			String dept11, String dept12, String dept13, String dept14,
			String dept15, String dept16, String course11, String course12,
			String course13, String course14, String course15, String course16,
			String section11, String section12, String section13,
			String section14, String section15, String section16,
			String credit11, String credit12, String credit13, String credit14,
			String credit15, String credit16, String time11, String time12,
			String time13, String time14, String time15, String time16) {
		super();
		this.formId = formId;
		this.year = year;
		this.term = term;
		this.reg1 = reg1;
		this.reg2 = reg2;
		this.reg3 = reg3;
		this.reg4 = reg4;
		this.reg5 = reg5;
		this.reg6 = reg6;
		this.dept1 = dept1;
		this.dept2 = dept2;
		this.dept3 = dept3;
		this.dept4 = dept4;
		this.dept5 = dept5;
		this.dept6 = dept6;
		this.course1 = course1;
		this.course2 = course2;
		this.course3 = course3;
		this.course4 = course4;
		this.course5 = course5;
		this.course6 = course6;
		this.section1 = section1;
		this.section2 = section2;
		this.section3 = section3;
		this.section4 = section4;
		this.section5 = section5;
		this.section6 = section6;
		this.credit1 = credit1;
		this.credit2 = credit2;
		this.credit3 = credit3;
		this.credit4 = credit4;
		this.credit5 = credit5;
		this.credit6 = credit6;
		this.time1 = time1;
		this.time2 = time2;
		this.time3 = time3;
		this.time4 = time4;
		this.time5 = time5;
		this.time6 = time6;
		this.reg11 = reg11;
		this.reg12 = reg12;
		this.reg13 = reg13;
		this.reg14 = reg14;
		this.reg15 = reg15;
		this.reg16 = reg16;
		this.dept11 = dept11;
		this.dept12 = dept12;
		this.dept13 = dept13;
		this.dept14 = dept14;
		this.dept15 = dept15;
		this.dept16 = dept16;
		this.course11 = course11;
		this.course12 = course12;
		this.course13 = course13;
		this.course14 = course14;
		this.course15 = course15;
		this.course16 = course16;
		this.section11 = section11;
		this.section12 = section12;
		this.section13 = section13;
		this.section14 = section14;
		this.section15 = section15;
		this.section16 = section16;
		this.credit11 = credit11;
		this.credit12 = credit12;
		this.credit13 = credit13;
		this.credit14 = credit14;
		this.credit15 = credit15;
		this.credit16 = credit16;
		this.time11 = time11;
		this.time12 = time12;
		this.time13 = time13;
		this.time14 = time14;
		this.time15 = time15;
		this.time16 = time16;
	}
	
	
	
	
}

