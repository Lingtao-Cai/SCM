var rowIndex;

function addSoItem() {
    var tr = $('<tr></tr>');
    $('#itemList222').append(tr);
    var index = parseInt(tr.index())+1;
    tr.append('<td>' + index + '</td>'
        + '<td><input type="text" name="productCode" class="soinput" readonly /> <img src="../demo/images/leftico03.png" class="soint" onclick="choiceSoSpxx(this)" /></td>'
        + '<td><input type="text" name="name" class="soinput" readonly /></td>'
        + '<td><input type="text" name="" class="soinput" readonly /></td>'
        + '<td><input type="text" class="soinput" /></td>'
        + '<td><input type="text" class="soinput" value="0" onchange="updateSoItemPrice(this)" /></td>'
        + '<td><input type="text" class="soinput" value="0" readonly/></td>'
        + '<td><img src="../demo/images/delete.gif" class="soint" onclick="delItem(this)"/></td>'
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
function choiceSoSpxx(img) {
    rowIndex = $(img).parents('tr').index();// 选择行的索引
    showSoDiv();
    $('#spxxTable [name=choice]').prop('checked', false);
}

function hiddenSoDiv() {
    $('#soDiv').show();
    $('#soProductDiv').hide();

}

function showSoDiv() {
    $('#soDiv').hide();
    $('#soProductDiv').show();
}



function sureSo() {
    var check = $('#spxxTable [name=choice]:checked');
    let flag = true;
    $('#itemList222').find('tr').each(function (){
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
            $('#itemList222 tr:eq(' + rowIndex + ')').find('td:eq(' + (i + 1) + ') :text').val($(this).text());
        });
        hiddenSoDiv();
    }
}

function saveSo(){
    Ajax.getHtml('/blank.html', function (html) {

        layer.open({
            type: 1,
            title: '确认修改',
            area: ['700px'],
            content: html,
            btn: ['确认', '取消'],
            yes: function () {
                testSoMain();
                layer.closeAll();
            },
            btn2: function () {
                layer.closeAll();
            }
        })
    })
}

function saveSoMain() {
    var sum = parseFloat($('#soProductTotal').val()) + parseFloat($('#soTipFee').val());
    var soMain = {
        "soId": $('#soId').val(),
        "customerCode": $('#soSelCus').val(),
        "account": $('#soAccount').val(),
        "createTime": $('#soTime').val(),
        "tipFee": $('#soTipFee').val(),
        "productTotal": $('#soProductTotal').val(),
        "soTotal": sum.valueOf(),
        "payType": $('#soPayType').find("option:selected").text(),
        "prePayFee": $('#soPrePayFee').val(),
        "remark": $('#soRemark').val(),
        "soItems": []
    }


    $('#itemList222').find('tr').each(function () {
        var allInputs = $(this).find('input')
        soMain.soItems.push({
            "soId":$('#soId').val(),
            "productCode":allInputs[0].value,
            "unitPrice":allInputs[2].value,
            "num":allInputs[4].value,
            "unitName":allInputs[3].value,
            "itemPrice":allInputs[5].value,
        })

    })

    $.ajax({
        url: "updateSoMain",
        type: "post",
        data: JSON.stringify(soMain),
        contentType : "application/json;charset=UTF-8",
        datatype: "json",
        success : function (){
            alert("成功")
            show('soList.html')
        },
        // 自定义请求头
        headers: {'Authorization': window.sessionStorage.getItem('token')}
    })


}

function testSoMain(){
    if($('#soId').val()==""){
        alert("编号不能为空");
        return false;
    }
    if($('#soSelCus').val()==""){
        alert("供应商不能为空");
        return false;
    }
    if($('#soAccount').val()==""){
        alert("创建用户不能为空");
        return false;
    }
    if($('#soTime').val()==""){
        alert("创建时间不能为空");
        return false;
    }
    if($('#soTipFee').val()==""){
        alert("附加费用不能为空");
        return false;
    }
    if($('#soProductTotal').val()==""){
        alert("产品总价不能为空");
        return false;
    }
    if($('#soPayType').find("option:selected").text()==""){
        alert("付款方式不能为空");
        return false;
    }
    if($('#soPrePayFee').val()==""){
        alert("最低预付款不能为空");
        return false;
    }
    return saveSoMain();

}

function updateSoItemPrice(obj) {
    let num = obj.value;
    let unitPrice = $(obj).parent().parent().find("input").eq(2).val();
    let sum = num * unitPrice;
    $(obj).parent().parent().find("input").eq(5).attr("value", sum);
    let productTotal = 0;
    $('#itemList222').find('tr').each(function (){
        var allInputs = $(this).find('input');
        productTotal+=parseFloat(allInputs[5].value);
    })
    $('#soProductTotal').attr("value", productTotal);
    // $('#sumPrice').attr("value", sum);
}

function setSoDefault(){
    if ($('#soPayType').find("option:selected").text() == "货到付款"){
        $('#defaultSoType').attr('value', '1');
    }
    if ($('#soPayType').find("option:selected").text() == "货到付款"){
        $('#defaultSoType').attr('value', '2');
    }
    if ($('#soPayType').find("option:selected").text() == "货到付款"){
        $('#defaultSoType').attr('value', '3');
    }
}

function changeSoFee() {
    if ($('#soPayType').val() == "3") {
        $('#soPrePayFee').removeAttr("readonly");
    }
}

function deleteSoMain(obj) {
    function delSoMain() {
        var a = obj.parentNode.parentNode.cells[1].innerText;
        Ajax.post("/removeSoMain", {soId: a}, function (result) {
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
                delSoMain();
            },
            btn2: function () {
                layer.closeAll();
            }
        })
    })
}


