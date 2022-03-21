function addCustomer() {
    Ajax.getHtml('/customerAdd.html', function (html) {
        $(function (){
            let date = new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + new Date().getDate();
            $('#customerCreateDate').attr("value", date);
        })
        layer.open({
            type: 1,
            title: '新增供应商',
            area: ['700px'],
            content: html,
            btn: ['确认', '取消'],
            yes: function () {
                testCustomerAdd();
            },
            btn2: function () {
                console.log('')
            }
        })
    })
}

function saveCustomer() {
    Ajax.post(
        '/addCustomer',
        {
            customerCode: $('#customerCode').val(),
            name: $('#customerName').val(),
            password:$('#customerPassword').val(),
            contactor:$('#customerContactor').val(),
            address:$('#customerAddress').val(),
            postcode:$('#customerPostcode').val(),
            tel:$('#customerTel').val(),
            fax:$('#customerFax').val(),
            createDate:$('#customerCreateDate').val()
        },
        function (result) {
            doResult(result, function () {
                layer.closeAll();
                alert(result.msg);
                show('customer.html');
                //layer.alert(result.msg)
            })
        }
    )
}
//测试是否符合要求
function testCustomerAdd() {
    if($('#customerCode').val()==""){
        alert("客户编号不能为空");
        return false;
    }
    if($('#customerName').val()==""){
        alert("客户名称不能为空");
        return false;
    }
    if($('#customerPassword').val()==""){
        alert("密码不能为空");
        return false;
    }
    if($('#customerContactor').val()==""){
        alert("联系人不能为空");
        return false;
    }
    if($('#customerAddress').val()==""){
        alert("地址不能为空");
        return false;
    }
    if($('#customerCreateDate').val()==""){
        alert("创建日期不能为空");
        return false;
    }
    if($('#customerTel').val()==""){
        alert("联系电话不能为空");
        return false;
    }
    return saveCustomer();
}


//删除产品
function deleteCustomer(obj) {
    function delCustomer(){
        var a = obj.parentNode.parentNode.cells[1].innerText;
        Ajax.post("/removeCustomer",{customerCode:a},function (result) {
            doResult(result,function () {
                layer.closeAll();
                alert(result.msg);
                show('customer.html');
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
                delCustomer();
            },
            btn2: function () {
                layer.closeAll();
            }
        })
    })
}

function updateCustomer(obj) {
    Ajax.getHtml('/customerUpdate.html', function (html) {
        // $(function (){
        //     let date = new Date().getFullYear() + '' + (new Date().getMonth() + 1) + '' + new Date().getDate() + '' + new Date().getSeconds();
        //     $('#categoryId').attr("value", date);
        // })
        $(function () {
            Ajax.post('/findCustomerByCode', {customerCode: obj.parentNode.parentNode.cells[1].innerText}, function (result){
                var customer=result.data
                $('#customerCode').attr("value",customer.customerCode);
                $('#customerName').attr("value", customer.name);
                $('#customerPassword').attr("value",customer.password);
                $('#customerContactor').attr("value",customer.contactor);
                $('#customerAddress').attr("value",customer.address);
                $('#customerPostcode').attr("value",customer.postcode);
                $('#customerCreateDate').attr("value",customer.createDate);
                $('#customerTel').attr("value",customer.tel);
                $('#customerFax').attr("value",customer.fax);
            })
        });


        layer.open({
            type: 1,
            title: '确认修改产品',
            area: ['700px'],
            content: html,
            btn: ['确认', '取消'],
            yes: function () {
                testCustomerUpdate();
            },
            btn2: function () {
                console.log('')
            }
        })
    })
}

function testCustomerUpdate() {
    if($('#customerCode').val()==""){
        alert("客户编号不能为空");
        return false;
    }
    if($('#customerName').val()==""){
        alert("客户名称不能为空");
        return false;
    }
    if($('#customerPassword').val()==""){
        alert("密码不能为空");
        return false;
    }
    if($('#customerContactor').val()==""){
        alert("联系人不能为空");
        return false;
    }
    if($('#customerAddress').val()==""){
        alert("地址不能为空");
        return false;
    }
    if($('#customerCreateDate').val()==""){
        alert("创建日期不能为空");
        return false;
    }
    if($('#customerTel').val()==""){
        alert("联系电话不能为空");
        return false;
    }
    return updateCustomer2();
}

function updateCustomer2(){
    Ajax.post(
        '/updateCustomer',
        {
            customerCode: $('#customerCode').val(),
            name: $('#customerName').val(),
            password:$('#customerPassword').val(),
            contactor:$('#customerContactor').val(),
            address:$('#customerAddress').val(),
            postcode:$('#customerPostcode').val(),
            tel:$('#customerTel').val(),
            fax:$('#customerFax').val(),
            createDate:$('#customerCreateDate').val()
        },
        function (result) {
            doResult(result, function () {
                layer.closeAll();
                alert(result.msg);
                show('customer.html');
            })
        }
    )
}

function searchCustomer(){
    showPage({
        currentPage: 1,
        pageSize: 2,
        url: '/searchCustomer',
        templateId: 'customerListTemplate',
        contentId: 'customerTableBody',
        params: {
            customerCode: $('#cCodeSearch').val(),
            name: $('#cNameSearch').val(),
            // password:$('#venderPassword').val(),
            contactor:$('#cContactorSearch').val(),
            // address:$('#venderAddress').val(),
            // postcode:$('#venderPostcode').val(),
            tel:$('#cTelSearch').val(),
            // fax:$('#venderFax').val(),
            // createDate:$('#venderCreateDate').val()
        }
    })

    // Ajax.post('findVenderByCode', {venderCode: $('#vCodeSearch')}, function (result){
    //
    // })
}