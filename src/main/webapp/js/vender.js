function addVender() {
    Ajax.getHtml('/venderAdd.html', function (html) {
        $(function (){
            let date = new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + new Date().getDate();
            $('#venderCreateDate').attr("value", date);
        })
        layer.open({
            type: 1,
            title: '新增供应商',
            area: ['700px'],
            content: html,
            btn: ['确认', '取消'],
            yes: function () {
                testVenderAdd();
            },
            btn2: function () {
                console.log('')
            }
        })
    })
}

function saveVender() {
    Ajax.post(
        '/addVender',
        {
            venderCode: $('#venderCode').val(),
            name: $('#venderName').val(),
            password:$('#venderPassword').val(),
            contactor:$('#venderContactor').val(),
            address:$('#venderAddress').val(),
            postcode:$('#venderPostcode').val(),
            tel:$('#venderTel').val(),
            fax:$('#venderFax').val(),
            createDate:$('#venderCreateDate').val()
        },
        function (result) {
            doResult(result, function () {
                layer.closeAll();
                alert(result.msg);
                show('vender.html');
                //layer.alert(result.msg)
            })
        }
    )
}
//测试是否符合要求
function testVenderAdd() {
    if($('#categoryId').val()==""){
        alert("产品分类ID不能为空");
        return false;
    }
    if($('#categoryName').val()==""){
        alert("产品分类名称不能为空");
        return false;
    }
    return saveVender();
}


//删除产品
function deleteVender(obj) {
    function delVender(){
        var a = obj.parentNode.parentNode.cells[1].innerText;
        Ajax.post("/removeVender",{venderCode:a},function (result) {
            doResult(result,function () {
                layer.closeAll();
                alert(result.msg);
                show('vender.html');
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
                delVender();
            },
            btn2: function () {
                layer.closeAll();
            }
        })
    })
}

function updateVender(obj) {
    Ajax.getHtml('/venderUpdate.html', function (html) {
        // $(function (){
        //     let date = new Date().getFullYear() + '' + (new Date().getMonth() + 1) + '' + new Date().getDate() + '' + new Date().getSeconds();
        //     $('#categoryId').attr("value", date);
        // })
        $(function () {
            Ajax.post('/findVenderByCode', {venderCode: obj.parentNode.parentNode.cells[1].innerText}, function (result){
                var vender=result.data
                $('#venderCode').attr("value",vender.venderCode);
                $('#venderName').attr("value", vender.name);
                $('#venderPassword').attr("value",vender.password);
                $('#venderContactor').attr("value",vender.contactor);
                $('#venderAddress').attr("value",vender.address);
                $('#venderPostcode').attr("value",vender.postcode);
                $('#venderCreateDate').attr("value",vender.createDate);
                $('#venderTel').attr("value",vender.tel);
                $('#venderFax').attr("value",vender.fax);
            })
        });


        layer.open({
            type: 1,
            title: '确认修改产品',
            area: ['700px'],
            content: html,
            btn: ['确认', '取消'],
            yes: function () {
                testVenderUpdate();
            },
            btn2: function () {
                console.log('')
            }
        })
    })
}

function testVenderUpdate() {
    if($('#venderCode').val()==""){
        alert("供应商编号不能为空");
        return false;
    }
    if($('#venderName').val()==""){
        alert("供应商名称不能为空");
        return false;
    }
    if($('#venderPassword').val()==""){
        alert("密码不能为空");
        return false;
    }
    if($('#venderContactor').val()==""){
        alert("联系人不能为空");
        return false;
    }
    if($('#venderAddress').val()==""){
        alert("地址不能为空");
        return false;
    }
    if($('#venderCreateDate').val()==""){
        alert("创建日期不能为空");
        return false;
    }
    if($('#venderTel').val()==""){
        alert("联系电话不能为空");
        return false;
    }
    return updateVender2();
}

function updateVender2(){
    Ajax.post(
        '/updateVender',
        {
            venderCode: $('#venderCode').val(),
            name: $('#venderName').val(),
            password:$('#venderPassword').val(),
            contactor:$('#venderContactor').val(),
            address:$('#venderAddress').val(),
            postcode:$('#venderPostcode').val(),
            tel:$('#venderTel').val(),
            fax:$('#venderFax').val(),
            createDate:$('#venderCreateDate').val()
        },
        function (result) {
            doResult(result, function () {
                layer.closeAll();
                alert(result.msg);
                show('vender.html');
            })
        }
    )
}

function searchVender(){
    showPage({
        currentPage: 1,
        pageSize: 2,
        url: '/searchVender',
        templateId: 'venderListTemplate',
        contentId: 'venderTableBody',
        params: {
            venderCode: $('#vCodeSearch').val(),
            name: $('#vNameSearch').val(),
            // password:$('#venderPassword').val(),
            contactor:$('#vContactorSearch').val(),
            // address:$('#venderAddress').val(),
            // postcode:$('#venderPostcode').val(),
            tel:$('#vTelSearch').val(),
            // fax:$('#venderFax').val(),
            // createDate:$('#venderCreateDate').val()
        }
    })

    // Ajax.post('findVenderByCode', {venderCode: $('#vCodeSearch')}, function (result){
    //
    // })
}