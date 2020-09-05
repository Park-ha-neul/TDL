$.ajax({
    method: "GET",
    url: `/api/v1/day`,
}).done((res) => {
    const day = document.getElementById('day');
    if(res.length < 1){
        console.log("데이야 데이야 나와 시발롬아");
        swal({
            icon: 'info',
            text: '아직 D-DAY가 없습니다.',
        });
        day.innerHTML += `<img src="/img/d-day.png" style="display: block; margin: 0px auto; width: 80%;">`;
    }
    for(const item of res) {
        day.innerHTML += `<div class="dayBox" onclick="getDay('${item.id}')">
                         <div class="dayLine"></div>
                         <div class="dayBoxContents">
                             <div id="dayTitle" class="dayTitle">${item.title}</div>
                             <div class="countDate">
                             <div class="dayDate">${formatDate(item.date)}</div>
                             <div class="dayContents">${calDate(item.date)}</div>
                             </div>
                         </div>
                       </div><br><br>`;
    }
}).fail((err) => {
    console.log(err);
    swal({
        icon: 'danger',
        text: 'D-DAY 등록에 실패하였습니다.',
    });
});

document.getElementById('addDay').onclick = function(){
    $('#myModalDay').modal('show');
}

function calDate(dateText){
    let today = new Date().setHours(9,0,0,0);
    const Dday = new Date(dateText);
    const diff = (today - Dday)/1000/60/60/24;

    if(diff < 0) {
        return `D${diff}`;
    } else if(diff > 0) {
        return `D+${diff}`;
    } else {
        return `D-Day`;
    }
}

document.getElementById('registDayBtn').onclick = function(){
    const title = document.getElementById('day_title');
    const date = document.getElementById('day_date');

    if(!title.value){
        alert("title을 작성해주세요");
        return false;
    } else if(!date.value) {
        alert("D-DAY 날짜를 선택해주세요");
        return false;
    }

    const result = {
        "title" : title.value,
        "date" : new Date(date.value).toString()
    };

    $.ajax({
        method: "POST",
        url: `/api/v1/day`,
        data: JSON.stringify(result),
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Content-type","application/json");
        },
    })
        .done(function(res) {
            if(res) {
                $('#myModalDay').modal('hide');
                swal({
                    icon: 'success',
                    text: 'D-DAY가 등록되었습니다.',
                }).then(result => {
                    location.reload();
                });
            } else {
                swal({
                    icon: 'danger',
                    text: 'D-DAY 등록에 실패하였습니다.',
                }).then(result => {
                    location.reload();
                });
            }
        })
        .fail(function(err) {
            alert("오류!");
        });
};

function formatDate(date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();
    if (month.length < 2) month = '0' + month; if (day.length < 2) day = '0' + day; return [year, month, day].join('-');
}

//상세보기
function getDay(id) {
    $.ajax({
        method: "GET",
        url: `/api/v1/day/${id}`,
    }).done((res) => {
        console.log(res);
        $('#myModalDayDetail').modal('show');
        document.getElementById('dayDetail').innerHTML = '';
        document.getElementById('dayDetail').innerHTML += `Title : <br><input type="text" class="form-control" id="day_modifyTitle" value="${res.title}" disabled>
        <hr>
        D-DAY Date : <br><input type="date" class="form-control" id="day_modifyDate" value="${formatDate(res.date)}" disabled>`
        const modifyBtn = document.getElementById('day_modify');
        modifyBtn.style.display = 'inherit';
        const deleteBtn = document.getElementById('day_detailDelete');
        deleteBtn.style.display = 'inherit';
        const modifySuccessBtn = document.getElementById('day_modifyBtn');
        modifySuccessBtn.style.display = 'none';
        modifySuccessBtn.onclick = function() {modifyDay(res.id);}
        deleteBtn.onclick = function() {deleteDay(res.id);}
    }).fail((err) => {
        console.log(err);
        swal({
            icon: 'info',
            text: '디데이 작성에 실패하였습니다.',
        });
    });
}

document.getElementById('day_modify').onclick = function() {
    const title = document.getElementById('day_modifyTitle');
    title.disabled = false;
    const date = document.getElementById('day_modifyDate');
    date.disabled = false;
    const modifyBtn = document.getElementById('day_modify');
    modifyBtn.style.display = 'none';
    const deleteBtn = document.getElementById('day_detailDelete');
    deleteBtn.style.display = 'none';
    const modifySuccessBtn = document.getElementById('day_modifyBtn');
    modifySuccessBtn.style.display = 'inherit';
};

function modifyDay(id) {
    const title = document.getElementById('day_modifyTitle').value;
    const date = document.getElementById(('day_modifyDate')).value;

    const result = {
        id: id,
        title: title,
        date: new Date(date).toString()
    }

    $.ajax({
        method: "PATCH",
        url: "http://ec2-13-124-86-248.ap-northeast-2.compute.amazonaws.com:8080/api/v1/day",
        data: JSON.stringify(result),
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Content-type","application/json");
        },
    })
        .done(function(result) {
            console.log(result);
            if(result) {
                $('#myModalDayDetail').modal('hide');
                swal({
                    icon: 'success',
                    text: '디데이가 수정되었습니다.',
                }).then(result => {
                    console.log(result);
                    location.reload();
                });
            } else {
                swal({
                    icon: 'danger',
                    text: '디데이 수정에 실패했습니다.',
                }).then(result => {
                    location.reload();
                });
            }
        })
        .fail(function(err) {
            alert("오류!");
        });
}

function deleteDay(id) {
    $.ajax({
        method: "DELETE",
        url: "http://ec2-13-124-86-248.ap-northeast-2.compute.amazonaws.com:8080/api/v1/day/" + id,
    })
        .done(function(res) {
            if(res) {
                $('#myModalDayDetail').modal('hide');
                swal({
                    icon: 'success',
                    text: '디데이가 삭제되었습니다.',
                }).then(result => {
                    location.reload();
                });
            } else {
                swal({
                    icon: 'danger',
                    text: '디데이 삭제에 실패했습니다.',
                }).then(result => {
                    location.reload();
                });
            }
        })
        .fail(function(err) {
            alert("오류!");
        });
}

