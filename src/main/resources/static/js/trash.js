var trashPage = 0;

$.ajax({
    method: "GET",
    url: `/api/v1/trash?page=${page}`,
    beforeSend : function() {
        document.getElementById('trashAddBtn').display = 'none';
    }
}).done((res) => {
    const trashHome = document.getElementById('trashHome');
    if(res.length < 1){
        trashHome.innerHTML += `<img src="/img/trashcan.png" style="display: block; margin: 0px auto;">`;
        return;
    } else {
        document.getElementById('trashAddBtn').display="inherit";
    }
    for(const item of res) {

        trashHome.innerHTML += `<div class="memoBox" onclick="getMemoTrash(${item.id})">
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
    trashPage += 1;
}).fail((err) => {
    console.log(err);
    swal({
        icon: 'danger',
        text: '메모 등록에 실패하였습니다.',
    });
});

function getMemoTrash(id) {
    $.ajax({
        method: "GET",
        url: `/api/v1/trash/${id}`,
    }).done((res) => {
        $('#myModal70').modal('show');
        memoDetail.innerHTML = '';
        memoDetail.innerHTML += `Title : <br><input type="text" class="form-control" id="memo_modifyTitle" value="${res.title}" disabled>
        <hr>
        Content : <br><textarea rows="10" class="form-control" id="memo_modifyContent" style="overflow:visible" disabled>${res.content}</textarea>`
        const modifyBtn = document.getElementById('memo_modify');
        modifyBtn.style.display = 'none';
        const deleteBtn = document.getElementById('memo_detailDelete');
        deleteBtn.style.display = 'inherit';
        const modifySuccessBtn = document.getElementById('memo_modifyBtn');
        modifySuccessBtn.style.display = 'none';
        const restoreBtn = document.getElementById('memo_restore');
        restoreBtn.style.display = 'inherit';
        restoreBtn.onclick = function() {restoreTrash(res.id);}
        deleteBtn.onclick = function() {deleteTrash(res.id);}
        // $('#myModal2').modal('show');
    }).fail((err) => {
        console.log(err);
        swal({
            icon: 'info',
            text: '메모 등록에 실패하였습니다.',
        });
    });
}

function deleteTrash(id) {
    $.ajax({
        method: "DELETE",
        url: "http://ec2-13-124-86-248.ap-northeast-2.compute.amazonaws.com:8080/api/v1/trash/" + id,
    })
        .done(function(res) {
            $('#myModal70').modal('hide');
            swal({
                icon: 'success',
                text: '메모가 완전히 삭제되었습니다.',
            }).then(result => {
                location.reload();
            });
        })
        .fail(function(err) {
            alert("오류!");
        });
}

function restoreTrash(id) {
    $.ajax({
        method: "GET",
        url: "http://ec2-13-124-86-248.ap-northeast-2.compute.amazonaws.com:8080/api/v1/trash/restore/" + id,
    })
        .done(function(res) {
            if(res) {
                $('#myModal70').modal('hide');
                swal({
                    icon: 'success',
                    text: '메모가 복구되었습니다.',
                }).then(result => {
                    location.reload();
                });
            } else {
                swal({
                    icon: 'danger',
                    text: '메모 복구에 실패했습니다.',
                }).then(result => {
                    location.reload();
                });
            }
        })
        .fail(function(err) {
            alert("오류!");
        });
}

document.getElementById('trashAddBtn').onclick = function() {
    $.ajax({
        method: "GET",
        url: `/api/v1/trash?page=${trashPage}`,
        beforeSend : function() {
            document.getElementById('trashAddBtn').display = 'none';
        }
    }).done((res) => {
        const trashHome = document.getElementById('trashHome');
        if(res.length < 1){
            swal({
                icon: 'info',
                text: '더이상 삭제된 메모가 없습니다.',
            });
        }
        for(const item of res) {

            trashHome.innerHTML += `<div class="memoBox" onclick="getMemoTrash(${item.id})">
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
        trashPage += 1;
        document.getElementById('trashAddBtn').display = 'inherit';
    }).fail((err) => {
        console.log(err);
        swal({
            icon: 'danger',
            text: '메모 등록에 실패하였습니다.',
        });
    });
}