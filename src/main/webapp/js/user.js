function addUser() {
    Ajax.getHtml('/userAdd.html', function (html) {

        $(function (){
            let date = new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + new Date().getDate();
            $('#createDate').attr("value", date);
        })

        layer.open({
            type: 1,
            title: '新增用户',
            area: ['700px'],
            content: html,
            btn: ['确认', '取消'],
            yes: function () {
                testUser();
            },
            btn2: function () {
                console.log('')
            }
        })
    })
}

//测试是否符合要求
function testUser() {
    var reg = /^[a-zA-Z][a-zA-Z0-9]{5,17}/;
    var arr = [];
    $('input:checkbox:checked').each(function () {
        arr.push($(this).val());
    });
    if(!reg.test($('#account').val())){
        alert("账户不符合规则，请输入6-18位字母或数字组成的账户，首位必须是字母");
        return false;
    }
    if($('#password').val()==""){
        alert("密码不能为空");
        return false;
    }
    if(arr.length==0){
        alert("请至少选择一项用户权限");
        return false;
    }
    return saveUser();
}

//保存用户
function saveUser() {
    var arr = [];
    $('input:checkbox:checked').each(function () {
        arr.push($(this).val());
    });
    //alert(arr.join());
    var arrs = arr.join();
    Ajax.post(
        '/addUser',
        {
            account: $('#account').val(),
            password: $('#password').val(),
            name:$('#name').val(),
            createDate:$('#createDate').val(),
            status:$('input:radio:checked').val(),
            userModel:arrs
        },
        function (result) {
            doResult(result, function () {
                layer.closeAll();
                alert(result.msg);
                show('userList.html');
                //layer.alert(result.msg)
            })
        }
    )
}

//查看权限
function queryModel(obj) {
    Ajax.getHtml('/queryModel.html', function (html) {
        $(function () {
            var a = obj.parentNode.parentNode.cells[1].innerText;
            $('#account').attr("value",a);
            $.post("/scm/queryUserModel",{account:a},function (data) {
                $('#model').attr("value",data);
            })
        });
        layer.open({
            type: 1,
            title: '用户权限',
            area: ['700px'],
            content: html,
            btn: ['确认', '取消'],
            yes: function () {
                layer.closeAll();
            },
            btn2: function () {
                layer.closeAll();
            }
        })
    })
}

//修改信息
function updateUser(obj) {
    Ajax.getHtml('/userUpdate.html', function (html) {
        $(function () {
            // var a = obj.parentNode.parentNode.cells[0].innerText;
            // $('#no').attr("value",a);
            // var b = obj.parentNode.parentNode.cells[1].innerText;
            // $('#account').attr("value",b);
            // var c = obj.parentNode.parentNode.cells[2].innerText;
            // $('#password').attr("value",c);
            // var d = obj.parentNode.parentNode.cells[3].innerText;
            // $('#name').attr("value",d);
            // var e = obj.parentNode.parentNode.cells[4].innerText;
            // $('#createDate').attr("value",e);
            // var f = obj.parentNode.parentNode.cells[5].innerText;
            // if(f==0){
            //     $('#b').prop("checked","checked");
            // }else{
            //     $('#a').attr("checked","checked");
            // }
            $('#account').attr('value', obj.account);
            $('#name').attr('value', obj.name);
            $('#password').attr('value', obj.password);
            $('#createDate').attr('value', obj.createDate);
            if (obj.status == '1'){
                $('#yesBox').attr("checked", "checked")
            }else {
                $('#noBox').attr("checked", "checked")
            }

            Ajax.post("/findModelName",{account:obj.account},function (list) {
                // let list = new Array();
                // list.push(data);
                //alert(data.valueOf());
                if(list.indexOf("admin")>-1){
                    $('#admin').attr("checked","checked");
                }
                if(list.indexOf("purchase")>-1){
                    $('#purchase').attr("checked","checked");
                }
                if(list.indexOf("sell")>-1){
                    $('#sell').attr("checked","checked");
                }
                if(list.indexOf("stock")>-1){
                    $('#stock').attr("checked","checked");
                }
                if(list.indexOf("finance")>-1){
                    $('#finance').attr("checked","checked");
                }
            })


        });

        layer.open({
            type: 1,
            title: '用户信息',
            area: ['700px'],
            content: html,
            btn: ['确认', '取消'],
            yes: function () {
                testUserUpdate();
            },
            btn2: function () {
                layer.closeAll();
            }
        })
    })
}
function testUserUpdate() {
    var arr = [];
    $('input:checkbox:checked').each(function () {
        arr.push($(this).val());
    });
    if($('#password').val()==""){
        alert("密码不能为空");
        return false;
    }
    if(arr.length==0){
        alert("请至少选择一项用户权限");
        return false;
    }
    return updateUser2();
}

//更新用户信息
function updateUser2(){
    var arr = [];
    $('input:checkbox:checked').each(function () {
        arr.push($(this).val());
    });
    var arrs = arr.join();
    Ajax.post(
        '/updateUser',
        {
            account: $('#account').val(),
            password: $('#password').val(),
            name:$('#name').val(),
            createDate:$('#createDate').val(),
            status:$('input:radio:checked').val(),
            userModel:arrs
        },
        function (result) {
            doResult(result, function () {
                layer.closeAll();
                alert(result.msg);
                show('userList.html');
            })
        }
    )
}

//删除用户
function deleteUser(obj) {
    function delUser(){
        //var a = obj.parentNode.parentNode.cells[1].innerText;
        Ajax.post("/removeUser",{account:obj},function (result) {
            doResult(result,function () {
                layer.closeAll();
                alert(result.msg);
                show('userList.html');
            })
        })
    }
    Ajax.getHtml('/blank.html', function (html) {

        layer.open({
            type: 1,
            title: '确认删除',
            area: ['700px'],
            content: html,
            btn: ['确认', '取消'],
            yes: function () {
                delUser();
            },
            btn2: function () {
                layer.closeAll();
            }
        })
    })
}