function findSoMain(obj) {
    showPage({
        currentPage: 1,
        pageSize: 2,
        url: '/findBySoPayType',
        templateId: 'soEndListTemplate',
        contentId: 'soEndTableBody',
        params: {
            payType: obj.innerText,
            account: $('#accountIn').text()
        }
    })

}

function endSoMain(obj) {
    let type = obj.parentNode.parentNode.cells[8].innerText;
    let status = obj.parentNode.parentNode.cells[10].innerText;
    if (type == "货到付款" && status != '3'){
        alert("货到付款 了结需要先付款")
        return false;
    }
    if (type == "款到发货" && status != '2'){
        alert("款到发货 了结需要先发货")
        return false;
    }
    if (type == "预付款到发货" && status != '3'){
        alert("预付款到发货 了结需要先付款")
        return false;
    }
    let date = new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + new Date().getDate();
    Ajax.post('/endSoMain', {
        soId: obj.parentNode.parentNode.cells[1].innerText,
        endUser: obj.parentNode.parentNode.cells[4].innerText,
        endTime: date
    }, function (result) {
        alert(result.msg);
        show("endSo.html");
    })
}

function addSo(){
    var sum = parseFloat($('#soProductTotal').val()) + parseFloat($('#soTipFee').val());
    var soMain = {
        "soId": $('#soId').val(),
        "customerCode": $('#soSelCus').val(),
        "account": $('#soAccount').val(),
        "createTime": $('#soTime').val(),
        "tipFee": $('#soTipFee').val(),
        "productTotal": $('#soProductTotal').val(),
        "soTotal": sum.valueOf(),
        "payType": $('#soPayType').find("option:selected").text(),
        "prePayFee": $('#soPrePayFee').val(),
        "remark": $('#soRemark').val(),
        "soItems": []
    }


    $('#itemList222').find('tr').each(function () {
        var allInputs = $(this).find('input')
        soMain.soItems.push({
            "soId":$('#soId').val(),
            "productCode":allInputs[0].value,
            "unitPrice":allInputs[2].value,
            "num":allInputs[4].value,
            "unitName":allInputs[3].value,
            "itemPrice":allInputs[5].value,
        })

    })

    $.ajax({
        url: "addSoMain",
        type: "post",
        data: JSON.stringify(soMain),
        contentType : "application/json;charset=UTF-8",
        datatype: "json",
        success : function (result){
            alert(result.msg);
            show('soList.html');
        },
        // 自定义请求头
        headers: {'Authorization': window.sessionStorage.getItem('token')}
    })
}

function showSoDetails(obj) {
    var p = obj.innerText;
    $(function () {
        Ajax.post('/findAllCustomer', {}, function (result) {
            $(result.data).each(function () {
                $('#soSelCus').append("<option value="+this.customerCode+">" + this.name + "</option>");
            })
        })

        Ajax.post('/findAllProduct', {}, function (result) {
            $(result.data).each(function () {
                var tr = $('<tr></tr>');
                $('#spxxTable').append(tr);
                tr.append(
                    '<td><input type="radio" name="choice"/></td>'
                    + '<td>' + this.productCode + '</td>'
                    + '<td>' + this.name + '</td>'
                    + '<td>' + this.price + '</td>'
                    + '<td>' + this.unitName + '</td>'
                )
            })
        })
    })
    $('#soup').hide();
    hiddenSoDiv();
    showPage({
        currentPage: 1,
        pageSize: 2,
        url: '/soItems',
        templateId: 'soItemsListTemplate',
        contentId: 'soItemsTableBody',
        params: {
            soId: p
        }
    })

}

function querySoSearch() {
    let dateRange=$('#timeRange').val();
    let arr = dateRange.split(" ~ ");
    showPage({
        currentPage: 1,
        pageSize: 2,
        url: '/querySoMain',
        templateId: 'querySoListTemplate',
        contentId: 'querySoTableBody',
        params:{
            startDate:arr[0],
            endDate:arr[1],
            soId:$('#querySoId').val(),
            status:$('#querySelectBox').val()
        }
    })
}

function incomeMoney(obj){
    let date = new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + new Date().getDate();
    Ajax.post('/receiveMoney', {
        soId: obj.parentNode.parentNode.cells[1].innerText,
        payUser: obj.parentNode.parentNode.cells[4].innerText,
        payTime: date,
        status: obj.parentNode.parentNode.cells[10].innerText,
        payType:obj.parentNode.parentNode.cells[8].innerText
    }, function (result) {
        alert(result.msg);
        show("income.html");
    })
}

function showIncomeSoMain(obj){
    showPage({
        currentPage: 1,
        pageSize: 2,
        url: '/findIncomeSoMain',
        templateId: 'incomeListTemplate',
        contentId: 'incomeTableBody',
        params:{
            payType: obj.innerText,
            account: $('#accountIn').text()
        }
    })
}


function searchSoByMonth() {
    let dateRange=$('#soTime').val();
    let startDate = dateRange + "-1";
    let endDate = dateRange + "-31";
    Ajax.post('/reportSoMain', {startDate:startDate, endDate:endDate}, function (result){
        $('#soNumTotal').text(result.data.totalSoMainNum);
        $('#soEndNum').text(result.data.endSoMainNum);
        $('#soTotalPrice').text(result.data.soMainTotalPrice);
        $('#soPaidTotal').text(result.data.paidFee);
    })
    showPage({
        currentPage: 1,
        pageSize: 2,
        url: '/reportSoMain',
        templateId: 'soReportItemsListTemplate',
        contentId: 'soReportItemsTableBody',
        params:{
            startDate:startDate,
            endDate:endDate
        }
    })
}





