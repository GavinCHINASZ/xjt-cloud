
function modifyPass() {
    var password = $('#passwordTxt').val();
    var password2 = $('#passwordTxt2').val();
    
    if (password != password2) {
        alert("两次输入的密码不一致!")
        return;
    }
    parameter = $('#userForm').serialize();
    $.post("/user/modifyPassword?" + access_token,parameter,function(data){
        if(200 == data.status){
            $('#user_dialog').dialog('close');
            showMsg('密码修改成功！');
        }else{
            showMsg(data.msg);
        }
    },"json").error(function (error, status, info){
        showMsg(error.responseText);
    });
}