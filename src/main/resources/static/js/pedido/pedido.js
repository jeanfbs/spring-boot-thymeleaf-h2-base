$(function(){
    
    $("#idProduto").change(function(){
        var opt = {};
        $( "#idProduto option:selected" ).each(function() {
            opt.id = parseInt($( this ).val());
            opt.preco = parseFloat($( this ).attr("preco"));
        });
        $("#preco").val(opt.preco);
    });


    $("#limpar").on("click", function(){
        $("#idProduto").prop('selectedIndex',0);
        $("#preco").val("");
        $("#quantidade").val(1);
    });
});