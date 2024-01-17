    // JavaScript Document
    var index =null;
    //点击新增常用联系人中的删除
    function deleterow (x){
        var del_box =  document.getElementById('del_box');
        del_box.style.display='block';
        index= x.parentNode.parentNode.id;
        return index;
    }



    //javascript 点击新增常用联系人中的修改
    function modify ( ){
        var tourist =  document.getElementById('tourist');
        tourist.style.display='block';
    }


    //点击新增常用联系人中的取消删除
    function  cancelDelete(){
        var del_box =  document.getElementById('del_box');
        del_box.style.display='none';

    }



    //点击新增常用联系人中的确认删除
    function  confirmDelete(){
        //获得表格对象
        var tb=document.getElementById('tableInfor');
        //根据id获得将要删除行的对象
        var tr =  document.getElementById(index);
        //取出行的索引，设置删除行的索引
        tb.deleteRow(tr.rowIndex);
        cancelDelete();
    }




