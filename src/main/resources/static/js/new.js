var calendar;
const log = console.log;
$.ajax({
    method: "GET",
    url: "http://ec2-13-124-86-248.ap-northeast-2.compute.amazonaws.com:8080/schedule",
}).done(async function(res) {
    var calendarEl = document.getElementById('calendar');

    await initThemeChooser({
      init: async function(themeSystem) {
        calendar = new FullCalendar.Calendar(calendarEl, {
          themeSystem: themeSystem,
          headerToolbar: {
            left: 'title',
            center: '',
            right: 'today prev,next'
          },
          // initialDate: '2020-06-12',
          initialView: 'dayGridMonth',
          weekNumbers: true,
          navLinks: false, // can click day/week names to navigate views
          editable: true,
          selectable: true,
          select: function(arg) {
            const realDate = arg.end.setDate(arg.end.getDate() - 1);
            const realEnd = formatDate(realDate);
            document.getElementById("schedule_title").value = "";
            document.getElementById("content").value = "";
            document.getElementById("start").value = arg.startStr;
            document.getElementById("end").value = arg.endStr;
            document.getElementById("realEnd").value = realEnd;
//            document.getElementById("distinct").value;
            document.getElementById('detailBox').innerHTML = '';
            document.getElementById('detail').innerHTML = '';
            if(arg.startStr !== realEnd) {
                $('#myModal').modal('show');
            } else {
                const todaySchedule = [];
                for(const item of res) {
                    const days = getDateRange(item.start, item.realEnd);
                    if(days.includes(arg.startStr)) {
                        todaySchedule.push(item);
                    }
                }
                if(todaySchedule.length > 0) {
                    for(const item of todaySchedule) {
                        document.getElementById('detailBox').innerHTML += `<div style="margin-bottom: 1%;cursor:pointer; border-radius: 10px; padding: 10px 20px; box-shadow: 0px 0px 8px lightgray;" onclick="goDetail('${item.id}')">${item.title}</div>`;
                    }
                    $('#myModal1').modal('show');
                } else {
                    swal({
                        icon: 'info',
                        title: 'info',
                        text: '아직 등록된 일정이 없습니다.',
                        button: "일정추가",
                        showCancleButton: true,
                    }).then(result => {
                          console.log(result);
                          if (result) {
                            $('#myModal').modal('show');
                          }
                      })
                }
            }
            calendar.unselect();
           },
          nowIndicator: true,
          dayMaxEvents: true, // allow "more" link when too many events
          // showNonCurrentDates: false,
          locale: 'ko',
          events: res,
        });
        await calendar.render();
          let idx;
          const list = document.getElementsByClassName('weatherInput');
          const listSize = list.length;
          console.log(listSize + '= listSize');
          for(let i = 0; i < listSize; i++) {
              if(list[i].querySelector('a').innerText==='1일') {
                  idx = i - 1;
                  break;
              }
          }

          const weatherList = makeWeekList();

          for(const item of weatherList) {
              console.log("..." + item);
              $.ajax({
                  method: "GET",
                  url: "http://ec2-13-124-86-248.ap-northeast-2.compute.amazonaws.com:8080/api/v1/weather/" + item,
              }).done(function(res) {
                  console.log(idx + "= idx");
                  console.log(item + "= item");
                  console.log(item * 1 + "= item * 1");
                  const inputWeatherItem = list[idx + (item * 1)];
                  console.log(inputWeatherItem);
                  const weatherItem = `<img src="${res.imgURL}" width="36" height="36">`;
                  inputWeatherItem.innerHTML += weatherItem;
              })
                  .fail(function(err) {
                  });
          }
      },

      change: function(themeSystem) {
        calendar.setOption('themeSystem', themeSystem);
      }

    });

  function inputDateComparison(obj) {

      // 날짜 입력 엘리먼트 ID는 startDate(시작일), endDate(종료일)로 동일해야 한다.
      var startDate = inputDateSplit(document.getElementById("start").value);    // 시작일
      var endDate = inputDateSplit(document.getElementById("end").value);        // 종료일

      var objDate = inputDateSplit(obj.value);    // 입력한 엘리먼트의 일자
      // 입력일을 확인하는 이유는 현재 작성한 일자가 시작일인지 종료일인지 확인하기 위해서이다.

      if(startDate == objDate && startDate > endDate) {

          alert("시작일이 종료일보다 이 후 일수는 없습니다.\n다시 선택하여 주시기 바랍니다.");
          obj.value = document.getElementById("end").value;
          obj.focus();
      } else if(endDate == objDate && endDate < startDate) {

          alert("종료일이 시작일보다 이 전 일수는 없습니다.\n다시 선택하여 주시기 바랍니다.");
          obj.value = document.getElementById("start").value;
          obj.focus();
      } else {
          return false;
      }
  };

  document.getElementById('add').onclick = function(){
    $('#myModal').modal('show');
  }

  document.getElementById('submitBtn').onclick = function() {
      const title = document.getElementById("schedule_title").value;
      const startDate = document.getElementById("start").value;
      const endDate = document.getElementById("end").value;
      const realEnd = document.getElementById("realEnd").value;
      const content = document.getElementById("content").value;
      const backgroundColorValue = 170 - document.getElementById("exRange").value;
      const backgroundColor = `rgb(238, 178,${backgroundColorValue})`;
//      const salt = Math.round(new Date().valueOf() * Math.random()) + '';

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
          "display" : dot(startDate,realEnd),
          "backgroundColor" : backgroundColor,
          "realEnd" : realEnd
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
            $('#myModal').modal('hide');
              swal({
                    icon: 'success',
                    text: '일정이 등록되었습니다.',
              }).then(result => {
                location.reload();
              });
          })
          .fail(function(err) {
              alert("여기가 문제인듯!");
          });
  }
}).fail(function(err) {
    alert("실패");
});

