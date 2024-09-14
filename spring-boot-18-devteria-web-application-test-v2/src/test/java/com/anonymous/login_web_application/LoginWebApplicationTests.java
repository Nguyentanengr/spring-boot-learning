package com.anonymous.login_web_application;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LoginWebApplicationTests {

    @Test
    void contextLoads() {}
}

/*
CONTENT:

1. dependence:

	spring-boot-starter-test bao gom:
		JUnit 				- thu vien tieu chuan cho viet test, chay va kiem thu cac don vi

		Spring Test 		- cung cap cac tien ich kiem thu tich hop và cac cong cu
							giup kiem thu cac ung dung Spring ma khong can phai
							khoi dong toan server, Ho tro kiem thu web (MockMvc),
							Kiem thu moi truong ung dung (TestContext Framework)

		AssertJ 			- Cung cap cac cu phap fluent assertions (assertThat().startsWith().endWith().contains();

		Hamcrest 			- La thu vien matchar de viet cac bieu thuc dieu kien (assertThat, set, status,...)

		Mockito 			- cho phep tao cac doi tuong gia lap de kiem thu, khong phu thuoc vao
								logic thuc te

		JSON-assert			- su dung de so sanh cac chuoi json mot cach chi tiet va linh hoat khong quan tam den thu tu

		JsonPath			- cho phep trich xuat du lieu tu cac doi tuong json thong qua duong dan


	Test & Jacoco
 */
