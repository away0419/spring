<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Insert title here</title>

<script src="http://code.jquery.com/jquery-1.11.2.min.js"></script>

<script>

$(document).ready(function() {
	var num=0;
	$("input[type=button]").click(function() {

		$.ajax({

			url : "jsonjsp.jsp",

			type : "post", //get post둘중하나

			data : {},

			success : function(data) {

				var obj = JSON.parse(data);

				var memberList = obj.memberList;

				var courseName = obj.courseName;

				var memberCount = obj.memberCount;

// 				console.log(data);

				var html = "";

				html += "<h1>과정명 : "+ courseName +"</h1>";

				html += "<h2>인원 : "+ memberCount +"</h2>";

				html += "<table border=1>"

			

					html += "<tr>";

					html += "<td>" + memberList[num].name + "</td>";

					html += "<td>" + memberList[num].birth + "</td>";

					html += "<td>" + memberList[num].address + "</td>";

					html += "</tr>";

			

				html += "</table>";

				$("div").append(html);
				
				num++;

			}

		});

		

		return false; //자기자신이 submit이나 뭔가 실행하는기능이 있다면 그것을 막고 위에 내용만 실행

	});

});

</script>

</head>

<body>

<input type="button" value="확인" />

<div></div>

</body>

</html>



