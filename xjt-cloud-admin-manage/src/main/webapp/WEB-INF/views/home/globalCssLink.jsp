<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" src="/resource/js/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="/resource/js/jqueryeui-1.3.6/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/resource/js/jqueryeui-1.3.6/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="/resource/js/jqueryeui-1.3.6/jquery.cookie.js"></script>
<script type="text/javascript" src="/resource/js/jqueryeui-1.3.6/datagrid-detailview.js"></script>
<script type="text/javascript" src="/resource/js/jqueryeui-1.3.6/prettify.js"></script>
<script type="text/javascript" src="/resource/js/jqueryeui-1.3.6/jquery.json-2.4.min.js"></script>
<script type="text/javascript" src="/resource/views/uploadFile.js"></script>
<script type="text/javascript" src="/resource/js/ajaxfileupload.js"></script>

<link rel="stylesheet" type="text/css" href="/resource/css/layout.css" />
<link rel="stylesheet" type="text/css" href="/resource/css/jquery-ui-1.8.16.custom.css"/>
<link rel="stylesheet" type="text/css" href="/resource/js/jqueryeui-1.3.6/themes/icon.css">
<link rel="stylesheet" type="text/css" href="/resource/css/main.css">
<link rel="stylesheet" type="text/css" href="/resource/css/skin01.css">

<script type="text/javascript" >
    var parameter = {};//请求传递参数
    var titleInfo = "提示信息";
    staticServer='${rootPath}';

    /**
     * 弹出提示信息
     * user:wangzhiwen
     * param: msg
     * return:
     * date:2017/8/11 14:04
     **/
    function showMsg(msg){
        $.messager.show({
            title : titleInfo,
            msg : msg,
            timeout : 2000,
            showType : 'slide'
        });
    }

    /**
     * 清空Dvi下面信息信息
     * user:wangzhiwen
     * *param: divName div名称
     * return:
     * date:2017/8/10 11:19
     **/
    function cleanDialog(divName){
        $('#' + divName + ' input').val('');
        $('#' + divName + ' select').val('');
        $('#' + divName + ' img').attr('src','');
        $('#' + divName + ' textarea').val('');
    }

    /**
     * 初使化页面数据
     * user:wangzhiwen
     * param: obj 初使化div数据，对象属性名必须与控件name一致
     * param: divId对象id
     * return:
     * date:2017/8/10 14:00
     **/
    function generateDialog(obj,divId){
        $.each(obj, function(key, val) {
            var objName = "#" + divId + " input[name='" + key + "']";
            if($(objName) != null){
                if($("#" + divId + " img#" + key).length > 0){
                    $("#" + key).attr("src",val);
                }else if($("#" + divId + " select[name=" + key + "]").length > 0){
                    $("#" + divId + " select[name=" + key + "]").val(val);
                }else if($("#" + divId + " textarea[name=" + key + "]" ).length > 0){
                    $("#" + divId + " textarea[name=" + key + "]").val(val);
                }else if($(objName).attr("type") == "radio"){
                    $(objName + "[value='" + val+ "']").prop('checked','checked');
                }
                $(objName).val(val);

            }
        });
    }

    function selectAll(checkbox,inputName){
        if(checkbox.checked == true){
            $("input[name='"+ inputName +"']").attr("checked", 'checked');
            $("input[name='"+ inputName +"']").prop("checked",true);
        }else{
            $("input[name='"+ inputName +"']").removeAttr("checked");
            $("input[name='"+ inputName +"']").prop("checked",false);
        }
    }
    
    function  getCheckedRadioValue(inputName) {
        var value = null;
        var obj = document.getElementsByName(inputName);
        for(var k in obj){
            var selectId = obj[k].value;
            if(selectId){
                if(obj[k].checked){
                    //取到对象数组后，我们来循环检测它是不是被选中
                    //如果选中，将value添加到变量s中
                    if(value != null){
                        value += ","+selectId;
                    }else{
                        value = selectId;
                    }
                }
            }
        }
        return value;
    }
</script>