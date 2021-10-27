/**
 * 上传图片
 * user:wangzhiwen
 * param: file_id
 * return:
 * date:2017/8/10 11:10
 **/
function uploadJs(file_id,packageName){
    var formData = new FormData();
    formData.append("file",$("#" + file_id + "_file_upload")[0].files[0]);
    formData.append("packageName",packageName);
    $.ajax({
        url:'uploadFtpFile', /*接口域名地址*/
        type:'post',
        data: formData,
        dataType: "json",
        contentType: false,
        processData: false,
        success:function(res){
            if(res.status == 200){
                $("#" + file_id).attr("src",res.object);
                $("#" + file_id + "_hid").val(res.object);
                alert('成功');
            }else{
                alert(res.msg);
            }
        }
    })
}