function makeWeekList() {
    const result = [];
    const today = new Date();
    for(let i = 0; i < 7; i++) {
        let pushItem = today.getDate() + i;
        if(pushItem < 10) {
            pushItem = "0" + pushItem;
        }
        result.push(pushItem);
    }
    return result;
}

function formatDate(date) {
var d = new Date(date),
month = '' + (d.getMonth() + 1),
day = '' + d.getDate(),
year = d.getFullYear();
if (month.length < 2) month = '0' + month; if (day.length < 2) day = '0' + day; return [year, month, day].join('-'); }

function dot(start, realEnd) {
    let dot;
    start === realEnd ? dot = "list-item" : dot = "block";
    return dot;
}

function checkCal() {
//    console.log(calendar.getEvents());
}

function getDateRange(startDate, endDate)
    {
        var dateMove = new Date(startDate);
        var strDate = startDate;
        var result = [];
        if (startDate == endDate)
        {
            var strDate = dateMove.toISOString().slice(0,10);
            result.push(strDate);
        }
        else
        {
            while (strDate < endDate)
            {
                var strDate = dateMove.toISOString().slice(0, 10);
                result.push(strDate);
                dateMove.setDate(dateMove.getDate() + 1);
            }
        }
        return result;
    };

function goDetail(id) {
    const event = getEventById(id);
    const eventContent = event._def.extendedProps.description;
    const detail = document.getElementById('detail');
    document.getElementById('mId').value = id;
    detail.innerHTML = '';
    detail.innerHTML += `중요도 : <br><input type="range" class="custom-range" id="modifyRange" value=${event.backgroundColor.split(',')[2].replace(')','')} disabled>
    일정 제목 : <br><input type="text" class="form-control" id="modifyTitle" value="${event.title}" disabled>
    일정 내용 : <br><input type="text" class="form-control" id="modifyContent" value="${eventContent}" disabled>`;
    $('#myModal1').modal('hide');
    const modifyBtn = document.getElementById('modify');
    modifyBtn.style.display = 'inherit';
    const deleteBtn = document.getElementById('detailDelete');
    deleteBtn.style.display = 'inherit';
    const modifySuccessBtn = document.getElementById('modifyBtn');
    modifySuccessBtn.style.display = 'none';
    $('#myModal2').modal('show');
}

function getEventById(id) {
    const dataArray = [...calendar.getEvents()];
    for(const item of dataArray) {
        if(item.id === id) {
            return item;
        }
    }
}

document.getElementById('modify').onclick = function() {
    const id = document.getElementById('mId').value;
    const range = document.getElementById('modifyRange');
    range.disabled= false;
    const title = document.getElementById('modifyTitle');
    title.disabled = false;
    const content = document.getElementById('modifyContent');
    content.disabled = false;
    const modifyBtn = document.getElementById('modify');
    modifyBtn.style.display = 'none';
    const deleteBtn = document.getElementById('detailDelete');
    deleteBtn.style.display = 'none';
    const modifySuccessBtn = document.getElementById('modifyBtn');
    modifySuccessBtn.style.display = 'inherit';
    modifySuccessBtn.onclick = function() {
        modifySchedule(id);
    }
}


function modifySchedule(id) {
    const title = document.getElementById("modifyTitle").value;
  const content = document.getElementById("modifyContent").value;
      const backgroundColorValue = 170 - document.getElementById("modifyRange").value;
      const backgroundColor = `rgb(238, 178,${backgroundColorValue})`;

      const event = {
        id: id,
        title : title,
        description: content,
        backgroundColor : backgroundColor
      }

    $.ajax({
      method: "PATCH",
      url: "http://ec2-13-124-86-248.ap-northeast-2.compute.amazonaws.com:8080/schedule",
      data: JSON.stringify(event),
      beforeSend: function (xhr) {
                  xhr.setRequestHeader("Content-type","application/json");
                  },
    })
      .done(function(res) {
            console.log(res);
          if(res) {
            $('#myModal2').modal('hide');
              swal({
                    icon: 'success',
                    text: '일정이 수정되었습니다.',
              }).then(result => {
                location.reload();
              });
          } else {
            swal({
                icon: 'danger',
                text: '일정이 수정되었습니다.',
              }).then(result => {
                location.reload();
              });
          }
      })
      .fail(function(err) {
          alert("오류!");
      });
}

    document.getElementById('detailDelete').onclick = function() {
        const id = document.getElementById('mId').value;
        console.log(id);
        $.ajax({
            method: "DELETE",
            url: `http://ec2-13-124-86-248.ap-northeast-2.compute.amazonaws.com:8080/schedule/${id}`
        }).done(function(res) {
        $('#myModal2').modal('hide');
        swal({
            icon: 'success',
            text: '일정이 삭제되었습니다.'
        }).then((result) => {
            location.reload();
        });
        });
    }

//    function w3_open() {
//      document.getElementById("mySidebar").style.display = "block";
//    }
//
//    function w3_close() {
//      document.getElementById("mySidebar").style.display = "none";
//    }