(function ($) {
    $.fn.hradio = function (options) {
        var self = this;
        debugger;
        return $(':radio+label', this).each(function () {
            $(this).addClass('hRadio');
            if ($(this).prev().is("checked"))
                $(this).addClass('hRadio_Checked');
        }).click(function (event) {
            var clickItem = $(this);
            //����
            
            //$(this).removeClass("hRadio_Checked");
            if (!$(this).prev().is(':checked') || !$(this).hasClass("hRadio_Checked")) {
				 if ($(this).prev().attr('disabled')) {
                    return;
                }
                $(".hRadio_Checked").each(function () {
                    if ($(this).prev().attr("name") == $(clickItem).prev().attr("name"))
                        $(this).removeClass("hRadio_Checked");
                });

                $(this).addClass("hRadio_Checked");
                
                
                
                //$(this).prev()[0].checked = true;
				$(this).prev().attr('checked', true);
			}

            event.stopPropagation();
        })
        .prev().hide();
    }

})(jQuery);