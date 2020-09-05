function w3_open() {
  document.getElementById("mySidebar").style.display = "block";
}

function w3_close() {
  document.getElementById("mySidebar").style.display = "none";
}
function userOut() {
    $.ajax({
        method: "DELETE",
        url: `/api/v1/user`,
    })
    .done(function(res) {
        swal({
            icon: 'success',
            text: '회원 탈퇴가 완료 되었습니다.',
        }).then(result => {
            location.href = '/logout';
        });
    })
    .fail(function(err) {
        alert("오류!");
    });
}