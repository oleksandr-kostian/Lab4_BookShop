/**
 * Created by Слава on 20.04.2016.
 */
$(document).ready(function() {
    $('a#edit').click( function(event){
        event.preventDefault();
        $('#overlay').fadeIn(400,
            function(){
                $('#edit_modal_form')
                    .css('display', 'block')
                    .animate({opacity: 1, top: '50%'}, 200);
            });
    });

    $('#edit_modal_close, #overlay').click( function(){
        $('#edit_modal_form')
            .animate({opacity: 0, top: '45%'}, 200,
                function(){
                    $(this).css('display', 'none');
                    $('#overlay').fadeOut(400);
                }
            );
    });
});
