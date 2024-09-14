package com.nguyentan.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}

/*
- Kien thuc thu duoc:

1. Cach su dung thymeleaf

- Them the <html> de su dung tien to thymeleaf, tao web dong

  		  <html xmlns:th="http://www.thymeleaf.org" lang="en">

- Su dung expression de connect du lieu

		  ${"attributename"} : tra ve gia tri (doi tuong) cua model attributes (hoac la cua 'attributename' trong model).

		  *{"..."} : tra ve gia tri cua mot bien (field) cua mot doi tuong cho truoc boi (th:object).

		  @{"..."} : xu ly va tra ve url cua server.

		  #{"key"} : tra ve gia tri (message) tu 'key' da duoc dinh nghia trong .properties file.

- Su dung tien to th: de connect du lieu

		  th:text : the hien gia tri duoc lay tu cac bieu thuc tren
		  vd: <span th:text="#{welcome.message}" />
		  Tao the span voi noi dung duoc lay tu key 'welcome.message' trong properties file

		  th:each : lap qua cac phan tu nam trong doi tuong duoc lay tu model attributes
		  vd:  <tr th:each="student: ${students}">
					<td th:text="${student.id}" />
					<td th:text="${student.name}" />
			   </tr>
		  th:each se duoc dinh nghia la phan tu student trong danh sach students va lap tat ca.

		  th:action : duoc su dung voi the <form>, cung cap dieu huong khi <form> duoc submit
		  th:object : chi dinh doi tuong trong doan ma java de rang buoc du lieu trong form voi doi tuong do. (them thong tin vao doi tuong)
		  th:field : chi dinh thuoc tinh cua doi tuong se duoc doi chieu voi du lieu trong the cu the
		  vd: <form th:action="@{/add}" th:object="${toDo}" method="post">
					<label>
						<input th:field="*{title}" type="text" class="input-title" placeholder="title">
					</label>
					<label>
						<input th:field="*{dealine}" type="text" class="input-dealine" placeholder="dealine">
					</label>
					<br>
					<label>
						<textarea th:field="*{description}" class="input-description" placeholder="description"></textarea>
					</label>
					<button type="submit" class="add-button">Add</button>
			  </form>
		  Gui post-request den controller voi url "/add"

		  th:href
		  th:for
		  th:value

- Su dung Errors function de the hien loi thuoc tinh

		  #fields.hasErrors() function : kiem tra thuoc tinh (field) vi pham rang buoc (true/ false).
		  #fields.errors() function : the hien loi cu the (tra ve doi tuong FieldError) cua thuoc tinh

		  <li th:each="err : ${#fields.errors('id')}" th:text="${err}" />
    	  <li th:each="err : ${#fields.errors('name')}" th:text="${err}" />
    	  Duyet qua tat ca loi trong FieldError va the hien ra danh sach.

    	  Co the thay the 'name' bang '*', 'all', 'global' de kiem tra trong pham vi rong hon
		  <li th:each="err : ${#fields.errors('all')}" th:text="${err}" />

- Su dung doi tuong Model.

		  Muon tuong tac voi doi tuong java, phai dam bao rang doi tuong do da duoc them vao
		  Model: public String page(Model model) {
					List<ToDo> sortedList = new ArrayList<>(listToDo.getListToDo()); // copy list
					sortedList.sort((o1, o2) -> o1.getDealine().compareTo(o2.getDealine()));
					model.addAttribute("list", sortedList);
					return "index";
				}

2. Cach tao mot copy list

	Trong truong hop muon tao ra mot ban sao cua mot danh sach, moi su thay doi khong lien quan den
	danh sach cu thi su dung : List<Type> copyList = new ArrayList<> (originalList);

3. Cach su dung bieu thuc lamda + Comparator

`	De sap xep copyList theo ngay thang giam dan theo chieu: (2024 -> 2023):
	copyList.sort((o1, o2) -> o1.getDateTime().compareTo(o2.getDateTime()));

6. Application Controller duoc khoi tao duy nhat mot lan

 */
