$.ajax({
method: "GET",
url: "http://ec2-13-124-86-248.ap-northeast-2.compute.amazonaws.com:8080/schedule",
})
.done(function(res) {
  const date = new Date();
          const d = date.getDate();
          const m = date.getMonth();
          const y = date.getFullYear();
          $('#external-events div.external-event').each(function() {
              const eventObject = {
                  title: $.trim($(this).text())
              };
              $(this).data('eventObject', eventObject);
              $(this).draggable({
                  zIndex: 999,
                  revert: true,
                  revertDuration: 0
              });
          });
          const calendar =  $('#calendar').fullCalendar({
              header: {
                  left: 'title',
                  center: 'agendaDay,agendaWeek,month',
                  right: 'prev,next today'
              },
              dayMaxEvents: true,
              editable: true,
              firstDay: 1,
              selectable: true,
              navLinks : true,
              defaultView: 'month',
              axisFormat: 'h:mm',
              events: res,
              columnFormat: {
                  month: 'ddd',
                  week: 'ddd d',
                  day: 'dddd M/d',
                  agendaDay: 'dddd d'
              },
              titleFormat: {
                  month: 'MMMM yyyy',
                  week: "MMMM yyyy",
                  day: 'MMMM yyyy'
              },
              allDaySlot: false,
              selectHelper: true,
              dateClick: function(info) {
                log.info('Clicked on:' + info.dateStr);
              },
  //             select: function(start, end, allDay) {
  //                 const title = document.getElementById('calendarTitle');
  //                 const title = prompt('Event Title:');
  //                 if (title) {
  //                 	calendar.fullCalendar('renderEvent',
  //                 		{
  //                 			title: document.
  //                 			start: start,
  //                 			end: end,
  //                 			allDay: allDay
  //                 		},
  // true
  // )};
  //     calendar.fullCalendar('unselect');
  // },
      droppable: false,
  });
})
.fail(function(err) {
    alert("오류낫어!");
    console.log(err);
});




  function inputDateSplit(obj) {
    var dateArray = obj.split("-");
    return dateArray[0] + dateArray[1] + dateArray[2];
  };

  function inputDateComparison(obj) {

    // 날짜 입력 엘리먼트 ID는 startDate(시작일), endDate(종료일)로 동일해야 한다.
    var startDate = inputDateSplit(document.getElementById("startDate").value);    // 시작일
    var endDate = inputDateSplit(document.getElementById("endDate").value);        // 종료일

    var objDate = inputDateSplit(obj.value);    // 입력한 엘리먼트의 일자
    // 입력일을 확인하는 이유는 현재 작성한 일자가 시작일인지 종료일인지 확인하기 위해서이다.

    if(startDate == objDate && startDate > endDate) {

        alert("시작일이 종료일보다 이 후 일수는 없습니다.\n다시 선택하여 주시기 바랍니다.");
        obj.value = document.getElementById("endDate").value;
        obj.focus();
    } else if(endDate == objDate && endDate < startDate) {

        alert("종료일이 시작일보다 이 전 일수는 없습니다.\n다시 선택하여 주시기 바랍니다.");
        obj.value = document.getElementById("startDate").value;
        obj.focus();
    } else {
        return false;
    }
};

function getDateRange(startDate, endDate) {
    let result = [];
    let dateMove = new Date(startDate);
    let strDate = startDate;
    if (startDate == endDate)
    {
        let strDate = dateMove.toISOString().slice(0,10);
        result.push(strDate);
    }
    else
    {
        while (strDate < endDate)
        {
            strDate = dateMove.toISOString().slice(0, 10);
            result.push(strDate);
            console.log(result);
            console.log(dateMove);
            dateMove.setDate(dateMove.getDate() + 1);
        }
    }
    return result;
};



document.getElementById('submitBtn').onclick = function() {
    const title = document.getElementById("schedule_title").value;
    const startDate = document.getElementById("startDate").value;
    const endDate = document.getElementById("endDate").value;
    const content = document.getElementById("schedule_content").value;

    if(title.length < 1) {
        alert("title을 작성해주세요");
        return false;
    } else if(startDate === "") {
        alert("시작일을 작성해주세요");
        return false;
    } else if(endDate === "") {
        endDate = startDate;
    }

    const event = {
        "title" : title,
        "start" : startDate,
        "end" : endDate,
        "description" : content,
        "dateRange" : getDateRange(startDate, endDate)
    };


    $.ajax({
        method: "POST",
        url: "http://ec2-13-124-86-248.ap-northeast-2.compute.amazonaws.com:8080/schedule",
        data: JSON.stringify(event),
        beforeSend: function (xhr) {
                    xhr.setRequestHeader("Content-type","application/json");
                    },
      })
        .done(function(res) {
            $('#calendar').fullCalendar( 'renderEvent', event, true);
            alert("일정이 등록되었습니다.");
        })
        .fail(function(err) {
            alert("오류!");
        });
}
