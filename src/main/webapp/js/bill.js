function searchBillReportByMonth(){
    let dateRange=$('#billReportTime').val();
    let startDate = dateRange + "-1";
    let endDate = dateRange + "-31";
    showPage({
        currentPage: 1,
        pageSize: 4,
        url: '/searchPayRecordByMonth',
        templateId: 'billListTemplate',
        contentId: 'billTableBody',
        params:{
            startDate:startDate,
            endDate:endDate
        }
    })
}

function showBillReportDetails(obj){
    let code = obj.parentNode.parentNode.cells[6].innerText;
    if (code == '3' || code == '1'){
        showStockOutDetails(obj);
    }else {
        showStockInDetails(obj);
    }
}