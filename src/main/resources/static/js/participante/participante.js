$(function(){

    $("#insertHeader").load("../../fragmentos/menu-navegacao.html");

    $(document).off("click", ".radioParticipante").on("click", ".radioParticipante", function(){
        $("#delete").removeAttr("disabled");
        $("#delete").attr("href", $("#delete").attr("base-view-url") + $(this).val());
        var line = $(this).parents("tr");
        
        var participante = {};
        participante.id = parseInt($(this).val());
        participante.nome = line.children("td:eq(2)").text();
        participante.telefone = line.children("td:eq(3)").text();
        participante.nascimento = line.children("td:eq(4)").text();
        
        $("#idParticipante").val(participante.id);
        $("#nome").val(participante.nome);
        $("#telefone").val(participante.telefone);
        $("#nascimento").val(participante.nascimento);
    });
    
});