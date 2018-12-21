$(function(){

    $("#insertHeader").load("../../fragmentos/menu-navegacao.html");
    

    $(document).off("click", ".radioProduto").on("click", ".radioProduto", function(){
        $("#delete").removeAttr("disabled");
        $("#delete").attr("href", $("#delete").attr("base-view-url") + $(this).val());
        var line = $(this).parents("tr");
        
        var produto = {};
        produto.id = parseInt($(this).val());
        produto.descricao = line.children("td:eq(2)").text();
        produto.preco = line.children("td:eq(3)").attr("value");
        
        $("#idProduto").val(produto.id);
        $("#descricao").val(produto.descricao);
        $("#preco").val(produto.preco);
    });
});