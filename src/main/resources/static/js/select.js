function changeAll() {
    if(this.checked){
        $("#list :checkbox").prop("checked", true);
    }else{
        $("#list :checkbox").prop("checked", false);
    }
}

function selectAll() {
    $("#list :checkbox,#all").prop("checked", true);
}

function unSelect() {
    $("#list :checkbox,#all").prop("checked", false);
}

function reverse() {
    $("#list :checkbox").each(function () {
        $(this).prop("checked", !$(this).prop("checked"));
    });
    allchk();
}

function allchk(){
    let var0 = $("#list :checkbox")
    var chknum = $("#list :checkbox").length;//选项总个数
    var chk = 0;
    $("#list :checkbox").each(function () {
        if($(this).prop("checked")===true){
            chk++;
        }
    });
    if(chknum===chk){//全选
        $("#all").prop("checked",true);
    }else{//不全选
        $("#all").prop("checked",false);
    }
}

function getValue() {
    let vals = "";
    $("#list :checkbox").each(function(){
        if($(this).prop("checked")===true){
            vals += $(this).val()+",";
        }
    });
    vals = vals.substring(0, vals.lastIndexOf(','));
    return vals;
}