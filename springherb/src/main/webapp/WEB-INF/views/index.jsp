<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html lang='ko'>
  <head>
    <meta charset='utf-8' />
    <link href='<c:url value="/resources/css/main.css"/>' rel="stylesheet" />
    <script src='<c:url value="/resources/js/main.js"/>'></script>
    <script type="text/javascript" 
	src="<c:url value='/resources/js/jquery-3.6.0.min.js'/>"></script>
    <script>
	var calendar;
    $(function() {
        var calendarEl = document.getElementById('calendar');

        calendar = new FullCalendar.Calendar(calendarEl, {
          headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek,timeGridDay,listMonth'
          },
          navLinks: true, // can click day/week names to navigate views
          businessHours: true, // display business hours
          editable: true,
          selectable: true,
          locale : "ko",
          dayMaxEvents: true,
          select:function(arg){
        	  var title= prompt("일정:");
        	  if(title){
        		  var obj = new Object();
        		  obj.title = title;
        		  obj.startDate = arg.start;
        		  obj.endDate = arg.end;
        		  obj.allday = arg.allDay;
        		  var jsondata= JSON.stringify(obj);

            	  $.ajax({
      				type:'POST',
      				url:"insertSchedule",
      				data:jsondata,
      				contentType: "application/json; charset=utf-8;",
      	            dataType: "json",
      				success : function(data) {
      					alert("성공");
      				}
      			  });
            	  
        		  calendar.addEvent({
        			  title:title,
        		  	  start:arg.start,
        		  	  end:arg.end,
        		  	  allDay:arg.allDay
        		  })
        	  }
        	  
          },
          droppable:true,
          drop:function(arg){
          },
          eventClick:function(arg){
        	  if(confirm("일정을 삭제하시겠습니까?")){
        		  arg.event.remove()
        	  }
          },
          eventAdd: function(arg) {		
      		
      	  }, 
      	  eventChange: function(arg) {	
      		AppCalendar.saveEvent("up", arg);
      	  }, 
      	  eventRemove: function(arg) {	
      		
      	  },
    	  
          events: function(info, successCallback, failureCallback){ 
        	  $.ajax({
  				type:'GET',
  				url:"listSchedule",
  	            dataType: "json",
  				success : function(data) {
  					 var events=[];
  					 
  					 $(data).each(function(index){
  						events.push({
	  						title: data[index].title,
	  						start: data[index].startDate,
	  						end: data[index].endDate,
	  						allDay: data[index].allday
  						});
  						 
  					 });
  					successCallback(events);
  				}
  			  });
      		}
        });
        calendar.render();
        
		$('.add-button').click(function(){
			allSave();
		});
        	
      });
      function allSave(){
    	  var allEvent = calendar.getEvents();
    	  
    	  var events = new Array();
    	  for(var i=0; i<allEvent.length; i++){
    		  var obj = new Object();
    		  
    		  obj.title = allEvent[i]._def.title;
    		  obj.allday = allEvent[i]._def.allDay;
    		  obj.start = allEvent[i]._instance.range.start;
    		  obj.end = allEvent[i]._instance.range.end;
    		  
    		  events.push(obj);
    	  }
    	  var jsondata= JSON.stringify(events);
    	  console.log(jsondata);
    	  
    	  savedata(jsondata);
      }
		function savedata(jsondata){
			$.ajax({
				type:'POST',
				url:"savedata",
				data:{"alldata" :jsondata},
				dataType:'text',
				async:false,
				success : function(data) {
					
				}
			});
		}
    
    </script>
    <style>

      body {
        margin: 40px 10px;
        padding: 0;
        font-family: Arial, Helvetica Neue, Helvetica, sans-serif;
        font-size: 14px;
      }

      #calendar {
        max-width: 1100px;
        margin: 0 auto;
      }
	  .add-button{
	  	position:absolute;
	  	top: 1px;
	  	right : 230px;
	  	background : #2C3E50;
	  	border: 0;
	  	color : white;
	  	height : 35px;
	  	border-radius:3px;
	  	width : 157px;
	  }
    </style>
    </head>
    <body>

      <div id='calendar' style="position : relative;">
      		
      		<div>
      		
      		</div>
      </div>
			<div>
      			<button class="add-button" type="button" >일정추가</button>
      		</div>
      		
    </body>
</html>
