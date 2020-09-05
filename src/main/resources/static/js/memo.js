console.log("읽었냐");
var page = 0;
    $.ajax({
        method: "GET",
        url: `/api/v1/memo?page=${page}`,
        beforeSend : function() {
            document.getElementById('memoAddBtn').display = 'none';
        }
    }).done((res) => {
        const memoHome = document.getElementById('memoHome');
        if(res.length < 1){
         swal({
                icon: 'info',
                text: '아직 작성된 메모가 없습니다.',
          });
            memoHome.innerHTML += `<img src="/img/no.png" style="display: block; margin: 0px auto;">`;
            return;
        }
        for(const item of res) {
        console.log(typeof item.createdDate);

            memoHome.innerHTML += `<div class="memoBox" onclick="getMemo(${item.id})">
                                 <div class="memoLine"></div>
                                 <div class="memoBoxContents">
                                     <div id="memoTitle" class="memoTitle">${item.title}</div>
                                     <div class="wrap">
                                     <div class="memoDate">${getTime(item.createdDate)}</div>
                                     <div class="memoContents">${sliceText(item.content)}</div>
                                     </div>
                                 </div>
                               </div><br><br>`;
        }
        page += 1;
    }).fail((err) => {
        console.log(err);
        swal({
            icon: 'danger',
            text: '메모 등록에 실패하였습니다.',
        });
    });

  document.getElementById('addmemo').onclick = function(){
    $('#myModal40').modal('show');
  }

  document.getElementById('registBtn').onclick = function(){
    const title = document.getElementById('memo_title');
    const content = document.getElementById('memo_content');

    if(!title.value){
        alert("title을 작성해주세요");
        return false;
    } else if(!content.value) {
        alert("content를 작성해주세요");
        return false;
    }

    const result = {
        "title" : title.value,
        "content" : content.value
    };
    console.log(result);

     $.ajax({
          method: "POST",
          url: `/api/v1/memo`,
          data: JSON.stringify(result),
          beforeSend: function (xhr) {
                      xhr.setRequestHeader("Content-type","application/json");
                      },
        })
          .done(function(res) {
                console.log(res);
              if(res) {
                $('#myModal40').modal('hide');
                  swal({
                        icon: 'success',
                        text: '메모가 등록되었습니다.',
                  }).then(result => {
                    location.reload();
                  });
              } else {
                swal({
                    icon: 'danger',
                    text: '메모 등록에 실패하였습니다.',
                  }).then(result => {
                    location.reload();
                  });
              }
          })
          .fail(function(err) {
              alert("오류!");
          });
}

//function memoList(id) {
//    const result = getElementById('id');
//    const memofolder = document.getElementById('memofolder');
//    memofolder.innerHTML = '';
//    memofolder.innerHTML += `Title: <input type="text" class="form-control" value="${result.title}"`;
//}

function getMemo(id) {
    $.ajax({
         method: "GET",
         url: `/api/v1/memo/${id}`,
     }).done((res) => {
     console.log(res);
        $('#myModal70').modal('show');
        memoDetail.innerHTML = '';
        memoDetail.innerHTML += `Title : <br><input type="text" class="form-control" id="memo_modifyTitle" value="${res.title}" disabled>
        <hr>
        Content : <br><textarea rows="10" class="form-control" id="memo_modifyContent" style="overflow:visible" disabled>${res.content}</textarea>`
        const modifyBtn = document.getElementById('memo_modify');
        modifyBtn.style.display = 'inherit';
        const deleteBtn = document.getElementById('memo_detailDelete');
        deleteBtn.style.display = 'inherit';
        const modifySuccessBtn = document.getElementById('memo_modifyBtn');
        modifySuccessBtn.style.display = 'none';
        const restoreBtn = document.getElementById('memo_restore');
        restoreBtn.style.display = 'none';
        modifySuccessBtn.onclick = function() {modifyMemo(res.id);}
        deleteBtn.onclick = function() {deleteMemo(res.id);}
        // $('#myModal2').modal('show');
     }).fail((err) => {
         console.log(err);
         swal({
             icon: 'info',
             text: '메모 등록에 실패하였습니다.',
         });
     });
}

