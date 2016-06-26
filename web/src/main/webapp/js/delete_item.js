/**
 * Created by Слава on 23.04.2016.
 */
$(document).ready(function() {
    $('a#del').click( function(event){
        event.preventDefault();
        $('#overlay').fadeIn(400,
            function(){
                $('#del_modal_form')
                    .css('display', 'block')
                    .animate({opacity: 1, top: '50%'}, 200);
            });
    });

    $('#del_modal_close, #overlay').click( function(){
        $('#del_modal_form')
            .animate({opacity: 0, top: '45%'}, 200,
                function(){
                    $(this).css('display', 'none');
                    $('#overlay').fadeOut(400);
                }
            );
    });
});
