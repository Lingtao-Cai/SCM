function stockPoMain(obj) {
    Ajax.getHtml('/blank.html', function (html) {

        layer.open({
            type: 1,
            title: '确认入库',
            area: ['700px'],
            content: html,
            btn: ['确认', '取消'],
            yes: function () {
                let date = new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + new Date().getDate();
                Ajax.post('/stockPoMain', {
                    poId: obj.parentNode.parentNode.cells[1].innerText,
                    stockUser: obj.parentNode.parentNode.cells[4].innerText, stockTime: date
                }, function (result) {
                    layer.closeAll()
                    alert(result.msg);
                    show("stockList.html");
                })
            },
            btn2: function () {
                layer.closeAll();
            }
        })
    })
}

function findStockPoMain(obj) {
    $('#stockPoTab').show();
    $('#stockDetails').hide();
    showPage({
        currentPage: 1,
        pageSize: 2,
        url: '/findStockPoByPayType',
        templateId: 'poStockListTemplate',
        contentId: 'poStockTableBody',
        params:{
            payType: obj.innerText,
            account: $('#accountIn').text()
        }
    })

}

function findStockOut(obj) {
    $('#stockOutTab').show();
    $('#stockOutDetails').hide();
    showPage({
        currentPage: 1,
        pageSize: 2,
        url: '/findStockOutByPayType',
        templateId: 'soStockOutListTemplate',
        contentId: 'soStockOutTableBody',
        params:{
            payType: obj.innerText,
            account: $('#accountIn').text()
        }
    })

}

function stockSoMain(obj) {
    Ajax.getHtml('/blank.html', function (html) {

        layer.open({
            type: 1,
            title: '确认出库',
            area: ['700px'],
            content: html,
            btn: ['确认', '取消'],
            yes: function () {
                let date = new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + new Date().getDate();
                Ajax.post('/stockSoMain', {
                    soId: obj.parentNode.parentNode.cells[1].innerText,
                    stockUser: obj.parentNode.parentNode.cells[4].innerText, stockTime: date
                }, function (result) {
                    layer.closeAll()
                    alert(result.msg);
                    show("stockOutList.html");
                })
            },
            btn2: function () {
                layer.closeAll();
            }
        })
    })
}

function showStockRecords(obj){
    let productCode = obj.innerText;
    $('#stockDetails').show();
    $('#stockInfo').hide();
    $('#page').hide();
    Ajax.get('/findStockRecords', {productCode:productCode}, function (result){
        doResult(result, function () {
            var str = template('stockDetailsTemplate', result);
            document.getElementById('stockDetailsTableBody').innerHTML = str;
        })
    })
}

function searchStockProduct(){
    var reg = /^[a-zA-Z]+$/
    if ($('#stockMax').val() == ''){
        $('#stockMax').val('99999999');
    }
    if ($('#stockMin').val() == ''){
        $('#stockMin').val('0');
    }
    if (reg.test($('#stockMax').val()) || reg.test($('#stockMin').val())){
        alert("最大最小值必须为数字");
        return false;
    }
    showPage({
        currentPage: 1,
        pageSize: 2,
        url: '/searchStockProduct',
        templateId: 'stockInfoTemplate',
        contentId: 'stockInfoTableBody',
        params:{productCode: $('#productCodeStock').val(),
            productName: $('#productNameStock').val(), numMin: $('#stockMin').val(), numMax: $('#stockMax').val()}
    })

}

function changeCount(obj){
    let productCode = obj.parentNode.parentNode.cells[1].innerText;

    // show('changeCount.html');
    Ajax.getHtml('/changeCount.html', function (html) {
        layer.open({
            type: 1,
            title: '新增用户',
            area: ['700px'],
            content: html,
            btn: ['确认', '取消'],
            yes: function () {
                saveCount(productCode);
                layer.closeAll();
            },
            btn2: function () {
                console.log('')
            }
        })
    })
}

