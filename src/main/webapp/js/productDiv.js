var rowIndex;

function addItem() {
    var tr = $('<tr></tr>');
    $('#itemList111').append(tr);
    var index = parseInt(tr.index())+1;
    tr.append('<td>' + index + '</td>'
        + '<td><input type="text" name="productCode" class="poinput" readonly /> <img src="../demo/images/leftico03.png" class="point" onclick="choiceSpxx(this)" /></td>'
        + '<td><input type="text" name="name" class="poinput" readonly /></td>'
        + '<td><input type="text" name="" class="poinput" readonly /></td>'
        + '<td><input type="text" class="poinput" /></td>'
        + '<td><input type="text" class="poinput" value="0" onchange="updateItemPrice(this)" /></td>'
        + '<td><input type="text" class="poinput" value="0"/></td>'
        + '<td><img src="../demo/images/delete.gif" class="point" onclick="delItem(this)"/></td>'
    );
}

//删除行,注意这里的行号全部要重新计算 刷新的
function delItem(delImg) {
    var tr = $(delImg).parent().parent();
    var index = tr.index() - 1;// 获得删除行的前一行的索引
    tr.remove();
    // 修改删除的行后面行的序号
    $('#itemList111 tr:gt(' + index + ')').each(function () {
        $(this).find('td:first').text($(this).index());
    });
}

// 选择产品
function choiceSpxx(img) {
    rowIndex = $(img).parents('tr').index();// 选择行的索引
    showDiv();
    $('#spxxTable [name=choice]').prop('checked', false);
}

function hiddenDiv() {
    $('#poDiv').show();
    $('#productDiv').hide();

}

function showDiv() {
    $('#poDiv').hide();
    $('#productDiv').show();
}


function sure() {
    var check = $('#spxxTable [name=choice]:checked');
    let flag = true;
    $('#itemList111').find('tr').each(function (){
        var allInputs = $(this).find('input')
        if (allInputs[0].value == check.parents('tr').find('td:eq(1)').text()){
            alert("该产品已存在明细单");
            flag = false;
            return false;
        }
    })

    if (check.length == 0) {
        alert('请先选择产品');
        return false;
    } else if (flag){
        var tr = check.parents('tr');
        tr.find('td:gt(0)').each(function (i) {
            $('#itemList111 tr:eq(' + rowIndex + ')').find('td:eq(' + (i + 1) + ') :text').val($(this).text());
        });
        hiddenDiv();
    }
}

function savePo(){
    Ajax.getHtml('/blank.html', function (html) {

        layer.open({
            type: 1,
            title: '确认修改',
            area: ['700px'],
            content: html,
            btn: ['确认', '取消'],
            yes: function () {
                testPoMain();
                layer.closeAll();
            },
            btn2: function () {
                layer.closeAll();
            }
        })
    })
}

function savePoMain() {
    var sum = parseFloat($('#poProductTotal').val()) + parseFloat($('#poTipFee').val());
    var poMain = {
        "poId": $('#poId').val(),
        "venderCode": $('#selVen').val(),
        "account": $('#poAccount').val(),
        "createTime": $('#poTime').val(),
        "tipFee": $('#poTipFee').val(),
        "productTotal": $('#poProductTotal').val(),
        "poTotal": sum.valueOf(),
        "payType": $('#payType').find("option:selected").text(),
        "prePayFee": $('#poPrePayFee').val(),
        "remark": $('#poRemark').val(),
        "poItems": []
    }


    $('#itemList111').find('tr').each(function () {
        var allInputs = $(this).find('input')
        poMain.poItems.push({
            "poId":$('#poId').val(),
            "productCode":allInputs[0].value,
            "unitPrice":allInputs[2].value,
            "num":allInputs[4].value,
            "unitName":allInputs[3].value,
            "itemPrice":allInputs[5].value,
        })

    })

    $.ajax({
    	url: "updatePoMain",
    	type: "post",
    	data: JSON.stringify(poMain),
    	contentType : "application/json;charset=UTF-8",
    	datatype: "json",
    	success : function (){
    		alert("成功")
            show('poList.html')
    	},
    	// 自定义请求头
    	headers: {'Authorization': window.sessionStorage.getItem('token')}
    })


}

function testPoMain(){
    if($('#poId').val()==""){
        alert("编号不能为空");
        return false;
    }
    if($('#selVen').val()==""){
        alert("供应商不能为空");
        return false;
    }
    if($('#poAccount').val()==""){
        alert("创建用户不能为空");
        return false;
    }
    if($('#poTime').val()==""){
        alert("创建时间不能为空");
        return false;
    }
    if($('#poTipFee').val()==""){
        alert("附加费用不能为空");
        return false;
    }
    if($('#poProductTotal').val()==""){
        alert("产品总价不能为空");
        return false;
    }
    if($('#payType').find("option:selected").text()==""){
        alert("付款方式不能为空");
        return false;
    }
    if($('#poPrePayFee').val()==""){
        alert("最低预付款不能为空");
        return false;
    }
    return savePoMain();

}