document.getElementById('memo_modify').onclick = function() {
  const title = document.getElementById('memo_modifyTitle');
  title.disabled = false;
  const content = document.getElementById('memo_modifyContent');
  content.disabled = false;
  const modifyBtn = document.getElementById('memo_modify');
  modifyBtn.style.display = 'none';
  const deleteBtn = document.getElementById('memo_detailDelete');
  deleteBtn.style.display = 'none';
  const modifySuccessBtn = document.getElementById('memo_modifyBtn');
  modifySuccessBtn.style.display = 'inherit';
}

function modifyMemo(id) {
      const title = document.getElementById('memo_modifyTitle').value;
      const content = document.getElementById(('memo_modifyContent')).value;

      const result = {
          id: id,
          title: title,
          content: content
      }

    $.ajax({
        method: "PATCH",
        url: "http://ec2-13-124-86-248.ap-northeast-2.compute.amazonaws.com:8080/api/v1/memo",
        data: JSON.stringify(result),
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Content-type","application/json");
        },
    })
        .done(function(result) {
            console.log(result);
            if(result) {
                $('#myModal70').modal('hide');
                swal({
                    icon: 'success',
                    text: '메모가 수정되었습니다.',
                }).then(result => {
                    location.reload();
                });
            } else {
                swal({
                    icon: 'danger',
                    text: '메모 수정에 실패했습니다.',
                }).then(result => {
                    location.reload();
                });
            }
        })
        .fail(function(err) {
            alert("오류!");
        });
}

function getTime(time) {
    const timeNumber = Date.parse(time);
    const date = new Date(timeNumber);
    return `${date.getFullYear()}년 ${date.getMonth() + 1}월 ${date.getDate()}일 ${date.getHours() < 12 ? "오전" : "오후"} ${(h = date.getHours() % 12) ? h : 12}:${date.getMinutes()}`;
}

const sliceText = (text) => (text.length > 45 ? text.substring(0,45) + "..." : text);

function deleteMemo(id) {
    $.ajax({
        method: "DELETE",
        url: "http://ec2-13-124-86-248.ap-northeast-2.compute.amazonaws.com:8080/api/v1/memo/" + id,
    })
        .done(function(res) {
            if(res) {
                $('#myModal70').modal('hide');
                swal({
                    icon: 'success',
                    text: '메모가 삭제되었습니다.',
                }).then(result => {
                    location.reload();
                });
            } else {
                swal({
                    icon: 'danger',
                    text: '메모 삭제에 실패했습니다.',
                }).then(result => {
                    location.reload();
                });
            }
        })
        .fail(function(err) {
            alert("오류!");
        });
}

document.getElementById('memoAddBtn').onclick = function() {
    $.ajax({
        method: "GET",
        url: `/api/v1/memo?page=${page}`,
        beforeSend : function() {
            document.getElementById('memoAddBtn').display = 'none';
        }
    }).done((res) => {
        const memoHome = document.getElementById('memoHome');
        if(res.length < 1){
            swal({
                icon: 'info',
                text: '더이상 작성된 메모가 없습니다.',
            });
        }
        for(const item of res) {
            console.log(typeof item.createdDate);

            memoHome.innerHTML += `<div class="memoBox" onclick="getMemo(${item.id})">
                                 <div class="memoLine"></div>
                                 <div class="memoBoxContents">
                                     <div id="memoTitle" class="memoTitle">${item.title}</div>
                                     <div class="wrap">
                                     <div class="memoDate">${getTime(item.createdDate)}</div>
                                     <div class="memoContents">${sliceText(item.content)}</div>
                                     </div>
                                 </div>
                               </div><br><br>`;
        }
        page += 1;
        document.getElementById('memoAddBtn').display = 'inherit';
    }).fail((err) => {
        console.log(err);
        swal({
            icon: 'danger',
            text: '메모 등록에 실패하였습니다.',
        });
    });
}