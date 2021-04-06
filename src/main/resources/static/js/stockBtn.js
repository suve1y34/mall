$(".plus").click(function() {
    var num = $(".stockBox").val();
    var plusNum = Number(num) + 1;
    var stock = $(".stock_hidden");

    if(plusNum >= stock) {
        $(".stockBox").val(num);
    } else {
        $(".stockBox").val(plusNum);
    }
});

$(".minus").click(function() {
    var num = $(".stockBox").val();
    var minusNum = Number(num) - 1;

    if(minusNum <= 0) {
        $(".stockBox").val(num);
    } else {
        $(".stockBox").val(minusNum);
    }
})