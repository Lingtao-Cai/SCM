function showOutcomerPoMain(obj){
    showPage({
        currentPage: 1,
        pageSize: 2,
        url: '/findOutcomeSoMain',
        templateId: 'outcomeListTemplate',
        contentId: 'outcomeTableBody',
        params:{
            payType: obj.innerText,
            account: $('#accountIn').text()
        }
    })
}

function outcomeMoney(obj){
    let date = new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + new Date().getDate();
    Ajax.post('/payMoney', {
        poId: obj.parentNode.parentNode.cells[1].innerText,
        payUser: obj.parentNode.parentNode.cells[4].innerText,
        payTime: date,
        status: obj.parentNode.parentNode.cells[10].innerText,
        payType:obj.parentNode.parentNode.cells[8].innerText
    }, function (result) {
        alert(result.msg);
        show("outcome.html");
    })
}

function searchPoByMonth() {
    let dateRange=$('#poTime').val();
    let startDate = dateRange + "-1";
    let endDate = dateRange + "-31";
    Ajax.post('/reportPoMainSum', {startDate:startDate, endDate:endDate}, function (result){
        $('#poNumTotal').text(result.data.totalPoMainNum);
        $('#poEndNum').text(result.data.endPoMainNum);
        $('#poTotalPrice').text(result.data.poMainTotalPrice);
        $('#poPaidTotal').text(result.data.paidFee);
    })
    showPage({
        currentPage: 1,
        pageSize: 2,
        url: '/reportPoMainDetail',
        templateId: 'poReportItemsListTemplate',
        contentId: 'poReportItemsTableBody',
        params:{
            startDate:startDate,
            endDate:endDate
        }
    })
}