function updateItemPrice(obj) {
    let num = obj.value;
    let unitPrice = $(obj).parent().parent().find("input").eq(2).val();
    let sum = num * unitPrice;
    $(obj).parent().parent().find("input").eq(5).attr("value", sum);
    let productTotal = 0;
    $('#itemList111').find('tr').each(function (){
        var allInputs = $(this).find('input');
        productTotal+=parseFloat(allInputs[5].value);
    })
    $('#poProductTotal').attr("value", productTotal);
    // $('#sumPrice').attr("value", sum);
}

function setDefault(){
    if ($('#payType').find("option:selected").text() == "货到付款"){
        $('#defaultType').attr('value', '1');
    }
    if ($('#payType').find("option:selected").text() == "货到付款"){
        $('#defaultType').attr('value', '2');
    }
    if ($('#payType').find("option:selected").text() == "货到付款"){
        $('#defaultType').attr('value', '3');
    }
}

function changeFee() {
    if ($('#payType').val() == "3") {
        $('#poPrePayFee').removeAttr("readonly");
    }
}

function deletePoMain(obj) {
    function delPoMain() {
        var a = obj.parentNode.parentNode.cells[1].innerText;
        Ajax.post("/removePoMain", {poId: a}, function (result) {
            doResult(result, function () {
                layer.closeAll();
                alert(result.msg);
                show('poList.html');
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
                delPoMain();
            },
            btn2: function () {
                layer.closeAll();
            }
        })
    })
}


function findPoMain(obj) {
    showPage({
        currentPage: 1,
        pageSize: 2,
        url: '/findByPayType',
        templateId: 'poEndListTemplate',
        contentId: 'poEndTableBody',
        params: {
            payType: obj.innerText,
            account: $('#accountIn').text()
        }
    })

}

function endPoMain(obj) {
    let type = obj.parentNode.parentNode.cells[8].innerText;
    let status = obj.parentNode.parentNode.cells[10].innerText;
    if (type == "货到付款" && status != '3'){
        alert("货到付款 了结需要先付款")
        return false;
    }
    if (type == "款到发货" && status != '2'){
        alert("款到发货 了结需要先收货")
        return false;
    }
    if (type == "预付款到发货" && status != '3'){
        alert("预付款到发货 了结需要先付款")
        return false;
    }
    let date = new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + new Date().getDate();
    Ajax.post('/endPoMain', {
        poId: obj.parentNode.parentNode.cells[1].innerText,
        endUser: obj.parentNode.parentNode.cells[4].innerText, endTime: date
    }, function (result) {
        alert(result.msg);
        show("endPo.html");
    })
}

function addPo(){
    var sum = parseFloat($('#poProductTotal').val()) + parseFloat($('#poTipFee').val());
    var poMain = {
        "poId": $('#poId').val(),
        "venderCode": $('#selVen').val(),
        "account": $('#poAccount').val(),
        "createTime": $('#poTime').val(),
        "tipFee": $('#poTipFee').val(),
        "productTotal": $('#poProductTotal').val(),
        "poTotal": sum.valueOf(),
        "payType": $('#payType').find("option:selected").text(),
        "prePayFee": $('#poPrePayFee').val(),
        "remark": $('#poRemark').val(),
        "poItems": []
    }


    $('#itemList111').find('tr').each(function () {
        var allInputs = $(this).find('input')
        poMain.poItems.push({
            "poId":$('#poId').val(),
            "productCode":allInputs[0].value,
            "unitPrice":allInputs[2].value,
            "num":allInputs[4].value,
            "unitName":allInputs[3].value,
            "itemPrice":allInputs[5].value,
        })

    })

    $.ajax({
        url: "addPoMain",
        type: "post",
        data: JSON.stringify(poMain),
        contentType : "application/json;charset=UTF-8",
        datatype: "json",
        success : function (result){
            alert(result.msg);
            show('poList.html');
        },
        // 自定义请求头
        headers: {'Authorization': window.sessionStorage.getItem('token')}
    })
}

function querySearch() {
    let dateRange=$('#timeRange').val();
    let arr = dateRange.split(" ~ ");
    showPage({
        currentPage: 1,
        pageSize: 2,
        url: '/queryPoMain',
        templateId: 'queryPoListTemplate',
        contentId: 'queryPoTableBody',
        params:{
            startDate:arr[0],
            endDate:arr[1],
            poId:$('#queryPoId').val(),
            status:$('#querySelectBox').val()
        }
    })
}