function saveCount(productCode){
    let date = new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + new Date().getDate();
    Ajax.post('/saveCount', {productCode: productCode, createUser: $('#accountIn').text(),
        description: $('#changeReason').val(), stockTime: date, changeNum: $('#changeNum').val(),type: $('#changeCountType').val()},function (result){
        alert(result.msg);
        show('checkStock.html');
    })
}


function searchStockInByMonth(){
    let dateRange=$('#stockInTime').val();
    let startDate = dateRange + "-1";
    let endDate = dateRange + "-31";
    showPage({
        currentPage: 1,
        pageSize: 4,
        url: '/searchStockInByMonth',
        templateId: 'poReportItemsListTemplate',
        contentId: 'poReportItemsTableBody',
        params:{
            startDate:startDate,
            endDate:endDate
        }
    })
}

function showStockInDetails(obj) {
    let p = obj.innerText;
    Ajax.getHtml('/poInfo.html', function (html){
        showPage({
            currentPage: 1,
            pageSize: 2,
            url: '/items',
            templateId: 'poInfoListTemplate',
            contentId: 'poInfoListTbody',
            params: {
                poId: p
            }
        })
        Ajax.post('/items', {poId:p}, function (result){
            $('#poInfoId').attr('value',result.data.poId);
            $('#poInfoTime').attr('value',result.data.createTime);
            $('#poInfoVender').text(result.data.venderName);
            $('#poInfoAccount').attr('value',result.data.account);
            $('#poInfoTipFee').attr('value',result.data.tipFee);
            $('#poInfoProductTotal').attr('value',result.data.productTotal);
            $('#poPayTypeDefaultInfo').text(result.data.payType);
            $('#poInfoPrePayFee').attr('value',result.data.prePayFee);
            $('#poInfoRemark').attr('value',result.data.remark);
        })

        layer.open({
            type: 1,
            title: '详细信息',
            area: ['1200px'],
            height:['300px'],
            content: html,
            btn: ['确认', '取消'],
            yes: function () {
                //saveCount(productCode);
                layer.closeAll();
            },
            btn2: function () {
                console.log('')
            }
        })

    })
}

function searchStockOutByMonth(){
    let dateRange=$('#stockOutTime').val();
    let startDate = dateRange + "-1";
    let endDate = dateRange + "-31";
    showPage({
        currentPage: 1,
        pageSize: 4,
        url: '/searchStockOutByMonth',
        templateId: 'soReportItemsListTemplate',
        contentId: 'soReportItemsTableBody',
        params:{
            startDate:startDate,
            endDate:endDate
        }
    })
}

function showStockOutDetails(obj) {
    let s = obj.innerText;
    Ajax.getHtml('/soInfo.html', function (html){
        showPage({
            currentPage: 1,
            pageSize: 2,
            url: '/soItems',
            templateId: 'soInfoListTemplate',
            contentId: 'soInfoListTbody',
            params: {
                soId: s
            }
        })
        Ajax.post('/soItems', {soId:s}, function (result){
            $('#soInfoId').attr('value',result.data.soId);
            $('#soInfoTime').attr('value',result.data.createTime);
            $('#soInfoVender').text(result.data.customerName);
            $('#soInfoAccount').attr('value',result.data.account);
            $('#soInfoTipFee').attr('value',result.data.tipFee);
            $('#soInfoProductTotal').attr('value',result.data.productTotal);
            $('#soPayTypeDefaultInfo').text(result.data.payType);
            $('#soInfoPrePayFee').attr('value',result.data.prePayFee);
            $('#soInfoRemark').attr('value',result.data.remark);
        })

        layer.open({
            type: 1,
            title: '详细信息',
            area: ['1200px'],
            height:['300px'],
            content: html,
            btn: ['确认', '取消'],
            yes: function () {
                //saveCount(productCode);
                layer.closeAll();
            },
            btn2: function () {
                console.log('')
            }
        })

    })
}

